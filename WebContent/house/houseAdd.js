Ext.namespace("house.add");

house.add.url = {
	save: '/house/house!saveHouse.do',
	getHouseInfo: '/house/house!getHouseInfo.do'
};

/**
 * 地址信息卡片
 * @param {} config
 * @return {}
 */
house.add.Win = function(config) {
	this.config = config;
	this.structureTypeStore = hs.StoreFactory.getComboStore(comboJsonData["STRUCTURE_TYPE"], true);
	this.useTypeStore = hs.StoreFactory.getComboStore(comboJsonData["USE_TYPE"], true);
};

house.add.layerRecord= [
     {name:"layerId"}
	,{name:"houseId"}
	,{name:"layerNum"}
	,{name:"roomNum"}
	,{name:"use"}
	,{name:"separateMode"}
	,{name:"buildingArea"}
	,{name:"structure"}
	,{name:"tenantType"}
	,{name:"tenantId"}
	,{name:"tenantName"}
	,{name:"gmtCreate"}
	,{name:"creator"}
	,{name:"gmtModify"}
	,{name:"modifier"}
];

house.add.Win.prototype = {
	getTbar:function() {
		if(!this.tbar) {
			var _this = this;
			this.tbar = new Ext.Toolbar({
				items:[{
					text : '保存',
					iconCls : 'icon_save2',
					handler : function() {
						
						hs.FormHelper.submit(house.add.url.save,
								function(form, action) {
									hs.MessageHelper.succuss({
										callback : function() {
											form.clearValues();
											_this.config.callback();
											_this.getWin().hide();
										}
									});
								},null, _this.getCardForm().getForm());
					}
				},{
					text : '保存并继续',
					iconCls : 'icon_saveas',
					handler : function() {
												
						hs.FormHelper.submit(house.add.url.save,
							function(form, action) {
								hs.MessageHelper.succuss({
									callback : function() {
										_this.config.callback();
										
										_this.houseIdField.setValue('');
										_this.addressField.setValue('');
									}
								});
							},null, _this.getCardForm().getForm());
					}
				}]
			});
		}
		return this.tbar;
	},
	getCardForm: function(){
		if(!this.formPanel){
			var _this = this;
			var defaultsCfg = {
				anchor: '95%'
			};
			
			this.houseIdField = new Ext.form.Hidden({
				name : 'houseId'
			});
			this.addressField = new Ext.form.TextField({
				fieldLabel : '地址',
				emptyText: 'XX路X号XXX室',
				name : 'address',
				allowBlank:false,
				maxLength:50
			});
			
			this.policeNameField = new Ext.form.Hidden({
				name : 'policeName'
			});
			this.policeIdField = new Ext.form.ComboBox({
				fieldLabel: '派出所'
				,hiddenName : 'policeId'
				,displayField: 'text'
				,allowBlank:false
				,store: hs.StoreFactory.getComboStore(comboJsonData["POLICE"])
				,listeners : {
					 select : function(combo, record, index) {
						 _this.policeNameField.setValue(record.data.text);
						 _this.communityIdField.setValue('');
						 _this.reseauIdField.setValue('');
					 }
				}
			});
			
			
			var communityReader = new Ext.data.JsonReader({
				totalProperty : "totalCount",
				root : "result",
				id : "id"
			}, Ext.data.Record.create(['id', 'text', 'pid']));
				
			var communityStore = new Ext.data.Store({
				reader : communityReader,
				data:comboJsonData["COMMUNITY"]
			});
			this.communityNameField = new Ext.form.Hidden({
				name : 'communityName',
				allowBlank:true
			});

			this.communityIdField = new Ext.form.ComboBox({
				 fieldLabel: '所属社区'
				,hiddenName : 'communityId'
				,displayField: 'text'
				,width: 120
				,allowBlank:false
				,store:communityStore
				,listeners : {
					 select : function(combo, record, index) {
						 _this.communityNameField.setValue(record.data.text);
					 },
					 expand:function(combo, record, index) {
						 communityStore.filter('pid', _this.policeIdField.getValue());
					 }
				}
			});
			
			var reseauReader = new Ext.data.JsonReader({
				totalProperty : "totalCount",
				root : "result",
				id : "id"
			}, Ext.data.Record.create(['id', 'text', 'cid']));
				
			var reseauStore = new Ext.data.Store({
				reader : reseauReader,
				data:comboJsonData["RESEAU"]
			});
			this.reseauNameField = new Ext.form.Hidden({
				name : 'reseauName',
				allowBlank:true
			});
			this.reseauIdField = new Ext.form.ComboBox({
				 fieldLabel: '所属网格'
				,hiddenName : 'reseauId'
				,displayField: 'text'
				,width: 120
				,allowBlank:false
				,store:reseauStore
				,listeners : {
					 select : function(combo, record, index) {
						 _this.reseauNameField.setValue(record.data.text);
					 },
					 expand:function(combo, record, index) {
						 reseauStore.filter('cid', _this.communityIdField.getValue());
					 }
				}
			});
			
			this.areaAmountField = new Ext.form.NumberField({
				fieldLabel : '面积(㎡)',
				name : 'areaAmount',
				allowNegative:false,
				minValue:0,
				maxValue:9999999
			});
			this.separateModeField = new Ext.form.ComboBox({
				 fieldLabel : '隔离方式'
				,hiddenName : 'separateMode'
				,displayField: 'text'
				,store: hs.StoreFactory.getComboStore(comboJsonData["SEPARATE_TYPE"],"--")
			});
			this.structureField = new Ext.form.ComboBox({
				fieldLabel : '房屋结构'
				,hiddenName : 'structure'
				,displayField: 'text'
				,store: hs.StoreFactory.getComboStore(comboJsonData["STRUCTURE_TYPE"],"--")
			});
			this.useField = new Ext.form.ComboBox({
				fieldLabel : '用途'
				,hiddenName : 'use'
				,displayField: 'text'
				,allowBlank:false
				,store: hs.StoreFactory.getComboStore(comboJsonData["USE_TYPE"])
			});
			this.propertyNoField = new Ext.form.TextField({
				fieldLabel : '产权证号',
				name : 'propertyNo',
				maxLength:20
			});
			this.grantDateField = new Ext.form.DateField({
				fieldLabel : '发证日期',
				name : 'grantDate',
				format : 'Y-m-d'
			});
			this.ownerNameField = new Ext.form.TextField({
				fieldLabel : '产权人',
				name : 'ownerName',
				allowBlank:false
				,maxLength:12
			});
			this.ownerTelphoneField = new Ext.form.TextField({
				fieldLabel : '联系电话',
				name : 'ownerTelphone',
				maxLength:20
			});
			this.jointOwnerNameField = new Ext.form.TextField({
				fieldLabel : '共有产权人',
				name : 'jointOwnerName',
				maxLength:12
			});
			this.jointOwnerTelphoneField = new Ext.form.TextField({
				fieldLabel : '联系电话',
				name : 'jointOwnerTelphone',
				maxLength:12
			});
			var formPanel = new Ext.form.FormPanel({
		            autoHeight:true,
					frame: true,
					labelAlign: 'right',
					layout: 'form',
		            defaults : {
						labelWidth : 70
					},
					items: [
						{
						    xtype:'fieldset',
						    title: '基本信息',
							labelAlign: 'right',
							bodyBorder: false,
						    items :[
						         _this.houseIdField,_this.policeNameField,_this.communityNameField,_this.reseauNameField
								,hs.FormLayout.wholeOneColumnedRow(_this.addressField,{anchor: '97%'})
				                ,hs.FormLayout.threeColumnedRow(_this.policeIdField,_this.communityIdField,_this.reseauIdField,defaultsCfg)
				                ,hs.FormLayout.twoColumnedRow(_this.structureField,_this.separateModeField,defaultsCfg)
				                ,hs.FormLayout.twoColumnedRow(_this.useField,_this.areaAmountField,defaultsCfg)
				             ]
						}
		                ,{
				            xtype:'fieldset',
				            title: '产权信息',
							labelAlign: 'right',
							bodyBorder: false,
				            items :[
				                 hs.FormLayout.twoColumnedRow(_this.propertyNoField,_this.grantDateField,defaultsCfg)
				                ,hs.FormLayout.twoColumnedRow(_this.ownerNameField,_this.ownerTelphoneField,defaultsCfg)
				                ,hs.FormLayout.twoColumnedRow(_this.jointOwnerNameField,_this.jointOwnerTelphoneField,defaultsCfg)
				            ]
						}]
			});
			this.formPanel = formPanel;
		}
		return this.formPanel;
	}
	,getWin : function() {
		if (!this.win) {
			var win = new Ext.Window({
				frame : true,
				width : 800,
				layout : 'fit',
				autoHeight : true,
				closeAction : 'hide',
				items : this.getCardForm(),
				tbar:this.getTbar()
			});
			
			this.win = win;
		}
		return this.win;
	}
	,show: function(houseId){
		this.getWin().show();
		this.getWin().setTitle("地址信息");
		if(houseId) {
			this.load(houseId);
		}
	}
	// 加载数据
	,load : function(houseId) {
		var _this = this;
		var formPanel = this.getCardForm();
		hs.FormHelper.load(house.add.url.getHouseInfo, {
			houseId: houseId
		}, function(form, action) {
			var data = Ext.util.JSON.decode(action.result.data);
			form.clearInvalid();
			form.setValues(data);
			_this.getWin().setTitle(data.address);
		}, formPanel.getForm());
	},
	initStreetStore: function(){
		if(!this.streetStore){
			var streetStore = new Ext.data.Store({
				autoLoad: false, 
				proxy:new Ext.data.HttpProxy({
					url:Ext.getDom("root").value + "/area/area!getStreetList.do",
					method:'POST'
				}),
				reader:new Ext.data.ArrayReader({
					root : 'data'
				}, [
				    {name : 'streetName',mapping : 'streetName'},
				    {name : 'streetId',mapping : 'streetId'}
				])
			});
			this.streetStore = streetStore;
		}
		return this.streetStore;
	},
	getStreetSelector: function() {
		if(!this.streetSelector) {
			var store = this.initStreetStore();
			this.streetSelector = new Ext.form.ComboBox({
				fieldLabel : '地址',
				emptyText: '路/村/小区',
				hiddenName : 'streetId',
				store: store,
			    mode : 'local',
			    editable: true,
			    allowBlank:false,
			    name : 'streetId',
			    valueField : "streetId",
			    hideTrigger: true,
			    triggerAction : 'all',
			    //forceSelection:true,
		        selectOnFocus : true,
			    typeAhead: true,
		     	displayField : "streetName",
		     	validator:function() {
		     		var id = this.getValue();
		     		if(!/^[0-9]*$/.test(id)) {
		     			return "该道路/小区不存在，请核实后重新输入.";
		     		}
		     		return true;
		     	},
		     	listConfig: {
	                loadingText: '数据加载中...',
	                emptyText: '没有该道路，请核实后重新输入.'
		     	},
		     	listeners:{
					'beforequery': {
						fn: function(e){
							var value = this.getRawValue();
							if(!e.forceAll && getByteLen(value) >= 2){
								store.load({params: {streetName: value}});
								return false;
							}
						}
					}
			     	,'change': function(){

					}
			   	 }
			});
		}
		return this.streetSelector;
	}
};
