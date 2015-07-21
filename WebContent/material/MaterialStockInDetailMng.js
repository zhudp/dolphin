Ext.namespace("MaterialStockInDetail.manage");
/**
 * 原料入库管理主面板
 * @param {} config
 */
MaterialStockInDetail.manage.MainPanel = function(config) {
	MaterialStockInDetail.manage.MainPanel.superclass.constructor.call(this, config);
	this.stockInId = config.stockInId;
	this.url.query = '/stockin/stockin!detailQueryPaged.do';
	this.url.remove = '/stockin/stockin!deleteDetail.do';
	this.url.get = '/stockin/stockin!getMaterialStockInDetail.do';
	this.url.queryPaged = '/stockin/stockin!detailQueryPaged.do';
	this.url.savematerial = '/stockin/stockin!saveMaterialStockInDetail.do';
};

Ext.extend(MaterialStockInDetail.manage.MainPanel,Common.manage.MainPanel,{
	/**
	 * grid Record 用户创建grid
	 */
	getGridRecord : function(){
		return [
		        { name:"detailId"},
				{ name:"stockInId"},
				{ name:"materialId"},
				{ name:"materialName"},
				{ name:"materialNo"},
				{ name:"standard"},
				{ name:"unit"},
				{ name:"price"},
				{ name:"planQuantity"},
				{ name:"realQuantity"},
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
			{header : "详情id",dataIndex : 'detailId',width : 60,hidden:true },
			{header : "入库id",dataIndex : 'stockInId',width : 60,hidden:true },
			{header : "原料id",dataIndex : 'materialId',width : 60,hidden:true },
			{header : "原料名",dataIndex : 'materialName',width : 60 },
			{header : "原料编号",dataIndex : 'materialNo',width : 60 },
			{header : "规格",dataIndex : 'standard',width : 60 },
			{header : "单位",dataIndex : 'unit',width : 60 },
			{header : "价格",dataIndex : 'price',width : 60 },
			{header : "计划数量",dataIndex : 'planQuantity',width : 60 },
			{header : "实际数量",dataIndex : 'realQuantity',width : 60 },
			{header : "创建时间",dataIndex : 'gmtCreate',width : 60,hidden:true },
			{header : "创建人",dataIndex : 'creator',width : 60,hidden:true },
			{header : "创建人编号",dataIndex : 'creatorId',width : 60,hidden:true },
			{header : "修改时间",dataIndex : 'gmtModify',width : 60,hidden:true },
			{header : "修改人",dataIndex : 'modifier',width : 60,hidden:true },
			{header : "修改人编号",dataIndex : 'modifierId',width : 60,hidden:true }
		]);
		return cm;
	}
	/**
	 * 获取信息编辑窗口
	 */
	,getCardWin : function(){
		var cardWin = new MaterialStockInDetail.card.Win({width : 480,height: 200,title:"原料入库详情",callbackClose:true});
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
