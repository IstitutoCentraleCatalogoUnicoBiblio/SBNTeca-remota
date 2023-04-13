roleList = [];
var selectedIndex;
updateMode = false;
destroyTables = false;

$(document).ready(function() {
	$(".modal-header .close").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
	});
	
	$("#noBtn").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
	});

	$("#yesBtn").click(function() {
		role_id = roleList[selectedIndex].id;
		$("#delete-confirm").addClass("hide").addClass("fade");

    	$.ajax({
    		url: restUrl + "user/role/delete/" + role_id,
    		type: "GET",
    		
    		success: function(jsonRes) {
    			if(jsonRes.result == "OK")
    				window.location.href = "roles";
    			
    			else
    				$("#error").css("display", "block").text(jsonRes.message);
    		},
    		error: function(obj) {
    			$("#roles").css("display", "none");
				$("#error").css("display", "block").text("HTTP " + obj.status + ": " + obj.responseText);
    		},
    		contentType: "application/json; charset=UTF-8",
    		dataType: "json"
    	});
	});
	
	$("#createRole").click(function() {
		if($("#roleRightsArea").css("display") == 'none') {
			$(".single_box > img").css("display", "none");
			$("#createRole").css("display", "none");
			$("#roleRightsArea").css("display", "block");
			$("#roleTableArea").css("display", "none");
			updateMode = false;

			if(!destroyTables)
				destroyTables = true;
			
			else {
				$("#homeRights").DataTable().destroy();
				$("#searchRights").DataTable().destroy();
				$("#statsRights").DataTable().destroy();
				$("#otherRights").DataTable().destroy();
			}
			
			createRightsTables(defaultRoleCreate);
		}
	});
	
	$("#undoBtn").click(function() {
		$(".single_box > img").css("display", "inline-block");
		$("#createRole").css("display", "inline-block");
		$("#roleRightsArea").css("display", "none");
		$("#roleTableArea").css("display", "block");
		$("#homeRights").DataTable().destroy();
		$("#searchRights").DataTable().destroy();
		$("#otherRights").DataTable().destroy();
	});
	
	$("#saveBtn").click(function() {
		if(!updateMode && $("input[name='role_name']").val() == "") {
			$("#rolenameError").css("display", "inline-block").text("Nome ruolo non specificato");
			return;
		}
		
		path = "user/role/" + (updateMode ? "update" : "insert");
		
		postData = {
				"id": parseInt($("#role_id").val()),
				"nome": $("input[name='role_name']").val(),
				"gestione_upload": $("#role_upload").prop("checked"),
				"gestione_upload_aggiornamento": $("#role_upload_update").prop("checked"),
				"gestione_importazioni": $("#role_import").prop("checked"),
				"gestione_importazioni_aggiornamento": $("#role_update").prop("checked"),
				"gestione_cambio_usage": $("#role_change_usage").prop("checked"),
				"gestione_elimina_progetti": $("#role_delete_project").prop("checked"),
				"gestione_pannello_export": $("#role_export_panel").prop("checked"),
				"gestione_ricerche": $("#role_search").prop("checked"),
				"gestione_pubblicazioni": $("#role_publication").prop("checked"),
				"gestione_cancellazioni": $("#role_delete").prop("checked"),
				"gestione_normalizzazioni": $("#role_normalize").prop("checked"),
				"gestione_export": $("#role_export").prop("checked"),
				"gestione_draft": $("#role_draft").prop("checked"),
				"gestione_statistiche": $("#role_stats").prop("checked"),
				"gestione_statistiche_oggetti_digitali": $("#role_digital_object_stats").prop("checked"),
				"gestione_utenti": $("#role_users").prop("checked"),
				"gestione_ruoli": $("#role_roles").prop("checked"),
				"gestione_oaiset": $("#role_oaiset").prop("checked")
			};
		
		$.ajax({
			url: restUrl + path,
			type: "POST",
			data: JSON.stringify(postData),
			success: function(jsonRes) {
				if(jsonRes.result == "KO")
					$("#rolenameError").css("display", "inline-block").text(jsonRes.message);
				
				else {
					$("#rolenameError").css("display", "none");
					window.location.href = "roles";
				}
			},
			error: function(obj) {
				$("#roles").css("display", "none");
				$("#error").css("display", "block").text("HTTP " + obj.status + ": " + obj.responseText);
			},
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		});
		
	});
	
	$.ajax({
		url: restUrl + "user/role/all",
		type: "GET",
		
		success: function(jsonRes) {
			data = [];
			roleList = jsonRes;
			
			for(i = 0; i < roleList.length; i++) {
				role = roleList[i];
				
				data[i] = [ 
		            role.nome,
		            		
		            "<a onclick=\"update(" + i + ");\" title=\"Aggiorna il ruolo utente '" + role.nome + "'\">" +
		            		"<i class=\"icon-pencil btn-icon\"></i></a><div class=\"icon_title_table\"> Modifica </div>" + 
		            		
		            "<a onclick=\"cancel(" + i + ");\" title=\"Rimuovi il ruolo utente '" + role.nome + "'\">" +
		            		"<i class=\"icon-trash btn-icon\"></i></a><div class=\"icon_title_table\"> Cancella </div>"
				];
			}
			
			$("#roles").DataTable({
				"autoWidth": false,
				"order": [],
				"paging": true,
				"lengthChange": false,
				"pageLength": 10,
				"pagingType": "full_numbers",
				"data": data,
				"language": {
				    "decimal":        "",
				    "emptyTable":     "Ruoli non presenti",
				    "info":           "Mostra da _START_ a _END_ risultati di _TOTAL_ totali",
				    "infoEmpty":      "Mostra 0 di 0 risultati",
				    "infoFiltered":   "",
				    "infoPostFix":    "",
				    "lengthMenu":     "Mostra _MENU_ risultati",
				    "loadingRecords": "Caricamento ...",
				    "processing":     "Elaborazione ...",
				    "search":         "Filtra: ",
				    "zeroRecords":    "La ricerca non ha prodotto risultati",
				    "paginate": {
				        "first":      "<<",
				        "last":       ">>",
				        "next":       ">",
				        "previous":   "<"
				    }
				},
				"columns": [
		            {"title": "Nome Ruolo", "className": "sortable", "type": "string"},
		            {"title": "", "className": "sortable actions", "type": "html", "searchable": false, "orderable": false},
				],
				"buttons": [ 
				    {"extend" : "csv", "title": "Ruoli", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
				    {"extend" : "pdf", "title": "Ruoli", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
				],
				"dom": 'f<"deleteSearch">rtipB'
			});
			
			$("div.deleteSearch").html("<a class=\"clickable_icon\" onclick=\"$('.dataTables_filter input').val('').trigger('keyup');\">" +
					"<i class=\"icon-remove btn-icon\"></i></a>");
		},
		error: function(obj) {
			$("#roles").css("display", "none");
			$("#error").css("display", "block").text("HTTP " + obj.status + ": " + obj.responseText);
		},
		contentType: "application/json; charset=UTF-8",
		dataType: "json"
	});
});

function cancel(index) {
	$(".modal-header h4").text("Cancella ruolo '" + roleList[index].nome + "'");
	selectedIndex = index;
	$("#delete-confirm").removeClass("hide").removeClass("fade");
}

function update(index) {
	if($("#roleRightsArea").css("display") == "block") {
		$("#roleRightsArea").css("display", "none");
		$("#roleTableArea").css("display", "block");
		
	}
	
	if(!destroyTables)
		destroyTables = true;
	
	else {
		$("#homeRights").DataTable().destroy();
		$("#searchRights").DataTable().destroy();
		$("#statsRights").DataTable().destroy();
		$("#otherRights").DataTable().destroy();
	}
	
	$(".single_box > img").css("display", "none");
	$("#createRole").css("display", "none");
	$("#roleRightsArea").css("display", "block");
	$("#roleTableArea").css("display", "none");
	updateMode = true;
	createRightsTables(roleList[index]);
}

function createRightsTables(defaultRole) {
	$("#role_id").val(defaultRole.id);
	
	
	if(updateMode)
		$("#role_name").val(defaultRole.nome).prop("disabled", true);
	
	homeData = [];
	
	
	homeData.push([
	            "<input type=\"checkbox\" id=\"role_upload\" name=\"role_upload\" " + (defaultRole.gestione_upload ? "checked=\"checked\" " : "") + "/>",
	            "<input type=\"checkbox\" id=\"role_upload_update\" name=\"role_upload_update\" " + (defaultRole.gestione_upload_aggiornamento ? "checked=\"checked\" " : "") + "/>",
	            "<input type=\"checkbox\" id=\"role_import\" name=\"role_import\" " + (defaultRole.gestione_importazioni ? "checked=\"checked\" " : "") + "/>",
	            "<input type=\"checkbox\" id=\"role_update\" name=\"role_update\" " + (defaultRole.gestione_importazioni_aggiornamento ? "checked=\"checked\" " : "") + "/>",
	            "<input type=\"checkbox\" id=\"role_change_usage\" name=\"role_change_usage\" " + (defaultRole.gestione_cambio_usage ? "checked=\"checked\" " : "") + "/>",
	            "<input type=\"checkbox\" id=\"role_delete_project\" name=\"role_delete_project\" " + (defaultRole.gestione_elimina_progetti ? "checked=\"checked\" " : "") + "/>",
	            "<input type=\"checkbox\" id=\"role_export_panel\" name=\"role_export_panel\" " + (defaultRole.gestione_pannello_export ? "checked=\"checked\" " : "") + "/>"
	            ]);
	
	$("#homeRights").DataTable({
		"autoWidth": false,
		"order": [],
		"paging": false,
		"searching": false,
		"ordering": false,
		"info": false,
		"lengthChange": false,
		"data": homeData,
		"columns": [
            {"title": "Upload Nuovo Progetto", "className": "sortable", "type": "html", "searchable": false, "orderable": false},
            {"title": "Upload Aggiornamento", "className": "sortable", "type": "html", "searchable": false, "orderable": false},
            {"title": "Importa Progetto", "className": "sortable", "type": "html", "searchable": false, "orderable": false},
            {"title": "Aggiorna Progetto", "className": "sortable", "type": "html", "searchable": false, "orderable": false},
            {"title": "Cambio Usage", "className": "sortable", "type": "html", "searchable": false, "orderable": false},
            {"title": "Cancella Progetto", "className": "sortable", "type": "html", "searchable": false, "orderable": false},
            {"title": "Pannello Esporta", "className": "sortable", "type": "html", "searchable": false, "orderable": false}
		]
	});
	
	searchData = [];
	
	searchData.push([ 
				"<input type=\"checkbox\" id=\"role_draft\" name=\"role_draft\" " + (defaultRole.gestione_draft ? "checked=\"checked\" " : "") + "/>",
	            "<input type=\"checkbox\" id=\"role_search\" name=\"role_search\" " + (defaultRole.gestione_ricerche ? "checked=\"checked\" " : "") + "/>",
	            "<input type=\"checkbox\" id=\"role_publication\" name=\"role_publication\" " + (defaultRole.gestione_pubblicazioni ? "checked=\"checked\" " : "") + "/>",
	            "<input type=\"checkbox\" id=\"role_delete\" name=\"role_delete\" " + (defaultRole.gestione_cancellazioni ? "checked=\"checked\" " : "") + "/>",
	            "<input type=\"checkbox\" id=\"role_normalize\" name=\"role_normalize\" " + (defaultRole.gestione_normalizzazioni ? "checked=\"checked\" " : "") + "/>",
	            "<input type=\"checkbox\" id=\"role_export\" name=\"role_export\" " + (defaultRole.gestione_export ? "checked=\"checked\" " : "") + "/>"
	            ]);
	
	$("#searchRights").DataTable({
		"autoWidth": false,
		"order": [],
		"paging": false,
		"searching": false,
		"ordering": false,
		"info": false,
		"lengthChange": false,
		"data": searchData,
		"columns": [
			{"title": "Bozze", "className": "sortable", "type": "html", "searchable": false, "orderable": false},
            {"title": "Consulta", "className": "sortable", "type": "html", "searchable": false, "orderable": false},
            {"title": "Pubblica", "className": "sortable", "type": "html", "searchable": false, "orderable": false},
            {"title": "Elimina", "className": "sortable", "type": "html", "searchable": false, "orderable": false},
            {"title": "Normalizza", "className": "sortable", "type": "html", "searchable": false, "orderable": false},
            {"title": "Esporta", "className": "sortable", "type": "html", "searchable": false, "orderable": false}
		]
	});
	
	statsData = [];
	
	statsData.push([ 
		"<input type=\"checkbox\" id=\"role_stats\" name=\"role_stats\" " + (defaultRole.gestione_statistiche ? "checked=\"checked\" " : "") + "/>",
        "<input type=\"checkbox\" id=\"role_digital_object_stats\" name=\"role_digital_object_stats\" " + (defaultRole.gestione_statistiche_oggetti_digitali ? "checked=\"checked\" " : "") + "/>"
		]);
	
	$("#statsRights").DataTable({
		"autoWidth": false,
		"order": [],
		"paging": false,
		"searching": false,
		"ordering": false,
		"info": false,
		"lengthChange": false,
		"data": statsData,
		"columns": [
            {"title": "Elaborati Statistici", "className": "sortable", "type": "html", "searchable": false, "orderable": false},
            {"title": "Oggetti Digitali", "className": "sortable", "type": "html", "searchable": false, "orderable": false}
		]
	});
	
	otherData = [];
	
	otherData.push([ 
	            "<input type=\"checkbox\" id=\"role_users\" name=\"role_users\" " + (defaultRole.gestione_utenti ? "checked=\"checked\" " : "") + "/>",
	            "<input type=\"checkbox\" id=\"role_roles\" name=\"role_roles\" " + (defaultRole.gestione_ruoli ? "checked=\"checked\" " : "") + "/>",
	            "<input type=\"checkbox\" id=\"role_oaiset\" name=\"role_oaiset\" " + (defaultRole.gestione_oaiset ? "checked=\"checked\" " : "") + "/>",
	            ]);
	
	$("#otherRights").DataTable({
		"autoWidth": false,
		"order": [],
		"paging": false,
		"searching": false,
		"ordering": false,
		"info": false,
		"lengthChange": false,
		"data": otherData,
		"columns": [
            {"title": "Utenti", "className": "sortable", "type": "html", "searchable": false, "orderable": false},
            {"title": "Ruoli", "className": "sortable", "type": "html", "searchable": false, "orderable": false},
            {"title": "Sets", "className": "sortable", "type": "html", "searchable": false, "orderable": false}
		]
	});
}