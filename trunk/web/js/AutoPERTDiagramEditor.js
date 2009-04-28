/*
 * $Id: AutoPERTDiagramEditor.js,v 1.10 2009/04/11 11:58:24
 * Copyright (c) 2008, ZisCloud Wang
 */
GraphEditor = {};

Ext.onReady(function() {
    Ext.QuickTips.init();
    // Disables browser context menu
    // 禁用浏览器的右键菜单
    mxEvent.disableContextMenu(document.body);

    // Creates the graph and loads the default stylesheet
    // 创建一个graph对象，并加载默认的样式
    var graph = new mxGraph();

    // Creates the command history (undo/redo)
    // 创建历史管理器
    var history = new mxUndoManager();

    // Loads the default stylesheet into the graph
    // 将默认样式将在到graph东西中
    var node = mxUtils.load('resources/default-style.xml').getDocumentElement();
    var dec = new mxCodec(node.ownerDocument);
    dec.decode(node, graph.getStylesheet());

    // Sets the style to be used when an elbow edge is double clicked
    // 设置elbow edge被双击时使用的样式
    graph.alternateEdgeStyle = 'vertical';

    //定义工程项目的树结构对象
    var projectTree = new Ext.tree.TreePanel({
        id:'project-tree',
        region:'center',
        layout:'fit',
        useArrows:true,
        autoScroll:true,
        animate:true,
        enableDD:true,
        containerScroll: true,
        //rootVisible:false,
        dataUrl: '/project/readProject?readAllProject=',
        root: {
            nodeType: 'async',
            text: '工程项目',
            iconCls: 'new-project-icon',
            draggable:false,
            id:'root'
        }
    });

    //缩略图面板
    var outlinePanel = new Ext.Panel({
        title:'缩略图',
        region:'south',
        layout:'fit',
        height:200,
        split:true
    });

    //显示PERT及工具栏的面板
    var mainPanel = new MainPanel(graph, history);

    //工程项目的属性表
    var projectPropertyGrid = new Ext.grid.PropertyGrid({
        id:'project-PropertyGrid',
        title: '工程项目属性表',
        closable: false,
        height:400,
        autoScroll:true,
        autoSort:false,
        clicksToEdit:3,
        tbar:[{
            text:'保存修改',
            iconCls:'save-icon',
            handler:function() {
                var sn = Ext.getCmp('project-tree').getSelectionModel().getSelectedNode();
                if (null != sn) {
                    var g = Ext.getCmp('project-PropertyGrid');
                    var store = g.getStore();
                    var s = Ext.getCmp('info-statusbar');
                    s.showBusy('正在保存数据...');
                    g.getEl().mask();
                    Ext.Ajax.request({
                        method:'POST',
                        url:'/project/UpdateProject?save=',
                        params:{
                            'project.id':store.getAt(0).data.value,
                            'project.name':store.getAt(1).data.value,
                            'project.planPeriod':store.getAt(3).data.value,
                            'project.planCost':store.getAt(5).data.value,
                            'project.planStartDate':store.getAt(7).data.value,
                            'project.contractor':store.getAt(11).data.value,
                            'project.actualStartDate':convertPropertyValue(store.getAt(8).data.value),
                            'project.actualCost':convertPropertyValue(store.getAt(6).data.value),
                            'project.actualPeriod':convertPropertyValue(store.getAt(4).data.value)
                        },
                        success:function(response) {
                            s.setStatus({
                                text:response.responseText,
                                clear:true
                            });
                            g.getEl().unmask();
                        },
                        failure:function(response) {
                            s.setStatus({
                                text:response.responseText,
                                clear:true
                            });
                            g.getEl().unmask();
                        }
                    });
                }
            }
        }]
    });
    //设置属性表格默认的日期格式
    projectPropertyGrid.getColumnModel().dateFormat = 'Y-m-d';
    //定义属性表中使用到的editor
    projectPropertyGrid.customEditors = {
        '项目名称': new Ext.grid.GridEditor(new Ext.form.TextField({
            allowBlank:false,
            maxlength:255
        })),
        '计划工期': new Ext.grid.GridEditor(new Ext.form.NumberField({
            allowBlank:false,
            minValue:0,
            regex:/^[0-9]*[1-9][0-9]*$/,
            regexText:'工期只能为正整数'
        })),
        '实际工期': new Ext.grid.GridEditor(new Ext.form.NumberField({
            allowBlank:false,
            minValue:0,
            regex:/^[0-9]*[1-9][0-9]*$/,
            regexText:'工期只能为正整数'
        })),
        '预算资金': new Ext.grid.GridEditor(new Ext.form.NumberField({
            allowBlank:false,
            minValue:0.0
        })),
        '实际资金': new Ext.grid.GridEditor(new Ext.form.NumberField({
            allowBlank:false,
            minValue:0.0
        })),
        '计划开工时间': new Ext.grid.GridEditor(new Ext.form.DateField({
            format:'Y-m-d',
            allowBlank:false
        })),
        '实际开工时间': new Ext.grid.GridEditor(new Ext.form.DateField({
            format:'Y-m-d',
            allowBlank:false
        })),
        '施工单位': new Ext.grid.GridEditor(new Ext.form.TextField({
            allowBlank:false,
            maxLength:255
        }))
    };

    //工序的属性表
    var processPropertyGrid = new Ext.grid.PropertyGrid({
        id:'process-PropertyGrid',
        title:'工序属性表',
        closable:false,
        height:400,
        autoScroll:true,
        autoSort:false,
        clicksToEdit:3,
        tbar:[{
            text:'保存修改',
            iconCls:'save-icon',
            handler:function() {
                var sn = Ext.getCmp('project-tree').getSelectionModel().getSelectedNode();
                if (null != sn) {
                    var g = Ext.getCmp('process-PropertyGrid');
                    var store = g.getStore();
                    var s = Ext.getCmp('info-statusbar');
                    s.showBusy('正在保存数据...');
                    g.getEl().mask();
                    Ext.Ajax.request({
                        method:'POST',
                        url:'/process/UpdateProcess?save=',
                        params:{
                            'project':sn.id,
                            'process.id':store.getAt(0).data.value,
                            'process.name':store.getAt(2).data.value,
                            'process.planPeriod':store.getAt(3).data.value,
                            'process.planCost':store.getAt(5).data.value,
                            'process.planStartDate':store.getAt(8).data.value,
                            'process.output':store.getAt(7).data.value,
                            'process.actualStartDate':convertPropertyValue(store.getAt(9).data.value),
                            'process.actualCost':convertPropertyValue(store.getAt(6).data.value),
                            'process.actualPeriod':convertPropertyValue(store.getAt(4).data.value)
                        },
                        success:function(response) {
                            readXML(mainPanel, sn, graph);
                            s.setStatus({
                                text:response.responseText,
                                clear:true
                            });
                            g.getEl().unmask();
                        },
                        failure:function(response) {
                            s.setStatus({
                                text:response.responseText,
                                clear:true
                            });
                            g.getEl().unmask();
                        }
                    });
                }
            }
        }]
    });
    //设置属性表格默认的日期格式
    processPropertyGrid.getColumnModel().dateFormat = 'Y-m-d';
    //定义属性表中使用到的editor
    processPropertyGrid.customEditors = {
        '工序名称': new Ext.grid.GridEditor(new Ext.form.TextField({
            allowBlank:false,
            maxlength:255
        })),
        '计划工期': new Ext.grid.GridEditor(new Ext.form.NumberField({
            allowBlank:false,
            minValue:0,
            regex:/^[0-9]*[1-9][0-9]*$/,
            regexText:'工期只能为正整数'
        })),
        '实际工期': new Ext.grid.GridEditor(new Ext.form.NumberField({
            allowBlank:false,
            minValue:0,
            regex:/^[0-9]*[1-9][0-9]*$/,
            regexText:'工期只能为正整数'
        })),
        '预算资金': new Ext.grid.GridEditor(new Ext.form.NumberField({
            allowBlank:false,
            minValue:0.0
        })),
        '年产出': new Ext.grid.GridEditor(new Ext.form.NumberField({
            allowBlank:false,
            minValue:0.0
        })),
        '实际资金': new Ext.grid.GridEditor(new Ext.form.NumberField({
            allowBlank:false,
            minValue:0.0
        })),
        '计划开工时间': new Ext.grid.GridEditor(new Ext.form.DateField({
            format:'Y-m-d',
            allowBlank:false
        })),
        '实际开工时间': new Ext.grid.GridEditor(new Ext.form.DateField({
            format:'Y-m-d',
            allowBlank:false
        }))
    };

    //设置propertyGrid的field为只读
    projectPropertyGrid.on("beforeedit", function(event) {
        if (-1 == EditablePropertyIndex.project.indexOf(event.row)) {
            event.cancel = true;
            return false;
        }
    });

    //禁用propertyGrid对property的排序
    projectPropertyGrid.colModel.config[0].sortable = false;
    processPropertyGrid.colModel.config[0].sortable = false;

    //设置propertyGrid的field为只读
    processPropertyGrid.on("beforeedit", function(event) {
        if (-1 == EditablePropertyIndex.process.indexOf(event.row)) {
            event.cancel = true;
            return false;
        }
    });

    //显示工程及工序信息的tab面板
    var infoTabPanel = new Ext.TabPanel({
        border:false,
        activeTab:1,
        tabPosition:'bottom',
        //将工程项目属性表及工序属性表添加的tab panel中
        items:[projectPropertyGrid,processPropertyGrid],
        bbar:new Ext.StatusBar({
            id:'info-statusbar',
            defaultText:'准备就绪'
        })
    });

    //显示工程及工序信息的面板
    var infoPanel = new Ext.Panel({
        title:'属性面板',
        region:'east',
        layout:'fit',
        split:true,
        width:300,
        collapsible: true,
        items: infoTabPanel
    });

    /*
     * 绑定工序项目树中节点（工程项目）的单击事件处理器；
     * 当用户单击工程项目的节点时，则从服务器请求该项目的
     * 基本信息，通过ToolTip的方式显示。
     */
    projectTree.on('dblclick', function(node) {
        if (node.id != 'root') {
            //从服务器请求数据并工序属性表
            var source = projectPropertyGrid.getSource();
            if (null == source) {
                readProjectInfo(infoTabPanel, projectPropertyGrid, infoPanel, node);
            } else {
                var sourceArray = Ext.util.JSON.encode(source).split(':');
                if (1 < sourceArray.length) {
                    var id = sourceArray[1].split(',')[0];
                    if (id != node.id) {
                        readProjectInfo(infoTabPanel, projectPropertyGrid, infoPanel, node);
                    }
                } else {
                    readProjectInfo(infoTabPanel, projectPropertyGrid, infoPanel, node);
                }
            }
            readXML(mainPanel, node, graph);
        }
    });

    var viewport = new Ext.Viewport(
    {
        layout:'border',
        items:[{
            xtype: 'panel',
            margins:'5 5 5 5',
            region: 'center',
            layout: 'border',
            border: false,
            items:[
                new Ext.Panel(
                {
                    title: '侧边栏',
                    region:'west',
                    layout:'border',
                    split:true,
                    width: 200,
                    collapsible: true,
                    border: false,
                    items:[projectTree,outlinePanel]
                }),
                mainPanel,
                infoPanel
            ]
        }]
    });

    //展开工程项目的树
    projectTree.getRootNode().expand();

    // Enables scrollbars for the graph container to make it more
    // native looking, this will affect the panning to use the
    // scrollbars rather than moving the container contents inline
    // 启用graph容器的滚动条
    mainPanel.graphPanel.body.dom.style.overflow = 'auto';

    // FIXME: For some reason the auto value is reset to hidden in
    // Safari on the Mac, this is _probably_ caused by ExtJs
    if (mxClient.IS_MAC &&
        mxClient.IS_SF)
    {
        graph.addListener(mxEvent.SIZE, function(graph)
        {
            graph.container.style.overflow = 'auto';
        });
    }

    // Initializes the graph as the DOM for the panel has now been created
    // 初始化graph对象
    graph.init(mainPanel.graphPanel.body.dom);
    graph.labelsVisible = true;
    graph.edgeLabelsMovable = false;
    graph.vertexLabelsMovable = false;
    graph.allowDanglingEdges = false;
    graph.cloneInvalidEdges = false;
    graph.allowLoops = false;
    graph.multigraph = true;
    graph.disconnectOnMove = false;
    graph.connectable = false;
    graph.connectableEdges = false;
    graph.dropEnabled = false;
    graph.panning = true;
    graph.tooltips = true;
    graph.connectionHandler.createTarget = false;
    graph.gridSize = 15;
    graph.cellsEditable = false;

    // Creates rubberband selection
    // 创建 rubberband选取
    var rubberband = new mxRubberband(graph);

    // Installs the command history after the initial graph
    // has been created
    // graph被初始化后，将历史管理器注册到graph中
    var listener = function(sender, evt)
    {
        history.undoableEditHappened(evt.getArgAt(0)/*edit*/);
    };

    graph.getModel().addListener(mxEvent.UNDO, listener);
    graph.getView().addListener(mxEvent.UNDO, listener);

    // Toolbar object for updating buttons in listeners
    var toolbarItems = mainPanel.graphPanel.getTopToolbar().items;

    // Updates the states of all buttons that require a selection
    var selectionListener = function()
    {
        var selected = !graph.isSelectionEmpty();

        toolbarItems.get('italic').setDisabled(!selected);
        toolbarItems.get('bold').setDisabled(!selected);
        toolbarItems.get('underline').setDisabled(!selected);
        toolbarItems.get('fillcolor').setDisabled(!selected);
        toolbarItems.get('fontcolor').setDisabled(!selected);
        toolbarItems.get('linecolor').setDisabled(!selected);
        toolbarItems.get('align').setDisabled(!selected);
        //如果有cell被选中，那么执行下面的代码
        if (true == selected) {
            var cell = graph.getSelectionCell();
            if (cell.isEdge()) {
                //从服务器请求数据并工序属性表
                var source = processPropertyGrid.getSource();
                if (null == source) {
                    readProcessInfo(infoTabPanel, processPropertyGrid, infoPanel, cell.getId());
                } else {
                    var sourceArray = Ext.util.JSON.encode(source).split(':');
                    if (1 < sourceArray.length) {
                        var id = sourceArray[1].split(',')[0];
                        if (('p' + id) != cell.getId()) {
                            readProcessInfo(infoTabPanel, processPropertyGrid, infoPanel, cell.getId());
                        }
                    } else {
                        readProcessInfo(infoTabPanel, processPropertyGrid, infoPanel, cell.getId());
                    }
                }
            }
        }

    };

    graph.getSelectionModel().addListener(mxEvent.CHANGE, selectionListener);

    // Updates the states of the undo/redo buttons in the toolbar
    var historyListener = function()
    {
        toolbarItems.get('undo').setDisabled(!history.canUndo());
        toolbarItems.get('redo').setDisabled(!history.canRedo());
    };

    history.addListener(mxEvent.ADD, historyListener);
    history.addListener(mxEvent.UNDO, historyListener);
    history.addListener(mxEvent.REDO, historyListener);

    // Updates the button states once
    selectionListener();
    historyListener();

    // Installs outline in outlinePanel
    var outline = new mxOutline(graph, outlinePanel.body.dom);

    // Overrides createGroupCell to set the group style for new groups to 'group'
    var previousCreateGroupCell = graph.createGroupCell;

    graph.createGroupCell = function()
    {
        var group = previousCreateGroupCell.apply(this, arguments);
        group.setStyle('group');

        return group;
    };

    // Redirects tooltips to ExtJs tooltips. First a tooltip object
    // is created that will act as the tooltip for all cells.
    var tooltip = new Ext.ToolTip(
    {
        target: graph.container,
        html: ''
    });

    // Disables the built-in event handling
    tooltip.disabled = true;

    // Installs the tooltip by overriding the hooks in mxGraph to
    // show and hide the tooltip.
    graph.tooltipHandler.show = function(tip, x, y)
    {
        if (tip != null &&
            tip.length > 0)
        {
            // Changes the DOM of the tooltip in-place if
            // it has already been rendered
            if (tooltip.body != null)
            {
                // TODO: Use mxUtils.isNode(tip) and handle as markup,
                // problem is dom contains some other markup so the
                // innerHTML is not a good place to put the markup
                // and this method can also not be applied in
                // pre-rendered state (see below)
                //tooltip.body.dom.innerHTML = tip.replace(/\n/g, '<br>');
                tooltip.body.dom.firstChild.nodeValue = tip;
            }

            // Changes the html config value if the tooltip
            // has not yet been rendered, in which case it
            // has no DOM nodes associated
            else
            {
                tooltip.html = tip;
            }

            tooltip.showAt([x, y + mxConstants.TOOLTIP_VERTICAL_OFFSET]);
        }
    };

    graph.tooltipHandler.hide = function()
    {
        tooltip.hide();
    };

    // Updates the document title if the current root changes (drilling)
    var drillHandler = function(sender)
    {
        var model = graph.getModel();
        var cell = graph.getCurrentRoot();
        var title = '';

        while (cell != null &&
               model.getParent(model.getParent(cell)) != null)
        {
            // Append each label of a valid root
            if (graph.isValidRoot(cell))
            {
                title = ' > ' +
                        graph.convertValueToString(cell) + title;
            }

            cell = graph.getModel().getParent(cell);
        }

        document.title = 'Auto PERT Diagram Editor' + title;
    };

    graph.getView().addListener(mxEvent.DOWN, drillHandler);
    graph.getView().addListener(mxEvent.UP, drillHandler);

    // Keeps the selection in sync with the history
    var undoHandler = function(sender, evt)
    {
        var changes = evt.getArgAt(0).changes;
        graph.setSelectionCells(graph.getSelectionCellsForChanges(changes));
    };

    history.addListener(mxEvent.UNDO, undoHandler);
    history.addListener(mxEvent.REDO, undoHandler);

    // Transfer initial focus to graph container for keystroke handling
    graph.container.focus();

    // Handles keystroke events
    var keyHandler = new mxKeyHandler(graph);

    // Ignores enter keystroke. Remove this line if you want the
    // enter keystroke to stop editing
    keyHandler.enter = function() {
    };

    keyHandler.bindKey(8, function()
    {
        graph.foldCells(true);
    });

    keyHandler.bindKey(13, function()
    {
        graph.foldCells(false);
    });

    keyHandler.bindKey(33, function()
    {
        graph.exitGroup();
    });

    keyHandler.bindKey(34, function()
    {
        graph.enterGroup();
    });

    keyHandler.bindKey(36, function()
    {
        graph.home();
    });

    keyHandler.bindKey(35, function()
    {
        graph.refresh();
    });

    keyHandler.bindKey(37, function()
    {
        graph.selectPreviousCell();
    });

    keyHandler.bindKey(38, function()
    {
        graph.selectParentCell();
    });

    keyHandler.bindKey(39, function()
    {
        graph.selectNextCell();
    });

    keyHandler.bindKey(40, function()
    {
        graph.selectChildCell();
    });

    keyHandler.bindKey(107, function()
    {
        graph.zoomIn();
    });

    keyHandler.bindKey(109, function()
    {
        graph.zoomOut();
    });

    keyHandler.bindKey(113, function()
    {
        graph.startEditingAtCell();
    });

    keyHandler.bindControlKey(65, function()
    {
        graph.selectAll();
    });

    keyHandler.bindControlKey(89, function()
    {
        history.redo();
    });

    keyHandler.bindControlKey(90, function()
    {
        history.undo();
    });

    keyHandler.bindControlKey(71, function()
    {
        graph.setSelectionCell(graph.groupCells(null, 20));
    });

    keyHandler.bindControlKey(85, function()
    {
        graph.setSelectionCells(graph.ungroupCells());
    });
    
    //为画布设置网格
    Ext.get('ext-gen141').toggleClass('x-grid-bg');
});

// Parses the mxGraph XML file format
function read(graph, filename) {
    var req = mxUtils.load(filename);
    var root = req.getDocumentElement();
    var dec = new mxCodec(root.ownerDocument);
    dec.decode(root, graph.getModel());
}

// 向服务器请求工程项目的信息，并更新属性面板
function readProjectInfo(infoTabPanel, propertyGrid, infoPanel, node) {
    infoTabPanel.setActiveTab('project-PropertyGrid');
    var loadMask = loadDataMask(infoPanel);
    loadMask.show();
    Ext.Ajax.request({
        url:'/project/readProject?readProjectById=&project.id=' + node.id,
        //读取项目信息成功
        success: function(response) {
            var info = response.responseText;
            if (info == AjaxReturnMark.ERROR) {
                errorMsg('加载数据失败', '无法在数据库中找到该工程项目的有关数据，请检查您的数据！');
            } else {
                propertyGrid.setSource(Ext.util.JSON.decode(info));
            }
            loadMask.hide();
        },
        //读取项目信息失败
        failure: function() {
            loadMask.hide();
            errorMsg('加载数据失败', '读取项目数据失败，请联系管理员！');
        }
    });
}

//从服务器获取工序的有关信息，并更新属性表
function readProcessInfo(infoTabPanel, propertyGrid, infoPanel, id) {
    infoTabPanel.setActiveTab(propertyGrid.id);
    var loadMask = loadDataMask(infoPanel);
    loadMask.show();
    Ext.Ajax.request({
        url:'/process/ReadProcess?readProcessById=&process.id=' + id.substring(1, id.length),
        //读取项目信息成功
        success: function(response) {
            var info = response.responseText;
            if (info == AjaxReturnMark.ERROR) {
                errorMsg('加载数据失败', '无法在数据库中找到该工序的有关数据，请检查您的数据！');
            } else {
                propertyGrid.setSource(Ext.util.JSON.decode(info));
            }
            loadMask.hide();
        },
        //读取项目信息失败
        failure: function() {
            loadMask.hide();
            errorMsg('加载数据失败', '读取工序数据失败，请联系管理员！');
        }
    });
}

function convertPropertyValue(value) {
    if (value.trim == '未知') {
        return null;
    }
}

// 读取用户选择的项目的xml文件
function readXML(mainPanel, node, graph) {
    var loadMask = loadDataMask(mainPanel);
    loadMask.show();
    Ext.Ajax.request({
        url:'/diagram/Platform?XmlPath=&project.id=' + node.id,
        //读取项目信息成功
        success: function(response) {
            var xmlPath = response.responseText;
            if (xmlPath == 'invalidParam') {
                errorMsg('打开工程项目', '请选择正确的工程项目！');
                return;
            }
            if (xmlPath == 'error') {
                errorMsg('绘图', '绘图时发生错误，请查看日志文件！');
                return;
            }
            graph.getModel().beginUpdate();
            try {
                // 根据用户的选择，从服务器请求该项目的xml文件的路径
                read(graph, response.responseText);
            } finally {
                // 更新画布
                graph.getModel().endUpdate();
            }
            loadMask.hide();
        },
        //读取项目信息失败
        failure: function() {
            loadMask.hide();
            errorMsg('读取项目数据失败', '读取项目数据失败，请联系管理员！');
        }
    });
}