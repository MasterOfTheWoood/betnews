/**
 * Управление полным видом новости.
 * Тут будут доступны все данные о новости полное описание, комментарии и головование
 * Created by dell on 06.04.2018.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    '../../news/models/OutcomeModel',
    '../../news/views/CommentView',
    '../../news/views/VoteView',
    '../../news/views/OutcomeConfirmationView',
    '../../user/views/UserLinkView',
    '../../user/views/LoginView',
    '../../news/models/NewsModel',
    'text!../../news/templates/NewsFullTemplate.html',
    'select2'
], function($, _, backbone, OutcomeModel, CommentView, VoteView, OutcomeConfirmationView, UserLinkView, LoginView,  NewsModel, NewsFullTemplate){
    return backbone.View.extend({

        template: _.template(NewsFullTemplate),
        tagName: 'div',
        className: 'modal fade',
        main: undefined,

        events:{
            "click #news-full-close" : "close",
            "click #add-news-comment-button" : "addComment",
            "change .news-outcomes-input": "openVote",
            "click .new-outcome-confirm": "confirmOutcome",
            "click .like" : "like",
            "click .dislike" : "dislike",
            "click .like-news" : "likeNews",
            "click .dislike-news" : "dislikeNews"
        },

        initialize: function(options){
            this.main = options.main;
        },

        render : function(){
          //  console.log("full news :: " + JSON.stringify(this.model));
            var self  = this;
            this.$el.html(this.template({news: this.model, user: this.main.getUser()}));
            this.model.get("comments").forEach(function (comment) {
                var commentView = new CommentView({model: comment});
                self.$el.find("#news-comments-container ul#comments-list").append(commentView.render());
            });
            var userLinkView = new UserLinkView({user: this.model.get("author")});
            this.$el.find(".news-author").html(userLinkView.render());
            this.$el.modal('show');
            /*var loadWait = setInterval(function () {
                console.log("loadWait...");
                if(self.isFunction(CKEDITOR.replace)) {
                    clearInterval(loadWait);
                    CKEDITOR.replace('add-comment-textarea');
                }
            }, 1000);*/
        },

        isFunction: function (functionToCheck) {
            var getType = {};
            return functionToCheck && getType.toString.call(functionToCheck) === '[object Function]';
        },

        close: function(){
            var editor = CKEDITOR.instances["add-comment-textarea"];
            if (editor) { editor.destroy(); }
            this.$el.modal('hide');
        },

        addComment: function () {
            if(this.main.getUser() && (this.main.getUser().get("userNick") !=  "anonymous")) {
                var self = this;
                $textarea =  $('#add-comment-textarea');
                $textarea.val(CKEDITOR.instances["add-comment-textarea"].getData());
                var comment = this.$el.find("#add-comment-textarea").val();
                var newsId = this.$el.find("#newsId").val();
                console.log("add comment " + comment);
                $.post("/member/news/addComment", {newsId: newsId, comment: comment}, function (data) {
                    var news = new NewsModel();
                    news.set(data);
                    var commentView = new CommentView({model: news});
                    self.$el.find("#news-comments-container").append(commentView.render());
                })
            }else {
                var loginView = this.main.getLoginView();
                loginView.render({message: "Для добавления комментария нужно авторизоваться"});
                this.listenTo(loginView, 'logged-in', this.addComment);
            }
        },

        openVote: function(event){
            if(this.main.getUser() && (this.main.getUser().get("userNick") !=  "anonymous")) {
                var e = event || window.event;
                var outcomeId = $(e.target).val();
                var outcome = this.model.get("outcomes").getById(outcomeId);
                var voteView = new VoteView({news: this.model, outcome: outcome});
                voteView.render();
                this.listenTo(voteView, 'vote-news', this.onVoteNews);
            }else {
                var loginView = this.getLoginView();
                loginView.render({message: "Для совершения ставки нужно авторизоваться"});
                this.listenTo(loginView, 'logged-in', this.onLogin);
            }
        },

        onVoteNews : function(data){
            this.$el.find('.news-outcomes-input').prop('disabled', true);
        },

        approve:  function() {
            var newsId = this.$el.find("newsId").val;
            console.log("approve " + newsId);
            $.post("/member/news/approve/", {newsId: newsId}, function (data) {

            })
        },

        likeNews: function (event) {
            if(this.main.getUser() && (this.main.getUser().get("userNick") !=  "anonymous")) {
                var e = event || window.event;
                var target = $(e.target).data("news-id") ? $(e.target) : $(e.target).parent();
                var newsId = target.data("news-id");
                console.log("like " + newsId);
                $.post("/member/news/like/", {newsId: newsId},
                    function (data) {
                        target.html('<i class="glyphicon glyphicon-thumbs-up icon"></i>' + data.likes.length);
                        target.parent().find(".dislike-news").html('<i class="glyphicon glyphicon-thumbs-down icon"></i>' + data.dislikes.length)
                    });
            }else {
                var loginView = this.main.getLoginView();
                loginView.render({message: "Для голосования за новость нужно авторизоваться"});
                this.listenTo(loginView, 'logged-in', this.likeNews);
            }
        },

        dislikeNews: function (event) {
            if(this.main.getUser() && (this.main.getUser().get("userNick") !=  "anonymous")) {
                var e = event || window.event;
                var target = $(e.target).data("news-id") ? $(e.target) : $(e.target).parent();
                var newsId = target.data("news-id");
                $.post("/member/news/dislike/", {newsId: newsId},
                    function (data) {
                        target.html('<i class="glyphicon glyphicon-thumbs-down icon"></i>' + data.dislikes.length);
                        target.parent().find(".like-news").html('<i class="glyphicon glyphicon-thumbs-up icon"></i>' + data.likes.length)
                    });
            }else {
                var loginView = new LoginView({message: "Для голосования против новости нужно авторизоваться"});
                loginView.render();
                this.listenTo(loginView, 'logged-in', this.dislikeNews);
            }
        },


        like: function (event) {
            if(this.main.getUser() && (this.main.getUser().get("userNick") !=  "anonymous")) {
                var e = event || window.event;
                var target = $(e.target).data("comment-id") ? $(e.target) : $(e.target).parent();
                var commentId = target.data("comment-id");
                console.log("like " + commentId);
                $.post("/member/news/comment/like/", {commentId: commentId},
                    function (data) {
                        target.html('<i class="glyphicon glyphicon-thumbs-up icon"></i>' + data.likes.length);
                        target.parent().find(".dislike").html('<i class="glyphicon glyphicon-thumbs-down icon"></i>' + data.dislikes.length)
                    });
            }else {
                var loginView = new LoginView({message: "Для голосования за комментарий нужно авторизоваться"});
                loginView.render();
                this.listenTo(loginView, 'logged-in', this.like);
            }
        },

        dislike: function (event) {
            if(this.main.getUser() && (this.main.getUser().get("userNick") !=  "anonymous")) {
                var e = event || window.event;
                var target = $(e.target).data("comment-id") ? $(e.target) : $(e.target).parent();
                var commentId = target.data("comment-id");
                $.post("/member/news/comment/dislike/", {commentId: commentId},
                    function (data) {
                        target.html('<i class="glyphicon glyphicon-thumbs-down icon"></i>' + data.dislikes.length);
                        target.parent().find(".like").html('<i class="glyphicon glyphicon-thumbs-up icon"></i>' + data.likes.length)
                    });
            }else {
                var loginView = this.main.getLoginView();
                loginView.render({message: "Для голосования против комментария нужно авторизоваться"});
                this.listenTo(loginView, 'logged-in', this.dislike);
            }
        },

        confirmOutcome: function(event){
            var e = event || window.event;
            var outcomeId = $(e.target).data("outcome-id");
            var outcomeDesc = $(e.target).data("outcome-desc");
            var outcomeConfirmationView = new OutcomeConfirmationView({outcomeId: outcomeId, outcomeDesc: outcomeDesc});
            outcomeConfirmationView.render();
        }
    });
});
