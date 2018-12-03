define([
    'jquery',
    'underscore',
    'backbone',
    'text!../../user/templates/ForgotPasswordTemplate.html'
], function($, _, backbone, InfoTemplate){
    return backbone.View.extend({

        tagName: 'div',
        className: 'modal fade',
        template: _.template(InfoTemplate),

        events:{
            "click #forgot-password-close" : "close",
            "click #forgot-password-submit" : "sendForgotPassword"
        },

        render : function(){
            this.$el.html(this.template) ;
            this.$el.modal('show');
        },

        close: function(){
            this.$el.modal('hide');
        },

        sendForgotPassword: function(){
            var email = this.$el.find("#email-text-input").val();
            $.post("/remindPassword", {email: email}, function(data){

            });
        }
    });
});