/**
 * Created by dell on 07.05.2017.
 */
define([
    'backbone'
], function(Backbone){
    return Backbone.Model.extend({
        defaults: {
            id: undefined,
            betValue: undefined,
            userId: undefined
        }
    });
});