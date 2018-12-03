/**
 * Created by dell on 07.05.2017.
 */
define([
    'backbone',
    '../../news/models/BetModel'
], function(Backbone, BetModel){
    return Backbone.Collection.extend({
        model: BetModel
    });
});