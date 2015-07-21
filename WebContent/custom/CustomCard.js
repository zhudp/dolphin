Ext.namespace("custom.card");

custom.card.url = {
	 get: '/custom/custom!getCustom.do'
	,save: '/custom/custom!saveCustom.do'
};

/**
 * 信息卡片
 * @param {} config
 * @return {}
 */
custom.card.Win = function(config) {
	this.config = config;
};

custom.card.Win.prototype = {
	getCardForm: function(){
		if(!this.form1){
			var _this = this;
			var defaultsCfg = {
				disabled:true,
				//disabledClass:'text-readonly',
				anchor: '97%'
			};
			var oneClmCfg = {
				disabled:true,
				//disabledClass:'text-readonly',
				anchor: '98%'
			};
			
			var customIdField = new Ext.form.Hidden({fieldLabel: '客户ID',name: 'customId',disabled:true});
			var customNoField = new Ext.form.TextField({fieldLabel: '客户编号',name: 'customNo',maxLength:5,allowBlank: false,maxLength:5});
			var customNameField = new Ext.form.TextField({fieldLabel: '客户名称',name: 'customName',allowBlank: false});
			//var shortNameField = new Ext.form.TextField({fieldLabel: '客户简称',name: 'shortName'});
			var shopNumberField = new Ext.form.NumberField({fieldLabel: '目前开店数',name: 'shopNumber',allowNegative:false,minValue:0,maxValue:9999999});
			var shopNumberPlanField = new Ext.form.NumberField({fieldLabel: '计划开店数',name: 'shopNumberPlan',allowNegative:false,minValue:0,maxValue:9999999});
			var addressField = new Ext.form.TextField({fieldLabel: '地址',name: 'address'});
			var webSiteField = new Ext.form.TextField({fieldLabel: '网址',name: 'webSite'});
			var contacts1Field = new Ext.form.TextField({fieldLabel: '联系人1',name: 'contacts1'});
			var contacts1JobField = new Ext.form.TextField({fieldLabel: '职务',name: 'contacts1Job'});
			var contacts1Phone1Field = new Ext.form.TextField({fieldLabel: '电话1',name: 'contacts1Phone1'});
			var contacts1Phone2Field = new Ext.form.TextField({fieldLabel: '电话2',name: 'contacts1Phone2'});
			var contacts1RemarkField = new Ext.form.TextField({fieldLabel: '备注',name: 'contacts1Remark'});
			var contacts2Field = new Ext.form.TextField({fieldLabel: '联系人2',name: 'contacts2'});
			var contacts2JobField = new Ext.form.TextField({fieldLabel: '职务',name: 'contacts2Job'});
			var contacts2Phone1Field = new Ext.form.TextField({fieldLabel: '电话1',name: 'contacts2Phone1'});
			var contacts2Phone2Field = new Ext.form.TextField({fieldLabel: '电话2',name: 'contacts2Phone2'});
			var contacts2RemarkField = new Ext.form.TextField({fieldLabel: '备注',name: 'contacts2Remark'});
			var officerField = new Ext.form.TextField({fieldLabel: '客户经理',name: 'officer'});
			var salesmanField = new Ext.form.TextField({fieldLabel: '业务员',name: 'salesman'});
			var remarkField = new Ext.form.TextArea({fieldLabel: '备注',name: 'remark',height:44});

			var customTypeField = new Ext.form.ComboBox({
				fieldLabel : '客户类别'
				,hiddenName : 'customType'
				,displayField: 'text'
				,value:'2'
				,store: hs.StoreFactory.getComboStore(comboJsonData["CUSTOM_TYPE"])
			});
			var customStatusField = new Ext.form.ComboBox({
				 fieldLabel : '合作阶段'
				,hiddenName : 'customStatus'
				,displayField: 'text'
				,value:'3'
				,store: hs.StoreFactory.getComboStore(comboJsonData["CUSTOM_STATUS"])
			});
			var customIndustryField = new Ext.form.ComboBox({
				fieldLabel : '所属行业'
				,hiddenName : 'customIndustry'
				,displayField: 'text'
				,value:'1'
				,store: hs.StoreFactory.getComboStore(comboJsonData["CUSTOM_INDUSTRY"])
			});
			
			var form1 = new Ext.form.FormPanel({
				frame: true,
				labelAlign: 'right',
				region: "center",
				border: false,
				autoScroll: true,
	            defaults : {
					labelWidth : 70
				},
				items: [
					{
					    xtype:'fieldset',
					    title: '基本信息',
						labelAlign: 'right',
						bodyBorder: false,
						anchor: '97%',
					    items :[customIdField
					        ,hs.FormLayout.threeColumnedRow(customNameField,customNoField,customTypeField,defaultsCfg,0.46,0.23,0.3)
					        ,hs.FormLayout.threeColumnedRow(customIndustryField,shopNumberField,shopNumberPlanField,defaultsCfg)
					        ,hs.FormLayout.threeColumnedRow(customStatusField,officerField,salesmanField,defaultsCfg)
							,hs.FormLayout.wholeOneColumnedRow(addressField,oneClmCfg)
							,hs.FormLayout.wholeOneColumnedRow(webSiteField,oneClmCfg)
							,hs.FormLayout.wholeOneColumnedRow(remarkField,oneClmCfg)
			             ]
					}
	                ,{
			            xtype:'fieldset',
			            title: '联系人1',
						labelAlign: 'right',
						collapsible:true,
						bodyBorder: false,
						anchor: '97%',
			            items :[
			                 hs.FormLayout.twoColumnedRow(contacts1Field,contacts1JobField,defaultsCfg)
			                ,hs.FormLayout.twoColumnedRow(contacts1Phone1Field,contacts1Phone2Field,defaultsCfg)
			                ,hs.FormLayout.wholeOneColumnedRow(contacts1RemarkField,oneClmCfg)
			            ]
					}
	                ,{
	                	xtype:'fieldset',
	                	title: '联系人2',
	                	labelAlign: 'right',
	                	collapsible:true,
	                	collapsed:true,
	                	anchor: '97%',
	                	bodyBorder: false,
	                	items :[
                	         hs.FormLayout.twoColumnedRow(contacts2Field,contacts2JobField,defaultsCfg)
                	        ,hs.FormLayout.twoColumnedRow(contacts2Phone1Field,contacts2Phone2Field,defaultsCfg)
                	        ,hs.FormLayout.wholeOneColumnedRow(contacts2RemarkField,oneClmCfg)
                	    ]
	                }
		        ]
		     });
			this.form1 = form1;
		}
		return this.form1;
	}
	,getWin : function() {
		if (!this.win) {
			var _this = this;
			function eachItem(item,index,length) {
			    if (item.items && item.items.getCount() > 0) {
			       item.items.each(eachItem, this);
			    }
			    else if(!item.autoFill){
			    	item.setDisabled(!item.disabled);
			    }
			}
			
			var win = new Ext.Window({
				frame : true,
				width : 680,
				height: 500,
				closeAction : 'hide',
				layout: 'border',
				items:[this.getCardForm()],
		        tbar:[{
					text : "编辑",
					iconCls : 'hs-button-edit',
					scope : this,
					enableToggle:true,
					handler : function() {
						_this.getCardForm().items.each(eachItem, this);
						var saveBtn = win.getTopToolbar().get(1);
						saveBtn.setDisabled(!saveBtn.disabled);
					}
				},{
					text : "保存",
					iconCls : 'icon_save2',
					scope : this,
					disabled:true,
					handler : function() {
						hs.FormHelper.submit(custom.card.url.save,
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
				}
		              
		        ]
			});
			
			this.win = win;
		}
		return this.win;
	}
	,show: function(customId){
		this.getWin().show();
		this.getWin().setTitle("客户信息");
		//载入信息
		if(customId) {
			this.customId = customId;
			this.load();
		}
		// 新增
		else {
			this.getWin().getTopToolbar().get(0).toggle().handler();
		}
	}
	// 加载数据
	,load : function() {
		var _this = this;
		var formPanel = _this.getCardForm();
		hs.FormHelper.load(custom.card.url.get, {
			customId: _this.customId
		}, function(form, action) {
			var data = action.result.data;
			form.clearInvalid();
			form.setValues(data);
			_this.getWin().setTitle("客户信息："+data.customNo+"__"+data.customName);
		}, formPanel.getForm());
	}
};

