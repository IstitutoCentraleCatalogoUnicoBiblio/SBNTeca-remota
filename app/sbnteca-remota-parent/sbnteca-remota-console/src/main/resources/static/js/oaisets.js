oaisetList = [];
var selectedID;

$(document).ready(function() {
	$(".modal-header .close").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
	});
	
	$("#noBtn").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
	});
	
	$("#yesBtn").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
		
		$.ajax({
    		url: oaiUrl + "oaiset/" + selectedID,
    		type: "DELETE",
    		
    		success: function(jsonRes) {
    			if(jsonRes.status == "ok")
    				window.location.href = "oaisets";
    			
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
			
			if($("#oaiset_profile_mets_simple").prop("checked"))
				profiles.push({ "name": $("#oaiset_profile_mets_simple").val() });

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
	
	if(window.location.pathname.indexOf("oaisets") >= 0) {
		$.ajax({
			url: oaiUrl + "oaiset",
			type: "GET",
			
			success: function(jsonRes) {
				data = [];
				oaisetList = jsonRes;
				
				for(i = 0; i < oaisetList.length; i++) {
					oaiset = oaisetList[i];
					
					data[i] = [ 
			            oaiset.name,
			            oaiset.spec,
			            oaiset.project,
			            
			            "<a title=\"Vedi i documenti per l'OAI Set '" + oaiset.name + "'\" " +
			            		"href=\"" + oaiHandler + "?verb=ListIdentifiers&metadataPrefix="+oaiset.profiles[0].name+"&set=" + oaiset.name + "\">" +
			            		"<i class=\"icon-file btn-icon\"></i></a><div class=\"icon_title_table\"> Info </div>" +
			            
			            "<form id=\"updateForm_" + i + "\" style=\"display: inline\" method=\"POST\">" +
			            		"<input type=\"hidden\" id=\"oaiset_" + i + "\" name=\"oaiset\" />" +
			            		"<a onclick=\"updateOaiset(" + i + ");\" title=\"Aggiorna l'OAI Set '" + oaiset.name + "'\">" +
			            		"<i class=\"icon-pencil btn-icon\"></i></a></form><div class=\"icon_title_table\"> Modifica </div>" +
			            
			            "<a onclick=\"deleteOaiset(" + i + ");\" title=\"Rimuovi l'OAI Set '" + oaiset.name + "'\">" +
			            		"<i class=\"icon-trash btn-icon\"></i></a><div class=\"icon_title_table\"> Cancella </div>"
					];
				}
				
				$("#oaisetTable").DataTable({
					"autoWidth": false,
					"order": [],
					"paging": true,
					"lengthChange": false,
					"pageLength": 30,
					"pagingType": "full_numbers",
					"data": data,
					"language": {
					    "decimal":        "",
					    "emptyTable":     "Sets non presenti",
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
			            {"title": "Identificativo", "className": "sortable", "type": "string"},
			            {"title": "Progetto", "className": "sortable", "type": "string"},
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
				$("#oaisetTable").css("display", "none");
				$("#error").css("display", "block").text("HTTP " + obj.status + ": " + obj.responseText);
			},
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		});
	}
});

function deleteOaiset(index) {
	$(".modal-header h4").text("Cancella OAI set '" + oaisetList[index].id + "'");
	selectedID = oaisetList[index].id;
	$("#delete-confirm").removeClass("hide").removeClass("fade");
}

function updateOaiset(index) {
	$("#oaiset_" + index).val(JSON.stringify(oaisetList[index]));
	$("#updateForm_" + index).submit();
}