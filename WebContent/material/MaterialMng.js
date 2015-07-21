Ext.namespace("Material.manage");
/**
 * 原料管理主面板
 * @param {} config
 */
Material.manage.MainPanel = function(config) {
	Material.manage.MainPanel.superclass.constructor.call(this, config);
	this.config = config;
	if(Ext.isObject(config)){
		if(Ext.isDefined(config.cardWin)){
			this.cardWin = config.cardWin;
		}
		if(Ext.isDefined(config.recordIdName)){
			this.recordIdName = config.recordIdName;
		}
	}
	if(!Ext.isDefined(this.url)){
		this.url={};
	}
	this.url.query = '/material/material!queryPaged.do';
	this.url.remove = '/material/material!delete.do';
	this.url.get = '/material/material!getMaterial.do';
	this.url.queryPaged = '/material/material!queryPaged.do';
	this.url.savematerial = '/material/material!saveMaterial.do';
};

Ext.extend(Material.manage.MainPanel,Common.manage.MainPanel,{
	/**
	 * grid Record 用户创建grid
	 */
	getGridRecord : function(){
		return [
		     { name:"materialId"},  
		     { name:"materialName"},
		   	 { name:"materialNo"},
			 { name:"standard"},
			 { name:"materialType"},
			 { name:"unit"},
			 { name:"weight"},
			 { name:"area"},
			 { name:"remark"},
			 { name:"minStore"},
			 { name:"maxStore"},
			 { name:"gmtCreate"},
			 { name:"creator"},
			 { name:"creatorId"},
			 { name:"gmtModify"},
			 { name:"modifier"},
			 { name:"modifierId"},
			 { name:"kind"},
			 { name:"price"}
			 ];
	}
	,getGridCm : function(){
		var sm =new Ext.grid.CheckboxSelectionModel({singleSelect : true });
		var cm =new Ext.grid.ColumnModel([ 
			new Ext.grid.RowNumberer(),
			sm,
			{header:"ID",dataIndex:'materialId',width:60,hidden:true},
			{header : "原材料名称",dataIndex : 'materialName',width : 80 },
			{header : "原材料编号",dataIndex : 'materialNo',width : 80 },
			{header : "规格",dataIndex : 'standard',width : 60 },
			{header : "物资分类",dataIndex : 'materialType',width : 80, 
				renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["MATERIAL_TYPE"]))},
			{header : "物质性质",dataIndex : 'kind',width : 80, 
					renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["MATERIAL_KIND"]))},
				
			{header : "单位",dataIndex : 'unit',width : 60 },
			{header : "单位重量",dataIndex : 'weight',width : 70 ,sortable:true},
			{header : "单位面积",dataIndex : 'area',width : 70 },
			{header : "价格",dataIndex : 'price',width : 70 },
			{header : "备注",dataIndex : 'remark',width : 60 ,hidden:true},
			{header : "最小库存",dataIndex : 'minStore',width : 70 },
			{header : "最大库存",dataIndex : 'maxStore',width : 70 },
			{header : "创建时间",dataIndex : 'gmtCreate',width : 70 ,hidden:true},
			{header : "创建人",dataIndex : 'creator',width : 60 ,hidden:true},
			{header : "创建人ID",dataIndex : 'creatorId',width : 70 ,hidden:true},
			{header : "更新时间",dataIndex : 'gmtModify',width : 70 ,hidden:true},
			{header : "更新人",dataIndex : 'modifier',width : 60,hidden:true },
			{header : "更新人ID",dataIndex : 'modifierId',width : 70,hidden:true }
		]);
		return cm;
	}
	/**
	 * 获取查询面板
	 */
	,getTopBar: function(){
		if(!this.tBar){
			var _this = this;
			this.materialNameField = new Ext.form.TextField({
				fieldLabel : '原料名称',
				name : 'materialName',
				width: 140,scope:_this
				,listeners : {'specialkey' : {fn : function(field, e){_this.enterHander(_this,field, e);}}}
			});
			this.materialNoField = new Ext.form.TextField({
				fieldLabel : '原料编码'
				,name : 'materialNo'
				,width: 100
				,listeners : {'specialkey' : {fn : function(field, e){_this.enterHander(_this,field, e);}}}
			});
			
			this.materialTypeField = new Ext.form.ComboBox({
				fieldLabel : '原料类别'
				,hiddenName : 'use'
				,displayField: 'text'
				,store: hs.StoreFactory.getComboStore(comboJsonData["MATERIAL_TYPE"],true)
				,width: 80
			});
			this.materialKindField = new Ext.form.ComboBox({
				fieldLabel : '原料类别'
				,hiddenName : 'kind'
				,displayField: 'text'
				,store: hs.StoreFactory.getComboStore(comboJsonData["MATERIAL_KIND"],true)
				,width: 80
			});
			var toolbar = new Ext.Panel({
				frame : false,
				border : false,
				region : "north",
				layout : 'fit',
				height: 34,
				tbar:[{
					xtype: 'buttongroup',
					columns: 13,
					labelAlign:'right',
					defaults: {
						labelAlign:'right',
						scale: 'small'
					},
					items:[
					       new Ext.form.Label({text : "原料名称："})
					      ,this.materialNameField
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "原料编号："})
					      ,this.materialNoField
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "原料类别："})
					      ,this.materialTypeField
					      ,new Ext.form.Label({text : "原料性质："})
					      ,this.materialKindField
					      ,{xtype:'tbspacer',width:15}
					      ,{
				                text: '查询',
				                scale: 'medium',
								iconCls: 'hs-button-search',
				                handler:function() {
				                	_this.searchFn(_this);
								}
				            }
					]
				}]
			});
			
			this.tBar = toolbar;
		}
		return this.tBar;
	}
	/**
	 * 提供搜索条件
	 */
	,getSearchParm : function(){
		return {materialName: this.materialNameField.getValue()
				,materialNo: this.materialNoField.getValue()
				,materialType: this.materialTypeField.getValue()
				,kind: this.materialKindField.getValue()};
	},
	/**
	 * 获取信息编辑窗口
	 */
	getCardWin : function(){
		var cardWin = new Material.card.Win({width : 680,height: 350,title:"原料管理",callbackClose:true});
		cardWin.setCallback(this.searchFn);
		cardWin.setMainPanel(this);
		this.setCardWin(cardWin);
		return this.cardWin;
	}
});
