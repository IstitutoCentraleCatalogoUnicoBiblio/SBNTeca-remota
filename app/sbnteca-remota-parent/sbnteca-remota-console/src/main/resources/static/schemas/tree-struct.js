tree_struct = [
    {'id': 'gen', 'type': 'parent', 'label': "Sezione GEN", 'icon': "", 'selected': true},
    {'id': 'bib', 'type': 'parent', 'label': "Sezione BIB", 'icon': "", 'selected': ''},
    {'id': 'stru', 'type': 'parent', 'label': "Sezione STRU", 'icon': "images/stru_index_icon.png", 'selected': ''},
    {'id': 'img', 'type': 'parent', 'label': "Sezione IMG", 'icon': "", 'selected': ''},
    {'id': 'audio', 'type': 'parent', 'label': "Sezione AUDIO", 'icon': "", 'selected': ''},
    {'id': 'video', 'type': 'parent', 'label': "Sezione VIDEO", 'icon': "", 'selected': ''},
    {'id': 'doc', 'type': 'parent', 'label': "Sezione DOC", 'icon': "", 'selected': ''},
    {'id': 'ocr', 'type': 'parent', 'label': "Sezione OCR", 'icon': "", 'selected': ''},
    {'id': 'dis', 'type': 'parent', 'label': "Sezione DIS", 'icon': "", 'selected': ''}
];

// JSON Standard per JSTree, al momento opzionale perché ho un TreeBuilder che genera direttamente l'html che serve
/*tree_struct = { 'core' : {
    'data' : [
        {
            'id': 'gen',
            'text' : 'GEN',
            'state' : {
                'opened' : false,
                'selected' : true
            },
            'children' : []
        },
        {
            'id': 'bib',
            'text' : 'GEN',
            'state' : {
                'opened' : false,
                'selected' : false
            },
            'children' : []
        },
        {
            'id': 'stru',
            'text' : 'GEN',
            'state' : {
                'opened' : true,
                'selected' : false
            },
            'children' : []
        },
        {
            'id': 'img',
            'text' : 'GEN',
            'state' : {
                'opened' : false,
                'selected' : false
            },
            'children' : []
        },
        {
            'id': 'audio',
            'text' : 'GEN',
            'state' : {
                'opened' : false,
                'selected' : false
            },
            'children' : []
        },
        {
            'id': 'video',
            'text' : 'GEN',
            'state' : {
                'opened' : false,
                'selected' : false
            },
            'children' : []
        },
        {
            'id': 'doc',
            'text' : 'GEN',
            'state' : {
                'opened' : false,
                'selected' : true
            },
            'children' : []
        },
        {
            'id': 'ocr',
            'text' : 'GEN',
            'state' : {
                'opened' : false,
                'selected' : false
            },
            'children' : []
        },
        {
            'id': 'dis',
            'text' : 'GEN',
            'state' : {
                'opened' : false,
                'selected' : false
            },
            'children' : []
        }
    ]
} };*/