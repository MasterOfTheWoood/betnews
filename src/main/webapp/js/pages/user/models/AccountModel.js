/**
 * Created by dell on 29.04.2018.
 */
define([
    'backbone'
], function(Backbone){
    return Backbone.Model.extend({
        defaults: {
            id: undefined,
            balance: 0
        }
    });
});