/**
 * Created by Evgeniy.Guzeev on 09.02.2018.
 */
define([
    'backbone',
    '../../user/models/MailModel'
], function(Backbone, MailModel){
    return Backbone.Collection.extend({
        model: MailModel
    });
});