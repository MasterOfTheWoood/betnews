/**
 * Created by dell on 08.05.2017.
 */
define([
    'jquery',
    'underscore',
    'backbone',
    'marionette',
    '../../news/views/CommentView',
    'text!../../news/templates/CommentTemplate.html'
], function($, _, backbone, marionette, CommentView, CommentTemplate){
    return backbone.Marionette.CollectionView.extend({

        template: _.template(CommentTemplate),
        tagName: "li",
        events:{

        },

        initialize: function (options) {
            this.newsId = options.newsId;
        },

        render : function(){
            console.log("comment: " + JSON.stringify(this.model));
            var self = this;
            if(this.model.get("replays")){
                this.model.get("replays").forEach(function (reComment) {
                    var commentView = new CommentView({model: reComment, newsId: this.newsId});
                    self.$el.find("#news-comments-container-" + this.newsId).append(commentView.render());
                })
            }
            return this.template(this.model.attributes);
        }
    });
});
