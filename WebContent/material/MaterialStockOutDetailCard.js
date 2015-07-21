Ext.namespace("MaterialStockOutDetail.card");
MaterialStockOutDetail.card.Win = function(config) {
	MaterialStockOutDetail.card.Win.superclass.constructor.call(this, config);
	this.url.get = '/stockout/stockout!getMaterialStockOutDetail.do';
	this.url.save ='/stockout/stockout!saveMaterialStockOutDetail.do';
};
Ext.extend(MaterialStockOutDetail.card.Win,Commons.card.Win, {
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
		this.stockOutIdField = new Ext.form.Hidden({id : 'stockOutId',autoFill:true});
		var materialIdField = new Ext.form.Hidden({id : 'materialId',autoFill:true});
		
//		var materialNameField =new Ext.form.TextField({fieldLabel : '原料名',id : 'materialName',allowBlank : false});
		var materialNoFeild = new Ext.form.TextField({fieldLabel : '原料编号',id : 'materialNo',allowBlank : false});
		var standardField = new Ext.form.TextField({fieldLabel : '规格',id : 'standard',allowBlank : false});
		var unitField = new Ext.form.TextField({fieldLabel : '单位',id : 'unit',allowBlank : false});
		var priceField = new Ext.form.NumberField({fieldLabel : '单价',id : 'price',allowBlank : false,allowNegative:false,minValue:0,maxValue:9999999});
		var quantityField = new Ext.form.NumberField({fieldLabel : '实际数量',id : 'quantity',allowBlank : false,allowNegative:false,minValue:0,maxValue:9999999});
		
		var _this = this;
		materialNameField = new Ext.ux.form.SearchField({
			name:'materialName',
			fieldLabel:'原料名',
			allowBlank : false,
			doSearch:function(){
				var materialChooser = new MaterialChooser({
					scope:_this,
					callBack:function(record){
						materialIdField.setValue(record.get('materialId'));
						materialNameField.setValue(record.get('materialName'));
						standardField.setValue(record.get('standard'));
						unitField.setValue(record.get('unit'));
			    	}
				});
				materialChooser.show();
			}
		});
		var items = [detailIdField,this.stockOutIdField,materialIdField,
		             hs.FormLayout.twoColumnedRow(materialNoFeild,materialNameField,defaultsCfg),
		             hs.FormLayout.twoColumnedRow(standardField,quantityField,oneClmCfg),
		             hs.FormLayout.twoColumnedRow(unitField,priceField,defaultsCfg)
		             ];
		return items;
	}
	/**
	 * 显示编辑窗口
	 * 重写stockOutId设置
	 * @param id 
	 * 
	 */
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
