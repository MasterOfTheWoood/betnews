/**
 * Created by dell on 08.05.2017.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    '../../news/views/AddNewsView',
    '../../news/models/NewsModel',
    '../../user/views/LoginView',
    'text!../../news/templates/NewsFilterTemplate.html'
], function($, _, backbone, AddNewsView, NewsModel, LoginView, NewsFilterTemplate){
    return backbone.View.extend({

        el: '#search-container',
        template: _.template(NewsFilterTemplate),
        addNewsView: undefined,
        topics: undefined,
        types: undefined,
        main: undefined,

        events:{
            'click #filter-search-button': 'search',
            'click #filter-add-news-button': 'openAddNews'
        },

        initialize: function(options){
            this.topics = options.topics;
            this.types = options.types;
            this.main = options.main;
        },

        render : function(){
            this.$el.html(this.template({topics: this.topics, types: this.types}));
        },

        openAddNews: function(){
            if(this.main.getUser() && (this.main.getUser().get("userNick") !=  "anonymous")) {
                console.log("openAddNews.." + this.main.getUser());
                if (!this.addNewsView)
                    this.addNewsView = new AddNewsView({
                        model: new NewsModel(),
                        topics: this.topics,
                        types: this.types
                    });
                this.addNewsView.render();
                this.listenTo(this.addNewsView, 'add-news', this.pushNews);
            }else {
                var loginView = new LoginView({message: "Для добавления новости нужно авторизоваться"});
                loginView.render();
                this.listenTo(loginView, 'logged-in', this.onLogin);
            }
        },

        pushNews: function (news) {
           console.log("news filter view: pushNews:" + JSON.stringify(news));
           this.main.pushNews(news);
        },

        onLogin: function (data) {
            this.main.checkUser(data);
        },

        search: function () {

        }
    });
});
