var selectedProject;
var timeout;

$(document).ready(function() {
	$("#delete-confirm .modal-header .close").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
	});
	
	$("#delete-confirm #noBtn").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
	});
	
	$("#delete-op-error .modal-header .close").click(function() {
		$("#delete-op-error").addClass("hide").addClass("fade");
	});
	
	$("#delete-op-error #deleteProjectErrorOkBtn").click(function() {
		$("#delete-op-error").addClass("hide").addClass("fade");
	});
	
	$("#yesBtn").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
		$("#projectTableArea").addClass("hide");
		$("#waitArea").removeClass("hide").html("<b>Cancellazione progetto '" + selectedProject + "' in corso.</b>");
	 	
	 	timeout = window.setInterval(function() {
			var txt = $('#waitArea').text();
			var pointIdx = txt.indexOf('.');
			var originalText = txt.substring(0, pointIdx);
			var numPoints = (txt.length - pointIdx) % 3 + 1;
			var textNew = originalText;
			
			for(var i = 0; i < numPoints; i++)
				textNew += ".";
			
			$('#waitArea').html("<b>" + textNew + "</b>");
		}, 1000);
	 	
    	$.ajax({
    		url: restUrl + "deleteFolder/" + selectedProject,
    		type: "GET",
    		
    		success: function(jsonRes) {
    			window.clearInterval(timeout);
    			
    			if(jsonRes.stato == 0)
    				window.location.href = "clear";
    			
    			else {
    				$("#delete-op-error").removeClass("hide").removeClass("fade");
    				$("#deleteProjectErrorMsg").text(jsonRes.messaggio);
    			}
    		},
    		error: function(obj, status, error) {
    			window.clearInterval(timeout);
    			
    			if(status != 0)
    				console.log(JSON.parse(obj.responseText).messaggio);
    		},
    		contentType: "application/json; charset=UTF-8",
    		dataType: "json"
    	});
	});
	
	$.ajax({
		url: restUrl + "projects",
		success: function(jsonRes) {
			data = [];
			
			for(i = 0; i < jsonRes.length; i++) {
				data.push([
				           jsonRes[i],
				           "<a onclick=\"deleteProject('" + jsonRes[i] + "')\" title=\"Rimuovi '" + jsonRes[i] + "'\"><i class=\"icon-trash btn-icon\"></i></a><div class=\"icon_title_table\"> Cancella </div>"
				]);
			}

			$("#projectTable").DataTable({
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
				    "emptyTable":     "Non sono stati trovati progetti",
				    "info":           "Disponibili _TOTAL_ progetti",
				    "infoEmpty":      "Non ci sono progetti disponibili",
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
				       {"title": "", "className": "sortable actions", "type": "html", "searchable": false}
				],
				"buttons": [ 
				    {"extend" : "csv", "title": "Progetti", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
				    {"extend" : "pdf", "title": "Progetti", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
				],
				"dom": 'f<"deleteSearch">rtipB'
			});
			
			$(".deleteSearch").html("<a onclick=\"$('.dataTables_filter input').val('').trigger('keyup');\">" +
					"<i class=\"icon-remove btn-icon\"></i></a>");
		},
		error: function(obj) {
			$("#projectTable").css("display", "none");
			$("#error").css("display", "block").text("HTTP " + obj.status + " - " + obj.responseText);
		},
		dataType: "json"
	});
});

function deleteProject(project) {
	$(".modal-header h4").text("Cancella progetto '" + project + "'");
	selectedProject = project;
	$("#delete-confirm").removeClass("hide").removeClass("fade");
}