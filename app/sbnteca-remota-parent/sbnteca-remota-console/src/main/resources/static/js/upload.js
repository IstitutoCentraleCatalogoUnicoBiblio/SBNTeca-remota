zipFormats = [ "zip", "tar", "tar.gz", "tar.bz2", "tar.Z", "gz", "bz2", "Z" ];
var directoryServer;
var opExecuting = false;
var changePageHref;

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
	
	$("#project").keyup(function() {
		val = $(this).val();
		
		if(val != null && val != undefined && val != "")
			$("#error_project").css("display", "none");
	});
	
	$("#inputFile").change(function() {
		$("#displayFileName").text($(this).val().split('\\').pop());
	});
	
	$("input[name='type_upload_search']").change(function() {
		$("#directoryServerSearch").val($(this).val() == "compress" ? "Ricerca" : "Mostra file");
	});
	
	if(window.location.href.indexOf("uploadUpdate") >= 0
	|| window.location.href.indexOf("uploadResources") >= 0
	|| window.location.href.indexOf("uploadUnimarc") >= 0
	) {
		var add = (window.location.href.indexOf("uploadResources") >= 0
			|| window.location.href.indexOf("uploadUnimarc") >= 0)
			?"&vfs=true":"";
		$.ajax({
			url: restUrl + "projects?filter=all"+add,
			type: "GET",
			success: function(values) {
				$("#project").append($('<option>', { value: "", text: "", selected: true }));
				
				for(i = 0; i < values.length; i++)
					$("#project").append($('<option>', { value: values[i], text: values[i] }));
			},
			error: function(obj, status, error) {
				alert(status + "\n" + error);
			},
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		});
	}
	
	$("#uploadClientMode").click(function() {
		$("#fileClientField").css("display", "block");
		$("#fileServerField").css("display", "none");
		$("#buttonClientField").css("display", "block");
		$("#buttonServerField").css("display", "none");
		$("#uploadMode li.active").removeClass("active");
		$("#uploadClientMode").parent().addClass("active");
		$("#error_display").css("display", "none");
		$("#error_project").css("display", "none");
	});
	
	$("#uploadServerMode").click(function() {
		$("#fileClientField").css("display", "none");
		$("#fileServerField").css("display", "block");
		$("#buttonClientField").css("display", "none");
		$("#buttonServerField").css("display", "block");
		$("#uploadMode li.active").removeClass("active");
		$("#uploadServerMode").parent().addClass("active");
		$("#error_display").css("display", "none");
		$("#error_project").css("display", "none");
	});
	
	$("#uploadServerFileTable").DataTable({
		"autoWidth": false,
		"order": [],
		"destroy": true,
		"paging": true,
		"ordering": false,
		"lengthChange": false,
		"pageLength": 10,
		"pagingType": "full_numbers",
		"retrieve": true,
		"data": [],
		"language": {
		    "decimal":        "",
		    "emptyTable":     "Non sono stati trovati file",
		    "info":           "Disponibili _TOTAL_ file",
		    "infoEmpty":      "Non ci sono file disponibili",
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
		"buttons": [ 
		    {"extend" : "csv", "title": "Upload File Server", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
		    {"extend" : "pdf", "title": "Upload File Server", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
		],
		"drawCallback": function(settings) {
			if(this.api().data().length == 0)
				$("#uploadServerFileTable .dataTables_empty").attr('colspan', 1);
		},
		"dom": 'f<"deleteSearch">rtipB'
	});
	
	$(".deleteSearch").html("<a onclick=\"$('.dataTables_filter input').val('').trigger('keyup');\">" +
			"<i class=\"icon-remove btn-icon\"></i></a>");
	
	$("#directoryServerSearch").click(function() {
		$("#error_display").css("display", "none");
		var call = "upload/search?dir=";
		if(window.location.href.indexOf("uploadResources") >= 0)
			call = "uploadResources/search?dir=";
		if(window.location.href.indexOf("uploadUnimarc") >= 0)
			call = "uploadResources/search?dir=";
		$.ajax({
			url: restUrl + call + $("#directoryServer").val() + "&type=" + $("input[name='type_upload_search']:checked").val(),
			success: function(jsonRes) {
				data = [];
				directoryServer = $("#directoryServer").val();
				
				cols = [  {"title": "Nome File", "className": "sortable", "type": "string"} ];
				
				if($("input[name='type_upload_search']:checked").val() == "compress")
					cols.push( {"title": "", "className": "sortable", "type": "html", "searchable": false} );
				
				for(i = 0; i < jsonRes.length; i++) {
					record = [ jsonRes[i] ];
					
					if($("input[name='type_upload_search']:checked").val() == "compress")
						record.push("<input type=\"radio\" name=\"" + directoryServer + "" +
					    		"\" value=\"" + jsonRes[i] + "\" " +
					    		"" + (i == 0 ? "checked=\"checked\" " : " ") + "/>" );
					
					data.push(record);
				}
				
				$("#serverFiles").css("display", "block");
				$("#uploadServerFileTable").DataTable().destroy();
				$("#uploadServerFileTable").find('thead').remove();
				$("#uploadServerFileTable").find('tbody').remove();
				$("#uploadServerFileTable").find('tfoot').remove();

				$("#uploadServerFileTable").DataTable({
					"autoWidth": false,
					"order": [],
					"destroy": true,
					"paging": true,
					"ordering": false,
					"lengthChange": false,
					"pageLength": 10,
					"pagingType": "full_numbers",
					"retrieve": true,
					"data": data,
					"language": {
					    "decimal":        "",
					    "emptyTable":     "Non sono stati trovati file",
					    "info":           "Disponibili _TOTAL_ file",
					    "infoEmpty":      "Non ci sono file disponibili",
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
					"columns": cols,
					"buttons": [ 
					    {"extend" : "csv", "title": "Upload File Server", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
					    {"extend" : "pdf", "title": "Upload File Server", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
					],
					"drawCallback": function(settings) {
						if(this.api().data().length == 0)
							$("#uploadServerFileTable .dataTables_empty").attr('colspan', cols.length);
					},
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
	
	var call = restUrl + "upload";
	if($('#updateCall').val() == 'y') {
		call = restUrl + "upload/update";
	}
	if($('#updateCall').val() == 'r') {
		call = restUrl + "upload/resources";
	}
	if($('#updateCall').val() == 'mrc') {
		call = restUrl + "upload/unimarc";
	}

	
	$("#btnUpload").click(function() {
		error = false;
		var project = $('#project').val();
		var newProject = $('#newProject')?$('#newProject').val():"";
		var createContainer = ($('#createContainer') && $('#createContainer').length>0)?(""+$('#createContainer')[0].checked):"false";
		if(newProject && newProject.length>0)
			project = newProject;
		directoryServer = $("#directoryServer").val();
		$("#serverFiles").css("display", "none");

		if($("#inputFile").val() == "" || $("#inputFile").val() == undefined || $("#inputFile").val() == null) {
			$("#error_display").css("display", "inline").text("File da caricare non specificato");
			error = true;
		}
		
		if($('#updateCall').val() == 'y' && (project == "" || project == undefined || project == null) && !error) {
			$("#error_project").css("display", "inline");
			error = true;
		}
		
		if($('#updateCall').val() == 'n' && !error) {
			found = false;
			
			for(i = 0; i < zipFormats.length; i++) {
				format = zipFormats[i];
				regex = new RegExp("." + format + "$");
				
				if(regex.test($("#inputFile").val())) {
					found = true;
					break;
				}
			}
			
			if(!found) {
				$("#error_display").css("display", "inline").text("Formato non consentito");
				error = true;
			}
		}
		
		if(!error) {
			$("#error_display").css("display", "none");
			$("#error_project").css("display", "none");
			$("#uploadMode").css("display", "none");
			$("#buttonClientField").css("display", "none");
			$("#project").prop("disabled", true);
			
			$.ajax({
				url: restUrl + "runUpload",
				data: {
					"user": $('#user').val(),
					"update": $('#updateCall').val() == 'y',
					"createContainer": createContainer
				},
				success: function(jsonRes) {
					$("#progress_box").css("display", "inline");
					$("#status").removeClass("alert-error");
					$('#status').html('Uploading');
					opExecuting = true;
					
					var timeout = window.setInterval(function() {
						$.ajax({
							url: restUrl + "upload/status",
							data: {
								"id": jsonRes.id_processo
							},
							success: function(json) {
								if(json.stato == 3) {
									$('#status').html('Processing');
									$('.percent').html(json.messaggio);
							        $('.bar').width($('.percent').text());
								}
							},
							error: function(json) {
								$("#error_display").css("display", "inline").text(JSON.stringify(JSON.parse(obj.responseText)).messaggio);
								$("#retryBtn").css("display", "inline");
							}
						});
					}, 1000);
					
					$('#btnUpload').fileupload({
						"url": call,
						"dataType": "json",
						"sequentialUploads": true,
						"formData": {
							"process": jsonRes.id_processo,
							"project": project
						},
						"progressall": function (e, data) {
					        var progress = parseInt(data.loaded / data.total * 100, 10);
					        var bar = $('.bar');
					        var percent = $('.percent');
					        var percentVal = progress + '%';
					        bar.width(percentVal);
					        percent.html(percentVal);
					    },
						"done": function(e, data) {
							if(data.result.stato < 0) {
								$("#status").removeClass("alert-info").removeClass("alert-success").addClass("alert-error");
								$("#retryBtn").css("display", "inline");
								$('.percent').html("0%");
								$('.bar').width("0%");
							}
							
							else {
								$("#status").removeClass("alert-info").removeClass("alert-error").addClass("alert-success");
								$('.percent').html("100%");
								$('.bar').width("100%");
							}

							$('#status').html(data.result.messaggio);
							window.clearInterval(timeout);
							opExecuting = false;
						},
						"fail": function(e, data) {
							$("#status").css("display", "inline").text(data.result.messaggio).removeClass("alert-success").removeClass("alert-info").addClass("alert-error");
							$("#retryBtn").css("display", "inline");
							document.getElementById('progressbar').style.display = "none";
							window.clearInterval(timeout);
							opExecuting = false;
						}
					});
					
					$('#btnUpload').fileupload('send', { "fileInput": $("#inputFile") });
				},
				error: function(obj) {
					$("#error_display").css("display", "inline").text(JSON.stringify(JSON.parse(obj.responseText)).messaggio);
				},
				dataType: "json"
			});
		}
	});
	
	$("#btnUploadServer").click(function() {
		error = false;
		var project = $('#project').val();
		var createContainer = ($('#createContainer') && $('#createContainer').length>0)?$('#createContainer')[0].checked:"false";
		directoryServer = $("#directoryServer").val();
		$("#serverFiles").css("display", "none");
		
		if($('#updateCall').val() == 'y' && (project == "" || project == undefined || project == null) && !error) {
			$("#error_project").css("display", "inline");
			error = true;
		}
		
		if($("input[name='type_upload_search']:checked").val() == "compress")
			serverFile = $("input[name=\"" + directoryServer + "\"]:checked").val();
		
		else {
			idx = directoryServer.lastIndexOf("/");
			serverFile = idx < 0 ? directoryServer : directoryServer.substring(idx + 1);
		}
		
		if(serverFile == "" || serverFile == undefined || serverFile == null) {
			$("#error_display").css("display", "inline").text("File da caricare non specificato");
			error = true;
		}
		
		if(!error) {
			$("#error_display").css("display", "none");
			$("#error_project").css("display", "none");
			$("#uploadMode").css("display", "none");
			$("#buttonServerField").css("display", "none");
			$("#fileServerField").css("display", "none");
			$("#fileServerInfoField").css("display", "inline-block");
			$("#fileServer").text($("input[name='type_upload_search']:checked").val() == "compress" ? serverFile : directoryServer);
			$("#project").prop("disabled", true);
			
			$.ajax({
				url: restUrl + "runUpload",
				data: {
					"user": $('#user').val(),
					"update": $('#updateCall').val() == 'y',
					"createContainer": createContainer
				},
				success: function(jsonRes) {
					$("#progress_box").css("display", "inline");
					$("#status").removeClass("alert-error");
					$('#status').html('Uploading');
					opExecuting = true;
					
					var timeout = window.setInterval(function() {
						$.ajax({
							url: restUrl + "upload/status",
							data: {
								"id": jsonRes.id_processo
							},
							success: function(json) {
								if(json.stato == 3) {
									$('#status').html('Processing');
									$('.percent').html(json.messaggio);
							        $('.bar').width($('.percent').text());
								}
							},
							error: function(json) {
								$("#error_display").css("display", "inline").text(JSON.stringify(JSON.parse(obj.responseText)).messaggio);
								$("#retryBtn").css("display", "inline");
							}
						});
					}, 1000);

					baseDir = directoryServer;
					
					if($("input[name='type_upload_search']:checked").val() == "compress")
						serverFile = $("input[name=\"" + directoryServer + "\"]:checked").val();
					
					else {
						idx = directoryServer.lastIndexOf("/");
						serverFile = idx < 0 ? directoryServer : directoryServer.substring(idx + 1);
						baseDir = idx < 0 ? "" : directoryServer.substring(0, idx);
					}
					
					var theUrl = restUrl + "uploadServer";
					if($('#updateCall').val() == 'y')
					  	theUrl = restUrl + "uploadServer/update"
					if($('#updateCall').val() == 'r')
					  	theUrl = restUrl + "uploadServer/resources"
					if($('#updateCall').val() == 'mrc')
					  	theUrl = restUrl + "uploadServer/unimarc"
					$.ajax({
						url: theUrl + "?" +
								"process=" + jsonRes.id_processo + "&project=" + $('#project').val() + "&dir=" + baseDir + "&file=" + serverFile,
										
						success: function(result) {
							if(result.stato < 0) {
								$("#status").removeClass("alert-info").removeClass("alert-success").addClass("alert-error");
								$("#retryBtn").css("display", "inline");
								$('.percent').html("0%");
								$('.bar').width("0%");
							}
							
							else {
								$("#status").removeClass("alert-info").removeClass("alert-error").addClass("alert-success");
								$('.percent').html("100%");
								$('.bar').width("100%");
							}

							$('#status').html(result.messaggio);
							window.clearInterval(timeout);
							opExecuting = false;
						},
						error: function(obj) {
							$("#status").css("display", "inline").text(result.messaggio).removeClass("alert-success").removeClass("alert-info").addClass("alert-error");
							$("#retryBtn").css("display", "inline");
							document.getElementById('progressbar').style.display = "none";
							window.clearInterval(timeout);
							opExecuting = false;
						},
						dataType: "json"
					});
				},
				error: function(obj) {
					$("#error_display").css("display", "inline").text(JSON.stringify(JSON.parse(obj.responseText)).messaggio);
				},
				dataType: "json"
			});
		}
	});
});

