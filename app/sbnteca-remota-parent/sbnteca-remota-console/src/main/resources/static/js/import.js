projects = [];
lastSelectedProject = null;
currentProject = null;
statusTimeout = null;
var opExecuting = false;
var changePageHref;
var validateMetsFields = false;

$(document).ready(function() {
	$("#op-executing-warning .modal-header .close").click(function() {
		$("#op-executing-warning").addClass("hide").addClass("fade");
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
	
	$("#Validator\\.DocumentFormat").change(function() {
		if($(this).val() == "mag"){
			$("#metsConfigArea").css("display", "none");
			$("#acqUsageGroup").css("display", "block");
			$("#expUsageGroup").css("display", "block");
			$("#usagea_1").prop( "checked", false );
			$("#usagee_1").prop( "checked", false );
			$("#usagea_2").prop( "checked", false );
			$("#usagee_2").prop( "checked", false );
			$("#usagea_3").prop( "checked", false );
			$("#usagee_3").prop( "checked", false );
		}
		else{
			$("#usagea_1").prop( "checked", false );
			$("#usagee_1").prop( "checked", false );
			$("#usagea_2").prop( "checked", false );
			$("#usagee_2").prop( "checked", false );
			$("#usagea_3").prop( "checked", true );
			$("#usagee_3").prop( "checked", true );
			$("#metsConfigArea").css("display", "block");
			$("#acqUsageGroup").css("display", "none");
			$("#expUsageGroup").css("display", "none");
		}
	});
	
	$("#Mets\\.stprog").keyup(function() {
		$("#metsStprogValidation").removeClass("error");
	});
	
	$("#Mets\\.agency").keyup(function() {
		$("#metsAgencyValidation").removeClass("error");
	});
	

	if(!editableProjects) {
		$("#tablesArea").css("display", "none");
		$("#importBtnArea").css("display", "none");
		$("#confirm-error").css("display", "none");
		$("#importForm").css("display", "block");
		
		var idx = window.location.href.indexOf("validate=");
		
		if(idx > 0) {
			var draftID = window.location.href.substring(idx).replace("validate=", "");
			
			if(draftID.indexOf("&") > 0)
				draftID = draftID.substring(0, draftID.indexOf("&"));
			
			$.ajax({
				url: restUrl + "editor/validate?id=" + draftID,
				success: function(jsonRes) {
					projects.push({ 
						"nome": jsonRes.project, 
						"tutto": false, 
						"mag": [ jsonRes.mag ] 
					});

					$("#selectedMagTable").DataTable({
						"autoWidth": false,
						"order": [],
						"destroy": true,
						"paging": true,
						"ordering": false,
						"searching": false,
						"lengthChange": false,
						"pageLength": 10,
						"pagingType": "full_numbers",
						"retrieve": true,
						"data": [ [
							jsonRes.project, jsonRes.mag, jsonRes.identifier, jsonRes.year_part_number, jsonRes.issue_part_name, "MAG", 
							
							"<a title=\"Rimuovi il MAG '" + jsonRes.mag + "' dalla lista\" " +
					   				"onclick=\"deleteMagFromSelectedList('" + jsonRes.project + "', '" + jsonRes.mag + "');\">" +
					   				"<i class=\"icon-remove btn-icon\"></i></a><div class=\"icon_title_table\"> Cancella </div>"  
					   	] ],
						"language": {
						    "decimal":        "",
						    "emptyTable":     "Nessun elemento selezionato",
						    "info":           "Progetti selezionati: 0; MAG selezionati: 1",
						    "infoEmpty":      "&nbsp;",
						    "infoFiltered":   "",
						    "infoPostFix":    "",
						    "lengthMenu":     "Mostra _MENU_ risultati",
						    "loadingRecords": "Caricamento...",
						    "processing":     "Elaborazione...",
						    "search":         "Filtra:",
						    "zeroRecords":    "Nessun elemento selezionato",
						    "paginate": {
						        "first":      "<<",
						        "last":       ">>",
						        "next":       ">",
						        "previous":   "<"
						    }
						},
						"createdRow": function( row, data, dataIndex ) {
						    if(data[2] == "PROGETTO")
						        $(row).addClass('project');
						},
						"columns": [
						       {"title": "Progetto", "type": "string"},
						       {"title": "MAG", "type": "string"},
						       {"title": "Identifier", "type": "string"},
						       {"title": "Anno/Numero parte", "type": "string"},
						       {"title": "Estremi fasc./Titolo parte", "type": "string"},
						       {"title": "Oggetto", "type": "string"},
						       {"title": "", "type": "html", "className": "sortable actions", "orderable": false},
						],
						"buttons": [ 
						    {"extend" : "csv", "title": "MAGS", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
						    {"extend" : "pdf", "title": "MAGS", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
						],
						"dom": 'f<"deleteSearch">rtipB'
					});
				},
				error: function(obj) {
					if(timeout)
						window.clearInterval(timeout);
					alert(JSON.stringify(JSON.parse(obj.responseText), null, '\t'));
				},
				dataType: "json"
			});
		}
		
		else
			createSummaryTable(0);
	}
	
	else {
		$("#historyWindow .modal-header .close").click(function() {
			$("#historyWindow").addClass("hide").addClass("fade");
		});
		
		$("#historyTable").DataTable({
			"autoWidth": false,
			"order": [],
			"destroy": true,
			"paging": true,
			"ordering": false,
			"lengthChange": false,
			"pageLength": 5,
			"pagingType": "full_numbers",
			"retrieve": true,
			"data": [],
			"language": {
			    "decimal":        "",
			    "emptyTable":     "Cronologia non disponibile",
			    "info":           "_TOTAL_ operazioni",
			    "infoEmpty":      "",
			    "infoFiltered":   "",
			    "infoPostFix":    "",
			    "lengthMenu":     "Mostra _MENU_ risultati",
			    "loadingRecords": "Caricamento...",
			    "processing":     "Elaborazione...",
			    "search":         "Filtra:",
			    "zeroRecords":    "Cronologia non disponibile",
			    "paginate": {
			        "first":      "<<",
			        "last":       ">>",
			        "next":       ">",
			        "previous":   "<"
			    }
			},
			"columns": [
			       {"title": "Data", "className": "sortable", "type": "string", "searchable": false},
			       {"title": "Utente", "className": "sortable", "type": "string"},
			       {"title": "Job", "className": "sortable", "type": "string", "searchable": false},
			       {"title": "Operazione", "className": "sortable", "type": "string"},
			       {"title": "MAG elaborati", "className": "sortable", "type": "string", "searchable": false},
			       {"title": "MAG indicizzati", "className": "sortable", "type": "string", "searchable": false},
			       {"title": "Report", "className": "sortable", "type": "html", "searchable": false}
			],
			"dom": 'f<"deleteSearch">rtip'
		});
		
		$("#historyTable_wrapper .deleteSearch").html("<a class=\"btn-link\" onclick=\"$('#historyTable_wrapper .dataTables_filter input').val('').trigger('keyup');\">" +
				"<img src=\"images/icon_delete.gif\"></img></a>");
		
		$("#magTable").DataTable({
			"autoWidth": false,
			"order": [],
			"destroy": true,
			"paging": true,
			"lengthChange": false,
			"pageLength": 10,
			"pagingType": "full_numbers",
			"retrieve": true,
			"data": [],
			"language": {
			    "decimal":        "",
			    "emptyTable":     "Non sono stati trovati MAG",
			    "info":           "Mostra da _START_ a _END_ MAG di _TOTAL_ totali",
			    "infoEmpty":      "Mostra 0 di 0 MAG",
			    "infoFiltered":   "",
			    "infoPostFix":    "",
			    "lengthMenu":     "Mostra _MENU_ MAG",
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
				   {"title": "Seleziona", "type": "html", "className": "sortable", "searchable": false, "orderable": false},
			       {"title": "File", "className": "sortable", "type": "string"},
			       {"title": "Directory", "className": "sortable", "type": "string"},
			       {"title": "", "type": "html", "className": "sortable", "searchable": false, "orderable": false}
			]
		});
		
		loadFilter = window.location.pathname.indexOf("import") >= 0 ? "new" : "import";
		
		$.ajax({
			url: restUrl + "projects?filter=" + loadFilter,
			success: function(jsonRes) {
				data = [];
				
				jsonRes.sort(function (a, b) {
				    return a.toLowerCase().localeCompare(b.toLowerCase());
				});
				
				for(i = 0; i < jsonRes.length; i++) {
					data.push([
								"<input title=\"Importa intero progetto '" + jsonRes[i] + "'\" type=\"checkbox\" value=\"" + jsonRes[i] + "\" " +
										"onchange=\"addFolder('" +jsonRes[i] + "')\"/><div class=\"icon_title_table\">Importa tutto</div>",
		           		
					           jsonRes[i],
					           
					           "<a title=\"Consulta la cronologia del progetto '" + jsonRes[i] + "'\" " +
					           		"onclick=\"viewHistory('" + jsonRes[i] + "')\"><i class=\"icon-time btn-icon\"></i></a>" +
					           				"<div class=\"icon_title_table\"> Vedi cronologia </div>" +
					           				
					           "<a title=\"Importa MAG singoli relativi al progetto '" + jsonRes[i] + "'\" class=\"btn-link\" " +
					           		"onclick=\"selezionaMag('" + jsonRes[i] + "', this, false)\"><i class=\"icon-folder-open btn-icon\"></i></a>" +
					           				"<div class=\"icon_title_table\"> Apri progetto </div>"
					]);
				}

				$("#projectTable").DataTable({
					"autoWidth": false,
					"order": [],
					"paging": true,
					"ordering": false,
					"lengthChange": false,
					"pageLength": 10,
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
						   {"title": "Seleziona", "type": "html", "className": "", "searchable": false},
					       {"title": "Nome", "className": "sortable", "type": "string"},
					       {"title": "", "type": "html", "className": "sortable actions", "searchable": false}
					      
					],
					"buttons": [ 
					    {"extend" : "csv", "title": "Progetti", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
					    {"extend" : "pdf", "title": "Progetti", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
					],
					"dom": 'f<"deleteSearch">rtipB'
				});
				
				$("#projectTable_wrapper .deleteSearch").html("<a onclick=\"$('#projectTable_wrapper .dataTables_filter input').val('').trigger('keyup');\">" +
						"<i class=\"icon-remove btn-icon\"></i></a>");
			},
			error: function(obj) {
				if(timeout)
					window.clearInterval(timeout);
				alert(JSON.stringify(JSON.parse(obj.responseText), null, '\t'));
			},
			dataType: "json"
		});

		createSummaryTable(0);
	}
	
	$(".acq_usage").change(function() {
		$("#acqUsageGroup").removeClass("error");
		$("#errorAcqUsage").css("display", "none");
		
		for(i = 1; i < 5; i++) {
			if($("#usagea_" + i).prop("checked")){
				$("#usagee_" + i).prop("disabled", false).parent().removeClass("check_disabled");
				if(i==3)
					$("#usagee_5").prop("disabled", false).parent().removeClass("check_disabled");
			}
			else{
				$("#usagee_" + i).prop("checked", false).prop("disabled", true).parent().addClass("check_disabled");
				if(i==3)
					$("#usagee_5").prop("checked", false).prop("disabled", true).parent().addClass("check_disabled");
			}
		}
	});
	
	$("#magRecentBtn").click(function() {
		$.ajax({
			url: restUrl + "selectmag?project=" + lastSelectedProject + "&recent=" + ($(this).val() == "Visualizza recenti"),
			type: "GET",
			success: function(jsonRes) {
				projectMags = [];
				jsonProject = null;
				
				for(i = 0; i < projects.length; i++) {
					if(projects[i].nome == lastSelectedProject) {
						jsonProject = projects[i];
						projectMags = projects[i].mag;
						break;
					}
				}
				
				data = [];
				
				for(i = 0; i < jsonRes.length; i++) {
					pos = jsonRes[i].lastIndexOf("/");		   
					dirinput = jsonRes[i].substring(0, pos + 1);
					file = jsonRes[i].substring(pos + 1);	
					alreadyChecked = false;
					
					for(j = 0; j < projectMags.length; j++) {
						if(projectMags[j] == jsonRes[i]) {
							alreadyChecked = true;
							break;
						}
					}
					
					data.push([
			           "<input type=\"checkbox\" class=\"mag_check\" title=\"Seleziona il MAG/METS '" + jsonRes[i] + "'\" " +
			           		"onchange=\"aggiungiMag('" + jsonRes[i] + "', '" + lastSelectedProject + "')\" value=\"" + jsonRes[i] + "\" " +
			           		"" + (alreadyChecked ? "checked=\"checked\" " : " ") + " " +
			           		"" + (jsonProject != null && jsonProject.tutto ? "disabled=\"disabled\" " : " ") + "/>" +
			           		"<div class=\"icon_title_table\">Seleziona MAG/METS</div>",
				           		
			           file, dirinput,
			           
			           "<a title=\"Visualizza MAG/METS '" + jsonRes[i] + "'\" onclick=\"viewMag1('" + lastSelectedProject + "', " +
			           		"'" + jsonRes[i].replace(/'/g, "\'") + "')\"><i class=\"icon-file btn-icon\"></i></a>" +
			           		"<div class=\"icon_title_table\"> Visualizza MAG/METS </div>"
			           
					]);
				}
				
				searchText = $("#magTable_filter input").val();
				$("#magTable").DataTable().destroy();
				
				$("#magTable").DataTable({
					"autoWidth": false,
					"order": [],
					"destroy": true,
					"paging": true,
					"lengthChange": false,
					"pageLength": 10,
					"pagingType": "full_numbers",
					"retrieve": true,
					"data": data,
					"language": {
					    "decimal":        "",
					    "emptyTable":     "Non sono stati trovati MAG",
					    "info":           "_START_-_END_ di _TOTAL_ MAG",
					    "infoEmpty":      "0-0 di 0 MAG",
					    "infoFiltered":   "",
					    "infoPostFix":    "",
					    "lengthMenu":     "Mostra _MENU_ MAG",
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
						   {"title": "Seleziona", "type": "html", "className": "sortable", "searchable": false, "orderable": false},
					       {"title": "File", "className": "sortable", "type": "string"},
					       {"title": "Directory", "className": "sortable", "type": "string"},
					       {"title": "", "type": "html", "className": "sortable actions", "searchable": false, "orderable": false}
					       
					],
					"drawCallback": function(settings) {
						$("#singleMagArea").show("slide", { direction: "left" }, 500).css("margin-top", offsetHeight);
					},
					"buttons": [ 
					    {"extend" : "csv", "title": lastSelectedProject, "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
					    {"extend" : "pdf", "title": lastSelectedProject, "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
					],
					"dom": 'f<"deleteSearch">rtipB'
				});
				
				$("#magTable_wrapper .deleteSearch").html("<a class=\"btn-link\" onclick=\"$('#magTable_wrapper .dataTables_filter input').val('').trigger('keyup');\">" +
						"<i class=\"icon-remove btn-icon\"></i></a>");
				
				$("#magTable_wrapper .deleteSearch").after("<div class=\"fast_selection row\">" +
						"<a class=\"btn-link\" title=\"Seleziona tutti\" onclick=\"selectMultipleMag('magTable');\"><img src=\"images/select_all_icon.png\"></img></a> " +
						"<a class=\"btn-link\" title=\"Seleziona Pagina\" onclick=\"selectMultipleMag(null);\"><img src=\"images/select_page_icon.png\"></img></a> " +
						"<a class=\"btn-link\" title=\"Deseleziona Pagina\" onclick=\"unselectMultipleMag(null);\"><img src=\"images/unselect_page_icon.png\"></img></a> " +
						"<a class=\"btn-link\" title=\"Deseleziona tutti\" onclick=\"unselectMultipleMag('magTable');\"><img src=\"images/unselect_all_icon.png\"></img></a>" +
						"</div>");
				
				$("#magTable_filter input").val(searchText);
				
				if(searchText != '')
					$("#magTable").DataTable().search(searchText).draw();
			}
		});
		
		if($(this).val() == "Visualizza recenti")
			$(this).val("Visualizza tutti");
		
		else
			$(this).val("Visualizza recenti");
	});
	
	$("#importBtn").click(function() {
		if(projects.length == 0) {
			alert("Non ci sono progetti o documenti da importare, ripetere la selezione");
			return;
		}
		
		var service = $("input[name=service]:checked").val();
		var conf = {};
		
		$(".config_parameter").each(function() {
			conf[this.id] = $(this).val();
		});
		
		usageSets = buildUsageSets();
		
		if(usageSets.usagea.length == 0) {
			$("#acqUsageGroup").addClass("error");
			$("#errorAcqUsage").css("display", "inline-block");
			document.getElementById("acqUsageGroup").scrollIntoView();
			return;
		}
		
		else
			$("#acqUsageGroup").removeClass("error");
		
		if($("#metsConfigArea").css("display") == "block") {
			metsError = false;
			
			if(validateMetsFields && $("#Mets\\.stprog").val() == "") {
				$("#metsStprogValidation").addClass("error");
				metsError = true;
			}
			
			else
				$("#metsStprogValidation").removeClass("error");
			
			if(validateMetsFields && $("#Mets\\.agency").val() == "") {
				$("#metsAgencyValidation").addClass("error");
				metsError = true;
			}
			
			else
				$("#metsAgencyValidation").removeClass("error");
			
			if(metsError)
				return;
		}
		
		var timeout;
		
		var dataPost = {
				"utente": $("#userID").val(),
				"usage_acquisizione": usageSets.usagea,
				"usage_esportazione": usageSets.usagee,
				"sorgente": $('#sorgenteDoc').val(),
				"flag_update_mag": 0,
				"flag_pubblica": $('#flag_pubblica').prop("checked") ? 1 : 0,
				"flag_sovrascrittura": $('#flag_overwrite').prop("checked") ? 1 : 0,
				"vestizione_mag": $('#dress_mag').prop("checked") ? 1 : 0,
				"progetti": projects,
				"configurazione" : conf
			};

		$.ajax({
			url: restUrl + "run" + service.charAt(0).toUpperCase() + service.slice(1),
			type: "POST",
			data: JSON.stringify(dataPost),
			success: function(jsonRes) {
				$("#importJobID").text(jsonRes.id_job);
				$("#reportForm input[name=\"id\"]").val(jsonRes.id_job);
				
				if(service == "import")
					$("#recoveryBtn").css('display','inline');
				
				else
					$("#recoveryBtn").css('display','none');
					
				
				$('#blockDisplay').addClass("hide").addClass("fade");
				window.clearInterval(timeout);
				opExecuting = true;
				
				$.ajax({
					url: restUrl + "" + service,
					data: {
						"id": jsonRes.id_job
					},
					success: function(json) {
						if(json.mag_totali != undefined && json.mag_totali == 0)
							$("#warningNoMag").removeClass("hide");
						
						viewStatusFromJson(json);
						window.clearInterval(statusTimeout);
						opExecuting = false;
					},
					error: function(json) {
						window.clearInterval(statusTimeout);
						opExecuting = false;

			 			if(json.status != 0)
			 				console.log(JSON.stringify(json, null, '\t'));
					}
				});
				
				$("#importForm").css("display", "none");
				$("#importBtnArea").css("display", "none");
				$("#jobStatusArea").css("display", "inline-block");
				statusImport(jsonRes.id_job);
			},
			error: function(obj) {
				if(timeout)
					window.clearInterval(timeout);
				alert(JSON.stringify(JSON.parse(obj.responseText), null, '\t'));
			},
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		});
		
		$('#blockDisplay').removeClass("hide").removeClass("fade");
		
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
	});
	
	$("#updateBtn").click(function() {
		if(projects.length == 0) {
			alert("Non ci sono progetti o documenti da importare, ripetere la selezione");
			return;
		}
		var service = $("input[name=service]:checked").val();
		var restCall = "import";
    	if(service=="import")
    		service = "Update";
    	else{
    		service = "Validate";
    		restCall = "validate";
    	}
		var conf = {};
		
		$(".config_parameter").each(function() {
			conf[this.id] = $(this).val();
		});
		
		usageSets = buildUsageSets();

		if(usageSets.usagea.length == 0) {
			$("#acqUsageGroup").addClass("error");
			$("#errorAcqUsage").css("display", "inline-block");
			return;
		}
		
		else
			$("#acqUsageGroup").removeClass("error");
		
		if($("#metsConfigArea").css("display") == "block") {
			metsError = false;
			
			if(validateMetsFields && $("#Mets\\.stprog").val() == "") {
				$("#metsStprogValidation").addClass("error");
				metsError = true;
			}
			
			else
				$("#metsStprogValidation").removeClass("error");
			
			if(validateMetsFields && $("#Mets\\.agency").val() == "") {
				$("#metsAgencyValidation").addClass("error");
				metsError = true;
			}
			
			else
				$("#metsAgencyValidation").removeClass("error");
			
			if(metsError)
				return;
		}
		
		var timeout;
		
		var dataPost = {
				"utente": $("#userID").val(),
				"usage_acquisizione": usageSets.usagea,
				"usage_esportazione": usageSets.usagee,
				"sorgente": $('#sorgenteDoc').val(),
				"flag_update_mag": 1,
				"flag_pubblica": $('#flag_pubblica').prop("checked") ? 1 : 0,
				"flag_sovrascrittura": $('#flag_overwrite').length == 0 || $('#flag_overwrite').prop("checked") ? 1 : 0,
				"vestizione_mag": $('#dress_mag').prop("checked") ? 1 : 0,
				"progetti": projects,
				"configurazione": conf
			};

		$.ajax({
			url: restUrl + "run"+service,
			type: "POST",
			data: JSON.stringify(dataPost),
			success: function(jsonRes) {
				$("#importJobID").text(jsonRes.id_job);
				$("#reportForm input[name=\"id\"]").val(jsonRes.id_job);
				$('#blockDisplay').addClass("hide").addClass("fade");
				window.clearInterval(timeout);
				opExecuting = true;
				
				$.ajax({
					url: restUrl + restCall,
					data: {
						"id": jsonRes.id_job
					},
					success: function(json) {
						if(json.mag_totali != undefined && json.mag_totali == 0)
							$("#warningNoMag").removeClass("hide");
						
						viewStatusFromJson(json);
						window.clearInterval(statusTimeout);
						opExecuting = false;
					},
					error: function(json) {
						window.clearInterval(statusTimeout);
						opExecuting = false;

			 			if(json.status != 0)
			 				console.log(JSON.stringify(json, null, '\t'));
					}
				});

				$("#importForm").css("display", "none");
				$("#importBtnArea").css("display", "none");
				$("#jobStatusArea").css("display", "inline-block");
				statusImport(jsonRes.id_job);
			},
			error: function(obj) {
				$('#blockDisplay').addClass("hide").addClass("fade");
				window.clearInterval(timeout);
				alert(JSON.stringify(JSON.parse(obj.responseText), null, '\t'));
			},
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		});
		
		$('#blockDisplay').removeClass("hide").removeClass("fade");
		
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
	});
	
	$("#recoveryBtn").click(function() {
		var jobID = $("#importJobID").text();
		var timeout;
		
		$.ajax({
			url: restUrl + "recoverImport",
			data: {
				"id" : jobID
			},
			success: function(jsonRes) {
				$('#blockDisplay').addClass("hide").addClass("fade");
				window.clearInterval(timeout);
				opExecuting = true;
				
				if(jsonRes.stato > 0) {
					$.ajax({
						url: restUrl + "" + service,
						data: {
							"id": jsonRes.id_job
						},
						success: function(json) {
							if(json.mag_totali != undefined && json.mag_totali == 0)
								$("#warningNoMag").removeClass("hide");
							
							viewStatusFromJson(json);
							window.clearInterval(statusTimeout);
							opExecuting = false;
						},
						error: function(json) {
							window.clearInterval(statusTimeout);
							opExecuting = false;

				 			if(json.status != 0)
				 				console.log(JSON.stringify(json, null, '\t'));
						}
					});
					
					$("#importForm").css("display", "none");
					$("#importBtnArea").css("display", "none");
					$("#jobStatusArea").css("display", "inline-block");
					statusImport(jsonRes.id_job);
				}
				
				else
					alert(JSON.stringify(jsonRes, null, '\t'));
			},
			error: function(obj) {
				if(timeout)
					window.clearInterval(timeout);
				alert(JSON.stringify(JSON.parse(obj.responseText), null, '\t'));
			},
			dataType: "json"
		});

		$('#blockDisplay').removeClass("hide").removeClass("fade");

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
	});
	
	$("#changeUsageBtn").click(function() {
		if(projects.length == 0) {
			alert("Non ci sono progetti o documenti da importare, ripetere la selezione");
			return;
		}
		
		var conf = {};
		
		$(".config_parameter").each(function() {
			conf[this.id] = $(this).val();
		});
		
		usageSets = buildUsageSets();
		
		if(usageSets.usagea.length == 0) {
			$("#acqUsageGroup").addClass("error");
			$("#errorAcqUsage").css("display", "inline-block");
			return;
		}
		
		else
			$("#acqUsageGroup").removeClass("error");
		
		var timeout;
		
		var dataPost = {
				"utente": $("#userID").val(),
				"usage_acquisizione": usageSets.usagea,
				"usage_esportazione": usageSets.usagee,
				"sorgente": $('#sorgenteDoc').val(),
				"progetti": projects
			};

		$.ajax({
			url: restUrl + "runChangeUsage",
			type: "POST",
			data: JSON.stringify(dataPost),
			success: function(jsonRes) {
				$("#importJobID").text(jsonRes.id_job);
				$("#reportForm input[name=\"id\"]").val(jsonRes.id_job);
				
				$('#blockDisplay').addClass("hide").addClass("fade");
				window.clearInterval(timeout);
				opExecuting = true;
				
				$.ajax({
					url: restUrl + "import",
					data: {
						"id": jsonRes.id_job
					},
					success: function(json) {
						if(json.mag_totali != undefined && json.mag_totali == 0)
							$("#warningNoMag").removeClass("hide");
						
						viewStatusFromJson(json);
						window.clearInterval(statusTimeout);
						opExecuting = false;
					},
					error: function(json) {
						window.clearInterval(statusTimeout);
						opExecuting = false;

			 			if(json.status != 0)
			 				console.log(JSON.stringify(json, null, '\t'));
					}
				});

				$("#importForm").css("display", "none");
				$("#importBtnArea").css("display", "none");
				$("#jobStatusArea").css("display", "inline-block");
				statusImport(jsonRes.id_job);
			},
			error: function(obj) {
				$('#blockDisplay').addClass("hide").addClass("fade");
				window.clearInterval(timeout);
				alert(JSON.stringify(JSON.parse(obj.responseText), null, '\t'));
			},
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		});
		
		$('#blockDisplay').removeClass("hide").removeClass("fade");
		
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
	});
});

function OpenInNewTab(url) {
    var win = window.open(url, '_blank');
    win.focus();
}

function viewStatusFromJson(json) {
	if(json.tempo_inizio_processo != undefined)
		$("#job_start").html(json.tempo_inizio_processo);
	
	if(json.tempo_fine_processo != undefined)
		$("#job_end").html(json.tempo_fine_processo);
	
	if(json.tempo_inizio_validazione != undefined)
		$("#validate_start").html(json.tempo_inizio_validazione);
	
	if(json.tempo_fine_validazione != undefined)
		$("#validate_end").html(json.tempo_fine_validazione);
	
	
	var magProcessed = json.mag_elaborati != undefined ? json.mag_elaborati : 0;
	var magTotal = json.mag_totali != undefined ? json.mag_totali : 0;
	var magOK = json.mag_OK != undefined ? json.mag_OK : 0;
	var magKO = json.mag_KO != undefined ? json.mag_KO : 0;
	var digitalObjects = json.oggetti_digitali == undefined ? null : json.oggetti_digitali;
	
	var progress = parseInt(magTotal == 0 ? 100 : magProcessed * 100 / magTotal);
	$('.percent').html(progress + "%");
	$('.bar').width(progress + "%");
	
	$("#status").html(json.messaggio + "; XML elaborati: " + magProcessed + "; " +
			"XML totali: " + magTotal + "; XML OK: " + magOK + "; XML KO: " + magKO +
			(digitalObjects != null ? "; Oggetti digitali: " + digitalObjects : ""));
}

function addFolder(project) {	
	for(i = 0; i < projects.length; i++) {
		p = projects[i];
		
		if(p.nome == project) {
			if(!$("input[type=checkbox][value=\"" + project + "\"]").prop("checked"))
				projects.splice(i, 1);
			
			else {
				p.tutto = true;
				p.mag = [];
				projects[i] = p;
			}

			if(currentProject != null && currentProject == project) {
				var allBox = $("#magTable").DataTable().$(".mag_check");
				
				for(j = 0; j < allBox.length; j++) {
					if($("input[type=checkbox][value=\"" + project + "\"]").prop("checked"))
						$(allBox[j]).prop("checked", false);
					
					$(allBox[j]).prop("disabled", $("input[type=checkbox][value=\"" + project + "\"]").prop("checked"));
				}
			}

			$("#selectedMagTable").DataTable().destroy();
			createSummaryTable(0);
			return;
		}
	}
	
	if($("input[type=checkbox][value=\"" + project + "\"]").prop("checked")) {
		p = { "nome": project, "tutto": true, "mag": [] }
		projects.push(p);
	}

	if(currentProject != null && currentProject == project) {
		var allBox = $("#magTable").DataTable().$(".mag_check");
		
		for(j = 0; j < allBox.length; j++) {
			if($("input[type=checkbox][value=\"" + project + "\"]").prop("checked"))
				$(allBox[j]).prop("checked", false);
			
			$(allBox[j]).prop("disabled", $("input[type=checkbox][value=\"" + project + "\"]").prop("checked"));		
		}
	}
	
	$("#selectedMagTable").DataTable().destroy();
	createSummaryTable(0);
}

function selezionaMag(project, click_elem, recent) {
	lastSelectedProject = project;
	
	$.ajax({
		url: restUrl + "selectmag?project=" + project + "&recent=" + recent,
		type: "GET",
		success: function(jsonRes) {
			offsetHeight = $(click_elem).offset().top - $("#tablesArea").offset().top - 15;
			$("#singleMagArea").hide();
			currentProject = project;
			$("#magRecentBtn").val("Visualizza recenti");
			$("#magTableHeader").text("MAG (Progetto '" + currentProject + "')");
			projectMags = [];
			jsonProject = null;
			
			for(i = 0; i < projects.length; i++) {
				if(projects[i].nome == project) {
					jsonProject = projects[i];
					projectMags = projects[i].mag;
					break;
				}
			}
			
			data = [];
			
			for(i = 0; i < jsonRes.length; i++) {
				pos = jsonRes[i].lastIndexOf("/");		   
				dirinput = jsonRes[i].substring(0, pos + 1);
				file = jsonRes[i].substring(pos + 1);	
				alreadyChecked = false;
				
				for(j = 0; j < projectMags.length; j++) {
					if(projectMags[j] == jsonRes[i]) {
						alreadyChecked = true;
						break;
					}
				}
				
				data.push([
		           "<input type=\"checkbox\" class=\"mag_check\" title=\"Seleziona il MAG '" + jsonRes[i] + "'\" " +
		           		"onchange=\"aggiungiMag('" + jsonRes[i] + "', '" + project + "')\" value=\"" + jsonRes[i] + "\" " +
		           		"" + (alreadyChecked ? "checked=\"checked\" " : " ") + " " +
		           		"" + (jsonProject != null && jsonProject.tutto ? "disabled=\"disabled\" " : " ") + "/>" +
		           		"<div class=\"icon_title_table\">Seleziona MAG</div>",
			           		
		           file, dirinput,
		           
		           "<a title=\"Visualizza MAG '" + jsonRes[i] + "'\" onclick=\"viewMag1('" + project + "', " +
		           		"'" + jsonRes[i].replace(/'/g, "\'") + "')\"><i class=\"icon-file btn-icon\"></i></a>" +
		           		"<div class=\"icon_title_table\"> Visualizza MAG </div>"
		           
				]);
			}
			
			$("#magTable").DataTable().destroy();
			
			$("#magTable").DataTable({
				"autoWidth": false,
				"order": [],
				"destroy": true,
				"paging": true,
				"lengthChange": false,
				"pageLength": 10,
				"pagingType": "full_numbers",
				"retrieve": true,
				"data": data,
				"language": {
				    "decimal":        "",
				    "emptyTable":     "Non sono stati trovati MAG",
				    "info":           "_START_-_END_ di _TOTAL_ MAG",
				    "infoEmpty":      "0-0 di 0 MAG",
				    "infoFiltered":   "",
				    "infoPostFix":    "",
				    "lengthMenu":     "Mostra _MENU_ MAG",
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
					   {"title": "Seleziona", "type": "html", "className": "sortable", "searchable": false, "orderable": false},
				       {"title": "File", "className": "sortable", "type": "string"},
				       {"title": "Directory", "className": "sortable", "type": "string"},
				       {"title": "", "type": "html", "className": "sortable actions", "searchable": false, "orderable": false}
				       
				],
				"drawCallback": function(settings) {
					$("#singleMagArea").show("slide", { direction: "left" }, 500).css("margin-top", offsetHeight);
				},
				"buttons": [ 
				    {"extend" : "csv", "title": project, "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
				    {"extend" : "pdf", "title": project, "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
				],
				"dom": 'f<"deleteSearch">rtipB'
			});
			
			$("#magTable_wrapper .deleteSearch").html("<a class=\"btn-link\" onclick=\"$('#magTable_wrapper .dataTables_filter input').val('').trigger('keyup');\">" +
					"<i class=\"icon-remove btn-icon\"></i></a>");
			
			$("#magTable_wrapper .deleteSearch").after("<div class=\"fast_selection row\">" +
					"<a class=\"btn-link\" title=\"Seleziona tutti\" onclick=\"selectMultipleMag('magTable');\"><img src=\"images/select_all_icon.png\"></img></a> " +
					"<a class=\"btn-link\" title=\"Seleziona Pagina\" onclick=\"selectMultipleMag(null);\"><img src=\"images/select_page_icon.png\"></img></a> " +
					"<a class=\"btn-link\" title=\"Deseleziona Pagina\" onclick=\"unselectMultipleMag(null);\"><img src=\"images/unselect_page_icon.png\"></img></a> " +
					"<a class=\"btn-link\" title=\"Deseleziona tutti\" onclick=\"unselectMultipleMag('magTable');\"><img src=\"images/unselect_all_icon.png\"></img></a>" +
					"</div>");
		},
		error: function(obj) {
			alert(JSON.stringify(JSON.parse(obj.responseText), null, '\t'));
		},
		contentType: "application/json; charset=UTF-8",
		dataType: "json"
	});
}

function statusImport(jobID) {
	$(document).ready(function() {
		var statusType = jobID.startsWith("imp") ? "import" : "validate";
		$("#reportForm").attr("action", restUrl + "" + statusType + "/status");
		
		statusTimeout = window.setInterval(function() {
			$.ajax({
				url: restUrl + "" + statusType + "/status",
				data: {
					"id": jobID
				},
				success: function(json) {
					if(json.mag_totali != undefined && json.mag_totali == 0)
						$("#warningNoMag").removeClass("hide");
					
					if(json.stato > 0)
						viewStatusFromJson(json);
				},
				error: function(json) {
					window.clearInterval(statusTimeout);

		 			if(json.status != 0)
		 				console.log(JSON.stringify(json, null, '\t'));
				}
			});
		}, 2000);
	});
}

function viewMag1(project, relativePath) {	
    OpenInNewTab(restUrl + "mag?project=" + project + "&rel=" + relativePath);
    //OpenInNewTab(restUrl + "getmagxml?id=" + relativePath);
}

function aggiungiMag(path, project) {	
	projectFound = false;
	
	for(i = 0; i < projects.length; i++) {
		p = projects[i];
		
		if(p.nome == project) {
			projectFound = true;
			
			if(!p.tutto) {
				found = false;
				
				for(j = 0; j < p.mag.length; j++) {
					if(p.mag[j] == path) {
						found = true;
						
						if(!$("input[value='" + path + "']").prop("checked")) {
							p.mag.splice(j, 1);
							
							if(p.mag.length == 0)
								projects.splice(i, 1);
						}
						
						$("#selectedMagTable").DataTable().destroy();
						createSummaryTable(0);
						break;
					}
				}
				
				if(!found && $("input[value='" + path + "']").prop("checked")) {
					p.mag.push(path);
					$("#selectedMagTable").DataTable().destroy();
					createSummaryTable(0);
				}
				
				break;
			}
		}
	}
	
	if(!projectFound) {
		projects.push({ "nome": project, "tutto": false, "mag": [ path ] });
		$("#selectedMagTable").DataTable().destroy();
		createSummaryTable(0);
	}
}

function buildUsageSets() {
	usagea = [];
	usagee = [];
	
	for(i = 1; i < 5; i++) {
		if($("#usagea_" + i).prop("checked"))
			usagea.push(i);
		
	}
	for(i = 1; i < 6; i++) {
		var contains = false;
		if($("#usagee_" + i).prop("checked")){
			usagee.push(i);
			if(i==4)
				contains = true;
			if(i==5 && !contains)
				usagee.push(4);
		}
	}

	return {
		"usagea" : usagea,
		"usagee" : usagee
	}
}

function confirm(buttonElem) {
	if($(buttonElem).val() == "Conferma Selezione") {
		if(projects.length == 0)
			$("#confirm-error").css("display", "inline-block");
		
		else {
			$("#tablesArea").css("display", "none");
			$("#confirm-error").css("display", "none");
			$("#importForm").css("display", "block");
			$(buttonElem).val("Modifica Selezione");
		}
	}
	
	else {
		$("#tablesArea").css("display", "inline-block");
		$("#confirm-error").css("display", "none");
		$("#importForm").css("display", "none");
		$(buttonElem).val("Conferma Selezione");
	}
}

function viewHistory(project) {
	$.ajax({
		url: restUrl + "history/" + project,
		success: function(jsonRes) {
			$("#historyTable").DataTable().destroy();
			
			if(jsonRes.stato < 0)
				$("#history-error").css("display", "inline-block").text(jsonRes.messaggio);
			
			else
				$("#history-error").css("display", "none");
			
			data = [];
			
			for(i = 0; i < jsonRes.cronologia.length; i++) {
				data.push([
				           jsonRes.cronologia[i].data, jsonRes.cronologia[i].username, jsonRes.cronologia[i].id_job,
				           jsonRes.cronologia[i].operazione, jsonRes.cronologia[i].mag_elaborati, jsonRes.cronologia[i].mag_indicizzati,
				           jsonRes.cronologia[i].id_job.startsWith("imp_") ? 
				        		   "<a href=\"" + restUrl + "import/status?id=" + jsonRes.cronologia[i].id_job + "&report=ALL\">" +
				        		   		"<img src=\"images/xml_icon.png\"></img></a><div class=\"icon_title_table\"> Report Job </div>" :
				        		   "<div class=\"icon_title_table\"> Report Job </div>"
				        		   			
				]);
			}

			$("#historyTable").DataTable({
				"autoWidth": false,
				"order": [],
				"destroy": true,
				"paging": true,
				"ordering": false,
				"lengthChange": false,
				"pageLength": 5,
				"pagingType": "full_numbers",
				"retrieve": true,
				"data": data,
				"language": {
				    "decimal":        "",
				    "emptyTable":     "Cronologia non disponibile",
				    "info":           "_TOTAL_ operazioni",
				    "infoEmpty":      "",
				    "infoFiltered":   "",
				    "infoPostFix":    "",
				    "lengthMenu":     "Mostra _MENU_ risultati",
				    "loadingRecords": "Caricamento...",
				    "processing":     "Elaborazione...",
				    "search":         "Filtra:",
				    "zeroRecords":    "Cronologia non disponibile",
				    "paginate": {
				        "first":      "<<",
				        "last":       ">>",
				        "next":       ">",
				        "previous":   "<"
				    }
				},
				"columns": [
				       {"title": "Data", "className": "sortable", "type": "string", "searchable": false},
				       {"title": "Utente", "className": "sortable", "type": "string"},
				       {"title": "Job", "className": "sortable", "type": "string", "searchable": false},
				       {"title": "Operazione", "className": "sortable", "type": "string"},
				       {"title": "MAG elaborati", "className": "sortable", "type": "string", "searchable": false},
				       {"title": "MAG indicizzati", "className": "sortable", "type": "string", "searchable": false},
				       {"title": "Report", "className": "sortable", "type": "html", "searchable": false}
				],
				"buttons": [ 
				    {"extend" : "csv", "title": project, "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
				    {"extend" : "pdf", "title": project, "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
				],
				"dom": 'f<"deleteSearch">rtipB'
			});
			
			$("#historyTable_wrapper .deleteSearch").html("<a class=\"btn-link\" onclick=\"$('#historyTable_wrapper .dataTables_filter input').val('').trigger('keyup');\">" +
					"<i class=\"icon-remove btn-icon\"></i></a>");
			
			$("#historyWindow .modal-header h4").text("Cronologia Progetto '" + project + "'");
			$("#historyWindow").removeClass("hide").removeClass("fade");
		},
		error: function(obj) {
			if(timeout)
				window.clearInterval(timeout);
			alert(JSON.stringify(JSON.parse(obj.responseText), null, '\t'));
		},
		dataType: "json"
	});
}

function deleteMagFromSelectedList(project, path) {
	if(opExecuting)
		return;
	
	projectIndex = -1;

	for(i = 0; i < projects.length; i++) {
		if(projects[i].nome == project) {
			projectIndex = i;
			
			if(!projects[i].tutto) {
				index = -1;
				
				for(j = 0; j < projects[i].mag.length; j++) {
					if(projects[i].mag[j] == path) {
						index = j;
						break;
					}
				}
				
				if(index >= 0)
					projects[i].mag.splice(index, 1);
				
				if(currentProject == project)
					$("#magTable").DataTable().$("input[value='" + path + "']").prop("checked", false);
			}
			
			else {
				projects[i].tutto = false;
				$("#projectTable").DataTable().$("input[value='" + project + "']").prop("checked", false);
			}
		}
	}
	
	if(projectIndex >= 0 && projects[projectIndex].mag.length == 0)
		projects.splice(projectIndex, 1);
	
	pageNum = $("#selectedMagTable").DataTable().page();
	$("#selectedMagTable").DataTable().destroy();
	createSummaryTable(pageNum);
}

function undoSelection(buttonElem) {
	$("#singleMagArea").css("display", "none");
	$("#tablesArea").css("display", "inline-block");
	$("#confirm-error").css("display", "none");
	$("#importForm").css("display", "none");
	$("#projectTable").DataTable().$("input[type='checkbox']").prop("checked", false);
	$(buttonElem.previousSibling.previousSibling).val("Conferma Selezione");
	projects = [];
	$("#selectedMagTable").DataTable().destroy();
	createSummaryTable(0);
}

function createSummaryTable(pageNum) {
	selectedMagList = [];
	var countProjects = 0;
	var countMags = 0;
	
	for(i = 0; i < projects.length; i++) {
		p = projects[i];
		
		if(p.tutto) {
			selectedMagList.push([ p.nome, " - ", "PROGETTO", 
				"<a title=\"Rimuovi il progetto '" + p.nome + "' dalla lista\" " +
			    		"onclick=\"deleteMagFromSelectedList('" + p.nome + "', null);\"><i class=\"icon-remove btn-icon\"></i></a>" +
			    				"<div class=\"icon_title_table\"> Cancella </div>"
			]);
			
			countProjects++;
		}
		
		else {
			for(j = 0; j < p.mag.length; j++) {
				selectedMagList.push([ p.nome, p.mag[j], "MAG/METS",
					"<a title=\"Rimuovi il MAG/METS '" + p.mag[j] + "' dalla lista\" " +
				   		"onclick=\"deleteMagFromSelectedList('" + p.nome + "', '" + p.mag[j] + "');\">" +
				   		"<i class=\"icon-remove btn-icon\"></i></a><div class=\"icon_title_table\"> Cancella </div>" 
				]);
				
				countMags++;
			}
		}
	}
	
	lastPageNum = Math.ceil(selectedMagList.length / 10) - 1;

	$("#selectedMagTable").DataTable({
		"autoWidth": false,
		"order": [],
		"destroy": true,
		"paging": true,
		"ordering": false,
		"searching": false,
		"lengthChange": false,
		"pageLength": 10,
		"pagingType": "full_numbers",
		"retrieve": true,
		"data": selectedMagList,
		"language": {
		    "decimal":        "",
		    "emptyTable":     "Nessun elemento selezionato",
		    "info":           "Progetti selezionati: " + countProjects + "; MAG selezionati: " + countMags,
		    "infoEmpty":      "&nbsp;",
		    "infoFiltered":   "",
		    "infoPostFix":    "",
		    "lengthMenu":     "Mostra _MENU_ risultati",
		    "loadingRecords": "Caricamento...",
		    "processing":     "Elaborazione...",
		    "search":         "Filtra:",
		    "zeroRecords":    "Nessun elemento selezionato",
		    "paginate": {
		        "first":      "<<",
		        "last":       ">>",
		        "next":       ">",
		        "previous":   "<"
		    }
		},
		"createdRow": function( row, data, dataIndex ) {
		    if(data[2] == "PROGETTO")
		        $(row).addClass('project');
		},
		"columns": [
		       {"title": "Progetto", "type": "string"},
		       {"title": "MAG/METS", "type": "string"},
		       {"title": "Oggetto", "type": "string"},
		       {"title": "", "type": "html", "className": "sortable actions", "orderable": false},
		],
		"buttons": [ 
		    {"extend" : "csv", "title": "MAGS", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
		    {"extend" : "pdf", "title": "MAGS", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
		],
		"dom": 'f<"deleteSearch">rtipB'
	}).page(pageNum <= lastPageNum ? pageNum : lastPageNum).draw(false);
}

function selectMultipleMag(idTable) {
	var allBox = idTable == null ? $(".mag_check") : $("#" + idTable).DataTable().$(".mag_check", {"filter":"applied"});
	
	for(i = 0; i < allBox.length; i++)
		$(allBox[i]).prop("checked", true);
	
	
	jsonProject = { "nome": currentProject, "tutto": false, "mag": [] };
	projectFound = false;
	
	for(i = 0; i < projects.length; i++) {
		if(currentProject == projects[i].nome) {
			projectFound = true;
			jsonProject = projects[i];
			break;
		}
	}
	
	if(!jsonProject.tutto) {
		for(i = 0; i < allBox.length; i++) {
			magFound = false;
			
			for(j = 0; j < jsonProject.mag.length; j++) {
				if(jsonProject.mag[j] == allBox[i].value) {
					magFound = true;
					break;
				}
			}
			
			if(!magFound)
				jsonProject.mag.push(allBox[i].value);
		}
	}
	
	if(!projectFound)
		projects.push(jsonProject);
	
	$("#selectedMagTable").DataTable().destroy();
	createSummaryTable(0);
}

function unselectMultipleMag(idTable) {
	var allBox = idTable == null ? $(".mag_check") : $("#" + idTable).DataTable().$(".mag_check" , {"filter":"applied"});
	
	for(i = 0; i < allBox.length; i++)
		$(allBox[i]).prop("checked", false);
	
	jsonProject = null;
	projectIndex = -1;
	
	for(i = 0; i < projects.length; i++) {
		if(currentProject == projects[i].nome) {
			jsonProject = projects[i];
			projectIndex = i;
			break;
		}
	}
	
	if(jsonProject != null && !jsonProject.tutto) {
		for(i = 0; i < allBox.length; i++) {
			for(j = 0; j < jsonProject.mag.length; j++) {
				if(jsonProject.mag[j] == allBox[i].value) {
					jsonProject.mag.splice(j, 1);
					break;
				}
			}
		}
	}
	
	if(jsonProject != null && jsonProject.mag.length == 0 && !jsonProject.tutto && projectIndex > -1)
		projects.splice(projectIndex, 1);
	
	$("#selectedMagTable").DataTable().destroy();
	createSummaryTable(0);
}
