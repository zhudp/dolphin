Ext.namespace("order.card");

order.card.url = {
	 get: '/order/order!getOrder.do'
	,save: '/order/order!saveOrder.do'
	,exportExcel: '/order/order!exportOrderDetail.do'
};

/**
 * 信息卡片
 * @param {} config
 * @return {}
 */
order.card.Win = function(config) {
	this.config = config;
};

order.card.Win.prototype = {
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
			
			var orderIdField = new Ext.form.Hidden({fieldLabel: '订单ID',id: 'orderId'});
			var orderNoField = new Ext.form.TextField({fieldLabel: '订单编号',name: 'orderNo',emptyText:'自动编号',autoFill:true});
			var orderNameField = new Ext.form.TextField({fieldLabel: '订单名称',name: 'orderName',allowBlank: false});
			var orderPriceField  = new Ext.form.NumberField({
				fieldLabel: '订单价格',
				name: 'orderPrice',
				value:0,
				allowBlank: false,
				minValue:0,
				maxValue:9999999,
				selectOnFocus:true
			});
			var remarkField = new Ext.form.TextArea({fieldLabel: '备注',name: 'remark',height:66});
			var officerField = new Ext.form.TextField({fieldLabel : '经办人',name: 'officer'});
			var planDeliveryDate = new Ext.form.DateField({fieldLabel : '发货日期',name: 'planDeliveryDate',allowBlank : false});
			var deliveryAddressField = new Ext.form.TextField({fieldLabel : '发货地址',name: 'deliveryAddress',allowBlank : false});
			
			var customIdField = new Ext.form.Hidden({fieldLabel: '所属客户ID',name: 'customId',value:initCustomId});
			var customNoField = new Ext.form.TextField({fieldLabel: '客户编号',name: 'customNo',readOnly:true,value:initCustomNo});
			var customNameField =new Ext.ux.form.SearchField({
				fieldLabel : '客户名称',
				name:'customName',
				value:initCustomName,
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

			var orderStatusField = new Ext.form.ComboBox({
				 fieldLabel : '订单状态'
				,hiddenName : 'status'
				,displayField: 'text'
				,value:'1'
				,store: hs.StoreFactory.getComboStore(comboJsonData["ORDER_STATUS"])
			});
			
			var form1 = new Ext.form.FormPanel({
				height:360,
				bodyStyle:'padding:4px 0px;background-color:#D3E1F1;',
				title:'订单信息',
				labelAlign: 'right',
				border: false,
				autoScroll: true,
	            defaults : {
					labelWidth : 70
				},
				items: [orderIdField,customIdField
			        ,hs.FormLayout.twoColumnedRow(customNameField,customNoField,defaultsCfg)
			        ,hs.FormLayout.twoColumnedRow(orderNoField,orderNameField,defaultsCfg)
					,hs.FormLayout.twoColumnedRow(orderPriceField,orderStatusField,defaultsCfg)
					,hs.FormLayout.twoColumnedRow(planDeliveryDate,officerField,defaultsCfg)
					,hs.FormLayout.wholeOneColumnedRow(deliveryAddressField,oneClmCfg)
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
						hs.FormHelper.submit(order.card.url.save,
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
						window.open(Ext.getDom('root').value+order.card.url.exportExcel+'?orderId='+this.orderId);
					}
				}]
		     });
			this.form1 = form1;
		}
		return this.form1;
	}
	,getDetailGrid: function() {
		if(!this.detailGrid) {
			var detailPanel = new order.detail.MainPanel();
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
	,show: function(orderId){
		this.getWin().show();
		this.getWin().setTitle("订单信息");
		//载入信息
		if(orderId) {
			this.orderId = orderId;
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
		hs.FormHelper.load(order.card.url.get, {
			orderId: _this.orderId
		}, function(form, action) {
			var data = action.result.data;
			form.clearInvalid();
			form.setValues(data);
			_this.getWin().setTitle("订单信息："+data.orderNo+"__"+data.orderName);
		}, formPanel.getForm());
		
		_this.getDetailGrid().load({orderId: _this.orderId});
	}
};

