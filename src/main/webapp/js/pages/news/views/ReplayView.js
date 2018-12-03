
/**
 * Created by Evgeniy.Guzeev on 15.05.2018.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    '../../news/views/CommentView',
    '../../news/models/NewsModel',
    'text!../../news/templates/ReplayTemplate.html'
], function($, _, backbone, CommentView, NewsModel, ReplayTemplate){
    return backbone.View.extend({

        template: _.template(ReplayTemplate),

        events:{
            "click #replay-comment-submit" : "replay"
        },

        initialize: function(options){
            this.commentId = options.commentId;
            this.newsId = options.newsId;
            this.main = options.main;
        },

        render : function(){
            return this.$el.html(this.template({commentId: this.commentId}));
        },

        replay : function () {
            if(this.main.getUser() && (this.main.getUser().get("userNick") !=  "anonymous")) {
                var self = this;
                var comment = this.$el.find("#replay-comment-input").val();
                console.log("add comment " + comment);
                $.post("/member/news/addComment", {newsId: this.newsId, comment: comment, replayComment: this.commentId}, function (data) {
                    var news = new NewsModel();
                    news.set(data);
                    var commentView = new CommentView({model: news});
                    self.$el.html(commentView.render());
                })
            }else {
                var loginView = this.main.getLoginView();
                loginView.render({message: "Для добавления комментария нужно авторизоваться"});
                this.listenTo(loginView, 'logged-in', this.addComment);
            }
        }
    });

});