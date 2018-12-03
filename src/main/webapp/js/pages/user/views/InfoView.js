/**
 * Created by Evgeniy.Guzeev on 12.02.2018.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'text!../../user/templates/InfoTemplate.html'
], function($, _, backbone, InfoTemplate){
    return backbone.View.extend({

        tagName: 'div',
        className: 'modal fade',
        template: _.template(InfoTemplate),

        events:{
            "click #info-close" : "close"
        },

        close: function(){
            this.$el.modal('hide');
        },


        render : function(){
            this.$el.html(this.template) ;
            this.$el.modal('show');
        }
    });
});