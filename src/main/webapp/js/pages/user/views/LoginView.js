/**
 * Created by dell on 08.05.2017.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    '../../user/views/RegistrationView',
    '../../user/views/ForgotPasswordView',
    'text!../../user/templates/LoginTemplate.html'
], function($, _, backbone, RegistrationView, ForgotPasswordView, LoginTemplate){
    return backbone.View.extend({

        tagName: 'div',
        className: 'modal fade',
        template: _.template(LoginTemplate),
        registrationView: undefined,
        forgotPasswordView: undefined,
        message: undefined,

        events:{
            "click #login-submit" : "login",
            "click #login-close" : "close",
            "click #login-create-account" : "openRegistration",
            "click #login-forgot-password" : "openForgetPassword"
        },

        initialize: function () {

        },

        close: function(){
            this.$el.modal('hide');
        },

        login: function(event){
            var self = this;
            var username = this.$el.find("#login-text-input").val();
            var password = this.$el.find("#login-password-input").val();
            console.log("username:: " + username + " password: " +  password);

            $.ajax({
                contentType: 'application/json',
                data: JSON.stringify({"username": username, "password": password}),
                dataType: 'json',
                success: function(data){
                    console.log("success:: " + JSON.stringify(data));
                    $.ajaxSetup({
                        headers : {
                            'X-Auth-Token' : data.token
                        }
                    });
                    self.trigger("logged-in", data.token);
                    self.close();
                },
                error: function(){
                    console.log("success:: " + JSON.stringify(data));
                    // self.close();
                },
                processData: false,
                type: 'POST',
                url: '/auth'
            });
        },

        render : function(options){
            this.message = options? options.message : "";
            this.$el.html(this.template({message: this.message})) ;
            this.$el.modal('show');
        },

        openRegistration: function () {
            this.close();
            if(!this.registrationView)
                this.registrationView = new RegistrationView();
            this.registrationView.render();
        },

        openForgetPassword: function () {
            this.close();
            if(!this.forgotPasswordView)
                this.forgotPasswordView = new ForgotPasswordView();
            this.forgotPasswordView.render();
        }
    });
});