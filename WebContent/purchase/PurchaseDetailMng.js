Ext.namespace("PurchaseDetail.manage");
PurchaseDetail.manage.MainPanel = function(config) {
	PurchaseDetail.manage.MainPanel.superclass.constructor.call(this, config);
	this.purchaseId = config.purchaseId;
	this.url.query = '/purchase/purchase!detailQueryPaged.do';
	this.url.remove = '/purchase/purchase!deleteDetail.do';
	this.url.get = '/purchase/purchase!getPurchaseDetail.do';
	this.url.queryPaged = '/purchase/purchase!detailQueryPaged.do';
};

Ext.extend(PurchaseDetail.manage.MainPanel,Common.manage.MainPanel,{
	/**
	 * grid Record 用户创建grid
	 */
	getGridRecord : function(){
		return [
				{ name:"detailId"},
				{ name:"materialName"},
				{ name:"standard"},
				{ name:"materialNo"},
				{ name:"unit"},
				{ name:"planNumber"},
				{ name:"storeinNumber"},
				{ name:"remark"},
				{ name:"isDeleted"}
	    		];
	}
	,getGridCm : function(){
		var sm =new Ext.grid.CheckboxSelectionModel({singleSelect : true });
		var cm =new Ext.grid.ColumnModel([ 
			new Ext.grid.RowNumberer(),
			sm,
			{header : "id",dataIndex : 'detailId',width : 60,hidden:true  },
			{header : "原材料名称",dataIndex : 'materialName',width : 100 },
			{header : "原材料规格",dataIndex : 'standard',width : 100 },
			{header : "原材料编号",dataIndex : 'materialNo',width : 100 },
			{header : "产品单位",dataIndex : 'unit',width : 100 },
			{header : "计划数量",dataIndex : 'planNumber',width : 60 },
			{header : "实际数量",dataIndex : 'storeinNumber',width : 60 },
			{header : "备注",dataIndex : 'remark',width : 60 }
		]);
		return cm;
	}
	/**
	 * 获取信息编辑窗口
	 */
	,getCardWin : function(){
		var cardWin = new PurchaseDetail.card.Win({width : 480,height: 230,title:"采购详情",callbackClose:true});
		cardWin.setCallback(this.searchFn);
		cardWin.setMainPanel(this);
		this.setCardWin(cardWin);
		return this.cardWin;
	}
	/**
	 * 提供搜索条件
	 */
	,getSearchParm : function(){
		if(Ext.isObject(this.stockCard.getLoadParam(this.stockCard))){
			return this.stockCard.getLoadParam(this.stockCard);
		}else{
			return {};
		}
	}
	,setStockCard :function(parm){
		this.stockCard =parm;
	}
});
