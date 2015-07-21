Ext.namespace("Material.card");
/**
 * 原料信息编辑/添加窗口
 * @param config{}
 * @returns {Material.card.Win}
 */
Material.card.Win = function(config) {
	Material.card.Win.superclass.constructor.call(this, config);
	if(!Ext.isDefined(this.url)){
		this.url={};
	};
	this.url.get = '/material/material!getMaterial.do';
	this.url.save ='/material/material!saveMaterial.do';
};
/**
 * 基于common.card.Win的编辑窗口
 */
Ext.extend(Material.card.Win,Commons.card.Win, {
	/**
	 * 窗口标题获取
	 * @param data
	 * @returns
	 */
	getWindTitle : function(data){
		if(data){
			return this.title +"："+data.materialNo+"__"+data.materialName;
		}else{
			return this.title;
		}
	}
	/**
	 * 表单数据加载参数
	 */
	,getLoadParam : function(_this){
		return {materialId: _this.id};
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
		var materialIdField = new Ext.form.Hidden({fieldLabel: '原料ID',name: 'materialId',autoFill:true});
		var materialNoField = new Ext.form.TextField({fieldLabel : '编号',id : 'materialNo',allowBlank : false,emptyText:'自动编号',autoFill:true});
		var materialNameField = new Ext.form.TextField({fieldLabel : '名称',id : 'materialName',allowBlank : false});
		var standardField = new Ext.form.TextField({fieldLabel : '规格',id : 'standard',allowBlank : true});
		var unitField = new Ext.form.TextField({fieldLabel : '单位',id : 'unit',allowBlank : true});
		var weightField = new Ext.form.NumberField({fieldLabel : '单位重量',id : 'weight',allowBlank : false,allowNegative:false,minValue:0,maxValue:9999999});
		var areaField = new Ext.form.NumberField({fieldLabel : '单位表面积',id : 'area',allowBlank : false,allowNegative:false,minValue:0,maxValue:9999999});
		var minStoreField = new Ext.form.NumberField({fieldLabel : '最小库存量',id : 'minStore',allowBlank : false,allowNegative:false,minValue:0,maxValue:9999999});
		var maxStoreField = new Ext.form.NumberField({fieldLabel : '最大库存量',id : 'maxStore',allowBlank : false,allowNegative:false,minValue:0,maxValue:9999999});
		var remarkField = new Ext.form.TextArea({fieldLabel: '备注',name: 'remark',height:65,anchor: '95%',allowBlank : true,disabled:true});
		var materialTypeField = new Ext.form.ComboBox({	fieldLabel : '物资分类',hiddenName : 'materialType',displayField: 'text'
			,store: hs.StoreFactory.getComboStore(comboJsonData["MATERIAL_TYPE"])
		});
		var materialKindField = new Ext.form.ComboBox({	fieldLabel : '物资性质',hiddenName : 'kind',displayField: 'text'
			,store: hs.StoreFactory.getComboStore(comboJsonData["MATERIAL_KIND"])
		});
		var priceField = new Ext.form.NumberField({fieldLabel : '价格',id : 'price',allowBlank : false,allowNegative:false,minValue:0,maxValue:9999999});
		var items = [materialIdField,
		             hs.FormLayout.threeColumnedRow(materialNoField,materialNameField,standardField,defaultsCfg),
		             hs.FormLayout.threeColumnedRow(unitField,weightField,areaField,defaultsCfg),
		             hs.FormLayout.threeColumnedRow(materialTypeField,minStoreField,maxStoreField,defaultsCfg),
		             hs.FormLayout.twoColumnedRow(materialKindField,priceField,defaultsCfg),
		             hs.FormLayout.wholeOneColumnedRow(remarkField,oneClmCfg)
		             ];
		return items;
	}
});
