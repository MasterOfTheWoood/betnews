/**
 * Created by dell on 07.05.2017.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    '../../news/views/NewsFullView',
    '../../user/views/UserLinkView',
    'text!../../news/templates/NewsTemplate.html',
    'jquery_ui'
], function($, _, backbone, NewsFullView, UserLinkView, NewsTemplate){
    return backbone.View.extend({

        el: '#news-container',
        tagName: "div",
        className: ".col-xs-6",
        template: _.template(NewsTemplate),
        main: undefined,

        events:{
            "click .news-description-more" : "showMore"
        },

        initialize: function(options){
            this.news = options.news;
            this.main = options.main;
        },

        render : function(){
         //   console.log("append news: " + JSON.stringify(this.news));
            this.$el.append(this.template({news: this.news}));
            var userLinkView = new UserLinkView({user: this.news.get("author")});
            this.$el.find("#news-author-" + this.news.get("id")).html(userLinkView.render());
        },

        showMore: function (event) {
            var e = event || window.event;
            var newsId = $(e.target).data("news-id");
            if(newsId == this.news.get("id")) {
                var newsFullView = new NewsFullView({model: this.news, main: this.main});
                newsFullView.render();
            }
        }
    });
});





