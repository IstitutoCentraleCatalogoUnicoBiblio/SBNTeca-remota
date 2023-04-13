Glizy.oop.declare("glizy.FormEdit.checkbox", {
    $extends: Glizy.oop.get('glizy.FormEdit.standard'),

    getValue: function () {
        return this.$element.val();
    },

    setValue: function (value) {
        this.$element.val(value);
    },
});