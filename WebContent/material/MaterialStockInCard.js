Ext.namespace("MaterialStockIn.card");
/**
 * 原料入库编辑/添加窗口
 * @param config{}
 * @returns {MaterialStockIn.card.Win}
 */
MaterialStockIn.card.Win = function(config) {
	MaterialStockIn.card.Win.superclass.constructor.call(this, config);
	this.url.get = '/stockin/stockin!getMaterialStockIn.do';
	this.url.save ='/stockin/stockin!saveMaterialStockIn.do';
};
/**
 * 基于common.card.Win的编辑窗口
 */
Ext.extend(MaterialStockIn.card.Win,Commons.card.Win, {
	/**
	 * 窗口标题获取
	 * @param data
	 * @returns
	 */
	getWindTitle : function(data){
		if(data){
			return this.title +"："+data.materialStockInNo;
		}else{
			return this.title;
		}
	}
	/**
	 * 表单数据加载参数
	 */
	,getLoadParam : function(_this){
		return {stockInId: _this.id};
	}
	/**
	 * 创建表单
	 * @returns {Array}
	 */
	,createFormItem : function(){
		var defaultsCfg = {disabled:true,anchor: '95%'};
		var oneClmCfg = {disabled:true,anchor: '97%'};
		// 表单作用域在该组件，便于外部引用
		this.stockInIdField =  new  Ext.form.Hidden({fieldLabel : 'id',name: 'stockInId',autoFill:true});
		this.orderIdField =  new  Ext.form.Hidden({fieldLabel : '订单id',name : 'orderId',autoFill:true});
		this.supplierIdField =  new  Ext.form.Hidden({fieldLabel : '供应商id',name : 'supplierId',autoFill:true});
		this.stockInNoField =  new  Ext.form.TextField({fieldLabel : '编号',name: 'stockInNo',emptyText:'自动编号',autoFill:true});
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
		
		this.supplierNoField =  new  Ext.form.TextField({fieldLabel : '供应商编号',name : 'supplierNo',allowBlank : false});
		this.supplierOfficerField =  new  Ext.form.TextField({fieldLabel : '负责人',name : 'supplierOfficer',allowBlank : false});
		this.purchaseOfficerField =  new  Ext.form.TextField({fieldLabel : '采购员',name : 'purchaseOfficer',allowBlank : false});
		
		this.purchaseDateField =  new  Ext.form.DateField({fieldLabel : '采购日期',name : 'purchaseDate',editable:false,format:"Y-m-d",emptyText:"请选择..."});
		this.ordersDateField =  new  Ext.form.DateField({fieldLabel : '下单日期',name : 'ordersDate',editable:false,format:"Y-m-d",emptyText:"请选择..."});
		this.stockInDateField =  new  Ext.form.DateField({fieldLabel : '入库日期',name : 'stockInDate',editable:false,format:"Y-m-d",emptyText:"请选择..."});
		this.remarkField =  new  Ext.form.TextArea({fieldLabel : '备注',name : 'remark',height:30,anchor: '95%'});
		var items = [this.stockInIdField,this.stockInNoField,this.orderIdField,this.supplierIdField,
		             hs.FormLayout.threeColumnedRow(this.stockInNoField,this.orderNoField,this.purchaseOfficerField,defaultsCfg),
		             hs.FormLayout.threeColumnedRow(this.supplierNoField,this.supplierNameField,this.supplierOfficerField,defaultsCfg),
		             hs.FormLayout.threeColumnedRow(this.purchaseDateField,this.ordersDateField,this.stockInDateField,defaultsCfg),
		             hs.FormLayout.wholeOneColumnedRow(this.remarkField,oneClmCfg)
		             ];
		return items;
	}
	,getDetailGrid :function(){
		var _this =this;
		//明细grid
		this.materialStockInDetail =new MaterialStockInDetail.manage.MainPanel({recordIdName:"detailId"});
		this.materialStockInDetail.setStockCard(_this);
		return this.materialStockInDetail.getCenterGrid();
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
		_this.materialStockInDetail.searchFn(_this.materialStockInDetail);
	}
	/**
	 * 根据订单信息填充表单，供选择组件回调
	 */
	,loadOrder:function(_this,orderInf){
	}
});
