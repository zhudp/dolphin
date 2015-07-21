Ext.namespace("Common.manage");
/**
 * 公用主面板
 * @param {} config
 * @return {}
 */
Common.manage.MainPanel = function(config) {
	this.config = config;
	if(Ext.isObject(config)){
		if(Ext.isDefined(config.cardWin)){
			this.cardWin = config.cardWin;
		}
		if(Ext.isDefined(config.recordIdName)){
			this.recordIdName = config.recordIdName;
		}
	}
	if(!Ext.isDefined(this.url)){
		this.url={};
	}
};
Common.manage.MainPanel.prototype = {
	/**
	 * 以viewPort方式显示主界面
	 */
	showViewPort: function(){
		new Ext.Viewport({
				layout : "border",
				margins : '0 0 0 0',
				defaults : {
					border : false
				},
				items : [this.getTopBar(), this.getCenterGrid()]
			});
		this.searchFn(this);
	}
	/**
	 * 以窗口形式显示
	 */
	,showWindow : function(){
		var width ;
		var height;
		var config = this.config;
		if(Ext.isObject(config) && Ext.isNumber(config.width)){
			width = config.width;
		}else{
			width =500;
		}
		if(Ext.isObject(config) && Ext.isNumber(config.height)){
			height = config.height;
		}else{
			height = 400;
		}
		var win = new Ext.Window({
			frame : true,
			width : width,
			height: height,
			defaults : {border : false},
			margins : '0 0 0 0',
			closeAction : 'close',
			layout: 'border',
			items:[this.getTopBar(), this.getCenterGrid()]
		});
		win.show();
		this.searchFn(this);
	}
	/**
	 * 获取查询面板，该方法由子类实现
	 */
	,getTopBar: function(){
		var toolbar = new Ext.Panel({//默认建立一个空白面板
			frame : false,
			border : false,
			region : "north",
			height: 5
		});
		return toolbar;
	}
	/**
	 * 提供搜索条件，该方法由子类实现
	 */
	,getSearchParm : function(){
		return {};
	}
	/**
	 * 搜索函数，其中搜索条件有getSearchParm方法提供
	 */
	,searchFn: function(_this){
		var store = _this.getCenterGrid().getStore();
		var paggingBar = _this.getCenterGrid().getBottomToolbar();
		hs.StoreHelper.beforeload(store,_this.getSearchParm() );
		store.load({
			params : {
				start : 0,
				limit : paggingBar.pageSize || PAGE_SIZE
			}
		});
	}
	/**
	 * 表单回车响应方法
	 */
	,enterHander : function (_this,field, e){
		if (e.getKey() == Ext.EventObject.ENTER) {
			_this.searchFn(_this);
		}
	}
	/**
	 * 获取grid的cm配置，子类必须重写该函数，来配置grid列
	 */
	,getGridCm : function(){
		
		var sm =new Ext.grid.CheckboxSelectionModel({singleSelect : true });
		var cm =[ 
			new Ext.grid.RowNumberer(),
			,sm
		];
		return cm;
	}
	/**
	 * 获取grid 操作栏配置，子类可以重写该方法来修改grid操作栏
	 */
	,getGridTbBar : function(_this){
		var tbbar =[
		            {
						text : "查看详情",
						iconCls : 'hs-button-view',
						scope : this,
						handler : function() {
							_this.showCard();
						}
					},'-', {
						text : "添加",
						id:'010101',
						iconCls : 'hs-button-add',
						scope : this,
						handler : function() {
							_this.showAddWin();
						}
					},'-', {
						text : "删除",
						iconCls : 'hs-button-remove',
						scope : this,
						handler : function() {
							var record = this.getSelected();
							if(!record){
								return;
							}
							var _this = this;
							var idParam = {};
							idParam[_this.recordIdName] = record.get(_this.recordIdName);
							hs.MessageHelper.confirm('确定要删除？', function() {
								hs.ActionHelper.request(
										_this.url.remove//配置项
										,idParam
										,function(){_this.searchFn(_this);}
								);
							});
						}
					}
				];
		
		return _this.getExtendGridTbBar(_this,tbbar);
	}
	/**
	 * 扩展gridTbBar，包括添加新按钮到bar中
	 */
	,getExtendGridTbBar:function(_this,tbbar){
		return tbbar;
	}
	/**
	 * grid Record 子类实现
	 */
	,getGridRecord : function(){return [];}
	/**
	 * 获取grid
	 */
	,getCenterGrid: function(){
		if (!this.mngGrid) {
			var _this = this;
			var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true });
			var gridStore = hs.StoreFactory.getStore(_this.url.query, _this.getGridRecord());
			var grid = new Ext.grid.GridPanel({
				region : "center",
				layout : 'fit',
                store: gridStore,
                cm: _this.getGridCm(),
                sm: sm,
				enableColumnResize: true,
				stripeRows:true,
				trackMouseOver: true,
		        loadMask: {msg:'数据加载中，请稍候...'},
		        enableColLock: false,
		        bbar: new Ext.PagingToolbar({
		            pageSize: PAGE_SIZE,
		            store: gridStore,
		            displayInfo: true,
		            plugins: [new Ext.ux.plugins.PageComboResizer()]
		        }),
	        	tbar:_this.getGridTbBar(_this),
		        load: function(baseParams) {
		        	hs.StoreHelper.beforeload(gridStore, baseParams);
					gridStore.load({params: {start: 0,limit: PAGE_SIZE}});
				}
            });
			grid.addListener('rowdblclick', function(){_this.showCard();});
			this.mngGrid = grid;
		}
		return this.mngGrid;
	}
	/**
	 * 获取已经选择的grid记录，只支持单选
	 */
	,getSelected: function(){
		var records = this.getCenterGrid().getSelectionModel().getSelections();
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
	/**
	 * 显示编辑窗口
	 */
	,showCard: function(){
		var record = this.getSelected();
		if(!record){
			return;
		}
		var _this = this;
		var cardWin = this.getCardWin();
		cardWin.show(record.get(_this.recordIdName));
	}
	/**
	 * 显示添加窗口
	 */
	,showAddWin: function(){
		var cardWin = this.getCardWin();
		cardWin.getCardForm().getForm().reset();
		cardWin.show();
	}
	/**
	 * 获取信息编辑窗口
	 */
	,getCardWin : function(){
		return this.cardWin;
	}
	/**
	 * 注入编辑窗口
	 */
	,setCardWin : function(cardWin){
		this.cardWin = cardWin;
	}
};