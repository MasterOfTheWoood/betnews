define([
    'jquery',
    'underscore',
    'backbone',
    'text!../../user/templates/RegistrationTemplate.html'
], function($, _, backbone, RegistrationTemplate){
    return backbone.View.extend({

        tagName: 'div',
        className: 'modal fade',
        template: _.template(RegistrationTemplate),

        events:{
            "click #registration-submit" : "registration",
            "click #registration-close" : "close"
        },

        render : function(){
            this.$el.html(this.template);
            this.$el.modal('show');
        },

        registration: function () {
            var self = this;
            console.log(JSON.stringify(this.$el.find("#registration-form").serializeObject()));
            $.ajax({
                contentType: 'application/json',
                data: JSON.stringify(this.$el.find("#registration-form").serializeObject()),
                dataType: 'json',
                success: function(data){
                    self.close();
                },
                error: function(){
                   // self.close();
                },
                processData: false,
                type: 'POST',
                url: '/registration'
            });
        },

        close: function(){
            this.$el.modal('hide');
        }
    });
});

