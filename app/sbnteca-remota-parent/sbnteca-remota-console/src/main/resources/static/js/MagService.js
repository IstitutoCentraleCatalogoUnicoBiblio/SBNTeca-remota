Glizy.oop.declare("MagService", {
    config: null,

    initialize: function (config) {
        this.config = config;

        Glizy.events.on("magservice.projectMediaPickerCaller", this.onProjectMediaPickerCaller, this);
        Glizy.events.on("magservice.projectMediaPickerCallerChild", this.onProjectMediaPickerCallerChild, this);
        Glizy.events.on("magservice.getBibDataFromIdentifierCaller", this.onGetBibDataFromIdentifierCaller, this);
    },

    ajaxCaller: function(data, callback) {
        var _this = this;
        var section = data.section;
        var json = null;
        $.ajax(data.url+"?id="+data.magId+"&sections="+section)
            .done(function(result) {
                console.log("success");
                json = result;
            })
            .fail(function() {
                console.log("error");
            })
            .always(function() {
                console.log("complete");
                callback(json);
            });
    },

    onProjectMediaPickerCaller: function(e) {
        this.projectMediaPickerCaller(e.message.data, function(json){
            console.log("onProjectMediaPickerCaller");
            Glizy.events.broadcast("magservice.projectMediaPickerCaller_res", {sender: e.message.sender, response: json});
        });
    },

    onProjectMediaPickerCallerChild: function(e) {
        this.projectMediaPickerCaller(e.message.data, function(json){
            console.log("onProjectMediaPickerCallerChild");
            Glizy.events.broadcast("magservice.projectMediaPickerCallerChild_res", {sender: e.message.sender, response: json});
        });
    },

    // http://localhost/AnalizzatoreMag/rest/resources?project=<progetto>[&depth=<livello_profondità>][&rel=<path_relativo>][&filter=img|audio|video|ocr|doc][&prefix=<prefisso_autocomplete_risorse>]
    projectMediaPickerCaller: function(data, callback) {
        var _this = this;
        var json = null;
        $.ajax(data.url+"?project="+data.project+"&filter="+data.filter)
            .done(function(result) {
                console.log("success");
                json = result;
            })
            .fail(function() {
                console.log("error");
            })
            .always(function() {
                console.log("complete");
                callback(json);
            });
    },

    // http://localhost/AnalizzatoreMag/rest/resource/info?rel=<path_relativo>
    mediaSimpleMetadataCaller: function(data, callback) {
        var _this = this;
        var json = null;
        $.ajax(data.url+"?rel="+data.rel)
            .done(function(result) {
                console.log("success");
                json = result;
            })
            .fail(function() {
                console.log("error");
            })
            .always(function() {
                console.log("complete");
                callback(json);
            });
    },

    // http://localhost/AnalizzatoreMag/rest/editor/content/resource?id=<mag_id>&type=img|audio|video|ocr|doc&sequence_number=<numero_sequenza>[&usage=<usage_richiesto>]
    mediaExtendedMetadataCaller: function(data, callback) {
        var _this = this;
        var json = null;
        $.ajax(data.url+"?id="+data.id+"&type="+data.type+"&sequence_number="+data.seq)
            .done(function(result) {
                console.log("success");
                json = {'usage': [], 'groupID': ''};
                _.each(result, function(usage) {
                    json['usage'].push(usage);
                    json['groupID'] = usage.groupID;
                });
            })
            .fail(function() {
                console.log("error");
            })
            .always(function() {
                console.log("complete");
                callback(json);
            });
    },

    // http://localhost/AnalizzatoreMag/rest/editor/save?section=<sezione>&user=<user_id>&[id=<mag_id>| project=<progetto>]
    saveMagCaller: function(data, callback) {
        var _this = this;
        var json = null;
        $.ajax({
            method: 'POST',
            url: data.url+"?section="+data.section+"&user="+data.user+"&id="+data.magId,
            data: JSON.stringify(data.data),
            contentType: "application/json"
        })
            .done(function(result) {
                console.log("success");
                json = result;
            })
            .fail(function(error) {
                console.log("error");
                json = error;
            })
            .always(function() {
                console.log("complete");
                callback(json);
            });
    },

    onGetBibDataFromIdentifierCaller: function(e) {
        this.getBibDataFromIdentifierCaller(e.message.data, function(json){
            console.log("onGetBibDataFromIdentifierCaller");
            Glizy.events.broadcast("sectionBib.getBibDataFromIdentifierCaller_res", {response: json});
        });
    },

    // http://localhost/AnalizzatoreMag/rest/resources?project=<progetto>[&depth=<livello_profondità>][&rel=<path_relativo>][&filter=img|audio|video|ocr|doc][&prefix=<prefisso_autocomplete_risorse>]
    getBibDataFromIdentifierCaller: function(data, callback) {
        var _this = this;
        var res = null;
        $.ajax(data.url+"?identifier="+data.identifier)
            .done(function(result) {
                console.log("success");
                res=result;
            })
            .fail(function() {
                console.log("error");
            })
            .always(function() {
                console.log("complete");
                callback(res);
            });
    },

    convertFormat: function(data) {
        var _this = this;
        var conversion = {};
        _.each(data, function(value, key) {
            if (_.isArray(value)) {
                conversion[key] = [];
                _.each(value, function(v) {
                    if (_.isObject(v)) {
                        conversion[key].push(_this.convertFormat(v));
                    } else {
                        var objValue = {};
                        objValue[key] = v;
                        conversion[key].push(objValue);
                    }
                });
            } else {
                conversion[key] = value;
            }
        });
        return conversion;
    }
});