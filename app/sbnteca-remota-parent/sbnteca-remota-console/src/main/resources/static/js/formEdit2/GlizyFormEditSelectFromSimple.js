Glizy.oop.declare("glizy.FormEdit.selectfromsimple", {
    $extends: Glizy.oop.get('glizy.FormEdit.standard'),
    multiple: null,

    initialize: function (element) {
        element.data('instance', this);
        this.$element = element;

        element.removeAttr('value');
        element.css('width', '500px');

        var fieldName = element.data('field') || element.attr('name');
        var proxy = element.data('proxy');
        var minimumInputLength = $(this).data('min_input_length') || 0;

        element.autocomplete({
            source: function(request,response) {
                $.ajax({
                    url: proxy,
                    dataType: "json",
                    data: {
                        field: fieldName,
                        prefix: request.term
                    },
                    success: function(data) {
                        response(data);
                    }
                });
            },
            minLength: minimumInputLength
        });
    }
});