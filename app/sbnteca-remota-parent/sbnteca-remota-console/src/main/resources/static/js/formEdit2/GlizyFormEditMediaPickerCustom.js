Glizy.oop.declare("glizy.FormEdit.mediapickercustom", {
    $extends: Glizy.oop.get('glizy.FormEdit.standard'),
    $mediaPicker: null,
    $dialogContainer: null,
    populateDataEnabled: false,
    eventPos: null,
    eventPosMagService: null,
    eventPosMagServiceChild: null,
    imageResizer: null,
    directory: null,
    mediapickerLoaded: false,
    objTree: null,
    MediaPickerCallerBusy: false,

    initialize: function (element, glizyOpt) {
        element.attr("id","input-mediapickercustom-"+new Date().getTime());
        element.data('instance', this);
        this.$element = element;
        this.populateDataEnabled = element.attr('data-populate_data') == 'true';
        this.imageResizer = glizyOpt.imageResizer;
        
        this.mediapickerLoaded = false;

        var that = this;
        var $input = element.hide(),
            pickerType = $input.attr('data-mediatype'),
            project = $input.attr('data-project'),
            externalFiltersOR = $input.attr('data-externalfiltersor'),
            hasPreview = $input.attr('data-preview') == 'true';

        this.templateDefine();

        this.$mediaPicker = jQuery(Glizy.template.render(hasPreview ? 'mediapickercustom.pickerWithPreview' : 'mediapickercustom.pickerWithoutPreview', {name: element.attr('name')}));
        this.$dialogContainer = jQuery(Glizy.template.render('mediapickercustom.pickerDialog', {name: element.attr('name')}));


       // that.$mediaPicker =
       //     hasPreview ? jQuery('<div id="'+element.attr('name')+'-mediapicker" class="mediaPickerSelector mediaPickerField"><div class="mediaPickerCaption"></div><div class="mediaPickerElement">' + GlizyLocale.MediaPicker.imageEmpty + '</div></div>')
       //     : jQuery('<input class="mediaPickerField" type="text" size="50" readonly="readonly" style="cursor:pointer" value="' + GlizyLocale.MediaPicker.imageEmptyText + '">');

        this.eventPosMagService = Glizy.events.on("magservice.projectMediaPickerCaller_res", this.onProjectMediaPickerCallerTimeout_res, this);
        this.eventPosMagServiceChild = Glizy.events.on("magservice.projectMediaPickerCallerChild_res", this.onProjectMediaPickerCallerChild_res, this);

        if (!$input.next().hasClass('mediaPickerField')) {
            var that = this;
            this.$dialogContainer.insertAfter($input);
            this.$mediaPicker.insertAfter($input).click(function() {
                if (that.eventPosMagService === null || that.eventPosMagService === undefined) 
                    that.eventPosMagService = Glizy.events.on("magservice.projectMediaPickerCaller_res", that.onProjectMediaPickerCallerTimeout_res, that);
                if (that.eventPosMagServiceChild === null || that.eventPosMagServiceChild === undefined) 
                    that.eventPosMagServiceChild = Glizy.events.on("magservice.projectMediaPickerCallerChild_res", that.onProjectMediaPickerCallerChild_res, that);

                var options = {
                    "title":"Scegli il file da collegare",
                    "modal": true,
                    "appendTo": that.$dialogContainer,
                    "dialogClass": "mediapickerTreeDialog",
                    "width": "90%",
                    "maxWidth": "90%",
                    "height": window.innerHeight-200,
                    "maxHeight": window.innerHeight-200,
                    "draggable": false,
                    "open": function(event, ui) {
                        // avoid resizing beyond the lower dialog
                        $(this).dialog( "option", "maxWidth", this.clientWidth );
                    },
                    "close": function( event, ui ) {
                        if (parseInt(that.eventPosMagService) >= 0) {
                            Glizy.events.unbind("magservice.projectMediaPickerCaller_res", that.eventPosMagService);
                            that.eventPosMagService = null;
                        }
                        if (parseInt(that.eventPosMagServiceChild) >= 0) {
                            Glizy.events.unbind("magservice.projectMediaPickerCallerChild_res", that.eventPosMagServiceChild);
                            that.eventPosMagServiceChild = null;
                        }
                        //$( "#mediapicker-tree" ).dialog( "destroy" );
                    }
                };
                if (!that.mediapickerLoaded) {
                    Glizy.events.broadcast("magservice.projectMediaPickerCaller", {data: {url: app_config.restMediaPicker, project: new URLSearchParams(window.location.search).get("id"), filter: element.attr('data-mediatype')+'&depth=1'}, sender: {"attr":"id","value":element.attr('id')}});
                    var treeHtml = Glizy.template.render('mediapickercustom.resultsTree');
                    setTimeout(function(){
                        Glizy.openDialog(treeHtml,options);
                    }, 200);
                    that.mediapickerLoaded = true;
                    that.MediaPickerCallerBusy = false;
                } else {
                    setTimeout(function(){
                        var mediapickerTree = $('#'+element.attr('id')).parent(".controls").find("#mediapicker-tree");
                        Glizy.openDialog(mediapickerTree, options);
                    }, 200);
                }
                //    var url = glizyOpt.mediaPicker;
                //    if (project) {
                //        url += '?project=' + project;
                //    }
                //    if (pickerType) {
                //        url += '&filter=' + pickerType;
                //    }
                //    else if(externalFiltersOR){
                //        url += '&externalFiltersOR=' + externalFiltersOR;
                //    }
                //    console.log(url);
                //    Glizy.openIFrameDialog( hasPreview ? GlizyLocale.MediaPicker.imageTitle : GlizyLocale.MediaPicker.mediaTitle,
                //                            url,
                //                            1400,
                //                            50,
                //                            50,
                //                            null,
                //                            Glizy.responder(that, that.disposeEvent));
                //    //Glizy.openDialog(null);
                //    Glizy.lastMediaPicker = that;
                //    that.eventPos = Glizy.events.on("glizycms.onSetMediaPicker", Glizy.responder(that, that.onSetMediaPicker));
            });
        }

    },

    setDirectoryEvents: function() {
        $('body').on('click', '.nav-dir', (function () {
            var data = [];
            var tableHtml = {begin: '', middle: '', end: ''};
            tableHtml.begin += Glizy.template.render('mediapickercustom.resultsTableBegin');
            this.recursiveVisit(this.directory, data, $(this).data('depth') + 1);
            console.log(data);
            tableHtml.middle += this.populateRecord(data);
            tableHtml.end += Glizy.template.render('mediapickercustom.resultsTableEnd');
            //console.log(tableHtml);
            this.$dialogContainer.html(Glizy.template.render('mediapickercustom.pickerDialogContent'));
            $('#dialog-content').html(tableHtml.begin +  tableHtml.middle + tableHtml.end)
        }));
    },

    getValue: function () {
        return this.$element.val();
    },

    setValue: function (value,ele) {
        var element = ele ? $("input["+ele.attr+"='"+ele.value+"'") : this.$element;
        var _this=this;
        if (value) {
            _this.populateData(value,element);
        }
    },

    populateData: function(values,element) {
        var val = typeof values==="object" ? values : {"src":values,"thumbnail":app_config.rootUrl+values+"?mode="+self.config.thumbnailMode};
        var input = element;
        input.val(val.src);
        var preview = input.next("[id$='-mediapicker']");
        if(val.thumbnail && preview && preview.length){
            var thumbnail = input.attr('data-mediatype')==="img" ? val.thumbnail : self.config['section_meta'][input.attr('data-mediatype')]['placeholder'];
            $(preview).find(".mediaPickerElement img").attr("src",thumbnail);
        }
    },

    clearData: function() {
        // TODO: slegare il componente dal repeater
        var $container = this.$element.closest('.GFERowContainer');
        $container.find('input[disabled=disabled]').val('');
    },

    setProps: function (props) {
        var $this = this.$mediaPicker,
            $img = $this.find('img');

        if (this.populateDataEnabled) {
            if (props) {
                this.populateData(props);
            } else {
                this.clearData();
            }
        }

        if (!props || !props.id) {
            if ($img.length) {
                $img.replaceWith(GlizyLocale.MediaPicker.imageEmpty);
            }
            else {
                $this.val(GlizyLocale.MediaPicker.imageEmptyText);
            }
            $this.prev().val('');
        }
        else {
            if ($img.length) {
                $img.load(function () {

                        var w = this.naturalWidth,
                            h = this.naturalHeight,
                            maxW = $this.width() -6,
                            maxH = $this.height() -6;

                        if (w > maxW) {
                            h = h * (maxW / w);
                            w = maxW;
                        }
                        if (h > maxH) {
                            w = w * (maxH / h);
                            h = maxH;
                        }
                        jQuery(this).attr({width: w, height: h})
                            .show();
                    })
                    .hide();

                var src = this.imageResizer.replace('#id#', props.id);
                $img.attr({title: props.title, src: src})
                    .data({id: props.id, fileName: props.fileName});

                if ($img[0].complete && $img[0].naturalWidth !== 0) {
                    $img.trigger('load');
                }
            }
            else {
                $this.val(props.title);
            }
            $this.prev().val( JSON.stringify(props) );
        }
    },

    getName: function () {
        return this.$element.attr('name');
    },

    getPreview: function (val) {
        try {
            var props = JSON.parse(val);
            return props.title;
        } catch(e) {
            return val;
        }
    },

    disposeEvent: function()
    {
        if (this.eventPos!==null && this.eventPos!==undefined) {
            Glizy.events.unbind("glizycms.onSetMediaPicker", this.eventPos);
            this.eventPos = null;
        }

        if (this.eventPosMagService!==null && this.eventPosMagService!==undefined) {
            Glizy.events.unbind("magservice.projectMediaPickerCaller_res", this.eventPosMagService);
            this.eventPosMagService = null;
        }

        if (this.eventPosMagServiceChild!==null && this.eventPosMagServiceChild!==undefined) {
            Glizy.events.unbind("magservice.projectMediaPickerCallerChild_res", this.eventPosMagServiceChild);
            this.eventPosMagServiceChild = null;
        }
    },

    onSetMediaPicker: function(event)
    {
        this.disposeEvent();
        this.setProps(event.message);
        Glizy.closeIFrameDialog();
    },

    onProjectMediaPickerCallerTimeout_res: function(e) {
        var self = this;
        var ev = e;
        setTimeout(function(){
            self.onProjectMediaPickerCaller_res(ev);
        },500);
    },

    onProjectMediaPickerCallerChild_res: function (e) {
        if (e.message.response.length >= 0)
            this.onProjectMediaPickerCaller_res(e,true);
        else {
            var mediapickerTree = $('#'+e.message.sender.value).parent(".controls").find("#mediapicker-tree");
            var parentNode = mediapickerTree.jstree(true).get_node(e.message.sender.opener.id);
            var childNode = mediapickerTree.jstree(true).get_node(e.message.sender.opener.id+'_waiting');
            if (childNode) {
                var cloneNode = $.extend(true, {}, childNode);
                cloneNode.text = 'Nessun contenuto trovato';
                mediapickerTree.jstree(true).delete_node(childNode);
                mediapickerTree.jstree(true).create_node(parentNode.id, cloneNode);
            }
        }
    },

    onProjectMediaPickerCaller_res: function(e,loadChild) {
        var that = this;
        if (that.MediaPickerCallerBusy) {
            return;
        }
        that.MediaPickerCallerBusy = true;
        if(!e.message.response || typeof e.message.response !== "object")
            return console.log("Dati mancanti");
        var sender = e.message.sender;
        var jsonData = e.message.response;
        var iterate = true;
        var ready = false;
        var mediapickerTree = $('#'+sender.value).parent(".controls").find("#mediapicker-tree");
        if (loadChild) {
            var findParent = function(obj, id) {
                var parent = _.find(obj, { id: id });
                return parent;
            };
            var objtoScan = that.objTree;
            _.forEach(sender.parents,function(parent){
                var p = findParent(objtoScan, parent);
                if (p) {
                    objtoScan = p.children;
                }
            });
            var objChild = that.addMediaPickerTreeNodesFromJson(jsonData, true, sender.opener.id, iterate);
            var parentOfChild = _.find(objtoScan, { id: sender.opener.id });
            if (parentOfChild) 
                parentOfChild.children = objChild;
            mediapickerTree.jstree('destroy');
        } else {
            that.objTree = that.addMediaPickerTreeNodesFromJson(jsonData, true, "mediapickertree", iterate);
            ready = true;
        }
        //mettere iterate=false per abilitare lazy loading custom in caso di problemi con il progressive render di jstree
        var element = this.$element;
        mediapickerTree.jstree({
            "core" : {
                "data":that.objTree,
                "progressive_render": true,
                check_callback : function (operation, node, node_parent, node_position, more) {
                    return true;
                },
                "animation": false
            },
            "plugins" : ["json_data"]
        });
        
        mediapickerTree.on('select_node.jstree', function (e, data) {
            var type = data.node.data && data.node.data.type;
            if (type && type !== "directory") {
                that.setValue(data.node.data, sender);
                var msg = {
                    "sender": sender,
                    "value": data.node.data
                };
                Glizy.events.broadcast('mediapickerTree.selectMedia', msg);
            }
        });
        var onOpenNode = function (e, data) {
            if (!ready)
                return;
            var parents = _.clone(data.node.parents.reverse());
            var text = '';
            _.forEach(parents, function (parent) {
                if (parent !== '#') {
                    var parentNode = mediapickerTree.jstree(true).get_node(parent);
                    if (parentNode) {
                        text += parentNode.text + '/';
                    }
                }
            })
            text += data.node.text;
            Glizy.events.broadcast("magservice.projectMediaPickerCallerChild", { data: { url: app_config.restMediaPicker, project: new URLSearchParams(window.location.search).get("id"), filter: element.attr('data-mediatype') + '&depth=1&rel=' + text }, sender: { "attr": "id", "value": sender.value, "opener": data.node, "parents": parents } });
        };
        $( document ).tooltip({
            items: "#mediapicker-tree li[data-type='img'] i.jstree-themeicon-custom",
            content: function() {
                var element = $( this );
                var img = $(element).css("background-image").slice(4, -1).replace(/"/g, "");
                return "<img class='jstree-themeicon-custom-tooltip' src='"+img+"'/>";
            }
        });
        if(iterate){
            mediapickerTree.unbind('open_node.jstree');
            mediapickerTree.on('open_node.jstree', onOpenNode);
        } else {
            mediapickerTree.on('open_node.jstree', function (e, data) {
                var id = data.node.id;
                var tree = $("#mediapicker-tree");
                var node = tree.jstree().get_node(id);
                if(node.children.indexOf(id+"_waiting")===-1)
                    return;
                tree.jstree().delete_node($("#"+id+"_waiting"));
                var serverNode = self.treeBuilder.mappingTreeObj.fromJsTreeToExt(id,jsonData);
                var childs;
                if(serverNode.contenuto_directory && serverNode.contenuto_directory.length)
                    childs = that.addMediaPickerTreeNodesFromJson(serverNode.contenuto_directory,false,id,false);
                if(childs){
                    _.forEach(childs,function(value){
                        tree.jstree('create_node',$("#"+id),value,'last');
                    });
                }
            });
        }

        if (loadChild) {
            mediapickerTree.unbind('ready.jstree');
            mediapickerTree.on('ready.jstree', function(e) {
                _.forEach(sender.parents,function(parent){
                    if (parent !== '#') {
                        mediapickerTree.jstree("open_node", parent);
                    }
                });
                mediapickerTree.jstree("open_node", sender.opener.id);
                ready = true;
            });
        } 
        self.jsTree = mediapickerTree;
        
        that.MediaPickerCallerBusy = false;
    },

    addMediaPickerTreeNodesFromJson: function(json,root,parent,iterate){
        var that = this;
        var objJsTree = [];
        _.forEach(json,function(value,key){
            //inserisco i campi src e thumbnail mockati in attesa delle modifiche al servizio
            value.src = value.tipo!=="directory" ? value.nome.replace("?mode="+self.config.thumbnailMode,"") : null;
            value.thumbnail = value.tipo!=="directory" ? value.tipo==="img" ? app_config.rootUrl+value.nome : self.config['section_meta'][value.tipo]['placeholder'] : null;
            var schema = {
                "text": value.tipo!=="directory" ? value.label : "nome",
                "icon": value.thumbnail,
                "data":{
                    "type":value.tipo,
                    "src": value.src,
                    "thumbnail":value.thumbnail
                },
                "li_attr":{
                    "data-type":value.tipo
                }
            };
            var id = !root ? "contenuto_directory-" + key.toString() : key.toString();
            var node = self.treeBuilder.mappingTreeObj.fromExtToJsTree(value,schema,parent,id);
            if(iterate){
                if(value.contenuto_directory && value.contenuto_directory.length){
                    var childs = that.addMediaPickerTreeNodesFromJson(value.contenuto_directory,false,node.id,iterate);
                    node.children = childs;
                } else if(value.tipo === "directory") {
                    node.children = [{ "id": node.id + "_waiting", "text": "Attendi" }];
                }
            }
            else{
                node.children = (value.contenuto_directory && value.contenuto_directory.length>0) ? [{"id":node.id+"_waiting","text":"Attendi"}] : false;
            }
            objJsTree.push(node);
        });
        return objJsTree;
    },

    /*
     { "id" : "ajson1", "parent" : "#", "text" : "Simple root node" },
     { "id" : "ajson2", "parent" : "#", "text" : "Root node 2" },
     { "id" : "ajson3", "parent" : "ajson2", "text" : "Child 1" },
     { "id" : "ajson4", "parent" : "ajson2", "text" : "Child 2" },
     */

    recursiveVisit: function(node, data, level) {
        var _this = this;
        //console.log('Contenuto di data: ' + data);
        _.each(node, function(item, i) {
            data.push({"name": item.nome, "type": item.tipo, "depth": level, "index": i});
            //console.log(item);
            /*data += Glizy.template.render('mediapickercustom.resultsTableRowBegin');
            _.each(item, function(property) {
                if ($.type(property) === "string")
                    data += Glizy.template.render('mediapickercustom.resultsTableCell', {"property": property});
            });
            data += Glizy.template.render('mediapickercustom.resultsTableCell', {"property": father});
            data += Glizy.template.render('mediapickercustom.resultsTableRowEnd');
            if (item.tipo == 'directory') {
                level = item.nome;
                _this.recursiveVisit(item.contenuto_directory, data, level);
            }*/
        });
    },

    populateRecord: function(data) {
        var html = '';
        _.each(data, function(el) {
            html += Glizy.template.render('mediapickercustom.resultsTableRowBegin');
            _.each(el, function(val, key) {
                if ($.type(val) === "string")
                    html += Glizy.template.render('mediapickercustom.resultsTableCell', {value: val, key: key, index: el.index, depth: el.depth});
            });
            html += Glizy.template.render('mediapickercustom.resultsTableRowEnd');
        });
        return html;
    },

    focus: function () {
        var mediaPickerId = this.$element.attr('id')+'-mediapicker';
        $('#'+mediaPickerId).addClass('GFEValidationError');
        document.getElementById(mediaPickerId).scrollIntoView();
    },

    destroy: function() {
        this.disposeEvent();
    },

    isDisabled: function() {
        return this.$element.attr('disabled') == 'disabled';
    },

    addClass: function(className) {
        this.$element.addClass(className);
    },

    removeClass: function(className) {
        this.$element.removeClass(className);
    },

    templateDefine: function() {
        Glizy.template.define('mediapickercustom.pickerWithPreview',
            '<div id="<%= name %>-mediapicker" class="mediaPickerSelector mediaPickerField">' +
            '<div class="mediaPickerCaption"></div>' +
            '<div class="mediaPickerElement"><%= GlizyLocale.MediaPicker.imageEmpty %></div>' +
            '</div>'
        );

        Glizy.template.define('mediapickercustom.pickerWithoutPreview',
            '<input class="mediaPickerField" type="text" size="50" readonly="readonly" style="cursor:pointer" value="<%= GlizyLocale.MediaPicker.imageEmptyText %>">'
        );

        Glizy.template.define('mediapickercustom.pickerDialog',
            '<div id="<%= name %>-mediaPickerDialog" class="mediaPickerDialog" style="display:inline"></div>'
        );

        Glizy.template.define('mediapickercustom.pickerDialogContent',
            '<div id="dialog-content"></div>'
        );

        Glizy.template.define('mediapickercustom.resultsTableBegin',
            '<table class="table table-striped">'+
            '<thead>'+
            '<tr>'+
            '<th>Nome</th>'+
            '<th>Tipo</th>'+
            '<th>Profondità</th>'+
            '</tr>'+
            '</thead>'+
            '<tbody>'
        );

        Glizy.template.define('mediapickercustom.resultsTree',
            '<div id="mediapicker-tree">'+
            '<h4>Attendi il caricamento dei dati</h4>'+
            '</div>'
        );

        Glizy.template.define('mediapickercustom.resultsTableRowBegin',
            '<tr>'
        );

        Glizy.template.define('mediapickercustom.resultsTableRowEnd',
            '</tr>'
        );

        Glizy.template.define('mediapickercustom.resultsTableCell',
            '<% if (key == "name") { %>' +
            '<td><a class="nav-dir" href="#" data-path="<%= index %>|<%= value %>" data-depth="<%= depth %>"><%= value %></a></td>'+
            '<% } else { %>'+
            '<td><%= value %></td>'+
            '<% } %>'
        );

        Glizy.template.define('mediapickercustom.resultsTableEnd',
            '</tbody>'+
            '</table>'
        );
    }



});
