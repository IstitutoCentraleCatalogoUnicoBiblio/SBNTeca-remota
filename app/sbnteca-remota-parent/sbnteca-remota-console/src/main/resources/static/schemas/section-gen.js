section_gen = { "id": "section_gen", "name": "Sezione Gen",
    "attributes": [
        // Al momento non servono
        /*{
            "id": "creation",
            "repeatable": false,
            "mandatory": "",
            "type": "datetime",
            "label": "Data di creazione",
            "cssClass": "",
            "value": ""
        },
        {
            "id": "last_update",
            "repeatable": false,
            "mandatory": "",
            "type": "datetime",
            "label": "Data di ultima modifica",
            "cssClass": "",
            "value": ""
        }*/
    ],
    "contents": [
        {
            "id": "SectionGen",
            "type": "section"
        },
        {
            "id": "project",
            "repeatable": false,
            "mandatory": "required",
            "readonly": "readonly",
            "type": "selectfromsimple",
            "label": "Progetto padre",
            "cssClass": "autocomplete",
            "value": "",
            "pattern": ""
        },
        {
            "id": "stprog",
            "repeatable": false,
            "mandatory": "required",
            "type": "selectfromsimple",
            "label": "Progetto di digitalizzazione",
            "cssClass": "autocomplete",
            "value": "",
            "pattern": ""
        },
        {
            "id": "collection",
            "repeatable": false,
            "mandatory": "",
            "type": "selectfromsimple",
            "label": "Collezione",
            "cssClass": "autocomplete",
            "value": "",
            "pattern": ""
        },
        {
            "id": "agency",
            "repeatable": false,
            "mandatory": "required",
            "type": "selectfromsimple",
            "label": "Agenzia",
            "cssClass": "autocomplete",
            "value": "",
            "pattern": ""
        },
        {
            "id": "access_rights",
            "repeatable": false,
            "mandatory": "required",
            "type": "select",
            "label": "Condizioni di accesso",
            "cssClass": "",
            "value": "",
            "data": [
                {
                    "value": "",
                    "description": "-"
                },
                {
                    "value": "0",
                    "description": "(uso riservato all'interno dell'istituzione)"
                },
                {
                    "value": "1",
                    "description": "(uso pubblico)"
                }
            ]
        },
        {
            "id": "completeness",
            "repeatable": false,
            "mandatory": "required",
            "type": "select",
            "label": "Completezza",
            "cssClass": "",
            "value": "",
            "data": [
                {
                    "value": "",
                    "description": "-"
                },
                {
                    "value": "0",
                    "description": "(digitalizzazione completa)"
                },
                {
                    "value": "1",
                    "description": "(digitalizzazione parziale)"
                }
            ]
        },
        {
            "id": "img_group",
            "repeatable": true,
            "mandatory": "",
            "collapsable": true,
            "type": "object",
            "label": "Gruppo Immagine",
            "etichette":"id",
            "cssClass": "",
            "attributes": [
                // {
                //     "id": "id",
                //     "repeatable": false,
                //     "mandatory": "required",
                //     "type": "text",
                //     "label": "Identificatore",
                //     "cssClass": "",
                //     "value": "",
                //     "pattern": "^[A-Za-z]+[A-Za-z0-9._-]*"
                // }
            ],
            "children": [
                {
                    "id": "id",
                    "repeatable": false,
                    "mandatory": "required",
                    "type": "text",
                    "label": "Identificatore",
                    "cssClass": "",
                    "value": "",
                    "pattern": "^[A-Za-z]+[A-Za-z0-9._-]*"
                },
                {
                    "id": "image_metrics",
                    "repeatable": false,
                    "mandatory": "required",
                    "collapsable": false,
                    "type": "object",
                    "label": "Caratteristiche immagine",
                    "cssClass": "",
                    "attributes": [],
                    "children": [
                        {
                            "id": "samplingfrequencyunit",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "select",
                            "label": "Unità di misura",
                            "cssClass": "",
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
                            ],
                            "value": ""
                        },
                        {
                            "id": "samplingfrequencyplane",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "select",
                            "label": "Piano focale del campionamento",
                            "cssClass": "",
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
                            ],
                            "value": ""
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
                            "id": "photometricinterpretation",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "select",
                            "label": "Interpretazione fotometrica dei bit del campione",
                            "cssClass": "",
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
                            ],
                            "value": ""
                        },
                        {
                            "id": "bitpersample",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "select",
                            "label": "Numero di bit per ciascun campione",
                            "cssClass": "",
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
                            ],
                            "value": ""
                        }
                    ]
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
                            ],
                            "value": ""
                        },
                        {
                            "id": "format_compression",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "select",
                            "label": "Compressione",
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
                            ],
                            "value": ""
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
                }
            ]
        },
        {
            "id": "audio_group",
            "repeatable": true,
            "mandatory": "",
            "collapsable": true,
            "type": "object",
            "label": "Gruppo Audio",
            "etichette":"id",
            "cssClass": "",
            "attributes": [
                // {
                //     "id": "id",
                //     "type": "text",
                //     "repeatable": false,
                //     "mandatory": "required",
                //     "label": "Identificatore",
                //     "cssClass": "",
                //     "value": "",
                //     "pattern": "^[A-Za-z]+[A-Za-z0-9._-]*"
                // }
            ],
            "children": [
                {
                    "id": "id",
                    "type": "text",
                    "repeatable": false,
                    "mandatory": "required",
                    "label": "Identificatore",
                    "cssClass": "",
                    "value": "",
                    "pattern": "^[A-Za-z]+[A-Za-z0-9._-]*"
                },
                {
                    "id": "audio_metrics",
                    "repeatable": false,
                    "mandatory": "required",
                    "collapsable": false,
                    "type": "object",
                    "label": "Caratteristiche audio",
                    "cssClass": "",
                    "attributes": [],
                    "children": [
                        {
                            "id": "samplingfrequency",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "select",
                            "label": "Frequenza di campionamento",
                            "cssClass": "",
                            "data": [
                                {
                                    "value": "",
                                    "description": "-"
                                },
                                {
                                    "value": "8",
                                    "description": ""
                                },
                                {
                                    "value": "11.025",
                                    "description": ""
                                },
                                {
                                    "value": "12",
                                    "description": ""
                                },
                                {
                                    "value": "16",
                                    "description": ""
                                },
                                {
                                    "value": "22.05",
                                    "description": ""
                                },
                                {
                                    "value": "24",
                                    "description": ""
                                },
                                {
                                    "value": "32",
                                    "description": ""
                                },
                                {
                                    "value": "44.1",
                                    "description": ""
                                },
                                {
                                    "value": "48",
                                    "description": ""
                                },
                                {
                                    "value": "96",
                                    "description": ""
                                }
                            ],
                            "value": ""
                        },
                        // Questi due campi successivi sono alternativi, deve essere presente almeno uno dei due
                        {
                            "id": "bitpersample",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "select",
                            "label": "Numero di bit del campione",
                            "cssClass": "",
                            "data": [
                                {
                                    "value": "",
                                    "description": "-"
                                },
                                {
                                    "value": "8",
                                    "description": ""
                                },
                                {
                                    "value": "16",
                                    "description": ""
                                },
                                {
                                    "value": "24",
                                    "description": ""
                                }
                            ],
                            "value": ""
                        },
                        {
                            "id": "bitrate",
                            "repeatable": false,
                            "mandatory": "required",
                            "type": "select",
                            "label": "Kbps del campione",
                            "cssClass": "",
                            "data": [
                                {
                                    "value": "",
                                    "description": "-"
                                },
                                {
                                    "value": "24",
                                    "description": ""
                                },
                                {
                                    "value": "32",
                                    "description": ""
                                },
                                {
                                    "value": "48",
                                    "description": ""
                                },
                                {
                                    "value": "56",
                                    "description": ""
                                },
                                {
                                    "value": "64",
                                    "description": ""
                                },
                                {
                                    "value": "96",
                                    "description": ""
                                },
                                {
                                    "value": "128",
                                    "description": ""
                                },
                                {
                                    "value": "160",
                                    "description": ""
                                },
                                {
                                    "value": "192",
                                    "description": ""
                                },
                                {
                                    "value": "256",
                                    "description": ""
                                },
                                {
                                    "value": "320",
                                    "description": ""
                                },
                                {
                                    "value": "384",
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
                    "label": "Formato audio",
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
                            "data": [
                                {
                                    "value": "",
                                    "description": "-"
                                },
                                {
                                    "value": "audio/wav",
                                    "description": ""
                                },
                                {
                                    "value": "audio/mpeg",
                                    "description": ""
                                },
                                {
                                    "value": "audio/mpg",
                                    "description": ""
                                },
                                {
                                    "value": "audio/mp3",
                                    "description": ""
                                },
                                {
                                    "value": "audio/x-mpeg",
                                    "description": ""
                                },
                                {
                                    "value": "audio/midi",
                                    "description": ""
                                },
                                {
                                    "value": "audio/x-realaudio",
                                    "description": ""
                                }
                            ],
                            "value": ""
                        },
                        {
                            "id": "format_compression",
                            "repeatable": false,
                            "mandatory": "",
                            "type": "select",
                            "label": "Compressione",
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
                                    "value": "Linear PCM",
                                    "description": ""
                                },
                                {
                                    "value": "MPEG-1 layer 1",
                                    "description": ""
                                },
                                {
                                    "value": "MPEG-1 layer 2",
                                    "description": ""
                                },{
                                    "value": "MPEG-1 layer 3",
                                    "description": ""
                                },
                                {
                                    "value": "AC3",
                                    "description": ""
                                },{
                                    "value": "Dolby",
                                    "description": ""
                                },
                                {
                                    "value": "DTS",
                                    "description": ""
                                }
                            ],
                            "value": ""
                        },
                        {
                            "id": "channel_configuration",
                            "repeatable": false,
                            "mandatory": "",
                            "type": "select",
                            "label": "Configurazione canali audio",
                            "cssClass": "",
                            "data": [
                                {
                                    "value": "",
                                    "description": "-"
                                },
                                {
                                    "value": "Mono",
                                    "description": ""
                                },
                                {
                                    "value": "Dual mono",
                                    "description": ""
                                },
                                {
                                    "value": "Joint stereo",
                                    "description": ""
                                },
                                {
                                    "value": "Stereo",
                                    "description": ""
                                },{
                                    "value": "2ch",
                                    "description": ""
                                },
                                {
                                    "value": "105",
                                    "description": ""
                                },{
                                    "value": "4ch",
                                    "description": ""
                                },
                                {
                                    "value": "5.1ch",
                                    "description": ""
                                },
                                {
                                    "value": "6.1ch",
                                    "description": ""
                                }
                            ],
                            "value": ""
                        }
                    ]
                },
                {
                    "id": "transcription",
                    "repeatable": false,
                    "mandatory": "",
                    "collapsable": false,
                    "type": "object",
                    "label": "Modalità di trascrizione digitale",
                    "cssClass": "",
                    "attributes": [],
                    "children": [
                        {
                            "id": "sourcetype",
                            "type": "text",
                            "repeatable": false,
                            "mandatory": "",
                            "label": "Fonte analogica usata",
                            "cssClass": "",
                            "value": "",
                            "pattern": ""
                        },
                        {
                            "id": "transcriptionagency",
                            "type": "text",
                            "repeatable": false,
                            "mandatory": "",
                            "label": "Istituzione",
                            "cssClass": "",
                            "value": "",
                            "pattern": ""
                        },
                        {
                            "id": "transcriptiondate",
                            "type": "datetime",
                            "mandatory": "",
                            "format": "yyyy-mm-ddThh:ii:ss",
                            "repeatable": false,
                            "label": "Data di digitalizzazione",
                            "cssClass": "",
                            "value": ""
                        },
                        {
                            "id": "devicesource",
                            "type": "text",
                            "repeatable": false,
                            "mandatory": "",
                            "label": "Apparecchiatura di digitalizzazione",
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
                                    "type": "object",
                                    "repeatable": false,
                                    "mandatory": "required",
                                    "label": "Dispositivo audio utilizzato",
                                    "cssClass": "",
                                    "value": "",
                                    "attributes": [
                                        {
                                            "id": "Type",
                                            "type": "text",
                                            "repeatable": false,
                                            "mandatory": "required",
                                            "label": "Tipo",
                                            "cssClass": "",
                                            "value": "",
                                            "pattern": ""
                                        },
                                        {
                                            "id": "Unique_identifier",
                                            "type": "text",
                                            "repeatable": false,
                                            "mandatory": "",
                                            "label": "Codice identificativo",
                                            "cssClass": "",
                                            "value": "",
                                            "pattern": ""
                                        },
                                        {
                                            "id": "Comments",
                                            "type": "text",
                                            "repeatable": false,
                                            "mandatory": "",
                                            "label": "Commento",
                                            "cssClass": "",
                                            "value": "",
                                            "pattern": ""
                                        }
                                    ],
                                    "children": []
                                },
                                {
                                    "id": "device_manufacturer",
                                    "type": "text",
                                    "repeatable": false,
                                    "mandatory": "required",
                                    "label": "Produttore del dispositivo",
                                    "cssClass": "",
                                    "value": "",
                                    "pattern": ""
                                },
                                {
                                    "id": "device_model",
                                    "type": "object",
                                    "repeatable": false,
                                    "mandatory": "required",
                                    "label": "Modello del dispositivo",
                                    "cssClass": "",
                                    "value": "",
                                    "attributes": [
                                        {
                                            "id": "Model",
                                            "type": "text",
                                            "repeatable": false,
                                            "mandatory": "required",
                                            "label": "Nome",
                                            "cssClass": "",
                                            "value": "",
                                            "pattern": ""
                                        },
                                        {
                                            "id": "Serial_Number",
                                            "type": "text",
                                            "repeatable": false,
                                            "mandatory": "",
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
                                    "type": "text",
                                    "repeatable": false,
                                    "mandatory": "",
                                    "label": "Software di acquisizione",
                                    "cssClass": "",
                                    "value": "",
                                    "pattern": ""
                                },
                                {
                                    "id": "device_settings",
                                    "type": "text",
                                    "repeatable": false,
                                    "mandatory": "",
                                    "label": "Impostazioni usate",
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
                            "collapsable": true,
                            "type": "object",
                            "label": "Modalità di trascrizione digitale",
                            "cssClass": "",
                            "attributes": [],
                            "children": [

                            ]
                        },
                        {
                            "id": "transcriptiondata",
                            "repeatable": true,
                            "mandatory": "",
                            "collapsable": true,
                            "type": "object",
                            "label": "Dati del processo di digitalizzazione",
                            "cssClass": "",
                            "attributes": [],
                            "children": [

                            ]
                        }*/
                    ]
                }
            ]
        },
        {
            "id": "video_group",
            "repeatable": true,
            "mandatory": "",
            "collapsable": true,
            "type": "object",
            "label": "Gruppo Video",
            "etichette":"id",
            "cssClass": "",
            "attributes": [
                // {
                //     "id": "id",
                //     "type": "text",
                //     "repeatable": false,
                //     "mandatory": "required",
                //     "label": "Identificatore",
                //     "cssClass": "",
                //     "value": "",
                //     "pattern": "^[A-Za-z]+[A-Za-z0-9._-]*"
                // }
            ],
            "children": [
                {
                    "id": "id",
                    "type": "text",
                    "repeatable": false,
                    "mandatory": "required",
                    "label": "Identificatore",
                    "cssClass": "",
                    "value": "",
                    "pattern": "^[A-Za-z]+[A-Za-z0-9._-]*"
                },
                {
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
                            "mandatory": "required",
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
                            "label": "Codifica contenuto",
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
                    "label": "Digitalizzazione",
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
                                    "id": "device_setting",
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
                }
            ]
        }
    ],
    "buttons": [
        {
            "type": "save",
            "label": "Salva sezione"
        }
    ]
};