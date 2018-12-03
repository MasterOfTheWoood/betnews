/**
 * Created by Evgeniy.Guzeev on 15.05.2018.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'text!../../news/templates/OutcomeConfirmationTemplate.html'
], function($, _, backbone, OutcomeConfirmationTemplate){
    return backbone.View.extend({

        tagName: 'div',
        className: 'modal fade',
        template: _.template(OutcomeConfirmationTemplate),
        outcomeId: undefined,

        events:{
            "click #outcome-confirm-submit" : "confirmOutcome",
            "click #outcome-confirm-close" : "close"
        },

        close: function(){
            this.$el.modal('hide');
        },

        initialize: function(options){
            this.outcomeId = options.outcomeId;
            this.outcomeDesc = options.outcomeDesc;
        },

        render : function(){
            this.$el.html(this.template({outcomeId: this.outcomeId, outcomeDesc: this.outcomeDesc})) ;
            this.$el.modal('show');
        },

        confirmOutcome: function(){
            var self = this;
            var comment = this.$el.find("#confirmation-news-comment").val();
            var confirmation = this.$el.find("#confirmation-news-confirmation").val();
            $.post("/member/news/confirm/", {outcomeId: this.outcomeId, confirmation: confirmation, comment: comment},
                function (data) {
                this.close();
            })
        }
    });
});