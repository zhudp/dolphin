/**
 * 维护公告管理.
 */
/**
 * 编程流程 1、定义该模块访问后台数据的所有 URL 2、如果有Grid，定义Grid的 record 3、定义
 * 查询formPanel及其中的各个控件、查询方法，提供返回FormPanel、查询方法、初始化Grid的接口 4、定义 GridPanel
 * （必要参数：callback方法，一般为刷新grid的方法） 5、定义 增、改 所需的Form、Win 6、使用 Viewport+border嵌套fit
 * 布局渲染到页面上
 */
Ext.namespace("dolphin.sysUpdateLog");

dolphin.sysUpdateLog.URL = {
	saveURL : '/dolphin/system-log!saveUpdateLog.do',
	getURL : '/dolphin/system-log!getUpdateLog.do',
	queryURL : '/dolphin/system-log!queryUpdateLog.do',
	deleteURL : '/dolphin/system-log!deleteUpdateLog.do'
};

dolphin.sysUpdateLog.Record = [

	{ name:"id"},{name:"updateTime"},{ name:"context"},{ name:"gmtCreate"},{ name:"creator"},{ name:"updateDate"}

];

dolphin.sysUpdateLog.Panel = function(){
		var Win;
 		var store = hs.StoreFactory.getStore(dolphin.sysUpdateLog.URL.queryURL, dolphin.sysUpdateLog.Record);
	   
	    var Grid = hs.GridFactory.getGridPanel({
			title : '维护公告列表',
			pageSize : 20,
			store : store,
			autoScroll : true, // 允许滚动条
			cm: [
				new Ext.grid.RowNumberer()
				,{header : "id",dataIndex : 'id', hidden : true}
				,{header : "停机维护日期",dataIndex : 'updateDate',width : 80 }
				,{header : "停机维护时间",dataIndex : 'updateTime',width : 70 }
				,{header : "内容",dataIndex : 'context',width : 200 }
				,{header : "创建时间",dataIndex : 'gmtCreate',width : 80 }
				,{header : "创建者",dataIndex : 'creator',width : 70 }
			],
	        tbar: [{
				text : "添加"
				,iconCls: 'icon_add'
				,scope:this
				,handler:function() {
					if (!Win) {
						Win = dolphin.sysUpdateLog.Win(Grid.getStore());
					}
					Win.show("新增");
				}
			},{
				text : "修改"
				,iconCls: 'icon_edit'
				,scope:this
				,handler:function() {
						var record = Grid.getSelectionModel().getSelected();
						if (!record) {
							hs.MessageHelper.info({
										msg : '请选择记录后再进行操作！'
									});
							return;
						}
						if (!Win) {
							Win = dolphin.sysUpdateLog.Win(Grid.getStore());
						}
						Win.show("修改", record);
				}
			},{
				text : "删除"
				,iconCls: 'icon_delete'
				,scope:this
				,handler:function() {
						var record = Grid.getSelectionModel().getSelected();
						if (!record) {
							hs.MessageHelper.info({
										msg : '请选择记录后再进行操作！'
									});
							return;
						}
						hs.MessageHelper.confirm("确认删除所选记录吗？", function() {
									hs.ActionHelper
											.request(
													dolphin.sysUpdateLog.URL.deleteURL,
													{
														id : record.id
													}, store.reload())
								});					
				}
			}]
		});
	
	return {
		getGrid : function() {
			return Grid;
		},
		load : function(baseParams) {
			hs.StoreHelper.beforeload(store, baseParams);
			store.load({
						params : {
							start : 0,
							limit : PAGE_SIZE
						}
					});
		}
	}

};
dolphin.sysUpdateLog.Win = function(store) {
	var form = new dolphin.sysUpdateLog.Form();
	var win = new Ext.Window({
				layout : 'fit',
				height : 250,
				width : 400,
				autoHeight : true,
				items : form.getForm(),
				buttons : [new Ext.Button({
							text : '保存',
							handler : function() {
								hs.FormHelper.submit(
										dolphin.sysUpdateLog.URL.saveURL,
										function(form, action) {
											hs.MessageHelper.succuss({
														callback : function() {
															hs.WindowHelper
																	.hide(true);
															store.reload();
														}
													});
										}, null, form.getForm().getForm());
							}
						}), new Ext.Button({
							text : '关闭',
							handler : function() {
								win.hide();
							}
						})]
			});
	return {
		show : function(title, record) {
			win.setTitle(title);
			win.show();
			form.load(record);
		},
		setFormValue : function(config) {
			form.getForm().getForm().setValues({});
		},
		getForm : function() {
			return form.getForm();
		}
	}
};

dolphin.sysUpdateLog.Form = function() {
	var dateField = new Ext.form.DateField({
		fieldLabel:'维护日期'
		,allowBlank:false
		,name:'updateDate'
		,format: 'Y-m-d'
		,width: 100
		,tabIndex:4
	});
	var timeField = new Ext.form.TimeField({
		fieldLabel:'维护时间'
		,name:'updateTime'
		,format: 'H:i'
		,width: 100
		,tabIndex:4
		,allowBlank:false
	});
	
	// 基本信息
	var form = new Ext.form.FormPanel({
				autoWidth : true,
				autoHeight : true,
				frame : true,
				defaults : {
					labelWidth : 75
				},
				labelAlign : 'right',
				items : [
					{hidden : true,items : [new Ext.form.NumberField({id : 'id'})]}
					, dateField
					,timeField
					,new Ext.form.TextArea({fieldLabel : '内容',id : 'context',allowBlank : false,height:180,width:250})
				]
			});

	return {
		load : function(record) {
			if (record) {
				hs.FormHelper.load(dolphin.sysUpdateLog.URL.getURL, {
							id : record.id
						}, null, form.getForm());
			}
		},
		getForm : function() {
			return form;
		}
	}
}
/**
 * 将之前定义的容器布局在一个panel中，并渲染到页面的方法
 */
dolphin.sysUpdateLog.View = function() {

	var grid = dolphin.sysUpdateLog.Panel();
	grid.load();
	var sysUpdateLogView = new Ext.Viewport({
				layout : "border",
				margins : '0 0 0 0',
				defaults : {
					border : false
				},
				items : [{
							region : "center",
							layout : 'fit',
							items : [grid.getGrid()]
						}]
			});
}
