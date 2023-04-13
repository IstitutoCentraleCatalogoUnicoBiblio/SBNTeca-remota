Glizy.oop.declare("FormBuilder", {
    config: null,
    project: null,
    dataForm:null,
    originalData:{
        get: function () { return this.originalData; },
        set: function (data) { this.originalData=data;}
    },

    initialize: function (config, project) {
        this.config = config;
        this.project = project;
        this.templateDefine();
        this.attachEvents();
    },

    attachEvents: function() {
        var _this=this;
    },

    setProject: function (project) {
        this.project = project;
        this.templateDefine();
    },

    getProject: function () {
        return this.project
    },

    serializeData:function(data){
        var _this=this;
        data = data || _this.dataForm;
        var serData = {};
        _.forEach(data.attributes,function(value,key){
            var identifier = value.type && value.type === "mediapickercustom" ? "file" : value.id;
            var filedId = value.type && value.type === "mediapickercustom" ? "name" : "id";
            serData[identifier]=$("["+filedId+"='"+identifier+"'").val();
        });
        _.forEach(data.contents,function(value,key){
            var identifier = value.type && value.type === "mediapickercustom" ? "file" : value.id;
            var filedId = value.type && value.type === "mediapickercustom" ? "name" : "id";
            serData[identifier]=$("["+filedId+"='"+identifier+"'").val();
        });
        return serData;
    },

    getSchemaFromField: function(ar,field){
        var object;
        _.forEach(ar,function(value){
            if(value.id===field){
                object=value.data;
            }
        });
        return object;
    },

    render: function(data) {
        var _this=this;
        var html = '';
        data.config = this.config;
        _.each(data.attributes, function(item) {
            if(item.etichette){
                try{
                    item.etichette_schema = JSON.stringify(_this.getSchemaFromField(item.children,item.etichette)).replace(/ /g, '_');
                }
                catch(err){}
            }
            html += Glizy.template.render('FormBuilder.' + item.type, item);
        });
        _.each(data.contents, function(item) {
            if(item.etichette){
                try{
                    item.etichette_schema = JSON.stringify(_this.getSchemaFromField(item.children,item.etichette)).replace(/ /g, '_');
                }
                catch(err){}
            }
            html += Glizy.template.render('FormBuilder.' + item.type, item);
        });
        if (data.buttons.length > 0) {
            html += Glizy.template.render('FormBuilder.preButtons', {});
            _.each(data.buttons, function (item) {
                html += Glizy.template.render('FormBuilder.' + item.type, item);
            });
            html += Glizy.template.render('FormBuilder.postButtons', {});
        }
        _this.dataForm=data;
        return html;
    },

    templateDefine: function() {
        Glizy.template.define('FormBuilder.text',
            '<div class="control-group">'+
            '<label for="pageEdit-<%= id %>" class="control-label <%= mandatory %>"><%= label %></label>'+
            '<div class="controls">'+
            '<% var ro = ""; %>'+
            '<% if (typeof(readonly) != "undefined") { %>'+
            '<% ro = readonly; %>'+
            '<% } %>'+
            '<input id="<%= id %>" name="<%= id %>" title="<%= label %>" class="span11 <%= mandatory %> <%= cssClass %>" type="text" size="50" '+
            '<% if (pattern && pattern != "") { %>'+
            'pattern="'+ '<%= pattern %>'+ '" '+
            '<% } %>'+
            '<%= ro %>'+
            ' />'+
            '</div>'+
            '</div>'
        );

        Glizy.template.define('FormBuilder.hidden',
            '<input id="<%= id %>" name="<%= id %>" title="<%= label %>" class="span11 <%= mandatory %> <%= cssClass %>" type="hidden" size="50" />'
        );

        Glizy.template.define('FormBuilder.date',
            '<div class="control-group">'+
            '<label for="pageEdit-<%= id %>" class="control-label <%= mandatory %>"><%= label %></label>'+
            '<div class="controls">'+
            '<input id="<%= id %>" name="<%= id %>" title="<%= label %>" class="span11 <%= mandatory %> <%= cssClass %>" size="50" data-type="date" />'+
            '</div>'+
            '</div>'
        );

        Glizy.template.define('FormBuilder.datetime',
            '<div class="control-group">'+
            '<label for="pageEdit-<%= id %>" class="control-label <%= mandatory %>"><%= label %></label>'+
            '<div class="controls">'+
            '<input id="<%= id %>" name="<%= id %>" title="<%= label %>" class="span11 <%= mandatory %> <%= cssClass %>" size="50" data-type="datetime" data-format="<%= format %>"/>'+
            '</div>'+
            '</div>'
        );

        Glizy.template.define('FormBuilder.select',
            '<div class="control-group">'+
            '<label for="pageEdit-<%= id %>" class="control-label <%= mandatory %>"><%= label %></label>'+
            '<div class="controls">'+
            '<select id="<%= id %>" name="<%= id %>" title="<%= label %>" class="span11 <%= mandatory %> <%= cssClass %>">'+
            '<% _.each(data, function(option) { %>'+
            '<option value="<%= option.value %>"><%= option.value + " " + option.description %></option>'+
            '<% }); %>'+
            '<select>'+
            '</div>'+
            '</div>'
        );

        Glizy.template.define('FormBuilder.object',
            '<% var maxR = ""; %>'+
            '<% if (typeof(repeatmax) != "undefined") { %>'+
            '<% maxR = "data-repeatmax=" + repeatmax + " "%>'+
            '<% } %>'+
            '<% if (repeatable) { %>'+
            '<% var etich = ""; %>'+
            '<% if (typeof(etichette) != "undefined") { %>'+
            '<% etich = "data-etichette=" + etichette + " " %>'+
            '<% } %>'+
            '<% var etich_schema = ""; %>'+
            '<% if (typeof(etichette_schema) != "undefined") { %>'+
            '<% etich_schema = "data-etichette_schema=" + etichette_schema + " " %>'+
            '<% } %>'+
            '<fieldset id="<%= id %>" data-type="repeat" data-collapsable="<%= collapsable %>" ' + '<%= maxR %>' + '<%= etich %>' + '<%= etich_schema %>' + '>' +
            '<% } else { %>'+
            '<fieldset id="<%= id %>" ' + '<%= maxR %>' + '>' +
            '<% } %>'+
            '<legend><%= label %></legend>'+
            '<% var html = ""; %>'+
            '<% _.each(children, function(child) { %>'+
            '<% html += Glizy.template.render("FormBuilder."+child.type, child); %>'+
            '<% }); %>'+
            '<% _.each(attributes, function(attribute) { %>'+
            '<% html += Glizy.template.render("FormBuilder."+attribute.type, attribute); %>' +
            '<% }); %>'+
            '<%= html %>' +
            '</fieldset>'
        );

        Glizy.template.define('FormBuilder.tinymce',
            '<div class="control-group">'+
            '<label for="pageEdit-<%= id %>" class="control-label"><%= label %></label>'+
            '<div class="controls">'+
            '<textarea  id="<%= id %>" name="<%= id %>" title="<%= label %>" class="span11 <%= cssClass %>" cols="75" rows="20" wrap="off" data-type="tinymce"></textarea>'+
            '</div>'+
            '</div>'
        );

        Glizy.template.define('FormBuilder.mediapickercustom',
            '<div class="control-group">'+
            '<label for="pageEdit-<%= id %>" class="control-label"><%= label %></label>'+
            '<div class="controls">'+
            '<input id="<%= id %>" name="<%= id %>" title="<%= label %>" class="span11 <%= cssClass %>" type="text" value="" data-src="" data-thumbnail="" data-populate_data="true" data-mediatype="<%= mediatype %>" data-type="mediapickercustom" data-project="'+this.getProject()+'" data-preview="true" />'+
            '</div>'+
            '</div>'
        );

        Glizy.template.define('FormBuilder.preButtons',
            '<div class="formButtons">'+
            '<div class="content">'
        );

        Glizy.template.define('FormBuilder.postButtons',
            '</div>'+
            '</div>'
        );

        Glizy.template.define('FormBuilder.back',
            '<input class="btn btn-primary" type="button" value="<%= label %>" data-action="back" />'
        );

        Glizy.template.define('FormBuilder.save',
            '<input class="btn btn-primary js-glizycms-save" type="button" value="<%= label %>" data-action="save" />'
        );

        Glizy.template.define('FormBuilder.section',
            '<fieldset id="<%= id %>" data-type="<%= id %>" style="display: none"></fieldset>'
        );

        Glizy.template.define('FormBuilder.selectfromsimple',
            '<div class="control-group">'+
            '<label for="pageEdit-<%= id %>" class="control-label <%= mandatory %>"><%= label %></label>'+
            '<div class="controls">'+
            '<% var ro = ""; %>'+
            '<% if (typeof(readonly) != "undefined") { %>'+
            '<% ro = readonly; %>'+
            '<% } %>'+
            '<input data-type="selectfromsimple" data-proxy="<%= config.restAutoCompleteField %>" id="<%= id %>" name="<%= id %>" title="<%= label %>" class="span11 <%= mandatory %> <%= cssClass %>" type="text" size="50" '+ '<%= ro %>'+ '/>'+
            '</div>'+
            '</div>'
        );

        Glizy.template.define('FormBuilder.selectfromsimpletextarea',
            '<div class="control-group">'+
            '<label for="pageEdit-<%= id %>" class="control-label <%= mandatory %>"><%= label %></label>'+
            '<div class="controls">'+
            '<% var ro = ""; %>'+
            '<% if (typeof(readonly) != "undefined") { %>'+
            '<% ro = readonly; %>'+
            '<% } %>'+
            '<% var nRows = ""; %>'+
            '<% if (typeof(rows) != "undefined") { %>'+
            '<% nRows = rows; %>'+
            '<% } %>'+
            '<textarea rows="<%= nRows %>" data-type="selectfromsimple" data-proxy="<%= config.restAutoCompleteField %>" id="<%= id %>" name="<%= id %>" title="<%= label %>" class="span11 <%= mandatory %> <%= cssClass %>" type="text" size="50" '+ '<%= ro %>'+ '/>'+
            '</div>'+
            '</div>'
        );
    }
});