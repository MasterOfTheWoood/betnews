/**
 * Created by Evgeniy.Guzeev on 28.03.2018.
 */
define([
    'backbone',
    '../../news/models/TopicModel'
], function(Backbone, TopicModel){
    return Backbone.Collection.extend({
        model: TopicModel,
        url:"/topics/"
    });
});