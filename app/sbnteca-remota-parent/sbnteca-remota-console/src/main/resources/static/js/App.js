Glizy.oop.declare("App", {
    self: this,
    form: null,
    formId: null,
    glizyOpt: null,
    jsTree: null,
    formBuilder: null,
    treeBuilder: null,
    galleryBuilder: null,
    magService: null,
    config: null,
    tree_schema: null,
    section_gen_schema: null,
    section_bib_schema: null,
    section_stru_schema: null,
    section_img_schema: null,
    section_audio_schema: null,
    section_video_schema: null,
    section_doc_schema: null,
    section_ocr_schema: null,
    section_dis_schema: null,
    element_node: null,
    stru_node: null,
    stru_json: null,
    stru_node_selected: null,
    sectionId: null,
    ajaxContext: null,
    createFormContext: null,
    createGalleryContext: null,
    attachFormContext: null,
    setGalleryEventsContext: null,
    bibEventsContext: null,
    medias: null,
    totalPages: null,
    currentPage: null,
    project: null,
    magId: null,
    sectionJson:null,
    mediaActive:null,
    mediaActiveChanged:false,
    mediasDeleted:[],
    sectionModify:false,
    getStruFromServer:false,

    initialize: function(formId, config, glizyOpt ) {
        self.formId = formId;
        self.form = $('#'+formId);
        //self.project = 'testdiprova';
        //self.magId = 'draft_20161118092441986';
        self.project = '';
        self.magId = this.getParameterByName('id');
        self.draft = "true"===this.getParameterByName('draft');
        self.config = config;
        self.formBuilder = Glizy.oop.create('FormBuilder', config, self.project);
        self.treeBuilder = Glizy.oop.create('TreeBuilder', config, self.project, self.magId);
        self.galleryBuilder = Glizy.oop.create('GalleryBuilder');
        this.magService = Glizy.oop.create('MagService', config);

        self.glizyOpt = glizyOpt;

        self.tree_schema = tree_struct;
        self.section_gen_schema = section_gen;
        self.section_bib_schema = section_bib;
        self.section_stru_schema = section_stru;
        self.section_img_schema = section_img;
        self.section_audio_schema = section_audio;
        self.section_video_schema = section_video;
        self.section_doc_schema = section_doc;
        self.section_ocr_schema = section_ocr;
        self.section_dis_schema = section_dis;
        self.element_node = element_node;
        self.stru_node = stru_node;
        self.sectionId = 'gen';
        self.ajaxContext = $.proxy(this.ajaxCaller, this);
        self.saveContext = $.proxy(this.onSaveData, this);
        self.createFormContext = $.proxy(this.createFormFromJson, this);
        self.createGalleryContext = $.proxy(this.createGalleryFromJson, this);
        self.updateGalleryContext = $.proxy(this.updateGalleryFromJson, this);
        self.attachFormContext = $.proxy(this.attachFormEdit, this);
        self.setGalleryEventsContext = $.proxy(this.setGalleryEvents, this);
        self.createDropdownContext = $.proxy(this.createDropdownMenu, this);
        self.totalPages = 0;
        self.currentPage = 1;
        self.mappingTreeObj = this.mappingTreeObj;
        self.createStruObjTreeLevelFromJson = this.createStruObjTreeLevelFromJson;
        self.goToSection = this.goToSection;
        self.checkSectionModify = this.checkSectionModify;
        self.sectionModify = this.sectionModify;
        self.mediasDeleted = this.mediasDeleted;
        self.getStruFromServer = this.getStruFromServer
        // questo valore deve essere ottenuto dal servizio, al momento cablato

        var treeview_dropdown = $('#treeview-dropdown');
        treeview_dropdown.html(this.createDropdownMenu());
        treeview_dropdown.hide();

        // Invoco la stru e genero il mag-tree
        // var ajaxStru = {
        //     "url": self.config['restServerUrlContent'],
        //     "magId": self.magId,
        //     "section": "stru"
        // };
        // this.ajaxCaller(ajaxStru);

        self.getStruFromServer();

        // Collego eventi
        this.attachEvents();
        this.setGalleryEvents();

        // Invoco la gen, al caricamento della pagina, e la renderizzo subito
        var ajaxData = {
            "url": self.config['restServerUrlContent'],
            "magId": self.magId,
            "section": self.sectionId
        };
        this.ajaxCaller(ajaxData);
    },

    getStruFromServer: function(clb,clbParams){
        // Invoco la stru e genero il mag-tree
        var ajaxStru = {
            "url": self.config['restServerUrlContent'],
            "magId": self.magId,
            "section": "stru"
        };
        this.ajaxContext(ajaxStru,clb,clbParams);
    },

    attachEvents: function() {
        var _this=this;
        // Glizy events
        Glizy.events.on("app.saveData", self.saveContext);
        Glizy.events.on("app.MagTreeCreated", this.setTreeEvents);
        Glizy.events.on('mediapickerTree.selectMedia', this.mediaPickerTreeSelectedMedia, _this);
        Glizy.events.on("app.loadSection", _this.onLoadSection, _this);
    },

    onLoadSection: function(e){
        var _this = this;
        if(e && e.message){
            var data = e.message;
            var section = data.section;
            var schema = _this.getSchemaJson(section);
            _this.createFormFromJson(schema);
            _this.attachFormEdit(data[section]);
        }
    }, 

    mediaPickerTreeSelectedMedia: function(msg){
        var _this = this;
        var mediapickerTree = $('#'+msg.message.sender.value).parent(".controls").find("#mediapicker-tree");
        mediapickerTree.dialog( "close" );
        switch (self.sectionId) {
            case 'img':
            case 'audio':
            case 'video':
            case 'doc':
            case 'dis':
                var data = {
                    url:self.config['restMediaMetadata'],
                    rel:msg.message.value.src
                };
                _this.magService.mediaSimpleMetadataCaller(data, function (json) {
                    _this.setMediaPickerForm(msg.message.sender,self.sectionId,json);
                });
                self.mediaActiveChanged=true;
                break;
            case 'ocr':
                if(msg.message.value.type==="img")
                    return false;
                var data = {
                    url:self.config['restMediaMetadata'],
                    rel:msg.message.value.src
                };
                _this.magService.mediaSimpleMetadataCaller(data, function (json) {
                    _this.setMediaPickerForm(msg.message.sender,self.sectionId,json);
                });
                self.mediaActiveChanged=true;
                break;
            default:
                return false;
        }
    },

    setMediaPickerForm: function(sender,type,obj){
        var box = $("["+sender.attr+"="+sender.value+"]").closest(".GFERowContainer");
        box.find("[name='filesize']").val(obj.filesize);
        box.find("[name='format_mime']").val(obj.format_mime);
        box.find("[name='format_name']").val(obj.format_name);
        box.find("[name='md5']").val(obj.md5);
        box.find("[name='datetimecreated']").val(obj.datetimecreated);
        if(type==="img"){
            box.find("[name='imagewidth']").val(obj.imagewidth);
            box.find("[name='imagelength']").val(obj.imagelength);
        }
        if(type==="audio" || type==="video"){
            box.find("[name='duration']").val(obj.duration);
        }
    },

    setTreeEvents: function() {
        var _this=this;
        var dropdown = $('#treeview-dropdown');
        var tree = $('#mag-tree');
        var ajaxData = { "url": self.config['restServerUrlContent'], "magId": self.magId };
        tree.on('activate_node.jstree', (function (e, data) {
            var checkModify = self.checkSectionModify(self.sectionId,data.node.id);
            if(checkModify){
                var dialog = '<div id="dialog-check-modify" title="Modifiche non salvate">'
                            +'<p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>Attenzione, ci sono modifiche non salvate. Sei sicuro di voler cambiare sezione?</p>'
                            +'</div>';
                $("body").append(dialog);
                $( "#dialog-check-modify" ).dialog({
                    resizable: false,
                    height: "auto",
                    width: 400,
                    modal: true,
                    closeOnEscape: false,
                    open: function(event, ui) { $(".ui-dialog-titlebar-close", ui.dialog).hide(); },
                    buttons: {
                        "Cambia sezione": function() {
                            $( "#dialog-check-modify" ).dialog( "destroy" );
                            $( "#dialog-check-modify" ).remove();
                            var oldSection = self.sectionId;
                            self.sectionId = data.node.id;
                            self.mediasDeleted = [];
                            self.sectionModify=false;
                            if(oldSection.indexOf("stru")!==-1){
                                var clb = self.goToSection;
                                var clbPrms = [dropdown,ajaxData,data.node,self.sectionId,"gen"];
                                self.getStruFromServer(clb,clbPrms);
                            }
                            else{
                                self.goToSection(dropdown,ajaxData,data.node);
                            }
                        },
                        "Annulla": function() {
                            tree.jstree(true).select_node(self.sectionId);
                            tree.jstree(true).deselect_node(data.node.id);
                            $( "#dialog-check-modify" ).dialog( "destroy" );
                            $( "#dialog-check-modify" ).remove();
                        },
                        "Salva": function() {
                            tree.jstree(true).select_node(self.sectionId);
                            tree.jstree(true).deselect_node(data.node.id);
                            $( ".js-glizycms-save" ).trigger( "click" );
                            $( "#dialog-check-modify" ).dialog( "destroy" );
                            $( "#dialog-check-modify" ).remove();
                        }
                    }
                });
                return;
            }
            self.sectionId = data.node.id;
            self.goToSection(dropdown,ajaxData,data.node);
        }));
    },

    checkSectionModify:function(sectionId,newSectionId){
        if(sectionId==="stru" && newSectionId.indexOf("stru")!==-1)
            return false;
        switch (sectionId) {
            case 'gen':
            case 'bib':
            case 'dis':
                var changed = !_.isMatch(self.formBuilder.originalData.get(),self.formBuilder.serializeData());
                return changed;
                break;
            case 'img':
            case 'audio':
            case 'video':
            case 'doc':
            case 'ocr':
                return self.sectionModify;
                break;
            case 'stru':
                return self.sectionModify;
                break;
            default:
                if(newSectionId.indexOf("stru")!==-1)
                    return false;
                else
                    return self.sectionModify;
                break;
        }
    },

    goToSection:function(dropdown,ajaxData,node,select,deselect){
        switch (self.sectionId) {
            case 'gen':
            case 'bib':
            case 'img':
            case 'audio':
            case 'video':
            case 'doc':
            case 'ocr':
            case 'dis':
                dropdown.hide();
                ajaxData['section'] = self.sectionId;
                ajaxData['placeholder'] = self.config['section_meta'][self.sectionId]['placeholder'];
                self.ajaxContext(ajaxData);
                break;
            case 'stru':
                dropdown.show();
                $("#add-elem").hide();
                self.createFormContext(section_stru);
                self.attachFormContext([]);
                break;
            default:
                $("#add-elem").show();
                if (node.data.type == 'stru') {
                    $('#treeview-dropdown').show();
                    self.createFormContext(self.stru_node);
                } else if (node.data.type == 'element') {
                    $('#treeview-dropdown').hide();
                    self.createFormContext(self.element_node);
                }
                self.attachFormContext(node.data.treedata);
                break;
        }
        $("#message-box").html("");
        var tree = $("#mag-tree");
        if(select){
            tree.jstree(true).select_node(select);
        }
        if(deselect){
            tree.jstree(true).deselect_node(deselect);
        }
    },

    renderSectionWarning:function(warning){
        var messageBox = '<div class="alert alert-warning alert-dismissible" role="alert">'+
                        '<button id="message-box-close-btn" type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'+
                        '<div id="message-box-content"></div>'+
                        '</div>';
        $("#message-box").html(messageBox);
        $("#message-box-close-btn").on("click",function(){
            localStorage.setItem("showMessageGallery","false");
        });
        _.forEach(warning,function(value){
            var message = "<p>"+value+"</p>";
            $("#message-box-content").append(message);
        });
    },

    initGallery: function(json, type) {
        var arType = ["img","audio","video","doc","ocr"];
        if(arType.indexOf(type)===-1){
            window.location.reload();
            return false;
        }
        var _this=this;
        var groups;
        switch (type) {
            case 'img':
                groups = json['img_groups'];
                break;
            case 'audio':
                groups = json['audio_groups'];
                break;
            case 'video':
                groups = json['video_groups'];
                break;
            default:
                groups = [];
                break;
        }
        self.sectionJson=json;
        if(!self.sectionJson[self.sectionId])
            self.sectionJson[self.sectionId]=[];
        var warning = [];
        if(localStorage.getItem("showMessageGallery")!=="false"){
            warning.push("Ricorda che la modifica del numero di sequenza dei media o lo spostamento tramite drag and drop, comporta il ricalcolo dei numeri di sequenza di tutti i media coinvolti nello spostamento, con possibilità di perdita di eventuali riferimenti esterni.");
        }
        if(self.sectionJson.warning){
            warning = warning.concat(self.sectionJson.warning);
        }
        if(warning.length>0)
            _this.renderSectionWarning(warning);
        self.medias = [];
        self.mediaActive = null;
        var medias = json[type];
        var mediaData = {
            "name": "Sezione " + type.toUpperCase(),
            "contents": [],
            "groups" : { "id": "groups", "mandatory": "", "label": "Gruppi", "cssClass": "", "groups": groups},
            "buttons": [
                {
                    "type": "save",
                    "label": "Salva sezione"
                }
            ]
        };
        _.each(medias, function(item) {
            var src = self.config['rootUrl'] + item.file;
            if (type != 'img' || item.file == '') {
                src = json.placeholder;
            }
            var label = item.nomenclature;
            // if (item.nomenclature.length > 15) {
            //     label = item.nomenclature.substring(0, 15) + '...';
            // }
            mediaData.contents.push(
                {
                    'id': 'media' + item.id,
                    'type': 'entry',
                    'label': label,
                    'descr': '',
                    'src': src,
                    'seq': item.sequence_number,
                    'media_type': type,
                    'groups': groups,
                    'holdingsID': item.holdingsID,
                    'side': item.side,
                    'scale': item.scale,
                    'note': item.note,
                    'usage_ab': item.usage_ab
                    //'source': item.source
                }
            )
        });
        _.each(mediaData.contents, function(item) {
            self.medias.push(item);
        });
        self.totalPages = Math.ceil(mediaData.contents.length / self.config['pagination']);
        if (self.totalPages == 0) {
            self.totalPages = 1;
        }
        mediaData['contents'] = self.medias.slice(0, self.config['pagination']);
        mediaData['pagination'] = {'id': 'pagination', 'type': 'pagination', 'totalPages': self.totalPages, 'selected': 1};
        self.createGalleryContext(mediaData, false);
        self.attachFormContext([]);
        $("#gallery").tooltip();
        var gallery = $("#gallery");
        var startPos;
        var endPos;
        gallery.sortable({
            start: function (event, ui) {
                startPos = ui.item.index();
            },
            stop: function (event, ui) {
                _this.reloadGallerySequence(startPos,endPos,ui);
            }
        });
        gallery.disableSelection();
    },

    setGalleryEvents: function() {
        var _this = this;
        var mediaData = {};
        var body = $('body');
        // body.on('click', '.anchor-thumbnail', (function () {
        //     $('.anchor-thumbnail').colorbox({href: $(this).attr('src')});
        // }));
        body.on('click', 'button[id="addmedia"]', (function () {
            var total_count;
            try{
                var maxObj = _.max(self.medias, function(media){ return parseInt(media.seq); });
                total_count = !_.isFinite(maxObj.seq) ? 1 : parseInt(maxObj.seq)+1;
            }
            catch(err){
                total_count=self.medias.length+1;
            }
            var id = "new_"+new Date().getTime();
            var label = "";
            var seq = total_count;
            var src = self.config['section_meta'][self.sectionId]['placeholder'];
            self.medias.push({'id': id, 'type': 'entry', 'label': label, 'desc': "", 'src': src, 'groups' : $('#groups').data('groups'), 'media_type': self.sectionId, 'seq': seq.toString()});
            self.totalPages = Math.ceil(self.medias.length / self.config['pagination']);
            self.currentPage = self.totalPages;
            var begin = (self.totalPages - 1) * self.config['pagination'];
            var end = (self.totalPages * self.config['pagination']);
            mediaData['name'] = 'Sezione ' + self.sectionId.toUpperCase();
            mediaData['contents'] = self.medias.slice(begin, end);
            mediaData['pagination'] = {'id': 'pagination', 'type': 'pagination', 'totalPages': self.totalPages, 'selected': self.totalPages};
            self.updateGalleryContext(mediaData);
            var mediaToAdd = {"id":id,"sequence_number":seq.toString(),"file":"","nomenclature":label, "added": true};
            self.sectionJson[self.sectionId].push(mediaToAdd);
            $('a.cancel-button').on('click', (function (e) {
                var id;
                try{
                    id = $(this).parents(".entry-thumb")[0].id;
                }
                catch(err){
                    var myStack = {"dir1":"down", "dir2":"right", "push":"top"};
                    $.pnotify({
                        title: "Attenzione",
                        text: "C'è stato un errore durante la cancellazione del media",
                        stack: myStack,
                        hide:true,
                        delay: 3000,
                        remove: true,
                        history: false
                    })
                    return;
                }
                _this.deleteMediaGallery(mediaData,id);
            }));
            self.sectionModify=true;
        }));
        body.on('click', 'a.cancel-button', (function (e) {
            var id;
            try{
                id = $(this).parents(".entry-thumb")[0].id;
            }
            catch(err){
                var myStack = {"dir1":"down", "dir2":"right", "push":"top"};
                $.pnotify({
                    title: "Attenzione",
                    text: "C'è stato un errore durante la cancellazione del media",
                    stack: myStack,
                    hide:true,
                    delay: 3000,
                    remove: true,
                    history: false
                })
                return;
            }
            _this.deleteMediaGallery(mediaData,id);
        }));
        body.on('click', '.pagination-entry', (function (e) {
            self.currentPage = parseInt(e.target.text);
            var begin = (parseInt(e.target.text) - 1) * self.config['pagination'];
            var end = (parseInt(e.target.text) * self.config['pagination']);
            mediaData['contents'] = self.medias.slice(begin, end);
            mediaData['pagination'] = {'id': 'pagination', 'type': 'pagination', 'totalPages': self.totalPages, 'selected': parseInt(e.target.text)};
            self.updateGalleryContext(mediaData);
        }));
        body.on('click', 'li#prev', (function () {
            self.currentPage = $('li.active').data('num');
            self.totalPages = Math.ceil(self.medias.length / self.config['pagination']);
            if (self.currentPage - 1 >= 1) {
                var begin = ((self.currentPage - 1) - 1) * self.config['pagination'];
                var end = (self.currentPage - 1) * self.config['pagination'];
                mediaData['contents'] = self.medias.slice(begin, end);
                mediaData['pagination'] = {'id': 'pagination', 'type': 'pagination', 'totalPages': self.totalPages, 'selected': self.currentPage - 1};
                self.updateGalleryContext(mediaData);
                self.currentPage = self.currentPage - 1;
            }
        }));
        body.on('click', 'li#next', (function () {
            self.currentPage = $('li.active').data('num');
            self.totalPages = Math.ceil(self.medias.length / self.config['pagination']);
            if (self.currentPage + 1 <= self.totalPages) {
                var begin = ((self.currentPage + 1) - 1) * self.config['pagination'];
                var end = (self.currentPage + 1) * self.config['pagination'];
                mediaData['contents'] = self.medias.slice(begin, end);
                mediaData['pagination'] = {'id': 'pagination', 'type': 'pagination', 'totalPages': self.totalPages, 'selected': self.currentPage + 1};
                self.updateGalleryContext(mediaData);
                self.currentPage = self.currentPage + 1;
            }
        }));
        body.on('click', 'a.edit-metadata', (function () {
            var result = '';
            var seq = $(this).data('seq');
            var nomenclature = $(this).data('nomenclature');
            var groups = $(this).data('groups');
            var holdingsID = $(this).data('holdingsid');
            var scale = $(this).data('scale');
            var side = $(this).data('side');
            var note = $(this).data('note');
            var usage_ab = $(this).data('usage_ab');
            var type = $(this).data('type');
            var id = $(this).closest(".entry-thumb").attr("id");
            id = id.indexOf("new")!==-1 ? id.replace("media","") : parseInt(id.replace("media",""));
            var itemJson = _.find(self.sectionJson[self.sectionId],{"id":id});
            var target = itemJson && itemJson.target ? itemJson.target : null;
            if(itemJson && itemJson.usage){
                result=_.clone(itemJson);
                result.usage=[];
                _.forEach(itemJson.usage,function(value,key){
                    value.usage = [value.usage];
                    result.usage.push(value);
                });
                _this.prepareMediaFormEdit(seq,nomenclature,groups,holdingsID,scale,side,note,usage_ab,target,type,result);
            }
            else{
                var data = {'url': self.config['restServerUrlMedia'], 'id': self.magId, 'type': $(this).data('type'), 'seq': id};
                _this.magService.mediaExtendedMetadataCaller(data, function(json) {
                    if (json && !jQuery.isEmptyObject(json)) {
                        result = json;
                    }
                    _this.prepareMediaFormEdit(seq,nomenclature,groups,holdingsID,scale,side,note,usage_ab,target,type,result);
                });
            }
        }));
        body.on('click', 'input[data-action="back"]', (function () {
            _this.initGallery(self.sectionJson,self.sectionId);
        }));
    },

    deleteMediaGallery:function(mediaData,id){
        var _this=this;
        var mediaIndex = _.findIndex(self.medias,{"id":id});
        self.medias.splice(mediaIndex, 1);
        var itemDeleted = self.sectionJson[self.sectionId].splice(mediaIndex, 1);
        if(!itemDeleted[0].added){
            delete itemDeleted[0]["updated"];
            itemDeleted[0]["deleted"]=true;
            self.mediasDeleted.push(itemDeleted[0]);
        }
        self.totalPages = Math.ceil(self.medias.length / self.config['pagination']);
        if (self.totalPages == 0) {
            self.totalPages = 1;
        }
        self.currentPage = $('li.active').data('num');
        var index = self.totalPages;
        if (self.totalPages > self.currentPage) {
            index = self.currentPage;
        } else {
            index = self.totalPages;
        }
        var begin = (index - 1) * self.config['pagination'];
        var end = (index) * self.config['pagination'];
        mediaData['contents'] = self.medias.slice(begin, end);
        mediaData['pagination'] = {'id': 'pagination', 'type': 'pagination', 'totalPages': self.totalPages, 'selected': index};
        _this.reloadMediaSequence();
        self.updateGalleryContext(mediaData);
        self.sectionModify=true;
    },

    prepareMediaFormEdit:function(seq,nomenclature,groups,holdingsID,scale,side,note,usage_ab,target,type,result){
        var _this=this;
        var groupData = [{"value":"","description":"-"}];
        if (groups) {
            _.each(groups.split(','), function (item) {
                groupData.push(
                    {
                        "value": item,
                        "description": ""
                    }
                )
            });
        }
        var schema = _this.getSchemaJson(self.sectionId);
        switch (type) {
            case 'img':
            case 'audio':
            case 'video':
                var schemaGroup = _.find(schema.contents,{"id":"usage"});
                if(schemaGroup)
                    schemaGroup.children[1].data = groupData;
                break;
            default:
                break;
        }
        result['sequence_number'] = seq;
        result['nomenclature'] = nomenclature;
        result['target'] = target;
        result['holdingsID'] = holdingsID;
        result['scale'] = scale;
        result['side'] = side;
        result['note'] = note;
        result['usage_ab'] = usage_ab;
        _this.populateEdit(schema, result);
        self.mediaActive = seq;
        $("form#pageEdit :input").on("change",function(){
            self.mediaActiveChanged=true;
        });
        $(".GFEFieldset .GFERowDelete").on("click",function(){
            self.mediaActiveChanged=true;
        });
        $(".GFEFieldset .btn.GFEAddRow").on("click",function(){
            setTimeout(function(){
                $("form#pageEdit :input").on("change",function(){
                    self.mediaActiveChanged=true;
                });
                $(".GFEFieldset .GFERowDelete").on("click",function(){
                    self.mediaActiveChanged=true;
                });
            });
        });
    },

    populateEdit: function(schema, data) {
        this.createFormFromJson(schema);
        this.attachFormEdit(data);
    },

    createTreeFromJson: function(data, json,openNode) {
        var _this = this;
        var html = self.treeBuilder.render(data);
        $('#treeview-inner').html(html);
        if(self.magId.indexOf("draft_")===-1 && !self.draft)
            $("#btnValidateMag").remove();
        var magTree = $('#mag-tree');
        $('#add-stru').off().on('click', function() {
            var selected = magTree.jstree("get_selected", true);
            var isFolder = magTree.jstree("get_selected", true)[0].data.type == 'stru';
            if (isFolder) {
                var newNode = {
                    nomenclature:'Nuovo element',
                    sequence_number:'',
                    element:[],
                    stru:[]
                };
                var serverNode;
                if(selected[0].id==="stru"){
                    serverNode = _this.stru_json;
                }
                else{
                    try{
                        serverNode = self.treeBuilder.mappingTreeObj.fromJsTreeToExt(selected[0].id,_this.stru_json).stru;
                    }
                    catch(err){
                        console.log(err)
                    }
                }
                if(serverNode)
                    serverNode.push(newNode);
                _this.createTreeFromJson(self.tree_schema, _this.stru_json, selected[0].id);
                Glizy.events.broadcast("app.MagTreeCreated");
                self.sectionModify = true;
            }
            else{
                var myStack = {"dir1":"down", "dir2":"right", "push":"top"};
                $.pnotify({
                    title: "Attenzione",
                    text: "Puoi aggiungere nodi solo dopo aver selezionato una cartella",
                    addclass: "stack-custom",
                    stack: myStack,
                    hide:true,
                    delay: 3000,
                    remove: true,
                    history: false
                });
            }
        });
        $('#add-elem').off().on('click', function() {
            var selected = magTree.jstree("get_selected", true);
            var isFolder = magTree.jstree("get_selected", true)[0].data.type == 'stru';
            if (isFolder) {
                var newNode = {
                    "nomenclature": "",
                    "identifier": "",
                    "file": "",
                    "start": "",
                    "stop": "",
                    "resource": ""
                };
                var serverNode;
                if(selected[0].id==="stru"){
                    serverNode = _this.stru_json;
                }
                else{
                    try{
                        serverNode = self.treeBuilder.mappingTreeObj.fromJsTreeToExt(selected[0].id,_this.stru_json).element;
                    }
                    catch(err){}
                }
                if(serverNode)
                    serverNode.push(newNode);
                _this.createTreeFromJson(self.tree_schema, _this.stru_json, selected[0].id);
                Glizy.events.broadcast("app.MagTreeCreated");
                self.sectionModify = true;
            }
            else{
                var myStack = {"dir1":"down", "dir2":"right", "push":"top"};
                $.pnotify({
                    title: "Attenzione",
                    text: "Puoi aggiungere nodi solo dopo aver selezionato una cartella",
                    addclass: "stack-custom",
                    stack: myStack,
                    hide:true,
                    delay: 3000,
                    remove: true,
                    history: false
                });
            }
        });
        magTree.jstree({
            "core" : {
                check_callback : function (operation, node, node_parent, node_position, more) {
                    // operation can be 'create_node', 'rename_node', 'delete_node', 'move_node' or 'copy_node'
                    // in case of 'rename_node' node_position is filled with the new node name
                    if (operation == 'move_node') {
                        if (node_parent.data && node.data.type == 'element') {
                            return node_parent.data.type === "stru"
                        } else if (node_parent.data && node.data.type == 'stru') {
                            return node_parent.data.type === "stru" || node_parent.id === 'stru';
                        } else {
                            return false;
                        }
                    }
                    return true;
                }
            },
            "plugins" : ["dnd","actions"]
        });
        magTree.jstree(true).add_action("all", {
            "id": "action_remove",
            "class": "icon icon-trash btn-delete-node",
            "text": "",
            "after": true,
            "selector": "i",
            "event": "click",
            "callback": function(node_id, node, action_id, action_el){
                var deleteNode = self.treeBuilder.mappingTreeObj.deleteFromExt(node.id,_this.stru_json);
                if(!deleteNode){
                    var myStack = {"dir1":"down", "dir2":"right", "push":"top"};
                    $.pnotify({
                        title: "Attenzione",
                        text: "C'è stato un problema nella cancellazione dell'elemento",
                        addclass: "stack-custom",
                        stack: myStack,
                        hide:true,
                        delay: 3000,
                        remove: true,
                        history: false
                    });
                    return;
                }
                self.sectionModify = true;
                _this.createTreeFromJson(self.tree_schema, _this.stru_json,node.parent);
                Glizy.events.broadcast("app.MagTreeCreated");
            }
        });
        //mettere iterate=false per abilitare lazy loading custom in caso di problemi con il progressive render di jstree
        var iterate= true;
        var struTreeObj = self.treeBuilder.addStruTreeNodesFromJson(json,"stru",true,"stru",iterate);
        _.forEach(struTreeObj,function(value){
            magTree.jstree('create_node',$("#stru"),value,'last');
        });
        magTree.on('select_node.jstree', function (e, data) {
            if(_this.stru_node_selected){
                var changed = !_.isMatch(self.formBuilder.originalData.get(),self.formBuilder.serializeData());
                if(changed){
                    _this.updateExtDataFromStruTree(_this.stru_node_selected,self.formBuilder.serializeData());
                    _this.updateStruNode(_this.stru_node_selected,self.formBuilder.serializeData());
                    self.sectionModify = true;
                }
            }
            var newSelected = data.node.parent==="#" ? null : data.node;
            _this.stru_node_selected = newSelected;
            $(".btn-delete-node").css("display","none");
            if(data.node.parent==="#")
                return;
            $("#"+data.node.id+ " > .btn-delete-node").css("display","inline-block");
        });
        magTree.on('move_node.jstree', function (e, data) {
            var id = data.node.id;
            var serverNode = self.treeBuilder.mappingTreeObj.fromJsTreeToExt(id,_this.stru_json);
            self.treeBuilder.mappingTreeObj.deleteFromExt(id,_this.stru_json);
            var idNewParent = data.parent;
            var serverNewParent = self.treeBuilder.mappingTreeObj.fromJsTreeToExt(idNewParent,_this.stru_json);
            serverNewParent[data.node.data.type].push(serverNode);
            self.sectionModify = true;
            _this.createTreeFromJson(self.tree_schema, _this.stru_json,data.node.parent);
            Glizy.events.broadcast("app.MagTreeCreated");
        });
        if(!iterate){
            magTree.on('open_node.jstree', function (e, data) {
                if(data.node.parent==="#")
                    return
                var id = data.node.id;
                var node = magTree.jstree().get_node(id);
                if(node.children.indexOf(id+"_waiting")===-1)
                    return;
                magTree.jstree().delete_node($("#"+id+"_waiting"));
                var serverNode = self.treeBuilder.mappingTreeObj.fromJsTreeToExt(id,json);
                var childs;
                if(serverNode.stru && serverNode.stru.length)
                    childs = self.treeBuilder.addStruTreeNodesFromJson(serverNode.stru,id,false,"stru",false);
                if(serverNode.element && serverNode.element.length){
                    var eleChilds = self.treeBuilder.addStruTreeNodesFromJson(serverNode.element,id,false,"element",false);
                    childs = childs ? childs.concat(eleChilds) : eleChilds;
                }
                if(childs){
                    _.forEach(childs,function(value){
                        magTree.jstree('create_node',$("#"+id),value,'last');
                    });
                }
            })
        }
        magTree.on('ready.jstree', function(e, data) {
            if(openNode){
                magTree.jstree(true)._open_to(openNode);
                magTree.jstree("open_node",openNode);
                magTree.jstree(true).select_node(openNode);
                magTree.jstree(true).deselect_node("gen");
                self.sectionId=openNode;
                var node = magTree.jstree(true).get_node(openNode);
                _this.stru_node_selected=node;
                if(node.id==="stru"){
                    $('#treeview-dropdown').show();
                    return;
                }
                if (node.data.type == 'stru') {
                    $('#treeview-dropdown').show();
                    self.createFormContext(self.stru_node);
                } else if (node.data.type == 'element') {
                    $('#treeview-dropdown').hide();
                    self.createFormContext(self.element_node);
                }
                self.attachFormContext(node.data.treedata);
            }
        })
        self.jsTree = magTree;
    },

    createDropdownMenu: function() {
        return '<a data-toggle="dropdown" href="#" data-target="treeview-dropdown" class="dropdown-toggle"><i class="icon-double-angle-down"></i> Modifica Stru</a>' +
            '<ul class="dropdown-menu pull-right" role="menu" aria-labelledby="dLabel" style="margin-top: 25px">' +
            '<li><a id="add-stru" href="#"><i class="icon-plus"></i> Aggiungi nodo Stru</a></li>' +
            '<li><a id="add-elem" href="#"><i class="icon-plus"></i> Aggiungi nodo Element</a></li>' +
            '</ul>';
    },

    // Andrebbe spostata nel TreeBuilder
    createStruTreeFromJson: function(json, c, innerStru) {
        var _this = this;
        var html = '';
        _.each(json, function(value) {
            html += '<ul><li data-type="stru" data-jstree=\'{"icon":"images/stru_icon.png"}\' data-treedata=\'' + JSON.stringify(value) + '\'>';
            var sequence_number = 'Seq. n.: ';
            if (value['sequence_number']) {
                sequence_number += value['sequence_number'];
            }
            var nomenclature = ' - Nome: ';
            if (value['nomenclature']) {
                nomenclature += value['nomenclature'];
            }
            html += sequence_number + ' ' + nomenclature;
            _.each(value, function(v, k) {
                if (k == 'element') {
                    html += '<ul>';
                    var i = 0;
                    _.each(v, function(elementValue) {
                        var nomenclature = 'Nome: ' + elementValue['nomenclature'];
                        html += '<li data-type="element" data-jstree=\'{"icon":"images/stru_element_icon.png"}\' data-treedata=\''+JSON.stringify(elementValue)+'\'>' + nomenclature + '</li>';
                        i++;
                    });
                    html += '</ul>';
                }
                if (k == 'stru') {
                    html += _this.createStruTreeFromJson(v, 0, true);
                }
            });
            c++;
            html += '</li></ul>';
        });
        return html;
    },

    createFormFromJson: function(data) {
        var html = '<div class="section-title"><h1>' + data.name + '</h1></div>';
        html += self.formBuilder.render(data);
        self.form.html(html);
    },

    createGalleryFromJson: function(data) {
        var _this=this;
        var html = '<div class="section-title"><h1>' + data.name + '</h1></div>';
        html += self.galleryBuilder.render(data);
        self.form.html(html);
        var pageSelected = $("li[data-num='"+self.currentPage+"'] a");
        if(pageSelected) {
            setTimeout(function(){pageSelected.trigger('click');},100);
        }
        if(self.sectionId==="img")
            _this.createGalleryDialog();
    },

    createGalleryDialog: function(){
        $('#gallery').append("<div id='gallery-thumb-dialog'><img src='' /></div>");
        var dialogHeight = window.innerHeight-105;
        var dialogOpts = {
            "autoOpen":false,
            "modal": true,
            "dialogClass":"gallery-thumb-dialog",
            "width":"90%",
            "height":(window.innerHeight-60),
            "position": { my: "top", at: "top", of: window },
            "draggable": false
        };
        $('#gallery-thumb-dialog').dialog(dialogOpts);
        $('#gallery .galleryImg').on("click",function(ev){
            $('#gallery-thumb-dialog img').attr("src","");
            var title = $(ev.target).next("h4").html();
            $('#gallery-thumb-dialog').dialog("option","title",title);
            $('#gallery-thumb-dialog').dialog("open");
            var imgSrc = ev.target.src.replace("?mode="+self.config.thumbnailMode,"");
            setTimeout(function(){
                $('#gallery-thumb-dialog img').attr("src",imgSrc);
            },100);
        });
    },

    updateGalleryFromJson: function(data) {
        var _this=this;
        var html = '';
        html += self.galleryBuilder.update(data);
        $('#gallery').html(html);
        $('.pagination').html(self.galleryBuilder.updatePagination(data));
        if(self.sectionId==="img")
            _this.createGalleryDialog();
        var gallery = $("#gallery");
        var startPos;
        var endPos;
        gallery.sortable({
            start: function (event, ui) {
                startPos = ui.item.index();
            },
            stop: function (event, ui) {
                _this.reloadGallerySequence(startPos,endPos,ui);
            }
        });
        gallery.disableSelection();
    },

    reloadGallerySequence:function(startPos,endPos,ui){
        if (startPos === ui.item.index())
            return;
        var _this = this;
        var pagination = self.config['pagination'];
        var currPage = $('li.active').data('num');
        var actualPos = pagination * (currPage - 1);
        startPos = actualPos + startPos;
        endPos = ui.item.index();
        endPos = actualPos + endPos;
        var element = self.medias[startPos];
        element['seq'] = endPos;
        element['id'] = 'media'+endPos;
        ui.item.find("a.edit-metadata").attr('data-seq', endPos + 1);
        self.medias.splice(endPos, 0, self.medias.splice(startPos, 1)[0]);
        //riordino il media spostato anche nel json dei dati
        self.sectionJson[self.sectionId].splice(endPos, 0, self.sectionJson[self.sectionId].splice(startPos, 1)[0]);
        var from = startPos<endPos ? startPos : endPos;
        var to = startPos>endPos ? startPos : endPos;
        _this.reloadMediaSequence(from,to);
        self.sectionModify=true;
    },

    reloadMediaSequence: function(from,to){
        var n = from+1;
        for(var i = from; i<=to; i++){
            var media = self.medias[i];
            media['seq'] = n.toString();
            //media['id'] = 'media'+n;
            //cambio il sequence_number del media spostato anche nel json dei dati
            self.sectionJson[self.sectionId][i].sequence_number=n.toString();
            if(!self.sectionJson[self.sectionId][i].added)
                self.sectionJson[self.sectionId][i].updated=true;
            n++;
        }
    },

    updateGalleryMedia:function(secJson,mediaAct,mediaData){
        var _this = this;
        var indexMediaUpdated = _.findIndex(secJson,{"sequence_number":mediaAct.toString()});
        if(indexMediaUpdated===-1)
            return;
        var usage = {};
        var low = {
            level:0,
            value:""
        };
        _.forEach(mediaData.usage,function(value,key){
            usage[value.usage] = value;
            if(low.level<parseInt(value.usage)){
                low.level=parseInt(value.usage);
                low.value=value.file;
            }
        });
        secJson[indexMediaUpdated].nomenclature = mediaData.nomenclature;
        secJson[indexMediaUpdated].groupID = mediaData.groupID;
        secJson[indexMediaUpdated].holdingsID = mediaData.holdingsID;
        secJson[indexMediaUpdated].scale = mediaData.scale;
        secJson[indexMediaUpdated].side = mediaData.side;
        secJson[indexMediaUpdated].note = mediaData.note;
        secJson[indexMediaUpdated].usage_ab = mediaData.usage_ab;
        secJson[indexMediaUpdated].target = mediaData.target;
        if(mediaData.source)
            secJson[indexMediaUpdated].source = mediaData.source;
        secJson[indexMediaUpdated].usage = usage;
        if(low.value)
            secJson[indexMediaUpdated].file = low.value+"?mode="+self.config.thumbnailMode;

        var newSeq = parseInt(mediaData.sequence_number);
        if(newSeq!==mediaAct){
            self.medias.splice((newSeq-1), 0, self.medias.splice(indexMediaUpdated, 1)[0]);
            //riordino il media spostato anche nel json dei dati
            secJson.splice((newSeq-1), 0, secJson.splice(indexMediaUpdated, 1)[0]);
            var from = mediaAct<newSeq ? mediaAct : newSeq;
            var to = mediaAct>newSeq ? mediaAct : newSeq;
            _this.reloadMediaSequence(from-1,to-1);
        }
        if(!secJson[indexMediaUpdated].added)
            secJson[indexMediaUpdated].updated=true;
    },

    attachFormEdit: function(formData){
        if (formData) {
            window.formDataActive = Glizy.oop.create("glizy.FormEdit", self.formId, _.extend({
                AJAXAction: 'event:app.saveData',
                formData: formData
            }, self.glizyOpt));
            self.formBuilder.originalData.set(self.formBuilder.serializeData());
            if(self.sectionId==="img" || self.sectionId==="audio" || self.sectionId==="video" || self.sectionId==="bib"){
                var msg = {
                    "form":formData,
                };
                Glizy.events.broadcast("app.Section"+self.sectionId+"Created",msg);
            }
        }
    },

    updateExtDataFromStruTree: function(node,data){
        var _this = this;
        var id = node.id;
        var serverNode = self.treeBuilder.mappingTreeObj.fromJsTreeToExt(id,_this.stru_json);
        _.each(data,function(value,key){
            serverNode[key]=value;
        });
        return _this.stru_json;
    },

    updateStruNode: function(node,data){
        var _this = this;
        var id = node.id;
        if(node.id==="stru")
            return;
        _.forEach(data,function(value,key){
            node.data.treedata[key] = value;
        });
        node.li_attr["data-treedata"] = JSON.stringify(data);
        node.text=data.nomenclature;
        var magTree = $('#mag-tree');
        magTree.jstree(true).rename_node(magTree.jstree(true).get_node(id),data.nomenclature);
    },

    onSaveData: function(e) {
        var _this = this;
        if(self.mediaActive){
            if(self.mediaActiveChanged){
                _this.updateGalleryMedia(self.sectionJson[self.sectionId],self.mediaActive,e.message.data);
                self.mediaActiveChanged=false;
                self.sectionModify=true;
            }
            _this.initGallery(self.sectionJson,self.sectionId);
            return;
        }
        var ajaxData = {
            "url":self.config['restServerUrlSave'],
            "magId": self.magId,
            "user": self.config['user']
        };
        if(self.sectionId == 'stru' || _this.stru_node_selected || (self.sectionId.indexOf('stru-')!==-1)){
            var changed = !_.isMatch(self.formBuilder.originalData.get(),self.formBuilder.serializeData());
            if(changed){
                if(!_this.stru_node_selected){
                    var magTree = $('#mag-tree');
                    _this.stru_node_selected = magTree.jstree().get_node(self.sectionId);
                }
                _this.updateExtDataFromStruTree(_this.stru_node_selected,self.formBuilder.serializeData());
                _this.updateStruNode(_this.stru_node_selected,self.formBuilder.serializeData());
            } 
            ajaxData.section = "stru";
            ajaxData.data = _this.stru_json; 
            _this.saveData(ajaxData);
            return;
        }
        if (self.sectionId == 'gen' || self.sectionId == 'bib' || self.sectionId == 'dis') {
            ajaxData.section = self.sectionId;
            ajaxData.data = e.message.data;
            _this.saveData(ajaxData);
        }
        else{
            ajaxData.section = self.sectionId;
            var mediaUpdated = _.filter(self.sectionJson[self.sectionId],{updated:true});
            var mediaAdded = _.filter(self.sectionJson[self.sectionId],{added:true});
            ajaxData.data = mediaUpdated.concat(mediaAdded).concat(self.mediasDeleted);
            _this.saveData(ajaxData);
        }
    },

    saveData:function(ajaxData){
        var _this=this;
        var saveAlert = $.pnotify({
            title: 'Attendi',
            text: 'Stiamo salvando i dati, ti preghiamo di attendere...',
            type: 'info',
            hide: false,
            history: false,
            addclass: 'stack-modal',
            stack: {
                'dir1': 'down',
                'dir2': 'right',
                'modal': true
            },
            buttons: {
                closer: false,
                sticker: false
            },
            mobile: {
                swipe_dismiss: false
            }
        });
        _this.magService.saveMagCaller(ajaxData, function (json) {
            self.mediasDeleted=[];
            self.sectionModify=false;
            saveAlert.remove();
            self.formBuilder.originalData.set(self.formBuilder.serializeData());
            if (json['stato']) {
                if(json["id"] && self.magId!==json["id"]){
                    var newPath = window.location.href.replace(self.magId,json["id"]);
                    window.location.href=newPath;
                }
                switch (self.sectionId) {
                    case 'img':
                    case 'audio':
                    case 'video':
                    case 'doc':
                    case 'ocr':
                        var dropdown = $('#treeview-dropdown');
                        var tree = $('#mag-tree');
                        var ajaxData = { "url": self.config['restServerUrlContent'], "magId": self.magId };
                        self.goToSection(dropdown,ajaxData,null);
                        break;
                    default:
                        break;
                }
            }
            else{
                var myStack = {"dir1":"down", "dir2":"right", "push":"top"};
                $.pnotify({
                    title: "Attenzione",
                    text: "C'è stato un problema nel salvataggio dei dati. Ti preghiamo di riprovare più tardi.",
                    addclass: "stack-custom",
                    stack: myStack,
                    hide:true,
                    delay: 3000,
                    remove: true,
                    history: false
                });
            }
        });
    },

    onSaveDataCalled:false,

    getSchemaJson: function(data) {
        var json = null;
        switch (data) {
            case "gen":
                json = self.section_gen_schema;
                break;
            case "bib":
                json = self.section_bib_schema;
                break;
            case "stru":
                json = self.section_stru_schema;
                break;
            case "img":
                json = self.section_img_schema;
                break;
            case "audio":
                json = self.section_audio_schema;
                break;
            case "video":
                json = self.section_video_schema;
                break;
            case "doc":
                json = self.section_doc_schema;
                break;
            case "ocr":
                json = self.section_ocr_schema;
                break;
            case "dis":
                json = self.section_dis_schema;
                break;
        }
        return json;
    },

    ajaxCaller: function(data,clb,clbParams) {
        var _this = this;
        var section = data.section;
        var schema = _this.getSchemaJson(section);
        _this.magService.ajaxCaller(data, function(json) {
            if (schema) {
                self.project = json['project'];
                self.formBuilder.setProject(json['project']);
                self.treeBuilder.setProject(json['project']);
                switch (section) {
                    case "gen":
                        json[section]['project'] = json['project'];
                        _this.createFormFromJson(schema);
                        _this.attachFormEdit(json[section]);
                        break;
                    case "bib":
                    case "dis":
                        _this.createFormFromJson(schema);
                        _this.attachFormEdit(json[section]);
                        break;
                    case "stru":
                        _this.stru_json = json[section] || [];
                        _this.createTreeFromJson(self.tree_schema, _this.stru_json);
                        Glizy.events.broadcast("app.MagTreeCreated");
                        break;
                    case "img":
                    case "audio":
                    case "video":
                    case "doc":
                    case "ocr":
                        json['placeholder'] = data.placeholder;
                        _this.initGallery(json, section);
                        break;
                }
                Glizy.events.broadcast("app.Section" + section + "Created");
                Glizy.events.broadcast("app.SetAutoComplete");
                if(clb){
                    clb.apply(this,clbParams);
                }
            }
        });
    },

    getParameterByName: function(name, url) {
        if (!url) url = window.location.href;
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }
});