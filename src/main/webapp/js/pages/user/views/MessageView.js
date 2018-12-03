/**
 * Created by Evgeniy.Guzeev on 09.02.2018.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'text!../../user/templates/MessagesTemplate.html'
], function($, _, backbone, MessagesTemplate){
    return backbone.View.extend({

        tagName: 'li',
        template: _.template(MessagesTemplate),

        events:{

        },


        initialize: function (options) {
            this.message = options.message;
            this.current = options.current;
        },


        render : function(){
            console.log("" + JSON.stringify(this.message));
            return this.$el.html(this.template({message: this.message, current : this.current})) ;
        }
    });
});