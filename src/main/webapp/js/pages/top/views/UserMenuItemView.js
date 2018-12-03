/**
 * Created by Evgeniy.Guzeev on 25.01.2018.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    '../../user/views/UserConfigView',
    '../../user/views/MailView',
    '../../user/views/PaymentView',
    'text!../../top/templates/UserMenuItemsTemplate.html'
], function($, _, backbone, UserConfigView, MailView, PaymentView, UserMenuItemsTemplate){
    return backbone.View.extend({

        el: '#login-menu-container',
        template: _.template(UserMenuItemsTemplate),
        loginView: undefined,
        userNews: {},
        userConfigView: undefined,

        events:{
            "click #properties-menu-item" : "openConfig",
            "click #mail-menu-item" : "openMail",
            "click #login-menu-item": "openLogin",
            "click #payment-menu-item": "openPayment"
        },

        initialize: function (options) {
            this.main = options.main;
            this.topics = options.topics;
            this.types = options.types;
        },

        render : function(){
            console.log("userMenu:: render: " + JSON.stringify(this.main.getUser()));
            this.$el.html(this.template(this.main.getUser().attributes));
        },

        openConfig: function () {
            if(!this.userConfigView)
                this.userConfigView = new UserConfigView({model: this.main.getUser(), userNews: this.main.getUserNews(), topics: this.topics, types: this.types});
            this.userConfigView.render();
        },

        openMail: function () {
            if(!this.mailView)
                this.mailView = new MailView({dialogs: this.main.getDialogs(), currentUser: this.main.getUser()});
            this.mailView.render();
        },

        openPayment: function(){
            if(!this.paymentView)
                this.paymentView = new PaymentView();
            this.paymentView.render();
        },

        openLogin: function () {
            console.log("openLogin..." + this.loginView);
            var loginView = this.main.getLoginView();
            loginView.render();
        }
    });
});
