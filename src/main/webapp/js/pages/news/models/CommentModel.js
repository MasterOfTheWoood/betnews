/**
 * Created by dell on 07.05.2017.
 */
define([
    'backbone',
    '../../news/collections/CommentCollection',
    '../../user/models/UserModel'
], function(Backbone, CommentCollection, UserModel){
    return Backbone.Model.extend({
        defaults: {
            id: undefined,
            description: undefined,
            date: undefined,
            user: new UserModel(),
            likes: [],
            dislikes: [],
            replays: []
        },

        parse: function(response) {
            response.replays = new CommentCollection(response.replays);
            response.user = new UserModel(response.user);
            return response;
        }
    });
});