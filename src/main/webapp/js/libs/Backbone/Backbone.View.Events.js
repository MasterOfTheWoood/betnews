(function (root, factory) {
    if (typeof exports === 'object' && root.require) {
        module.exports = factory(require("backbone"));
    } else if (typeof define === "function" && define.amd) {
        // AMD. Register as an anonymous module.
        define(["backbone"], function(Backbone) {
            // Use global variables if the locals are undefined.
            return factory(Backbone || root.Backbone);
        });
    } else {
        // RequireJS isn't being used. Assume underscore and backbone are loaded in <script> tags
        factory(Backbone);
    }
})(this, function (Backbone) {

    /**
     * Подменяем и расширяем Backbone.View, добавляя в него методы для работы с событиями DOM
     * @class Backbone.EventsView
     * @extends Backbone.View
     */
    Backbone.EventsView = Backbone.View.redefine(/**@lends Backbone.View*/{
        /**
         * @returns {Object}
         */
        events: function () {
            return {};
        },

        /**
         * @param {Event} e
         * @returns {boolean}
         * @protected
         */
        _containsEventTarget: function (e) {
            return this.$el[0] === e.target || !!this.$el.has(e.target).length;
        },

        /**
         * @param {Event} e
         * @returns {Boolean}
         * @private
         */
        _isMetaKey: function (e) {
            return e.ctrlKey || e.altKey || e.metaKey || e.which < 32
        },

        /**
         * Keys' codes
         * @type {object}
         */
        _KEYS: {
            TAB: 9,
            ENTER: 13,
            SHIFT: 16,
            ESC: 27,
            SPACE: 32,
            LEFT: 37,
            UP: 38,
            RIGHT: 39,
            DOWN: 40,
            BACKSPACE: 8,
            DELETE: 46
        }

    });

    return Backbone.EventsView;

});