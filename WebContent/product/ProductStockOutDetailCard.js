Ext.namespace("ProductStockOutDetail.card");
ProductStockOutDetail.card.Win = function(config) {
	ProductStockOutDetail.card.Win.superclass.constructor.call(this, config);
	this.url.get = '/product/stockout!getProductStockOutDetail.do';
	this.url.save ='/product/stockout!saveProductStockOutDetail.do';
};
Ext.extend(ProductStockOutDetail.card.Win,Commons.card.Win, {
	getWindTitle : function(data){
			return this.title;
	}
	,getLoadParam : function(_this){
		return {detailId: _this.id};
	}
	,createFormItem : function(){
		var defaultsCfg = {disabled:true,anchor: '95%'};
		var oneClmCfg = {disabled:true,anchor: '97%'};
		
		var detailIdField =  new Ext.form.Hidden({id : 'detailId',autoFill:true});
		this.stockOutIdField = new Ext.form.Hidden({id : 'stockOutId',autoFill:true});
		var productIdField = new Ext.form.Hidden({id : 'productId',autoFill:true});
		
		var productNoFeild = new Ext.form.TextField({fieldLabel : '产品编号',id : 'productNo',allowBlank : false});
		var standardField = new Ext.form.TextField({fieldLabel : '规格',id : 'standard',allowBlank : false});
		var unitField = new Ext.form.TextField({fieldLabel : '单位',id : 'unit',allowBlank : false});
//		var priceField = new Ext.form.NumberField({fieldLabel : '单价',id : 'price',allowBlank : false,allowNegative:false,minValue:0,maxValue:9999999});
		var boxNoField = new Ext.form.TextField({fieldLabel : '箱号',id : 'boxNo',allowBlank : false});
		var quantityField = new Ext.form.NumberField({fieldLabel : '数量',id : 'quantity',allowBlank : false,allowNegative:false,minValue:0,maxValue:9999999});
		
		var _this = this;
		productNameField = new Ext.ux.form.SearchField({
			name:'productName',
			fieldLabel:'产品名',
			allowBlank : false,
			doSearch:function(){
				var productChooser = new ProductChooser({
					scope:_this,
					callBack:function(records){
						var record = records[0];
						productIdField.setValue(record.get('productId'));
						productNameField.setValue(record.get('productName'));
						standardField.setValue(record.get('standard'));
						unitField.setValue(record.get('unit'));
						productNoFeild.setValue(record.get('productNo'));
			    	}
				});
				productChooser.show();
			}
		});
		var items = [detailIdField,this.stockOutIdField,productIdField,
		             hs.FormLayout.twoColumnedRow(productNoFeild,productNameField,defaultsCfg),
		             hs.FormLayout.twoColumnedRow(standardField,quantityField,oneClmCfg),
		             hs.FormLayout.twoColumnedRow(unitField,boxNoField,defaultsCfg)
		             ];
		return items;
	}
	,show: function(id){
		var _this = this;
		this.getWin().show();
		this.getWin().setTitle(this.getWindTitle());
		_this.stockOutIdField.setValue(_this.mainPanel.stockCard.stockOutIdField.getValue());
		//载入信息
		if(id) {
			this.id = id;//
			this.load();
		}
		// 新增
		else {
			this.getWin().getTopToolbar().get(0).toggle().handler();
			
		}
	}
});
