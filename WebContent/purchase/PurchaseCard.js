Ext.namespace("Purchase.card");
Purchase.card.Win = function(config) {
	Purchase.card.Win.superclass.constructor.call(this, config);
	this.url.get = '/purchase/purchase!getPurchase.do';
	this.url.save ='/purchase/purchase!savePurchase.do';
};
Ext.extend(Purchase.card.Win,Commons.card.Win, {
	getWindTitle : function(data){
		if(data){
			return this.title +"："+data.purchaseNo;
		}else{
			return this.title;
		}
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
		var oneClmCfg = {disabled:true,anchor: '97%'};
		this.purchaseIdField =  new  Ext.form.Hidden({fieldLabel : 'id',name: 'purchaseId',autoFill:true});
		this.supplierIdField = new Ext.form.Hidden({fieldLabel : '供应商ID',id : 'supplierId',autoFill:true});
		this.purchaseNoField = new Ext.form.TextField({fieldLabel : '采购编号',id : 'purchaseNo',autoFill:true,emptyText:'自动编号'});
		this.purchaseNameField = new Ext.form.TextField({fieldLabel : '采购单名称',id : 'purchaseName',allowBlank : false});
		this.orderDateField = new Ext.form.DateField({fieldLabel : '采购日期',id : 'orderDate',allowBlank : false});
		this.planStoreinDateField = new Ext.form.DateField({fieldLabel : '计划入库',id : 'planStoreinDate'});
		this.realStoreinDateField = new Ext.form.DateField({fieldLabel : '入库日期',id : 'realStoreinDate'});
		this.totalAmountField = new Ext.form.TextField({fieldLabel : '总金额',id : 'totalAmount',autoFill:true});
		this.officerField = new Ext.form.TextField({fieldLabel : '采购负责人',id : 'officer',});
		this.purchaseTypeField = new Ext.form.ComboBox({fieldLabel : '采购类别',hiddenName : 'purchaseType',displayField: 'text'
			,store: hs.StoreFactory.getComboStore(comboJsonData["PURCHASE_TYPE"])});
		this.statusField = new Ext.form.ComboBox({fieldLabel : '状态',hiddenName : 'status',displayField: 'text',
			store: hs.StoreFactory.getComboStore(comboJsonData["PURCHASE_STATUS"])});
		this.remarkField =  new  Ext.form.TextArea({fieldLabel : '备注',name : 'remark',height:30,anchor: '95%'});
		
//		this.supplierNameField = new Ext.form.TextField({fieldLabel : '供应商名称',id : 'supplierName'});
		// 选择填充
		var _this =this;
		this.supplierNameField = new Ext.ux.form.SearchField({
			name:'supplierName',
			fieldLabel:'供应商名称',
			doSearch:function(){
				var supplierChooser = new SupplierChooser({
					scope:_this,
					callBack:function(record){
						this.supplierIdField.setValue(record.get('supplierId'));
						this.supplierNameField.setValue(record.get('supplierName'));
			    	}
				});
				supplierChooser.show();
			}
		});
		
		var items = [this.purchaseIdField,this.supplierIdField,
		             hs.FormLayout.threeColumnedRow(this.purchaseNoField,this.purchaseNameField,this.orderDateField ,defaultsCfg),
		             hs.FormLayout.threeColumnedRow(this.purchaseTypeField,this.statusField,this.officerField,defaultsCfg),
		             hs.FormLayout.threeColumnedRow(this.supplierNameField,this.planStoreinDateField,this.realStoreinDateField,defaultsCfg),
		             hs.FormLayout.twoColumnedRow(this.totalAmountField,this.remarkField,defaultsCfg)
		             ];
		return items;
	}
	,getDetailGrid :function(){
		var _this =this;
		//明细grid
		this.purchaseDetail =new PurchaseDetail.manage.MainPanel({recordIdName:"detailId"});
		this.purchaseDetail.setStockCard(_this);
		return this.purchaseDetail.getCenterGrid();
	}
	/**
	 * 窗口元素
	 */
	,getWinItems : function(){
		return [{frame : false,border : false,region : "north",height: 115,items:[this.getCardForm()]},
				this.getDetailGrid()
		       ];
	}
	/**
	 * 加载数据
	 * 重写加载详情表格部分
	 */
	,load : function() {
		var _this = this;
		var formPanel = _this.getCardForm();
		hs.FormHelper.load(this.url.get,this.getLoadParam(_this), function(form, action) {
			var data = Ext.util.JSON.decode(action.result.data);
			form.clearInvalid();
			form.setValues(data);
			_this.getWin().setTitle(_this.getWindTitle(data));
		}, formPanel.getForm());
		//加载详情grid数据
		_this.purchaseDetail.searchFn(_this.purchaseDetail);
	}
});
