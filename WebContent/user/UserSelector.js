Ext.namespace("Ext.ux.chooser");

/**
 * 用户选择器
 * @param  config <br>
 *  		 .title 	窗口名称 	（默认）<br>
 *  		 .callback(records) 	回调方法 （必填）<br>
 *  		 .pageSize 	显示行数 	（默认15）<br>
 *  		 .singleSelect 		是否限制单选(默认否)<br>
 */
Ext.ux.chooser.UserSelector = function(config) {
	
	this.config = config;
	
	this.config.queryUrl = '/dolphin/user!queryByArea.do';
	this.config.record = [
      {name:"id"},
      {name:"userName"},
      {name:"reseauInfo"}
     ];
	this.config.sm = new Ext.grid.CheckboxSelectionModel();
	this.config.column = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer()
		,this.config.sm
		,{header : "用户ID",dataIndex : 'id',width : 60,hidden:true}
	    ,{header : "用户姓名",dataIndex : 'userName',width : 70 }
	    ,{header : "管辖网格",dataIndex : 'reseauInfo',width :200}
	]);
	this.config.sm2 = new Ext.grid.CheckboxSelectionModel();
	this.config.column2 = new Ext.grid.ColumnModel([
       new Ext.grid.RowNumberer()
       ,this.config.sm2
       ,{header : "用户ID",dataIndex : 'id',width : 60,hidden:true}
	    ,{header : "用户姓名",dataIndex : 'userName',width : 70 }
	    ,{header : "管辖网格",dataIndex : 'reseauInfo',width :200}
     ]);
};

Ext.ux.chooser.UserSelector.prototype = {
	show : function(){
		if(!this.win){
		    var win = new Ext.Window({
				renderTo: Ext.getBody(),
				title: this.config.title || '选择网格巡查员',
				layout: 'column',
				width : 710,
				height:460,
				minWidth:710,
				minHeight:460,
				closeAction:'close',
				items:[{
		        	border:false,
		        	columnWidth: .46,
		        	items:[this.getGrid()]
		        },{
		        	border:false,
		        	columnWidth: .08,
		        	bodyStyle:'padding:80px 4px;background-color:#DFE8F6;',
		        	items:[
		        		this.getAddButton()
		        		,{
			        		bodyStyle:'height:30px;background-color:#DFE8F6;',
			        		border:false,
			        		html:'&nbsp;'
		        		}
		        		,this.getDelButton()
		        	]
		        },{
		        	border:false,
		        	columnWidth: .46,
		        	items:[this.getGrid2()]
		        }],
				tbar:this.getTbar(),
			    listeners:{
					'beforeclose' : {fn:function() {
						var g2= this.getGrid2();
						g2.getSelectionModel().selectAll();
						var records = g2.getSelectionModel().getSelections();
//						if (records && records.length>0) {
//							return confirm("确定退出吗？");
//						}
						
					},scope:this}
		        }
			});
		    
		    this.win = win;
		}
		this.win.show();
		this.getTbar().doSearch();
	},
	getTbar: function() {
		if(!this.tbar) {
			var grid = this.getGrid();
			var grid2 = this.getGrid2();
			var config = this.config;
			
			var communityField = new Ext.form.ComboBox({
				fieldLabel: '　　社区'
				,hiddenName : 'communityId'
				,value:this_user_CommunityId
				,disabled:this_user_CommunityId==null?false:true
				,displayField: 'text'
				,width: 120
				,store: hs.StoreFactory.getComboStore(comboJsonData["COMMUNITY"],true)
				,listeners : {
					select : function(combo, record, index) {
						doSearch();
					}
				}
			});
			
			var doSearch = function() {
				grid.load({
					communityId : communityField.getValue()
				});
			};
			
			var doConfirm = function() {
				grid2.getSelectionModel().selectAll();
				var records = grid2.getSelectionModel().getSelections();
				if (!records || records.length==0) {
					hs.MessageHelper.info({msg : '请双击左侧网格进行选择后，点击确定按钮添加用户信息！'});
					return false;
				}
				if(config.singleSelect && records.length > 1) {
					hs.MessageHelper.info({msg : '只能添加一个网格信息！'});
					return false;
				}
				config.callBack(records);
				return true;
			};
			
			var tbar = new Ext.Toolbar({
				items:[
					{xtype:'label',text : "社区："},communityField
					,{xtype : 'tbspacer',width : 10 }
					/*,{
						text : '查  询',
						iconCls : 'hs-button-search',
						handler : function() {
							doSearch();
						}
					}*/
					,'->',{
						text: '确定',
						iconCls : 'icon_save',
						scope: this,
						handler: function(){
							if(doConfirm())
							this.win.hide();
							
						}
					},'-'
				]
			});
			tbar.doSearch=doSearch;
			tbar.doConfirm=doConfirm;
			this.tbar = tbar;
		};
		return this.tbar;
	},
	getAddButton: function(){
		if(!this.addButton) {
			var g1 = this.getGrid();
			var g2 = this.getGrid2();
			var singleSelect = this.config.singleSelect;
			this.addButton = new Ext.Button({
				text:'添加>>',
				handler:function() {
					var records =  g1.getSelectionModel().getSelections();
					if(singleSelect) {
						g2.store.removeAll();
					}
			    	g2.store.add(records);
			    	g2.store.sort('communityId', 'ASC');
				}
			});
		}
		return this.addButton;
	},
	getDelButton: function(){
		if(!this.delButton) {
			var g1 = this.getGrid();
			var g2 = this.getGrid2();
			this.delButton = new Ext.Button({
				text:'<<删除',
				handler:function() {
					var records =  g2.getSelectionModel().getSelections();
			    	g2.store.remove(records);
			    	g2.store.sort('communityId', 'ASC');
				}
			});
		}
		return this.delButton;
	},
	getGrid: function() {
		if(!this.grid) {
			var pageSize = 400;
			var store = hs.StoreFactory.getStore(this.config.queryUrl, this.config.record);
			var pagging = new Ext.PagingToolbar({
				pageSize : pageSize,
				store : store,
				displayInfo : false
			});
			var grid = new Ext.grid.GridPanel({
				height:400,
				store: store,
				//border:false,
				stripeRows:true,
				sm: this.config.sm,
				cm: this.config.column,
				bbar:pagging,
				loadMask: {msg:'数据加载中，请稍候...'},
				load:function(baseParams) {
					hs.StoreHelper.beforeload(store, baseParams);
					store.load({
						params : {
							start : 0,
							limit : pagging.pageSize || pageSize
						}
					});
				},
				listeners:{
					'rowdblclick' : {fn:function(grid, rowIndex, columnIndex, e) {
						this.getAddButton().handler();
					},scope:this}
		        }
			});
			
			this.grid = grid;
		}
		
		return this.grid;
	},
	getGrid2: function() {
		if(!this.grid2) {
			var store = hs.StoreFactory.getStore(this.config.queryUrl, this.config.record);
			store.remoteSort=false;
			if(this.config.selectedStore) {
				this.config.selectedStore.each(function(rec, index) {
					store.add(rec);
				});
			}
			var grid2 = new Ext.grid.GridPanel({
				height:400,
				store: store,
				border:true,
				stripeRows:true,
				sm: this.config.sm2,
				cm: this.config.column2,
				listeners:{
					'rowdblclick' : {fn:function(grid, rowIndex, columnIndex, e) {
						this.getDelButton().handler();
					},scope:this}
				}
			});
			
			this.grid2 = grid2;
		}
		
		return this.grid2;
	}
};
