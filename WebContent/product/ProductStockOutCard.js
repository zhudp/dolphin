Ext.namespace("ProductStockOut.card");
ProductStockOut.card.Win = function(config) {
	ProductStockOut.card.Win.superclass.constructor.call(this, config);
	this.url.get = '/product/stockout!getProductStockOut.do';
	this.url.save ='/product/stockout!saveProductStockOut.do';
};
Ext.extend(ProductStockOut.card.Win,Commons.card.Win, {
	getWindTitle : function(data){
		if(data){
			return this.title +"："+data.stockOutNo;
		}else{
			return this.title;
		}
	}
	/**
	 * 表单数据加载参数
	 */
	,getLoadParam : function(_this){
		return {stockOutId: _this.id};
	}
	,createFormItem : function(){
		var defaultsCfg = {disabled:true,anchor: '95%'};
		var oneClmCfg = {disabled:true,anchor: '97%'};
		// 表单作用域在该组件，便于外部引用
		this.stockOutIdField =  new  Ext.form.Hidden({name: 'stockOutId',autoFill:true});
		this.orderIdField = new Ext.form.Hidden({id : 'orderId',autoFill:true});
		this.stockOutNoField = new Ext.form.TextField({fieldLabel : '出库编号',id : 'stockOutNo',autoFill:true});
		this.orderNoField = new Ext.form.TextField({fieldLabel : '订单号',id : 'orderNo',allowBlank : false});
		this.officerField = new Ext.form.TextField({fieldLabel : '负责人',id : 'officer',allowBlank : false});
		this.stockOutDateField =  new  Ext.form.DateField({fieldLabel : '出库日期',name : 'stockOutDate',editable:false,format:"Y-m-d",emptyText:"请选择..."});
		this.remarkField =  new  Ext.form.TextArea({fieldLabel : '备注',name : 'remark',height:30,anchor: '95%'});
		//search 订单编号
		
		var _this = this;
		this.orderNoField = new Ext.ux.form.SearchField({
			name:'orderNo',
			fieldLabel:'订单号',
			doSearch:function(){
				var orderChooser = new OrderChooser({//TODO:订单选择器
					scope:_this,
					callBack:function(record){
						this.orderIdField.setValue(record.get('orderId'));
						this.orderNoField.setValue(record.get('orderNo'));
					}
				});
				orderChooser.show();
			}
		});
		
		var items = [this.stockOutIdField,this.orderIdField,this.orderIdField,
		             hs.FormLayout.twoColumnedRow(this.stockOutNoField,this.orderNoField,defaultsCfg),
		             hs.FormLayout.twoColumnedRow(this.stockOutDateField,this.officerField,defaultsCfg),
		             hs.FormLayout.wholeOneColumnedRow(this.remarkField,oneClmCfg)
		             ];
		return items;
	}
	,getDetailGrid :function(){
		var _this =this;
		//明细grid
		this.productStockOutDetail =new ProductStockOutDetail.manage.MainPanel({recordIdName:"detailId"});
		this.productStockOutDetail.setStockCard(_this);
		return this.productStockOutDetail.getCenterGrid();
	}
	/**
	 * 窗口元素
	 */
	,getWinItems : function(){
		return [{frame : false,border : false,region : "north",height: 90,items:[this.getCardForm()]},
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
		_this.productStockOutDetail.searchFn(_this.productStockOutDetail);
	}
});
