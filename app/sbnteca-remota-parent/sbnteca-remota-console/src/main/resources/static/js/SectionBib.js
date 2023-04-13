Glizy.oop.declare("glizy.FormEdit.SectionBib", {
    $extends: Glizy.oop.get('glizy.FormEdit.standard'),
    eventPos: null,
    self:null,

    initialize: function (element) {
        var _this = this;
        element.data('instance', this);
        this.$element = element;
        self.formData = this.formData;
        this.eventPos = Glizy.events.on("app.SectionbibCreated", this.onCreated, _this);
        Glizy.events.on("glizy.formEdit2.repeat.createdRepeat", this.onCreatedRepeat, _this);
        this.templateDefine();
        self.manageLevel = this.manageLevel;
        self.getBibDataFromIdentifier = this.getBibDataFromIdentifier;
        self.initIdentifierReq = this.initIdentifierReq;
        Glizy.events.on("sectionBib.getBibDataFromIdentifierCaller_res", this.getBibDataFromIdentifierCaller_res, _this);
    },

    destroy: function() {
        Glizy.events.unbind("app.SectionbibCreated", this.eventPos);
    },

    getBibDataFromIdentifier: function(id){
        Glizy.events.broadcast("magservice.getBibDataFromIdentifierCaller", {data: {url: app_config.restServerSbnRequest, identifier: encodeURI(id)}});
    },

    getBibDataFromIdentifierCaller_res: function(res){
        var data = res.message.response;
        var actualData = self.formData;
        _.each(data,function(value,key){
            if(!actualData[key] || actualData[key].length===0){
                actualData[key]=value;
            }
        });
        var ajaxData = {
            "section":"bib",
            "bib":actualData,
        };
        Glizy.events.broadcast("app.loadSection",ajaxData);
    },

    onCreated: function(e) {
        if(e && e.message)
            self.formData = e.message.form;
        var _this = this;
        var body = $('body');
        $("#SectionBib").prop("disabled", true);
        var boxHelp = $("#piece [name='stpiece_per'], #piece [name='stpiece_vol']");
        _.forEach(boxHelp,function(value,key){
            var name = $(value).attr("name");
            var containerHelp = $(value).parent();
            var helpEle = $("<span data-pdf='"+name+"' class='icon icon-info-sign GFEIconInfo GFERightIcon'></span>");
            helpEle.on("click",function(){
                var name = $(this).attr("data-pdf");
                var info = Glizy.template.render('sectionbib.'+name);
                var options = {
                    "title":"Info",
                    "modal": true,
                    open: function() {
                        var markup = info;
                        $(this).html(markup);
                    },
                    "dialogClass":"mediapickerInfoDialog",
                    "width":"90%",
                    "position": { my: "top", at: "top", of: window },
                    "draggable": false,
                    "close": function( event, ui ) {
                        //$( "#mediapicker-tree" ).dialog( "destroy" );
                    }
                };
                setTimeout(function(){
                    Glizy.openDialog(info,options);
                },200);
            })
            containerHelp.append(helpEle);
        });
        self.initIdentifierReq();
        self.manageLevel();

    },

    onCreatedRepeat: function(e){
        var _this = this;
        var boxHelp = $("#piece [name='stpiece_per'], #piece [name='stpiece_vol']");
        _.forEach(boxHelp,function(value,key){
            var name = $(value).attr("name");
            var containerHelp = $(value).parent();
            var helpEle = $("<span data-pdf='"+name+"' class='icon icon-info-sign GFEIconInfo GFERightIcon'></span>");
            helpEle.on("click",function(){
                var name = $(this).attr("data-pdf");
                var info = Glizy.template.render('sectionbib.'+name);
                var options = {
                    "title":"Info",
                    "modal": true,
                    open: function() {
                        var markup = info;
                        $(this).html(markup);
                    },
                    "dialogClass":"mediapickerInfoDialog",
                    "width":"90%",
                    "position": { my: "top", at: "top", of: window },
                    "draggable": false,
                    "close": function( event, ui ) {
                        //$( "#mediapicker-tree" ).dialog( "destroy" );
                    }
                };
                setTimeout(function(){
                    Glizy.openDialog(info,options);
                },200);
            })
            containerHelp.append(helpEle);
        });
        self.initIdentifierReq();
        self.manageLevel();
    },

    initIdentifierReq: function(){
        var boxBib = $("#SectionBib");
        if(boxBib.length<1)
            return;
        var boxIden = $("#identifiers [name='identifier']");
        _.forEach(boxIden,function(value,key){
            var name = $(value).attr("name");
            var containerIden = $(value).parent();
            var helpIden = $("<span class='GFEIconInfo GFERightIcon'><i class='fa fa-cloud-download'></i></span>");
            helpIden.on("click",function(){
                self.getBibDataFromIdentifier($(this).prevAll('input').val());
            });
            containerIden.append(helpIden);
        });
    },

    manageLevel: function(){
        var body = $('body');
        var currentLevel = $('#level').val();
        var pieceLegend = $("#piece legend")[0];
        if (currentLevel == 's') {
            $(pieceLegend).html("Seriali")
            $("input[name='year']").closest('div .control-group').show();
            $("input[name='issue']").closest('div .control-group').show();
            $("input[name='stpiece_per']").closest('div .control-group').show();
            $("input[name='part_number']").closest('div .control-group').hide();
            $("input[name='part_number']").prop("disabled", true);
            $("input[name='part_name']").closest('div .control-group').hide();
            $("input[name='part_name']").prop("disabled", true);
            $("input[name='stpiece_vol']").closest('div .control-group').hide();
            $("input[name='stpiece_vol']").prop("disabled", true);
        } else {
            $(pieceLegend).html("Unità componenti")
            $("input[name='year']").closest('div .control-group').hide();
            $("input[name='year']").prop("disabled", true);
            $("input[name='issue']").closest('div .control-group').hide();
            $("input[name='issue']").prop("disabled", true);
            $("input[name='stpiece_per']").closest('div .control-group').hide();
            $("input[name='stpiece_per']").prop("disabled", true);
            $("input[name='part_number']").closest('div .control-group').show();
            $("input[name='part_name']").closest('div .control-group').show();
            $("input[name='stpiece_vol']").closest('div .control-group').show();
        }
        body.on('change', '#level', (function (e) {
            if (e.target.value == 's') {
                $(pieceLegend).html("Seriali")
                $("input[name='year']").closest('div .control-group').show();
                $("input[name='year']").prop("disabled", false);
                $("input[name='issue']").closest('div .control-group').show();
                $("input[name='issue']").prop("disabled", false);
                $("input[name='stpiece_per']").closest('div .control-group').show();
                $("input[name='stpiece_per']").prop("disabled", false);
                $("input[name='part_number']").val('');
                $("input[name='part_number']").closest('div .control-group').hide();
                $("input[name='part_number']").prop("disabled", true);
                $("input[name='part_name']").val('');
                $("input[name='part_name']").closest('div .control-group').hide();
                $("input[name='part_name']").prop("disabled", true);
                $("input[name='stpiece_vol']").val('');
                $("input[name='stpiece_vol']").closest('div .control-group').hide();
                $("input[name='stpiece_vol']").prop("disabled", true);
            } else {
                $(pieceLegend).html("Unità componenti")
                $("input[name='year']").val('');
                $("input[name='year']").closest('div .control-group').hide();
                $("input[name='year']").prop("disabled", true);
                $("input[name='issue']").val('');
                $("input[name='issue']").closest('div .control-group').hide();
                $("input[name='issue']").prop("disabled", true);
                $("input[name='stpiece_per']").val('');
                $("input[name='stpiece_per']").closest('div .control-group').hide();
                $("input[name='stpiece_per']").prop("disabled", true);
                $("input[name='part_number']").closest('div .control-group').show();
                $("input[name='part_number']").prop("disabled", false);
                $("input[name='part_name']").closest('div .control-group').show();
                $("input[name='part_name']").prop("disabled", false);
                $("input[name='stpiece_vol']").closest('div .control-group').show();
                $("input[name='stpiece_vol']").prop("disabled", false);
            }
        }));
        $("input#piece-addRowBtn").on('click', '', function() {
            if ($('#level').val() == 's') {
                $(pieceLegend).html("Seriali")
                $("input[name='year']").closest('div .control-group').show();
                $("input[name='year']").prop("disabled", false);
                $("input[name='issue']").closest('div .control-group').show();
                $("input[name='issue']").prop("disabled", false);
                $("input[name='stpiece_per']").closest('div .control-group').show();
                $("input[name='stpiece_per']").prop("disabled", false);
                $("input[name='part_number']").val('');
                $("input[name='part_number']").closest('div .control-group').hide();
                $("input[name='part_number']").prop("disabled", true);
                $("input[name='part_name']").val('');
                $("input[name='part_name']").closest('div .control-group').hide();
                $("input[name='part_name']").prop("disabled", true);
                $("input[name='stpiece_vol']").val('');
                $("input[name='stpiece_vol']").closest('div .control-group').hide();
                $("input[name='stpiece_vol']").prop("disabled", true);
            } else {
                $(pieceLegend).html("Unità componenti")
                $("input[name='year']").val('');
                $("input[name='year']").closest('div .control-group').hide();
                $("input[name='year']").prop("disabled", true);
                $("input[name='issue']").val('');
                $("input[name='issue']").closest('div .control-group').hide();
                $("input[name='issue']").prop("disabled", true);
                $("input[name='stpiece_per']").val('');
                $("input[name='stpiece_per']").closest('div .control-group').hide();
                $("input[name='stpiece_per']").prop("disabled", true);
                $("input[name='part_number']").closest('div .control-group').show();
                $("input[name='part_number']").prop("disabled", false);
                $("input[name='part_name']").closest('div .control-group').show();
                $("input[name='part_name']").prop("disabled", false);
                $("input[name='stpiece_vol']").closest('div .control-group').show();
                $("input[name='stpiece_vol']").prop("disabled", false);
            }
        });
    },

    templateDefine: function(){
        Glizy.template.define('sectionbib.stpiece_vol',
            "<div id=\"pieceInfoDialog-stpiece_vol\"><p>Gli indici si indicano con \"*\"<br/><i>Indice dell’annata 1990 --> (1990)*</i></p><p>Tuttavia se l'indice esce come fascicolo numerato entro la numerazione regolare del periodico, l'indice è trattato come un regolare fascicolo.</p></div>"
        );
        Glizy.template.define('sectionbib.stpiece_per',
            "<div id=\"pieceInfoDialog-stpiece_per\"><p>La sintassi fa riferimento allo standard SICI (ANSI/NISO Z39.56) per i segmenti <b>Chronology, Enumeration e Supplements and Indexes</b> (http://www.niso.org/standards/standard_detail.cfm?std_id=530).</p><p>Il risultato si presenta come: <i>(cronologia)livello_numerazione:livello_numerazione:livello_numerazione:livello_numerazione</i></p> <p>Regole:</p> <ol> <li><b>Cronologia:</b><br/>Tutte le date sono registrate numericamente fra parentesi tonde secondo il formato YYYYMMDD (YYYY = anno, MM = mese, DD = giorno). Si usano solo i livelli applicabili. Per esempio, se la data da registrare non ha mese e giorno o stagione, si registra solo l'anno. I codici da adottare sono:<br/> <ul>Codici di cronologia<br/> <li>01 = gennaio</li> <li>02 = febbraio</li> <li>03 = marzo</li> <li>04 = aprile</li> <li>05 = maggio</li> <li>06 = giugno</li> <li>07 = luglio</li> <li>08 = agosto</li> <li>09 = settembre</li> <li>10 = ottobre</li> <li>11 = novembre</li> <li>12 = dicembre</li> <li>21 = Primavera</li> <li>22 = Estate</li> <li>23 = Autunno</li> <li>24 = Inverno</li> <li>31 = primo quarto</li> <li>32 = secondo quarto</li> <li>33 = terzo quarto</li> <li>34 = quarto quarto</li></ul> <i>Es: La Repubblica 23 gennaio 2005 -> (20050123)<br/> Airone febbraio 2003 -> (200302)<br/> Renaissance Quarterly, 2° quarto 2004 -> (200432)</i></li> <li><b>Cronologia combinata:</b><br/> Si usa la barra \"/\" nella cronologia per una combinazione di date o di parti di data<br/> Esempi:<br/> <i>(199312/199401)20:2 per Dicembre 93 / Gennaio 94 volume 20, n. 2<br/> (119021/22)17:3/4 per Primavera Estate 1990 volume 17 numero 3/4</i><br/> Se manca la cronologia la punteggiatura \"()\" è comunque obbligatoria.</li> <li><b>Numerazione:</b><br/> La numerazione identifica il fascicolo. Vi possono essere più livelli (con un massimo di 4) separati da due punti \":\" Può essere omessa - senza segnalare l'omissione - se il fascicolo non presenta questa tipologia di informazioni. Per esempio:<br/> <i>La Repubblica 23 gennaio 2005, anno 24, n. 23 --> (20050123)24:23<br/> Airone febbraio 2003, anno 18 n. 2 --> (200302)18:2<br/> Renaissance Quarterly, 2° quarto 2004, anno 36 parte 2 --> (200432)36:2.</i></li> <li><b>Numerazione combinata e numerazione continua:</b><br/> Nella numerazione si può usare la barra \"/\" per una numerazione combinata.<br/> (119021/22)17:3/4 per Primavera Estate 1990 volume 17 numero 3/4.<br/> Se vi sono due tipologie di numerazione si preferisce quella regolare<br/> volume:numero e in questo caso non si tiene conto della numerazione continua dei fascicoli.<br/> Vol 21, n. 13 (fasc 389) 23 giugno 1995 viene codificato in (11950623)21:13<br/> e non si tiene conto di \"fasc 389\"<br/> Se un periodico presenta solo una numerazione progressiva dei fascicoli senza alcuna indicazione cronologica e senza alcuna indicazione di fascicolo il risultato sarà, ad esempio, ()454 per indicare il fascicolo numero 454.</li> <li><b>Supplementi e indici:</b><br/> Un supplemento al fascicolo si indica con il \"+\"<br/> <i>(19950910)+ per Supplemento del 10 settembre 1995 non riferito a un particolare<br/> numero<br/> (198408)21:8+ per Supplemento al Volume 21 numero 8, Agosto 1984</i><br/> Gli indici si indicano con \"*\"<br/> <i>Indice dell'annata 1990 --> (1990)*</i><br/> Tuttavia se l'indice esce come fascicolo numerato entro la numerazione regolare del periodico, l'indice è trattato come un regolare fascicolo.</li></ol></div>"   
        );
    }
});