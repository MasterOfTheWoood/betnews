/**
 * Created by Evgeniy.Guzeev on 19.03.2018.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'text!../../user/templates/AlertTemplate.html'
], function($, _, backbone, AlertTemplate){
    return backbone.View.extend({

        tagName: 'div',
        className: 'modal fade',
        template: _.template(AlertTemplate),
        message: "",
        events:{
            "click #alert-close" : "close"
        },

        close: function(){
            this.$el.modal('hide');
        },

        initialize: function(options){
            this.message = options.message;
        },

        render : function(){
            this.$el.html(this.template({message: this.message})) ;
            this.$el.modal('show');
        }
    });
});