Ext.namespace("MaterialStockOutDetail.manage");
MaterialStockOutDetail.manage.MainPanel = function(config) {
	MaterialStockOutDetail.manage.MainPanel.superclass.constructor.call(this, config);
	this.stockOutId = config.stockOutId;
	this.url.query = '/stockout/stockout!detailQueryPaged.do';
	this.url.remove = '/stockout/stockout!deleteDetail.do';
	this.url.get = '/stockout/stockout!getMaterialStockOutDetail.do';
	this.url.queryPaged = '/stockout/stockout!detailQueryPaged.do';
};

Ext.extend(MaterialStockOutDetail.manage.MainPanel,Common.manage.MainPanel,{
	getGridRecord : function(){
		return [
		        { name:"detailId"},
		        { name:"stockOutId"},
		        { name:"materialId"},
		        { name:"materialName"},
		        { name:"materialNo"},
		        { name:"standard"},
		        { name:"unit"},
		        { name:"price"},
		        { name:"quantity"},
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
			{header : "出库id",dataIndex : 'stockOutId',width : 60 },
			{header : "原料id",dataIndex : 'materialId',width : 60,hidden:true },
			{header : "原料编号",dataIndex : 'materialNo',width : 60 },
			{header : "原料",dataIndex : 'materialName',width : 60 },
			{header : "规格",dataIndex : 'standard',width : 60 },
			{header : "单位",dataIndex : 'unit',width : 60 },
			{header : "价格",dataIndex : 'price',width : 60 },
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
		var cardWin = new MaterialStockOutDetail.card.Win({width : 480,height: 200,title:"原料出库详情",callbackClose:true});
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
