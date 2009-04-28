function errorMsg(title, msg) {
    Ext.MessageBox.show({
        title: title,
        msg: msg,
        buttons: Ext.MessageBox.OK,
        animEl: 'mb9',
        icon: 'error-icon'
    });
}

function loadDataMask(target) {
    return loadMask(target, '正在加载数据，请稍候！');
}

function loadMask(target, msg) {
    return new Ext.LoadMask(target.id, {
        msg: msg,
        removeMask: true //完成后移除
    });
}

AjaxReturnMark = {
    ERROR:'error'
}

EditablePropertyIndex = {
    project:[1,3,4,5,6,7,8,11],
    process:[2,3,4,5,6,7,8,9]
}