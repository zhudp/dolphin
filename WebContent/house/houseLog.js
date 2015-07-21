Ext.namespace("house.log");

house.log.url = {
	queryChangeLog: '/house/house!queryChangeLog.do',
	queryInspectLog: '/inspect/inspect!queryInspectLog.do',
	queryHistoryLog: '/inspect/inspect!queryHistoryLog.do'
};

house.log.changeRecord = [
	{ name:"id"}
	,{ name:"houseId"}
	,{ name:"alterType"}
	,{ name:"oldValue"}
	,{ name:"newValue"}
	,{ name:"alterTime"}
	,{ name:"alterArea"}
	,{ name:"alterReseau"}
	,{ name:"alterId"}
	,{ name:"alterName"}
];

house.log.inspectRecord = [
    { name:"id"}
    ,{ name:"inspectId"}
    ,{ name:"objectType"}
    ,{ name:"objectId"}
    ,{ name:"objectName"}
    ,{ name:"logTime"}
    ,{ name:"safeCheckId"}
    ,{ name:"checkResult"}
    ,{ name:"inspectDate"}
    ,{ name:"inspectorId"}
    ,{ name:"inspectorName"}
    ,{ name:"changeCount"}
    ,{ name:"coSafeCount"}
];

house.log.historyRecord = [
   { name:"id"}
   ,{ name:"companyId"}
   ,{ name:"companyName"}
   ,{ name:"leaveType"}
   ,{ name:"oldObjectId"}
   ,{ name:"oldObjectName"}
   ,{ name:"newObjectId"}
   ,{ name:"newObjectName"}
   ,{ name:"operateTime"}
   ,{ name:"operaterId"}
   ,{ name:"operaterName"}
   ,{ name:"operateTimeOut"}
   ,{ name:"objectType"}
];

/**
 * 企业日志查询
 * @param {} config
 * @return {}
 */
house.log.logWin = function(config) {
	this.config = config;
	this.alertTypeStore = hs.StoreFactory.getComboStore(comboJsonData["ALERT_TYPE"], false);
	this.leaveTypeStore = hs.StoreFactory.getComboStore(comboJsonData["LEAVE_TYPE"], false);
	this.houseId = "";
};

house.log.logWin.prototype = {
	getLogTab: function(){
		if(!this.tabPanel){
			var _this = this;
			var tabPanel = new Ext.TabPanel({
				activeTab : 0,
				frame: true,
				autoScroll: true,
				border : false,
				autoHeight : true,
				layoutOnTabChange : true,
				deferredRender : false,
				items: [
		 			// 企业变更日志 grid
		 			_this.getChangeLogGrid()
		 			// 企业巡查日志 grid
		 			, _this.getInspectLogGrid()
		 			// 地质历史日志 grid
		 			, _this.getHistoryLogGrid()
				]
			});
			this.tabPanel = tabPanel;
		}
		return this.tabPanel;
	}
	// 企业变更日志
	,getChangeLogGrid: function(){
		if (!this.changeGrid) {
			var _this = this;
			var gridStore = hs.StoreFactory.getStore(
					house.log.url.queryChangeLog, house.log.changeRecord);
			this.changeLogStore = gridStore;
			var grid = new Ext.grid.GridPanel({
                store: gridStore,
                title: '变更日志',
                autoScroll: true,
                height: 339,
                cm: new Ext.grid.ColumnModel([ 
					new Ext.grid.RowNumberer()
					,{header : "ID",dataIndex : 'id',width : 60, hidden: true }
					,{header : "操作类别",dataIndex : 'alterType',width : 60, align: 'center',
						renderer: dolphin.resource.Util.render(_this.alertTypeStore) }
					,{header : "修改前内容",dataIndex : 'oldValue',width : 188, autoHeight: true,
						renderer:function(value, meta, record) {   
                            meta.attr = 'style="white-space:normal;"';    
                            return value;
                       }}
					,{header : "修改后内容",dataIndex : 'newValue',width : 188, autoHeight: true,
						renderer:function(value, meta, record) {   
                            meta.attr = 'style="white-space:normal;"';    
                            return value;
                       }}
					,{header : "操作员",dataIndex : 'alterName',width : 80, align: 'center' }
					,{header : "修改时间",dataIndex : 'alterTime',width : 100 }
					,{header : "企业ID",dataIndex : 'houseId',width : 60 , hidden: true }
					,{header : "企业名称",dataIndex : 'houseName',width : 60, hidden: true }
					,{header : "操作员ID",dataIndex : 'alterId',width : 60, hidden: true }
				]),
                enableColumnHide: false,
				enableColumnMove: false,
				enableColumnResize: true,
				enableHdMenu: false,
				enableRowHeightSync: false,
				monitorWindowResize: false,
		        loadMask: {msg:'数据加载中，请稍候...'},
		        enableColLock: false,
				stripeRows:true,
				trackMouseOver: true,
		        bbar: new Ext.PagingToolbar({
		            pageSize: PAGE_SIZE,
		            store: gridStore,
		            displayInfo: true,
		            plugins: [
		            	new Ext.ux.plugins.PageComboResizer()
		            ]
		        }),
		        tbar: [
					{
						text : "查看全部",
						iconCls : 'hs-button-view',
						id: 'showAllChangeLog',
						disabled: true,
						handler : function() {
							grid.load({houseId: _this.houseId});
							this.disable();
						}
					}
		        ],
		        load: function(baseParams) {
		        	hs.StoreHelper.beforeload(gridStore, baseParams);
					gridStore.load({
						params: {
							start: 0,
							limit: PAGE_SIZE
						}
					});
				}
            });
			this.changeGrid = grid;
		}
		return this.changeGrid;
	}
	// 企业巡查日志
	,getInspectLogGrid: function(){
		var _this = this;
		if (!this.inspectLogGrid) {
			var gridStore = hs.StoreFactory.getStore(
					house.log.url.queryInspectLog, house.log.inspectRecord);
			gridStore.on('load', function() {
				var arr = Ext.query('a[class="link"]');//获得所有a标签 这里得到一个Array数组  
			    for(var i = 0;i < arr.length; i++){  
			        var id = Ext.id(); 
			        arr[i].id = id;  
			        var value = arr[i].attributes['value'].value;
			        if(value == 'showChange'){
			            Ext.get(id).on('click',function(){
			            	_this.showChangeList();
			            });  
			        }  
			    }});
			this.inspectLogStore = gridStore;
			var grid = new Ext.grid.GridPanel({
                store: gridStore,
                autoScroll: true,
                title: '巡查日志',
                border: false,
                height: 339,
                cm: new Ext.grid.ColumnModel([ 
					new Ext.grid.RowNumberer()
					,{header : "巡查日期",dataIndex : 'inspectDate',width : 70 , hidden: true}
					,{header : "巡查时间",dataIndex : 'logTime',width : 120, align: 'center' }
					,{header : "检查员",dataIndex : 'inspectorName',width : 80, align: 'center' }
					,{header : "关联更变日志",dataIndex : 'changeCount',width : 90, align: 'center',
						renderer: function(value, meta, record){
							if(value!= 0){
								return '<a href="#" title="有'+value+'次变更！" value="showChange" class="link">'
									+ '查看（<font style="color:red">'+value+'</font>）</a>';
							}
							else{
								return '<font style="color: #646464">无变更</font>';
							}
						}}
					,{header : "检查员ID",dataIndex : 'inspectorId',width : 80, hidden: true }
					,{header : "检查结果",dataIndex : 'checkResult',width : 60, hidden: true }
					,{header : "ID",dataIndex : 'id',width : 60, hidden: true }
					,{header : "inspectId", dataIndex : 'inspectId',width : 60, hidden: true }
					,{header : "巡查主ID（关联巡查主表ID）",dataIndex : 'inspectId',width : 60, hidden: true }
					,{header : "巡查对象类型（1房屋、2人、3企业）",dataIndex : 'objectType',width : 60, hidden: true }
					,{header : "巡查对象ID",dataIndex : 'objectId',width : 60, hidden: true }
					,{header : "巡查对象名称",dataIndex : 'objectName',width : 60, hidden: true }
				]),
                enableColumnHide: false,
				enableColumnMove: false,
				enableColumnResize: true,
				enableHdMenu: false,
				enableRowHeightSync: false,
				monitorWindowResize: false,
				stripeRows:true,
				trackMouseOver: true,
				loadMask: {msg:'数据加载中，请稍候...'},
		        enableColLock: false,
		        bbar: new Ext.PagingToolbar({
		            pageSize: PAGE_SIZE,
		            store: gridStore,
		            displayInfo: true,
		            plugins: [
		            	new Ext.ux.plugins.PageComboResizer()
		            ]
		        }),
		        load: function(baseParams) {
		        	hs.StoreHelper.beforeload(gridStore, baseParams);
					gridStore.load({
						params: {
							start: 0,
							limit: PAGE_SIZE
						}
					});
				}
            });
			this.inspectLogGrid = grid;
		}
		return this.inspectLogGrid;
	}
	// 地址历史日志
	,getHistoryLogGrid: function(){
		if (!this.historyLogGrid) {
//			var _this = this;
			var gridStore = hs.StoreFactory.getStore(
					house.log.url.queryHistoryLog, house.log.historyRecord);
			this.historyLogStore = gridStore;
			var grid = new Ext.grid.GridPanel({
                store: gridStore,
                autoScroll: true,
                title: '入住历史',
                border: false,
                height: 339,
                cm: new Ext.grid.ColumnModel([ 
					new Ext.grid.RowNumberer()
					,{header : "id",dataIndex : 'id',width : 20 , hidden: true}
					,{header : "companyId",dataIndex : 'companyId',width : 20 , hidden: true}
					,{header : "名称",dataIndex : 'companyName',width : 160, align: 'left',
						renderer: function(value, meta, record){
							if(record.data.objectType == "1"){
								return '<a href="javascript:void(0)" onClick=showCompanyCard(\"'+record.data.companyId+'\") >'+record.data.companyName+' </a>';
							}else{
								return '<a href="javascript:void(0)" onClick=showPeopleCard(\"'+record.data.companyId+'\") >'+record.data.companyName+' </a>';
							}
						}}
//					,{header : "操作时间",dataIndex : 'operateTime',width : 100, align: 'center'}
//					,{header : "搬迁",dataIndex : 'leaveType',width : 80, align: 'left',
//						renderer: dolphin.resource.Util.render(_this.leaveTypeStore) }
					,{header : "oldObjectId",dataIndex : 'oldObjectId',width : 20, hidden: true }
					,{header : "newObjectId",dataIndex : 'newObjectId',width : 20, hidden: true }
					,{header : "搬入时间",dataIndex : 'operateTime',width : 100, align:'left' }
					,{header : "搬出时间",dataIndex : 'operateTimeOut',width : 100, align:'left' }
//					,{header : "旧地址",dataIndex : 'oldObjectName',width : 100, align:'left' }
//					,{header : "新地址",dataIndex : 'newObjectName',width : 100, align:'left' }
					,{header : "operaterId",dataIndex : 'operaterId',width : 20, hidden: true }
					,{header : "操作员",dataIndex : 'operaterName',width : 60, align: 'left' }
				]),
                enableColumnHide: false,
				enableColumnMove: false,
				enableColumnResize: true,
				enableHdMenu: false,
				enableRowHeightSync: false,
				monitorWindowResize: false,
				stripeRows:true,
				trackMouseOver: true,
				loadMask: {msg:'数据加载中，请稍候...'},
		        enableColLock: false,
		        bbar: new Ext.PagingToolbar({
		            pageSize: PAGE_SIZE,
		            store: gridStore,
		            displayInfo: true,
		            plugins: [
		            	new Ext.ux.plugins.PageComboResizer()
		            ]
		        }),
		        load: function(baseParams) {
		        	hs.StoreHelper.beforeload(gridStore, baseParams);
					gridStore.load({
						params: {
							start: 0,
							limit: PAGE_SIZE
						}
					});
				}
            });
			this.historyLogGrid = grid;
		}
		return this.historyLogGrid;
	}
	,getWin : function() {
		if (!this.win) {
			var tabPanel = this.getLogTab();
			var win = new Ext.Window({
				frame : true,
				autoScroll: true,
				width : 700,
				height : 400,
				closeAction : 'hide',
				items : tabPanel
			});
			
			win.addListener("beforeshow", function() {
				tabPanel.setActiveTab(0);
			});
			this.win = win;
		}
		return this.win;
	}
	,show: function(houseId, address){
		this.getWin().show();
		this.getWin().setTitle(address);
		this.load(houseId);
	}
	// 加载数据
	,load : function(houseId) {
		// 加载变更日志
		this.getChangeLogGrid().load({houseId: houseId});
		
		// 加载巡查日志
		this.getInspectLogGrid().load({objectId: houseId, objectType: '1'});
		
		// 加载历史日志
		this.getHistoryLogGrid().load({houseId: houseId});
		
		// 保存houseId数据
		this.houseId = houseId;
	}
	// 查看修改内容列表
	,showChangeList: function(){
		var record = this.getInspectLogGrid().getSelectionModel().getSelected();
		this.getChangeLogGrid().load({houseId: record.get('objectId'), inspectId: record.get('inspectId')});
		this.getLogTab().setActiveTab(0);
		Ext.getCmp('showAllChangeLog').enable();
	}
};
var companyCard,peopleCard;
/**
 * 显示企业卡片
 * @param companyId
 * @param companyName
 */
function showCompanyCard(companyId,companyName) {
	if(!companyCard) {
		companyCard = new company.card.infoWin();
	}
	companyCard.show(companyId, companyName);
}

/**
 * 显示人员卡片
 * @param peopleId
 */
function showPeopleCard(peopleId) {
	if(!peopleCard) {
		peopleCard = new people.detail.DetailPanel({});
	}
	peopleCard.load(peopleId);
	peopleCard.getWin().show();
}