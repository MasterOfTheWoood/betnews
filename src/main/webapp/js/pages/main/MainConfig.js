/**
 * Created by dell on 07.05.2017.
 */
require.config({
    baseUrl: '/js/pages/main/',
    waitSeconds: 0,
    paths:{
        jquery: '/js/libs/Jquery/Jquery',
        jquery_ui: '/js/libs/jquery-ui/jquery-ui',
        backbone: '/js/libs/Backbone/Backbone',
      ///  marionette: '/js/libs/Backbone/Backbone.Marionette',
    //    radio: '/js/libs/Backbone/Backbone.Radio',
        underscore: '/js/libs/Underscore',
        bootstrap:'/js/libs/Bootstrap/bootstrap',
        bootstrap_fa:'/js/libs/Bootstrap/all.min',
        carousel: '/js/libs/Bootstrap/carousel',
        util: '/js/libs/Bootstrap/util',
        select2:'/js/libs/select2.full.min',
        text: '/js/libs/Text',
        ckeditor: '/js/libs/ckeditor/ckeditor'
    },

    shim: {
        underscore: {
            exports: "_"
        },
        jquery_ui :{
            "deps" :['jquery']
        },
        bootstrap : { "deps" : ['jquery'] },
        bootstrap_fa : { "deps" : ['jquery'] },
        select2: { "deps" :['jquery'] },
    //    radio: {"deps" : ['backbone', 'underscore']},
    //    marionette: {"deps" : ['radio', 'jquery', 'underscore', 'backbone']},
        ckeditor: {
            deps: ['jquery'],
            exports: 'CKEDITOR'
        }
    }
});

require([
    'jquery',
    './MainRouter',
    'ckeditor'
],function($, MainRouter,ckeditor){
    $.fn.serializeObject = function(){

        var self = this,
            json = {},
            push_counters = {},
            patterns = {
                "validate": /^[a-zA-Z][a-zA-Z0-9_]*(?:\[(?:\d*|[a-zA-Z0-9_]+)\])*$/,
                "key":      /[a-zA-Z0-9_]+|(?=\[\])/g,
                "push":     /^$/,
                "fixed":    /^\d+$/,
                "named":    /^[a-zA-Z0-9_]+$/
            };


        this.build = function(base, key, value){
            base[key] = value;
            return base;
        };

        this.push_counter = function(key){
            if(push_counters[key] === undefined){
                push_counters[key] = 0;
            }
            return push_counters[key]++;
        };

        $.each($(this).serializeArray(), function(){

            // skip invalid keys
            if(!patterns.validate.test(this.name)){
                return;
            }

            var k,
                keys = this.name.match(patterns.key),
                merge = this.value,
                reverse_key = this.name;

            while((k = keys.pop()) !== undefined){

                // adjust reverse_key
                reverse_key = reverse_key.replace(new RegExp("\\[" + k + "\\]$"), '');

                // push
                if(k.match(patterns.push)){
                    merge = self.build([], self.push_counter(reverse_key), merge);
                }

                // fixed
                else if(k.match(patterns.fixed)){
                    merge = self.build([], k, merge);
                }

                // named
                else if(k.match(patterns.named)){
                    merge = self.build({}, k, merge);
                }
            }

            json = $.extend(true, json, merge);
        });

        return json;
    };
    MainRouter.startHistory();
});