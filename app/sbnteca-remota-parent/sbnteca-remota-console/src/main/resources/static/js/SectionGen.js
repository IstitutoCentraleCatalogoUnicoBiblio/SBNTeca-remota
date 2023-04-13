Glizy.oop.declare("glizy.FormEdit.SectionGen", {
    $extends: Glizy.oop.get('glizy.FormEdit.standard'),
    eventPos: null,

    initialize: function (element) {
        var _this=this;
        element.data('instance', this);
        this.$element = element;
        Glizy.events.on("app.SectiongenCreated", this.onCreated, _this);
        Glizy.events.on("glizy.formEdit2.repeat.openCollapsed", this.onOpenCollapsed, _this);
        Glizy.events.on("glizy.formEdit2.repeat.createdRepeat", this.onCreatedRepeat, _this);
    },

    destroy: function() {
        Glizy.events.unbind("app.SectiongenCreated");
        Glizy.events.unbind("glizy.formEdit2.repeat.openCollapsed");
        Glizy.events.unbind("glizy.formEdit2.repeat.createdRepeat");
        $("#audio_group [name=bitrate], #audio_group [name=bitpersample]").off("change");
    },

    onCreated: function(msg) {
        var _this = this;
        var audioRepeater = $("div[id^=undefined-audio_group].GFERowContainer");
        _.forEach(audioRepeater,function(value){
            var id = value.id;
            var idLenght = value.id.split("-").length;
            if(idLenght===2){
                var audioBitrate = $("#"+id+" [name=bitrate]").val() ? $("#"+id+" [name=bitrate]") : $("#"+id+" [name=bitpersample]").val() ? $("#"+id+" [name=bitpersample]") : null;
                if(audioBitrate)
                    _this.manageBitFields(audioBitrate);
            }
        });
    },

    onCreatedRepeat: function(e){
        var _this = this;
        if(e.message.elementId==="audio_group"){
            var audioBitrate = $("#"+e.message.elementId+" [name=bitrate], #"+e.message.elementId+" [name=bitpersample]");
            for(var i=0;i<audioBitrate.length;i++){
                var ele = $(audioBitrate[i]);
                ele.on("change",function(ev){
                    _this.manageBitFields(this,ev);
                });
            }
        }
    },

    onOpenCollapsed: function(e){
        var _this = this;
        var childIndex = e.message.childIndex;
        var selectBitField = $("#undefined-audio_group"+childIndex+" [name=bitrate]").val() ? $("#undefined-audio_group"+childIndex+" [name=bitrate]") : $("#undefined-audio_group"+childIndex+" [name=bitpersample]");
        _this.manageBitFields(selectBitField);
    },

    manageMetadataFields: function(form,ele){
        console.log("qui")
        var _this = this;
        var container = $(ele).closest(".GFERowContainer");
        var selectBitField = $(container).find("[name='bitrate']") ? $(container).find("[name='bitrate']") : $(container).find("[name='bitpersample']");
        _this.manageBitFields(selectBitField);
    },

    detectChangeEvent:false,

    manageBitFields: function(ele,ev){
        var self = this;
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