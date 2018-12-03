/**
 * Created by Evgeniy.Guzeev on 28.03.2018.
 */
define([
    'backbone',
    '../../news/models/TypeModel'
], function(Backbone, TypeModel){
    return Backbone.Collection.extend({
        model: TypeModel,
        url:"/types/"
    });
});