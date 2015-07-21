Ext.namespace("SupplierQuote.card");
/**
 * 原料信息编辑/添加窗口
 * @param config{}
 * @returns {SupplierQuote.card.Win}
 */
SupplierQuote.card.Win = function(config) {
	SupplierQuote.card.Win.superclass.constructor.call(this, config);
	if(!Ext.isDefined(this.url)){
		this.url={};
	};
	this.url.get = '/supplier/quote!getSupplierQuote.do';
	this.url.save ='/supplier/quote!saveSupplierQuote.do';
};
/**
 * 基于common.card.Win的编辑窗口
 */
Ext.extend(SupplierQuote.card.Win,Commons.card.Win, {
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
		return {quoteId: _this.id};
	}
	/**
	 * 创建表单
	 * @returns {Array}
	 */
	,createFormItem : function(){
		var defaultsCfg = {
			disabled:true,
			anchor: '95%'
		};
		var oneClmCfg = {
			disabled:true,
			anchor: '97%'
		};
		var quoteIdField = new Ext.form.Hidden({fieldLabel: '报价ID',name: 'quoteId',autoFill:true});
		var materialIdField = new Ext.form.Hidden({fieldLabel: '原料ID',name: 'materialId',autoFill:true});
		var materialNoField = new Ext.form.TextField({fieldLabel : '原料编号',id : 'materialNo',allowBlank : false,emptyText:'自动编号',autoFill:true});
		var supplierIdField = new Ext.form.Hidden({fieldLabel: '供应商ID',name: 'supplierId',autoFill:true});
		var standardField = new Ext.form.TextField({fieldLabel : '规格',id : 'standard',allowBlank : true});
		var unitField = new Ext.form.TextField({fieldLabel : '单位',id : 'unit',allowBlank : true});
		var remarkField = new Ext.form.TextArea({fieldLabel: '备注',name: 'remark',height:65,anchor: '95%',allowBlank : true,disabled:true});
		var priceField = new Ext.form.NumberField({fieldLabel : '价格',id : 'price',allowBlank : false,allowNegative:false,minValue:0,maxValue:9999999});
		var _this =this;
		var supplierNameField = new Ext.ux.form.SearchField({
			name:'supplierName',
			fieldLabel:'供应商名称',
			doSearch:function(){
				var supplierChooser = new SupplierChooser({
					scope:_this,
					callBack:function(record){
						supplierIdField.setValue(record.get('supplierId'));
						supplierNameField.setValue(record.get('supplierName'));
			    	}
				});
				supplierChooser.show();
			}
		});
		var materialNameField = new Ext.ux.form.SearchField({
			name:'materialName',
			fieldLabel:'原料名',
			allowBlank : false,
			doSearch:function(){
				var materialChooser = new MaterialChooser({
					scope:_this,
					callBack:function(record){
						materialIdField.setValue(record.get('materialId'));
						materialNameField.setValue(record.get('materialName'));
						materialNoField.setValue(record.get('materialNo'));
						standardField.setValue(record.get('standard'));
						unitField.setValue(record.get('unit'));
			    	}
				});
				materialChooser.show();
			}
		});
		
		var items = [materialIdField,quoteIdField,supplierIdField,
		             hs.FormLayout.twoColumnedRow(supplierNameField,materialNameField,defaultsCfg),
		             hs.FormLayout.twoColumnedRow(materialNoField,standardField,defaultsCfg),
		             hs.FormLayout.twoColumnedRow(unitField,priceField,defaultsCfg),
		             hs.FormLayout.wholeOneColumnedRow(remarkField,oneClmCfg)
		             ];
		return items;
	}
});
