section_audio = { "id": "section_audio", "name": "Sezione AUDIO",
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
            "id": "SectionAudio",
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
                    "label": "Gruppo audio associato",
                    "cssClass": "",
                    "value": "",
                    "data": []
                },
                {
                    "id": "file",
                    "repeatable": false,
                    "mandatory": "required",
                    "type": "mediapickercustom",
                    "mediatype": "audio",
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
                    "id": "audio_dimensions",
                    "repeatable": false,
                    "mandatory": "required",
                    "collapsable": false,
                    "type": "object",
                    "label": "Durata audio",
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
                            "label": "Bitrate del campione",
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
                    "label": "Trascrizione digitale",
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
            "label": "Annulla"
        },
        {
            "type": "save",
            "label": "Conferma"
        }
    ]
};