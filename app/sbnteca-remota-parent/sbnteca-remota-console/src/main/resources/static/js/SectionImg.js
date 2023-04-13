Glizy.oop.declare("glizy.FormEdit.SectionImg", {
    $extends: Glizy.oop.get('glizy.FormEdit.standard'),
    eventPos: null,

    initialize: function (element) {
        var _this=this;
        element.data('instance', this);
        this.$element = element;
        Glizy.events.on("app.SectionimgCreated", this.onCreated, _this);
        Glizy.events.on("glizy.formEdit2.repeat.openCollapsed", this.onOpenCollapsed, _this);
        Glizy.events.on("glizy.formEdit2.repeat.createdRepeat", this.onCreatedRepeat, _this);
    },

    destroy: function() {
        Glizy.events.unbind("app.SectionimgCreated");
        Glizy.events.unbind("glizy.formEdit2.repeat.openCollapsed");
        Glizy.events.unbind("glizy.formEdit2.repeat.createdRepeat");
        $("#sequence_number").off("change");
        $("#usage [name=groupID]").off("change");
    },

    onCreated: function(msg) {
        var _this = this;
        $("#sequence_number").on("change",function(){
            var ele = $(this);
            var seq = ele.val();
            if(seq<=0 || seq>self.medias.length){
                var myStack = {"dir1":"down", "dir2":"right", "push":"top"};
                $.pnotify({
                    title: "Attenzione",
                    text: "Puoi inserire un numero di sequenza compreso tra 1 e "+self.medias.length,
                    addclass: "stack-custom",
                    stack: myStack,
                    hide:true,
                    delay: 3000,
                    remove: true,
                    history: false
                })
                ele.val(self.mediaActive.toString());
            }
        })
    },

    onCreatedRepeat: function(e){
        var _this = this;
        var childIndex = e.message.childIndex;
        var selectGroupId = $("#undefined-usage"+childIndex+" [name=groupID]");
        var groupValues = self.sectionJson["img_group_objects"] ? self.sectionJson["img_group_objects"][selectGroupId.val()] : null;
        var value = {"groupID":selectGroupId.val(),"groupValues":groupValues};
        _this.manageMetadataFields(value,selectGroupId);
        selectGroupId.on("change",function(){
            var groupValues = self.sectionJson["img_group_objects"] ? self.sectionJson["img_group_objects"][$(this).val()] : null;
            var value = {"groupID":$(this).val(),"groupValues":groupValues};
            _this.manageMetadataFields(value,this);
        });
    },

    onOpenCollapsed: function(e){
        var _this = this;
        var childIndex = e.message.childIndex;
        var selectGroupId = $("#undefined-usage"+childIndex+" [name=groupID]");
        var groupValues = self.sectionJson["img_group_objects"] ? self.sectionJson["img_group_objects"][selectGroupId.val()] : null;
        var value = {"groupID":selectGroupId.val(),"groupValues":groupValues};
        _this.manageMetadataFields(value,selectGroupId);
        selectGroupId.on("change",function(){
            var groupValues = self.sectionJson["img_group_objects"] ? self.sectionJson["img_group_objects"][$(this).val()] : null;
            var value = {"groupID":$(this).val(),"groupValues":groupValues};
            _this.manageMetadataFields(value,this);
        });
    },

    manageMetadataFields: function(form,ele){
        var container = $(ele).closest(".GFERowContainer");
        try{
            var id = parseInt(container[0].id.replace( /^\D+/g, ''));
            var existGroupID = form.usage ? form.usage[id].groupID : form.groupID;
            if(existGroupID && form.groupValues){
                _.forEach(form.groupValues,function(value,key){
                    var field = $(container).find("[name="+key+"]");
                    if(field){
                        field.val(value);
                        field.attr("disabled",true);
                    }
                });
            }
            else{
                var fields = $(container).find("*[name]");
                _.forEach(fields,function(value,key){
                    $(value).attr("disabled",false);
                });
            }
        }
        catch(err){}
    }
});