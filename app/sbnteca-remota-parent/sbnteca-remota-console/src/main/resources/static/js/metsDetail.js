var size = 10;
var data;
var magID;

$(document).ready(function() {
	magID = window.location.href.slice(window.location.href.indexOf('id=') + 3);
	
	$("#metadataWindow .modal-header .close").click(function() {
		$("#metadataWindow").addClass("hide").addClass("fade");
	});
	
	$.ajax({
		url: restUrl + "metsdata?" + window.location.href.slice(window.location.href.indexOf('?') + 1),
		success: function(jsonRes) {
			$('#containerData').append("<div class=\"help-block row\"></div><div class=\"help-block row\"></div>");
			$("#containerData").append("<div id=\"importSection\" class=\"row-fluid detail-section form-horizontal\"><div class=\"detail-section-header\"><h4>IMPORT</h4></div></div>");
			imp = jsonRes.importazione;
			displayValue("importSection", "magProject", "Progetto", imp.progetto);
			displayValue("importSection", "magImpLastUpdate", "Ultima modifica", imp.ultima_modifica);
			displayValue("importSection", "magPublicFlag", "Pubblicazione OAI", imp.pubblicazione);
			displayValue("importSection", "magOaiIdentifier", "OAI Identifier", imp.oaiidentifier);
			
			
			$('#containerData').append("<div class=\"help-block row\"></div><div class=\"help-block row\"></div>");
			$("#containerData").append("<div id=\"genSection\" class=\"row-fluid detail-section form-horizontal\"><div class=\"detail-section-header\"><h4>GEN</h4></div></div>");
			gen = jsonRes.gen;
			displayValue("genSection", "magCreation", "Data Creazione", gen.data_creazione);
			displayValue("genSection", "magLastUpdate", "Ultima modifica", gen.ultima_modifica);
			displayValue("genSection", "magStprog", "Stprog", gen.stprog);
			displayValue("genSection", "magCollection", "Collezione", gen.collezione);
			displayValue("genSection", "magAgency", "Agenzia", gen.agenzia);
			displayValue("genSection", "magAccess", "Diritti di accesso", gen.accesso);
			displayValue("genSection", "magCompleteness", "Completo", gen.completezza);
			
			if(gen.gruppi_immagini.length > 0) {
				$("#genSection").append("<div id=\"imgGroupBox\" class=\"control-group\">" +
						"<div class=\"offset1 span8\"><a class=\"btn-link\" onclick=\"openGroups('img');\">" +
						"<img id=\"imgGroupOpenicon\" class=\"group_openicon\" src=\"images/icon_plus.gif\"></img></a> " +
						"<em>Gruppi Immagini</em></div></div>" +
						"<div id=\"imgGroupArea\" class=\"row-fluid offset1 span9\" style=\"display: none\"></div>");
				
				
				for(var i = 0; i < gen.gruppi_immagini.length; i++) {
					group = gen.gruppi_immagini[i];
					elemID = "img_group_" + (i + 1);
					
					$('#imgGroupArea').append("<div class=\"page-header\"><h4>Gruppo " + group.id + "</h4></div>");
					$('#imgGroupArea').append("<div id=\"" + elemID + "\" class=\"row-fluid form-horizontal\"></div>");
					
					displayInlineValues(elemID, "Campionamento", [
						{
							"name": "Unità",
							"value": group.unita_campionamento
						},
						{
							"name": "Piano",
							"value": group.piano_campionamento
						},
						{
							"name": "X",
							"value": group.x_campionamento
						},
						{
							"name": "Y",
							"value": group.y_campionamento
						},
						{
							"name": "Modello",
							"value": group.fotometrica
						},
						{
							"name": "Bit Per Campione",
							"value": group.bit_campione
						},
					]);
					
					displayInlineValues(elemID, "Formato", [
						{
							"name": "Nome",
							"value": group.nome_formato
						},
						{
							"name": "MIME",
							"value": group.mime_formato
						},
						{
							"name": "Compressione",
							"value": group.compressione_formato
						}
					]);

					displayInlineValues(elemID, "PPI", [
						{
							"value": group.ppi
						}
					]);

					displayInlineValues(elemID, "DPI", [
						{
							"value": group.dpi
						}
					]);
					
					displayInlineValues(elemID, "Scansione", [
						{
							"name": "Sorgente",
							"value": group.tipo_sorgente
						},
						{
							"name": "Agenzia",
							"value": group.agenzia_scansione
						},
						{
							"name": "Dispositivo",
							"value": group.dispositivo_sorgente
						}
					]);
					
					displayInlineValues(elemID, "Scanner", [
						{
							"name": "Produttore",
							"value": group.produttore_scanner
						},
						{
							"name": "Modello",
							"value": group.modello_scanner
						},
						{
							"name": "Software",
							"value": group.software_cattura
						}
					]);
					
					$('#imgGroupArea').append("<div class=\"help-block row\"></div><div class=\"help-block row\"></div>");
				}
			}
			
			if(gen.gruppi_audio.length > 0) {
				$("#genSection").append("<div id=\"audioGroupBox\" class=\"control-group\"><div class=\"offset1 span8\">" +
						"<a class=\"btn-link\" onclick=\"openGroups('audio');\"><img id=\"audioGroupOpenicon\" " +
						"class=\"group_openicon\" src=\"images/icon_plus.gif\"></img></a> <em>Gruppi Audio</span></em></div></div>" +
						"<div class=\"row-fluid offset1 span9\" id=\"audioGroupArea\" style=\"display: none\"></div>");
				
				
				for(var i = 0; i < gen.gruppi_audio.length; i++) {
					group = gen.gruppi_audio[i];
					elemID = "audio_group_" + (i + 1);
					
					$('#audioGroupArea').append("<div class=\"page-header\"><h4>Gruppo " + group.id + "</h4></div>");
					$('#audioGroupArea').append("<div id=\"" + elemID + "\" class=\"row-fluid form-horizontal\"></div>");
					
					displayInlineValues(elemID, "Campionamento", [
						{
							"name": "Frequenza",
							"value": group.frequenza
						},
						{
							"name": "Bit Per Campione",
							"value": group.bit_campione
						},
						{
							"name": "Velocità",
							"value": group.velocita
						}
					]);
					
					displayInlineValues(elemID, "Formato", [
						{
							"name": "Nome",
							"value": group.nome_formato
						},
						{
							"name": "MIME",
							"value": group.mime_formato
						},
						{
							"name": "Compressione",
							"value": group.compressione_formato
						},
						{
							"name": "Canale",
							"value": group.canale_formato
						}
					]);
					
					$('#audioGroupArea').append("<div class=\"help-block row\"></div><div class=\"help-block row\"></div>");
				}
			}
			
			if(gen.gruppi_video.length > 0) {
				$("#genSection").append("<div id=\"videoGroupBox\" class=\"control-group\"><div class=\"offset1 span8\">" +
						"<a class=\"btn-link\" onclick=\"openGroups('video');\">" +
						"<img id=\"videoGroupOpenicon\" class=\"group_openicon\" src=\"images/icon_plus.gif\"></img></a> " +
						"<em>Gruppi Video</em></div></div>" +
						"<div class=\"row-fluid offset1 span9\" id=\"videoGroupArea\" style=\"display: none\"></div>");
				
				
				for(var i = 0; i < gen.gruppi_video.length; i++) {
					group = gen.gruppi_video[i];
					elemID = "video_group_" + (i + 1);
					
					$('#videoGroupArea').append("<div class=\"page-header\"><h4>Gruppo " + group.id + "</h4></div>");
					$('#videoGroupArea').append("<div id=\"" + elemID + "\" class=\"row-fluid form-horizontal\"></div>");
					
					displayInlineValues(elemID, "Dimensioni", [
						{
							"name": "Dimensioni Video",
							"value": group.dimensioni_video
						},
						{
							"name": "Rapporto Video",
							"value": group.rapporto
						},
						{
							"name": "Velocità Frame",
							"value": group.velocita_frame
						}
					]);
					
					displayInlineValues(elemID, "Formato", [
						{
							"name": "Nome",
							"value": group.nome_formato
						},
						{
							"name": "MIME",
							"value": group.mime_formato
						},
						{
							"name": "Formato Video",
							"value": group.video_formato
						},
						{
							"name": "Codifica",
							"value": group.codifica_formato
						},
						{
							"name": "Stream",
							"value": group.tipo_stream_formato
						},
						{
							"name": "Codec",
							"value": group.codec_formato
						}
					]);
					
					$("#videoGroupArea").append("<div class=\"help-block row\"></div><div class=\"help-block row\"></div>");
				}
			}
			
			
			
			$('#containerData').append("<div class=\"help-block row\"></div><div class=\"help-block row\"></div>");
			$("#containerData").append("<div id=\"bibSection\" class=\"row-fluid detail-section form-horizontal\"><div class=\"page-header\"><h4>dmdSec</h4></div></div>");
			bib = jsonRes.dmdSec.mdWrap.xmlData.mods;
			displayValues("bibSection", "magIdentifier", "Identifier", bib.identifier);
			displayValue("bibSection", "genre", "Genre", bib.genre);
			displayValue("bibSection", "magLevel", "Livello Bibliografico", bib.livello_bibliografico);
			displayValues("bibSection", "magTitle", "Titolo", bib.titleInfo);
			displayValues("bibSection", "magCreator", "Autore", bib.autori);
			displayValues("bibSection", "magPublisher", "Editore", bib.editori);
			displayValues("bibSection", "magDate", "Data pubblicazione", bib.date_pubblicazione);
			displayValues("bibSection", "magSubject", "Soggetto", bib.soggetti);
			displayValues("bibSection", "magDescription", "Descrizione", bib.descrizioni);
			displayValues("bibSection", "magContributor", "Contributore", bib.contributori);
			displayValues("bibSection", "magFormat", "Formato", bib.formati);
			displayValues("bibSection", "magLanguage", "Lingua", bib.lingue);
			displayValues("bibSection", "magSource", "Sorgente", bib.sorgenti);
			displayValues("bibSection", "magRelation", "Relazioni", bib.relazioni);
			displayValues("bibSection", "magCoverage", "Copertura", bib.coperture);
			displayValues("bibSection", "magRights", "Diritti", bib.diritti);
			displayValues("bibSection", "magGeo", "Coordinate geografiche", bib.geo);
			displayValues("bibSection", "magNotDate", "Data notifica", bib.date_notifica);
			
			if(bib.unita != null) {
				displayInlineValues("bibSection", "Unità", [
					{
						"name": "Pubblicazione",
						"value": bib.unita.pubblicazione
					},
					{
						"name": "Anno",
						"value": bib.unita.anno
					},
					{
						"name": "Periodo",
						"value": bib.unita.periodo
					},
					{
						"name": "Nome",
						"value": bib.unita.nome_unita
					},
					{
						"name": "Numero",
						"value": bib.unita.numero_unita
					},
					{
						"name": "Volume",
						"value": bib.unita.volume
					}
				]);
			}

			if(bib.location.length > 0) {
				for(var i = 0; i < bib.location.length; i++) {
					obj = bib.location[i];
					// TODO
					break;
					segnature = "";
					
					if(obj.segnature.length > 0) {
						for(var j = 0; j < obj.segnature.length; j++) {
							if(j > 0)
								segnature += " - ";
							
							segnature += obj.segnature[j];
						}
					}
					
					displayInlineValues("bibSection", "Localizzazione" + (obj.id != null ? " " + obj.id : ""), [
						{
							"value": obj.biblioteca
						},
						{
							"name": "Inventario",
							"value": obj.numero_inventario
						},
						{
							"name": "Segnature",
							"value": segnature.length > 0 ? segnature : null
						}
					]);
				}
			}
			
			if(jsonRes.stru && jsonRes.stru.length > 0) {
				$('#containerData').append("<div class=\"help-block row\"></div><div class=\"help-block row\"></div>");
				$("#containerData").append("<div id=\"struSection\" class=\"row-fluid\">" +
						"<div class=\"detail-section-header\"><h4>STRU</h4></div><div id=\"stru_tree\" /><div class=\"help-block row\"></div><div class=\"help-block row\"></div></div>");
				
				struList = [];
				
				for(var i = 0; i < jsonRes.stru.length; i++) {
					struElem = {};
					mapJstreeData(jsonRes.stru[i], struElem);
					struList.push(struElem);
				}
				
				$("#stru_tree").jstree({ 
					"core" : {
						"data" : [{
				    			  "id": "stru_index",
				            	  "text" : "<span>Indice</span>",
				            	  "icon" : "images/stru_index_icon.png",
 				            	  "children" : struList,
				            	  "state" : { "opened" : true }
				        }]
					}
				});
			}
			
			data = jsonRes.risorse;
			
			for(var i = 0; jsonRes.risorse && i < jsonRes.risorse.length; i++) {
				type = jsonRes.risorse[i].tipo;
				
				usageList = jsonRes.risorse[i].lista_usage;
				
				$('#containerData').append("<div class=\"help-block row\"></div><div class=\"help-block row\"></div>");
				$("#containerData").append("<div id=\"" + type + "Section\" class=\"row-fluid\">" +
						"<div class=\"detail-section-header\"><h4>" + type.toUpperCase() + "</h4></div></div>");
				
				
				for(var j = 0; j < usageList.length; j++) {
					resourceList = usageList[j].risorse_digitali;
					start = 0;
					end = size < resourceList.length ? size : resourceList.length;
					
					$("#" + type + "Section").append("<div id=\"" + type + "_" + usageList[j].usage + "\" class=\"row-fluid\">" +
							"<div class=\"help-block row\"></div><div class=\"row-fluid\"><div class=\"span11\">" +
							"<div class=\"detail-section-header\"><h5>USAGE " + usageList[j].usage + " (" + (usageList[j].export ? "INTERNO/ESTERNO" : "INTERNO") + ")</h5></div>" +
								
							"<table id=\"" + type + "_" + usageList[j].usage + "\" class=\"table table-bordered\"><tbody>" +
								"<tr class=\"odd\"><td colspan=\"" + size + "\" class=\"hide\"><input type=\"hidden\" class=\"gallery_start\" value=\"0\" /></td></tr>" +
								"<tr class=\"even\" id=\"" + type + "_usage_gallery_" + usageList[j].usage + "_line0\"></tr>" +
								"<tr class=\"odd\" id=\"" + type + "_usage_gallery_" + usageList[j].usage + "_line1\"></tr>" +
								"<tr class=\"even\"><td colspan=\"" + size + "\"><div id=\"" + type + "_usage_gallery_paginate_" + usageList[j].usage + "\" " +
										"class=\"usage_gallery_paginate\"></div></td></tr></tbody></table>");
					
					
					for(var k = start; k < end; k++) {
						detailResource = resourceList[k];
						resourceTitle = detailResource.nomenclatura;
						resourceLine = k - start >= size / 2 ? 1 : 0;
						
						if(resourceTitle == null || resourceTitle.length == 0)
							resourceTitle = "[vuoto]";
						
						metadataFunc = "openPopupResourceMetadata('" + resourceTitle.replace(new RegExp("'", 'g'), "\\'").
								replace(new RegExp("\"", 'g'), "&quot;") + "', '" + type + "', '" + detailResource.numero_sequenza + "', " +
								"'" + usageList[j].usage + "');";
						
						previewUrl = restUrl + "digitalResource/preview/" + type + "/" +
								detailResource.numero_sequenza + "/" + usageList[j].usage + "?id=" + magID + "&width=96&height=96";
						
						ajaxRequest = "displayResource/" + type + "/" + detailResource.numero_sequenza + "/" + usageList[j].usage + "?id=" + magID;
						
						
						$("#" + type + "_usage_gallery_" + usageList[j].usage + "_line" + resourceLine).append(
								"<td class=\"gallery_preview_box\"><a class=\"gallery_item_preview btn-link open_gallery\" " +
										"href=\"" + ajaxRequest + "\"><img src=\"" + previewUrl + "\"></img></a>" + 
										
								"<div class=\"help-block\"></div>" +
								
								"<div class=\"gallery_item_title table-links\"><a title=\"" + resourceTitle.replace(new RegExp("\"", 'g'), "&quot;") + "\" " +
										"class=\"btn-link\" onclick=\""+  metadataFunc + "\">" + resourceTitle + "</a></div></div></td>");
					}
					
					for(var k = end; k < size; k++) {
						resourceLine = k - start >= size / 2 ? 1 : 0;
						$("#" + type + "_usage_gallery_" + usageList[j].usage + "_line" + resourceLine).append("<td class=\"gallery_preview_box\"></td>");
					}

					$("#" + type + "_usage_gallery_paginate_" + usageList[j].usage).html("<a title=\"Scorri la gallery\" " +
							"class=\"left_arrow offset3 span1" + (start == 0 ? "" : " btn-link") + "\" style=\"text-align: right\">" +
							"<img src=\"images/left_arrow_small.png\"></img></a> " +
							"<span class=\"paginate_info span4\">" + (start + 1) + "-" + end + " di " + resourceList.length + " totali</span>" +
							"<a title=\"Scorri la gallery\" class=\"right_arrow span1" + (resourceList.length <= 7 ? "" : " btn-link") + "\" style=\"text-align: left\">" +
							"<img src=\"images/right_arrow_small.png\"></img></a>");
				}
				
				$("#" + type + "Section .open_gallery").modaal({ "type" : "ajax", "background" : "darkgray" });
			}
			
			$("a.left_arrow").click(function(e) {
				var galleryAreaID = e.target.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.id;
				var type_usage = galleryAreaID.split("_");
				type = type_usage[0];
				usage = type_usage[1];
				
				for(var i = 0; i < data.length; i++) {
					if(data[i].tipo == type) {
						for(var j = 0; j < data[i].lista_usage.length; j++) {
							usageList = data[i].lista_usage[j];
							
							if(usageList.usage == usage) {
								resourceList = usageList.risorse_digitali;
								start = parseInt($("#" + galleryAreaID + " .gallery_start").val());
								
								if(start == 0)
									return;
								
								offset = start - size;
								$("#" + type + "_usage_gallery_" + usage + "_line0 .gallery_preview_box").empty();
								$("#" + type + "_usage_gallery_" + usage + "_line1 .gallery_preview_box").empty();
								
								for(var k = 0; k < size; k++) {
									resourceLine = k >= size / 2 ? 1 : 0;
									galleryID = type + "_usage_gallery_" + usage + "_line" + resourceLine;
									tdArray = $("#" + galleryID + " .gallery_preview_box");
									
									if(offset + k < resourceList.length) {
										$(tdArray[k < size / 2 ? k : k - (size / 2)]).append(
												"<a class=\"gallery_item_preview btn-link open_gallery\" " +
														"href=\"\"><img src=\"\"></img></a>" + 
														
												"<div class=\"help-block\"></div>" +
												
												"<div class=\"gallery_item_title table-links\"><a title=\"\" " +
														"class=\"btn-link\" onclick=\"\"></a></div></div>");
										
										previewImgArray = $("#" + galleryID + " .gallery_item_preview img");
										ajaxRequestArray = $("#" + galleryID + " a.open_gallery");
										titleArray = $("#" + galleryID + " .gallery_item_title a");
										
										resourceTitle = resourceList[offset + k].nomenclatura;
										
										if(resourceTitle == null || resourceTitle.length == 0)
											resourceTitle = "[vuoto]";
										
										metadataFunc = "openPopupResourceMetadata('" + resourceTitle.replace(new RegExp("'", 'g'), "\\'").
												replace(new RegExp("\"", 'g'), "&quot;") + "', '" + type + "', '" + resourceList[offset + k].numero_sequenza + "', " +
												"'" + usage + "');";
										
										previewUrl = restUrl + "digitalResource/preview/" + type + "/" +
												resourceList[offset + k].numero_sequenza + "/" + usage + "?id=" + magID + "&width=96&height=96";
								
										ajaxRequest = "displayResource/" + type + "/" + 
												resourceList[offset + k].numero_sequenza + "/" + usage + "?id=" + magID;
										
										$(previewImgArray[k < size / 2 ? k : k - (size / 2)]).attr("src", previewUrl);
										$(ajaxRequestArray[k < size / 2 ? k : k - (size / 2)]).prop("href", ajaxRequest);
										$(titleArray[k < size / 2 ? k : k - (size / 2)]).attr("onclick", metadataFunc);
										$(titleArray[k < size / 2 ? k : k - (size / 2)]).attr("title", resourceTitle.replace(new RegExp("\"", 'g'), "&quot;"));
										$(titleArray[k < size / 2 ? k : k - (size / 2)]).text(resourceTitle);
									}
								}

								$("#" + galleryAreaID + " .gallery_start").val(start >= size ? start - size : 0);
								$("#" + galleryAreaID + " a.right_arrow").addClass("btn-link");
								$("#" + galleryAreaID + " a.left_arrow").removeClass("btn-link");
								
								if(start > size)
									$("#" + galleryAreaID + " a.left_arrow").addClass("btn-link");
								
								$("#" + type + "_usage_gallery_paginate_" + usage + " .paginate_info").text((offset + 1) + "-" + (offset + size) + " " +
										"di " + resourceList.length + " totali");
								
							}
						}
					}
				}
				
				$("#" + type + "Section .open_gallery").modaal({ "type" : "ajax", "background" : "darkgray" });
			});
			
			$("a.right_arrow").click(function(e) {
				var galleryAreaID = e.target.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.id;
				var type_usage = galleryAreaID.split("_");
				type = type_usage[0];
				usage = type_usage[1];
				
				for(var i = 0; i < data.length; i++) {
					if(data[i].tipo == type) {
						for(var j = 0; j < data[i].lista_usage.length; j++) {
							usageList = data[i].lista_usage[j];
							
							if(usageList.usage == usage) {
								resourceList = usageList.risorse_digitali;
								start = parseInt($("#" + galleryAreaID + " .gallery_start").val());
								
								if(start + size >= resourceList.length)
									return;
								
								offset = start + size;
								$("#" + type + "_usage_gallery_" + usage + "_line0 .gallery_preview_box").empty();
								$("#" + type + "_usage_gallery_" + usage + "_line1 .gallery_preview_box").empty();
								
								for(var k = 0; k < size; k++) {
									resourceLine = k >= size / 2 ? 1 : 0;
									galleryID = type + "_usage_gallery_" + usage + "_line" + resourceLine;
									tdArray = $("#" + galleryID + " .gallery_preview_box");
									
									if(offset + k < resourceList.length) {
										$(tdArray[k < size / 2 ? k : k - (size / 2)]).append(
												"<a class=\"gallery_item_preview btn-link open_gallery\" " +
														"href=\"\"><img src=\"\"></img></a>" + 
														
												"<div class=\"help-block\"></div>" +
												
												"<div class=\"gallery_item_title table-links\"><a title=\"\" " +
														"class=\"btn-link\" onclick=\"\"></a></div></div>");
										
										previewImgArray = $("#" + galleryID + " .gallery_item_preview img");
										ajaxRequestArray = $("#" + galleryID + " a.open_gallery");
										titleArray = $("#" + galleryID + " .gallery_item_title a");
										
										resourceTitle = resourceList[offset + k].nomenclatura;
										
										if(resourceTitle == null || resourceTitle.length == 0)
											resourceTitle = "[vuoto]";
										
										metadataFunc = "openPopupResourceMetadata('" + resourceTitle.replace(new RegExp("'", 'g'), "\\'").
												replace(new RegExp("\"", 'g'), "&quot;") + "', '" + type + "', '" + resourceList[offset + k].numero_sequenza + "', " +
												"'" + usage + "');";
										
										previewUrl = restUrl + "digitalResource/preview/" + type + "/" +
												resourceList[offset + k].numero_sequenza + "/" + usage + "?id=" + magID + "&width=96&height=96";
								
										ajaxRequest = "displayResource/" + type + "/" + 
												resourceList[offset + k].numero_sequenza + "/" + usage + "?id=" + magID;
										
										$(previewImgArray[k < size / 2 ? k : k - (size / 2)]).attr("src", previewUrl);
										$(ajaxRequestArray[k < size / 2 ? k : k - (size / 2)]).prop("href", ajaxRequest);
										$(titleArray[k < size / 2 ? k : k - (size / 2)]).attr("onclick", metadataFunc);
										$(titleArray[k < size / 2 ? k : k - (size / 2)]).attr("title", resourceTitle.replace(new RegExp("\"", 'g'), "&quot;"));
										$(titleArray[k < size / 2 ? k : k - (size / 2)]).text(resourceTitle);
									}
									
								}

								$("#" + galleryAreaID + " .gallery_start").val(start + size);
								$("#" + galleryAreaID + " a.left_arrow").addClass("btn-link");
								$("#" + galleryAreaID + " a.right_arrow").removeClass("btn-link");
								
								if(start + size < resourceList.length)
									$("#" + galleryAreaID + " a.right_arrow").addClass("btn-link");
								
								$("#" + type + "_usage_gallery_paginate_" + usage + " .paginate_info").text((offset + 1) + "-" + 
										(offset + size <= resourceList.length ? offset + size : resourceList.length) + " " +
										"di " + resourceList.length + " totali");
							}
						}
					}
				}
				
				$("#" + type + "Section .open_gallery").modaal({ "type" : "ajax", "background" : "darkgray" });
			});
			
			dis = jsonRes.dis;
			
			if(dis && dis.dis_item != null && dis.dis_item != undefined && dis.dis_item.length > 0) {
				$('#containerData').append("<div class=\"help-block row\"></div><div class=\"help-block row\"></div>");
				$("#containerData").append("<div id=\"disSection\" class=\"row-fluid detail-section form-horizontal\"><div class=\"detail-section-header\"><h4>DIS</h4></div></div>");
				
				for(var i = 0; i < dis.dis_item.length; i++) {
					disItemData = [];
					
					if(dis.dis_item[i].file != null && dis.dis_item[i].file != "" && dis.dis_item[i].file != undefined)
						disItemData.push({ "name": "File", "value": dis.dis_item[i].file });
					
					if(dis.dis_item[i].preview != null && dis.dis_item[i].preview != "" && dis.dis_item[i].preview != undefined)
						disItemData.push({ "name": "Preview", "value": dis.dis_item[i].preview });
					
					if(dis.dis_item[i].available != null && dis.dis_item[i].available != "" && dis.dis_item[i].available != undefined)
						disItemData.push({ "name": "Disponibile", "value": dis.dis_item[i].available });
					
					
					
					if(disItemData.length > 0)
						displayInlineValues("disSection", "Item", disItemData);
					
					else
						displayValue("disSection", "magDisItem" + i, "Item", " - ");
				}
			}
		},
		error: function(obj) {
			$("#containerData").css("display", "none");
			$("#error").css("display", "block").text("HTTP " + obj.status + " - " + obj.responseText);
		},
		dataType: "json"
	});
});

function openGroups(type) {
	idArea = type + "GroupArea";
	idIcon = type + "GroupOpenicon";
	
	
	if(idArea != "imgGroupArea") {
		$("#imgGroupArea").css("display", "none");
		$("#imgGroupOpenicon").attr("src", "images/icon_plus.gif");
	}
	
	if(idArea != "audioGroupArea") {
		$("#audioGroupArea").css("display", "none");
		$("#audioGroupOpenicon").attr("src", "images/icon_plus.gif");
	}
	
	if(idArea != "videoGroupArea") {
		$("#videoGroupArea").css("display", "none");
		$("#videoGroupOpenicon").attr("src", "images/icon_plus.gif");
	}
	
	
	
	if($("#" + idArea).css("display") == "none") {
		$("#" + idArea).slideDown(500);
		$("#" + idIcon).attr("src", "images/icon_minus.gif");
	}
	
	else {
		$("#" + idArea).slideUp(500);
		$("#" + idIcon).attr("src", "images/icon_plus.gif");
	}
}

function openPopupResourceMetadata(title, type, sequenceNumber, usage) {
	containerID = "metadataSection";
	$("#" + containerID).html("");
	
	$.ajax({
		url: restUrl + "digitalResource/data/" + type + "/" + sequenceNumber + "/" + usage + "?id=" + magID,
		success: function(jsonRes) {
			displayValue(containerID, "resourceType", "Tipologia Risorsa", type.toUpperCase());
			displayValue(containerID, "resourceHoldingsID", "ID Localizzazione", jsonRes.id_localizzazione);
			displayValue(containerID, "resourceGroupID", "Gruppo " + type.toUpperCase(), jsonRes.id_gruppo);
			displayValue(containerID, "resourceSequenceNumber", "Numero di sequenza", sequenceNumber);
			displayValue(containerID, "resourceNomenclature", "Nomenclatura", jsonRes.nomenclatura);
			displayValue(containerID, "resourceUsage", "Usage", jsonRes.usage.join(", "));
			displayValue(containerID, "resourceFile", "File", jsonRes.file);
			displayValue(containerID, "resourceMd5", "MD5", jsonRes.md5);
			displayValue(containerID, "resourceSize", "Dimensioni File", jsonRes.dimensioni_file + " byte");
			displayValue(containerID, "resourceCreated", "Data Creazione", jsonRes.data_creazione);
			displayValue(containerID, "resourceDuration", "Durata", jsonRes.durata);
			
			displayInlineValues(containerID, "Dimensioni Immagine", [
				{
					"name": "Larghezza",
					"value": jsonRes.larghezza_immagine
				},
				{
					"name": "Altezza",
					"value": jsonRes.altezza_immagine
				},
				{
					"name": "X",
					"value": jsonRes.x_immagine
				},
				{
					"name": "Y",
					"value": jsonRes.y_immagine
				}
			]);
			
			displayInlineValues(containerID, "Campionamento", [
				{
					"name": "Unità",
					"value": jsonRes.unita_campionamento
				},
				{
					"name": "Piano",
					"value": jsonRes.piano_campionamento
				},
				{
					"name": "X",
					"value": jsonRes.x_campionamento
				},
				{
					"name": "Y",
					"value": jsonRes.y_campionamento
				},
				{
					"name": "Modello",
					"value": jsonRes.fotometrica
				},
				{
					"name": "Frequenza",
					"value": jsonRes.frequenza
				},
				{
					"name": "Bit Per Campione",
					"value": jsonRes.bit_campione
				},
				{
					"name": "Velocità",
					"value": jsonRes.velocita
				}
			]);
			
			displayInlineValues(containerID, "Formato", [
				{
					"name": "Nome",
					"value": jsonRes.nome_formato
				},
				{
					"name": "MIME",
					"value": jsonRes.mime_formato
				},
				{
					"name": "Compressione",
					"value": jsonRes.compressione_formato
				},
				{
					"name": "Canale",
					"value": jsonRes.canale_formato
				},
				{
					"name": "Formato Video",
					"value": jsonRes.video_formato
				},
				{
					"name": "Codifica",
					"value": jsonRes.codifica_formato
				},
				{
					"name": "Stream",
					"value": jsonRes.tipo_stream_formato
				},
				{
					"name": "Codec",
					"value": jsonRes.codec_formato
				}
			]);
			
			displayValue(containerID, "resourcePpi", "PPI", jsonRes.ppi);
			displayValue(containerID, "resourceDpi", "DPI", jsonRes.dpi);
			
			displayInlineValues(containerID, "Scansione", [
				{
					"name": "Sorgente",
					"value": jsonRes.tipo_sorgente
				},
				{
					"name": "Agenzia",
					"value": jsonRes.agenzia_scansione
				},
				{
					"name": "Dispositivo",
					"value": jsonRes.dispositivo_sorgente
				}
			]);
			
			displayInlineValues(containerID, "Scanner", [
				{
					"name": "Produttore",
					"value": jsonRes.produttore_scanner
				},
				{
					"name": "Modello",
					"value": jsonRes.modello_scanner
				},
				{
					"name": "Software",
					"value": jsonRes.software_cattura
				}
			]);
			
			displayInlineValues(containerID, "Video", [
				{
					"name": "Dimensioni Video",
					"value": jsonRes.dimensioni_video
				},
				{
					"name": "Rapporto Video",
					"value": jsonRes.rapporto
				},
				{
					"name": "Velocità Frame",
					"value": jsonRes.velocita_frame
				}
			]);
			


			if(jsonRes.target.length > 0) {
				for(var i = 0; i < jsonRes.target.length; i++) {
					obj = jsonRes.target[i];
					
					displayInlineValues(containerID, "Target" + (obj.id != null ? " " + obj.id : ""), [
						{
							"name": "Tipo",
							"value": obj.tipo
						},
						{
							"name": "Immagine",
							"value": obj.immagine
						},
						{
							"name": "Performance",
							"value": obj.performance
						},
						{
							"name": "Profili",
							"value": obj.profili
						}
					]);
				}
			}
			
			displayValue(containerID, "resourceSourceOcr", "Sorgente OCR", jsonRes.sorgente_ocr);
			displayValue(containerID, "resourceSoftwareOcr", "Software OCR", jsonRes.software_ocr);
			displayValue(containerID, "resourceNote", "Note", jsonRes.note);
		},
		error: function(obj) {
			$("#containerData").css("display", "none");
			$("#error").css("display", "block").text("HTTP " + obj.status + " - " + obj.responseText);
		},
		dataType: "json"
	});
	
	$("#metadataWindow").removeClass("hide").removeClass("fade");
	$("#metadataWindow .modal-header h4").text("Metadati Risorsa '" + title + "'");
}

function mapJstreeData(jsonInput, jsonOutput) {
	if(jsonInput.tipo == "stru") {
		var nomen = jsonInput.nomenclatura;
		var title = nomen != null && nomen.length > 0 ? nomen : "[vuoto]";
		
		if(nomen == null || nomen.length == 0)
			nomen = "[vuoto]";
		
		else if(nomen.length > 150) {
			nomen = nomen.substring(0, 150);
			nomen = nomen.substring(0, nomen.lastIndexOf(" ")) + " ...";
		}
		
		jsonOutput.text = "<span title=\"" + title + "\">" + nomen + "</span>";
		jsonOutput.icon = "images/stru_icon.png";
	}
	
	else {
		jsonOutput.text = "";
		jsonOutput.icon = "";
		var nomen = jsonInput.nomenclatura;
		var title = nomen != null && nomen.length > 0 ? nomen : "[vuoto]";
		
		if(nomen != null && nomen.length > 0) {
			jsonOutput.text = "<em>Nomenclatura: </em>" + title;
			jsonOutput.icon = "images/stru_nomenclature_icon.png";
		}

		if(jsonInput.file != undefined && jsonInput.file != null) {
			jsonOutput.icon = "images/stru_file_icon.png";
			
			if(jsonOutput.text.length > 0)
				jsonOutput.text += "; ";
			
			jsonOutput.text += "<em>File: </em>" + jsonInput.file;
		}
		
		if(jsonInput.identifier != undefined && jsonInput.identifier != null) {
			if(jsonOutput.text.length > 0)
				jsonOutput.text += "; ";
			
			jsonOutput.text += "<em>Identifier: </em>" + jsonInput.identifier;
			jsonOutput.icon = "images/stru_identifier_icon.png";
		}
		
		if(jsonInput.unita != undefined && jsonInput.unita != null) {
			if(jsonInput.unita.pubblicazione != null) {
				if(jsonOutput.text.length > 0)
					jsonOutput.text += "; ";
				
				jsonOutput.text += "<em>Pubblicazione: </em>" + jsonInput.unita.pubblicazione;
			}

			if(jsonInput.unita.anno != null) {
				if(jsonOutput.text.length > 0)
					jsonOutput.text += "; ";
				
				jsonOutput.text += "<em>Anno: </em>" + jsonInput.unita.anno;
			}
			
			if(jsonInput.unita.periodo != null) {
				if(jsonOutput.text.length > 0)
					jsonOutput.text += "; ";
				
				jsonOutput.text += "<em>Periodo: </em>" + jsonInput.unita.periodo;
			}
			
			if(jsonInput.unita.nome_unita != null) {
				if(jsonOutput.text.length > 0)
					jsonOutput.text += "; ";
				
				jsonOutput.text += "<em>Nome: </em>" + jsonInput.unita.nome_unita;
			}

			if(jsonInput.unita.numero_unita != null) {
				if(jsonOutput.text.length > 0)
					jsonOutput.text += "; ";
				
				jsonOutput.text += "<em>Numero: </em>" + jsonInput.unita.numero_unita;
			}
			
			if(jsonInput.unita.volume != null) {
				if(jsonOutput.text.length > 0)
					jsonOutput.text += "; ";
				
				jsonOutput.text += "<em>Volume: </em>" + jsonInput.unita.volume;
			}
			
			jsonOutput.icon = "images/stru_piece_icon.png";
		}
		
		if(jsonInput.start != undefined && jsonInput.start != null) {
			if(jsonOutput.text.length > 0)
				jsonOutput.text += "; ";
			
			jsonOutput.text += "<em>Tipo risorse: </em>" + 
					(jsonInput.tipo_risorse != undefined && jsonInput.tipo_risorse != null ? jsonInput.tipo_risorse : "img") + "; " + 
					"<em>Start: </em>" + jsonInput.start;
			
			if(jsonInput.stop != undefined && jsonInput.stop != null)
				jsonOutput.text += "; <em>Stop: </em>" + jsonInput.stop;
		}

		jsonOutput.text = "<span title=\"" + $(jsonOutput.text).text() + "\">" + jsonOutput.text + "</span>";
		
		if(jsonOutput.icon == "images/stru_nomenclature_icon.png" || jsonOutput.icon.length == 0)
			jsonOutput.icon = "images/stru_element_icon.png";
	}
	
	jsonOutput.state = { "opened" : true };
	
	if(jsonInput.figli != undefined && jsonInput.figli != null && jsonInput.figli.length > 0) {
		var outputChildren = [];
		
		for(var i = 0; i < jsonInput.figli.length; i++) {
			var outputChild = {};
			mapJstreeData(jsonInput.figli[i], outputChild);
			outputChildren.push(outputChild);
		}
		
		jsonOutput.children = outputChildren;
	}
}

function displayValue(sectionID, propertyID, name, value) {
	if(value != null) {
		$("#" + sectionID).append("<div class=\"control-group\"><label class=\"control-label\"><b>" + name + ": </b></label><div id=\"" + propertyID + "\" class=\"controls\"></div></div>");
		
		$("<div/>", {
			"class": "radio inline",
		    "text": value
		}).appendTo("#" + propertyID);
	}
}

function displayValues(sectionID, propertyID, name, values) {
	if(values && values.length > 0) {
		$("#" + sectionID).append("<div class=\"control-group\"><label class=\"control-label\"><b>" + name + ": </b></label><div id=\"" + propertyID + "\" class=\"controls\"></div></div>");
		
		for(var i = 0; i < values.length; i++) {
			$("<div/>", {
				"class": "radio inline",
			    "text": values[i]
			}).appendTo("#" + propertyID);
			
			$("<div/>", {
				"class": "help-block row",
			    "text": ""
			}).appendTo("#" + propertyID);
		}
	}
}

function displayInlineValues(sectionID, name, innerData) {
	finalText = "";
	
	for(var i = 0; i < innerData.length; i++) {
		if(innerData[i].value != null) {
			if(finalText.length > 0)
				finalText += "; ";
			
			finalText += (innerData[i].name != null ? "<em>" + innerData[i].name + ": </em>" : "") + "<span>" + innerData[i].value + "</span>";
		}
	}
	
	if(finalText.length > 0) {
		$("#" + sectionID).append("<div class=\"control-group\"><label class=\"control-label\"><b>" + name + ": </b></label>" +
				"<div class=\"controls\"><span class=\"radio inline\">" + finalText + "</span></div></div>");
	}
}

function getInlineValues(sectionID, innerData) {
	finalText = "";
	
	for(var i = 0; i < innerData.length; i++) {
		if(innerData[i].value != null) {
			if(finalText.length > 0)
				finalText += "; ";
			
			finalText += (innerData[i].name != null ? "<em>" + innerData[i].name + ": </em>" : "") + innerData[i].value;
		}
	}
	
	return finalText;
}