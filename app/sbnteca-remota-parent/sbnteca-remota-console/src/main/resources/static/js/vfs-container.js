itemList = [];
objectList = [];
containerList = [];
var labelContainerText;
var selectedID;
var directoryOpen = "";
var containerOpen = "";
var deleteType ="";


$(document).ready(function() {
// preventing page from redirecting
    $("html").on("dragover", function(e) {
        e.preventDefault();
        e.stopPropagation();
        var dragText = "Trascina qui";
        if($("#labelUpload").text()!=dragText)
        	labelContainerText = $("#labelUpload").text();
        $("#labelUpload").text(dragText);
    });

    $("html").on("drop", function(e) {
    	e.preventDefault();
    	e.stopPropagation();
    	$("#labelUpload").text(labelContainerText);
    });

	// drag enter
	$('#labelUpload').on('dragenter', function (e) {
		e.stopPropagation();
		e.preventDefault();
		$("#labelUpload").text("Drop");
	});

	// Drag over
	$('#labelUpload').on('dragover', function (e) {
		e.stopPropagation();
		e.preventDefault();
		$("#labelUpload").text("Drop");
	});

	// Drop
	$('#labelUpload').on('drop', function (e) {
		e.stopPropagation();
		e.preventDefault();
        $("#labelUpload").text("Caricamento in corso..");
		var file = e.originalEvent.dataTransfer.files;
		for(var i = 0; i< file.length; i++){
	        var fd = new FormData();
    	    fd.append('idContainer', new URLSearchParams(window.location.search).get("idContainer"))
        	fd.append('file', file[i]);
        	uploadData(fd);
        }
	});


	$(".modal-header .close").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
		$("#editor-confirm").addClass("hide").addClass("fade");
       	$("#modifyContainer").addClass("hide").addClass("fade");
       	$("#mets-create-confirm").addClass("hide").addClass("fade");
	});

	$("#noBtn").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
	});

	$("#noBtnMets").click(function() {
		$("#mets-create-confirm").addClass("hide").addClass("fade");
	});

	$("#noBtnMag").click(function() {
		$("#mag-create-confirm").addClass("hide").addClass("fade");
	});

	$("#yesBtnMets").click(function() {
		console.log("Creazione Mets");
		createMetsOfContainerConfirmed(metsId);
		$("#mets-create-confirm").addClass("hide").addClass("fade");
	});

	$("#yesBtnMag").click(function() {
		console.log("Creazione Mag");
		createMagOfContainerConfirmed(magId);
		$("#mag-create-confirm").addClass("hide").addClass("fade");
	});

	$("#yesBtn").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");

		$.ajax({
    		url: restUrl + "vfs/"+deleteType+"?"
    			+ $.param({"id": selectedID}),
    		type: "DELETE",
    		success: function(jsonRes) {
    			if(jsonRes.stato == 0)
//    				window.location.href = location.protocol
//    				+ '//' + location.host + location.pathname+"?idDirectory="+directoryOpen;
    				window.location.reload();
    			else
    				$("#error").css("display", "block").text(jsonRes.message);
    		},
    		error: function(obj, status, error) {
    			$("#oaisetTable").css("display", "none");
				$("#error").css("display", "block").text("HTTP " + obj.status + ": " + obj.responseText);
    		},
    		contentType: "application/json; charset=UTF-8",
    		dataType: "json"
    	});
	});

	$("#editor-confirm #yesBtnEdit").click(function() {
		$("#editor-confirm").addClass("hide").addClass("fade");
		var add = selectedDocumentFormat=="mets"?"Mets":"";
		window.location.href = "editor"+add+"?id=" + selectedMagID;
	});

	$("#noBtnEdit").click(function() {
		$("#editor-confirm").addClass("hide").addClass("fade");
	});

	$("#oaiset_name").keyup(function() {
		if($("#oaiset_name_error").css("display") != "none") {
			$("#oaiset_name_error").css("display", "none");
			$("#oaiset_name_error").parent().removeClass("error");
		}
	});

	$("#oaiset_spec").keyup(function() {
		if($("#oaiset_spec_error").css("display") != "none") {
			$("#oaiset_spec_error").css("display", "none");
			$("#oaiset_spec_error").parent().removeClass("error");
		}
	});

	$("#saveBtn").click(function() {
		errors = false;

		if($("#oaiset_name").val() == null || $("#oaiset_name").val().length == 0 ||  $("#oaiset_name").val() == undefined) {
			errors = true;
			$("#oaiset_name_error").parent().addClass("error");
			$("#oaiset_name_error").css("display", "inline-block");
		}

		if($("#oaiset_spec").val() == null || $("#oaiset_spec").val().length == 0 ||  $("#oaiset_spec").val() == undefined) {
			errors = true;
			$("#oaiset_spec_error").parent().addClass("error");
			$("#oaiset_spec_error").css("display", "inline-block");
		}

		if(!errors) {
			id = $("#oaiset_id").val();

			if(id == "" || id == undefined)
				id = -1;

			profiles = [];

			if($("#oaiset_profile_mag").prop("checked"))
				profiles.push({ "name": $("#oaiset_profile_mag").val() });

			if($("#oaiset_profile_mets").prop("checked"))
				profiles.push({ "name": $("#oaiset_profile_mets").val() });

			if($("#oaiset_profile_oai_dc").prop("checked"))
				profiles.push({ "name": $("#oaiset_profile_oai_dc").val() });

			data = {
					"id": id,
					"name": $("#oaiset_name").val(),
					"spec": $("#oaiset_spec").val(),
					"description_it": $("#oaiset_description_it").val(),
					"description_en": $("#oaiset_description_en").val(),
					"project": $("#oaiset_project").val(),
					"servletName": "OAIHandlerDati",
					"type": null,
					"solrquery": $("#oaiset_solrquery").val(),
					"limiters" : [],
					"profiles": profiles,
					"values": [],
					"constants": [],
					"fields": []
			}

			$.ajax({
				url: oaiUrl + "oaiset",
				type: "POST",
				data: JSON.stringify(data),
				success: function(jsonRes) {
					if(jsonRes.status == "ok")
						window.location.href = "oaisets";

					else
						$("#error").css("display", "block").text(jsonRes.message);
				},
				error: function(obj) {
					$("#oaisetTable").css("display", "none");
					$("#error").css("display", "block").text("HTTP " + obj.status + ": " + obj.responseText);
				},
				contentType: "application/json; charset=UTF-8",
				dataType: "json"
			});
		}
	});
	var isLoadContainers = true;
	if(window.location.pathname.indexOf("vfs") >= 0) {
		if(isLoadContainers){
			loadContainers();
		}
	}
	if(new URLSearchParams(window.location.search).has("idContainer")){
		openContainer(new URLSearchParams(window.location.search).get("idContainer"));
	}
});

function loadContainers(){
			if(new URLSearchParams(window.location.search).has("idDirectory")){
				directoryOpen = new URLSearchParams(window.location.search).get("idDirectory");
			}
			$.ajax({
			url: restUrl + "vfs/containers",
			type: "GET",
			data:{
				"rows": 100000,
				"filterUpload": "true",
				"idDirectory": new URLSearchParams(window.location.search).get("idDirectory"),
				"idContainer": new URLSearchParams(window.location.search).get("idContainer")+""
			},
			success: function(jsonRes) {
				data = [];
				itemList = jsonRes;
				containerList = jsonRes;
				for(i = 0; i < itemList.length; i++) {
					item = itemList[i];

					data[i] = [
						item.id,
			            item.id,
			            item.label,
			            item.resourceType,
			            ((""+item.draft)=="true")?"<img src=\"images/draft_icon.jpg\" alt='draft' height='26px' width='26px'/>":"No",
			            item.timestamp,
						(item.idPublic?
						(
						"<a target='_blank' href=\""+ restUrl.replace("/rest/","/") + "iiif/v3/manifestById.json?id="+encodeURIComponent(item.idPublic)+"\">Manifest</a> "
						+ "<a  target='_blank' title='Vedi in Mirador' href=\"https://projectmirador.org/embed/?manifest="+
							encodeURIComponent(restUrl.replace("/rest/","/") + "iiif/v3/manifestById.json?id="+encodeURIComponent(item.idPublic))+
						"\"><i class=\"icon-eye-open btn-icon\" title=\"Vedi in Mirador\"></i></a> "
						+			            "<a title=\"Visualizza la scheda di dettaglio di '" + item.idPublic + "'\" " +
                         			            		" href='magDetail?type="+item.resourceType+"&amp;id=" + encodeURIComponent(item.idPublic) + "'>" +
                         			            		"<i class=\"icon-list btn-icon\"></i></a><div class=\"icon_title_table\"> METS </div>"

						):"")+
			            (("mets"!=item.resourceType)?("<a title=\"Crea o ricrea il MAG del contenitore '" + item.id + "' con le risorse associate\" " +
			            		" onclick=\'createMagOfContainer(\"" + item.id + "\")\'>" +
			            		"<i class=\"icon-plus btn-icon\"></i></a><div class=\"icon_title_table\"> MAG </div>"):"") +
			            (("mag"!=item.resourceType)?("<a title=\"Crea o ricrea il METS del contenitore '" + item.id + "' con le risorse associate\" " +
			            		" onclick=\'createMetsOfContainer(\"" + item.id + "\")\'>" +
			            		"<i class=\"icon-plus btn-icon\"></i></a><div class=\"icon_title_table\"> METS </div>"):"") +
			            (("mag"==item.resourceType||"mets"==item.resourceType)?("<a onclick=\"editor('" + item.idPublic + "','"+item.resourceType+"','"+item.draft+"');\" title=\"Modifica "+item.resourceType+"\">" +
                        						   			"<i class=\"icon-edit btn-icon\"></i></a><div class=\"icon_title_table\"> Modifica "+item.resourceType+" </div>"):"")+
//			            "<a title=\"Modifica del contenitore '" + item.id + "' tramite editore METS (diventando bozza)\" " +
//			            		" href='editorMets?id=" + encodeURIComponent(item.id) + "'>" +
//			            		"<i class=\"icon-pencil btn-icon\"></i></a><div class=\"icon_title_table\"> METS </div>" +
			            ((!new URLSearchParams(window.location.search).has("idContainer"))?("<a title=\"Vedi le risorse del contenitore '" + item.id + "'\" " +
			            		" onclick=\'openContainer(\"" + item.id + "\")\'>" +
			            		"<i class=\"icon-folder-open btn-icon\"></i></a><div class=\"icon_title_table\"> Info </div>"):"") +

			            "<form id=\"updateForm_" + i + "\" style=\"display: inline\" method=\"POST\">" +
			            		"<input type=\"hidden\" id=\"oaiset_" + i + "\" name=\"oaiset\" />" +
			            		"<a onclick='createModContainer(\"" + item.id + "\", \"container\");' title=\"Aggiorna la label del contenitore '" + item.id + "'\">" +
			            		"<i class=\"icon-pencil btn-icon\"></i></a></form><div class=\"icon_title_table\"> Modifica </div>" +
			            "<a onclick=\"deleteContainer(" + i + ");\" title=\"Rimuovi il contenitore '" + item.id + "'\">" +
			            		"<i class=\"icon-trash btn-icon\"></i></a><div class=\"icon_title_table\"> Cancella </div>"
					];
				}

 				if ($.fn.dataTable.isDataTable('#vfsTable')) {
					$('#vfsTable').DataTable().clear();
					$('#vfsTable').DataTable().destroy();
				}
				$("#vfsTable").DataTable({
					"autoWidth": false,
					"order": [],
					"paging": true,
					"lengthChange": false,
					"pageLength": 5,
					"pagingType": "full_numbers",
					"data": data,
					"language": {
					    "decimal":        "",
					    "emptyTable":     "Nessun contenitore presente",
					    "info":           "Mostra da _START_ a _END_ risultati di _TOTAL_ totali",
					    "infoEmpty":      "Mostra 0 di 0 risultati",
					    "infoFiltered":   "",
					    "infoPostFix":    "",
					    "lengthMenu":     "Mostra _MENU_ risultati",
					    "loadingRecords": "Caricamento...",
					    "processing":     "Elaborazione...",
					    "search":         "Filtra:",
					    "zeroRecords":    "La ricerca non ha prodotto risultati",
					    "paginate": {
					        "first":      "<<",
					        "last":       ">>",
					        "next":       ">",
					        "previous":   "<"
					    }
					},
					"columns": [
						{"title": "", "className": "sortable", "type": "string"},
			            {"title": "Id", "className": "sortable", "type": "string"},
			            {"title": "Label", "className": "sortable", "type": "string"},
			            {"title": "Tipo", "className": "sortable", "type": "string"},
			            {"title": "Draft", "className": "sortable", "type": "string"},
			            {"title": "Data", "className": "sortable", "type": "string"},
			            {"title": "", "className": "sortable actions", "type": "html", "searchable": false, "orderable": false}
					],
					"columnDefs":[
						{
                                "render": function ( data, type, row ) {
                                    //return "< img src=images/sort_asc.png>"
                                    if(row[3])
                                    	return '('+ row[3] +')';
                                    else
                                    	return "No";
                                },
                                "targets": 0
                            }
					],
					"buttons": [
					    {"extend" : "csv", "title": "OAI_Sets", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
					    {"extend" : "pdf", "title": "OAI_Sets", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
					],
					"dom": 'f<"deleteSearch">rtipB'
				});

				$("div.deleteSearch").html("<a onclick=\"$('.dataTables_filter input').val('').trigger('keyup');\"><i class=\"icon-remove btn-icon\"></i></a>");
			},
			error: function(obj) {
				$("#vfsTable").css("display", "none");
				$("#error").css("display", "block").text("HTTP " + obj.status + ": " + obj.responseText);
			},
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		});
}

function deleteObject(index) {
	$(".modal-header h4").text("Cancella oggetto digitale '" + objectList[index].id + "'");
	selectedID = objectList[index].id;
	deleteType = "resource";
	$("#delete-confirm").removeClass("hide").removeClass("fade");
}

function updateOaiset(index) {
	$("#oaiset_" + index).val(JSON.stringify(oaisetList[index]));
	$("#updateForm_" + index).submit();
}

function openContainer(idContainer){
		containerOpen = idContainer;
		$.ajax({
			url: restUrl + "vfs/resources",
			type: "GET",
			data: {
				"rows": 1000000,
				"idContainer": idContainer
			},
			success: function(jsonRes) {
				data = [];
				objectList = jsonRes;

				for(i = 0; i < objectList.length; i++) {
					object_digital = objectList[i];

					data[i] = [
			            object_digital.id,
			            ("true"==object_digital.used)?"<i class=\"icon-lock btn-icon\"></i>":"<i class=\"icon-unlock btn-icon\"></i>",
			            object_digital.label,
			            object_digital.timestamp,
			            object_digital.contentType,
			            object_digital.usage,
			            ("true"==object_digital.frontespizio?"F":"")+
			            "<img src='"+restUrl.replace("/rest/","/")+"digitalObject/"+
                                    			            			object_digital.id+"?cache=false&mode=preview&w=45&h=45'>" +
			            " <a title=\"Vedi l'oggetto digitale '" + object_digital.id + "'\" " +
			            		" target='blank' href='"+restUrl.replace("/rest/","/")+"digitalObject/"+
			            			object_digital.id+"'>" +
			            		"<i class=\"icon-eye-open btn-icon\"></i></a><div class=\"icon_title_table\"> Info </div>" +

			            "<form id=\"updateForm_" + i + "\" style=\"display: inline\" method=\"POST\">" +
			            		"<input type=\"hidden\" id=\"oaiset_" + i + "\" name=\"oaiset\" />" +
			            		"<a onclick='createModContainer(\"" + object_digital.id + "\",\"object\");' title=\"Aggiorna l'oggetto digitale '" + object_digital.id + "'\">" +
			            		"<i class=\"icon-pencil btn-icon\"></i></a></form><div class=\"icon_title_table\"> Modifica </div>" +
			            "<a onclick=\"deleteObject(" + i + ");\" title=\"Rimuovi oggetto digitale '" + object_digital.id + "'\">" +
			            		"<i class=\"icon-trash btn-icon\"></i></a><div class=\"icon_title_table\"> Cancella </div>"
					];
				}

 				if ($.fn.dataTable.isDataTable('#containersTable')) {
					$('#containersTable').DataTable().clear();
					$('#containersTable').DataTable().destroy();
				}
				//$("#labelContainer").text("Oggetti digitali del contenitore: "+idContainer);
				$("#labelContainer").text("Oggetti digitali del contenitore");
				$("#containersTable").DataTable({
					"autoWidth": false,
					"order": [],
					"paging": true,
					"lengthChange": false,
					"pageLength": 10,
					"pagingType": "full_numbers",
					"data": data,
					"language": {
					    "decimal":        "",
					    "emptyTable":     "Nessun oggetto digitale presente",
					    "info":           "Mostra da _START_ a _END_ risultati di _TOTAL_ totali",
					    "infoEmpty":      "Mostra 0 di 0 risultati",
					    "infoFiltered":   "",
					    "infoPostFix":    "",
					    "lengthMenu":     "Mostra _MENU_ risultati",
					    "loadingRecords": "Caricamento...",
					    "processing":     "Elaborazione...",
					    "search":         "Filtra:",
					    "zeroRecords":    "La ricerca non ha prodotto risultati",
					    "paginate": {
					        "first":      "<<",
					        "last":       ">>",
					        "next":       ">",
					        "previous":   "<"
					    }
					},
					"columns": [
			            {"title": "Id", "className": "sortable", "type": "string","visible": false},
			            {"title": "In uso", "className": "sortable", "type": "string"},
			            {"title": "Label", "className": "sortable", "type": "string"},
			            {"title": "Data", "className": "sortable", "type": "string"},
			            {"title": "Content-type", "className": "sortable", "type": "string"},
			            {"title": "Usage", "className": "sortable", "type": "string"},
			            {"title": "", "className": "sortable actions", "type": "html", "searchable": false, "orderable": false}
					],
					"buttons": [
					    {"extend" : "csv", "title": "OAI_Sets", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
					    {"extend" : "pdf", "title": "OAI_Sets", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
					],
					"dom": 'f<"deleteSearch">rtipB'
				});

				$("div.deleteSearch").html("<a onclick=\"$('.dataTables_filter input').val('').trigger('keyup');\"><i class=\"icon-remove btn-icon\"></i></a>");
			},
			error: function(obj) {
				$("#vfsTable").css("display", "none");
				$("#error").css("display", "block").text("HTTP " + obj.status + ": " + obj.responseText);
			},
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		});
}

function uploadData(formData){
	console.log("Upload");
    $.ajax({
 		url: restUrl + "vfs/upload",
        type: 'post',
        data: formData,
        contentType: false,
        processData: false,
        dataType: 'json',
        success: function(response){
            if(response.stato == 0){
 				if(new URLSearchParams(window.location.search).has("idContainer")){
 					openContainer(new URLSearchParams(window.location.search).get("idContainer"));
			 	}
			 	$("#labelUpload").text("Caricato.");
           }
            else{
            	$("#error").css("display", "block").text("HTTP " + response.stato + ": " + response.message);
            }
        },
        error: function(obj) {
        	$("#error").css("display", "block").text("HTTP " + obj.status + ": " + obj.responseText);
        }
    });
}



function editor(magID, documentFormat, isDraft) {
    if("true"==isDraft){
		var add = documentFormat=="mets"?"Mets":"";
		window.location.href = "editor"+add+"?id=" + magID+"&draft=true";
    }
    else if("null"==""+magID){
    	if(documentFormat=="mets")
    		alert("Il contenitore non è indicizzato, crea il record con il pulsante '+'!");
    	else
    		alert("Il contenitore non è indicizzato, importa il record!");
    }
    else{
		$.ajax({
			url: restUrl + "editor/checkProject?id=" + magID,
			type: "GET",
			success: function(result) {
				if(result) {
					$("#editor-confirm .modal-header h4").text("Modalità Editor MAG/METS '" + magID + "'");
					selectedMagID = magID;
					selectedDocumentFormat = documentFormat;
					$("#editor-confirm").removeClass("hide").removeClass("fade");
				}
				else {
					$("#editor-open-error").removeClass("hide").removeClass("fade");
				}
			},
			error: function(json) {
				alert(JSON.stringify(json, null, '\t'));
			},
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		});
	}
}

function deleteContainer(index) {
	deleteType = "container";
	$(".modal-header h4").text("Cancella contenitore '" + containerList[index].id + "'");
	selectedID = containerList[index].id;
	$("#delete-confirm").removeClass("hide").removeClass("fade");
}
