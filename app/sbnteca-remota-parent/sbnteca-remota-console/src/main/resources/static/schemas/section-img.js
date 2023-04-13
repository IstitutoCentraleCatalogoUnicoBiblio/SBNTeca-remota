section_img = { "id": "section_img", "name": "Sezione IMG",
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
            "id": "SectionImg",
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
            "label": "Nomenclatura carte/pagine/fogli/titoli",
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
                    "label": "Gruppo immagine associato",
                    "cssClass": "",
                    "value": "",
                    "data": []
                },
                {
                    "id": "file",
                    "repeatable": false,
                    "mandatory": "required",
                    "type": "mediapickercustom",
                    "mediatype": "img",
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
                    "id": "image_dimensions",
                    "repeatable": false,
                    "mandatory": "required",
                    "collapsable": false,
                    "type": "object",
                    "label": "Dimensioni immagine",
                    "cssClass": "",
                    "children": [
                        {
                            "id": "imagelength",
                            "repeatable": false,
                            "mandatory": "required",
                            "readonly":"readonly",
                            "type": "text",
                            "label": "Lunghezza dell'immagine (in pixel)",
                            "cssClass": "",
                            "value": "",
                            "pattern": ""
                        },
                        {
                            "id": "imagewidth",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "text",
                            "label": "Larghezza dell'immagine (in pixel)",
                            "readonly":"readonly",
                            "cssClass": "",
                            "value": "",
                            "pattern": ""
                        },
                        {
                            "id": "source_xdimension",
                            "repeatable": false,
                            "mandatory": "",
                            "type": "text",
                            "label": "Lunghezza dell'oggetto analogico (in pollici)",
                            "cssClass": "",
                            "value": "",
                            "pattern": "[0-9]+(\.[0-9]{0,2})?"
                        },
                        {
                            "id": "source_ydimension",
                            "repeatable": false,
                            "mandatory": "",
                            "type": "text",
                            "label": "Larghezza dell'oggetto analogico (in pollici)",
                            "cssClass": "",
                            "value": "",
                            "pattern": "[0-9]+(\.[0-9]{0,2})?"
                        }
                    ],
                    "attributes": []
                },
                {
                    "id": "image_metrics",
                    "repeatable": false,
                    "mandatory": "required",
                    "collapsable": false,
                    "type": "object",
                    "label": "Caratteristiche immagine",
                    "cssClass": "",
                    "children": [
                        {
                            "id": "samplingfrequencyunit",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "select",
                            "label": "Unità di misura",
                            "cssClass": "",
                            "value": "",
                            "data": [
                                {
                                    "value": "",
                                    "description": "-"
                                },
                                {
                                    "value": "1",
                                    "description": "(nessuna unità di misura definita)"
                                },
                                {
                                    "value": "2",
                                    "description": "(inch, pollice)"
                                },
                                {
                                    "value": "3",
                                    "description": "(centimetro)"
                                }
                            ]
                        },
                        {
                            "id": "samplingfrequencyplane",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "select",
                            "label": "Piano focale del campionamento",
                            "cssClass": "",
                            "value": "",
                            "data": [
                                {
                                    "value": "",
                                    "description": "-"
                                },
                                {
                                    "value": "1",
                                    "description": "(camera/scanner focal plane)"
                                },
                                {
                                    "value": "2",
                                    "description": "(object plane)"
                                },
                                {
                                    "value": "3",
                                    "description": "(source object plane)"
                                }
                            ]
                        },
                        {
                            "id": "xsamplingfrequency",
                            "repeatable": false,
                            "mandatory": "",
                            "type": "text",
                            "label": "Frequenza di campionamento nella direzione orizzontale",
                            "cssClass": "",
                            "value": "",
                            "pattern": "^[0-9]+$"
                        },
                        {
                            "id": "ysamplingfrequency",
                            "repeatable": false,
                            "mandatory": "",
                            "type": "text",
                            "label": "Frequenza di campionamento nella direzione verticale",
                            "cssClass": "",
                            "value": "",
                            "pattern": "^[0-9]+$"
                        },
                        {
                            "id": "bitpersample",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "select",
                            "label": "Numero di bit per ciascun campione",
                            "cssClass": "",
                            "value": "",
                            "data": [
                                {
                                    "value": "",
                                    "description": "-"
                                },
                                {
                                    "value": "1",
                                    "description": "(bitonale, bianco e nero)"
                                },
                                {
                                    "value": "4",
                                    "description": "(4-bit scala di grigi)"
                                },
                                {
                                    "value": "8",
                                    "description": "(8-bit scala di grigi o gamma di 256 colori)"
                                },
                                {
                                    "value": "8,8,8",
                                    "description": "(24-bit RGB)"
                                },
                                {
                                    "value": "16,16,16",
                                    "description": "(48-bit TIFF, HDR)"
                                },
                                {
                                    "value": "8,8,8,8",
                                    "description": "(32-bit CMYK)"
                                }
                            ]
                        },
                        {
                            "id": "photometricinterpretation",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "select",
                            "label": "Interpretazione fotometrica dei bit del campione",
                            "cssClass": "",
                            "value": "",
                            "data": [
                                {
                                    "value": "",
                                    "description": "-"
                                },
                                {
                                    "value": "WhiteIsZero",
                                    "description": ""
                                },
                                {
                                    "value": "BlackIsZero",
                                    "description": ""
                                },
                                {
                                    "value": "RGB",
                                    "description": ""
                                },
                                {
                                    "value": "Palette color",
                                    "description": ""
                                },
                                {
                                    "value": "Transparency Mask",
                                    "description": ""
                                },
                                {
                                    "value": "CMYK",
                                    "description": ""
                                },
                                {
                                    "value": "YcbCr",
                                    "description": ""
                                },
                                {
                                    "value": "CIELab",
                                    "description": ""
                                }
                            ]
                        }
                    ],
                    "attributes": []
                },
                {
                    "id": "ppi",
                    "repeatable": false,
                    "mandatory": "",
                    "type": "text",
                    "label": "Risoluzione in pixel per pollice",
                    "cssClass": "",
                    "value": "",
                    "pattern": "^[0-9]+$"
                },
                {
                    "id": "dpi",
                    "repeatable": false,
                    "mandatory": "",
                    "type": "text",
                    "label": "Risoluzione in punti per pollice",
                    "cssClass": "",
                    "value": "",
                    "pattern": "^[0-9]+$"
                },
                {
                    "id": "format",
                    "repeatable": false,
                    "mandatory": "required",
                    "collapsable": false,
                    "type": "object",
                    "label": "Formato immagine",
                    "cssClass": "",
                    "attributes": [],
                    "children": [
                        {
                            "id": "format_name",
                            "repeatable": false,
                            "mandatory": "required",
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
                            "value": "",
                            "data": [
                                {
                                    "value": "",
                                    "description": "-"
                                },
                                {
                                    "value": "image/jpeg",
                                    "description": ""
                                },
                                {
                                    "value": "image/tiff",
                                    "description": ""
                                },
                                {
                                    "value": "image/gif",
                                    "description": ""
                                },
                                {
                                    "value": "image/png",
                                    "description": ""
                                },
                                {
                                    "value": "image/vnd.djvu",
                                    "description": ""
                                },
                                {
                                    "value": "application/pdf",
                                    "description": ""
                                }
                            ]
                        },
                        {
                            "id": "format_compression",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "select",
                            "label": "Compressione",
                            "cssClass": "",
                            "value": "",
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
                                    "value": "CCITT 1D",
                                    "description": ""
                                },
                                {
                                    "value": "CCITT Group 3",
                                    "description": ""
                                },
                                {
                                    "value": "CCITT Group 4",
                                    "description": ""
                                },{
                                    "value": "LZW",
                                    "description": ""
                                },
                                {
                                    "value": "JPG",
                                    "description": ""
                                },{
                                    "value": "PNG",
                                    "description": ""
                                },
                                {
                                    "value": "DJVU",
                                    "description": ""
                                }
                            ]
                        }
                    ]
                },
                {
                    "id": "scanning",
                    "repeatable": false,
                    "mandatory": "",
                    "collapsable": false,
                    "type": "object",
                    "label": "Modalità della scansione",
                    "cssClass": "",
                    "attributes": [],
                    "children": [
                        {
                            "id": "sourcetype",
                            "repeatable": false,
                            "mandatory": "",
                            "type": "text",
                            "label": "Caratteristiche fisiche del supporto",
                            "cssClass": "",
                            "value": "",
                            "pattern": ""
                        },
                        {
                            "id": "scanningagency",
                            "repeatable": false,
                            "mandatory": "",
                            "type": "text",
                            "label": "Ente produttore",
                            "cssClass": "",
                            "value": "",
                            "pattern": ""
                        },
                        {
                            "id": "devicesource",
                            "repeatable": false,
                            "mandatory": "",
                            "type": "text",
                            "label": "Tipologia dell'apparecchiatura",
                            "cssClass": "",
                            "value": "",
                            "pattern": ""
                        },
                        {
                            "id": "scanningsystem",
                            "repeatable": false,
                            "mandatory": "",
                            "collapsable": false,
                            "type": "object",
                            "label": "Dispositivo usato",
                            "cssClass": "",
                            "attributes": [],
                            "children": [
                                {
                                    "id": "scanner_manufacturer",
                                    "repeatable": false,
                                    "mandatory": "",
                                    "type": "text",
                                    "label": "Produttore del dispositivo",
                                    "cssClass": "",
                                    "value": "",
                                    "pattern": ""
                                },
                                {
                                    "id": "scanner_model",
                                    "repeatable": false,
                                    "mandatory": "",
                                    "type": "text",
                                    "label": "Modello dell'apparecchiatura",
                                    "cssClass": "",
                                    "value": "",
                                    "pattern": ""
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
                                }
                            ]
                        }
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
                    "type": "text",
                    "readonly":"readonly",
                    "label": "Data di creazione",
                    "cssClass": "",
                    "value": "",
                    "pattern":""
                }
            ]
        },
        {
            "id": "side",
            "repeatable": false,
            "mandatory": "",
            "type": "select",
            "label": "Numero pagine coperte",
            "cssClass": "",
            "value": "",
            "data": [
                {
                    "value": "",
                    "description": "-"
                },
                {
                    "value": "left",
                    "description": ""
                },
                {
                    "value": "right",
                    "description": ""
                },
                {
                    "value": "double",
                    "description": ""
                },
                {
                    "value": "part",
                    "description": ""
                }
            ]
        },
        {
            "id": "scale",
            "repeatable": false,
            "mandatory": "",
            "type": "select",
            "label": "Scala millimetrica",
            "cssClass": "",
            "value": "",
            "data": [
                {
                    "value": "",
                    "description": "-"
                },
                {
                    "value": "0",
                    "description": "(no)"
                },
                {
                    "value": "1",
                    "description": "(sì)"
                }
            ]
        },
        {
            "id": "target",
            "repeatable": true,
            "mandatory": "",
            "collapsable": true,
            "type": "object",
            "label": "Target",
            "cssClass": "",
            "attributes": [],
            "children": [
                {
                    "id": "targetType",
                    "repeatable": false,
                    "mandatory": "",
                    "type": "select",
                    "label": "Tipo target",
                    "cssClass": "",
                    "value": "",
                    "data": [
                        {
                            "value": "",
                            "description": "-"
                        },
                        {
                            "value": "0",
                            "description": "(esterno)"
                        },
                        {
                            "value": "1",
                            "description": "(interno)"
                        }
                    ]
                },
                {
                    "id": "targetID",
                    "repeatable": false,
                    "mandatory": "required",
                    "type": "text",
                    "label": "Nome del target",
                    "cssClass": "",
                    "value": "",
                    "pattern": ""
                },
                // Mostra solo se targetType = 0
                {
                    "id": "imageData",
                    "repeatable": false,
                    "mandatory": "",
                    "type": "text",
                    "label": "Path immagine digitale",
                    "cssClass": "",
                    "value": "",
                    "pattern": "",
                    "constraint": ["target,targetType,0"]
                },
                {
                    "id": "performanceData",
                    "repeatable": false,
                    "mandatory": "",
                    "type": "text",
                    "label": "Path dati immagine target",
                    "cssClass": "",
                    "value": "",
                    "pattern": ""
                },
                {
                    "id": "profiles",
                    "repeatable": false,
                    "mandatory": "",
                    "type": "text",
                    "label": "Path file con profilo dei colori ICC (o altro)",
                    "cssClass": "",
                    "value": "",
                    "pattern": ""
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
    // attributi per cambiare css
    "buttons": [
        {
            "type": "back",
            "label": "Annulla"
        },
        {
            "type": "save",
            "label": "Conferma"
        }
    ]
};