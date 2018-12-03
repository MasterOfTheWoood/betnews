/**
 * Created by Evgeniy.Guzeev on 27.02.2018.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'text!../../news/templates/OutcomeEditTemplate.html'
], function($, _, backbone, OutcomeEditTemplate){
    return backbone.View.extend({

        template: _.template(OutcomeEditTemplate),
        tagName: "div",
        newsId: undefined,

        initialize: function (params) {
            this.newsId = params.newsId;
        },

        events:{
            "click .outcome-label" : "showOutcomeEditor",
            "blur .outcome-label-editor-input" : "showOutcomeLabel",
            "click .outcome-delete" : "remove",
            "change .outcome-label-editor-input" : "changeLabel"
        },

        render : function(){
        //    console.log("Outcome edit id = " + this.$el.attr("id"));
            this.$el.append(this.template(this.model.attributes)) ;
        },

        showOutcomeEditor: function(event){
            var e = event || window.event;
            $(e.target).hide();
            $(e.target.parentNode).find(".outcome-label-editor").show();
        },

        showOutcomeLabel: function(event){
            var e = event || window.event;
            $(e.target.parentNode).hide();
            $(e.target.parentNode.parentNode).find(".outcome-label").show();
        },

        changeLabel: function (event) {
            var e = event || window.event;
            var value = $(e.target).val();
            $(e.target.parentNode.parentNode).find(".outcome-label").html(value);
        }
    });
});

