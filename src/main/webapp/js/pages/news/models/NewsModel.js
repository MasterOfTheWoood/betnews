/**
 * Created by dell on 07.05.2017.
 */
define([
    'backbone',
    '../../news/collections/CommentCollection',
    '../../news/collections/OutcomeCollection',
    '../../user/collections/UserCollection',
    '../../user/models/UserModel'
], function(Backbone, CommentCollection, OutcomeCollection, UserCollection, UserModel){
    return Backbone.Model.extend({
        defaults: {
            id: undefined,
            title: undefined,
            description: undefined,
            newsTopic: undefined,
            newsType: undefined,
            rating: undefined,
            createDate: undefined,
            illustration: undefined,
            author: new UserModel(),
            arbiter: new UserModel(),
            comments: new CommentCollection(),
            outcomes: new OutcomeCollection(),
            canVoteUsers: new UserCollection(),
            likes: [],
            dislikes: []
        },

        initialize: function (news) {
            if(news) {
                this.outcomes = new OutcomeCollection(news.outcomes);
                this.comments = new CommentCollection(news.comments);
                this.canVoteUsers = new UserCollection(news.canVoteUsers);
            }
        },

        parse: function(response) {
            response.outcomes = new OutcomeCollection(response.outcomes);
            response.comments = new CommentCollection(response.comments);
            response.canVoteUsers = new UserCollection(response.canVoteUsers);
            return response;
        }
    });
});