Ext.namespace("custom.visit.card");

custom.visit.card.url = {
	 get: '/custom/custom!getCustomVisit.do'
	,save: '/custom/custom!saveCustomVisit.do'
};

/**
 * 信息卡片
 * @param {} config
 * @return {}
 */
custom.visit.card.Win = function(config) {
	this.config = config;
};

custom.visit.card.Win.prototype = {
	getCardForm: function(){
		if(!this.form1){
			var _this = this;
			var defaultsCfg = {
				disabled:true,
				//disabledClass:'text-readonly',
				anchor: '95%'
			};
			var oneClmCfg = {
				disabled:true,
				//disabledClass:'text-readonly',
				anchor: '97%'
			};
			
			var customIdField = new Ext.form.Hidden({fieldLabel: '客户ID',name: 'customId',disabled:true});
			var visitIdField = new Ext.form.Hidden({fieldLabel: '拜访ID',name: 'visitId',disabled:true});
			var visitDateField = new Ext.form.DateField({fieldLabel: '拜访日期',name: 'visitDate',allowBlank: false});
			var visitPeopleField = new Ext.form.TextField({fieldLabel: '执行人',name: 'visitPeople',allowBlank: false});
			var visitContextField = new Ext.form.TextArea({fieldLabel: '拜访内容',name: 'visitContext',height:160,allowBlank: false,maxLength:240});
			
			var customNameField =new Ext.ux.form.SearchField({
				fieldLabel : '客户名称',
				name:'customName',
				editable:false,
				allowBlank: false,
				doSearch:function(){
					var customChooser = new CustomChooser({
						callBack:function(customId, customName){
							customIdField.setValue(customId);
							customNameField.setValue(customName);
				    	}
					});
					customChooser.show();
				}
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
				items: [visitIdField,customIdField
			        ,hs.FormLayout.wholeOneColumnedRow(customNameField,oneClmCfg)
			        ,hs.FormLayout.twoColumnedRow(visitDateField,visitPeopleField,defaultsCfg)
					,hs.FormLayout.wholeOneColumnedRow(visitContextField,oneClmCfg)
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
				width : 480,
				height: 300,
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
						hs.FormHelper.submit(custom.visit.card.url.save,
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
	,show: function(visitId){
		this.getWin().show();
		this.getWin().setTitle("客户拜访记录");
		//载入信息
		if(visitId) {
			this.visitId = visitId;
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
		hs.FormHelper.load(custom.visit.card.url.get, {
			visitId: _this.visitId
		}, function(form, action) {
			var data = action.result.data;
			form.clearInvalid();
			form.setValues(data);
			_this.getWin().setTitle("客户拜访信息："+data.customNo+"__"+data.customName);
		}, formPanel.getForm());
	}
};

