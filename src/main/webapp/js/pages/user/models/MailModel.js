/**
 * Created by Evgeniy.Guzeev on 09.02.2018.
 */
define([
    'backbone',
    '../../user/models/UserModel'
], function(Backbone, UserModel){
    return Backbone.Model.extend({
        defaults: {
            id: undefined,
            message: "",
            userTo:  new UserModel(),
            userFrom: new UserModel(),
            date: undefined
        },
        initialize: function (mail) {
            this.userTo = new UserModel(mail.userTo);
            this.userFrom = new UserModel(mail.userFrom);
        },

        parse: function(response) {
            response.userTo = new UserModel(response.userTo);
            response.userFrom = new UserModel(response.userFrom);
            return response;
        }
    });
});