section_ocr = {
    "id": "section_ocr",
    "name": "Sezione OCR",
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
            "label": "Titolo",
            "cssClass": "",
            "value": "",
            "pattern": ""
        },
        {
            "id": "usage",
            "repeatable": true,
            "mandatory": false,
            "collapsable": false,
            "type": "object",
            "label": "Ambiti d'uso",
            "etichette":"usage",
            "repeatmax": 1,
            "cssClass": "",
            "attributes": [],
            "children": [
                {
                    "id": "usage",
                    "repeatable": false,
                    "mandatory": "",
                    "type": "select",
                    "label": "Ambito d'uso",
                    "cssClass": "",
                    "value": "",
                    "data": [
                        {
                            "value": "0",
                            "description": ""
                        }
                    ]
                },
                {
                    "id": "file",
                    "repeatable": false,
                    "mandatory": "required",
                    "type": "mediapickercustom",
                    "mediatype": "ocr",
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
                    "readonly":"readonly",
                    "mandatory": "",
                    "type": "text",
                    "label": "Dimensione del file",
                    "cssClass": "",
                    "value": "",
                    "pattern": ""
                },
                {
                    "id": "source",
                    "repeatable": false,
                    "mandatory": "required",
                    "type": "mediapickercustom",
                    "mediatype": "img",
                    "label": "File sorgente",
                    "cssClass": "",
                    "value": "",
                    "pattern": ""
                },
                {
                    "id": "format",
                    "repeatable": false,
                    "mandatory": "required",
                    "collapsable": false,
                    "type": "object",
                    "label": "Formato media",
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
                            "value": "",
                            "data": [
                                {
                                    "value": "",
                                    "description": "-"
                                },
                                {
                                    "value": "text/plain",
                                    "description": ""
                                },
                                {
                                    "value": "text/xml",
                                    "description": ""
                                },
                                {
                                    "value": "text/html",
                                    "description": ""
                                },
                                {
                                    "value": "text/rtf",
                                    "description": ""
                                },
                                {
                                    "value": "application/msword",
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
                                    "value": "ZIP",
                                    "description": ""
                                },
                                {
                                    "value": "RAR",
                                    "description": ""
                                },
                                {
                                    "value": "GZ",
                                    "description": ""
                                }
                            ]
                        }
                    ]
                },
                {
                    "id": "software_ocr",
                    "repeatable": false,
                    "mandatory": "",
                    "type": "text",
                    "label": "Tipo software",
                    "cssClass": "",
                    "value": "",
                    "pattern": ""
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
                    "pattern":"",
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
