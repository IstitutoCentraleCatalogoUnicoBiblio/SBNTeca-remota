element_node = {
    "name": "Sezione Stru",
    "attributes" : [
    ],
    "contents": [
        {
            "id": "nomenclature",
            "repeatable": false,
            "mandatory": "",
            "collapsable": false,
            "type": "text",
            "label": "Titolo",
            "cssClass": "",
            "attributes": [],
            "children": [],
            "pattern": ""
        },
        // identifier: se non c'è nella stru è lo stesso dell'xml
        {
            "id": "identifier",
            "repeatable": false,
            "mandatory": "",
            "collapsable": false,
            "type": "text",
            "label": "Identificativo MAG",
            "cssClass": "",
            "attributes": [],
            "children": [],
            "pattern": ""
        },
        {
            "id": "resource",
            "repeatable": false,
            "mandatory": "",
            "collapsable": false,
            "type": "select",
            "label": "Risorsa",
            "cssClass": "",
            "attributes": [],
            "children": [],
            "data": [
                {
                    "value": "",
                    "description": "-"
                },
                {
                    "value": "img",
                    "description": ""
                },
                {
                    "value": "audio",
                    "description": ""
                },
                {
                    "value": "video",
                    "description": ""
                },
                {
                    "value": "ocr",
                    "description": ""
                },
                {
                    "value": "doc",
                    "description": ""
                }
            ]
        },
        {
            "id": "start",
            "repeatable": false,
            "mandatory": "",
            "collapsable": false,
            "type": "text",
            "label": "Inizio sequenza",
            "cssClass": "",
            "attributes": [],
            "children": [],
            "pattern": ""
        },
        {
            "id": "stop",
            "repeatable": false,
            "mandatory": "",
            "collapsable": false,
            "type": "text",
            "label": "Fine sequenza",
            "cssClass": "",
            "attributes": [],
            "children": [],
            "pattern": ""
        }
    ],
    "buttons": [
        {
            "type": "save",
            "label": "Salva"
        }
    ]
};