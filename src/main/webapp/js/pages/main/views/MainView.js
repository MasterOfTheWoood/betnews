/**
 * Created by dell on 27.01.2018.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    '../../top/views/InfoMenuItemView',
    '../../top/views/UserMenuItemView',
    '../../news/views/NewsListView',
    '../../news/views/NewsFilterView',
    '../../news/views/CarouselView',
    '../../news/views/PopularNewsView',
    '../../user/views/LoginView',
    '../../user/models/UserModel',
    '../../news/collections/NewsCollection',
    '../../user/collections/DialogCollection',
    'text!templates/MainTemplate.html'
], function($, _, backbone, InfoMenuItemView, UserMenuItemView, NewsListView, NewsFilterView, CarouselView, PopularNewsView, LoginView, UserModel, NewsCollection, DialogCollection, MainTemplate){
    return backbone.View.extend({

        el: '#main-container',
        template: _.template(MainTemplate),
        infoMenuItemView: undefined,
        userMenuItemView: undefined,
        newsListView: undefined,
        newsFilterView: undefined,
        loginView: new LoginView(),
        types: undefined,
        topics: undefined,
        user: new UserModel(),
        userNews: new NewsCollection(),
        dialogs: new DialogCollection(),
        carouselView: undefined,
        popularNewsView: undefined,

        events:{
        },

        initialize: function(options){
            console.log("mainview::" + JSON.stringify(options));
            this.user = new UserModel();
            this.user.set(options.user);
            this.topics = options.topics;
            this.types = options.types;
            this.onLogin();
        },

        render : function(){
            console.log("main view render...");
            this.$el.html(this.template) ;
            this.loginView = new LoginView();
            this.listenTo(this.loginView, 'logged-in', this.onLogin);
            this.infoMenuItemView = new InfoMenuItemView();
            this.infoMenuItemView.render();
            this.userMenuItemView = new UserMenuItemView({main: this,topics: this.topics, types: this.types});
            this.userMenuItemView.render();
            this.newsListView = new NewsListView({main: this, topics: this.topics, types: this.types});
            this.carouselView = new CarouselView({main: this});
            this.popularNewsView = new PopularNewsView({main: this});
            this.newsFilterView = new NewsFilterView({topics: this.topics, types: this.types, main: this});
            this.newsFilterView.render();
            this.userMenuItemView.render();
        },

        getUser: function(){
            return this.user;
        },

        setUser: function (user) {
            this.user.set(user);
        },

        getUserNews: function () {
           return this.userNews;
        },

        setUserNews: function (news) {
            this.userNews.set(news)
        },

        pushNews: function(news){
            this.newsListView.pushNews(news)
        },

        getLoginView: function () {
           return this.loginView;
        },


        onLogin: function (token) {
            var self = this;
            console.log("main:: checkUser");
            $.when(
                $.ajax({
                    url: "/current",
                    method: "post",
                    headers: {
                        "X-Auth-Token" : token
                    }
                }),
                $.ajax({
                    url: "/member/news/current",
                    method: "post",
                    headers: {
                        "X-Auth-Token" : token
                    }
                }),

                this.dialogs.fetch()

            ).then(function(data, news){
                console.log("data: "  + JSON.stringify(data));
                console.log("news: "  + JSON.stringify(news));
                console.log("dialogs: "  + JSON.stringify(self.dialogs));
                self.setUser(data[0]);
                self.setUserNews(news);
                self.userMenuItemView.render()
            });
        },

        getDialogs: function(){
            return this.dialogs;
        }
    });
});
