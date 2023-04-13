section_bib = { "id": "section_bib", "name": "Sezione Bib",
    "attributes": [
        {
            "id": "level",
            "repeatable": false,
            "mandatory": "",
            "type": "select",
            "label": "Livello",
            "cssClass": "",
            "value": "",
            "data": [
                {
                    "value": "a",
                    "description": "(spoglio)"
                },
                {
                    "value": "m",
                    "description": "(monografia)"
                },
                {
                    "value": "s",
                    "description": "(seriale)"
                },
                {
                    "value": "c",
                    "description": "(raccolta prodotta dall'istituzione)"
                },
                {
                    "value": "f",
                    "description": "(unità archivistica)"
                },
                {
                    "value": "d",
                    "description": "(unità documentaria)"
                }

            ]
        }
    ],
    "contents": [
    {
        "id": "SectionBib",
        "type": "section"
    },
    {
        "id": "identifiers",
        "repeatable": true,
        "mandatory": "",
        "collapsable": false,
        "type": "object",
        "label": "Identificatori",
        "cssClass": "",
        "attributes": [],
        "children": [
            {
                "id": "identifier",
                "type": "selectfromsimple",
                "repeatable": true,
                "mandatory": "required",
                "label": "Identificatore",
                "cssClass": "autocomplete",
                "value": "",
                "pattern": ""
            }
        ]
    },
    {
        "id": "piece",
        "repeatable": true,
        "mandatory": "",
        "collapsable": false,
        "type": "object",
        "repeatmax": 1,
        "label": "Unità componenti",
        "cssClass": "",
        "attributes": [],
        "children": [
            {
                "id": "year",
                "type": "text",
                "repeatable": false,
                "mandatory": "required",
                "label": "Anno",
                "cssClass": "",
                "value": "",
                "pattern": ""
            },
            {
                "id": "issue",
                "type": "text",
                "repeatable": false,
                "mandatory": "required",
                "label": "Estremi del fascicolo",
                "cssClass": "",
                "value": "",
                "pattern": ""
            },
            {
                "id": "stpiece_per",
                "type": "text",
                "repeatable": false,
                "mandatory": "",
                "label": "Riferimento al fascicolo",
                "cssClass": "",
                "value": "",
                "pattern": "\((\\d{4}(/\\d{4})?(([0-1][1-9])(/[0-1][1-9])?([1-31](/[1-31])?)?|(([21-24](/[21-24])?)|([31-34](/[31-34])?))*))?(/\\d{4}(([0-1][1-9])([1-31])*|([21-24]|[31-34])*)?)?\)\\d{1,4}(\:(\\d{1,4}(/\\d{1,4})?))*"
            },
            {
                "id": "part_number",
                "type": "text",
                "repeatable": false,
                "mandatory": "",
                "label": "Numero unità componente",
                "cssClass": "",
                "value": "",
                "pattern": ""
            },
            {
                "id": "part_name",
                "type": "text",
                "repeatable": false,
                "mandatory": "",
                "label": "Titolo unità componente",
                "cssClass": "",
                "value": "",
                "pattern": ""
            },
            {
                "id": "stpiece_vol",
                "type": "text",
                "repeatable": false,
                "mandatory": "",
                "label": "Riferimento a unità componente",
                "cssClass": "",
                "value": "",
                "pattern": "\\d{1,3}\:\\d{1,4}(\:\\d{1,4})*"
            }
        ]
    },
    {
        "id": "titles",
        "repeatable": true,
        "mandatory": "",
        "collapsable": false,
        "type": "object",
        "label": "Titoli",
        "cssClass": "",
        "attributes": [
            /*{
                "id": "xml:lang",
                "type": "text",
                "repeatable": false,
                "mandatory": "",
                "label": "XML:LANG",
                "cssClass": "",
                "value": "",
                "pattern": "^$|[A-Za-z]{2}"
            }*/
        ],
        "children": [
            {
                "id": "title",
                "type": "selectfromsimple",
                "repeatable": true,
                "mandatory": "",
                "label": "Titolo",
                "cssClass": "autocomplete",
                "value": "",
                "pattern": ""
            }
        ]
    },
    {
        "id": "creators",
        "repeatable": true,
        "mandatory": "",
        "collapsable": false,
        "type": "object",
        "label": "Autori",
        "cssClass": "",
        "attributes": [
            /*{
                "id": "xml:lang",
                "type": "text",
                "repeatable": false,
                "mandatory": "",
                "label": "XML:LANG",
                "cssClass": "",
                "value": "",
                "pattern": "^$|[A-Za-z]{2}"
            }*/
        ],
        "children": [
            {
                "id": "creator",
                "type": "selectfromsimple",
                "repeatable": true,
                "mandatory": "",
                "label": "Autore",
                "cssClass": "autocomplete",
                "value": "",
                "pattern": ""
            }
        ]
    },
    {
        "id": "publishers",
        "repeatable": true,
        "mandatory": "",
        "collapsable": false,
        "type": "object",
        "label": "Editori",
        "cssClass": "",
        "attributes": [
            /*{
                "id": "xml:lang",
                "type": "text",
                "repeatable": false,
                "mandatory": "",
                "label": "XML:LANG",
                "cssClass": "",
                "value": "",
                "pattern": "^$|[A-Za-z]{2}"
            }*/
        ],
        "children": [
            {
                "id": "publisher",
                "type": "selectfromsimple",
                "repeatable": true,
                "mandatory": "",
                "label": "Entità",
                "cssClass": "autocomplete",
                "value": "",
                "pattern": ""
            }
        ]
    },
    {
        "id": "subjects",
        "repeatable": true,
        "mandatory": "",
        "collapsable": false,
        "type": "object",
        "label": "Parole chiave/soggetti",
        "cssClass": "",
        "attributes": [
            /*{
                "id": "xml:lang",
                "type": "text",
                "repeatable": false,
                "mandatory": "",
                "label": "XML:LANG",
                "cssClass": "",
                "value": "",
                "pattern": "^$|[A-Za-z]{2}"
            }*/
        ],
        "children": [
            {
                "id": "subject",
                "type": "selectfromsimple",
                "repeatable": true,
                "mandatory": "",
                "label": "Parola chiave/soggetto",
                "cssClass": "autocomplete",
                "value": "",
                "pattern": ""
            }
        ]
    },
    {
        "id": "descriptions",
        "repeatable": true,
        "mandatory": "",
        "collapsable": false,
        "type": "object",
        "label": "Descrizioni",
        "cssClass": "",
        "attributes": [
            /*{
                "id": "xml:lang",
                "type": "text",
                "repeatable": false,
                "mandatory": "",
                "label": "XML:LANG",
                "cssClass": "",
                "value": "",
                "pattern": "^$|[A-Za-z]{2}"
            }*/
        ],
        "children": [
            {
                "id": "description",
                "type": "selectfromsimpletextarea",
                "repeatable": true,
                "mandatory": "",
                "label": "Descrizione",
                "cssClass": "autocomplete",
                "value": "",
                "pattern": "",
                "rows": 10
            }
        ]
    },
    {
        "id": "contributors",
        "repeatable": true,
        "mandatory": "",
        "collapsable": false,
        "type": "object",
        "label": "Entità responsabili di contributo",
        "cssClass": "",
        "attributes": [
            /*{
                "id": "xml:lang",
                "type": "text",
                "repeatable": false,
                "mandatory": "",
                "label": "XML:LANG",
                "cssClass": "",
                "value": "",
                "pattern": "^$|[A-Za-z]{2}"
            }*/
        ],
        "children": [
            {
                "id": "contributor",
                "type": "selectfromsimple",
                "repeatable": true,
                "mandatory": "",
                "label": "Entità",
                "cssClass": "autocomplete",
                "value": "",
                "pattern": ""
            }
        ]
    },
    {
        "id": "dates",
        "repeatable": true,
        "mandatory": "",
        "collapsable": false,
        "type": "object",
        "label": "Date",
        "cssClass": "",
        "attributes": [
        ],
        "children": [
            {
                "id": "date",
                "type": "text",
                "repeatable": true,
                "mandatory": "",
                "label": "Data",
                "cssClass": "autocomplete",
                "value": "",
                "pattern": ""
            }
        ]
    },
    {
        "id": "types",
        "repeatable": true,
        "mandatory": "",
        "collapsable": false,
        "type": "object",
        "label": "Tipi",
        "cssClass": "",
        "attributes": [
        ],
        "children": [
            {
                "id": "type",
                "type": "selectfromsimple",
                "repeatable": true,
                "mandatory": "",
                "label": "Tipo",
                "cssClass": "autocomplete",
                "value": "",
                "pattern": ""
            }
        ]
    },
    {
        "id": "formats",
        "repeatable": true,
        "mandatory": "",
        "collapsable": false,
        "type": "object",
        "label": "Formati fisici",
        "cssClass": "",
        "attributes": [],
        "children": [
            {
                "id": "format",
                "type": "selectfromsimple",
                "repeatable": true,
                "mandatory": "",
                "label": "Formato",
                "cssClass": "autocomplete",
                "value": "",
                "pattern": ""
            }
        ]
    },
    {
        "id": "sources",
        "repeatable": true,
        "mandatory": "",
        "collapsable": false,
        "type": "object",
        "label": "Fonti",
        "cssClass": "",
        "attributes": [],
        "children": [
            {
                "id": "source",
                "type": "selectfromsimple",
                "repeatable": true,
                "mandatory": "",
                "label": "Fonte",
                "cssClass": "autocomplete",
                "value": "",
                "pattern": ""
            }
        ]
    },
    {
        "id": "languages",
        "repeatable": true,
        "mandatory": "",
        "collapsable": false,
        "type": "object",
        "label": "Lingue",
        "cssClass": "",
        "attributes": [],
        "children": [
            {
                "id": "language",
                "type": "selectfromsimple",
                "repeatable": true,
                "mandatory": "",
                "label": "Lingua",
                "cssClass": "autocomplete",
                "value": "",
                "pattern": "[a-z]{2}(-[a-z]{2})?"
            }
        ]
    },
    {
        "id": "relations",
        "repeatable": true,
        "mandatory": "",
        "collapsable": false,
        "type": "object",
        "label": "Legami gerarchici / Titolo uniforme / Incipit / Altri titoli / ecc.",
        "cssClass": "",
        "attributes": [],
        "children": [
            {
                "id": "relation",
                "type": "selectfromsimple",
                "repeatable": true,
                "mandatory": "",
                "label": "Riferimento",
                "cssClass": "autocomplete",
                "value": "",
                "pattern": ""
            }
        ]
    },
    {
        "id": "coverages",
        "repeatable": true,
        "mandatory": "",
        "collapsable": false,
        "type": "object",
        "label": "Estensione o scopo",
        "cssClass": "",
        "attributes": [],
        "children": [
            {
                "id": "coverage",
                "type": "selectfromsimple",
                "repeatable": true,
                "mandatory": "",
                "label": "Estensione o scopo",
                "cssClass": "autocomplete",
                "value": "",
                "pattern": ""
            }
        ]
    },
    {
        "id": "rights",
        "repeatable": true,
        "mandatory": "",
        "collapsable": false,
        "type": "object",
        "label": "Diritti",
        "cssClass": "",
        "attributes": [],
        "children": [
            {
                "id": "right",
                "type": "selectfromsimple",
                "repeatable": true,
                "mandatory": "",
                "label": "Diritti",
                "cssClass": "autocomplete",
                "value": "",
                "pattern": ""
            }
        ]
    },
    {
        "id": "holdings",
        "repeatable": true,
        "mandatory": "",
        "collapsable": true,
        "type": "object",
        "etichette":"id",
        "label": "Localizzazioni, inventari e collocazioni",
        "cssClass": "",
        "attributes": [
            // {
            //     "id": "id",
            //     "type": "text",
            //     "repeatable": false,
            //     "mandatory": "",
            //     "label": "ID",
            //     "cssClass": "",
            //     "value": "",
            //     "pattern": ""
            // }
        ],
        "children": [
            {
                "id": "id",
                "type": "text",
                "repeatable": false,
                "mandatory": "",
                "label": "ID",
                "cssClass": "",
                "value": "",
                "pattern": ""
            },
            {
                "id": "library",
                "type": "selectfromsimple",
                "repeatable": false,
                "mandatory": "",
                "label": "Istituzione",
                "cssClass": "autocomplete",
                "value": "",
                "pattern": ""
            },
            {
                "id": "inventory_number",
                "type": "text",
                "repeatable": false,
                "mandatory": "",
                "label": "Numero d'inventario",
                "cssClass": "",
                "value": "",
                "pattern": ""
            },
            {
                "id": "shelfmark",
                "type": "object",
                "repeatable": true,
                "mandatory": "",
                "collapsable": false,
                "label": "Collocazioni",
                "cssClass": "",
                "attributes": [
                    {
                        "id": "type",
                        "type": "text",
                        "repeatable": false,
                        "mandatory": "",
                        "label": "Tipo di collocazione",
                        "cssClass": "",
                        "value": "",
                        "pattern": ""
                    }
                ],
                "children": [
                    {
                        "id": "shelfmark",
                        "type": "text",
                        "repeatable": false,
                        "mandatory": "",
                        "label": "Collocazione",
                        "cssClass": "",
                        "value": "",
                        "pattern": ""
                    }
                ],
                "value": ""
            }
        ]
    },
    {
        "id": "local_bib",
        "repeatable": false,
        "mandatory": "",
        "collapsable": true,
        "type": "object",
        "label": "Sistemi di catalogazione specializzata",
        "cssClass": "",
        "attributes": [],
        "children": [
            {
                "id": "geo_coords",
                "type": "object",
                "repeatable": true,
                "mandatory": "",
                "collapsable": false,
                "label": "Coordinate geografiche",
                "cssClass": "",
                "attributes": [],
                "children": [
                    {
                        "id": "geo_coord",
                        "type": "text",
                        "repeatable": false,
                        "mandatory": "",
                        "label": "Coordinata",
                        "cssClass": "",
                        "value": "",
                        "pattern": ""
                    }
                ],
                "value": ""
            },
            {
                "id": "not_dates",
                "type": "object",
                "repeatable": true,
                "mandatory": "",
                "collapsable": false,
                "label": "Date di notifica",
                "cssClass": "",
                "attributes": [],
                "children": [
                    {
                        "id": "not_date",
                        "type": "text",
                        "repeatable": false,
                        "mandatory": "",
                        "label": "Data",
                        "cssClass": "",
                        "value": "",
                        "pattern": ""
                    }
                ],
                "value": ""
            }
        ]
    }],
    "buttons": [
        {
            "type": "save",
            "label": "Salva sezione"
        }
    ]
};