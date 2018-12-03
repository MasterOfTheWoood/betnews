/**
 * Created by dell on 07.05.2017.
 */
define([
    'backbone',
    'bootstrap',
    'views/MainView',
    '../news/collections/TopicCollection',
    '../news/collections/TypeCollection',
    '../news/collections/NewsCollection'
], function(Backbone, Bootstrap, MainView, TopicCollection, TypeCollection, NewsCollection){
    var Route =  Backbone.Router.extend({
        mainView: undefined,
        routes:{ "": "empty"},

        initialize: function(){

        },

        startHistory: function(){
            Backbone.history.start();
        },

        empty: function () {
            this.load();
        },

        load: function () {
            var self = this;
            this.topicCollection = new TopicCollection();
            this.typeCollection = new TypeCollection();
            this.userNews = new NewsCollection();
            $.post("/current", {}, function (user) {
                $.when(
                    self.topicCollection.fetch(),
                    self.typeCollection.fetch(),
                    $.ajax({
                        url: "/member/news/current",
                        method: "post",
                        success: function (data) {
                            if (data) {

                                self.userNews = data;
                            }
                        }
                    })
                ).done(function(){
                    self.mainView = new MainView({
                        user: user,
                        topics: self.topicCollection,
                        types: self.typeCollection
                    });
                    self.mainView.setUserNews(self.userNews);
                    self.mainView.render();
                });
            })
        }
    });
    return new Route();
});
