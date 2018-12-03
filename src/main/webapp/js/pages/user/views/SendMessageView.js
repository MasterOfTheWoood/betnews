/**
 * Created by Evgeniy.Guzeev on 22.05.2018.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'text!../../user/templates/SendMessageTemplate.html'
], function($, _, backbone, SendMessageTemplate){
    return backbone.View.extend({

        tagName: 'div',
        className: 'modal fade',
        template: _.template(SendMessageTemplate),
        message: "",
        events:{
            "click #send-message-submit" : "send",
            "click #send-message-cancel" : "close"
        },

        close: function(){
            var editor = CKEDITOR.instances["send-message-body"];
            if (editor) { editor.destroy(); }
            this.$el.modal('hide');
        },

        initialize: function(options){
            this.userTo = options.userTo;
        },

        render : function(){
            this.$el.html(this.template({user: this.userTo})) ;
            this.$el.modal('show');
            var self = this;
            var loadWait = setInterval(function () {
                console.log("loadWait...");
                if(self.isFunction(CKEDITOR.replace)) {
                    clearInterval(loadWait);
                    CKEDITOR.replace('send-message-body');
                }
            }, 1000);
        },

        isFunction: function (functionToCheck) {
            var getType = {};
            return functionToCheck && getType.toString.call(functionToCheck) === '[object Function]';
        },

        send: function(){
            var self = this;
            var userTo = this.userTo;
            $textarea =  $('#send-message-body');
            $textarea.val(CKEDITOR.instances["send-message-body"].getData());
            var message = this.$el.find("#send-message-body").val();
            $.post("/member/sendMessage", {message: message, to : userTo.id}, function(){
                self.close();
            });
        }
    });
});