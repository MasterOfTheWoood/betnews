/**
 * Created by dell on 23.06.2018.
 */
define([
    'backbone',
    '../../user/models/DialogModel'
], function(Backbone, DialogModel){
    return Backbone.Collection.extend({
        url: "/member/getDialogs",
        model: DialogModel
    });
});