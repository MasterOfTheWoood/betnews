/**
 * Created by dell on 07.05.2017.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    '../../news/views/NewsFullView',
    '../../user/views/UserLinkView',
    '../../news/views/CommentView',
    '../../news/views/ReplayView',
    '../../news/models/NewsModel',
    '../../news/models/CommentModel',
    '../../news/collections/NewsCollection',
    'text!../../news/templates/PopularNewsTemplate.html',
    'jquery_ui'
], function($, _, backbone, NewsFullView, UserLinkView, CommentView, ReplayView, NewsModel, CommentModel, NewsCollection, PopularNewsTemplate){
    return backbone.View.extend({

        el: '#popular-news',
        tagName: "div",
        template: _.template(PopularNewsTemplate),
        main: undefined,

        events:{
            "click .reply": "showReplay",
            "click #pop-news-comment-submit" : "sendComment"
        },

        initialize: function(options){
            this.main = options.main;
            var self = this;
            this.collection = new NewsCollection();
            this.collection.fetch({
                url: "/popular",
                success: function(){
                    self.render()
                }
            })
        },

        render : function(){
            this.$el.html(this.template({news: this.collection.at(0)}));
            var self = this;
            console.log(JSON.stringify(this.collection.at(0).get("comments")));
            this.collection.at(0).get("comments").forEach(function (comment) {
                var commentView = new CommentView({model: comment});
                self.$el.find("#pop-news-comments ul#pop-comments-list").append(commentView.render());
            });
        },

        showReplay: function(event){
            if(this.main.getUser() && (this.main.getUser().get("userNick") !=  "anonymous")) {
                var e = event || window.event;
                var newsId = this.$el.find("#pop-news-id").val();
                var commentId = $(e.target).data("comment-id");
                $(e.target).parents(".comment-body").find(".reply-list").append(new ReplayView({commentId: commentId, main : this.main, newsId: newsId}).render());
            }else {
                var loginView = this.main.getLoginView();
                loginView.render({message: "Для добавления комментария нужно авторизоваться"});
                this.listenTo(loginView, 'logged-in', this.addComment);
            }
        },

        sendComment: function (event) {
            var e = event || window.event;
            if(this.main.getUser() && (this.main.getUser().get("userNick") !=  "anonymous")) {
                var self = this;
                var comment = this.$el.find("#pop-news-comment-input").val();
                var newsId = this.$el.find("#pop-news-id").val();
                var commentReplayId = undefined;
                if($(e.target).val("commentId"))
                    commentReplayId = $(e.target).val("commentId");
                console.log("add comment " + comment);
                $.post("/member/news/addComment", {newsId: newsId, comment: comment, commentReplayId: commentReplayId}, function (data) {
                    var newComment = new CommentModel();
                    newComment.set(data);
                    var commentView = new CommentView({model: newComment, newsId: newsId});
                    self.$el.find("#news-comments-container").append(commentView.render());
                })
            }else {
                var loginView = this.main.getLoginView();
                loginView.render({message: "Для добавления комментария нужно авторизоваться"});
                this.listenTo(loginView, 'logged-in', this.addComment);
            }
        }
    });
});






