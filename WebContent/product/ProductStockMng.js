Ext.namespace("ProductStock.manage");
/**
 * 产品库存台帐
 * @param {} config
 */
ProductStock.manage.MainPanel = function(config) {
	ProductStock.manage.MainPanel.superclass.constructor.call(this, config);
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
	this.url.query = '/product/product!productStock.do';
	this.url.queryPaged = '/product/product!productStock.do';
	
};

Ext.extend(ProductStock.manage.MainPanel,Common.manage.MainPanel,{
	/**
	 * grid Record 用户创建grid
	 */
	getGridRecord : function(){
		return [
		   	 {name:"productId"}
		 	,{name:"productNo"}
		 	,{name:"productName"}
		 	,{name:"productType"}
		 	,{name:"standard"}
		 	,{name:"unit"}
		 	,{name:"price"}
		 	,{name:"minStore"}
		 	,{name:"maxStore"}
		 	,{name:"stock"}
			 ];
	}
	,getGridCm : function(){
		var sm =new Ext.grid.CheckboxSelectionModel({singleSelect : true });
		var cm =new Ext.grid.ColumnModel([ 
			new Ext.grid.RowNumberer(),
			sm
			,{header:"产品ID",dataIndex:'productId',width:60,hidden:true,sortable:true}
			,{header:"产品编号",dataIndex:'productNo',width:110,sortable:true}
			,{header:"产品名称",dataIndex:'productName',width:90,sortable:true}
			,{header:"类型",dataIndex:'productType',width:80,hidden:true,sortable:true,
				renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["PRODUCT_TYPE"]))}
			,{header:"规格",dataIndex:'standard',width:100}
			,{header:"单位",dataIndex:'unit',width:50,align:'center',sortable:true}
			,{header:"报价",dataIndex:'price',width:80,renderer: 'cnMoney',align:'right',sortable:true}
			,{header:"最小库存",dataIndex:'minStore',width:70,align:'right',sortable:true}
			,{header : "库存",dataIndex : 'stock',width : 70 }
		]);
		return cm;
	}
	/**
	 * 获取查询面板
	 */
	,getTopBar: function(){
		if(!this.tBar){
			var _this = this;
			this.productNameField = new Ext.form.TextField({
				fieldLabel : '产品名称',
				name : 'productName',
				width: 140,scope:_this
				,listeners : {'specialkey' : {fn : function(field, e){_this.enterHander(_this,field, e);}}}
			});
			this.productNoField = new Ext.form.TextField({
				fieldLabel : '产品编码'
				,name : 'productNo'
				,width: 100
				,listeners : {'specialkey' : {fn : function(field, e){_this.enterHander(_this,field, e);}}}
			});
			
			this.productTypeField = new Ext.form.ComboBox({
				fieldLabel : '产品类别'
				,hiddenName : 'use'
				,displayField: 'text'
				,store: hs.StoreFactory.getComboStore(comboJsonData["MATERIAL_TYPE"],true)
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
					       new Ext.form.Label({text : "产品名称："})
					      ,this.productNameField
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "产品编号："})
					      ,this.productNoField
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "产品类别："})
					      ,this.productTypeField
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
		return {productName: this.productNameField.getValue()
				,productNo: this.productNoField.getValue()
				,productType: this.productTypeField.getValue()
				};
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
