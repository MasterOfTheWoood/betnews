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
     * Заменяет свойства класса при определенных условиях.
     * @param {Function|Boolean} [condition=true] Условие, при выполнении которого произойдет замена. Если
     * передана функция, будет проверен ее результат.
     * @param {Object|Function} replacement Объект свойства которого будут переопределять свойства прототипа функции.
     * Если передана функция, будет взят ее результат, единственным аргументом в эту функцию будет передан оригинальный
     * прототип
     * @return {Function} this
     * @example Простое использование
     * <code class="javascript">
     * var Block = Backbone.View.extend({
     *     initialize: function(){
     *         this.initHandlers();
     *     },
     *
     *     initHandlers: function(){
     *         console.log('init handlers');
     *         this.$el.on('click', some_function);
     *     }
     * });
     *
     * var MobileBlock = Block.redefine({
     *     initHandlers: function(){
     *         console.log('init handlers for mobile devices');
     *         this.$el.on('touchstart', some_function);
     *     }
     * });
     *
     * MobileBlock === Block; // true, MobileBlock - алиас для документации
     *
     * var instance = new Block;  // залогируется 'init handlers for mobile devices'
     * </code>
     *
     * @example Использование оригинальных методов внутри переопердеелнных
     * <code class="javascript">
     * var Origin = Backbone.View.extend({
     *     method: function(){
     *         console.log('origin method');
     *     }
     * });
     *
     * Origin.redefine(function(origin){return {
     *     method: function(){
     *         origin.method.call(this);
     *         console.log('redefined method');
     *     }
     * }});
     *
     * var instance = new Origin;
     * instance.method();         // залогируется 'origin method' и 'redefined method'
     * </code>
     *
     * @example Условное смешивание
     * <code class="javascript">
     * var Block = Backbone.View.extend({
     *     initialize: function(){
     *         this.initHandlers();
     *     },
     *
     *     initHandlers: function(){
     *         console.log('init handlers');
     *         this.$el.on('click', some_function);
     *     }
     * });
     *
     * var MOBILE_DEVICE = true;
     * var MobileBlock = Block.redefine(MOBILE_DEVICE, {
     *     initHandlers: function(){
     *         console.log('init handlers for mobile devices');
     *         this.$el.on('touchstart', some_function);
     *     }
     * });
     * </code>
     */
    var redefine = function(condition, replacement){
        if (!replacement) {
            replacement = condition;
            condition = true;
        }

        if (typeof condition == 'function') {
            condition = condition();
        }

        if (!condition) {
            return this;
        }

        if (typeof replacement == 'function') {
            var origin = {};
            replacement = replacement(origin);
            for (var prop in replacement) {
                if (replacement.hasOwnProperty(prop)) {
                    origin[prop] = this.prototype[prop];
                }
            }
        }

        _.extend(this.prototype, replacement);
        return this;
    };

    Backbone.Model.redefine = Backbone.Collection.redefine = Backbone.Router.redefine = Backbone.View.redefine = Backbone.History.redefine = redefine;
});