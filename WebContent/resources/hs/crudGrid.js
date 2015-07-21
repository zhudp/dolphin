/*  
 * 可配置的综合Grid控件，继承新增、修改、删除、检索、分页功能。 
 */  
Ext.namespace("Ext.ux.crudGrid");   
Ext.ux.crudGrid.GridPanel = Ext.extend(Ext.grid.GridPanel, {   
    // 储存表格结构   
    structure : '',   
    // 表格绑定的表   
    tablename : '',   
    // 指定加载的列 默认为读取表上所有列数据   
    fields : '',   
    // 每页显示条数   
    pageSize : '15',   
    // 表单里控件的默认宽   
    fieldwidth : 50,   
    // reader类型如果当为json的时候，url不能空
    readType : 'json',   
    // 获取数据的URL   
    url : '',     
    // 表格主键   
    keyField : '',   
    // 是否需要分组，默认为false，如果设置为true须再设置两个参数一个为myGroupField和myGroupTextTpl   
    needGroup : false,   
    // 分组的字段名称   
    myGroupField : 'group',   
    // 分组显示的模板，eg：{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Items" : "Item"]})   
    myGroupTextTpl : '{text}',   
    // 列模式的选择模式,默认为rowselectModel，如果相设置成check模式，就设置为check   
    selectType : '',   
    // 默认排序字段   
    defaultSortField : 'id',  
    //排序方式
    defaultSortDir:'desc',
    //查询过滤信息,常用在检索部分 
    filterString:'',
    //绑定行的双击事件的函数体，默认为空如:rowdblclickfunction:function(grid,rowIndex,event){};
    rowdblclickfunction:null,
    //是否需要top工具栏，默认为false
    visibleTopToolbar:false,
	//是否需要增加按钮
	needAdd : false,
	//是否需要删除按钮
	needDel:false,
	//是否需要编辑按钮
	needEdit:false,
	//是否需要审核按钮
	needPass:false,
	//是否需要归档按钮
	needFiled:false,
	//是否需要搜索按钮
	needSeach:false,
    // 是否需要分页工具栏，默认为true   
    needPage : true,  
    //上部工具栏的设置,默认为‘-’
    topBar:'-', 
    frame : false,   
    // 是否带展开按钮，默认为false   
    collapsible : false,   
    animCollapse : true,   
    loadMask : true,   
    viewConfig : {   
        forceFit : true  
    },   
    // 存储表头信息   
    col : null,   
    // private   
    initComponent : function() {   
        if (this.structure != '') {   
            this.initStructure();   
        }   
        Ext.ux.crudGrid.GridPanel.superclass.initComponent.call(this);   
    },   
    // private   
    initEvents : function() {   
        Ext.ux.crudGrid.GridPanel.superclass.initEvents.call(this);   
        /*  
         * this.getStore().load( { params : { start : 0, limit : 30 } });  
         *   
         */  
        if (this.loadMask) {   
            // Ext.MessageBox.wait('sssssss')   
            this.loadMask = new Ext.LoadMask(this.bwrap, Ext.apply({   
                                store : this.store   
                            }, this.loadMask));   
        }   
    },   
    // private   
    initStructure : function() {
        var oDs = null;   
        var oCM = new Array(); // 列模式数组   
        var oRecord = new Array(); // 容器对数组   
        // 构成grid的两个必须组件: column和record，根据structure将这两个组件创建出来   
        // 判断表格的选择模式   
        if (this.selectType == 'check') {   
            var sm = new Ext.grid.CheckboxSelectionModel();   
            oCM[oCM.length] = sm;// 在列模式数组中添加checkbox模式按钮   
            this.selModel = sm;// 并将selModel设置为check模式   
        }   
        var len = this.structure.length;// 得到结构的长度   
        for (var i = 0; i < len; i++) {   
            var c = this.structure[i];   
            // alert( c.hidden ? c.width: this.fieldwidth)   
            // 如果字段为hidden，则不生成filters和columnMode   
            // 默认格式化日期列   
            if (c.type == 'date' && !c.renderer) {   
                c.renderer = this.formatDate;   
            }   
            oCM[oCM.length] = {   
                header : c.header,   
                dataIndex : c.dataIndex,   
                hidden : c.hidden || false,   
                width : !c.hidden ? c.width : this.fieldwidth,   
                // 类型为数字则右对齐   
                align : c.type == 'numeric' ? 'right' : 'left',   
                // 结构的渲染函数   
                renderer : c.renderer   
            };   
            oRecord[oRecord.length] = {
                name : c.dataIndex,   
                // 如果类型不是date型则全部为string型   
                type : c.type == 'date' ? 'date' : 'string',   
                dateFormat : 'Y-m-d'  
            };   
        }   
        this.col = oCM;   
        // 生成columnModel   
        this.cm = new Ext.grid.ColumnModel(oCM);   
        // this.colModel = this.cm;   
        // 默认可排序   
        this.cm.defaultSortable = true;   
        // 生成表格数据容器   
        var record = Ext.data.Record.create(oRecord);   
        
        // 判断读取类别，只实现jsonreader
        var reader;   
        var tablename = this.tablename;   
        var pageSize = this.pageSize;   
        var fields = this.fields;   
        switch (this.readType) {   
			case 'json' :   
                reader = new Ext.data.JsonReader({
					totalProperty: 'totalProperty',
					successPropety: 'successProperty',
					root: 'results',
					id: 'id'
				}, record);
				if (!this.needGroup) { 
					//如果不允许分组，就走普通的store
					this.ds = new Ext.data.Store({
						url : this.url,
						reader: reader
					});
			   }
			   else{
				//如果使用分组grid，就走这里的grouping
					this.ds = new Ext.data.GroupingStore({   
						proxy : new Ext.data.HttpProxy({   
							url : this.url   
						}),   
						reader : reader,   
						sortInfo : {   
							field : this.defaultSortField,   
							direction : this.defaultSortDir 
						},   
						remoteSort : true,   
						groupField : this.myGroupField,   
						listeners : {   
							'reload' : function() {   
								//alert('reload')   
							}   
						} 
					});
					//分组的view
				   this.view = new Ext.grid.GroupingView({   
						forceFit : true,   
						groupTextTpl : this.myGroupTextTpl   
					});   
				}
				break;
            default :   
                break;   
        }     
        this.store = this.ds;   
        // 生成分页工具栏   
        if (this.needPage) {   
            var pagingToolbar = new Ext.PagingToolbar({   
                displayInfo : true,   
                displayMsg : '当前记录数:{0} - {1} 总记录数: {2}',   
                emptyMsg : '没有符合条件的记录',   
                store : this.store   
            });   
            pagingToolbar.pageSize = this.pageSize;   
            this.bbar = pagingToolbar;   
            this.bottomToolbar = this.bbar;   
            var ogrid = this;   
            var keyField = this.keyField;   
        } 
	/**
	 * 增加
	 */
	var btn_add_toolBar = new Ext.Button({
		text: '增加',
		iconCls: 'icon-add',
		handler: function(){
			window_toolBar_add();
		}
	});
	/**
	 * 删除
	 */
	var btn_del_toolBar = new Ext.Button({
		text: '批量删除',
		iconCls: 'icon-del',
		handler: function(){
			Ext.Msg.confirm('批量删除', '删除选择信息后信息将不可恢复，确定要删除吗？', function(btn){
				if (btn == 'yes') {
					betchDeleteToolBar();
				}
			})
		}
	});
	/**
	 * 编辑
	 */
	var btn_edit_toolBar = new Ext.Button({
		text:'编辑',
		iconCls:'icon-edit',
		handler:function(){
			window_toolBar_edit();
		}
	});
	/**
	 * 审核
	 */
	var btn_state_toolBar = new Ext.Button({
		text: '审核',
		iconCls: 'icon-pass',
		handler: function(){
		},
		menu: {
			items: [{
				text: '审核',
				handler: function(){
					state(99);	
				}
				
			}, {
				text: '取消审核',
				handler: function(){
					state(0);
					
				}
				
			}]
		}
	});
	/**
	 * 归档
	 */
	var btn_isFile_toolBar = new Ext.Button({

		text: '归档',
		iconCls: 'icon-pass',
		handler: function(){
		},
		menu: {
			items: [{
				text: '归档',
				handler: function(){
					isFiled(false);
					
				}
				
			}, {
				text: '关闭',
				handler: function(){
					isFiled(true);
					
				}
				
			}]
		}

	});
	/**
	 * 搜索框
	 */
	var txt_search_toolBar = new Ext.form.TextField({
		id: 'txt_search_toolBar',
		name: 'search',
		width: 200,
		emptyText: '多条件可用逗号或者空格隔开!'
	});
	/**
	 * 搜索按钮
	 */
	var btn_search_toolBar = new Ext.Button({
		text: '查询',
		iconCls: 'icon-search',
		handler: function(){
		  //  store_node.load({
		  //      params: {
		  //          start: 0,
		  //          limit: 15
		  //      }
		   // });
		}
	});  
       //生成功能top工具栏
       if(this.visibleTopToolbar){
			var topToolbar = new Ext.Toolbar({   
                items : []   
            })      
      }
	  if(this.needAdd){
				topToolbar.addItem('-',btn_add_toolBar);
			} 
			if(this.needEdit){
				topToolbar.addItem('-',btn_edit_toolBar);
			}
			if(this.needDel){
				topToolbar.addItem('-',btn_del_toolBar);
			}
			 
			if(this.needPass){
				topToolbar.addItem('-',btn_state_toolBar);
			} 
			if(this.needFiled){
				topToolbar.addItem('-',btn_isFile_toolBar);
			} 
			if(this.needSeach){
				topToolbar.addItem( {
				xtype : 'tbfill'
			},txt_search_toolBar,'-',btn_search_toolBar);
		} 
        this.tbar = topToolbar;   
        this.topToolbar = this.tbar;   
        // 将filter加入grid   
        // this.plugins = filters;   
        var filterString = this.filterString;   
        this.store.on('beforeload', function() {   
            var para = {   
                action : 'show',   
                pageSize : pageSize,   
                name : tablename,  
                filterString:filterString,
                tmpId : '',   
                tmpName : ''  
            };   
            Ext.apply(this.baseParams, para);   
        });   
       // this.store.load({params : {start : 0,limit : this.pageSize}}); 
        //绑定行双击事件
        this.on('rowdblclick',function(grid,rowIndex,event){
        	if(Ext.isEmpty(this.rowdblclickfunction)){
				
        	}else{
        		this.rowdblclickfunction(grid,rowIndex,event);
        	}
        });    
    },   
    /*  
     * @功能：请求成功显示信息  
     */  
    doSuccess : function(action, form) {   
        var ogrid = this;   
        Ext.Msg.alert('提示', '操作成功');   
        ogrid.getStore().reload();   
    },   
    /*  
     * @功能：请求失败显示信息  
     */  
    doFailure : function(action, form) {   
        Ext.Msg.alert('请求错误', '服务器未响应，请稍后再试');   
    },  
 
    /*  
     * @功能:默认的格式化日期函数 @返回格式：2008-09-21  
     */  
    formatDate : function(v) {   
        return v ? v.dateFormat('Y-m-d') : ''  
    },
    //获取所有选中的record
   getSelectionRecords:function() {  
        if (this.getSelectionModel().getSelections()[0])  
            return  this.getSelectionModel().getSelections();  
        else {  
            alert('请选择数据！');  
          return false;  
          }  
    },  
    //重新载入数据
    reload:function() {  
        this.getStore().reload();  
    },  
    //载入数据
    loadData:function(){
      	this.store.load({params : {start : 0,limit : this.pageSize}}); 
    },
    //获取选中行的第一个record
    getSelectionRecord:function() {  
        if (this.getSelectionModel().getSelections()[0])  
            return  this.getSelectionModel().getSelections()[0];  
        else {   
			return null;  
        }  
	}
});  

/** 使用范例
 * Ext.onReady(function() { 
	Ext.QuickTips.init(); 
	Ext.form.Field.prototype.msgTarget = 'side'; 
	var gridStructure = [
		// grid的数据结构   
		{header : '事件编号',     name : 'SJID',     hidden : "true",     type:'label'   }, 
		{     header : '研究项目号',     name : 'YJXMBH',     width : 200,     type:'combo',     
		//绑定数据库     
		fobj:'YL_YJXM',     text:'XMMC',     value:'YJXMBH'     
		//绑定数组     
		//fobj:[[1,'是'],[2,'否']]   
		}, 
		{     header : '时间点',     name : 'SJD',     align : 'center',     width : 100,     type:'number'     
		//rendererunction(v){return v=='4.0'?'是':'否'}   
		}, 
		{     header : '时间点描述',     name : 'SJDMS',     align : 'center',     width : 100   }, 
		{     header : '事件',     name : 'SJ',     align : 'center',     width : 100   }, 
		{     header : '检查项目编号',     name : 'JCXMBH',     align : 'center',     width : 100   }
	]; 
	var myUxGrid = new Ext.ux.grid.MyGrid({
		// 创建封装的MyGrid对象   
		id : 'dept-center-stat-grid',   
		url : 'http://localhost080/YLXT/servlet/DealDataJsonServlet.do',  
		selectType : 'check',   
		// dataObject : myGridData,
		// defaultSortField : 'stat_sn',   
		// keyField : 'basicUnitNo',   
		structure : gridStructure,   
		autoScroll : true,   
		autoWidth : true,  
		frame : true,   
		bodyStyle : 'width:100%',   
		layout : 'fit',   
		height : 600,   
		findField:'YJXMBH', 
		//指定搜索字段   keyField:'SJID', 
		//指定主键字段   loadMask : {   msg : label.wait   },   
		title : '研究步骤维护',  
		renderTo : Ext.getBody(),  
		tablename : 'YL_YJBZ' 
	}); 
}); 
 */