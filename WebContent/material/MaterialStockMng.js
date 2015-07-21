Ext.namespace("MaterialStock.manage");
/**
 * 原料库存台帐
 * @param {} config
 */
MaterialStock.manage.MainPanel = function(config) {
	MaterialStock.manage.MainPanel.superclass.constructor.call(this, config);
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
	this.url.query = '/material/material!materialStock.do';
	this.url.queryPaged = '/material/material!materialStock.do';
};

Ext.extend(MaterialStock.manage.MainPanel,Common.manage.MainPanel,{
	/**
	 * grid Record 用户创建grid
	 */
	getGridRecord : function(){
		return [
		     { name:"materialId"},
		     { name:"stock"},
		     { name:"materialName"},
		   	 { name:"materialNo"},
			 { name:"standard"},
			 { name:"materialType"},
			 { name:"unit"},
			 { name:"weight"},
			 { name:"area"},
			 { name:"minStore"},
			 { name:"maxStore"},
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
			{header : "原材料编号",dataIndex : 'materialNo',width : 80 },
			{header : "原材料名称",dataIndex : 'materialName',width : 80 },
			{header : "规格",dataIndex : 'standard',width : 60 },
			{header : "物资分类",dataIndex : 'materialType',width : 80, 
				renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["MATERIAL_TYPE"]))},
			{header : "物质性质",dataIndex : 'kind',width : 80, 
					renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["MATERIAL_KIND"]))},
			{header : "单位",dataIndex : 'unit',width : 60 ,hidden:true},
			{header : "价格",dataIndex : 'price',width : 70 ,hidden:true},
			{header : "最小库存",dataIndex : 'minStore',width : 70 },
			{header : "最大库存",dataIndex : 'maxStore',width : 70,hidden:true },
			{header : "库存",dataIndex : 'stock',width : 70 }
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
	 * 扩展gridTbBar，包括添加新按钮到bar中
	 * 添加导出入库表单
	 */
	getExtendGridTbBar:function(_this,tbbar){
		var exportItem={
			text : "导出台帐",
			iconCls : 'icon_excel',
			scope : _this,
			handler : function() {
				alert("未实现");
				return;
				var record = this.getSelected();
				if(!record){
					return;
				}
				var _this = this;
				var idParam = {};
				idParam[_this.recordIdName] = record.get(_this.recordIdName);
				hs.MessageHelper.confirm('确定要导出？', function() {
					// 参数
					var p = Ext.urlEncode(idParam);
					window.open(Ext.getDom('root').value+_this.url.exportExcel+'?'+p);
				});
			}
		};
		var newTbbar =[];//放弃基本操作
		newTbbar.push(exportItem);
		return newTbbar;
	},

});
