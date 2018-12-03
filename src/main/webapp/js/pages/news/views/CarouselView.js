/**
 * Created by dell on 07.05.2017.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    '../../news/views/NewsFullView',
    '../../user/views/UserLinkView',
    '../../news/collections/NewsCollection',
    'text!../../news/templates/NewsCarouselTemplate.html',
    'jquery_ui',
    'util',
    'bootstrap'
], function($, _, backbone, NewsFullView, UserLinkView, NewsCollection, NewsCarouselTemplate){
    return backbone.View.extend({

        el: '#news-carousel',
        tagName: "div",
        template: _.template(NewsCarouselTemplate),
        main: undefined,

        events:{

        },

        initialize: function(options){
            this.main = options.main;
            var self = this;
            this.collection = new NewsCollection();
            this.collection.fetch({
                url: "/carousel",
                success: function(){
                    self.render()
                }
            })
        },

        render : function(){
            this.$el.html(this.template({newsList: this.collection}));
        }
    });
});






