section_dis = {
    "id": "section_dis",
    "name": "Sezione DIS",
    "attributes": [],
    "contents": [
        {
            "id": "dis_item",
            "repeatable": true,
            "mandatory": "",
            "collapsable": true,
            "type": "object",
            "label": "Elementi DIS",
            "cssClass": "",
            "attributes": [],
            "children": [
                {
                    "id": "file",
                    "repeatable": false,
                    "mandatory": "required",
                    "type": "mediapickercustom",
                    "mediatype": "dis",
                    "label": "Collegamento al file",
                    "cssClass": "",
                    "value": "",
                    "pattern": ""
                },
                {
                    "id": "preview",
                    "repeatable": false,
                    "mandatory": "",
                    "type": "select",
                    "label": "Preview",
                    "cssClass": "",
                    "value": "",
                    "pattern": "",
                    "data": [
                        {
                            "value": "",
                            "description": "-"
                        },
                        {
                            "value": "thumbnail",
                            "description": "(per immagini)"
                        },
                        {
                            "value": "sample",
                            "description": "(per altri media)"
                        }
                    ]
                },
                {
                    "id": "available",
                    "repeatable": false,
                    "mandatory": "",
                    "type": "text",
                    "label": "Condizioni di accessibilità",
                    "cssClass": "",
                    "value": "",
                    "pattern": ""
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