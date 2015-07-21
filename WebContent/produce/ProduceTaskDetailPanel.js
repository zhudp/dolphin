Ext.namespace("produce.detail");

produce.detail.url = {
	 query : '/produce/produce!queryPagedDetail.do'
	,save : '/produce/produce!saveOrderDetail.do'
	,remove : '/produce/produce!removeDetail.do'
	
};

produce.detail.record = [
	 {name:"detailId"}
	,{name:"taskId"}
	,{name:"productId"}
	,{name:"productNo"}
	,{name:"productName"}
	,{name:"standard"}
	,{name:"unit"}
	,{name:"price"}
	,{name:"productNum",type: 'int'}
	,{name:"stock",type: 'int'}
	,{name:"minStore",type: 'int'}
	,{name:"produceTask",type: 'int'}
	,{name:"remark"}
];

produce.detail.MainPanel = function(config) {
	this.config = config?config:{};
};

produce.detail.MainPanel.prototype = {
	getDetailGrid: function(){
		if (!this.detailGrid) {
			var _this = this;
			var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true });
			var reader = new Ext.data.JsonReader({
		        idProperty:'detailId',
		        fields: produce.detail.record,
		        remoteGroup:false,
		        remoteSort: false
		    });
			var gridStore = new Ext.data.GroupingStore({
	            reader: reader,
	            proxy : new Ext.data.HttpProxy({
	                url: Ext.getDom("root").value + produce.detail.url.query,
	                method: 'POST'
	            }),
	            groupField: 'taskId'
	        });
			var areadyCount = false;
			var grid = new Ext.grid.EditorGridPanel({
				title:'任务明细',
				height:360,
                store: gridStore,
                clicksToEdit: 1,
                cm: new Ext.grid.ColumnModel([ 
					new Ext.grid.RowNumberer()
					,sm
					,{header:"ID",dataIndex:'detailId',width:60,hidden:true,sortable:true}
					,{header:"订单ID",dataIndex:'taskId',width:60,hidden:true,sortable:true}
					,{header:"产品ID",dataIndex:'productId',width:60,hidden:true,sortable:true}
					,{header:"产品编号",dataIndex:'productNo',width:86,sortable:true,
						summaryType: 'count',
			            summaryRenderer: function(value, summaryData, dataIndex) {
			                return ' - 合计 - ';
			            }}
					,{header:"产品名称",dataIndex:'productName',width:90,sortable:true}
					,{header:"规格",dataIndex:'standard',width:100}
					,{header:"单位",dataIndex:'unit',width:50,align:'center',sortable:true}
					,{header:"单价",dataIndex:'price',width:70,renderer: 'cnMoney',align:'right',sortable:true}
					,{header:"数量",dataIndex:'productNum',width:60,align:'right',
						summaryType:'sum',
						editor: new Ext.form.NumberField({
							name:'productNum',
							allowBlank: false,
							maxValue:999999,
							minValue:0,
							selectOnFocus:true
				    })}
					,{header:"合计",dataIndex:'total',width:80,align:'right',sortable:true,
						renderer: function(value, meta, record){
							return Ext.util.Format.cnMoney(record.get('productNum')*record.get('price'));
						},
						summaryType: function(v, record, field, data){
							return v + record.get('productNum') * record.get('price');
						},
			            summaryRenderer:Ext.util.Format.cnMoney
			        }
					,{header:"备注",dataIndex:'remark',width:90,editor: new Ext.form.TextArea({
						name:'remark',
						maxLength:100
				    })}
					,{header:"最小",dataIndex:'minStore',width:65,align:'right',sortable:true,
						renderer: function(value, meta, record){
							meta.css+='stock_area';
							return value;
						}
					}
					,{header:"当前",dataIndex:'stock',width:65,align:'right',sortable:true,
						renderer: function(value, meta, record){
							meta.css+='stock_area';
							return value;
						}
					}
					,{header:"生产",dataIndex:'produceTask',width:65,align:'right',sortable:true,
						renderer: function(value, meta, record){
							meta.css+='stock_area';
							if(!areadyCount) {
								value = record.get('productNum')+record.get('minStore')-record.get('stock');
								areadyCount = true;
							}
							return value;
						},
						editor: new Ext.form.NumberField({
							name:'produceTask',
							allowBlank: false,
							maxValue:999999,
							minValue:0,
							selectOnFocus:true
						})
					}
				]),
				view: new Ext.grid.GroupingView({
		            forceFit:true,
		            showGroupName: false,
		            enableNoGroups:false,
					enableGroupingMenu:false,
					groupTextTpl: '产品清单  (共{[values.rs.length]}种产品)'
		        }),
		        plugins: [new Ext.ux.grid.HybridSummary(),
		                  new Ext.ux.plugins.GroupHeaderGrid({
								rows: [
									[
										{header: ' ', colspan: 1, align: 'center'},
										{header: ' ', colspan: 1, align: 'center'},
										{header: '订单产品列表', colspan: 11, align: 'center'},
										{header: '库存信息', colspan: 3, align: 'center'}
									]
								],
								hierarchicalColMenu: false
							})],
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
							var creatTaskBtn = grid.getTopToolbar().get(8);
							saveBtn.setDisabled(!saveBtn.disabled);
							addBtn.setDisabled(!addBtn.disabled);
							delBtn.setDisabled(!delBtn.disabled);
							creatTaskBtn.setDisabled(!creatTaskBtn.disabled);
						}
					},'-', {
						text : "保存",
						iconCls : 'icon_save2',
						disabled:true,
						scope : this,
						handler : function() {
							
							var param = {};
							var fileds=gridStore.reader.meta.fields;
							if(gridStore.getCount() > 0) {
								
								gridStore.each(function(rec, index) {
									Ext.each(fileds, function(filed,index){
										if(!param[filed.name]) {
											param[filed.name] = new Array();
										}
										param[filed.name].push(rec.get(filed.name));
									});
								});
							}
							
							hs.ActionHelper.request(
								produce.detail.url.save,
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
							var productChooser = new ProductChooser({
								singleSelect:false,
								callBack:function(records){
									Ext.each(records, function(rec,index){
										rec.set('taskId',_this.config.taskId);
										rec.set('productNum',1);
									});
									gridStore.add(records);
						    	}
							});
							productChooser.show();
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
							var _this = this;
							hs.MessageHelper.confirm('确定要删除？', function() {
								hs.ActionHelper.request(
										produce.detail.url.remove
										,{detailId: record.get('detailId')}
										,function(){gridStore.reload();}
								);
							});
						}
					}
	        	],
		        load: function(baseParams) {
		        	_this.config.taskId= baseParams.taskId;
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