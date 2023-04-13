var MAX_INT = 2147483647;

$(document).ready(function() {
	$(".groupby").change(function() {
		$("#groupby_select").removeClass("error");
		$("#groupError").css("display", "none");
	});
	
	$(".filter").each(function(i, element) {
		var field = element.id.replace("channel_", "");

		$.ajax({
			url: restUrl + "values?field=" + field + "&N=" + MAX_INT,
			type: "GET",
			success: function(values) {
				$("#channel_" + field).append($('<option>', {value: "", text: ""}));
				
				for(i = 0; i < values.length; i++)
					$("#channel_" + field).append($('<option>', { value: values[i], text: values[i]}));
			},
			error: function(obj, status, error) {
				alert(status + "\n" + error);
			},
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		});
	});
	
	$("#statsBtn").click(function() {
		var group = getGroup();
		
		if(group.length == 0) {
			$("#groupby_select").addClass("error");
			$("#groupError").css("display", "inline-block");
			return;
		}
		
		$("#groupby_select").removeClass("error");
		$("#groupError").css("display", "none");
		var request = { "ricerca": {}, "raggruppamenti": group };
		
		$(".filter").each(function(i, element) {
			var field = element.id.replace("channel_", "");
			var value = $(element).val();
			
			if(value != "")
				request.ricerca[field] = value;
		});
		
		drawStatsTable(request, false);
	});
	
	$("#undoBtn").click(function() {
		window.location.href = "objectsStats";
	});
	
	var group = getGroup();
	
	if(group.length == 0) {
		$("#groupError").css("display", "inline-block");
		return;
	}

	else
		$("#groupError").css("display", "none");
	
	var request = { "ricerca": {}, "raggruppamenti": group };
	drawStatsTable(request, true);
	
});

function getGroup() {
	group = [];
	
	$(".groupby").each(function(i, element) {
		if($(element).prop("checked"))
			group.push($(element).val());
	});
	
	return group;
}

function filterBy(field, value) {
	$(".filter").each(function(i, element) {
		if(field == element.id.replace("channel_", ""))
			$(element).val(value);
		
		else
			$(element).val("");
	});
	
	var check = false;
	
	$(".groupby").each(function(i, element) {
		if(element.id.endsWith(field)) {
			$(element).prop("checked", false);
			check = true;
		}
		
		else if(check) {
			$(element).prop("checked", true);
			check = false;
		}
		
		else
			$(element).prop("checked", false);
	});
	
	var group = getGroup();

	if(group.length == 0) {
		$("#groupby_select").addClass("error");
		$("#groupError").css("display", "inline-block");
		return;
	}

	$("#groupby_select").removeClass("error");
	$("#groupError").css("display", "none");
	var request = { "ricerca": {}, "raggruppamenti": group };
	
	$(".filter").each(function(i, element) {
		var field = element.id.replace("channel_", "");
		var value = $(element).val();
		
		if(value != "")
			request.ricerca[field] = value;
	});
	
	drawStatsTable(request, false);
}

function drawStatsTable(request, create) {
	$.ajax({
		url: restUrl + "digitalObjectStats",
		type: "POST",
		data: JSON.stringify(request),
		success: function(jsonTable) {
			columns = [];
			
			for(i = 0; i < jsonTable.raggruppamenti.length; i++) {
				columns.push({"title": $("label[for=\"group_" + jsonTable.raggruppamenti[i] + "\"]").text(), 
					"className": "sortable result_field", "type": jsonTable.raggruppamenti[i] == "collection" ? 
							"string" : "html", "orderable": true});
			}
			
			columns.push({"title": "MAG/METS", "className": "sortable", "type": "num", "orderable": true});
			columns.push({"title": "Img", "className": "sortable", "type": "num", "orderable": true});
			columns.push({"title": "Audio", "className": "sortable", "type": "num", "orderable": true});
			columns.push({"title": "Video", "className": "sortable", "type": "num", "orderable": true});
			columns.push({"title": "Ocr", "className": "sortable", "type": "num", "orderable": true});
			columns.push({"title": "Doc", "className": "sortable", "type": "num", "orderable": true});
			
			data = [];
			
			for(i = 0; i < jsonTable.record.length; i++) {
				r = [];
				
				for(j = 0; j < jsonTable.record[i].valori.length; j++) {
					if(jsonTable.raggruppamenti[j] == "collection")
						r.push(jsonTable.record[i].valori[j]);
					
					else {
						r.push("<a onclick=\"filterBy('" + jsonTable.raggruppamenti[j] + "', " +
								"'" + jsonTable.record[i].valori[j].replace("'", "''") + "');\" " +
								"class=\"btn-link\">" + jsonTable.record[i].valori[j] + "</a>");
					}
						
				}
				
				r.push(jsonTable.record[i].numero_documenti);
				r.push(jsonTable.record[i].numero_immagini);
				r.push(jsonTable.record[i].numero_audio);
				r.push(jsonTable.record[i].numero_video);
				r.push(jsonTable.record[i].numero_ocr);
				r.push(jsonTable.record[i].numero_doc);
				data.push(r);
			}
			
			if(!create) {
				$("#statsTable").DataTable().destroy();
				$("#statsTable").find('thead').remove();
				$("#statsTable").find('tbody').remove();
				$("#statsTable").find('tfoot').remove();
			}
			
			$("#statsTable").DataTable({
				"autoWidth": false,
				"order": [],
				"destroy": true,
				"paging": true,
				"searching": false,
				"ordering": true,
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
				"columns": columns,
				"footerCallback": function (row, data, start, end, display) {
					$("#statsTable").find('tfoot').remove();
					var api = this.api(), data;
		            
		            var intVal = function (i) {
		                return typeof i === 'string' ? i.replace(/[\$,]/g, '')*1 : typeof i === 'number' ? i : 0;
		            };
		            
					var footer = "<tfoot><tr>";
					footer += "<th class=\"sortable result_field\" colspan=\"" + request.raggruppamenti.length + "\">Totale</th>";
					
					for(i = request.raggruppamenti.length; i < columns.length; i++) {
						pageTotal = api.column(i, { page: 'current' }).data().reduce(function (a, b) {
		                    return intVal(a) + intVal(b);
			            }, 0);
						
						footer += "<th class=\"sortable icon_link\">" + pageTotal + "</th>";
					}
					
					footer += "</tr></tfoot>";
					$("#statsTable").append(footer);
		        },
				"buttons": [ 
				    {"extend" : "csv", "title": "Statistiche", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
				    {"extend" : "pdf", "title": "Statistiche", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
				],
				"dom": 'frtipB'
			});
		},
		error: function(obj, status, error) {
			alert(status + "\n" + error);
		},
		contentType: "application/json; charset=UTF-8",
		dataType: "json"
	});
}