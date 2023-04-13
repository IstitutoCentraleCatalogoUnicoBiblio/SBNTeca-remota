oaisetList = [];
containerList = [];
var selectedID;
var directoryOpen = "";
var containerOpen = "";
var vfsTypeOpen;
var deleteType ="";

$(document).ready(function() {
	$(".modal-header .close").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
       	$("#modifyContainer").addClass("hide").addClass("fade");
	});


	$("#noBtn").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
	});

	$("#yesBtn").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
		if(deleteType=="container"){
			$.ajax({
				url: restUrl + "vfs/container?"
								+ $.param({"id": selectedID}),
				type: "DELETE",
				success: function(jsonRes) {
					if(jsonRes.stato == 0)
						openDirectory(directoryOpen);
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
    	}
    	if(deleteType=="directory"){
			$.ajax({
				url: restUrl + "vfs/directory?"
								+ $.param({"id": selectedID}),
				type: "DELETE",
				success: function(jsonRes) {
					if(jsonRes.stato == 0)
						loadDirectories();
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
    	}
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

	if(window.location.pathname.indexOf("vfs") >= 0) {
		loadDirectories();
		if(new URLSearchParams(window.location.search).has("idDirectory")){
         	openDirectory(new URLSearchParams(window.location.search).get("idDirectory"));
        }
	}
});

function loadDirectories(){
		$.ajax({
			url: restUrl + "vfs/directories",
			type: "GET",
			success: function(jsonRes) {
				data = [];
				items = jsonRes;

				for(i = 0; i < items.length; i++) {
					item = items[i];

					data[i] = [
			            item.id,
			            item.label,
			            item.timestamp,
			            item.note,

			            "<a title=\"Vedi i contenitori per la cartella '" + item.id + "'\" " +
			            		" onclick=\'openDirectory(\"" + item.id + "\",\""+item.label+"\")\'>" +
			            		"<i class=\"icon-folder-open btn-icon\"></i></a><div class=\"icon_title_table\"> Info </div>" +

			            "<form id=\"updateForm_" + i + "\" style=\"display: inline\" method=\"POST\">" +
			            		"<input type=\"hidden\" id=\"oaiset_" + i + "\" name=\"oaiset\" />" +
			            		"<a onclick='createModContainer(\"" + item.id + "\",\"directory\");' title=\"Aggiorna la cartella '" + item.id + "'\">" +
			            		"<i class=\"icon-pencil btn-icon\"></i></a></form><div class=\"icon_title_table\"> Modifica </div>" +

			            "<a onclick='deleteDirectory(\"" + item.id + "\");' title=\"Rimuovi cartella '" + item.id + "'\">" +
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
					    "emptyTable":     "Nessuna cartella presente",
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
			            {"title": "Nome", "className": "sortable", "type": "string"},
			            {"title": "Label", "className": "sortable", "type": "string"},
			            {"title": "Data", "className": "sortable", "type": "string"},
			            {"title": "Nota", "className": "sortable", "type": "string"},
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


function deleteDirectory(index) {
	deleteType = "directory";
	$(".modal-header h4").text("Cancella cartella '" + index + "'");
	selectedID = index;
	$("#delete-confirm").removeClass("hide").removeClass("fade");
}

function updateOaiset(index) {
	$("#oaiset_" + index).val(JSON.stringify(oaisetList[index]));
	$("#updateForm_" + index).submit();
}


function openDirectory(idDirectory, label){
		directoryOpen = idDirectory;
		$("#newContainerGroup").css("display", "block");
		$.ajax({
			url: restUrl + "vfs/containers",
			type: "GET",
			data:{
				"rows": 10,
				"filterUpload": "true",
				"idDirectory": idDirectory
			},
			success: function(jsonRes) {
				data = [];
				containerList = jsonRes;
				/*
				for(i = 0; i < containerList.length; i++) {
					container = containerList[i];

					data[i] = [
						container.id,
			            container.id,
			            container.label,
			            container.resourceType,
			            ((""+container.draft)=="true")?"<img src=\"images/draft_icon.jpg\" alt='draft' height='26px' width='26px'/>":"No",
			            container.timestamp,
						(container.idPublic?
						(
						"<a target='_blank' href=\""+ restUrl.replace("/rest/","/") + "iiif/v3/manifestById.json?id="+encodeURIComponent(container.idPublic)+"\">Manifest</a> "
						+ "<a  target='_blank' title='Vedi in Mirador' href=\"https://projectmirador.org/embed/?manifest="+
							encodeURIComponent(restUrl.replace("/rest/","/") + "iiif/v3/manifestById.json?id="+encodeURIComponent(container.idPublic))+
						"\"><i class=\"icon-eye-open btn-icon\" title=\"Vedi in Mirador\"></i></a> "
						):"")+
			            "<a title=\"Vedi i contenitori per la cartella '" + container.id + "'\" " +
			            		" href=\"vfs-container?idDirectory="+idDirectory+"&idContainer=" + encodeURIComponent(container.id) + "\")\'>" +
			            		"<i class=\"icon-folder-open btn-icon\"></i></a><div class=\"icon_title_table\"> Info </div>" +

			            "<form id=\"updateForm_" + i + "\" style=\"display: inline\" method=\"POST\">" +
			            		"<input type=\"hidden\" id=\"oaiset_" + i + "\" name=\"oaiset\" />" +
			            		"<a onclick='createModContainer(\"" + container.id + "\",\"container\");' title='Aggiorna il contenitore \"" + container.id + "\"'>" +
			            		"<i class=\"icon-pencil btn-icon\"></i></a></form><div class=\"icon_title_table\"> Modifica </div>" +

			            "<a onclick=\"deleteContainer(" + i + ");\" title=\"Rimuovi contenitore '" + container.name + "'\">" +
			            		"<i class=\"icon-trash btn-icon\"></i></a><div class=\"icon_title_table\"> Cancella </div>"
					];
				}
				*/
				labelDirectory = idDirectory;
//				if("undefined"!=""+label)
//					labelDirectory = label;
				$("#labelDirectory").text("Contenitori della cartella: "+labelDirectory);
 				if ($.fn.dataTable.isDataTable('#containersTable')) {
					$('#containersTable').DataTable().clear();
					$('#containersTable').DataTable().destroy();
				}
				$("#containersTable").DataTable({
					"autoWidth": false,
					"order": [],
					"serverSide": true,
					"ajax": restUrl + "vfs/containersDataTable?idDirectory="+encodeURIComponent(idDirectory),
					"paging": true,
					"lengthChange": false,
					"pageLength": 10,
					"pagingType": "full_numbers",
					"data": data,
					"language": {
					    "decimal":        "",
					    "emptyTable":     "Nessuna cartella presente",
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
                            },
						{
                                "render": function ( data, type, row ) {
                                    //return "< img src=images/sort_asc.png>"
                                    if("true"==row[4])
                                    	return "<img src=\"images/draft_icon.jpg\" alt='draft' height='26px' width='26px'/>";
                                    else
                                    	return "No";
                                },
                                "targets": 4
                            },
							{
									"render": function ( data, type, row ) {
										//return "HI"+row[6];
										var publicId = row[6];
										var idC = row[0];
										var ret =
											(publicId?
												(
												"<a target='_blank' href=\""+ restUrl.replace("/rest/","/") + "iiif/v3/manifestById.json?id="+encodeURIComponent(publicId)+"\">Manifest</a> "
												+ "<a  target='_blank' title='Vedi in Mirador' href=\"https://projectmirador.org/embed/?manifest="+
													encodeURIComponent(restUrl.replace("/rest/","/") + "iiif/v3/manifestById.json?id="+encodeURIComponent(publicId))+
												"\"><i class=\"icon-eye-open btn-icon\" title=\"Vedi in Mirador\"></i></a> "
												):"")+
												"<a title=\"Vedi gli oggetti del contenitore '" + idC + "'\" " +
														" href=\"vfs-container?idDirectory="+encodeURIComponent(idDirectory)+"&idContainer=" + encodeURIComponent(idC) + "\")\'>" +
														"<i class=\"icon-folder-open btn-icon\"></i></a><div class=\"icon_title_table\"> Info </div>" +

												"<form id=\"updateForm_" + i + "\" style=\"display: inline\" method=\"POST\">" +
														"<input type=\"hidden\" id=\"oaiset_" + idC + "\" name=\"oaiset\" />" +
														"<a onclick='createModContainer(\"" + idC + "\",\"container\");' title='Aggiorna il contenitore \"" + idC + "\"'>" +
														"<i class=\"icon-pencil btn-icon\"></i></a></form><div class=\"icon_title_table\"> Modifica </div>" +

												"<a onclick='deleteContainerById(\"" + idC + "\");' title=\"Rimuovi contenitore '" + idC + "'\">" +
														"<i class=\"icon-trash btn-icon\"></i></a><div class=\"icon_title_table\"> Cancella </div>";
										return ret;
									},
									"targets": 6
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
function deleteContainer(index) {
	deleteType = "container";
	$(".modal-header h4").text("Cancella contenitore '" + containerList[index].id + "'");
	selectedID = containerList[index].id;
	$("#delete-confirm").removeClass("hide").removeClass("fade");
}

function deleteContainerById(id) {
	deleteType = "container";
	$(".modal-header h4").text("Cancella contenitore '" + id + "'");
	selectedID = id;
	$("#delete-confirm").removeClass("hide").removeClass("fade");
}
