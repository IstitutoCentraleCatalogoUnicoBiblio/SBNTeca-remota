var listMagIDs = [];
var not_listMagIDs = [];
var allMagIDs = false;
var lastBaseSearch;
var timeout;
var resultTableColumns = [];
var selectedMagID;
var opExecuting = false;
var changePageHref;


$(document).ready(function() {
	listMagIDs = [];
	not_listMagIDs = [];
	allMagIDs = false;
	
	$(".modal-header .close").click(function() {
		$("#editor-open-error").addClass("hide").addClass("fade");
		$("#editor-confirm").addClass("hide").addClass("fade");
		$("#op-executing-warning").addClass("hide").addClass("fade");
	});
	
	$("#editor-confirm #noBtn").click(function() {
		$("#editor-confirm").addClass("hide").addClass("fade");
	});

	$("#editor-confirm #yesBtn").click(function() {
		$("#editor-confirm").addClass("hide").addClass("fade");
		window.location.href = "editor?id=" + selectedMagID;
	});
	
	$("#editor-open-error #openErrorCloseBtn").click(function() {
		$("#editor-open-error").addClass("hide").addClass("fade");
	});
	
	$("#op-executing-warning #noBtn").click(function() {
		$("#op-executing-warning").addClass("hide").addClass("fade");
	});

	$("#op-executing-warning #yesBtn").click(function() {
		$("#op-executing-warning").addClass("hide").addClass("fade");
		window.location.href = changePageHref;
	});
	
	$("#menu li a").click(function() {
		if(opExecuting) {
			$("#op-executing-warning").removeClass("hide").removeClass("fade");
			changePageHref = $(this).attr("href");
			return false;
		}
	});

	$("a.logout").click(function() {
		if(opExecuting) {
			$("#op-executing-warning").removeClass("hide").removeClass("fade");
			changePageHref = $(this).attr("href");
			return false;
		}
	});
	
	$("#opBackToStartBtn").click(function() {
		if(opExecuting) {
			$("#op-executing-warning").removeClass("hide").removeClass("fade");
			changePageHref = window.location.href;
			return false;
		}
		
		else
			window.location.reload(true);
	});
	
	if($("#selectableResults").val() == 'y')
		resultTableColumns.push({"title": "", "className": "sortable", "type": "html"});
	
	resultTableColumns.push({"title": "Identifier", "className": "sortable", "type": "html"});
	resultTableColumns.push({"title": "Label", "className": "sortable", "type": "html", "width": "30%"});
	resultTableColumns.push({"title": "Tipo VFS", "className": "sortable", "type": "html", "width": "15%"});
	resultTableColumns.push({"title": "Data", "className": "sortable", "type": "html"});
	resultTableColumns.push({"title": "Tipo contenuto", "className": "sortable", "type": "html"});
	resultTableColumns.push({"title": "Pubbl.", "className": "sortable", "type": "html"});
	resultTableColumns.push({"title": "", "className": "sortable actions", "type": "html", "orderable": false});
	   		
	$("input[name=\"publicFlag\"]").change(function() {
		$("#publicFlagError").css("display", "none");
		$("#publicFlagArea").removeClass("error");
	});
	
	$.ajax({
		url: restUrl + "vfs/search?facetOnly",
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
	
	$(".normalize_field").change(function() {
		$("#normalizeArea").css("display", "none");
		var idTableArea = this.id.replace("normalize_", "") + "_tableArea";
		var idTable = this.id.replace("normalize_", "") + "_table";
		
		if(!$(this).prop("checked"))
			$("#" + idTableArea).css("display", "none");
		
		else {
			lastBaseSearch.id = allMagIDs ? [] : listMagIDs;
			lastBaseSearch.not_id = allMagIDs ? [] : not_listMagIDs;
			delete lastBaseSearch.inizio;
			delete lastBaseSearch.lunghezza_risultati;
			
			$.ajax({
				url: restUrl + "normalizationFacet?field=" + this.id.replace("normalize_", ""),
				type: "POST",
				data: JSON.stringify(lastBaseSearch),
				success: function(values) {
					data = [];
					
					for(i = 0; i < values.length; i++) {
						data.push([ values[i].valore != null ? htmlentities(values[i].valore) : "[vuoto]", values[i].numero_documenti, 
						            "<input class=\"normalize_new_value\" onkeyup=\"$('#normalizeArea').css('display', 'none');\" type=\"text\" />" ]);
					}
					
					$("#" + idTable).DataTable().destroy();
	
					$("#" + idTable).DataTable({
						"autoWidth": false,
						"order": [],
						"destroy": true,
						"paging": true,
						"searching": false,
						"ordering": false,
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
						"rowCallback": function(row, data, index) {
							row.id = "normalize_update_row_" + index;
							$(row).addClass("normalize_update_row");
						},
						"drawCallback": function(settings) {
							$("#" + idTableArea).css("display", "block");
						},
						"columns": [
						            {"title": "Valore Attuale", "className": "sortable normalize_old_value", "type": "string", "width": "40%"},
						            {"title": "#", "className": "sortable", "type": "string"},
						            {"title": "Valore da Applicare", "className": "sortable normalize_new_value_container", "type": "html"}
						]
					});
				},
				error: function(json) {
					alert(JSON.stringify(json, null, '\t'));
				},
				contentType: "application/json; charset=UTF-8",
				dataType: "json"
			});
		}
	});
	
	$("#backToSearchBtn").click(function() {
		listMagIDs = [];
		not_listMagIDs = [];
		allMagIDs = false;
		
		$("#normalizationArea").css("display", "none");
		$("#executeOpError").css("display", "none");
		$("#publicFlag_error").css("display", "none");
		$("#onlyPublic").prop("checked", true);
		$("#onlyNotPublic").prop("checked", true);
		$("#summaryOperation").css("display", "none");
		$("#resultArea").css("display", "none");
		$("#ricerca_avanzata").css("display", "block");
	});
	
	$("#searchBtn").click(function() {
		var errors = false;
		
		$(".channel_date").each(function(index, element) {
			var val = $(element).val();
			
			if(val == "") {
				$("#" + element.id + "_control").removeClass("error");
				return;
			}
			
			if(val.split("-").length != 3) {
				errors = true;
				$("#" + element.id + "_control").addClass("error");
				return;
			}
			
			var date = Date.parse(val);

			if(isNaN(date)) {
				errors = true;
				$("#" + element.id + "_control").addClass("error");
			}
			
			else
				$("#" + element.id + "_control").removeClass("error");
		});
		
		if(errors) {
			document.getElementById("timestamp_area").scrollIntoView();
			return;
		}
		
		$("#ricerca_avanzata").css("display", "none");
		$("#blockDisplay").removeClass("hide").removeClass("fade");
		
		var timeout = window.setInterval(function() {
			var txt = $('#blockText').text();
			var pointIdx = txt.indexOf('.');
			var originalText = txt.substring(0, pointIdx);
			var numPoints = (txt.length - pointIdx) % 3 + 1;
			var textNew = originalText;
			
			for(var i = 0; i < numPoints; i++)
				textNew += ".";
			
			$('#blockText').text(textNew);
		}, 1000);
		
		dataPost = {
				"ricerca": {},
				"ricerca_date": {},
				"filtri_faccette": {}
		};
		
		$(".channel").each(function(index, element) {
			values = $(element).val();
			
			if(element.id == "channel_timestamp_start" || element.id == "channel_timestamp_end") {
				if(values != null && values != undefined && values != "") {
					var timestampField = element.id.replace("channel_", "").replace("_start", "").replace("_end", "");
					
					if(dataPost.ricerca_date[timestampField] == undefined)
						dataPost.ricerca_date[timestampField] = [ "", "" ];
					
					if(element.id == "channel_timestamp_start")
						dataPost.ricerca_date[timestampField][0] = values;
					
					else
						dataPost.ricerca_date[timestampField][1] = values;
				}
			}
			
			else if(values != null && values != undefined && values.length > 0)
				dataPost.ricerca[element.id.replace("channel_", "")] = values;
		});
		
		var publicFlagVal = $("#publicFlag").val();
		
		if(publicFlagVal != "")
			dataPost.ricerca["publicFlag"] = publicFlagVal;
		
		lastBaseSearch = dataPost;
		ajaxSearch(dataPost);
	});
	
	$("#deleteBtn").click(function() {
		$(".channel").each(function(index, element) {
			if($(element).attr("multiple"))
				$(element).val([]);
			
			else
				$(element).val("");
		});
		
		$("#publicFlag_error").css("display", "none");
		$("#onlyPublic").prop("checked", true);
		$("#onlyNotPublic").prop("checked", true);
	});
	
	$("#searchSelectPage").click(function() {
		if(allMagIDs) {
			listMagIDs = [];
			not_listMagIDs = [];
			return;
		}
		
		var chkList = $('.selectable_result');
		
		for(var i = 0; i < chkList.length; i++) {
			$(chkList[i]).prop('checked', true);
			val = $(chkList[i]).val();
			
			var idx = not_listMagIDs.indexOf(val);
			
			if(idx >= 0)
				not_listMagIDs.splice(idx, 1);
			
			else if(listMagIDs.indexOf(val) < 0)
				listMagIDs.push(val);
		}
		
		if(not_listMagIDs.length == 0 && listMagIDs.length == 0)
			allMagIDs = true;
	});

	$("#searchUnselectPage").click(function() {
		var chkList = $('.selectable_result');
		
		for(var i = 0; i < chkList.length; i++) {
			$(chkList[i]).prop('checked', false);
			val = $(chkList[i]).val();
			
			if(allMagIDs) {
				idx = not_listMagIDs.indexOf(val);
				
				if(idx < 0)
					not_listMagIDs.push(val);
			}
			
			else {
				idx = listMagIDs.indexOf(val);
				
				if(idx >= 0)
					listMagIDs.splice(idx, 1);
			}
		}

		allMagIDs = false;
	});

	$("#searchSelectAll").click(function() {
		var chkList = $('.selectable_result');
		
		for(var i = 0; i < chkList.length; i++)
			$(chkList[i]).prop('checked', true);

		allMagIDs = true;
		listMagIDs = [];
		not_listMagIDs = [];
	});

	$("#searchUnselectAll").click(function() {
		var chkList = $('.selectable_result');
		
		for(var i = 0; i < chkList.length; i++)
			$(chkList[i]).prop('checked', false);

		allMagIDs = false;
		listMagIDs = [];
		not_listMagIDs = [];
	});
	
	$("#executeOpBtn").click(function() {
		if(!allMagIDs && not_listMagIDs.length == 0 && listMagIDs.length == 0) {
			$("#executeOpError").css("display", "inline-block").text("Nessun MAG selezionato");
			return;
		}
		
		createSelectedSummaryTable();
		$("#executeOpError").css("display", "none");
		$("#resultArea").css("display", "none");
		$("#summaryOperation").css("display", "inline-block");
	});

	$("#publicMagBtn").click(function() {
		if(!$("#publicFlag_1").prop("checked") && !$("#publicFlag_0").prop("checked")) {
			$("#publicFlagError").css("display", "inline-block");
			$("#publicFlagArea").addClass("error");
			return;
		}
		
		if(!allMagIDs && listMagIDs.length == 0 && not_listMagIDs.length == 0) {
			alert("Non ci sono documenti selezionati, premere \"Annulla\" per ripetere la selezione");
			return;
		}
		
		$("#publicFlagError").css("display", "none");
		$("#confirmSelectionArea").css("display", "none");
		$("#finalResult").css("display", "inline-block");
		$("#finalResultTableArea").css("display", "none");
		
		lastBaseSearch.id = allMagIDs ? [] : listMagIDs;
		lastBaseSearch.not_id = allMagIDs ? [] : not_listMagIDs;
		delete lastBaseSearch.inizio;
		delete lastBaseSearch.lunghezza_risultati;
		var timeout;
		var flag = $("#publicFlag_1").prop("checked") ? 1 : 0;
		opExecuting = true;
		
	 	$.ajax({
	 		url: restUrl + "publicMag",
	 		type: "POST",
	 		data: JSON.stringify({ "utente" : $("#userID").val(), "flag_pubblica" : flag, "ricerca_avanzata" : lastBaseSearch }),
	 		success: function(json) {
	 			window.clearInterval(timeout);
	 			
	 			if(json.stato < 0)
	 				$("#publicResult").html(json.messaggio);
	 				
	 			else {
		 			var html = "<b>" + json.messaggio + "</b><br /><br /><br />";
		 			var publMag = [];
		 			var nonPublMag = [];
		 			var data = [];
		 			
		 			for(i = 0; i < json.mag.length; i++) {
		 				jsonMag = json.mag[i];
		 				
		 				if(jsonMag.pubblicato == 1 && jsonMag.errore.length == 0)
		 					publMag.push(jsonMag.id);

		 				else if(jsonMag.pubblicato == 0 && jsonMag.errore.length == 0)
		 					nonPublMag.push(jsonMag.id);
		 				
		 				data.push([ jsonMag.id, jsonMag.pubblicato == 1 ? "Si" : "No", jsonMag.errore ]);
		 			}
		 			
		 			html += "<b>Mag pubblicati (" + publMag.length + "):</b><br />";
		 			
		 			if(publMag.length == 0)
		 				html += "-";
		 			
		 			for(var i = 0; i < publMag.length; i++) {
		 				if(i > 0)
		 					html += "; ";
		 				
		 				html += publMag[i];
		 			}
		 			
		 			html += "<br /><br /><b>Mag non pubblicati (" + nonPublMag.length + "):</b><br />";
		 			
		 			if(nonPublMag.length == 0)
		 				html += "-";
		 			
		 			for(var i = 0; i < nonPublMag.length; i++) {
		 				if(i > 0)
		 					html += "; ";
		 				
		 				html += nonPublMag[i];
		 			}
		 			
		 			$("#infoResult").html(html);
		 			
		 			$("#finalResultTable").DataTable({
		 				"autoWidth": false,
		 				"order": [],
		 				"ordering": false,
		 				"paging": true,
		 				"searching": false,
		 				"lengthChange": false,
		 				"pageLength": 20,
		 				"pagingType": "full_numbers",
		 				"retrieve": true,
		 				"data": data,
		 				"language": {
		 				    "decimal":        "",
		 				    "emptyTable":     "Non ci sono MAG selezionati",
		 				    "info":           "Mostra da _START_ a _END_ risultati di _TOTAL_ totali",
		 				    "infoEmpty":      "Mostra 0 di 0 MAG",
		 				    "infoFiltered":   "",
		 				    "infoPostFix":    "",
		 				    "lengthMenu":     "Mostra _MENU_ MAG",
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
		 				            {"title": "MAG", "className": "sortable", "type": "string"},
		 				            {"title": "Pubblicato", "className": "sortable", "type": "string"},
		 					        {"title": "Errore", "className": "sortable", "type": "string"}
		 				],
		 				"buttons": [ 
 						    {"extend" : "csv", "title": "MAG_Pubblicazione", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
 						    {"extend" : "pdf", "title": "MAG_Pubblicazione", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
 						],
 						"dom": 'frtipB',
		 				"drawCallback": function(settings) {
		 					$("#finalResultTableArea").css("display", "inline-block");
		 				}
		 			});
	 			}
	 			
	 			opExecuting = false;
	 		},
	 		error: function(json) {
	 			window.clearInterval(timeout);
	 			opExecuting = false;
	 			
	 			if(json.status != 0)
	 				console.log(JSON.stringify(json, null, '\t'));
	 		},
	 		contentType: "application/json; charset=UTF-8",
	 		dataType: "json"
	 	});
	 	
	 	$("#infoResult").html("<b>Servizio di pubblicazione MAG in corso.</b>");
	 	
	 	timeout = window.setInterval(function() {
			var txt = $('#infoResult').text();
			var pointIdx = txt.indexOf('.');
			var originalText = txt.substring(0, pointIdx);
			var numPoints = (txt.length - pointIdx) % 3 + 1;
			var textNew = originalText;
			
			for(var i = 0; i < numPoints; i++)
				textNew += ".";
			
			$('#infoResult').html("<b>" + textNew + "</b>");
		}, 1000);
	});
	
	$("#deleteMagBtn").click(function() {
		if(!allMagIDs && listMagIDs.length == 0 && not_listMagIDs.length == 0) {
			alert("Non ci sono documenti selezionati, premere \"Annulla\" per ripetere la selezione");
			return;
		}

		lastBaseSearch.id = allMagIDs ? [] : listMagIDs;
		lastBaseSearch.not_id = allMagIDs ? [] : not_listMagIDs;
		delete lastBaseSearch.inizio;
		delete lastBaseSearch.lunghezza_risultati;
		$("#confirmSelectionArea").css("display", "none");
		$("#finalResult").css("display", "inline-block");
		$("#finalResultTableArea").css("display", "none");
		var timeout;
		opExecuting = true;
		
		$.ajax({
			url: restUrl + "deleteMag",
			type: "POST",
			data: JSON.stringify({
				"utente": $("#userID").val(),
				"ricerca_avanzata" : lastBaseSearch
			}),
			success: function(json) {
				window.clearInterval(timeout);
				var html = "<b>Processo di cancellazione terminato</b><br /><br /><br /><b>Mag cancellati (" + json.mag_cancellati.length + "):</b><br />";
				var data = [];
				
				if(json.mag_cancellati.length == 0)
					html += "-";
				
				for(i = 0; i < json.mag_cancellati.length; i++) {
					if(i > 0)
						html += "; ";
					
					html += json.mag_cancellati[i];
					data.push([ json.mag_cancellati[i], "", "<i class=\"icon-ok-circle btn-icon\"></i><div class=\"icon_title_table\"> OK </div>" ]);
				}
				
				if(json.mag_inesistenti.length > 0) {
					html += "<br /><br /><b>Mag non trovati (" + json.mag_inesistenti.length + "):</b><br />";
					
					for(i = 0; i < json.mag_inesistenti.length; i++) {
						if(i > 0)
							html += "; ";
						
						html += json.mag_inesistenti[i];
						data.push([ json.mag_inesistenti[i], "MAG non trovato", "<i class=\"icon-remove-circle btn-icon\"></i><div class=\"icon_title_table\"> KO </div>" ]);
					}
				}
				
				$("#infoResult").html(html);

	 			$("#finalResultTable").DataTable({
	 				"autoWidth": false,
	 				"order": [],
	 				"ordering": false,
	 				"paging": true,
	 				"searching": false,
	 				"lengthChange": false,
	 				"pageLength": 20,
	 				"pagingType": "full_numbers",
	 				"retrieve": true,
	 				"data": data,
	 				"language": {
	 				    "decimal":        "",
	 				    "emptyTable":     "Non ci sono MAG selezionati",
	 				    "info":           "Mostra da _START_ a _END_ risultati di _TOTAL_ totali",
	 				    "infoEmpty":      "Mostra 0 di 0 MAG",
	 				    "infoFiltered":   "",
	 				    "infoPostFix":    "",
	 				    "lengthMenu":     "Mostra _MENU_ MAG",
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
	 				            {"title": "MAG", "className": "sortable", "type": "string"},
	 				            {"title": "Errori", "className": "sortable", "type": "string"},
	 					        {"title": "", "className": "sortable actions", "type": "html"}
	 				],
	 				"buttons": [ 
					    {"extend" : "csv", "title": "MAG_Cancellazione", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
					    {"extend" : "pdf", "title": "MAG_Cancellazione", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
					],
					"dom": 'frtipB',
	 				"drawCallback": function(settings) {
	 					$("#finalResultTableArea").css("display", "inline-block");
	 				}
	 			});
	 			
	 			opExecuting = false;
			},
			error: function(json) {
				window.clearInterval(timeout);
				opExecuting = false;

	 			if(json.status != 0)
	 				console.log(JSON.stringify(json, null, '\t'));
			},
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		});
			
		$("#infoResult").html("<b>Cancellazione MAG in corso.</b>");
		
		timeout = window.setInterval(function() {
			var txt = $('#infoResult').text();
			var pointIdx = txt.indexOf('.');
			var originalText = txt.substring(0, pointIdx);
			var numPoints = (txt.length - pointIdx) % 3 + 1;
			var textNew = originalText;
			
			for(var i = 0; i < numPoints; i++)
				textNew += ".";
			
			$('#infoResult').html("<b>" + textNew + "</b>");
		}, 1000);
	});
	
	$("#exportMagBtn").click(function() {
		if(!allMagIDs && listMagIDs.length == 0 && not_listMagIDs.length == 0) {
			alert("Non ci sono documenti selezionati, premere \"Annulla\" per ripetere la selezione");
			return;
		}
		
		lastBaseSearch.id = allMagIDs ? [] : listMagIDs;
		lastBaseSearch.not_id = allMagIDs ? [] : not_listMagIDs;
		delete lastBaseSearch.inizio;
		delete lastBaseSearch.lunghezza_risultati;
		$("#confirmSelectionArea").css("display", "none");
		$("#finalResult").css("display", "inline-block");
		$("#finalResultTableArea").css("display", "none");
		var timeout;
		opExecuting = true;
		
		$.ajax({
			url: restUrl + "exportMag",
			type: "POST",
			data: JSON.stringify({
				"utente": $("#userID").val(),
				"ricerca_avanzata" : lastBaseSearch,
				"estensione": $("#exportExtension").val(),
				"format": $("#exportFormat").val(),
				"nome_file": $("#exportFilename").val(),
				"vestizione": $("#exportDress").prop("checked")
			}),
			success: function(json) {
				window.clearInterval(timeout);
				
				var html = "<b>Processo di esportazione terminato, creato il file '" + json.nome_file + "'." +
						"</b><br /><br /><br /><b>Mag esportati (" + json.mag_esportati.length + "):</b><br />";
				
				var data = [];
				var sep = false;
				
				for(i = 0; i < json.mag_esportati.length; i++) {
					if(!sep)
						sep = true;
					
					else
						html += "; ";
					
					html += json.mag_esportati[i];
					data.push([ json.mag_esportati[i], "Si", "<i class=\"icon-ok-circle btn-icon\"></i><div class=\"icon_title_table\"> OK </div>" ]);
				}
				
				if(json.mag_esportati.length == 0)
					html += " - ";
				
				html += "<br /><br /><b>Mag non esportati (" + json.mag_non_esportati.length + "):</b><br />";
				var sep = false;
				
				for(i = 0; i < json.mag_non_esportati.length; i++) {
					if(!sep)
						sep = true;
					
					else
						html += "; ";
					
					html += json.mag_non_esportati[i];
					data.push([ json.mag_non_esportati[i], "No", "<i class=\"icon-remove-circle btn-icon\"></i><div class=\"icon_title_table\"> KO </div>" ]);
				}
				if(json.mag_non_esportati.length == 0)
					html += " - ";
				
				$("#infoResult").html(html);

	 			$("#finalResultTable").DataTable({
	 				"autoWidth": false,
	 				"order": [],
	 				"ordering": false,
	 				"paging": true,
	 				"searching": false,
	 				"lengthChange": false,
	 				"pageLength": 20,
	 				"pagingType": "full_numbers",
	 				"retrieve": true,
	 				"data": data,
	 				"language": {
	 				    "decimal":        "",
	 				    "emptyTable":     "Non ci sono MAG selezionati",
	 				    "info":           "Mostra da _START_ a _END_ risultati di _TOTAL_ totali",
	 				    "infoEmpty":      "Mostra 0 di 0 MAG",
	 				    "infoFiltered":   "",
	 				    "infoPostFix":    "",
	 				    "lengthMenu":     "Mostra _MENU_ MAG",
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
	 				            {"title": "MAG", "className": "sortable", "type": "string"},
	 				            {"title": "Esportato", "className": "sortable", "type": "string"},
	 					        {"title": "", "className": "sortable actions", "type": "html"}
	 				],
	 				"buttons": [ 
					    {"extend" : "csv", "title": "MAG_Esportazione", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
					    {"extend" : "pdf", "title": "MAG_Esportazione", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
					],
					"dom": 'frtipB',
	 				"drawCallback": function(settings) {
	 					$("#finalResultTableArea").css("display", "inline-block");
	 				}
	 			});
	 			
	 			opExecuting = false;
			},
			error: function(json) {
				window.clearInterval(timeout);
				opExecuting = false;

	 			if(json.status != 0)
	 				console.log(JSON.stringify(json, null, '\t'));
			},
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		});
			
		$("#infoResult").html("<b>Esportazione MAG in corso.</b>");
		
		timeout = window.setInterval(function() {
			var txt = $('#infoResult').text();
			var pointIdx = txt.indexOf('.');
			var originalText = txt.substring(0, pointIdx);
			var numPoints = (txt.length - pointIdx) % 3 + 1;
			var textNew = originalText;
			
			for(var i = 0; i < numPoints; i++)
				textNew += ".";
			
			$('#infoResult').html("<b>" + textNew + "</b>");
		}, 1000);
	});
	
	$("#normalizeMagBtn").click(function() {
		if(!allMagIDs && listMagIDs.length == 0 && not_listMagIDs.length == 0) {
			alert("Non ci sono documenti selezionati, premere \"Annulla\" per ripetere la selezione");
			return;
		}
		
		lastBaseSearch.id = allMagIDs ? [] : listMagIDs;
		lastBaseSearch.not_id = allMagIDs ? [] : not_listMagIDs;
		delete lastBaseSearch.inizio;
		delete lastBaseSearch.lunghezza_risultati;
		$("#executeOpError").css("display", "none");
		json = {};
		json.ricerca_avanzata = lastBaseSearch;
		json.normalizzazione = [];
		leastOneChecked = false;
		
		$(".normalize_field").each(function(index, element) {
			if($(element).prop("checked")) {
				fieldName = element.id.replace("normalize_", "");
				
				jsonField = {};
				jsonField.campo = fieldName;
				jsonField.modifiche = [];
				
				var idTable = element.id.replace("normalize_", "") + "_table";
				dataTable = $("#" + idTable).DataTable();
				oldValues = dataTable.$(".normalize_old_value");
				newValues = dataTable.$(".normalize_new_value");
				
				for(i = 0; i < oldValues.length; i++) {
					oldValue = $(oldValues[i]).text();
					newValue = $(newValues[i]).val();
					
					if(newValue.length > 0)
						jsonField.modifiche.push({ "valore_attuale": oldValue == "[vuoto]" ? null : oldValue, "valore_nuovo": newValue });
				}
				
				if(jsonField.modifiche.length > 0)
					json.normalizzazione.push(jsonField);
				
				if(!leastOneChecked)
					leastOneChecked = true;
			}
		});
		
		if(json.normalizzazione.length == 0) {
			$("#normalizeArea").css("display", "inline-block");
			
			$("#normalizeError").text(leastOneChecked ? 
					"Nessuna sostituzione indicata sui campi selezionati" : 
					"Nessun campo selezionato per la normalizzazione");
		}
		
		else {
			$("#normalizeError").css("display", "none");
			$("#confirmSelectionArea").css("display", "none");
			$("#finalResult").css("display", "inline-block");
			$("#finalResultTableArea").css("display", "none");
			var timeout;
			opExecuting = true;
			
			$.ajax({
				url: restUrl + "normalize",
				type: "POST",
				data: JSON.stringify(json),
				success: function(result) {
					window.clearInterval(timeout);
					var html = "<b>" + result.messaggio + "</b><br />";
					data = [];
					
					for(i = 0; i < result.normalizzazione.length; i++) {
						fieldNormalize = result.normalizzazione[i];
						html += "<br /><br /><b>" + $("label[for=\"normalize_" + fieldNormalize.campo + "\"]").text() + ":</b><br />";
						
						for(j = 0; j < fieldNormalize.modifiche.length; j++) {
							html += fieldNormalize.modifiche[j].nuovo_valore + " <b>(" + fieldNormalize.modifiche[j].numero_documenti + ")</b><br />";
							
							data.push([ 
							            $("label[for=\"normalize_" + fieldNormalize.campo + "\"]").text(), 
							            fieldNormalize.modifiche[j].nuovo_valore, 
							            fieldNormalize.modifiche[j].numero_documenti, 
							            
							            fieldNormalize.modifiche[j].errore,
							            
							            fieldNormalize.modifiche[j].errore.length == 0 ? 
							            		"<i class=\"icon-ok-circle btn-icon\"></i><div class=\"icon_title_table\"> OK </div>" : 
							            				"<i class=\"icon-remove-circle btn-icon\"></i><div class=\"icon_title_table\"> KO </div>", 
							           
							            
							]);
						}
					}
					
					$("#infoResult").html(html);
					
		 			$("#finalResultTable").DataTable({
		 				"autoWidth": false,
		 				"order": [],
		 				"ordering": false,
		 				"paging": true,
		 				"searching": false,
		 				"lengthChange": false,
		 				"pageLength": 20,
		 				"pagingType": "full_numbers",
		 				"retrieve": true,
		 				"data": data,
		 				"language": {
		 				    "decimal":        "",
		 				    "emptyTable":     "Non ci sono record",
		 				    "info":           "Mostra da _START_ a _END_ risultati di _TOTAL_ totali",
		 				    "infoEmpty":      "Mostra 0 di 0 MAG",
		 				    "infoFiltered":   "",
		 				    "infoPostFix":    "",
		 				    "lengthMenu":     "Mostra _MENU_ MAG",
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
		 				            {"title": "Campo", "className": "sortable", "type": "string"},
		 				            {"title": "Nuovo Valore", "className": "sortable", "type": "string"},
		 					        {"title": "#", "className": "sortable", "type": "string"},
		 					        {"title": "Errori", "className": "sortable", "type": "string"},
		 					        {"title": "", "className": "sortable actions", "type": "html"}
		 				],
		 				"buttons": [ 
 						    {"extend" : "csv", "title": "MAG_Normalizzazione", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
 						    {"extend" : "pdf", "title": "MAG_Normalizzazione", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
 						],
 						"dom": 'frtipB',
		 				"drawCallback": function(settings) {
		 					$("#finalResultTableArea").css("display", "inline-block");
		 				}
		 			});
		 			
		 			opExecuting = false;
				},
				error: function(json) {
					window.clearInterval(timeout);
					opExecuting = false;

		 			if(json.status != 0)
		 				console.log(JSON.stringify(json, null, '\t'));
				},
				contentType: "application/json; charset=UTF-8",
				dataType: "json"
			});
			
			$("#infoResult").html("<b>Normalizzazione MAG in corso.</b>");
			
			timeout = window.setInterval(function() {
				var txt = $('#infoResult').text();
				var pointIdx = txt.indexOf('.');
				var originalText = txt.substring(0, pointIdx);
				var numPoints = (txt.length - pointIdx) % 3 + 1;
				var textNew = originalText;
				
				for(var i = 0; i < numPoints; i++)
					textNew += ".";
				
				$('#infoResult').html("<b>" + textNew + "</b>");
			}, 1000);
		}
	});
	
	$("#searchResults").DataTable({
		"autoWidth": false,
		"order": [],
		"destroy": true,
		"ordering": false,
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
		"columns": resultTableColumns
	});
	
	$(".normalize_table").DataTable({
		"autoWidth": false,
		"order": [],
		"destroy": true,
		"paging": true,
		"searching": false,
		"ordering": false,
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
		"rowCallback": function(row, data, index) {
			row.id = "normalize_update_row_" + index;
			$(row).addClass("normalize_update_row");
		},
		"columns": [
		            {"title": "Valore Attuale", "className": "sortable normalize_old_value", "type": "string"},
		            {"title": "#", "className": "sortable", "type": "string"},
		            {"title": "Valore da Applicare", "className": "sortable normalize_new_value_container", "type": "html"}
		]
	});
	
	$("#searchSummarySelected").DataTable({
		"autoWidth": false,
		"order": [],
		"destroy": true,
		"ordering": false,
		"paging": true,
		"searching": false,
		"lengthChange": false,
		"pageLength": 20,
		"pagingType": "full_numbers",
		"retrieve": true,
		"data": [],
		"language": {
		    "decimal":        "",
		    "emptyTable":     "Non ci sono MAG selezionati",
		    "info":           "Mostra da _START_ a _END_ risultati di _TOTAL_ totali",
		    "infoEmpty":      "Mostra 0 di 0 MAG",
		    "infoFiltered":   "",
		    "infoPostFix":    "",
		    "lengthMenu":     "Mostra _MENU_ MAG",
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
		            {"title": "MAG/METS", "className": "sortable", "type": "string"},
			        {"title": "", "className": "sortable actions", "type": "html"}
		]
	});
});

function ajaxSearch(dataPost) {
	dataPost.id = [];
	dataPost.not_id = [];
	$("#searchResults").DataTable().destroy();
	
	$("#searchResults").DataTable({
		"autoWidth": false,
		"order": [],
		"destroy": true,
		"paging": true,
		"searching": false,
		"ordering": false,
		"lengthChange": false,
		"pageLength": 30,
		"pagingType": "full_numbers",
		"retrieve": true,
		"processing": true,
		"serverSide": true,
		"ajax": {
			"data": function(d) {
				dataPost.inizio = d.start;
				dataPost.lunghezza_risultati = d.length;
				return JSON.stringify(dataPost);
			},
			"url": restUrl + "vfs/search",
			"type": "POST",
			"contentType": "application/json; charset=UTF-8",
			"dataType": "json",
			"dataSrc": function(json) {
				$(".facet_field_display").each(function(index, elem) {
					vals = [];
					
					for(i = 0; i < json.faccette.length; i++) {
						if(elem.id == "facet_" + json.faccette[i].nome.replace("Facet", "")) {
							vals = json.faccette[i].valori;
							break;
						}
					}
					
					menu = buildFacetMenu(elem.id, vals);
					$("#" + elem.id).html(menu);
				});
				
				$("#solrquery").val(json.solr_query);
				json.recordsTotal = json.numero_risultati;
				json.recordsFiltered = json.numero_risultati;
				json.data = [];
				
				for(i = 0; i < json.mag.length; i++) {
					row = [];
					
					if($("#selectableResults").val() == 'y') {
						row.push("<input type=\"checkbox\" class=\"selectable_result\" value=\"" + json.mag[i].id + "\" " +
								(allMagIDs || listMagIDs.indexOf(json.mag[i].id) >= 0 || (!allMagIDs && listMagIDs.length == 0 && 
										not_listMagIDs.length > 0 && not_listMagIDs.indexOf(json.mag[i].id) < 0) ? "checked=\"checked\" " : "") + 
								"onchange=\"selectMagOp('" + json.mag[i].id + "', $(this).prop('checked'))\" " +
								"title=\"Seleziona il MAG '" + json.mag[i].id + "'\" />" +
								"<div class=\"icon_title_table\">Seleziona</div>");
					}
					
					row.push(json.mag[i].identifier != undefined ? json.mag[i].identifier.join("<br /><br />") : "");
					row.push(json.mag[i].title != undefined ? json.mag[i].title.join("<br /><br />") : "");
					row.push(json.mag[i].year_part_number != undefined ? json.mag[i].year_part_number : "");
					row.push(json.mag[i].issue_part_name != undefined ? json.mag[i].issue_part_name : "");
					row.push(json.mag[i].level != undefined ? json.mag[i].level : "");
					row.push(json.mag[i].public_flag != undefined ? (json.mag[i].public_flag[0] == "0" ? "No" : "Si") : "");
					theUrl = "vfs?idDirectory="+encodeURIComponent(json.mag[i].id);
					var theUrlDettaglio = "magDetail?type="+encodeURIComponent(json.mag[i].documentFormat)+"&amp;id="+encodeURIComponent(json.mag[i].idMag);
					if("container"==json.mag[i].year_part_number)
						theUrl = "vfs-container?idContainer="+encodeURIComponent(json.mag[i].id)+"&amp;idDirectory="+encodeURIComponent(json.mag[i].project);
					if("object"==json.mag[i].year_part_number)
						theUrl = "vfs-container?idContainer="+encodeURIComponent(json.mag[i].idContainer)+"&amp;idDirectory="+encodeURIComponent(json.mag[i].project);
					row.push(
						"<a title=\"Vai al contenitore\" " +
                        									"href=\"" + theUrl + "\">" +
                        									"<i class=\"icon-folder-open btn-icon\"></i></a><div class=\"icon_title_table\"> Dettaglio </div>"+
                        (("directory"!=json.mag[i].year_part_number)?("<a title=\"Vai alla scheda di dettaglio\" " +
									"href=\"" + theUrlDettaglio + "\">" +
									"<i class=\"icon-list btn-icon\"></i></a><div class=\"icon_title_table\"> Dettaglio </div>"):"")

/*
							 +(window.location.href.indexOf("search") >= 0 ? "<a onclick=\"editor('" + json.mag[i].id + "');\" title=\"Modifica MAG\">" +
						   			"<i class=\"icon-edit btn-icon\"></i></a><div class=\"icon_title_table\"> Modifica MAG </div>" : "")
						   			*/
						   			);
					
					json.data.push(row);
				}
				
				return json.data;
			},
			"error": function(error) {
				$('#blockDisplay').addClass("hide").addClass("fade");
			}
		},
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
		"columns": resultTableColumns,
		"buttons": [ 
		    {
		    	"extend" : "csv", 
		    	"title": "MAG_Ricerca", 
		    	"tag": "a", 
		    	"className": "btn-icon", 
		    	"text": "", 
		    	"titleAttr": "Scarica in formato CSV",
		    	"action": function (e, dt, button, config) {
		    		$("#export-csv-window").removeClass("hide").removeClass("fade");
		    		
		    		$.ajax({
		    			"url": restUrl + "searchExportCsv",
		    			"type": "POST",
		    			"dataType": "text",
		    			"contentType": "application/json",
		    			"data": JSON.stringify(dataPost),
		    			"success": function(data) {
		    			    var blob = new Blob([data], {type: "octet/stream"});
		    			    var link = document.createElement('a');
		    			    window.URL = window.URL || window.webkitURL;
		    			    link.href = window.URL.createObjectURL(blob);
		    			    link.download = "MAG_Ricerca.csv";
		    			    $("#export-csv-window").addClass("hide").addClass("fade");
		    			    document.body.appendChild(link);
		    			    link.click();
		    			    document.body.removeChild(link);
		    			}
		    		});
		    	}
		    }
		],
		"dom": 'frtipB',
		"rowCallback": function(row, data, index) {
			$(row).addClass("search_result_row");
		},
		"drawCallback": function(settings) {
			if(this.api().data().length == 0)
				$("#searchResults .dataTables_empty").attr('colspan', resultTableColumns.length);
			
			$('#blockDisplay').addClass("hide").addClass("fade");
			window.clearInterval(timeout);
			$("#resultArea").css("display", "block");
		}
	});
}

function createSelectedSummaryTable() {
	lastBaseSearch.id = listMagIDs;
	lastBaseSearch.not_id = not_listMagIDs;
	$("#searchSummarySelected").DataTable().destroy();

	$("#searchSummarySelected").DataTable({
		"autoWidth": false,
		"order": [],
		"destroy": true,
		"ordering": false,
		"paging": true,
		"searching": false,
		"lengthChange": false,
		"pageLength": 20,
		"pagingType": "full_numbers",
		"retrieve": true,
		"processing": true,
		"serverSide": true,
		"ajax": {
			"data": function(d) {
				lastBaseSearch.inizio = d.start;
				lastBaseSearch.lunghezza_risultati = d.length;
				return JSON.stringify(lastBaseSearch);
			},
			"url": restUrl + "searchMag",
			"type": "POST",
			"contentType": "application/json; charset=UTF-8",
			"dataType": "json",
			"dataSrc": function(json) {
				json.recordsTotal = json.numero_risultati;
				json.recordsFiltered = json.numero_risultati;
				json.data = [];
				
				for(i = 0; i < json.mag.length; i++) {
					row = [ 
						json.mag[i].id, 
						"<a onclick=\"deleteSelectionMag('" + json.mag[i].id + "')\" title=\"Rimuovi il MAG '" + json.mag[i] +"' dalla lista\">" +
								"<i class=\"icon-remove btn-icon\"></i></a><div class=\"icon_title_table\"> Cancella </div>" 
						];
					
					json.data.push(row);
				}
				
				return json.data;
			}
		},
		"language": {
		    "decimal":        "",
		    "emptyTable":     "Non ci sono MAG selezionati",
		    "info":           "Mostra da _START_ a _END_ risultati di _TOTAL_ totali",
		    "infoEmpty":      "Mostra 0 di 0 MAG",
		    "infoFiltered":   "",
		    "infoPostFix":    "",
		    "lengthMenu":     "Mostra _MENU_ MAG",
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
		            {"title": "MAG/METS", "className": "sortable", "type": "string"},
			        {"title": "", "className": "sortable actions", "type": "html"}
		]
	});
}

function buildFacetMenu(prefix, map) {
	html = "";
	valueLength = 0;
	remaining = 0;
	fieldPrefix = prefix.replace("facet_", "");
	disable = (map.length == 1);
	
	for(j = 0; j < map.length; j++) {
		html += "<li" + (valueLength >= 10 ? " style=\"display: none\" class=\"" + fieldPrefix + "OthersDiv\"" : "") + (disable ? " class=\"disabled\"" : "") + ">" +
				"<a class=\"btn-link\"" + (disable ? "" : " onclick=\"facetAjaxSearch('" + fieldPrefix + "', '" +
				map[j].valore
					.replace(/\\/g, "\\\\")
					.replace(/\n/gm, "\\n")
					.replace(/'/g, "\\'")
						 + "');\"") + ">" +
				"<img src=\"images/ico_indice.gif\"></img>" + map[j].valore  + " (" + map[j].numero_documenti + ")</a></li>";
		
		valueLength++;
		
		if(valueLength > 10)
			remaining++;
	}
	
	if(remaining > 0)
		html += "<li id=\"" + fieldPrefix + "OthersLink\" onclick=\"displayOthers('" + fieldPrefix + "');\"><a class=\"btn-link\"><img src=\"images/ico_espandi.gif\"></img>Mostra gli altri risultati</a></li>"
	
	if(html == "")
		return "<li>Nessun risultato disponibile</li>";
	//alert(html);
	return html;
}

function facetAjaxSearch(key, value) {
	found = false;
	filterDiv = $(".filters");
	search = false;
	
	if(lastBaseSearch.filtri_faccette[key.replace("channel_", "")] == undefined || lastBaseSearch.filtri_faccette[key.replace("channel_", "")] == null) {
		lastBaseSearch.filtri_faccette[key.replace("channel_", "")] = [ value ];
		search = true;
	}
	
	else {
		if(lastBaseSearch.filtri_faccette[key.replace("channel_", "")].indexOf(value) < 0) {
			lastBaseSearch.filtri_faccette[key.replace("channel_", "")].push(value);
			search = true;
		}
	}
	
	if(search) {
		listMagIDs = [];
		not_listMagIDs = [];
		allMagIDs = false;
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
		
		ajaxSearch(lastBaseSearch);

		html = filterDiv.length == 0 ? "" : $("#search_filters").html();
		html += "<li class=\"filters\" id=\"" + key + "===" + value + "\">" +
				"<a class=\"btn-link\" onclick=\"deleteFilter(this.parentElement);\">" +
				"[x] " + $("#facet_" + key + "_header").text() + " ==> " + value + "</a></li>";
		
		$("#search_filters").html(html);
	}
}

function displayOthers(prefix) {
	if($("." + prefix + "OthersDiv").css("display") == "none") {
		$("." + prefix + "OthersDiv").css("display", "inline");
		$("#" + prefix + "OthersLink").html("<a class=\"btn-link\">Nascondi risultati</a>");
	}
	
	else {
		$("." + prefix + "OthersDiv").css("display", "none");
		$("#" + prefix + "OthersLink").html("<a class=\"btn-link\"><img src=\"images/ico_espandi.gif\"></img>Mostra gli altri risultati</a>");
	}
}

function deleteFilter(element) {
	var parts = element.id.split("===");
	var key = parts[0];
	var value = parts[1];
	$(element).remove();
	
	listMagIDs = [];
	not_listMagIDs = [];
	allMagIDs = false;
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
	
	if(lastBaseSearch.filtri_faccette[key] != undefined && lastBaseSearch.filtri_faccette[key] != null) {
		idx = lastBaseSearch.filtri_faccette[key].indexOf(parts[1]);
		
		if(idx >= 0)
			lastBaseSearch.filtri_faccette[key].splice(idx, 1);
	}
	
	filterDiv = $(".filters");
	
	if(filterDiv.length == 0)
		$("#search_filters").html("<li>-</li>");
	
	ajaxSearch(lastBaseSearch);
}

function selectMagOp(magID, checked) {
	if(checked) {
		idx = not_listMagIDs.indexOf(magID);
		
		if(idx >= 0)
			not_listMagIDs.splice(idx, 1);
		
		else if(listMagIDs.indexOf(magID) < 0)
			listMagIDs.push(magID);
		
		if(listMagIDs.length == 0 && not_listMagIDs.length == 0)
			allMagIDs = true;
	}
	
	else {
		if(allMagIDs) {
			if(not_listMagIDs.indexOf(magID) < 0)
				not_listMagIDs.push(magID);
			
			allMagIDs = false;
		}
		
		else {
			index = listMagIDs.indexOf(magID);
			
			if(index >= 0)
				listMagIDs.splice(index, 1);
		}
	}
}

function deleteSelectionMag(magID) {
	selectMagOp(magID, false);
	createSelectedSummaryTable();
	$(".normalize_field").trigger("change");
}

function detailMag(idMag, type) {
    var win = window.open("magDetail?id=" + idMag+"&type="+type, '_blank');
    win.focus();
}

function htmlentities(str) {
    var textarea = document.createElement("textarea");
    textarea.innerHTML = str;
    return textarea.innerHTML;
}

function editor(magID) {
	$.ajax({
		url: restUrl + "editor/checkProject?id=" + magID,
		type: "GET",
		success: function(result) {
			if(result) {
				$("#editor-confirm .modal-header h4").text("Modalità Editor MAG '" + magID + "'");
				selectedMagID = magID;
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