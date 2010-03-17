// Place your application-specific JavaScript functions and classes here
// This file is automatically included by javascript_include_tag :defaults

function refresh_project() {
    new Ajax.Request('/projects/new/project_list', {
        asynchronous:true,
        evalScripts:true,
        method:'get',
        parameters:'authenticity_token=' + encodeURIComponent('VlQd7RFLuHw4NJxmE03vltVWgjf/pyfVBKHnQpssjA0=')
    })
}

document.observe("dom:loaded", function() {
    style_change_register('main_menu');
    
    board_tabmenu_style_change_register();
});


function group_style_change_register(){
    var items = $$('li.group_item');
    if(items && items.length>0) {
        items[0].className = 'group_current';
    }
    style_change_register('group')
}

function board_tabmenu_style_change_register() {
    style_change_register('board_tabmenu')
}

function style_change_register(css_class) {
    var item_cssClass = css_class+'_item';
    var current_cssClass = css_class+'_current';
    
    var items = $$('li.'+item_cssClass+' a');
    if(items) {
        for(var i=0; i<items.length;i++) {
            (items[i]).observe('click', function(event){
                style_change_handler(item_cssClass, current_cssClass, event);
            });
        }
    }
    var currents = $$('li.'+current_cssClass+' a');
    if(currents) {
        for(var j=0; j<currents.length;j++) {
            (currents[j]).observe('click', function(event){
                style_change_handler(item_cssClass, current_cssClass, event);
            });
        }
    }
}

function style_change_handler(item_cssClass, current_cssClass, event) {
    var currents = $$('li.'+current_cssClass);
    if(currents) {
        for(var i=0; i<currents.length; i++) {
            currents[i].className = item_cssClass;
        }
    }
    
    var item = event.element().parentNode;
    if(item) {
        item.className = current_cssClass;
    }
}

function select_all_check_box(is_select) {
    var check_boxes = $$('input.id');
    if(check_boxes) {
        for(var i=0; i<check_boxes.length;i++) {
            check_boxes[i].checked = is_select;
        }
    }
}

function all_selected_id() {
    var check_boxes = $$('input.id');
    var ids='';
    if(check_boxes) {
        for(var i=0; i<check_boxes.length;i++) {
            var check_box = check_boxes[i];
            if(check_box.checked) {
                ids+=check_boxes[i].value+",";
            }
        }
    }
    if(ids.endsWith(',')) {
        return ids.substring(0, ids.length-1);
    }
    return ids;
}

function first_selected_id() {
    var check_boxes = $$('input.id');
    if(check_boxes) {
        for(var i=0; i<check_boxes.length;i++) {
            var check_box = check_boxes[i];
            if(check_box.checked) {
                return check_boxes[i].value;
            }
        }
    }
    return '';
}

function check_selected() {
    var check_boxes = $$('input.id');
    if(check_boxes) {
        for(var i=0; i<check_boxes.length;i++) {
            var check_box = check_boxes[i];
            if(check_box.checked) {
                return true;
            }
        }
    }
    return false;
}

// not used
function show_details(tr) {
    var td = tr.cells[tr.cells.length-1];
    if(td.className == 'chevron_expand') {
        td.className = 'chevron';
    } else {
        td.className = 'chevron_expand';
    }
}

function select_one(tr) {
    if (window.event.srcElement.tagName != 'INPUT') {
        var check_box = tr.cells[0].firstChild;
        check_box.checked= (!check_box.checked);
    }
}