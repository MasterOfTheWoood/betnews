/**
 * Created by Evgeniy.Guzeev on 28.03.2018.
 */
define([
    'backbone'
], function(Backbone){
    return Backbone.Model.extend({
        defaults: {
            key: undefined,
            name: undefined
        }
    });
});