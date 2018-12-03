/**
 * Created by dell on 07.05.2017.
 */
define([
    'backbone',
    '../../news/models/NewsModel'
], function(Backbone, NewsModel){
    return Backbone.Collection.extend({
        model: NewsModel,
        url:"/news/"
    });
});