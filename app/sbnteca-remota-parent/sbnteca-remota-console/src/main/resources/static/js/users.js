userList = [];
selectedIndex = -1;

$(document).ready(function() {
	$(".modal-header .close").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
	});
	
	$("#noBtn").click(function() {
		$("#delete-confirm").addClass("hide").addClass("fade");
	});

	$("#yesBtn").click(function() {
		user_id = userList[selectedIndex].id;

    	$.ajax({
    		url: restUrl + "user/delete/" + user_id,
    		type: "GET",
    		
    		success: function(jsonRes) {
    			$("#delete-confirm").addClass("hide").addClass("fade");
    			
    			if(jsonRes.result == "OK")
    				window.location.href = "users";
    			
    			else
    				$("#error").css("display", "block").text(jsonRes.message);
    		},
    		error: function(obj, status, error) {
    			$("#error").css("display", "block").text("HTTP " + obj.status + ": " + obj.responseText);
    		},
    		contentType: "application/json; charset=UTF-8",
    		dataType: "json"
    	});
	});
	
	path = "user/insert";
	
	if(window.location.pathname.indexOf("New") < 0)
		path = "user/update";
	
	$("#saveBtn").click(function() {
		postData = {
				"id": parseInt($("#user_id").val()),
				"nome": $("#user_name").val(),
				"cognome": $("#user_surname").val(),
				"username": $("#user_username").val(),
				"password": $("#user_password").val(),
				"email": $("#user_email").val(),
				"abilitato": $("#user_enabled").prop("checked") ? 1 : 0,
				"ruolo": parseInt($("#user_role").val())
			};
		
		$.ajax({
			url: restUrl + path,
			type: "POST",
			data: JSON.stringify(postData),
			success: function(jsonRes) {
				if(jsonRes.result == "OK")
					window.location.href = "users";
				
				else
					$("#error").css("display", "block").text(jsonRes.message);
			},
			error: function(obj) {
				$("#error").css("display", "block").text("HTTP " + obj.status + ": " + obj.responseText);
			},
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		});
		
	});
	
	if(window.location.pathname.indexOf("users") >= 0) {
		$.ajax({
			url: restUrl + "user/all",
			type: "GET",
			
			success: function(jsonRes) {
				data = [];
				userList = jsonRes;
				
				for(i = 0; i < userList.length; i++) {
					user = userList[i];
					
					data[i] = [ 
			            user.username, 
			            user.nome,
			            user.cognome,
			            user.abilitato ? "Si" : "No", 
			            		
			            "<form id=\"updateForm_" + i + "\" style=\"display: inline\" method=\"POST\">" +
			            		"<input id=\"user_" +  i + "\" type=\"hidden\" name=\"user\" />" +
			            		"<a onclick=\"update(" + i + ");\" title=\"Aggiorna l'utente '" + user.username + "'\">" +
			            		"<i class=\"icon-pencil btn-icon\"></i></a></form><div class=\"icon_title_table\"> Modifica </div>" + 
			            		
			            "<a onclick=\"cancel(" + i + ");\" title=\"Rimuovi l'utente '" + user.username + "'\">" +
			            		"<i class=\"icon-trash btn-icon\"></i></a><div class=\"icon_title_table\"> Cancella </div>"
					];
				}
				
				$("#users").DataTable({
					"autoWidth": false,
					"order": [],
					"paging": true,
					"lengthChange": false,
					"pageLength": 10,
					"pagingType": "full_numbers",
					"data": data,
					"language": {
					    "decimal":        "",
					    "emptyTable":     "Utenti non presenti",
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
			            {"title": "Username", "className": "sortable", "type": "string"},
			            {"title": "Nome", "className": "sortable", "type": "string"},
			            {"title": "Cognome", "className": "sortable", "type": "string"},
			            {"title": "Abilitato", "className": "sortable", "type": "string", "searchable": false, "orderable": false},
			            {"title": "", "className": "sortable actions", "type": "html", "searchable": false, "orderable": false}
					],
					"buttons": [ 
					    {"extend" : "csv", "title": "Utenti", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato CSV"},
					    {"extend" : "pdf", "title": "Utenti", "tag": "a", "className": "btn-icon", "text": "", "titleAttr": "Scarica in formato PDF"}
					],
					"dom": 'f<"deleteSearch">rtipB'
				});
				
				$("div.deleteSearch").html("<a onclick=\"$('.dataTables_filter input').val('').trigger('keyup');\"><i class=\"icon-remove btn-icon\"></i></a>");
			},
			error: function(obj) {
				$("#users").css("display", "none");
				$("#error").css("display", "block").text("HTTP " + obj.status + ": " + obj.responseText);
			},
			contentType: "application/json; charset=UTF-8",
			dataType: "json"
		});
	}
});

function cancel(index) {
	$(".modal-header h4").text("Cancella utente '" + userList[index].username + "'");
	selectedIndex = index;
	$("#delete-confirm").removeClass("hide").removeClass("fade");
}

function update(index) {
	$("#user_" + index).val(JSON.stringify(userList[index]));
	$("#updateForm_" + index).submit();
}