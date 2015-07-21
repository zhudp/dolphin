Ext.namespace("MaterialStockOut.card");
/**
 * 原料出库编辑/添加窗口
 * @param config{}
 * @returns {MaterialStockOut.card.Win}
 */
MaterialStockOut.card.Win = function(config) {
	MaterialStockOut.card.Win.superclass.constructor.call(this, config);
	this.url.get = '/stockout/stockout!getMaterialStockOut.do';
	this.url.save ='/stockout/stockout!saveMaterialStockOut.do';
};
/**
 * 基于common.card.Win的编辑窗口
 */
Ext.extend(MaterialStockOut.card.Win,Commons.card.Win, {
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
		this.departmentIdField = new Ext.form.Hidden({name: 'departmentId',autoFill:true});
		this.orderIdField = new Ext.form.Hidden({id : 'orderId',autoFill:true});
		this.stockOutNoField = new Ext.form.TextField({fieldLabel : '出库编号',id : 'stockOutNo',autoFill:true});
		this.orderNoField = new Ext.form.TextField({fieldLabel : '订单号',id : 'orderNo',allowBlank : false});
		this.departmentNameField = new Ext.form.TextField({fieldLabel : '部门',id : 'departmentName',allowBlank : false});
		this.officerField = new Ext.form.TextField({fieldLabel : '负责人',id : 'officer',allowBlank : false});
		this.stockOutDateField =  new  Ext.form.DateField({fieldLabel : '出库日期',name : 'stockOutDate',editable:false,format:"Y-m-d",emptyText:"请选择..."});
		this.remarkField =  new  Ext.form.TextArea({fieldLabel : '备注',name : 'remark',height:30,anchor: '95%'});
		//search 订单编号
/*		this.orderNoField =new Ext.ux.form.SearchField({
			fieldLabel : '订单编号',
			name:'orderNo',
			editable:false,
			allowBlank: false,
			doSearch:function(){
				var customChooser = new CustomChooser({
					callBack:function(record){
						customIdField.setValue(customId);
						customNameField.setValue(customName);
			    	}
				});
				customChooser.show();
			}
	    });*/
		var items = [this.stockOutIdField,this.departmentIdField,this.orderIdField,this.orderIdField,
		             hs.FormLayout.threeColumnedRow(this.stockOutNoField,this.orderNoField,this.stockOutDateField,defaultsCfg),
		             hs.FormLayout.twoColumnedRow(this.departmentNameField,this.officerField,defaultsCfg),
		             hs.FormLayout.wholeOneColumnedRow(this.remarkField,oneClmCfg)
		             ];
		return items;
	}
	,getDetailGrid :function(){
		var _this =this;
		//明细grid
		this.materialStockOutDetail =new MaterialStockOutDetail.manage.MainPanel({recordIdName:"detailId"});
		this.materialStockOutDetail.setStockCard(_this);
		return this.materialStockOutDetail.getCenterGrid();
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
		_this.materialStockOutDetail.searchFn(_this.materialStockOutDetail);
	}
	/**
	 * 根据订单信息填充表单，供选择组件回调
	 */
	,loadOrder:function(_this,orderInf){
		//TODO:待实现
	}
});
