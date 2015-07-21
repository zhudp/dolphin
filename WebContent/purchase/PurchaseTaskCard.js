Ext.namespace("PurchaseTask.card");
PurchaseTask.card.Win = function(config) {
	PurchaseTask.card.Win.superclass.constructor.call(this, config);
	this.url.get = '/purchase/purchase!getPurchasesTask.do';
	this.url.save ='/purchase/purchase!savePurchaseByTask.do';
	this.url.hasTask ='/purchase/purchase!hasTask.do';
};
Ext.extend(PurchaseTask.card.Win,Commons.card.Win, {
	getWindTitle : function(data){
		return this.title;
	}
	,getLoadParam : function(_this){
		return {purchaseId: _this.id};
	}
	/**
	 * 创建表单
	 * @returns {Array}
	 */
	,createFormItem : function(){
		var defaultsCfg = {disabled:true,anchor: '95%'};
		this.purchaseNameField = new Ext.form.TextField({fieldLabel : '采购名称',id : 'purchaseName',allowBlank : false});
		this.orderDateField = new Ext.form.DateField({fieldLabel : '采购日期',id : 'orderDate',allowBlank : false});
		this.planStoreinDateField = new Ext.form.DateField({fieldLabel : '计划入库',id : 'planStoreinDate',allowBlank : false});
		this.officerField = new Ext.form.TextField({fieldLabel : '采购负责人',id : 'officer',});
		
		var items = [
		             hs.FormLayout.twoColumnedRow(this.purchaseNameField,this.officerField ,defaultsCfg),
		             hs.FormLayout.twoColumnedRow(this.orderDateField,this.planStoreinDateField,defaultsCfg)
		             ];
		return items;
	}
	,getDetailGrid :function(){
		this.purchaseTaskDetail =new PurchaseTask.detail.MainPanel();
		// 反向引用
		this.purchaseTaskDetail.mainCard = this;
		return this.purchaseTaskDetail.getDetailGrid();
	}
	/**
	 * 窗口元素
	 */
	,getWinItems : function(){
		return [{frame : false,border : false,region : "north",height: 65,items:[this.getCardForm()]},
				this.getDetailGrid()
		       ];
	}
	,show: function(){
		this.getWin().show();
		this.getWin().setTitle(this.getWindTitle());
		this.load();
	}
	/**
	 * 加载数据
	 * 重写加载详情表格部分
	 */
	,load : function() {
		var _this = this;
		_this.purchaseTaskDetail.getDetailGrid().getStore().load();
	}
});
