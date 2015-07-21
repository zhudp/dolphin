Ext.namespace("help.faq.manage");

help.faq.manage.url = {
		queryHelpPage : '/help/help-faq!queryPaged.do'
		,getHelp : '/help/help-faq!get.do'
		,saveURL : '/help/help-faq!addHelpFaq.do'
		,deleteHelpFaq : '/help/help-faq!deleteHelpFaq.do'
};
help.faq.manage.record=[
	{name:"id"},
	{name:"helpType"},
	{name:"title"},
	{name:"keyWord"},
	{name:"content"},
	{name:"status"},
	{name:"browseCount"},
	{name:"gmtCreate"},
	{name:"creator"},
	{name:"gmtModify"},
	{name:"modifier"}
];

/**
 *  知识信息查询面板
 * @param {} config
 */
help.faq.manage.MainPanel = function(config) {
	this.config = config;
};

help.faq.manage.MainPanel.prototype={
		
	showViewPort: function() {
		var grid = this.getGrid();
		grid.load();
		new Ext.Viewport({
			layout: "border",
			margins: '0 0 0 0',
			defaults: {
				border: false
			},
			items: [{
					region: "north",
					layout: 'fit',
					height: 35,
					items: [this.getTbar()]
				},{
					region: "center",
					layout: 'fit',
					items: [grid]
				}
			]
		});
	},
	createWin: function(){
		if(!this.sWin){
			var topBar = this.getTbar();
			var grid = this.getGrid();
			var win = new Ext.Window({
				title : '知识信息列表',
				frame : true,
				width : 850,
				height : 500,
				layout : 'border',
				closeAction : 'hide',
				items : [{
					region : "north",
					layout : 'fit',
					height : 57,
					split: true,
					border : false,
					items : [topBar]
				}, {
					region : "center",
					layout : 'fit',
					items : [grid]
				}]
			});
			this.sWin = win;
		}
		return this.sWin;
	},
	getTbar:function() {
		if(!this.tbar) {
			var grid = this.getGrid();
			var searchFun = function() {
				grid.load({
					keyWord:keyWordField.getValue()
					,helpType:helpTypeComb.getValue()
				});
			};

			var keyWordField = new Ext.form.TextField({
				fieldLabel: '关键字',
				name : 'keyWord',
				width : 140,
				listeners:{
					'specialkey': {fn:function(field,e){
				   		if (e.getKey()==Ext.EventObject.ENTER){
				   			searchFun();
				   	 	}
					}}
			   	 }
			});
			
			var helpTypeComb = new Ext.form.ComboBox({
				fieldLabel: '主题'
				,hiddenName : 'helpType'
				,displayField: 'text'
				,width: 140
				,tabIndex:2
				,store: dolphin.resource.Util.getHelpTypeComboStore(true)
			});
			
			var toolbar = new Ext.form.FormPanel({
				frame : false,
				border : false,
				labelSeparator:'：',
				tbar : [{
					xtype: 'buttongroup',
		            defaults: {
		                scale: 'small'
		            },
					items : [
						{xtype:'label', text:"关键字："}
						,keyWordField
						,{xtype : 'tbspacer', width : 10}
						,{xtype:'label',text : "主题："}
						,helpTypeComb
						,{
			                text: '查询',
							iconCls: 'hs-button-search',
			                handler:function() {
								searchFun();
							}
			            }
					]
				}]
			});
			toolbar.searchFun = searchFun;
			this.tbar = toolbar;
		}
		return this.tbar;
	},
	getGrid:function() {
		if(!this.grid) {
			var _this = this;
			var store = hs.StoreFactory.getStore(help.faq.manage.url.queryHelpPage, help.faq.manage.record);
			store.sortInfo = {
				field : 'id',
				direction : 'DESC'
			};
			var paging = new Ext.PagingToolbar({
				pageSize : PAGE_SIZE,
				store : store,
				displayInfo : true,
				plugins : [new Ext.ux.plugins.PageComboResizer()]
			});
			var detailBtn = new Ext.Button({
				text : '知识详情',
				iconCls : 'hs-button-view',
				scope:this,
				handler : function() {
					var records = this.getGrid().getSelectionModel().getSelections();
					if (!records || records.length == 0) {
						hs.MessageHelper.info( {
							msg : '请选择记录后再进行操作！'
						});
						return;
					}
					if (records.length > 1) {
						hs.MessageHelper.info( {
							msg : '请选择1条记录进行操作！'
						});
						return;
					}

					// 显示知识卡片
					if(!this.cardWin){
						var cardWin = new help.card.infoWin();
						this.cardWin = cardWin;
					}
					this.cardWin.show(records[0].get('id'),records[0].get('title'));
				
				}
			});
			
			var addHelpBtn = new Ext.Button({
				text : '添加',
				id : '010401',
				iconCls : 'hs-button-add',
				handler : function() {
					var helpWin = new help.faq.helpWin({
						callback: function(isSave){
							_this.getTbar().searchFun();
							if(isSave)
								hs.MessageHelper.info({msg: '处理成功！'});
						}
					});
					helpWin.show();
				}});
			
			var editBtn = new Ext.Button({
				text : '修改',
				id : '010402',
				iconCls : 'hs-button-edit',
				handler : function() {
					var records = grid.getSelectionModel().getSelected();
					if (!records || records.length == 0 ) {
						hs.MessageHelper.info({msg : '请选择记录后再进行操作！'});
						return;
					}
					var helpWin = new help.faq.helpWin({
						callback: function(isSave){
							_this.getTbar().searchFun();
							if(isSave)
								hs.MessageHelper.info({msg: '处理成功！'});
						}
					});
					helpWin.show('edit', records.get('id'));
				}});
			
			var delBtn = new Ext.Button({
				text : '删除',
				id : '010403',
				iconCls : 'hs-button-remove',
				handler : function() {
					var record = grid.getSelectionModel().getSelected();
					if (!record) {
						hs.MessageHelper.info({
								msg : '请选择记录后再进行操作！'
							});
						return;
					}
					hs.MessageHelper.confirm("确认删除所选记录吗？", function() {
							hs.ActionHelper.request(help.faq.manage.url.deleteHelpFaq, {
								id : record.data.id
							}, _this.getTbar().searchFun());
						});
				}});
			var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true });
			var grid = new Ext.grid.GridPanel({
				ds: store,
				border:false,
				stripeRows:true,
				tbar : [detailBtn,'-',addHelpBtn,'-', editBtn,'-',delBtn],
				loadMask: {msg:'数据加载中，请稍候...'},
				sm:sm,
				cm: new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),sm,
					{header : "知识ID",dataIndex : 'id',width : 80, hidden: true}
					,{header : "主题",dataIndex : 'helpType',width : 100 , align: 'center',
						renderer: dolphin.resource.Util.render(dolphin.resource.Util.getHelpTypeComboStore())}
					,{header : "标题",  dataIndex : 'title',width : 260 }
					,{header : "关键字",  dataIndex : 'keyWord',	width : 150 }
//					,{header : "内容",dataIndex : 'content',width : 150 }
					,{header : "浏览次数",dataIndex : 'browseCount',width : 80 , sortable : true }
					,{header : "创建时间",dataIndex : 'gmtCreate',width : 130 , hidden: true}
					,{header : "创建人",dataIndex : 'creator',width : 130 , hidden: true}
					,{header : "修改时间",dataIndex : 'gmtModify',width : 130 , hidden: true}
					,{header : "修改人",dataIndex : 'modifier',width : 130 , hidden: true}
			    ]),
			    enableColumnHide: false,
				enableColumnMove: false,
				enableColumnResize: true,
				enableHdMenu: false,
				enableRowHeightSync: false,
				monitorWindowResize: false,
				stripeRows:true,
				trackMouseOver: true,
				bbar : paging,
				listeners:{
					'rowdblclick' : {fn:function(grid, rowIndex, columnIndex, e) {
						var records = this.getGrid().getSelectionModel().getSelections();
						if (!records || records.length == 0) {
							hs.MessageHelper.info( {
								msg : '请选择记录后再进行操作！'
							});
							return;
						}
						if (records.length > 1) {
							hs.MessageHelper.info( {
								msg : '请选择1条记录进行操作！'
							});
							return;
						}
						// 显示知识卡片
						if(!this.cardWin){
							var cardWin = new help.card.infoWin();
							this.cardWin = cardWin;
						}
						this.cardWin.show(records[0].get('id'),records[0].get('title'));
					},scope:this}
		        },
		  
				load: function(baseParams) {
					
					hs.StoreHelper.beforeload(store, baseParams);
					store.load({
						params: {
							start: 0,
							limit: paging.pageSize || PAGE_SIZE
						}
					});
				} 
			});
			
			this.grid = grid;
		}
		return this.grid;
	},
	getPeopleDetail :function() {
		if(!this.peopleDetail) {
			var peopleDetail = new people.detail.DetailPanel({});
			this.peopleDetail = peopleDetail;
		}
		
		return this.peopleDetail;
	}
	,getSelected: function(){
		var records = this.getGrid().getSelectionModel().getSelections();
		if (!records || records.length == 0) {
			hs.MessageHelper.info({msg : '请选择记录后再进行操作！'});
			return;
		}
		if (records.length > 1) {
			hs.MessageHelper.info({msg : '请选择1条记录进行操作！'});
			return;
		}
		return records[0];
	}
};

/**
 * 知识信息新增窗口
 * @param {} config
 * @return {}
 */
help.faq.helpWin = function(config) {
	this.config = config;
};

help.faq.helpWin.prototype = {
		getHelpForm: function(){
			if(!this.form){
				var titleText = new Ext.form.TextField({
					fieldLabel:'标题'
					,name:'title'
					,width:220
				});
				
				var keyWordText = new Ext.form.TextField({
					fieldLabel:'关键字'
					,name:'keyWord'
					,width:140
				});
				
				var contentText = new Ext.form.TextArea({
					fieldLabel:'内容'
					,name:'content'
					,width:260
					,height:120
				});
				
				var helpTypeComb = new Ext.form.ComboBox({
					fieldLabel: '主题'
					,hiddenName : 'helpType'
					,displayField: 'text'
					,width: 140
					,store: dolphin.resource.Util.getHelpTypeComboStore()
				});
				
				var idText = new Ext.form.Hidden({
					name : 'id',
					id : 'id'
				});
				
				var formPanel = new Ext.form.FormPanel({
					frame: true,
				    broder:false,
				    id: 'helpForm',
				    layout: 'form',
				    autoScroll: true,
					labelWidth: 85,
					labelAlign: 'right',
					items: [
					        hs.FormLayout.wholeOneColumnedRow(
					        		helpTypeComb
							    	,{anchor: '90%'}
								)
								,hs.FormLayout.wholeOneColumnedRow(
									titleText
							    	,{anchor: '90%'}
								)
								,hs.FormLayout.wholeOneColumnedRow(
									keyWordText
							    	,{anchor: '90%'}
								)
				                ,hs.FormLayout.wholeOneColumnedRow(
				                	contentText
							    	,{anchor: '90%'}
								)
				                ,hs.FormLayout.wholeOneColumnedRow(
				                	idText
									,{anchor: '90%'}
				                )
				           ]
				});
				this.form = formPanel;
			}
			return this.form;
		}
		,getWin : function() {
			if (!this.win) {
				var _this = this;
				var formPanel = this.getHelpForm();
				var win = new Ext.Window({
					model : true,
					frame : true,
					layout : 'fit',
					width : 500,
					height : 290,
					closeAction : 'hide',
					items : [formPanel],
					buttons:[{
			            text: '保存',
			            iconCls:'icon_save', 
			            id: 'saveReseauBtn',
			           	handler: function() {
			           		hs.FormHelper.submit(help.faq.manage.url.saveURL,
								function(form, action) {
									hs.MessageHelper.succuss({
												callback : function() {
													win.hide();
													_this.config.callback();

												}
											});
								}, null, formPanel.getForm());
						}
			        },{
			            text: '取消',
			            iconCls:'icon_cancel', 
			           	handler: function() {
			           		win.hide();
						}
			        }]
				});
				this.win = win;
			}
			return this.win;
		}
		,show: function(doType, id){
			this.getWin().show();
			if(doType=="edit"){
				this.load(id);
				this.getWin().setTitle('修改此知识信息');
			}else{
				this.getWin().setTitle('新建知识信息');
			}
		}
		,load : function(id) {
			hs.FormHelper.load(help.faq.manage.url.getHelp, {
						id: id
					}, this.successFn, this.getHelpForm().getForm());
		}
		,successFn: function(form, action){
			var data = action.result.data;
			if(data instanceof Array){
				var deCodeData = Ext.util.JSON.decode(data);
				form.clearInvalid();
				form.setValues(deCodeData);
			}
		}
};