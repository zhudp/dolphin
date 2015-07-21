Ext.namespace("produce.card");

produce.card.url = {
	 get: '/produce/produce!getProduceTask.do'
	,save: '/produce/produce!saveProduceTask.do'
	,exportExcel: '/produce/produce!exportProduceTask.do'
};

/**
 * 信息卡片
 * @param {} config
 * @return {}
 */
produce.card.Win = function(config) {
	this.config = config;
};

produce.card.Win.prototype = {
	getCardForm: function(){
		if(!this.form1){
			var _this = this;
			var defaultsCfg = {
				disabled:true,
				bodyStyle:'background-color:#D3E1F1;',
				anchor: '95%'
			};
			var oneClmCfg = {
				disabled:true,
				bodyStyle:'background-color:#D3E1F1;',
				anchor: '97%'
			};
			
			function eachItem(item,index,length) {
			    if (item.items && item.items.getCount() > 0) {
			       item.items.each(eachItem, this);
			    }
			    else if(!item.autoFill){
			    	if(item.isXType("textfield") || item.isXType("button") ||  item.isXType("searchfield")) {
			    		item.setDisabled(!item.disabled);
			    	}
			    }
			}
			
			var taskIdField = new Ext.form.Hidden({fieldLabel: '任务ID',id: 'taskId'});
			var taskNameField = new Ext.form.TextField({fieldLabel : '任务名称',name:'taskName',allowBlank : false});
			
			var orderIdField = new Ext.form.Hidden({fieldLabel: '订单ID',id: 'orderId'});
			var orderNoField = new Ext.form.TextField({fieldLabel: '订单编号',name: 'orderNo',readOnly:true});
			var orderNameField = new Ext.ux.form.SearchField({
				name:'orderName',
				fieldLabel:'订单名称',
				doSearch:function(){
					var orderChooser = new OrderChooser({
						scope:_this,
						callBack:function(record){
							orderIdField.setValue(record.get('orderId'));
							orderNoField.setValue(record.get('orderNo'));
							orderNameField.setValue(record.get('orderName'));
							taskNameField.setValue(record.get('orderName'));
						}
					});
					orderChooser.show();
				}
			});
			
			var customIdField = new Ext.form.Hidden({fieldLabel: '客户ID',name: 'customId'});
			var customNoField = new Ext.form.TextField({fieldLabel: '客户编号',name: 'customNo',readOnly:true});
			var customNameField =new Ext.ux.form.SearchField({
				fieldLabel : '客户名称',
				name:'customName',
				editable:false,
				allowBlank: false,
				doSearch:function(){
					var customChooser = new CustomChooser({
						callBack:function(customId, customName, customNo){
							customIdField.setValue(customId);
							customNoField.setValue(customNo);
							customNameField.setValue(customName);
				    	}
					});
					customChooser.show();
				}
		    });
			
			
			var planBeginDateField = new Ext.form.DateField({fieldLabel : '计划开始时间',name:'planBeginDate'});
			var planEndDateField = new Ext.form.DateField({fieldLabel : '计划结束时间',name:'planEndDate'});
			var realBeginDateField = new Ext.form.DateField({fieldLabel : '实际开始时间',name:'realBeginDate'});
			var realEndDateField = new Ext.form.DateField({fieldLabel : '实际结束时间',name:'realEndDate'});
			var storeInDateField = new Ext.form.DateField({fieldLabel : '入库时间',name:'storeInDate'});
			var remarkField = new Ext.form.TextArea({fieldLabel: '备注',name: 'remark',height:66});
			var officerField = new Ext.form.TextField({fieldLabel : '负责人',name: 'officer'});
			
			
			var taskStatusField = new Ext.form.ComboBox({
				 fieldLabel : '任务状态'
				,hiddenName : 'taskStatus'
				,displayField: 'text'
				,value:'1'
				,store: hs.StoreFactory.getComboStore(comboJsonData["TASK_STATUS"])
			});
			
			var form1 = new Ext.form.FormPanel({
				height:360,
				bodyStyle:'padding:4px 0px;background-color:#D3E1F1;',
				title:'生产任务信息',
				labelAlign: 'right',
				border: false,
				autoScroll: true,
	            defaults : {
					labelWidth : 80
				},
				items: [taskIdField,customIdField,orderIdField
				    ,hs.FormLayout.twoColumnedRow(customNameField,customNoField,defaultsCfg)
				    ,hs.FormLayout.twoColumnedRow(orderNameField,orderNoField,defaultsCfg)
					,hs.FormLayout.twoColumnedRow(taskNameField,taskStatusField,defaultsCfg)
					,hs.FormLayout.twoColumnedRow(planBeginDateField,planEndDateField,defaultsCfg)
					,hs.FormLayout.twoColumnedRow(realBeginDateField,realEndDateField,defaultsCfg)
					,hs.FormLayout.twoColumnedRow(storeInDateField,officerField,defaultsCfg)
					,hs.FormLayout.wholeOneColumnedRow(remarkField,oneClmCfg)
		        ],
		        tbar:[{
					text : "编辑",
					iconCls : 'hs-button-edit',
					scope : this,
					enableToggle:true,
					handler : function() {
						_this.getCardForm().items.each(eachItem, this);
						var saveBtn = form1.getTopToolbar().get(1);
						saveBtn.setDisabled(!saveBtn.disabled);
					}
				},{
					text : "保存",
					iconCls : 'icon_save2',
					scope : this,
					disabled:true,
					handler : function() {
						hs.FormHelper.submit(produce.card.url.save,
								function(form, action) {
									hs.MessageHelper.succuss({
										callback : function() {
											if(_this.config && _this.config.callback) {
												_this.config.callback();
											}
											if(_this.config && _this.config.callbackClose) {
												_this.getWin().hide();
											}
										}
									});
								},null, _this.getCardForm().getForm());
					}
				}, {
					text : "导出Excel",
					iconCls : 'icon_excel',
					scope : this,
					handler : function() {
						window.open(Ext.getDom('root').value+produce.card.url.exportExcel+'?taskId='+this.taskId);
					}
				}]
		     });
			this.form1 = form1;
		}
		return this.form1;
	}
	,getDetailGrid: function() {
		if(!this.detailGrid) {
			var detailPanel = new produce.detail.MainPanel();
			this.detailGrid= detailPanel.getDetailGrid();
		}
		return this.detailGrid;
	}
	,getTabPanel: function() {
		if(!this.tabPanel){
			var _this = this;
			this.tabPanel = new Ext.TabPanel({
				activeTab : 0,
				region: "center",
				frame: true,
				border : false,
				autoHeight : true,
				layoutOnTabChange : true,
				deferredRender : false,
				items: [_this.getCardForm(),_this.getDetailGrid()]
			});
		}
		return this.tabPanel;
	}
	,getWin : function() {
		if (!this.win) {
			var _this = this;
			
			var win = new Ext.Window({
				frame : true,
				width : 740,
				layout : 'fit',
				autoHeight : true,
				closeAction : 'hide',
				items:[this.getTabPanel()]
		        
			});
			
			this.win = win;
		}
		return this.win;
	}
	,show: function(taskId){
		this.getWin().show();
		this.getWin().setTitle("生产任务信息");
		//载入信息
		if(taskId) {
			this.taskId = taskId;
			this.load();
		}
		// 新增
		else {
			this.getCardForm().getTopToolbar().get(0).toggle().handler();
		}
	}
	// 加载数据
	,load : function() {
		var _this = this;
		var formPanel = _this.getCardForm();
		hs.FormHelper.load(produce.card.url.get, {
			taskId: _this.taskId
		}, function(form, action) {
			var data = action.result.data;
			form.clearInvalid();
			form.setValues(data);
			_this.getWin().setTitle("生产任务信息："+data.customName+"__"+data.taskName);
		}, formPanel.getForm());
		
		_this.getDetailGrid().load({taskId: _this.taskId});
	}
};

