/**
 * 操作日志
 */

Ext.namespace("systemLog");
systemLog.systemLogURL = {
	queryPaged : '/dolphin/system-log!queryPaged.do',
	operTypeMap : '/dolphin/system-log!queryOperType.do'
};
systemLog.systemLogRecord = [{
			name : 'slogId'
		}, {
			name : 'userId'
		}, {
			name : 'userName'
		}, {
			name : 'ipAdd'
		}, {
			name : 'operType'
		}, {
			name : 'operObjec'
		}, {
			name : 'slogComment'
		}, {
			name : 'slogCreateTime'
		}, {
			name : 'slogType'
		}, {
			name : 'slogTypeName'
		}];
systemLog.SystemLogBar = function() {
	var gird;
	var userNameText = new Ext.form.TextField({
				name : 'userNameSear',
				width : 120
			});

	var operTypeComboBox = new Ext.form.ComboBox({
				hiddenName : 'oper_type',
				width : 80,
				displayField : 'text',
				store : dolphin.resource.Util.getOperTypeComboStore(true)
			});

	var operTimeDate = new Ext.form.DateField({
				fieldLabel : '日志时间',
				name : 'operTimeSear',
				width : 100,
				format : 'Y-m-d'
			});
	var searchBtn = new Ext.Button({
				text : '查询',
				iconCls : 'hs-button-search',
				handler : search
			});

	function search() {
		grid.load({
					userName : userNameText.getValue(),
					operType : operTypeComboBox.getValue(),
					slogCreateTime : operTimeDate.value
				});
	}

	return {
		getPanel : function() {
			return searchFormPanel = {
				region : "north",
				layout : 'fit',
				height : 35,
				xtype : 'toolbar',
				items : [new Ext.Panel({
							frame : false,
							tbar : [{
								xtype : 'buttongroup',
								border : false,
								defaults : {
									scale : 'small'
								},
								items : [new Ext.form.Label({
													text : "操作员："
												}), userNameText,
										new Ext.form.Label({
													text : "操作类型："
												}), operTypeComboBox,
										new Ext.form.Label({
													text : "日志时间："
												}), operTimeDate, searchBtn]
							}]
						})]
			};
		},

		init : function(config) {
			grid = config.grid;
			search();
		}
	}
}

systemLog.SystemLogGrid = function(_config) {
	var config = _config || {};
	var gridStore = hs.StoreFactory.getStore(config.systemLogURL,
			systemLog.systemLogRecord);
	var operTypeStore = dolphin.resource.Util.getOperTypeComboStore();
	var grid = hs.GridFactory.getGridPanel({
				title:'系统日志',
				el : hs.ELFactory.getGridEL({
							id : "systemLogGrid"
						}),
				region : "center",
				store : gridStore,
				border:false,
				cm : [new Ext.grid.RowNumberer(), {
							header : "操作员",
							width : 80,
							dataIndex : 'userName'
						}, {
							header : "Ip地址",
							width : 100,
							dataIndex : 'ipAdd'
						}, {
							header : "操作类型",
							width : 80,
							sortable : true,
							dataIndex : 'operType',
							renderer : dolphin.resource.Util
									.render(operTypeStore)
						}, {
							header : "操作对象",
							width : 330,
							dataIndex : 'operObjec',
							hidden : 'true'
						}, {
							header : "操作数据",
							width : 420,
							dataIndex : 'slogComment',
							sortable : false
						}, {
							header : "日志时间",
							sortable : true,
							width : 120,
							dataIndex : 'slogCreateTime'
						}],
				pageSize : 22,
				height : 520
			});
	grid.on('cellclick', function(grid, rowIndex, columnIndex, e) {
				var record = grid.getStore().getAt(rowIndex); // Get the
				// Record
				var fieldName = grid.getColumnModel().getDataIndex(columnIndex); // Get
				// field
				// name
				var data = " 操作员：  " + record.get('userName') + "；";
				data = data
						+ " <BR /> 操作动作： "
						+ dolphin.resource.Util.getLabelByCode(record
										.get('operType'), operTypeStore) + "；";
				data = data + " <BR /> 操作时间： " + record.get('slogCreateTime')
						+ "；";
				data = data + " <BR /> 操作对象： " + record.get('operObjec') + "；";
				data = data + " <BR /> 操作数据： " + record.get('slogComment');

				if (fieldName == "slogComment") {
					// hs.MessageHelper.toolTip({targetId:e.target,text:data});
					hs.MessageHelper.info({
								title : "操作日志内容",
								msg : data
							});
				}
			});

	return {
		getSystemLogGrid : function() {
			return grid;
		},
		load : function(baseParams) {
			hs.StoreHelper.beforeload(gridStore, baseParams);
			gridStore.load({
						params : {
							start : 0,
							limit : 15
						}
					});
		}
	}
}
systemLog.SystemLogPanel = function() {
	var systemLogBar = systemLog.SystemLogBar();
	var systemLogGrid = systemLog.SystemLogGrid({
				systemLogURL : systemLog.systemLogURL.queryPaged
			});
	systemLogBar.init({
				grid : systemLogGrid
			}); // 使用 userGrid.getUserGrid() 页面报错：this.body is null

	var systemLogPanel = new Ext.Viewport({
				layout : 'border',
				border : true,
				items : [systemLogBar.getPanel(),
						systemLogGrid.getSystemLogGrid()]
			});
}
