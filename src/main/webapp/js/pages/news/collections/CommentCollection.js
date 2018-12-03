/**
 * Created by dell on 07.05.2017.
 */
define([
    'backbone',
    '../../news/models/CommentModel'
], function(Backbone, CommentModel){
    return Backbone.Collection.extend({
        model: CommentModel
    });
});