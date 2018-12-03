/**
 * Created by dell on 07.05.2017.
 */
define([
    'backbone',
    '../../news/collections/BetCollection',
    '../../user/models/AccountModel'
], function(Backbone, BetCollection, AccountModel){
    return Backbone.Model.extend({
        defaults: {
            id : undefined,
            userNick: "anonymous",
            password: undefined,
            passwordConfirm: undefined,
            email: undefined,
            avatarUrl: undefined,
            bets: new BetCollection(),
            account: new AccountModel()
        },

        parse: function(response) {
            response.bets = new BetCollection(response.bets);
            response.account = new AccountModel(response.account);
            return response;
        },

        validation: {
            userNick: {
                required: true
            },
            email: {
                pattern: 'email'
            }
        },

        url : "/current/"
    });
});