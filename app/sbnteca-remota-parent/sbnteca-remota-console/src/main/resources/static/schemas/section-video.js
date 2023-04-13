section_video = { "id": "section_video", "name": "Sezione VIDEO",
    "attributes": [
        {
            "id": "holdingsID",
            "repeatable": false,
            "mandatory": "",
            "readonly": "",
            "type": "text",
            "label": "ID holding",
            "cssClass": "",
            "value": "",
            "pattern": ""
        }
    ],
    "contents": [
        {
            "id": "SectionVideo",
            "type": "section"
        },
        {
            "id": "sequence_number",
            "repeatable": false,
            "mandatory": "required",
            "type": "text",
            "label": "Numero di sequenza",
            "cssClass": "",
            "value": "",
            "pattern": ""
        },
        {
            "id": "nomenclature",
            "repeatable": false,
            "mandatory": "required",
            "type": "text",
            "label": "Nomenclatura tracce/autori e titoli",
            "cssClass": "",
            "value": "",
            "pattern": ""
        },
        // Non capisco la combinazione dei valori più a/b
        {
            "id": "usage",
            "repeatable": true,
            "mandatory": "",
            "collapsable": true,
            "type": "object",
            "label": "Ambiti d'uso",
            "etichette":"usage",
            "repeatmax": 4,
            "cssClass": "",
            "attributes": [],
            "children": [
                {
                    "id": "usage",
                    "repeatable": false,
                    "mandatory": "required",
                    "type": "select",
                    "label": "Ambito d'uso",
                    "cssClass": "",
                    "value": "",
                    "data": [
                        {
                            "value": "",
                            "description": "-"
                        },
                        {
                            "value": "1",
                            "description": "(master)"
                        },
                        {
                            "value": "2",
                            "description": "(alta risoluzione)"
                        },
                        {
                            "value": "3",
                            "description": "(bassa risoluzione)"
                        },
                        {
                            "value": "4",
                            "description": "(preview)"
                        }
                    ]
                },
                {
                    "id": "groupID",
                    "repeatable": false,
                    "mandatory": "",
                    "readonly": "",
                    "type": "select",
                    "label": "Gruppo video associato",
                    "cssClass": "",
                    "value": "",
                    "data": []
                },
                {
                    "id": "file",
                    "repeatable": false,
                    "mandatory": "required",
                    "type": "mediapickercustom",
                    "mediatype": "video",
                    "label": "Collegamento al file",
                    "cssClass": "",
                    "value": "",
                    "pattern": ""
                },
                {
                    "id": "md5",
                    "repeatable": false,
                    "mandatory": "required",
                    "readonly":"readonly",
                    "type": "text",
                    "label": "Impronta del file",
                    "cssClass": "",
                    "value": "",
                    "pattern": ""
                },
                {
                    "id": "filesize",
                    "repeatable": false,
                    "mandatory": "",
                    "readonly":"readonly",
                    "type": "text",
                    "label": "Dimensione del file",
                    "cssClass": "",
                    "value": "",
                    "pattern": ""
                },
                {
                    "id": "video_dimensions",
                    "repeatable": false,
                    "mandatory": "required",
                    "collapsable": false,
                    "type": "object",
                    "label": "Durata video",
                    "cssClass": "",
                    "attributes": [],
                    "children": [
                        {
                            "id": "duration",
                            "repeatable": false,
                            "mandatory": "required",
                            "readonly":"readonly",
                            "type": "text",
                            "label": "Durata",
                            "cssClass": "",
                            "value": "",
                            "pattern": ""
                        }
                    ]
                },{
                    "id": "video_metrics",
                    "repeatable": false,
                    "mandatory": "required",
                    "collapsable": false,
                    "type": "object",
                    "label": "Caratteristiche video",
                    "cssClass": "",
                    "attributes": [],
                    "children": [
                        {
                            "id": "videosize",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "select",
                            "label": "Dimensione del frame",
                            "cssClass": "",
                            "data": [
                                {
                                    "value": "",
                                    "description": "-"
                                },
                                {
                                    "value": "160x120",
                                    "description": ""
                                },
                                {
                                    "value": "176x144",
                                    "description": ""
                                },
                                {
                                    "value": "192x144",
                                    "description": ""
                                },
                                {
                                    "value": "280x180",
                                    "description": ""
                                },
                                {
                                    "value": "320x240",
                                    "description": ""
                                },
                                {
                                    "value": "352x288",
                                    "description": ""
                                },
                                {
                                    "value": "360x288",
                                    "description": ""
                                },
                                {
                                    "value": "384x288",
                                    "description": ""
                                },
                                {
                                    "value": "480x576",
                                    "description": ""
                                },
                                {
                                    "value": "720x576",
                                    "description": ""
                                }
                            ],
                            "value": ""
                        },
                        {
                            "id": "aspectratio",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "select",
                            "label": "Formato dell'immagine",
                            "cssClass": "",
                            "data": [
                                {
                                    "value": "",
                                    "description": "-"
                                },
                                {
                                    "value": "1:1",
                                    "description": "(largezza:altezza)"
                                },
                                {
                                    "value": "4:3",
                                    "description": "(largezza:altezza)"
                                },
                                {
                                    "value": "16:9",
                                    "description": "(largezza:altezza)"
                                },
                                {
                                    "value": "2.11:1",
                                    "description": "(largezza:altezza)"
                                }
                            ],
                            "value": ""
                        },
                        {
                            "id": "framerate",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "select",
                            "label": "Frame per secondo",
                            "cssClass": "",
                            "data": [
                                {
                                    "value": "",
                                    "description": "-"
                                },
                                {
                                    "value": "23.976",
                                    "description": ""
                                },
                                {
                                    "value": "24",
                                    "description": ""
                                },
                                {
                                    "value": "25",
                                    "description": ""
                                },
                                {
                                    "value": "29.97",
                                    "description": ""
                                },
                                {
                                    "value": "30",
                                    "description": ""
                                },
                                {
                                    "value": "50",
                                    "description": ""
                                },
                                {
                                    "value": "59.94",
                                    "description": ""
                                },
                                {
                                    "value": "60",
                                    "description": ""
                                }
                            ],
                            "value": ""
                        }
                    ]
                },
                {
                    "id": "format",
                    "repeatable": false,
                    "mandatory": "required",
                    "collapsable": false,
                    "type": "object",
                    "label": "Formato video",
                    "cssClass": "",
                    "attributes": [],
                    "children": [
                        {
                            "id": "format_name",
                            "repeatable": false,
                            "mandatory": "",
                            "type": "text",
                            "label": "Formato",
                            "cssClass": "",
                            "value": "",
                            "pattern": ""
                        },
                        {
                            "id": "format_mime",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "select",
                            "label": "Tipo mime",
                            "cssClass": "",
                            "data": [
                                {
                                    "value": "",
                                    "description": "-"
                                },
                                {
                                    "value": "video/x-ms-asf",
                                    "description": ""
                                },
                                {
                                    "value": "video/avi",
                                    "description": ""
                                },
                                {
                                    "value": "video/mpeg",
                                    "description": ""
                                },
                                {
                                    "value": "video/vnd.rn-realvideo",
                                    "description": ""
                                },
                                {
                                    "value": "video/wmv",
                                    "description": ""
                                }
                            ],
                            "value": ""
                        },
                        {
                            "id": "videoformat",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "select",
                            "label": "Standard video",
                            "cssClass": "",
                            "data": [
                                {
                                    "value": "",
                                    "description": "-"
                                },
                                {
                                    "value": "Component",
                                    "description": ""
                                },
                                {
                                    "value": "NTSC",
                                    "description": ""
                                },
                                {
                                    "value": "PAL",
                                    "description": ""
                                },
                                {
                                    "value": "SECAM",
                                    "description": ""
                                },
                                {
                                    "value": "Unspecified",
                                    "description": ""
                                }
                            ],
                            "value": ""
                        },
                        {
                            "id": "encode",
                            "type": "select",
                            "repeatable": false,
                            "mandatory": "",
                            "label": "Tipo di encode",
                            "cssClass": "",
                            "value": "",
                            "pattern": "",
                            "data":[
                                {
                                    "value": "",
                                    "description": "-"
                                },
                                {
                                    "value": "interlaced",
                                    "description": ""
                                },
                                {
                                    "value": "non-interlaced",
                                    "description": ""
                                }
                            ]
                        },
                        {
                            "id": "streamtype",
                            "repeatable": false,
                            "mandatory": "",
                            "type": "select",
                            "label": "Tipo compressione",
                            "cssClass": "",
                            "data": [
                                {
                                    "value": "",
                                    "description": "-"
                                },
                                {
                                    "value": "Uncompressed",
                                    "description": ""
                                },
                                {
                                    "value": "MPEG-1",
                                    "description": ""
                                },
                                {
                                    "value": "MPEG-2",
                                    "description": ""
                                },
                                {
                                    "value": "MPEG-4",
                                    "description": ""
                                },
                                {
                                    "value": "Unspecified",
                                    "description": ""
                                }
                            ],
                            "value": ""
                        },
                        {
                            "id": "codec",
                            "type": "text",
                            "repeatable": false,
                            "mandatory": "",
                            "label": "Codec",
                            "cssClass": "",
                            "value": "",
                            "pattern": ""
                        }
                    ]
                },
                {
                    "id": "digitization",
                    "repeatable": false,
                    "mandatory": "",
                    "collapsable": false,
                    "type": "object",
                    "label": "Trascrizione digitale",
                    "cssClass": "",
                    "attributes": [],
                    "children": [
                        {
                            "id": "sourcetype",
                            "repeatable": false,
                            "mandatory": "",
                            "type": "text",
                            "label": "Fonte per l'acquisizione",
                            "cssClass": "",
                            "value": "",
                            "pattern": ""
                        },
                        {
                            "id": "transcriptionagency",
                            "repeatable": false,
                            "mandatory": "",
                            "type": "text",
                            "label": "Istituzione",
                            "cssClass": "",
                            "value": "",
                            "pattern": ""
                        },
                        {
                            "id": "devicesource",
                            "repeatable": false,
                            "mandatory": "",
                            "type": "text",
                            "label": "Apparecchiatura usata",
                            "cssClass": "",
                            "value": "",
                            "pattern": ""
                        },
                        /*{
                            "id": "transcriptionchain",
                            "repeatable": true,
                            "mandatory": "",
                            "collapsable": false,
                            "type": "object",
                            "label": "Strumenti",
                            "cssClass": "",
                            "attributes": [],
                            "children": [
                                {
                                    "id": "device_description",
                                    "repeatable": false,
                                    "mandatory": "required",
                                    "collapsable": false,
                                    "type": "object",
                                    "label": "Dispositivo usato",
                                    "cssClass": "",
                                    "attributes": [
                                        {
                                            "id": "Type",
                                            "repeatable": false,
                                            "mandatory": "required",
                                            "type": "text",
                                            "label": "Tipo",
                                            "cssClass": "",
                                            "value": "",
                                            "pattern": ""
                                        },
                                        {
                                            "id": "Unique_identifier",
                                            "repeatable": false,
                                            "mandatory": "",
                                            "type": "text",
                                            "label": "Identificativo dispositivo",
                                            "cssClass": "",
                                            "value": "",
                                            "pattern": ""
                                        },
                                        {
                                            "id": "Comments",
                                            "repeatable": false,
                                            "mandatory": "",
                                            "type": "text",
                                            "label": "Commenti",
                                            "cssClass": "",
                                            "value": "",
                                            "pattern": ""
                                        }
                                    ],
                                    "children": []
                                },
                                {
                                    "id": "device_manufacturer",
                                    "repeatable": false,
                                    "mandatory": "required",
                                    "type": "text",
                                    "label": "Produttore del dispositivo",
                                    "cssClass": "",
                                    "value": "",
                                    "pattern": ""
                                },
                                {
                                    "id": "device_model",
                                    "repeatable": false,
                                    "mandatory": "required",
                                    "collapsable": false,
                                    "type": "object",
                                    "label": "Modello dispositivo",
                                    "cssClass": "",
                                    "attributes": [
                                        {
                                            "id": "Model",
                                            "repeatable": false,
                                            "mandatory": "required",
                                            "type": "text",
                                            "label": "Nome modello",
                                            "cssClass": "",
                                            "value": "",
                                            "pattern": ""
                                        },
                                        {
                                            "id": "Serial_Number",
                                            "repeatable": false,
                                            "mandatory": "",
                                            "type": "text",
                                            "label": "Numero seriale",
                                            "cssClass": "",
                                            "value": "",
                                            "pattern": ""
                                        }
                                    ],
                                    "children": []
                                },
                                {
                                    "id": "capture_software",
                                    "repeatable": false,
                                    "mandatory": "",
                                    "type": "text",
                                    "label": "Software di acquisizione",
                                    "cssClass": "",
                                    "value": "",
                                    "pattern": ""
                                },
                                {
                                    "id": "device_settings",
                                    "repeatable": false,
                                    "mandatory": "",
                                    "type": "text",
                                    "label": "Impostazioni",
                                    "cssClass": "",
                                    "value": "",
                                    "pattern": ""
                                }
                            ]
                        },
                         {
                         "id": "transcriptionsummary",
                         "repeatable": true,
                         "mandatory": "",
                         "collapsable": false,
                         "type": "object",
                         "label": "Dati di sintesi",
                         "cssClass": "",
                         "attributes": [],
                         "children": []
                         },
                         {
                         "id": "transcriptiondata",
                         "repeatable": true,
                         "mandatory": "",
                         "collapsable": false,
                         "type": "object",
                         "label": "Dati",
                         "cssClass": "",
                         "attributes": [],
                         "children": []
                         }*/
                    ]
                },
                {
                    "id": "usage_ab",
                    "repeatable": false,
                    "mandatory": "",
                    "type": "select",
                    "label": "Copyright",
                    "cssClass": "",
                    "value": "",
                    "data": [
                        {
                            "value": "",
                            "description": "-"
                        },
                        {
                            "value": "a",
                            "description": "(il repository non ha il copyright dell'oggetto digitale)"
                        },
                        {
                            "value": "b",
                            "description": "(il repository ha il copyright dell'oggetto digitale)"
                        }
                    ]
                },
                {
                    "id": "datetimecreated",
                    "repeatable": false,
                    "mandatory": "",
                    "pattern": "",
                    "readonly":"readonly",
                    "type": "text",
                    "label": "Data di creazione",
                    "cssClass": "",
                    "value": ""
                }
            ]
        },
        {
            "id": "note",
            "repeatable": false,
            "mandatory": "",
            "type": "text",
            "label": "Note",
            "cssClass": "",
            "value": "",
            "pattern": ""
        }
    ],
    "buttons": [
        {
            "type": "back",
            "label": "Indietro"
        },
        {
            "type": "save",
            "label": "Conferma"
        }
    ]
};