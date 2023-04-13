

$(document).ready(function() {
		$("#noBtnModify").click(function() {
        	$("#modifyContainer").addClass("hide").addClass("fade");
        });

		$("#inputFile").change(function() {
			$("#displayFileName").text($(this).val().split('\\').pop());
		});

    	$("#yesBtnModify").click(function() {

    		var theData = {
    			"id": $('#idEdit').val(),
    			"label": $('#labelEdit').val(),
    			"directory": directoryOpen,
    			"parent": $('#containerEdit').val(),
    			"frontespizio": $('#frontespizioEdit').prop('checked'),
    			"vfsType": vfsTypeOpen
    		};

    		var formData = new FormData( $('#upLoadModifyContainerForm')[0] );
    		formData.append("id",$('#idEdit').val());
    		formData.append("label",$('#labelEdit').val());
    		formData.append("usage",$('#usageEdit').val());
    		formData.append("directory",directoryOpen);
    		formData.append("parent",$('#containerEdit').val());
    		formData.append("frontespizio",$('#frontespizioEdit').prop('checked'));
    		formData.append("vfsType",vfsTypeOpen);

    		$.ajax({
        		url: restUrl + "vfs/vfsFileUpload",
        		type: "POST",
        		data: formData,
                processData: false,
                contentType: false,
        		//data: JSON.stringify(theData),
        		//dataType: "json",
        		//traditional: true,
        		//contentType: "application/json; charset=UTF-8",
        		success: function(jsonRes) {
    				if(jsonRes.stato == 0){
    					$("#info").css("display", "block").text("Salvato con successo.");
    					$("#modifyContainer").addClass("hide").addClass("fade");
    					if(vfsTypeOpen=="object"){
							if(new URLSearchParams(window.location.search).has("idContainer")){
								openContainer(new URLSearchParams(window.location.search).get("idContainer"));
							}
    					}
    					else if(directoryOpen!=""){
    						openDirectory(directoryOpen);
    					}
    					else if(vfsTypeOpen=="directory"){
    						loadDirectories();
    					}
    					else if(vfsTypeOpen=="container"){
    						loadContainers();
    					}
    				}
    				else{
    					$("#error").css("display", "block").text(jsonRes.message);
    				}
        		},
    			error: function(obj, status, error) {
    				$("#error").css("display", "block").text("HTTP " + obj.status + ": " + obj.responseText);
    			}
        	});
        });
});

var metsId;
var magId;
function createMetsOfContainer(id){
	metsId = id;
	$("#mets-create-confirm").removeClass("hide").removeClass("fade");
}
function createMagOfContainer(id){
	magId = id;
	$("#mag-create-confirm").removeClass("hide").removeClass("fade");
}
function createMetsOfContainerConfirmed(id){
		$("#info").css("display", "block").text("Creazione METS in esecuzione.");
		$.ajax({
				url: restUrl + "vfs/createMets",
				type: "GET",
				data:{
					"id": id
				},
				success: function(jsonRes) {
					if(jsonRes.stato!=0){
						$("#error").css("display", "block").text(jsonRes.messaggio);
						$("#info").css("display", "none");
					}
					else{
						$("#error").css("display", "none");
						$("#info").css("display", "block").text("Mets creato. "+(jsonRes.messaggio?jsonRes.messaggio:""));
						location.reload();//openContainer(id);
					}
				},
				error: function(obj) {
					$("#error").css("display", "block").text("HTTP " + obj.status + ": " + obj.responseText);
				}
		});

}

function createMagOfContainerConfirmed(id){
		$("#info").css("display", "block").text("Creazione MAG in esecuzione.");
		$.ajax({
				url: restUrl + "vfs/createMag",
				type: "GET",
				data:{
					"id": id
				},
				success: function(jsonRes) {
					if(jsonRes.stato!=0){
						$("#error").css("display", "block").text(jsonRes.messaggio);
						$("#info").css("display", "none");
					}
					else{
						$("#error").css("display", "none");
						$("#info").css("display", "block").text("Mag creato. "+(jsonRes.messaggio?jsonRes.messaggio:""));
						location.reload();//openContainer(id);
					}
				},
				error: function(obj) {
					$("#error").css("display", "block").text("HTTP " + obj.status + ": " + obj.responseText);
				}
		});

}

function createModContainer(idContainerMod, vfsType){
	vfsTypeOpen = vfsType;
	if(idContainerMod){
		$('#headingEdit').text("Modifica dell'item '"+idContainerMod+"' ("+vfsType+")")
		$.ajax({
				url: restUrl + "vfs/vfsFile",
				type: "GET",
				data:{
					"id": ""+idContainerMod,
					"vfsType": ""+vfsType
				},
				success: function(jsonRes) {
					$('#idEdit').val(jsonRes.id);
					$('#labelEdit').val(jsonRes.label);
					$('#md5Edit').val(jsonRes.md5);
					$('#frontespizioEdit').prop('checked', "true"==jsonRes.frontespizio);
					$('#fileSizeEdit').val(jsonRes.fileSize);
					$('#usageEdit').val(jsonRes.usage);
					$('#contentTypeEdit').val(jsonRes.contentType);
					if(vfsType=="object"){
						var data = [containerOpen,"test","tess"];
						$('#containerEdit').val(containerOpen);
						$('#containerEdit').autocomplete({
                          source: function( request, response ) {
                                  $.ajax({
                                    url: restUrl + "vfs/containers-names",
                                    dataType: "json",
                                    data: {
                                      q: request.term
                                    },
                                    success: function( data ) {
                                      response( data );
                                    }
                                  });
                                }
                        });
						$('#containerEdit').autocomplete("option", "appendTo", ".eventInsForm");
						$("#containerEdit").attr("autocomplete", "on");
						$('#containerLine').css("display", "block");
						$('#frontespizioLine').css("display", "block");
						$('#md5Line').css("display", "block");
						$('#typeLine').css("display", "block");
						$('#fileSizeLine').css("display", "block");
						$('#fileLine').css("display", "block");
					}
					else{
						$('#containerLine').css("display", "none");
						$('#frontespizioLine').css("display", "none");
						$('#md5Line').css("display", "none");
						$('#typeLine').css("display", "none");
						$('#fileSizeLine').css("display", "none");
						$('#fileLine').css("display", "none");
					}
				},
				error: function(obj) {
					$("#vfsTable").css("display", "none");
					$("#error").css("display", "block").text("HTTP " + obj.status + ": " + obj.responseText);
				}
		});
	}
	else{
		$('#headingEdit').text("Creazione di un nuovo elemento "+vfsType)
		$('#idEdit').val("0");
		$('#frontespizioEdit').prop('checked', false);
		$('#containerEdit').val(containerOpen);
		$('#labelEdit').val("");
		$('#md5Edit').val("");
		$('#fileSizeEdit').val("");
		$('#contentTypeEdit').val("");
		if(vfsType=="object"){
			$('#containerLine').css("display", "block");
			$('#frontespizioLine').css("display", "block");
			$('#md5Line').css("display", "block");
			$('#usageLine').css("display", "block");
			$('#typeLine').css("display", "block");
			$('#fileSizeLine').css("display", "block");
			$('#fileLine').css("display", "block");
		}
		else{
			$('#containerLine').css("display", "none");
			$('#frontespizioLine').css("display", "none");
			$('#md5Line').css("display", "none");
			$('#usageLine').css("display", "none");
			$('#typeLine').css("display", "none");
			$('#fileSizeLine').css("display", "none");
			$('#fileLine').css("display", "none");
		}
	}
	$('#displayFileName').text("");
	$('#inputFile').val('');
	$("#modifyContainer").removeClass("hide").removeClass("fade");
}

