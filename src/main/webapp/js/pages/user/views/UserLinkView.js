/**
 * Created by Evgeniy.Guzeev on 06.06.2018.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    '../../user/views/SendMessageView',
    'text!../../user/templates/UserLinkTemplate.html'
], function($, _, backbone, SendMessageView, UserLinkTemplate){
    return backbone.View.extend({

        template: _.template(UserLinkTemplate),
        events:{
            "click .write-message" : "openWriteMessage"
        },

        initialize: function(options){
            this.user = options.user;
        },

        render : function(){
            var id = this.makeId();
            this.$el.html(this.template({user: this.user, id: id})) ;
            return this.$el;
        },

        makeId: function() {
            var text = "";
            var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            for (var i = 0; i < 5; i++)
                text += possible.charAt(Math.floor(Math.random() * possible.length));
            return text;
        },

        openWriteMessage: function(){
            var sendMessageView = new SendMessageView({userTo: this.user});
            sendMessageView.render();
        }
    });
});