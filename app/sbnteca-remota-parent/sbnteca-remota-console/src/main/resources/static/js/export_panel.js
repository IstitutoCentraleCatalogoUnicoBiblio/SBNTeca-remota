var completed;
var selectedIndex;

$(document).ready(function() {
	$(".modal-header .close").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
	});
	
	$("#noBtn").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
	});
	
	$("#yesBtn").click(function() {
		$.ajax({
    		url: restUrl + "export/delete/" + completed[selectedIndex].nome_file,
    		type: "GET",
    		success: function(jsonRes) {
    			$("#delete-confirm").addClass("hide").addClass("fade");
    			alert("Cancellazione export '" + completed[selectedIndex].nome_file + "' " + (jsonRes ? "eseguita" : "non riuscita"));
				window.location.reload(true);
    		},
    		error: function(obj, status, error) {
    			alert(obj.responseText);
    		},
    		contentType: "application/json; charset=UTF-8",
    		dataType: "json"
    	});
	});
	
	$.ajax({
		url: restUrl + "export/completed",
		success: function(jsonRes) {
			data = [];
			completed = jsonRes;
			
			for(i = 0; i < jsonRes.length; i++) {
				data.push([
				           jsonRes[i].nome_file,
				           jsonRes[i].username,
				           "<a href=\"" + restUrl + "export/download/" + jsonRes[i].nome_file + "\" title=\"Download '" + jsonRes[i].nome_file + "'\"><i class=\"icon-download btn-icon\"></i></a><div class=\"icon_title_table\"> Download </div>" + 
				           "<a onclick=\"deleteExport(" + i + ")\" title=\"Rimuovi '" + jsonRes[i].nome_file + "'\"><i class=\"icon-trash btn-icon\"></i></a><div class=\"icon_title_table\"> Cancella </div>"
				]);
			}

			$("#exportTable").DataTable({
				"autoWidth": false,
				"order": [],
				"paging": true,
				"ordering": false,
				"lengthChange": false,
				"pageLength": 30,
				"pagingType": "full_numbers",
				"retrieve": true,
				"data": data,
				"language": {
				    "decimal":        "",
				    "emptyTable":     "Non sono stati trovati export",
				    "info":           "Disponibili _TOTAL_ export",
				    "infoEmpty":      "Non ci sono export disponibili",
				    "infoFiltered":   "",
				    "infoPostFix":    "",
				    "lengthMenu":     "Mostra _MENU_ risultati",
				    "loadingRecords": "Caricamento...",
				    "processing":     "Elaborazione...",
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
				       {"title": "Nome File", "className": "sortable", "type": "string"},
				       {"title": "Utente", "className": "sortable", "type": "html", "searchable": false},
				       {"title": "", "className": "actions", "type": "html", "searchable": false}
				],
				"buttons": [ 
				    {"extend" : "csv", "title": "Export", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
				    {"extend" : "pdf", "title": "Export", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
				],
				"dom": 'f<"deleteSearch">rtipB'
			});
			
			$(".deleteSearch").html("<a onclick=\"$('.dataTables_filter input').val('').trigger('keyup');\">" +
					"<i class=\"icon-remove btn-icon\"></i></a>");
		},
		error: function(obj) {
			alert(obj.responseText);
		},
		dataType: "json"
	});
});

function deleteExport(index) {
	$(".modal-header h4").text("Cancella export '" + completed[index].nome_file + "'");
	selectedIndex = index;
	$("#delete-confirm").removeClass("hide").removeClass("fade");
}