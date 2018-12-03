/**
 * Created by dell on 30.05.2017.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    '../../news/views/AddNewsView',
    '../../news/models/NewsModel',
    'text!../../user/templates/UserConfigTemplate.html'
], function($, _, backbone, AddNewsView, NewsModel, UserConfigTemplate){
    return backbone.View.extend({

        template: _.template(UserConfigTemplate),
        tagName: 'div',
        className: 'modal fade',
        userNews: {},
        topics: undefined,
        types: undefined,

        events:{
            "click #login-submit" : "saveAll",
            "click #config-add-news-button-icon" : "openAddNews",
            "click #user-config-close" : "close"
        },

        close: function(){
            this.$el.modal('hide');
        },

        initialize: function (options) {
          //  console.log("User config: " + JSON.stringify(options));
            this.userNews = options.userNews;
            this.topics = options.topics;
            this.types = options.types;
        },

        render : function(){
          //  console.log("userNews:: " + JSON.stringify(this.userNews));
            this.$el.html(this.template({user: this.model, userNews: this.userNews}));
            this.$el.modal('show');
            this.showImageUpload();
        },

        showImageUpload: function(){
                $('[data-toggle="tooltip"]').tooltip();
            /*$('#fadeInOut').on('click', function(){
                $('.uploadCompleteOverlay').show().addClass('animated zoomIn');
                setTimeout(function(){
                    $('.uploadCompleteOverlay').addClass('zoomOut');
                }, 2000);
                setTimeout(function(){
                    $('.uploadCompleteOverlay').removeClass('animated zoomIn zoomOut').hide();
                }, 3000);
            });*/
        },

        saveAll: function () {
            var self = this;
            $.ajax({
                type: "POST",
                url: "/member/user/save/",
                contentType: 'application/json',
                data: JSON.stringify(this.$el.find("#user-settings-form").serializeObject()),
                dataType: 'json',
                processData: false,
                success: function(data)
                {
                    console.log("success: " + JSON.stringify(data));
                    self.close();
                },
                error: function(data){
                    console.log("error" + JSON.stringify(data));
                    self.showError(data.message);
                }
            });
        },
        openAddNews: function(){
            if (!this.addNewsView)
                this.addNewsView = new AddNewsView({
                    model: new NewsModel(),
                    topics: this.topics,
                    types: this.types
                });
            this.addNewsView.render();
            this.listenTo(this.addNewsView, 'add-news', this.pushNews);
        }
    });
});
