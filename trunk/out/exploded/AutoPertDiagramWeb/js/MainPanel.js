/*
 * $Id: MainPanel.js,v 1.46 2009/03/20 08:56:46 gaudenz Exp $
 * Copyright (c) 2008, Gaudenz Alder
 */
MainPanel = function(graph, history)
{
    var executeLayout = function(layout, animate, ignoreChildCount)
    {
        var cell = graph.getSelectionCell();

        if (cell == null ||
            (!ignoreChildCount &&
             graph.getModel().getChildCount(cell) == 0))
        {
            cell = graph.getDefaultParent();
        }

        // Animates the changes in the graph model except
        // for Camino, where animation is too slow
        if (animate && navigator.userAgent.indexOf('Camino') < 0)
        {
            var listener = function(sender, evt)
            {
                mxUtils.animateChanges(graph, evt.getArgAt(0)/*changes*/);
                graph.getModel().removeListener(listener);
            };

            graph.getModel().addListener(mxEvent.CHANGE, listener);
        }

        layout.execute(cell);
    };

    // Defines various color menus for different colors
    var fillColorMenu = new Ext.menu.ColorMenu(
    {
        items: [
            {
                text: '自动',
                handler: function()
                {
                    graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, mxConstants.NONE);
                }
            },
            '-'
        ],
        handler : function(cm, color)
        {
            if (typeof(color) == "string")
            {
                graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, '#' + color);
            }
        }
    });

    var gradientColorMenu = new Ext.menu.ColorMenu(
    {
        items: [
            {
                text: '向上',
                handler: function()
                {
                    graph.setCellStyles(mxConstants.STYLE_GRADIENT_DIRECTION, mxConstants.DIRECTION_NORTH);
                }
            },
            {
                text: '向右',
                handler: function()
                {
                    graph.setCellStyles(mxConstants.STYLE_GRADIENT_DIRECTION, mxConstants.DIRECTION_EAST);
                }
            },
            {
                text: '向下',
                handler: function()
                {
                    graph.setCellStyles(mxConstants.STYLE_GRADIENT_DIRECTION, mxConstants.DIRECTION_SOUTH);
                }
            },
            {
                text: '向左',
                handler: function()
                {
                    graph.setCellStyles(mxConstants.STYLE_GRADIENT_DIRECTION, mxConstants.DIRECTION_WEST);
                }
            },
            '-',
            {
                text: '自动',
                handler: function()
                {
                    graph.setCellStyles(mxConstants.STYLE_GRADIENTCOLOR, mxConstants.NONE);
                }
            },
            '-'
        ],
        handler : function(cm, color)
        {
            if (typeof(color) == "string")
            {
                graph.setCellStyles(mxConstants.STYLE_GRADIENTCOLOR, '#' + color);
            }
        }
    });

    var fontColorMenu = new Ext.menu.ColorMenu(
    {
        items: [
            {
                text: '自动',
                handler: function()
                {
                    graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, mxConstants.NONE);
                }
            },
            '-'
        ],
        handler : function(cm, color)
        {
            if (typeof(color) == "string")
            {
                graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, '#' + color);
            }
        }
    });

    var lineColorMenu = new Ext.menu.ColorMenu(
    {
        items: [
            {
                text: '自动',
                handler: function()
                {
                    graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, mxConstants.NONE);
                }
            },
            '-'
        ],
        handler : function(cm, color)
        {
            if (typeof(color) == "string")
            {
                graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, '#' + color);
            }
        }
    });

    var labelBackgroundMenu = new Ext.menu.ColorMenu(
    {
        items: [
            {
                text: '自动',
                handler: function()
                {
                    graph.setCellStyles(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, mxConstants.NONE);
                }
            },
            '-'
        ],
        handler : function(cm, color)
        {
            if (typeof(color) == "string")
            {
                graph.setCellStyles(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, '#' + color);
            }
        }
    });

    var labelBorderMenu = new Ext.menu.ColorMenu(
    {
        items: [
            {
                text: '自动',
                handler: function()
                {
                    graph.setCellStyles(mxConstants.STYLE_LABEL_BORDERCOLOR, mxConstants.NONE);
                }
            },
            '-'
        ],
        handler : function(cm, color)
        {
            if (typeof(color) == "string")
            {
                graph.setCellStyles(mxConstants.STYLE_LABEL_BORDERCOLOR, '#' + color);
            }
        }
    });

    // Defines the font family menu
    var fonts = new Ext.data.SimpleStore(
    {
        fields: ['label', 'font'],
        data : [['Helvetica', 'Helvetica'], ['Verdana', 'Verdana'],
            ['Times New Roman', 'Times New Roman'], ['Garamond', 'Garamond'],
            ['Courier New', 'Courier New']]
    });

    var fontCombo = new Ext.form.ComboBox(
    {
        store: fonts,
        displayField:'label',
        mode: 'local',
        width:120,
        triggerAction: 'all',
        emptyText:'选择字体',
        selectOnFocus:true,
        onSelect: function(entry)
        {
            if (entry != null)
            {
                graph.setCellStyles(mxConstants.STYLE_FONTFAMILY, entry.data.font);
                this.collapse();
            }
        }
    });

    // Handles typing a font name and pressing enter
    fontCombo.on('specialkey', function(field, evt)
    {
        if (evt.keyCode == 10 ||
            evt.keyCode == 13)
        {
            var family = field.getValue();

            if (family != null &&
                family.length > 0)
            {
                graph.setCellStyles(mxConstants.STYLE_FONTFAMILY, family);
            }
        }
    });

    // Defines the font size menu
    var sizes = new Ext.data.SimpleStore({
        fields: ['label', 'size'],
        data : [['6pt', 6], ['8pt', 8], ['9pt', 9], ['10pt', 10], ['12pt', 12],
            ['14pt', 14], ['18pt', 18], ['24pt', 24], ['30pt', 30], ['36pt', 36],
            ['48pt', 48],['60pt', 60]]
    });

    var sizeCombo = new Ext.form.ComboBox(
    {
        store: sizes,
        displayField:'label',
        mode: 'local',
        width:50,
        triggerAction: 'all',
        emptyText:'12pt',
        selectOnFocus:true,
        onSelect: function(entry)
        {
            if (entry != null)
            {
                graph.setCellStyles(mxConstants.STYLE_FONTSIZE, entry.data.size);
                this.collapse();
            }
        }
    });

    // Handles typing a font size and pressing enter
    sizeCombo.on('specialkey', function(field, evt)
    {
        if (evt.keyCode == 10 ||
            evt.keyCode == 13)
        {
            var size = parseInt(field.getValue());

            if (!isNaN(size) &&
                size > 0)
            {
                graph.setCellStyles(mxConstants.STYLE_FONTSIZE, size);
            }
        }
    });

    // turn on validation errors beside the field globally    
    Ext.form.Field.prototype.msgTarget = 'side';
    var newPrjctForm = new Ext.FormPanel({
        id:'newPrjctForm',
        labelWidth:90,
        url:'/project/NewProject?saveProject=',
        defaults:{width: 230},
        defaultType:'textfield',
        bodyStyle:'padding:5px 5px 0px 0px',
        items:[{
            fieldLabel:'工程名称',
            name:'project.name',
            allowBlank:false,
            maxlength:255
        },{
            fieldLabel:'工序编号前缀',
            name:'project.preSymbol',
            allowBlank:false,
            minLength:1,
            maxLength:4,
            regex:/^[A-Z]+$/,
            regexText:'工序编号前缀包含大写字母'
        },{
            xtype:'numberfield',
            fieldLabel:'计划工期',
            name:'project.planPeriod',
            allowBlank:false,
            minValue:0,
            regex:/^[0-9]*[1-9][0-9]*$/,
            regexText:'工期只能为正整数'

        },{
            xtype:'numberfield',
            fieldLabel:'预算资金',
            name:'project.planCost',
            allowBlank:false,
            minValue:0.0
        },{
            xtype:'datefield',
            fieldLabel:'计划开工时间',
            format:'Y-m-d',
            name:'project.planStartDate',
            allowBlank:false
        },{
            fieldLabel:'施工单位',
            name:'project.contractor',
            allowBlank:false,
            maxLength:255
        }],
        buttons:[{
            text:'保存',
            handler:function() {
                var sb = Ext.getCmp('newPrjctForm-statusbar');
                if (newPrjctForm.getForm().isValid()) {
                    sb.showBusy('正在保存数据...');
                    newPrjctForm.getEl().mask();
                    newPrjctForm.getForm().submit({
                        clientValidation: true,
                        url:'/project/NewProject?saveProject=',
                        success:function(form, action) {
                            sb.setStatus({
                                text:action.result.msg,
                                clear:true
                            });
                            newPrjctForm.getEl().unmask();
                            newPrjctForm.getForm().reset();
                        },
                        failure:function(form, action) {
                            sb.setStatus({
                                text:action.result.msg,
                                clear:true
                            });
                            newPrjctForm.getEl().unmask();
                            newPrjctForm.getForm().reset();
                        }
                    });
                } else {
                    sb.setStatus('您输入的信息不正确！');
                }
            }
        },{
            text:'取消',
            handler:function() {
                newPrjctWin.hide();
            }
        }]
    });

    var newPrjctWin;

    var newPrjctHandler = function() {
        if (!newPrjctWin) {
            newPrjctWin = new Ext.Window({
                title:'新建工程项目',
                modal:true,
                layout:'fit',
                width:360,
                height:264,
                closeAction :'hide',
                plain:true,
                items:newPrjctForm,
                bbar: new Ext.StatusBar({
                    id: 'newPrjctForm-statusbar',
                    defaultText: '准备就绪'
                })
            });
        }
        //显示窗口
        newPrjctWin.show(this);
    }

    //添加工序表单
    //var newPrcsForm = ;

    var newPrcsWin;

    var newPrcsHandler = function() {
        var sn = Ext.getCmp('project-tree').getSelectionModel().getSelectedNode();
        if (null != sn) {
            Ext.Ajax.request({
                url:'/process/ReadProcess?readProcessByProject=&project.id=' + sn.id,
                success:function(response) {
                    newPrcsWin = new Ext.Window({
                        title:'新建工序',
                        modal:true,
                        layout:'fit',
                        width:660,
                        height:464,
                        closeAction :'close',
                        plain:true,
                        items:new Ext.FormPanel({
                            id:'newPrcsForm',
                            labelWidth:90,
                            defaults:{width: 230},
                            bodyStyle:'padding:5px 0px 0px 5px',
                            defaultType:'textfield',
                            items:[{
                                fieldLabel:'工序名称',
                                name:'process.name',
                                allowBlank:false,
                                maxlength:255
                            },{
                                xtype:'numberfield',
                                name:'process.planPeriod',
                                fieldLabel:'计划工期',
                                allowBlank:false,
                                minValue:0,
                                regex:/^[0-9]*[1-9][0-9]*$/,
                                regexText:'工期只能为正整数'
                            },{
                                xtype:'datefield',
                                fieldLabel:'计划开工时间',
                                format:'Y-m-d',
                                name:'process.planStartDate',
                                allowBlank:false
                            },{
                                xtype:'numberfield',
                                fieldLabel:'预算资金',
                                name:'process.planCost',
                                allowBlank:false,
                                minValue:0.0
                            },{
                                xtype:'numberfield',
                                fieldLabel:'年产出',
                                name:'process.output',
                                allowBlank:false,
                                minValue:0.0
                            },{
                                id: 'prcsselector',
                                xtype:"itemselector",
                                name:"preProcessStr",
                                fieldLabel:"紧前工序",
                                dataFields:["code", "desc"],
                                toData:[],
                                width:540,
                                height:260,
                                msWidth:250,
                                msHeight:200,
                                valueField:"code",
                                displayField:"desc",
                                imagePath:"../extjs/images/",
                                toLegend:'已选紧前工序',
                                fromLegend:'可用工序',
                                fromData:Ext.decode(response.responseText),
                                toTBar:[{
                                    text:'清除全部',
                                    handler:function() {
                                        var i = this.getForm().findField("prcsselector");
                                        i.reset.call(i);
                                    }
                                }]
                            }],
                            buttons: [{
                                text: '保存',
                                handler: function() {
                                    var sb = Ext.getCmp('newPrcsForm-statusbar');
                                    var prcsF = Ext.getCmp('newPrcsForm').getForm();
                                    if (prcsF.isValid()) {
                                        sb.showBusy('正在保存数据...');
                                        prcsF.getEl().mask();
                                        prcsF.submit({
                                            clientValidation: true,
                                            url:'/process/NewProcess?saveProcess=&project.id=' + sn.id,
                                            success:function(form, action) {
                                                sb.setStatus({
                                                    text:action.result.msg,
                                                    clear:true
                                                });
                                                prcsF.getEl().unmask();
                                                prcsF.reset();
                                            },
                                            failure:function(form, action) {
                                                sb.setStatus({
                                                    text:action.result.msg,
                                                    clear:true
                                                });
                                                prcsF.getEl().unmask();
                                                prcsF.reset();
                                            }
                                        });
                                    } else {
                                        sb.setStatus('您输入的信息不正确！');
                                    }
                                }
                            },{
                                text:'取消',
                                handler:function() {
                                    newPrcsWin.close();
                                }
                            }]

                        }),
                        bbar: new Ext.StatusBar({
                            id: 'newPrcsForm-statusbar',
                            defaultText: '准备就绪'
                        })
                    });
                    //显示窗口
                    newPrcsWin.show(this);
                },
                failure:function(response) {
                    errorMsg('读取数据失败', '读取该项目工序信息失败！');
                }
            });
        } else {
            errorMsg('未选中工程项目', '请先选中您要添加工序的工程项目');
        }
    }

    var importWin;
    var importHandler = function() {
        var sn = Ext.getCmp('project-tree').getSelectionModel().getSelectedNode();
        if (null != sn) {
            importWin = new Ext.Window({
                title:'导入工程项目',
                width:500,
                layout:'fit',
                height:160,
                modal:true,
                plain:true,
                items:new Ext.FormPanel({
                    id:'importprcs-form',
                    fileUpload: true,
                    labelWidth: 60,
                    bodyStyle: 'padding: 10px 10px 0 10px;',
                    items: [{
                        xtype:'fileuploadfield',
                        fieldLabel:'Excel文件',
                        width:380,
                        name:'excel',
                        buttonCfg: {
                            text: '',
                            iconCls: 'upload-icon'
                        }
                    }],
                    buttons: [{
                        text: '导入',
                        handler: function() {
                            var sb = Ext.getCmp('importForm-statusbar');
                            var imtF = Ext.getCmp('importprcs-form').getForm();
                            if (imtF.isValid()) {
                                sb.showBusy('正在导入数据...');
                                imtF.getEl().mask();
                                imtF.submit({
                                    clientValidation: true,
                                    url:'/process/NewProcess?importProcess=&project.id=' + sn.id,
                                    success:function(form, action) {
                                        sb.setStatus({
                                            text:action.result.msg,
                                            clear:true
                                        });
                                        imtF.getEl().unmask();
                                        imtF.reset();
                                    },
                                    failure:function(form, action) {
                                        sb.setStatus({
                                            text:action.result.msg,
                                            clear:true
                                        });
                                        imtF.getEl().unmask();
                                        imtF.reset();
                                    }
                                });
                            } else {
                                sb.setStatus('请选择Excel文件！');
                            }
                        }
                    },{
                        text: '取消',
                        handler: function() {
                            importWin.close();
                        }
                    }]
                }),
                bbar:new Ext.StatusBar({
                    id:'importForm-statusbar',
                    defaultText:'准备就绪'
                })
            });
            importWin.show();
        } else {
            errorMsg('未选择工程项目', '在导入数据之前，请选择工程项目！');
        }
    }

    //画布面板
    this.graphPanel = new Ext.Panel(
    {
        region: 'center',
        border:false,
        tbar:[
            {
                id: 'newProject',
                text:'',
                iconCls:'new-project-icon',
                tooltip:'新建项目',
                handler:newPrjctHandler,
                scope:this
            },
            {
                id:'newProcess',
                text:'',
                iconCls:'new-process-icon',
                tooltip:'新建工序',
                handler:newPrcsHandler,
                scope:this
            },
            {
                id:'importProcess',
                text:'',
                iconCls:'import-icon',
                tooltip:'导入工序',
                handler:importHandler,
                scope:this
            },
            {
                text:'',
                iconCls:'save-icon',
                tooltip:'保存PERT图',
                handler:function()
                {
                    Ext.MessageBox.show({
                        msg: '正在保存数据，请稍候...',
                        progressText: '正在保存...',
                        width:300,
                        wait:true,
                        waitConfig: {interval:200},
                        icon:'saving-icon',
                        animEl: 'mb7'
                    });
                    var enc = new mxCodec(mxUtils.createXmlDocument());
                    var node = enc.encode(graph.getModel());
                    var xmlContent = encodeURIComponent(mxUtils.getPrettyXml(node));
                    mxUtils.post('/diagram/SaveDiagramXML', 'xml=' + xmlContent, function(req)
                    {
                        Ext.MessageBox.hide();
                        Ext.MessageBox.show({
                            title: '保存完成',
                            msg: '数据保存成功!',
                            buttons: Ext.MessageBox.OK,
                            animEl: 'mb9',
                            icon: 'info-icon'
                        });
                    }, function(req)
                    {
                        Ext.MessageBox.show({
                            title: '保存失败',
                            msg: '保存数据失败，请联系管理员！',
                            buttons: Ext.MessageBox.OK,
                            animEl: 'mb9',
                            icon: 'error-icon'
                        });
                    });
                },
                scope:this
            },
            '-',
            {
                id: 'print',
                text:'',
                iconCls: 'print-icon',
                tooltip: '打印PERT图',
                handler: function()
                {
                    mxUtils.print(graph);
                },
                scope:this
            },
            {
                id: 'print',
                text:'',
                iconCls: 'preview-icon',
                tooltip: '打印预览',
                handler: function()
                {
                    mxUtils.show(graph);
                },
                scope:this
            },
            '-',
            {
                id: 'undo',
                text:'',
                iconCls: 'undo-icon',
                tooltip: '撤销',
                handler: function()
                {
                    history.undo();
                },
                scope:this
            },{
                id: 'redo',
                text:'',
                iconCls: 'redo-icon',
                tooltip: '重做',
                handler: function()
                {
                    history.redo();
                },
                scope:this
            },
            '-',
            fontCombo,
            ' ',
            sizeCombo,
            '-',
            {
                id: 'bold',
                text: '',
                iconCls:'bold-icon',
                tooltip: '加粗',
                handler: function()
                {
                    graph.toggleCellStyleFlags(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
                },
                scope:this
            },
            {
                id: 'italic',
                text: '',
                tooltip: '斜体',
                iconCls:'italic-icon',
                handler: function()
                {
                    graph.toggleCellStyleFlags(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_ITALIC);
                },
                scope:this
            },
            {
                id: 'underline',
                text: '',
                tooltip: '下划线',
                iconCls:'underline-icon',
                handler: function()
                {
                    graph.toggleCellStyleFlags(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_UNDERLINE);
                },
                scope:this
            },
            '-',
            {
                id: 'align',
                text:'',
                iconCls: 'left-icon',
                tooltip: '文本对齐方式',
                handler: function() {
                },
                menu:
                {
                    id:'reading-menu',
                    cls:'reading-menu',
                    items: [
                        {
                            text:'左对齐',
                            checked:false,
                            group:'rp-group',
                            scope:this,
                            iconCls:'left-icon',
                            handler: function()
                            {
                                graph.setCellStyles(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_LEFT);
                            }
                        },
                        {
                            text:'水平居中',
                            checked:true,
                            group:'rp-group',
                            scope:this,
                            iconCls:'center-icon',
                            handler: function()
                            {
                                graph.setCellStyles(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
                            }
                        },
                        {
                            text:'右对齐',
                            checked:false,
                            group:'rp-group',
                            scope:this,
                            iconCls:'right-icon',
                            handler: function()
                            {
                                graph.setCellStyles(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_RIGHT);
                            }
                        },
                        '-',
                        {
                            text:'顶部对齐',
                            checked:false,
                            group:'vrp-group',
                            scope:this,
                            iconCls:'top-icon',
                            handler: function()
                            {
                                graph.setCellStyles(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_TOP);
                            }
                        },
                        {
                            text:'垂直居中',
                            checked:true,
                            group:'vrp-group',
                            scope:this,
                            iconCls:'middle-icon',
                            handler: function()
                            {
                                graph.setCellStyles(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
                            }
                        },
                        {
                            text:'底部对齐',
                            checked:false,
                            group:'vrp-group',
                            scope:this,
                            iconCls:'bottom-icon',
                            handler: function()
                            {
                                graph.setCellStyles(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
                            }
                        }]
                }
            },
            '-',
            {
                id: 'fontcolor',
                text: '',
                tooltip: '字体颜色',
                iconCls:'fontcolor-icon',
                menu: fontColorMenu // <-- submenu by reference
            },
            {
                id: 'linecolor',
                text: '',
                tooltip: '线条颜色',
                iconCls:'linecolor-icon',
                menu: lineColorMenu // <-- submenu by reference
            },
            {
                id: 'fillcolor',
                text: '',
                tooltip: '填充色',
                iconCls:'fillcolor-icon',
                menu: fillColorMenu // <-- submenu by reference
            },
            '-',
            {
                text:'缩放',
                iconCls: 'zoom-icon',
                handler: function(menu) {
                },
                menu:
                {
                    items: [
                        {
                            text:'自定义',
                            scope:this,
                            handler: function(item)
                            {
                                var value = mxUtils.prompt('Enter Source Spacing (Pixels)', parseInt(graph.getView().getScale() * 100));

                                if (value != null)
                                {
                                    graph.getView().setScale(parseInt(value) / 100);
                                }
                            }
                        },
                        '-',
                        {
                            text:'400%',
                            scope:this,
                            handler: function(item)
                            {
                                graph.getView().setScale(4);
                            }
                        },
                        {
                            text:'200%',
                            scope:this,
                            handler: function(item)
                            {
                                graph.getView().setScale(2);
                            }
                        },
                        {
                            text:'150%',
                            scope:this,
                            handler: function(item)
                            {
                                graph.getView().setScale(1.5);
                            }
                        },
                        {
                            text:'100%',
                            scope:this,
                            handler: function(item)
                            {
                                graph.getView().setScale(1);
                            }
                        },
                        {
                            text:'75%',
                            scope:this,
                            handler: function(item)
                            {
                                graph.getView().setScale(0.75);
                            }
                        },
                        {
                            text:'50%',
                            scope:this,
                            handler: function(item)
                            {
                                graph.getView().setScale(0.5);
                            }
                        },
                        '-',
                        {
                            text:'放大',
                            iconCls: 'zoomin-icon',
                            scope:this,
                            handler: function(item)
                            {
                                graph.zoomIn();
                            }
                        },
                        {
                            text:'缩小',
                            iconCls: 'zoomout-icon',
                            scope:this,
                            handler: function(item)
                            {
                                graph.zoomOut();
                            }
                        },
                        '-',
                        {
                            text:'实际大小',
                            iconCls: 'zoomactual-icon',
                            scope:this,
                            handler: function(item)
                            {
                                graph.zoomActual();
                            }
                        },
                        {
                            text:'适合屏幕',
                            iconCls: 'fit-icon',
                            scope:this,
                            handler: function(item)
                            {
                                graph.fit();
                            }
                        }]
                }
            },
            '-',
            {
                text:'选项',
                iconCls: 'preferences-icon',
                handler: function(menu) {
                },
                menu:
                {
                    items: [
                        {
                            text:'网格',
                            handler: function(menu) {
                            },
                            menu:
                            {
                                items: [
                                    {
                                        checked: true,
                                        text:'显示网格',
                                        scope:this,
                                        handler: function(item, checked)
                                        {
                                            Ext.get('ext-gen141').toggleClass('x-grid-bg');
                                        }
                                    },
                                    {
                                        checked: true,
                                        text:'对齐网格',
                                        scope:this,
                                        checkHandler: function(item, checked)
                                        {
                                            graph.setGridEnabled(checked);
                                        }
                                    }
                                ]
                            }
                        },
                        '-',
                        {
                            text:'显示 XML',
                            scope:this,
                            handler: function(item)
                            {
                                var enc = new mxCodec(mxUtils.createXmlDocument());
                                var node = enc.encode(graph.getModel());

                                mxUtils.popup(mxUtils.getPrettyXml(node));
                            }
                        },
                        {
                            text:'解析 XML',
                            scope:this,
                            handler: function(item)
                            {
                                //var xml = mxUtils.prompt('请输入 XML:', '');
                                Ext.Msg.prompt('XML', '请输入 XML:', function(btn, text) {
                                    if (btn == 'ok') {
                                        var xml = text;
                                        if (xml != null && xml.length > 0)
                                        {
                                            var doc = mxUtils.parseXml(xml);
                                            var dec = new mxCodec(doc);
                                            dec.decode(doc.documentElement, graph.getModel());
                                        }
                                    }
                                });
                            }
                        },
                        '-',
                        {
                            text:'控制台',
                            scope:this,
                            handler: function(item)
                            {
                                mxLog.setVisible(!mxLog.isVisible());
                            }
                        }]
                }
            }],
        onContextMenu : function(node, e)
        {
            var selected = !graph.isSelectionEmpty();

            this.menu = new Ext.menu.Menu(
            {
                id:'feeds-ctx',
                items: [{
                    text:'撤销',
                    iconCls:'undo-icon',
                    disabled: !history.canUndo(),
                    scope: this,
                    handler:function()
                    {
                        history.undo();
                    }
                },'-',
                    {
                        text:'格式',
                        disabled: !selected,
                        handler: function() {
                        },
                        menu:
                        {
                            items: [
                                {
                                    text:'背景颜色',
                                    disabled: !selected,
                                    handler: function() {
                                    },
                                    menu:
                                    {
                                        items: [
                                            {
                                                text: '填充色',
                                                iconCls:'fillcolor-icon',
                                                menu: fillColorMenu
                                            },
                                            {
                                                text: '渐变',
                                                menu: gradientColorMenu
                                            },
                                            {
                                                text:'投影',
                                                scope:this,
                                                handler: function()
                                                {
                                                    graph.toggleCellStyles(mxConstants.STYLE_SHADOW);
                                                }
                                            },
                                            '-',
                                            {
                                                text:'透明度',
                                                scope:this,
                                                handler: function()
                                                {
                                                    var value = mxUtils.prompt('请输入透明度 (0-100%)', '100');

                                                    if (value != null)
                                                    {
                                                        graph.setCellStyles(mxConstants.STYLE_OPACITY, value);
                                                    }
                                                }
                                            }]
                                    }
                                },
                                '-',
                                {
                                    text:'线条',
                                    disabled: !selected,
                                    handler: function() {
                                    },
                                    menu:
                                    {
                                        items: [
                                            {
                                                text: '线条颜色',
                                                iconCls:'linecolor-icon',
                                                menu: lineColorMenu
                                            },{
                                                text: '线条宽度',
                                                handler: function()
                                                {
                                                    var value = '0';
                                                    var state = graph.getView().getState(graph.getSelectionCell());

                                                    if (state != null)
                                                    {
                                                        value = state.style[mxConstants.STYLE_STROKEWIDTH] || 1;
                                                    }

                                                    value = mxUtils.prompt('请输入线条宽度 (单位：像素)', value);

                                                    if (value != null)
                                                    {
                                                        graph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, value);
                                                    }
                                                }
                                            }]
                                    }
                                }]
                        }
                    },
                    '-',
                    {
                        text:'编辑',
                        scope:this,
                        handler: function()
                        {
                            graph.startEditing();
                        }
                    },
                    '-',
                    {
                        text:'全选',
                        scope: this,
                        handler:function()
                        {
                            graph.selectAll();
                        }
                    }]
            });

            this.menu.on('hide', this.onContextHide, this);
            this.menu.showAt([e.clientX, e.clientY]);
        },

        onContextHide : function()
        {
            if (this.ctxNode)
            {
                this.ctxNode.ui.removeClass('x-node-ctx');
                this.ctxNode = null;
            }
        }
    });

    MainPanel.superclass.constructor.call(this,
    {
        region:'center',
        layout: 'fit',
        items: this.graphPanel
    });

    // Redirects the context menu to ExtJs menus
    var self = this; // closure
    graph.panningHandler.popup = function(x, y, cell, evt)
    {
        self.graphPanel.onContextMenu(null, evt);
    };

    graph.panningHandler.hideMenu = function()
    {
        if (self.graphPanel.menuPanel != null)
        {
            self.graphPanel.menuPanel.hide();
        }
    };

    // Fits the SVG container into the panel body
    this.graphPanel.on('resize', function()
    {
        graph.sizeDidChange();
    });
};

Ext.extend(MainPanel, Ext.Panel,
{
    movePreview : function(m, pressed)
    {

    }
});
