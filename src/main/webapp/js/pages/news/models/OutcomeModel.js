/**
 * Created by dell on 07.05.2017.
 */
define([
    'backbone',
    '../../news/collections/BetCollection'
], function(Backbone, BetCollection){
    return Backbone.Model.extend({
        defaults: {
            id: undefined,
            number: undefined,
            description: undefined,
            bets: new BetCollection()
        },

        parse: function(response) {
            response.bets = new BetCollection(response.bets);
            return response;
        }
    });
});