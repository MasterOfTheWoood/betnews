/**
 * Created by Evgeniy.Guzeev on 25.01.2018.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    '../../user/views/InfoView',
    'text!../../top/templates/InfoMenuItemTemplate.html'
], function($, _, backbone, InfoView, InfoMenuItemTemplate){
    return backbone.View.extend({

        el: '#info-menu-container',
        template: _.template(InfoMenuItemTemplate),
        infoView: undefined,

        events:{
            "click #info-menu-item" : "openInfo"
        },

        render : function(){
            this.$el.html(this.template);
        },

        openInfo: function () {
            if(!this.infoView)
                this.infoView = new InfoView();
            this.infoView.render();
        }
    });
});
