/**
 * Created by dell on 07.05.2017.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    '../../news/collections/NewsCollection',
    '../../news/views/NewsView'
], function($, _, backbone, NewsCollection, NewsView){
    return backbone.View.extend({

        tagName: "div",
        className: "panel panel-default",
        events:{
        },

        initialize: function(options){
            var self = this;
            this.main = options.main;
            this.collection = new NewsCollection();
            this.collection.fetch({
                success: function(){
                    self.render()
                }
            })
        },

        render : function(){
          //  console.log("news collection" + JSON.stringify(this.collection));
            var self = this;
            this.collection.forEach(function (news) {
                var newsView = new NewsView({news: news, main: self.main});
                newsView.render();
            })
        },

        pushNews : function(news){
          //  console.log("pushNews::" + JSON.stringify(news));
            this.collection.add(news);
            var newsView = new NewsView({news: news, main: this.main});
            newsView.render();
        }
    });
});

