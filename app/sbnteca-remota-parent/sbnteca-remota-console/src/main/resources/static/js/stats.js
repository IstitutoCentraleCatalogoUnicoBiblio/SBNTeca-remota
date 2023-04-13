$(document).ready(function() {
	$("#primaryBtn").click(function() {
		$.ajax({
			url: restUrl + "stats",
			type: "POST",
			data: JSON.stringify({
				  "query_nome_campo":"", 
				  "query_valore_campo":"", 
				  "faccette":[ $("#primaryField").val() ]
			}),
			success: function(jsonRes) {
				data = [];
				obj = jsonRes[0].risultati;
				
				for(j = 0; j < jsonRes[0].risultati.length; j++) {
					data[j] = [ 
					    jsonRes[0].risultati[j].valore, jsonRes[0].risultati[j].numero_documenti,
	            		
	            		 "<a onclick=\"displayFacets('" + jsonRes[0].nome_campo + "', '" + jsonRes[0].risultati[j].valore.replace(/\n/gm, "\\n").replace("'", "\\'") + "');\" title=\"Visualizza i dati associati a '" + jsonRes[0].risultati[j].valore + "'\">" +
         						"<i class=\"icon-arrow-down btn-icon\"></i></a><div class=\"icon_title_table\"> Valori correlati </div>"
					];
				}
				
				primaryFieldTitle = $("option[value='" + $("#primaryField").val() + "']").text();
				oTable = $("#primaryTable").DataTable();
				oTable.destroy();

				$("#primaryTable").DataTable({
					"autoWidth": false,
					"order": [],
					"destroy": true,
					"paging": true,
					"searching": false,
					"ordering": false,
					"lengthChange": false,
					"pageLength": 20,
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
			            {"title": "Valore", "className": "sortable", "type": "string", "orderable": false},
			            {"title": "Numero MAG/METS", "className": "sortable", "type": "string", "searchable": false, "orderable": false},
			            {"title": "", "className": "sortable actions", "type": "html", "searchable": false, "orderable": false}
					],
					"buttons": [ 
					    {"extend" : "csv", "title": primaryFieldTitle, "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
					    {"extend" : "pdf", "title": primaryFieldTitle, "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
					],
					"dom": 'frtipB'
				});
				
				$("#facetsSection").css("display", "none");
				
				for(j = 1; j < 6; j++) {
					$("#secondaryField_" + j).html("");
					$("#secondaryTable_" + j).DataTable().destroy();
					
					$("#secondaryTable_" + j).DataTable({
						"autoWidth": false,
						"order": [],
						"destroy": true,
						"paging": true,
						"searching": false,
						"ordering": false,
						"lengthChange": false,
						"pageLength": 10,
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
				            {"title": "Valore", "className": "sortable", "type": "string"},
				            {"title": "Numero MAG", "className": "sortable", "type": "string", "searchable": false, "orderable": false},
						]
					});
				}
			},
			error: function(obj, status, error) {
				alert(status + "\n" + error);
			},
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		});
	});
	
	$("#primaryTable").DataTable({
		"autoWidth": false,
		"order": [],
		"destroy": true,
		"paging": true,
		"searching": false,
		"ordering": false,
		"lengthChange": false,
		"pageLength": 20,
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
            {"title": "Valore", "className": "sortable", "type": "string", "orderable": false},
            {"title": "Numero MAG", "className": "sortable", "type": "string", "searchable": false, "orderable": false},
            {"title": "", "className": "sortable", "type": "html", "searchable": false, "orderable": false},
		]
	});

	for(j = 1; j < 6; j++) {
		$("#secondaryField_" + j).html("");
		$("#secondaryTable_" + j).DataTable({
			"autoWidth": false,
			"order": [],
			"destroy": true,
			"paging": true,
			"searching": false,
			"ordering": false,
			"lengthChange": false,
			"pageLength": 10,
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
	            {"title": "Valore", "className": "sortable", "type": "string", "orderable": false},
	            {"title": "Numero MAG", "className": "sortable", "type": "string", "searchable": false, "orderable": false},
			]
		});
	}
});

function displayFacets(fieldQuery, valueQuery) {
	$("#facetsSection").css("display", "inline");
	
	facets = [];
	
	$("#primaryField option").map(function() {
		val = $(this).val();
		
		if(val != fieldQuery)
			facets.push(val);
	});
	
	postData = {
		  "query_nome_campo": fieldQuery, 
		  "query_valore_campo": valueQuery, 
		  "faccette": facets
	};
	
	$.ajax({
		url: restUrl + "stats",
		type: "POST",
		data: JSON.stringify(postData),
		success: function(jsonRes) {
			for(i = 0; i < facets.length; i++) {
				data = [];
				
				for(j = 0; j < jsonRes.length; j++) {
					jsonField = jsonRes[j];
					
					if(jsonField.nome_campo == facets[i]) {
						for(k = 0; k < jsonField.risultati.length; k++) {
							data[k] = [ jsonField.risultati[k].valore, jsonField.risultati[k].numero_documenti ];
						}
					}
				}
				
				$("#secondaryField_" + (i + 1)).html($("option[value='" + facets[i] + "']").text());
				$("#primaryValue_" + (i + 1)).html("associato a '" + valueQuery + "'");
				secondaryFieldTitle = $("#secondaryField_" + (i + 1)).text();
				oTable = $("#secondaryTable_" + (i + 1)).DataTable();
				oTable.destroy();

				$("#secondaryTable_" + (i + 1)).DataTable({
					"autoWidth": false,
					"order": [],
					"destroy": true,
					"paging": true,
					"searching": false,
					"ordering": false,
					"lengthChange": false,
					"pageLength": 10,
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
			            {"title": "Valore", "className": "sortable", "type": "string", "orderable": false},
			            {"title": "Numero MAG", "className": "sortable", "type": "string", "searchable": false, "orderable": false},
					],
					"buttons": [ 
					    {"extend" : "csv", "title": secondaryFieldTitle, "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
					    {"extend" : "pdf", "title": secondaryFieldTitle, "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
					],
					"dom": 'frtipB'
				});
			}
		},
		error: function(obj, status, error) {
			alert(status + "\n" + error);
		},
		contentType: "application/json; charset=UTF-8",
		dataType: "json"
	});
}
