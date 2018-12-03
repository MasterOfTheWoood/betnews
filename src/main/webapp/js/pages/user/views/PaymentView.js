/**
 * Created by Evgeniy.Guzeev on 13.02.2018.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'text!../../user/templates/PaymentTemplate.html'
], function($, _, backbone, UserConfigTemplate){
    return backbone.View.extend({

        tagName: 'div',
        className: 'modal fade',
        template: _.template(UserConfigTemplate),

        events:{
        },

        render : function(){
            this.$el.html(this.template(this.model.attributes));
            this.$el.modal('show');
        },

        close: function(){
            this.$el.modal('hide');
        }
    });
});