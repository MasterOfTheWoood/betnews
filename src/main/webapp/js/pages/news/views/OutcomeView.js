/**
 * Created by dell on 08.05.2017.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'text!../../news/templates/OutcomeTemplate.html'
], function($, _, backbone, OutcomeTemplate){
    return backbone.View.extend({

        template: _.template(OutcomeTemplate),
        tagName: "div",
        newsId: undefined,

        initialize: function (params) {
            this.newsId = params.newsId;
        },

        events:{
        },

        render : function(){
      //      console.log(this.$el.attr("id") + " outcome render " + JSON.stringify(this.model.attributes));
            this.$el.append(this.template(this.model.attributes)) ;
        }
    });
});

