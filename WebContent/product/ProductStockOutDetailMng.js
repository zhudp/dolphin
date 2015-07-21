Ext.namespace("ProductStockOutDetail.manage");
ProductStockOutDetail.manage.MainPanel = function(config) {
	ProductStockOutDetail.manage.MainPanel.superclass.constructor.call(this, config);
	this.stockOutId = config.stockOutId;
	this.url.query = '/product/stockout!detailQueryPaged.do';
	this.url.remove = '/product/stockout!deleteDetail.do';
	this.url.get = '/product/stockout!getProductStockOutDetail.do';
	this.url.queryPaged = '/product/stockout!detailQueryPaged.do';
};

Ext.extend(ProductStockOutDetail.manage.MainPanel,Common.manage.MainPanel,{
	getGridRecord : function(){
		return [
		        { name:"detailId"},
		        { name:"stockOutId"},
		        { name:"productId"},
		        { name:"productName"},
		        { name:"productNo"},
		        { name:"standard"},
		        { name:"unit"},
		        { name:"boxNo"},
		        { name:"quantity"},
		        { name:"isDeleted"},
		        { name:"gmtCreate"},
		        { name:"creator"},
		        { name:"creatorId"},
		        { name:"gmtModify"},
		        { name:"modifier"},
		        { name:"modifierId"}
	    		];
	}
	,getGridCm : function(){
		var sm =new Ext.grid.CheckboxSelectionModel({singleSelect : true });
		var cm =new Ext.grid.ColumnModel([ 
			new Ext.grid.RowNumberer(),
			sm,
			{header : "id",dataIndex : 'detailId',width : 60,hidden:true },
			{header : "出库id",dataIndex : 'stockOutId',width : 60,hidden:true },
			{header : "产品id",dataIndex : 'productId',width : 60,hidden:true },
			{header : "产品编号",dataIndex : 'productNo',width : 60 },
			{header : "产品",dataIndex : 'productName',width : 60 },
			{header : "规格",dataIndex : 'standard',width : 60 },
			{header : "单位",dataIndex : 'unit',width : 60 },
			{header : "箱号",dataIndex : 'boxNo',width : 60 },
			{header : "数量",dataIndex : 'quantity',width : 60 },
			{header : "创建时间",dataIndex : 'gmtCreate',width : 60,hidden:true },
			{header : "创建人",dataIndex : 'creator',width : 60,hidden:true },
			{header : "创建人编号",dataIndex : 'creatorId',width : 60,hidden:true },
			{header : "修改时间",dataIndex : 'gmtModify',width : 60,hidden:true },
			{header : "修改人",dataIndex : 'modifier',width : 60,hidden:true },
			{header : "修改人编号",dataIndex : 'modifierId',width : 60,hidden:true }
		]);
		return cm;
	}
	,getCardWin : function(){
		var cardWin = new ProductStockOutDetail.card.Win({width : 480,height: 200,title:"产品出库详情",callbackClose:true});
		cardWin.setCallback(this.searchFn);
		cardWin.setMainPanel(this);
		this.setCardWin(cardWin);
		return this.cardWin;
	}
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
