Ext.namespace("PurchaseTask.detail");

PurchaseTask.detail.url = {
	 query : '/purchase/purchase!getPurchasesTask.do'
	,supplierQuery : '/purchase/purchase!getSupplierCombox.do'
	,save : '/purchase/purchase!savePurchaseByTask.do'
	,remove : '/purchase/purchase!removeDetail.do'
	
};

PurchaseTask.detail.record = [
	{ name:"taskId"},
	{ name:"produceTaskId"},
	{ name:"purchaseId"},
	{ name:"materialId"},
	{ name:"materialName"},
	{ name:"standard"},
	{ name:"unit"},
	{ name:"supplierId"},
	{ name:"supplierName"},
	{ name:"price"},
	{ name:"status"},
	{ name:"quantity",type: 'int'},
	{ name:"remark"}
];

PurchaseTask.detail.MainPanel = function(config) {
	this.config = config?config:{};
};

PurchaseTask.detail.MainPanel.prototype = {
	getDetailGrid: function(){
		if (!this.detailGrid) {
			var _this = this;
			var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true });
			var reader = new Ext.data.JsonReader({
		        id:'taskId',
		        totalProperty:'totalCount',
		        root:'result',
		        fields: PurchaseTask.detail.record
		    });
			var gridStore = new Ext.data.Store({
	            reader: reader,
	            proxy : new Ext.data.HttpProxy({
	                url: Ext.getDom("root").value + PurchaseTask.detail.url.query,
	                method: 'POST'
	            })
	        });
			//
			var ds = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: Ext.getDom("root").value + PurchaseTask.detail.url.supplierQuery
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'result',
		            totalProperty: 'totalCount',
		            id: 'quoteId'
		        }, [
					{ name:"quoteId"},
					{ name:"materialId"},
					{ name:"supplierId"},
					{ name:"supplierName"},
					{ name:"price"},
					{ name:"remark"}
		        ])
		    });

		    var resultTpl = new Ext.XTemplate(
		        '<tpl for="."><div class="search-item">',
		            '<h3><span>{supplierName}  {price}</span></h3>',
		        '</div></tpl>'
		    );
		    
		    var supplierEdit = new Ext.form.ComboBox({
		    	tpl:resultTpl,
		    	store: ds,
		        displayField:'supplierName',
		        typeAhead: true,
		        mode: 'remote',
		        triggerAction: 'all',
		        emptyText:'...',
		        selectOnFocus:true,
		        itemSelector: 'div.search-item',
		        onSelect: function(record){ 
		        	var records = _this.getDetailGrid().getSelectionModel().getSelections();
		        	records[0].set('supplierId',record.data.supplierId);
		        	records[0].set('supplierName',record.data.supplierName);
		        	records[0].set('price',record.data.price);
		        	
		        },
		        listeners : {'focus' : {fn : function(field, e){
		        	var records = _this.getDetailGrid().getSelectionModel().getSelections();
		        	var materialId = records[0].get('materialId');
		        	var baseParams ={};
		        	baseParams.materialId = materialId;
		        	ds.baseParams=baseParams;
		        	ds.load();
		        }}}
		    });
			//
			
			var grid = new Ext.grid.EditorGridPanel({
				title:'物料清单',
				height:360,
				region : "center",
                store: gridStore,
                clicksToEdit: 1,
                cm: new Ext.grid.ColumnModel([ 
					new Ext.grid.RowNumberer(),
					sm,
					{header : "生产任务单id",dataIndex : 'produceTaskId',width : 60,hidden:true,sortable:true},
					{header : "采购单ID",dataIndex : 'purchaseId',width : 60,hidden:true,sortable:true },
					{header : "原料ID",dataIndex : 'materialId',width : 60,hidden:true,sortable:true },
					{header : "原料",dataIndex : 'materialName',width : 120 },
					{header : "规格",dataIndex : 'standard',width : 60 },
					{header : "单位",dataIndex : 'unit',width : 60 },
					{header : "供应商id",dataIndex : 'supplierId',width : 100,hidden:true,sortable:true},
					{header : "供应商",dataIndex : 'supplierName',width : 120,editor:supplierEdit},
					{header : "数量",dataIndex : 'quantity',width : 60 ,
						 editor: new Ext.form.NumberField({
				                allowBlank: false,
				                allowNegative: false,
				                maxValue: 100000,
				                minValue: 1
				            })},
					{header : "价格",dataIndex : 'price',width : 60 ,
			            editor: new Ext.form.NumberField({
			                allowBlank: false,
			                allowNegative: false,
			                maxValue: 100000,
			                minValue: 1
			            })},
					{header : "备注",dataIndex : 'remark',width : 60 }

				]),
                sm: sm,
				enableColumnResize: true,
				stripeRows:true,
				trackMouseOver: true,
		        loadMask: {msg:'数据加载中，请稍候...'},
		        enableColLock: false,
	        	tbar:[{
						text : "编辑",
						iconCls : 'hs-button-edit',
						scope : this,
						enableToggle:true,
						handler : function() {
							var saveBtn = grid.getTopToolbar().get(2);
							var addBtn = grid.getTopToolbar().get(4);
							var delBtn = grid.getTopToolbar().get(6);
							saveBtn.setDisabled(!saveBtn.disabled);
							addBtn.setDisabled(!addBtn.disabled);
							delBtn.setDisabled(!delBtn.disabled);
						}
					},'-', {
						text : "保存",
						iconCls : 'icon_save2',
						disabled:true,
						scope : this,
						handler : function() {
							
							var param = {};
							/*
							 * {
							 * 采购名称
							 * 采购负责人
							 * 采购时间
							 * 计划入库时间
							 * detail：[ {field:value,field:value},{},]
							 * }
							 * */
							param.purchaseName = this.mainCard.purchaseNameField.getValue();
							param.orderDate=this.mainCard.orderDateField.getValue();
							param.orderDate =new Date(param.orderDate).format("Y-m-d H:i:s");
							param.planStoreinDate=this.mainCard.planStoreinDateField.getValue();
							param.planStoreinDate =new Date(param.planStoreinDate).format("Y-m-d H:i:s");
							param.officer=this.mainCard.officerField.getValue();
							param.detail=[];
							
							var fileds=gridStore.reader.meta.fields;
							if(gridStore.getCount() > 0) {
								
								gridStore.each(function(rec, index) {
									//行
									var detail={};
									Ext.each(fileds, function(filed,index){
										//列
										detail[filed.name] = rec.get(filed.name);
									});
									param.detail.push(detail);
								});
							}
							param.detailJson = Ext.util.JSON.encode(param.detail);
							hs.ActionHelper.request(
								PurchaseTask.detail.url.save,
								param,
								function(){
									
								}
							);
						}
					},'-',{
						text : "添加",
						iconCls : 'hs-button-add',
						scope : this,
						disabled:true,
						handler : function() {
							var materialChooser = new MaterialChooser({
								singleSelect:false,
								callBack:function(records){
									gridStore.add(records);
						    	}
							});
							materialChooser.show();
						}
					},'-', {
						text : "删除",
						iconCls : 'hs-button-remove',
						scope : this,
						disabled:true,
						handler : function() {
							var record = this.getSelected();
							if(!record){
								return;
							}
							hs.MessageHelper.confirm('确定要删除？', function() {
								gridStore.remove(record);
//								hs.ActionHelper.request(
//										PurchaseTask.detail.url.remove
//										,{detailId: record.get('detailId')}
//										,function(){gridStore.reload();}
//								);
							});
						}
					}
	        	],
		        load: function(baseParams) {
		        	_this.config.orderId= baseParams.orderId;
		        	hs.StoreHelper.beforeload(gridStore, baseParams);
					gridStore.load();
				}
            });
			this.detailGrid = grid;
		}
		return this.detailGrid;
	}
	,getSelected: function(){
		var records = this.getDetailGrid().getSelectionModel().getSelections();
		if (!records || records.length == 0) {
			hs.MessageHelper.info({msg : '请选择记录后再进行操作！'});
			return;
		}
		if (records.length > 1) {
			hs.MessageHelper.info({msg : '请选择1条记录进行操作！'});
			return;
		}
		return records[0];
	}
};