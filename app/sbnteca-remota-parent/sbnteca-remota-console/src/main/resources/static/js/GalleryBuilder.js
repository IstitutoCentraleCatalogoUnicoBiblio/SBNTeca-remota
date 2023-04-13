/**
 * Created by jacopo on 08/09/16.
 */
Glizy.oop.declare("GalleryBuilder", {
    initialize: function() {
        this.templateDefine();
    },

    render: function(data) {
        var html = Glizy.template.render('Gallery.hidden', data.groups);
        html += Glizy.template.render('Gallery.preEntry', {});
        _.each(data.contents, function(item) {
            html += Glizy.template.render('Gallery.'+item.type, item);
        });
        html += Glizy.template.render('Gallery.postEntry', {});
        html += Glizy.template.render('Gallery.prePagination', {});
        html += Glizy.template.render('Gallery.pagination', data.pagination);
        html += Glizy.template.render('Gallery.postPagination', {});
        if (data.buttons && data.buttons.length > 0) {
            html += Glizy.template.render('Gallery.preButtons', {});
            _.each(data.buttons, function (item) {
                html += Glizy.template.render('Gallery.' + item.type, item);
            });
            html += Glizy.template.render('Gallery.postButtons', {});
        }
        return html;
    },

    update : function(data) {
        var html = '';
        _.each(data.contents, function(item) {
            console.log(item.type);
            html += Glizy.template.render('Gallery.'+item.type, item);
        });
        return html;
    },

    updatePagination: function(data) {
       return Glizy.template.render('Gallery.pagination', data.pagination);
    },

    templateDefine: function() {
        Glizy.template.define('Gallery.preEntry',
            '<button id="addmedia" type="button" class="btn btn-primary" style="margin-bottom:25px">Aggiungi Media</button>' +
            '<ul id="gallery" class="thumbnails">'
        );
        Glizy.template.define('Gallery.entry',
            '<% var holdId = ""; %>'+
            '<% if (typeof(holdingsID) != "undefined") { %>'+
            '<% holdId = holdingsID%>'+
            '<%}%>'+
            '<% var scaleField = ""; %>'+
            '<% if (typeof(scale) != "undefined") { %>'+
            '<% scaleField = scale%>'+
            '<%}%>'+
            '<% var sideField = ""; %>'+
            '<% if (typeof(side) != "undefined") { %>'+
            '<% sideField = side%>'+
            '<%}%>'+
            '<% var noteField = ""; %>'+
            '<% if (typeof(note) != "undefined") { %>'+
            '<% noteField = note%>'+
            '<%}%>'+
            '<% var usage_abField = ""; %>'+
            '<% if (typeof(usage_ab) != "undefined") { %>'+
            '<% usage_abField = usage_ab%>'+
            '<%}%>'+
            '<li class="entry-thumb" id="<%= id %>">' +
            '<div class="thumbnail">' +
            //'<div class="cancel-thumbnail"><a class="cancel-button" href="#"><i class="icon-remove-sign"></i></a></div>' +
            //'<a href="<%= src %>" rel="groupImg" class="anchor-thumbnail">' +
            '<img class="galleryImg" src="<%= src %>" alt="testo alternativo" />' +
            //'</a>' +
            '<div class="galleryTitle" title="<%= label %>"><%= label %></div>' +
            '</div>' +
            '<div class="galleryBoxBtn">' +
            '<a href="#" class="edit-metadata gallery-btn btn btn-primary" role="button" data-nomenclature="<%= label %>" data-groups="<%= groups %>" data-seq="<%= seq %>" data-type="<%= media_type %>" data-note="<%= noteField %>" data-usage_ab="<%= usage_abField %>" data-holdingsID="<%= holdId %>" data-scale="<%= scaleField %>" data-side="<%= sideField %>" ><i class="icon icon-pencil"></i></a>' +
            '<a href="#" class="cancel-button gallery-btn btn btn-danger" role="button" ><i class="icon icon-trash"></i></a>' +
            '</div>' +
            '</li>'
        );
        Glizy.template.define('Gallery.hidden',
            '<input id="<%= id %>" name="<%= id %>" title="<%= label %>" class="span11 <%= mandatory %> <%= cssClass %>" data-groups="<%= groups %>" type="hidden" size="50" />'
        );
        Glizy.template.define('Gallery.postEntry',
            '</ul>'
        );
        Glizy.template.define('Gallery.prePagination',
            '<div class="pagination">'
        );
        Glizy.template.define('Gallery.pagination',
            '<ul>' +
            '<li id="prev"><a href="#">Prev</a></li>' +
            '<% var html = ""; %>' +
            '<% var c = 1; %>' +
            '<% for (var i = 0; i < totalPages; i++) { %>' +
            '<% var s = ""; %>' +
            '<% if (selected == (i + 1)) { %>' +
            '<% s = "active"; %>' +
            '<% } %>' +
            '<% html += Glizy.template.render("Gallery.paginationEntry", {"c": c, "s": s}); %>'+
            '<% c++; %>' +
            '<% } %>' +
            '<%= html %>' +
            '<li id="next"><a href="#">Next</a></li>' +
            '</ul>'
        );
        Glizy.template.define('Gallery.postPagination',
            '</div>'
        );
        Glizy.template.define('Gallery.paginationEntry',
            '<% if (s != "") { %>' +
            '<li class="<%= s %>" data-num="<%= c %>"><a class="pagination-entry" href="#"><%= c %></a></li>' +
            '<% } else { %>' +
            '<li data-num="<%= c %>"><a class="pagination-entry" href="#"><%= c %></a></li>' +
            '<% } %>'
        );
        Glizy.template.define('Gallery.preButtons',
            '<div class="formButtons">'+
            '<div class="content">'
        );

        Glizy.template.define('Gallery.postButtons',
            '</div>'+
            '</div>'
        );
        Glizy.template.define('Gallery.back',
            '<input class="btn btn-primary" type="button" value="<%= label %>" data-action="back" />'
        );
        Glizy.template.define('Gallery.save',
            '<input class="btn btn-primary js-glizycms-save" type="button" value="<%= label %>" data-action="save" />'
        );
    }
});