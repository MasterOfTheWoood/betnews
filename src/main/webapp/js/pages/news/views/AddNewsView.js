/**
 * Created by Evgeniy.Guzeev on 22.01.2018.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    '../../news/models/OutcomeModel',
    '../../news/models/NewsModel',
    '../../news/views/OutcomeEditView',
    'text!../../news/templates/AddNewsTemplate.html',
    'select2'
], function($, _, backbone, OutcomeModel, NewsModel, OutcomeEditView, AddNewsTemplate){
    return backbone.View.extend({

        template: _.template(AddNewsTemplate),
        tagName: 'div',
        className: 'modal fade',
        topics: undefined,
        types: undefined,

        events:{
            "click #news-add-submit" : "addNews",
            "click #news-add-outcome" : "addOutcome",
            "change #add-news-type" : "toggleArbiter"
        },

        initialize: function(options){
            this.topics = options.topics;
            this.types = options.types;
        },

        render : function(){
            console.log("AddNewsView render..");
            var self = this;
            this.$el.html(this.template({news: this.model, topics: this.topics, types: this.types}));
            this.model.get("outcomes").forEach(function (outcome) {
                var outcomeView = new OutcomeEditView({newsId: self.model.get("id"), model: outcome, el: "#news-add-outcomes"});
                outcomeView.render();
            });
            this.$el.modal('show');
            var loadWait = setInterval(function () {
                console.log("loadWait...");
                if(self.isFunction(CKEDITOR.replace)) {
                    clearInterval(loadWait);
                    CKEDITOR.replace('add-news-description');
                }
            }, 1000);
        },

        isFunction: function (functionToCheck) {
            var getType = {};
            return functionToCheck && getType.toString.call(functionToCheck) === '[object Function]';
        },
        close: function(){
            this.$el.modal('hide');
        },

        addOutcome: function(){
            var outcome = new OutcomeModel();
            this.model.get("outcomes").add(outcome);
            var outcomeView = new OutcomeEditView({newsId: this.model.get("id"), model: outcome, el: "#news-add-outcomes"});
            outcomeView.render();
        },

        toggleArbiter: function() {
            console.log("toggleArbiter::" + this.$el.find("#add-news-type").val());
            if (this.$el.find("#add-news-type").val() == "PRIVATE") {
                this.$el.find("#add-news-arbiter-container").show();
                this.$el.find("#add-news-can-vote-users-container").show();
                $('#add-news-arbiter').select2({
                    ajax: {
                        url: '/member/users',
                        dataType: 'json'
                    }
                });

                $('#add-news-canVoteUsers').select2({
                    ajax: {
                        url: '/member/users',
                        dataType: 'json'
                    }
                });
            }
            else {
                this.$el.find("#add-news-arbiter-container").hide();
                this.$el.find("#add-news-can-vote-users-container").hide();
            }
        },

        showError: function(message){

        },

        addNews: function(){
            var self = this;
            $textarea =  $('#add-news-description');
            $textarea.val(CKEDITOR.instances["add-news-description"].getData());
            $.ajax({
                  type: "POST",
                  url: "/member/news/create/",
                  contentType: 'application/json',
                  data: JSON.stringify(this.$el.find("#add-news-form").serializeObject()),
                  dataType: 'json',
                  processData: false,
                  success: function(data)
                  {
                      console.log("success: " + JSON.stringify(data));
                      self.close();
                      var news = new NewsModel(data);
                    //  news.set(data);
                      self.trigger("add-news", news);
                  },
                  error: function(data){
                      console.log("error" + JSON.stringify(data));
                      self.showError(data.message);
                  }
            });
        }
    });
});
