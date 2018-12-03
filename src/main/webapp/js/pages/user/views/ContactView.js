/**
 * Created by Evgeniy.Guzeev on 07.08.2018.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'text!../../user/templates/ContactTemplate.html'
], function($, _, backbone, ContactTemplate){
    return backbone.View.extend({

        template: _.template(ContactTemplate),

        events:{

        },

        initialize: function (options) {
            this.nick = options.nick;
            this.avatar = options.avatar;
        },


        render : function(){
            return this.$el.html(this.template({nick: this.nick, avatar: this.avatar})) ;
        }
    });
});