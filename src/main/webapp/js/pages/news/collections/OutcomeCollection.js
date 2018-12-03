/**
 * Created by dell on 07.05.2017.
 */
define([
    'backbone',
    '../../news/models/OutcomeModel'
], function(Backbone, OutcomeModel){
    return Backbone.Collection.extend({
        model: OutcomeModel,

        getById: function (id) {
            var result = undefined;
            console.log("outcomes:: get by id " + id);
            this.models.forEach(function (outcome) {
                console.log("outcomes::  " + JSON.stringify(outcome));
                if(outcome.id == id) {
                    console.log("i found it!");
                    result = outcome;
                }
            });
            return result;
        }
    });
});