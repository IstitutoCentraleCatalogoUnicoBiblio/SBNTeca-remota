Glizy.oop.declare("glizy.FormEdit.SectionAudio", {
    $extends: Glizy.oop.get('glizy.FormEdit.standard'),
    eventPos: null,

    initialize: function (element) {
        var _this=this;
        element.data('instance', this);
        this.$element = element;
        Glizy.events.on("app.SectionaudioCreated", this.onCreated, _this);
        Glizy.events.on("glizy.formEdit2.repeat.openCollapsed", this.onOpenCollapsed, _this);
        Glizy.events.on("glizy.formEdit2.repeat.createdRepeat", this.onCreatedRepeat, _this);
    },

    destroy: function() {
        Glizy.events.unbind("app.SectionaudioCreated");
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
        });
        var audioRepeater = $("div[id^=undefined-usage].GFERowContainer");
        _.forEach(audioRepeater,function(value){
            var id = value.id;
            var audioBitrate = $("#"+id+" [name=bitrate]").val() ? $("#"+id+" [name=bitrate]") : $("#"+id+" [name=bitpersample]").val() ? $("#"+id+" [name=bitpersample]") : null;
            if(audioBitrate)
                _this.manageBitFields(audioBitrate);
        });
    },

    onCreatedRepeat: function(e){
        var _this = this;
        var childIndex = e.message.childIndex;
        var selectGroupId = $("#undefined-usage"+childIndex+" [name=groupID]");
        var groupValues = self.sectionJson["audio_group_objects"] ? self.sectionJson["audio_group_objects"][selectGroupId.val()] : null;
        var value = {"groupID":selectGroupId.val(),"groupValues":groupValues};
        _this.manageMetadataFields(value,selectGroupId);
        selectGroupId.on("change",function(){
            var groupValues = self.sectionJson["audio_group_objects"] ? self.sectionJson["audio_group_objects"][$(this).val()] : null;
            var value = {"groupID":$(this).val(),"groupValues":groupValues};
            _this.manageMetadataFields(value,this);
        });
        var audioBitrate = $("#undefined-usage"+childIndex+" [name=bitrate], #undefined-usage"+childIndex+" [name=bitpersample]");
        audioBitrate.on("change",function(ev){
            _this.manageBitFields(this,ev);
        });
    },

    onOpenCollapsed: function(e){
        var _this = this;
        var childIndex = e.message.childIndex;
        var selectGroupId = $("#undefined-usage"+childIndex+" [name=groupID]");
        var groupValues = self.sectionJson["audio_group_objects"] ? self.sectionJson["audio_group_objects"][selectGroupId.val()] : null;
        var value = {"groupID":selectGroupId.val(),"groupValues":groupValues};
        _this.manageMetadataFields(value,selectGroupId);
        var selectBitField = $("#undefined-usage"+childIndex+" [name=bitrate]").val() ? $("#undefined-usage"+childIndex+" [name=bitrate]") : $("#undefined-usage"+childIndex+" [name=bitpersample]");
        _this.manageBitFields(selectBitField);
        selectGroupId.on("change",function(){
            var groupValues = self.sectionJson["audio_group_objects"] ? self.sectionJson["audio_group_objects"][$(this).val()] : null;
            var value = {"groupID":$(this).val(),"groupValues":groupValues};
            _this.manageMetadataFields(value,this);
        });
    },

    manageMetadataFields: function(form,ele){
        var _this = this;
        var container = $(ele).closest(".GFERowContainer");
        try{
            var id = parseInt(container[0].id.replace( /^\D+/g, ''));
            var existGroupID = form.usage ? form.usage[id].groupID : form.groupID;
            if(existGroupID && form.groupValues){
                var insertGroupValues = function(values){
                    _.forEach(values,function(value,key){
                        var field = $(container).find("[name="+key+"]");
                        if(field && value && typeof value === "object"){
                            return insertGroupValues(value);
                        }
                        if(field){
                            field.val(value);
                            field.attr("disabled",true);
                        }
                    });
                }
                insertGroupValues(form.groupValues);
            }
            else{
                var fields = $(container).find("*[name]");
                _.forEach(fields,function(value,key){
                    $(value).attr("disabled",false);
                });
            }
            var selectBitField = $(container).find("[name='bitrate']") ? $(container).find("[name='bitrate']") : $(container).find("[name='bitpersample']");
            _this.manageBitFields(selectBitField);
        }
        catch(err){}
    },

    manageBitFields: function(ele,ev){
        var container = $(ele).closest(".GFERowContainer");
        var fieldActive = $(ele);
        var fieldActiveName = $(ele).attr("name");
        var fieldActiveValue = $(ele).val();
        var fieldNoActive = fieldActiveName==="bitrate" ? $(container).find("[name='bitpersample']") : $(container).find("[name='bitrate']");
        if(fieldActiveValue){
            fieldNoActive.val("");
            fieldNoActive.attr("disabled",true);
        }
        else{
            fieldNoActive.attr("disabled",false);
            if(ev && ev.originalEvent && !self.detectChangeEvent){
                self.detectChangeEvent=true;
                formDataActive.invalidFields--;
                setTimeout(function(){
                    self.detectChangeEvent=false;
                },500);
            }
        }
    }
});