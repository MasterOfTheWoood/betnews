/**
 * Created by Evgeniy.Guzeev on 09.02.2018.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    '../../user/views/ContactView',
    '../../user/views/MessageView',
    '../../user/models/MailModel',
    'text!../../user/templates/MailTemplate.html'
], function($, _, backbone, ContactView, MessageView, MailModel, MailTemplate){
    return backbone.View.extend({

        tagName: 'div',
        className: 'modal fade',
        template: _.template(MailTemplate),
        dialogs: [],
        currentUser: undefined,
        resposer: undefined,

        events:{
            "click #mail-close" : "close",
            "click .message-answer-send" : "answer",
            "click #profile-img" : "onProfileClick",
            "click .expand-button" : "onExpand",
            "click #status-options ul li" : "onStatusClick",
            "click .submit" : "newMessage",
            "click .change-contact" : "onContactClick"
        },

        close: function(){
            this.$el.modal('hide');
        },

        initialize: function (options) {
            this.dialogs = options.dialogs;
            this.currentUser = options.currentUser;
        },


        render : function(){
            console.log("dialogs: " + JSON.stringify(this.dialogs));
            this.$el.html(this.template({dialogs: this.dialogs, currentUser: this.currentUser})) ;
            this.$el.modal('show');
            this.init();
        },

        init: function(){
            $(".messages").animate({ scrollTop: $(document).height() }, "fast");

            $(window).on('keydown', function(e) {
                if (e.which == 13) {
                    this.newMessage();
                    return false;
                }
            });
        },

        onProfileClick : function(){
            $("#status-options").toggleClass("active");
        },

        onExpand : function(){
            $("#profile").toggleClass("expanded");
            $("#contacts").toggleClass("expanded");
        },

        onStatusClick : function(){
            $("#profile-img").removeClass();
            $("#status-online").removeClass("active");
            $("#status-away").removeClass("active");
            $("#status-busy").removeClass("active");
            $("#status-offline").removeClass("active");
            $(this).addClass("active");

            if($("#status-online").hasClass("active")) {
                $("#profile-img").addClass("online");
            } else if ($("#status-away").hasClass("active")) {
                $("#profile-img").addClass("away");
            } else if ($("#status-busy").hasClass("active")) {
                $("#profile-img").addClass("busy");
            } else if ($("#status-offline").hasClass("active")) {
                $("#profile-img").addClass("offline");
            } else {
                $("#profile-img").removeClass();
            }

            $("#status-options").removeClass("active");
        },

        newMessage : function(){
            var self = this;
            var message = $(".message-input input").val();
            if($.trim(message) == '') {
                return false;
            }
            $.post("/member/sendMessage", {message: message, to : this.resposer}, function(message){
                new MessageView({message : new MailModel(message), current: self.currentUser}).render().appendTo($('.messages ul'));
                $('.message-input input').val(null);
                $('.contact.active .preview').html('<span>You: </span>' + message);
                $(".messages").animate({ scrollTop: $(document).height() }, "fast");
            });
        },

        onContactClick: function(event){
            console.log("onContactClick...");
            var e = event || window.event;
            var self = this;
            var fromId = $(e.target).parents(".contact").data("user-id");
            this.resposer = fromId;
            var fromNick = $(e.target).parents(".contact").data("user-nick");
            var fromAvatar = $(e.target).parents(".contact").data("user-avatar");
            $(".messages").html("<ul></ul>");
            new ContactView({nick: fromNick, avatar: fromAvatar}).render().appendTo($('.contact-profile'));
            $.post("/member/getMessages", {from: fromId, offset: 0}, function(messages){
                messages.forEach(function(message){
                    new MessageView({message : new MailModel(message), current: self.currentUser}).render().appendTo($('.messages ul'));
                });
                $(".messages").animate({ scrollTop: $(document).height() }, "fast");
            });
        }
    });
});