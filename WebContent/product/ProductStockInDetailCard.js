Ext.namespace("ProductStockInDetail.card");
/**
 * 产品入库详情编辑/添加窗口
 * @param config{}
 * @returns {ProductStockInDetail.card.Win}
 */
ProductStockInDetail.card.Win = function(config) {
	ProductStockInDetail.card.Win.superclass.constructor.call(this, config);
	this.url.get = '/product/stockin!getProductStockInDetail.do';
	this.url.save ='/product/stockin!saveProductStockInDetail.do';
};
Ext.extend(ProductStockInDetail.card.Win,Commons.card.Win, {
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
		return {detailId: _this.id};
	}
	/**
	 * 创建表单
	 * @returns {Array}
	 */
	,createFormItem : function(){
		var defaultsCfg = {disabled:true,anchor: '95%'};
		var oneClmCfg = {disabled:true,anchor: '97%'};
		
		var detailIdField =  new Ext.form.Hidden({id : 'detailId',autoFill:true});
		this.stockInIdField = new Ext.form.Hidden({id : 'stockInId',autoFill:true});
		var productIdField = new Ext.form.Hidden({id : 'productId',autoFill:true});
		
		var productNoFeild = new Ext.form.TextField({fieldLabel : '产品编号',id : 'productNo',allowBlank : false});
		var standardField = new Ext.form.TextField({fieldLabel : '规格',id : 'standard',allowBlank : false});
		var unitField = new Ext.form.TextField({fieldLabel : '单位',id : 'unit',allowBlank : false});
		var priceField = new Ext.form.NumberField({fieldLabel : '单价',id : 'price',allowBlank : false,allowNegative:false,minValue:0,maxValue:9999999});
		var planQuantityField = new Ext.form.NumberField({fieldLabel : '计划数量',id : 'planQuantity',allowBlank : false,allowNegative:false,minValue:0,maxValue:9999999});
		var realQuantityField = new Ext.form.NumberField({fieldLabel : '实际数量',id : 'realQuantity',allowBlank : false,allowNegative:false,minValue:0,maxValue:9999999});
		var productTypeField = new Ext.form.ComboBox({	fieldLabel : '类型',hiddenName : 'productType',displayField: 'text'
			,store: hs.StoreFactory.getComboStore(comboJsonData["PRODUCT_TYPE"])
		});
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
						productNoFeild.setValue(record.get('productId'));
						productNameField.setValue(record.get('productNo'));
						standardField.setValue(record.get('standard'));
						unitField.setValue(record.get('unit'));
						productTypeField.setValue(record.get('productType'));
			    	}
				});
				productChooser.show();
			}
		});
		
		var items = [detailIdField,this.stockInIdField,productIdField,
		             hs.FormLayout.twoColumnedRow(productNoFeild,productNameField,defaultsCfg),
		             hs.FormLayout.twoColumnedRow(standardField,productTypeField,defaultsCfg),
		             hs.FormLayout.twoColumnedRow(unitField,priceField,defaultsCfg),
		             hs.FormLayout.twoColumnedRow(planQuantityField,realQuantityField,defaultsCfg),
		             ];
		return items;
	}
	/**
	 * 显示编辑窗口
	 * 重写stockInId设置
	 * @param id 
	 * 
	 */
	,show: function(id){
		var _this = this;
		this.getWin().show();
		this.getWin().setTitle(this.getWindTitle());
		_this.stockInIdField.setValue(_this.mainPanel.stockCard.stockInIdField.getValue());
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
