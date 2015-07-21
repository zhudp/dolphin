Ext.namespace("PurchaseDetail.card");
PurchaseDetail.card.Win = function(config) {
	PurchaseDetail.card.Win.superclass.constructor.call(this, config);
	this.url.get = '/purchase/purchase!getPurchaseDetail.do';
	this.url.save ='/purchase/purchase!savePurchaseDetail.do';
};
Ext.extend(PurchaseDetail.card.Win,Commons.card.Win, {
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
		this.detailIdField =  new Ext.form.Hidden({id : 'detailId',autoFill:true});
		this.purchaseIdField = new Ext.form.Hidden({id : 'purchaseId',autoFill:true});
		this.materialIdField = new Ext.form.Hidden({id : 'materialId',autoFill:true});
		this.materialNoField = new Ext.form.Hidden({fieldLabel : '原材料编号',id : 'materialNo',autoFill:true});
		this.standardField = new Ext.form.TextField({fieldLabel : '规格',id : 'standard',allowBlank : false});
		this.unitField = new Ext.form.TextField({fieldLabel : '单位',id : 'unit',allowBlank : false});
		this.priceField = new Ext.form.NumberField({fieldLabel : '单价',id : 'price',allowBlank : false,allowNegative:false,minValue:0,maxValue:9999999});
		this.planNumberField = new Ext.form.NumberField({fieldLabel : '计划数量',id : 'planNumber',allowNegative:false,minValue:0,maxValue:9999999});
		this.storeinNumberField = new Ext.form.NumberField({fieldLabel : '数量',id : 'storeinNumber',allowNegative:false,minValue:0,maxValue:9999999});
		this.remarkField = new Ext.form.TextArea({fieldLabel : '备注',id : 'remark',height:30});
		
		var _this = this;
		this.materialNameField = new Ext.ux.form.SearchField({
			name:'materialName',
			fieldLabel:'原材料',
			allowBlank : false,
			doSearch:function(){
				var materialChooser = new MaterialChooser({
					scope:_this,
					callBack:function(record){
						this.materialIdField.setValue(record.get('materialId'));
						this.materialNameField.setValue(record.get('materialName'));
			    	}
				});
				materialChooser.show();
			}
		});
		
		var items = [this.detailIdField,this.purchaseIdField,this.materialIdField,this.materialNoField,
		             hs.FormLayout.twoColumnedRow(this.materialNameField,this.planNumberField,defaultsCfg),
		             hs.FormLayout.twoColumnedRow(this.standardField,this.storeinNumberField,defaultsCfg),
		             hs.FormLayout.twoColumnedRow(this.unitField,this.priceField,defaultsCfg),
		             hs.FormLayout.wholeOneColumnedRow(this.remarkField,oneClmCfg),
		             ];
		return items;
	}
	/**
	 * 显示编辑窗口
	 * 重写purchaseId设置
	 * @param id 
	 * 
	 */
	,show: function(id){
		var _this = this;
		this.getWin().show();
		this.getWin().setTitle(this.getWindTitle());
		_this.purchaseIdField.setValue(_this.mainPanel.stockCard.purchaseIdField.getValue());
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
