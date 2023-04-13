Glizy.oop.declare("TreeBuilder", {
    config: null,
    project: null,
    magID: null,

    initialize: function(config, project, magID) {
        this.config = config;
        this.project = project;
        this.magID = magID;
        this.templateDefine();
        Glizy.events.on("tree.loadMag", this.onLoadMag);
    },

    setProject: function (project) {
        this.project = project;
        this.templateDefine();
    },

    getProject: function () {
        return this.project
    },

    onLoadMag: function(e) {
        //console.log('onLoadMag', e.message);
    },

    render: function(data) {
        var html = Glizy.template.render('Tree.pre', {});
        _.each(data, function(item) {
            //console.log(item.selected);
            html += Glizy.template.render('Tree.'+item.type, item);
        });
        html += Glizy.template.render('Tree.post', {});
        html += Glizy.template.render('Tree.preButtons', {});
        html += Glizy.template.render('Tree.validate', {
            "label": "Pubblica in Teca"
        });
        html += Glizy.template.render('Tree.postButtons', {});
        return html;
    },

    templateDefine: function() {
        Glizy.template.define('Tree.pre',
            '<div id="mag-tree">' +
            '<ul>'
        );
        Glizy.template.define('Tree.post',
            '</ul>' +
            '</div>'
        );
        Glizy.template.define('Tree.parent',
            '<li data-jstree=\'{"icon":"<%= icon %>", "selected":"<%= selected %>"}\' data-type="<%= id %>" id="<%= id %>"><%= label %></li>'
        );
        Glizy.template.define('Tree.preButtons',
            '<div class="formButtons">'+
            '<div class="content">'
        );

        Glizy.template.define('Tree.postButtons',
            '</div>'+
            '</div>'
        );
        Glizy.template.define('Tree.validate',
            '<input id="btnValidateMag" class="btn btn-primary" type="button" value="<%= label %>" onclick="location.href=\'' + this.config.magValidationUrl + this.magID + '\';" />'
        );
    },

    mappingTreeObj:{
        getSruTreeNodeSchema:function(value,type){
            var schema = {
                "text":"nomenclature",
                "icon": type==="stru" ? "images/stru_icon.png" : "images/stru_element_icon.png",
                "data":{
                    "type":type,
                    "treedata":value
                },
                "li_attr":{
                    "data-type":type,
                    "data-treedata":JSON.stringify(value)
                }
            };
            return schema;
        },
        deleteFromExt:function(id,jsonData){
            var arId = id.split("-");
            var parentNode;
            try{
                for(var i=0;i<arId.length;i++){
                    var ele = _.isNaN(parseInt(arId[i])) ? arId[i] : parseInt(arId[i]);
                    if(i===(arId.length-1))
                       parentNode.splice(ele,1);
                    else 
                        parentNode = i===0 ? jsonData : parentNode[ele];
                }
            }
            catch(err){
                return false
            }
            return true;
        },
        fromExtToJsTree:function(obj,schema,parent,id){
            var o = {};
            o.id=parent ? parent+"-"+id : id;
            o.text=obj[schema.text]||schema.text||"[vuoto]";
            o.icon=obj[schema.icon]||schema.icon||null;
            o.data=schema.data;
            o.state=obj[schema.state]||null;
            o.li_attr=schema.li_attr||null;
            return o;
        },
        fromJsTreeToExt:function(id,jsonData){
            var arId = id.split("-");
            var extNode;
            _.forEach(arId,function(value,key){
                value = _.isNaN(parseInt(value)) ? value : parseInt(value);
                extNode = key===0 ? jsonData : extNode[value];
            });
            return extNode;
        }
    },

    addStruTreeNodesFromJson: function(json,parent,root,type,iterate){
        var that = this;
        var objJsTree = [];
        _.forEach(json,function(value,key){
            var tipo = root && (value.resource || value.resource==="") ? "element" : type;
            var schema = that.mappingTreeObj.getSruTreeNodeSchema(value,tipo);
            var id = !root && tipo ? tipo + "-" + key.toString() : key.toString();
            var node = that.mappingTreeObj.fromExtToJsTree(value,schema,parent,id);
            if(iterate){
                if(value.stru && value.stru.length){
                    var struChilds = that.addStruTreeNodesFromJson(value.stru,node.id,false,"stru",iterate);
                    node.children = struChilds;
                }
                if(value.element && value.element.length){
                    var eleChilds = that.addStruTreeNodesFromJson(value.element,node.id,false,"element",iterate);
                    node.children = node.children ? node.children.concat(eleChilds) : eleChilds;
                }
            }
            else{
                node.children = (value.stru && value.stru.length>0) || (value.element && value.element.length>0) ? [{"id":node.id+"_waiting","text":"Attendi"}] : false;
            }
            objJsTree.push(node);
        });
        return objJsTree;
    }
});