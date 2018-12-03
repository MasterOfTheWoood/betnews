/**
 * Created by Evgeniy.Guzeev on 14.05.2018.
 */
define([
    'backbone',
    '../../user/models/UserModel'
], function(Backbone, UserModel){
    return Backbone.Collection.extend({
        model: UserModel
    });
});