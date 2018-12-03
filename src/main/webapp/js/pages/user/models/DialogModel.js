/**
 * Created by dell on 23.06.2018.
 */
define([
    'backbone',
    '../../user/models/UserModel',
    '../../user/models/MailModel'
], function(Backbone, UserModel, MailModel){
    return Backbone.Model.extend({
        defaults: {
            user: undefined,
            mail: undefined
        },
        initialize: function (dialog) {
            this.user = new UserModel(dialog.user);
            this.mail = new MailModel(dialog.mail);
        },

        parse: function(response) {
            response.user = new UserModel(response.user);
            response.mail = new MailModel(response.mail);
            return response;
        }
    });
});