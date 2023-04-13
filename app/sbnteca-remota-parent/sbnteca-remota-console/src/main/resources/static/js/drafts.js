var selectedDraft;
var MAX_INT = 2147483647;
var noProjects = false;

$(document).ready(function() {
	var timeout;
	
	$("#delete-confirm .modal-header .close").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
	});
	
	$("#delete-confirm #noBtn").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
	});
	
	$("#delete-confirm #yesBtn").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
		
    	$.ajax({
    		url: restUrl + "deleteDraft?id=" + selectedDraft,
    		type: "GET",
    		
    		success: function(jsonRes) {
    			if(jsonRes == true) {
    				alert("Bozza correttamente cancellata");
    				window.location.href = "drafts";
    			}
    			
    			else
    				alert("Bozza non cancellata");
    		},
    		error: function(obj, status, error) {
    			alert("HTTP " + obj.status + ": " + obj.responseText);
    		},
    		contentType: "application/json; charset=UTF-8",
    		dataType: "json"
    	});
	});
	
	$("#create-draft .modal-header .close").click(function() {
		$("#create-draft").addClass("hide").addClass("fade");
	});
	
	$("#create-draft-mets .modal-header .close").click(function() {
		$("#create-draft-mets").addClass("hide").addClass("fade");
	});

	$("#create-draft #noCreateBtn").click(function() {
		$("#create-draft").addClass("hide").addClass("fade");
	});
	
	$("#create-draft #yesCreateBtn").click(function() {
		$.ajax({
    		url: restUrl + "editor/create",
    		type: "GET",
			data: {
				"project": $("#draft_select_project").val(),
				"user": $("#userID").val(),
				"label": $("#draft_label").val()
			},
    		success: function(jsonRes) {
    			$("#create-draft").addClass("hide").addClass("fade");
    			
    			if(jsonRes.stato == true)
    				window.location.href = "editor?id=" + jsonRes.id;
    			
    			else
    				alert("Bozza non creata");
    		},
    		error: function(obj, status, error) {
    			$("#create-draft").addClass("hide").addClass("fade");
    			alert("HTTP " + obj.status + ": " + obj.responseText);
    		},
    		contentType: "application/json; charset=UTF-8",
    		dataType: "json"
    	});
		
	});
	
	$("#create-draft-mets #yesCreateMetsBtn").click(function() {
		$.ajax({
    		url: restUrl + "metsCreate",
    		type: "GET",
			data: {
				"project": $("#draft_select_project_mets").val(),
				"user": $("#userID").val(),
				"label": $("#draft_label_mets").val()
			},
    		success: function(jsonRes) {
    			$("#create-draft-mets").addClass("hide").addClass("fade");

    			if(jsonRes.stato == true)
    				window.location.href = "editorMets?id=" + jsonRes.id;

    			else
    				alert("Bozza non creata");
    		},
    		error: function(obj, status, error) {
    			$("#create-draft-mets").addClass("hide").addClass("fade");
    			alert("HTTP " + obj.status + ": " + obj.responseText);
    		},
    		contentType: "application/json; charset=UTF-8",
    		dataType: "json"
    	});

	});

	$("#editor-open-error .modal-header .close").click(function() {
		$("#editor-open-error").addClass("hide").addClass("fade");
	});
	
	$("#openErrorCloseBtn").click(function() {
		$("#editor-open-error").addClass("hide").addClass("fade");
	});
	
	$("#editor-no-projects .modal-header .close").click(function() {
		$("#editor-no-projects").addClass("hide").addClass("fade");
	});
	
	$("#noProjectsCloseBtn").click(function() {
		$("#editor-no-projects").addClass("hide").addClass("fade");
	});
	
	$("#noProjectsMetsCloseBtn").click(function() {
		$("#editor-no-projects").addClass("hide").addClass("fade");
	});

	$("#draftCreateBtnLink").click(function() {
		$("#" + (noProjects ? "editor-no-projects" : "create-draft")).removeClass("hide").removeClass("fade");
	});

	$("#draftCreateMetsBtnLink").click(function() {
		$("#" + (noProjects ? "editor-no-projects" : "create-draft-mets")).removeClass("hide").removeClass("fade");
	});

	$.ajax({
		url: restUrl + "projects?filter=all",
		type: "GET",
		success: function(values) {
			if(values.length == 0) {
				noProjects = true;
				return;
			}
			
			values.sort(function (a, b) {
			    return a.toLowerCase().localeCompare(b.toLowerCase());
			});
			
			for(i = 0; i < values.length; i++){
				$("#draft_select_project").append($('<option>', { value: values[i], text: values[i], selected: i == 0 }));
				$("#draft_select_project_mets").append($('<option>', { value: values[i], text: values[i], selected: i == 0 }));
			}
		},
		error: function(obj, status, error) {
			alert(status + "\n" + error);
		},
		contentType: "application/json; charset=UTF-8",
		dataType: "json"
	});
	
	$.ajax({
		url: restUrl + "searchMag?facetOnly&draft",
		type: "POST",
		data: JSON.stringify({ "ricerca": {} }),
		success: function(json) {
			$(".channel").each(function(index, element) {
				if($(element).prop("tagName") == "SELECT") {
					$("#" + element.id).append($('<option>', {value: "", text: ""}));
					
					for(i = 0; i < json.faccette.length; i++) {
						if(element.id == "channel_" + json.faccette[i].nome.replace("Facet", "")) {
							for(j = 0; j < json.faccette[i].valori.length; j++) {
								$("#" + element.id).append($('<option>', 
										{ 
											"value": json.faccette[i].valori[j].valore, 
											"text": json.faccette[i].valori[j].valore, 
											"title": json.faccette[i].valori[j].valore
										}));
							}
						}
					}
				}
			});
		},
		error: function(json) {
			alert(JSON.stringify(json, null, '\t'));
		},
		contentType: "application/json; charset=UTF-8",
		dataType: "json"
	});
	
	$("#searchBtn").click(function() {
		dataPost = { "ricerca" : {} };
		$("#blockDisplay").removeClass("hide").removeClass("fade");
		
		timeout = window.setInterval(function() {
			var txt = $('#blockText').text();
			var pointIdx = txt.indexOf('.');
			var originalText = txt.substring(0, pointIdx);
			var numPoints = (txt.length - pointIdx) % 3 + 1;
			var textNew = originalText;
			
			for(var i = 0; i < numPoints; i++)
				textNew += ".";
			
			$('#blockText').text(textNew);
		}, 1000);
		
		$(".channel").each(function(index, element) {
			values = $(element).val();
			
			if(values != null && values != undefined && values.length > 0 && values != '')
				dataPost.ricerca[element.id.replace("channel_", "")] = values;
		});
		
		$.ajax({
			url: restUrl + "searchDraft",
			type: "POST",
			data: JSON.stringify(dataPost),
			success: function(json) {
				data = [];
				
				for(i = 0; i < json.length; i++) {
					row = [
					       json[i].username,
					       json[i].id,
					       json[i].identifier,
					       json[i].year_part_number,
					       json[i].issue_part_name,
					       json[i].documentFormat,
					       json[i].lastModified,
							"<a title=\"Vai al contenitore\" href=\"vfs-container?idDirectory="+encodeURIComponent(json[i].project)+
								"&amp;idContainer="+encodeURIComponent(json[i].vfsId) +"\"> <i class=\"icon-folder-open btn-icon\"></i></a><div class=\"icon_title_table\"> Contenitore </div>" +
					       "<a onclick=\"editor('" + json[i].id + "','"+json[i].vfsId+"','"+json[i].documentFormat+"');\" title=\"Modifica la bozza '" + json[i].id + "'\">" +
				            		"<i class=\"icon-edit btn-icon\"></i></a><div class=\"icon_title_table\"> Modifica bozza </div>" +
					       
				           "<a onclick=\"cancel('" + json[i].id + "');\" title=\"Rimuovi la bozza '" + json[i].id + "'\">" +
				            		"<i class=\"icon-trash btn-icon\"></i></a><div class=\"icon_title_table\"> Cancella </div>"
					];
					
					data.push(row);
				}
				
				$("#draftTable").DataTable().destroy();
				
				$("#draftTable").DataTable({
					"autoWidth": false,
					"order": [],
					"destroy": true,
					"paging": true,
					"searching": false,
					"lengthChange": false,
					"pageLength": 30,
					"pagingType": "full_numbers",
					"retrieve": true,
					"data": data,
					"language": {
					    "decimal":        "",
					    "emptyTable":     "Non sono stati trovati risultati",
					    "info":           "Mostra da _START_ a _END_ risultati di _TOTAL_ totali",
					    "infoEmpty":      "Mostra 0 di 0 risultati",
					    "infoFiltered":   "",
					    "infoPostFix":    "",
					    "lengthMenu":     "Mostra _MENU_ risultati",
					    "loadingRecords": "Caricamento...",
					    "processing":     "Elaborazione...",
					    "search":         "Ricerca:",
					    "zeroRecords":    "La ricerca non ha prodotto risultati",
					    "paginate": {
					        "first":      "<<",
					        "last":       ">>",
					        "next":       ">",
					        "previous":   "<"
					    }
					},
					"columns": [
					            {"title": "Utente", "className": "sortable", "type": "string", "orderable": true},
					            {"title": "Bozza", "className": "sortable", "type": "string", "orderable": false},
					            {"title": "Identifier", "className": "sortable", "type": "string", "orderable": true},
					            {"title": "Anno/Numero parte", "className": "sortable", "type": "string", "orderable": true},
					            {"title": "Estremi fasc./Titolo parte", "className": "sortable", "type": "string", "orderable": true},
					            {"title": "Tipo", "className": "sortable", "type": "string", "orderable": true},
					            {"title": "Ultima modifica", "className": "sortable", "type": "string", "orderable": true},
					            {"title": "", "className": "sortable actions", "type": "html", "orderable": false}
					],              	
					"buttons": [ 
					    {"extend" : "csv", "title": "Bozze", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
					    {"extend" : "pdf", "title": "Bozze", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
					],
					"dom": 'frtipB',
					"rowCallback": function(row, data, index) {
						$(row).addClass("search_result_row");
					},
					"drawCallback": function(settings) {
						if(data.length == 0)
							$("#draftTable .dataTables_empty").attr('colspan', 7);
						
						$('#blockDisplay').addClass("hide").addClass("fade");
						window.clearInterval(timeout);
						$("#resultArea").css("display", "block");
	 				}
				});
			},
			error: function(json) {
				$('#blockDisplay').addClass("hide").addClass("fade");
				window.clearInterval(timeout);
				$("#resultArea").css("display", "block");
				alert(JSON.stringify(json, null, '\t'));
			},
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		});
	});

	$("#draftTable").DataTable({
		"autoWidth": false,
		"order": [],
		"destroy": true,
		"paging": true,
		"searching": false,
		"lengthChange": false,
		"pageLength": 30,
		"pagingType": "full_numbers",
		"retrieve": true,
		"data": [],
		"language": {
		    "decimal":        "",
		    "emptyTable":     "Non sono stati trovati risultati",
		    "info":           "Mostra da _START_ a _END_ risultati di _TOTAL_ totali",
		    "infoEmpty":      "Mostra 0 di 0 risultati",
		    "infoFiltered":   "",
		    "infoPostFix":    "",
		    "lengthMenu":     "Mostra _MENU_ risultati",
		    "loadingRecords": "Caricamento...",
		    "processing":     "Elaborazione...",
		    "search":         "Ricerca:",
		    "zeroRecords":    "La ricerca non ha prodotto risultati",
		    "paginate": {
		        "first":      "<<",
		        "last":       ">>",
		        "next":       ">",
		        "previous":   "<"
		    }
		},
		"columns": [
            {"title": "Utente", "className": "sortable", "type": "string", "orderable": true},
            {"title": "Bozza", "className": "sortable", "type": "string", "orderable": false},
            {"title": "Identifier", "className": "sortable", "type": "string", "orderable": true},
            {"title": "Anno/Numero parte", "className": "sortable", "type": "string", "orderable": true},
            {"title": "Estremi fasc./Titolo parte", "className": "sortable", "type": "string", "orderable": true},
            {"title": "Tipo", "className": "sortable", "type": "string", "orderable": true},
            {"title": "Ultima modifica", "className": "sortable", "type": "string", "orderable": true},
            {"title": "", "className": "sortable actions", "type": "html", "orderable": false}
		],              	
		"buttons": [ 
		    {"extend" : "csv", "title": "Bozze", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
		    {"extend" : "pdf", "title": "Bozze", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
		],
		"dom": 'frtipB',
		"rowCallback": function(row, data, index) {
			$(row).addClass("search_result_row");
		},
		"drawCallback": function(settings) {
			$("#draftTable .dataTables_empty").attr('colspan', 7);
		}
	});
});

function editor(draftID, vfsId, documentFormat) {
	$.ajax({
		url: restUrl + "editor/checkProject?id=" + draftID,
		type: "GET",
		success: function(result) {
			if(result)
				window.location.href = "editor"+("mets"==documentFormat?("Mets?id="+vfsId):("?id="+draftID));
			
			else
				$("#editor-open-error").removeClass("hide").removeClass("fade");
		},
		error: function(json) {
			alert(JSON.stringify(json, null, '\t'));
		},
		contentType: "application/json; charset=UTF-8",
		dataType: "json"
	});
}

function cancel(draftID) {
	$("#delete-confirm .modal-header h4").text("Cancella bozza '" + draftID + "'");
	selectedDraft = draftID;
	$("#delete-confirm").removeClass("hide").removeClass("fade");
}