/**
 * Created by Evgeniy.Guzeev on 25.04.2018.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'text!../../news/templates/VoteTemplate.html'
], function($, _, backbone, VoteTemplate){
    return backbone.View.extend({

        tagName: 'div',
        className: 'modal fade',
        template: _.template(VoteTemplate),
        message: "",
        news: undefined,
        outcome: undefined,
        events:{
            "click #news-vote-close" : "close",
            "click #news-vote-submit" : "vote"
        },

        close: function(){
            this.$el.modal('hide');
        },

        initialize: function(options){
            this.news = options.news;
            this.outcome = options.outcome;
        },

        render : function(){
            console.log("vote news:" + JSON.stringify(this.news) + " outcome: " +  JSON.stringify(this.outcome));
            this.$el.html(this.template({outcome: this.outcome, news: this.news})) ;
            this.$el.modal('show');
        },

        vote:  function() {
            var value =  this.$el.find("#news-vote-bet").val();
            var self = this;
            $.post("/member/news/vote/", {newsId: this.news.get("id"), outcomeId: this.outcome.get("id"), value: value},
                function (data) {
                    self.trigger("vote-news", data);
                    self.close();
                })
        }
    });
});