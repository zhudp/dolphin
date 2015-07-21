Ext.namespace("ProductStockIn.card");
/**
 * 产品入库编辑/添加窗口
 * @param config{}
 * @returns {ProductStockIn.card.Win}
 */
ProductStockIn.card.Win = function(config) {
	ProductStockIn.card.Win.superclass.constructor.call(this, config);
	this.url.get = '/product/stockin!getProductStockIn.do';
	this.url.save ='/product/stockin!saveProductStockIn.do';
};
/**
 * 基于common.card.Win的编辑窗口
 */
Ext.extend(ProductStockIn.card.Win,Commons.card.Win, {
	/**
	 * 窗口标题获取
	 * @param data
	 * @returns
	 */
	getWindTitle : function(data){
		return this.title;
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
		var _this =this;
		// 表单作用域在该组件，便于外部引用
		this.stockInIdField =  new  Ext.form.Hidden({fieldLabel : 'id',name: 'stockInId',autoFill:true});
		this.orderIdField =  new  Ext.form.Hidden({fieldLabel : '订单id',name : 'orderId',autoFill:true});
		this.stockInNoField =  new  Ext.form.TextField({fieldLabel : '编号',name: 'stockInNo',emptyText:'自动编号',autoFill:true});
		this.ordersDateField =  new  Ext.form.DateField({fieldLabel : '下单日期',name : 'ordersDate',editable:false,format:"Y-m-d",emptyText:"请选择..."});
		this.stockInDateField =  new  Ext.form.DateField({fieldLabel : '入库日期',name : 'stockInDate',editable:false,format:"Y-m-d",emptyText:"请选择..."});
		this.officerField =  new  Ext.form.TextField({fieldLabel : '经办人',name : 'officer',allowBlank : false});
		this.remarkField =  new  Ext.form.TextArea({fieldLabel : '备注',name : 'remark',height:30,anchor: '95%'});
		
		this.orderNoField = new Ext.ux.form.SearchField({
			name:'orderNo',
			fieldLabel:'订单号',
			doSearch:function(){
				var orderChooser = new OrderChooser({
					scope:_this,
					callBack:function(record){
						this.orderIdField.setValue(record.get('orderId'));
						this.orderNoField.setValue(record.get('orderNo'));
					}
				});
				orderChooser.show();
			}
		});
		
		var items = [this.stockInIdField,this.orderIdField,
		             hs.FormLayout.twoColumnedRow(this.stockInNoField,this.orderNoField,defaultsCfg),
		             hs.FormLayout.twoColumnedRow(this.ordersDateField,this.stockInDateField,defaultsCfg),
		             hs.FormLayout.twoColumnedRow(this.officerField,this.remarkField,defaultsCfg)
		             ];
		return items;
	}
	,getDetailGrid :function(){
		var _this =this;
		//明细grid
		this.productStockInDetail =new ProductStockInDetail.manage.MainPanel({recordIdName:"detailId"});
		this.productStockInDetail.setStockCard(_this);
		return this.productStockInDetail.getCenterGrid();
	}
	/**
	 * 窗口元素
	 */
	,getWinItems : function(){
		return [{frame : false,border : false,region : "north",height: 100,items:[this.getCardForm()]},
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
		_this.productStockInDetail.searchFn(_this.productStockInDetail);
	}
});
