Ext.namespace(Commons.Chooser);
Commons.Chooser = function(config) {
	/**
	 * queryUrl:url,查询地址
	 * record:[{name:id}]
	 * cm:[{header:"ID",dataIndex:'customId',width:60,hidden:true},{}]
	 * tbar[{fieldLabel: '客户名称',name : 'customName',....}]
	 * callback:fn
	 * scope:Object
	 * width : 600,
	 * height: 500,
	 */
	if(Ext.isObject(config)){
		this.config = config;
		if(Ext.isNumber(config.width)){
			this.width = config.width;
		}else{
			this.width = 600;
		}
		if(Ext.isNumber(config.height)){
			this.height = config.height;
		}else{
			this.height = 500;
		}
		
		this.queryUrl =config.url;
		this.record = config.record;
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true });
		this.cm=[sm,new Ext.grid.RowNumberer()];
		if(Ext.isArray(config.cm)){
			for(var item in config.cm){
				this.cm.push(config.cm[item]);
			}
		}
		this.tbar =[];
		if(Ext.isArray(config.tbar)){
			var queryParm={};
			for(var item in config.tbar){
				fieldConfig =config.tbar[item];
				this.tbar.push({text: fieldConfig.fieldLabel});
				this[fieldConfig.name] = new Ext.form.TextField(fieldConfig);
				this.tbar.push(fieldConfig);
				queryparm[fieldConfig.name] ='';
			}
			this.tbar.push(
					{
			    	  xtype : 'button',
			    	  text : '查  询',
			    	  iconCls: 'hs-button-search',
			    	  handler : function() {
			    		  //TODO:this.queryParm 取参数值
			    		  var queryParm = this.queryParm;
			    		  function eachFn(scope, item, i, array){
			    			  queryParm[item] = scope[item].getValue();
			    		  }
			    		  Ext.each(this.queryParm,eachFn,this);
			    		  
			    		  this.dataGrid.load(queryParm);
			    	  },
			    	  scope : this
				      });
		      
			this.queryParm = queryParm;
		}
		this.callBack = config.callBack;
		this.callBackScope = config.scope;
	}
};
Commons.Chooser.prototype = {
	show : function(){
		if(!this.win){
		    this.win = new Ext.Window({
		    	title: '请选择...',
		    	frame : true,
				width : this.width,
				height: this.height,
				closeAction : 'hide',
				layout: 'border',
				items:[this.getDateGrid()],
				tbar:this.getTbar(),
				buttons: [
					{text: '确定',handler:this.doCallBack,scope: this},
				    {text: '取消',handler: function(){this.win.hide();},scope: this}
				]
			
		    });
		}
		this.win.show();
	},
	doCallBack:function(){
		var record = this.dataGrid.getSelectionModel().getSelected();
		if (!record) {
		   hs.MessageHelper.info( {msg : '请选择记录后再进行操作！'});
		   return;
		}
//		this.config.callBack(record.get("customId"),record.get("customName"),record.get("customNo"));
		this.callBack.call(this.callBackScope,record);
		this.win.hide();
	},
	getDateGrid:function(){
		if(!this.dataGrid) {
			var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true });
			this.dataGrid = new Ext.grid.GridPanel({
				region : "center",
				layout : 'fit',
				store : this.getStore(),
				border:false,
				cm: new Ext.grid.ColumnModel(this.cm),
			     listeners:{
					'rowdblclick' : {fn:function(grid, rowIndex, columnIndex, e) {
						this.doCallBack();
						this.win.hide();
					},scope:this}
		         },
			     sm: sm,
			     enableColumnResize: true,
			     stripeRows:true,
			     trackMouseOver: true,
			     loadMask: {msg:'数据加载中，请稍候...'},
			     enableColLock: false,
			     bbar: new Ext.PagingToolbar({
			    	 pageSize: PAGE_SIZE,
			    	 store: this.getStore(),
			    	 displayInfo: true,
			    	 plugins: [new Ext.ux.plugins.PageComboResizer()]
			     }),
			     load:function(baseParams) {
			    	 hs.StoreHelper.beforeload(this.getStore(), baseParams);
			    	 this.getStore().load( {
			    		 params : {start : 0,limit : PAGE_SIZE}
		    	 });
		      }
			});
		}
		return this.dataGrid;
	},
	/**
	 * 下面方法留给子类重写
	 * 默认实现是从配置项中获取对应信息
	 * @returns
	 */
	getStore : function(){
		if(!this.store){
			this.store =hs.StoreFactory.getStore(this.queryUrl, this.record);
		}
		return this.store;
	},
	getRecord : function(){
		return this.record;
	},
	getCm : function(){
		return this.cm;
	},
	getTbar : function(){
		return this.tbar;
	}
};
