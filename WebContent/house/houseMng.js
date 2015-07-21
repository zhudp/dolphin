Ext.namespace("house.manage");

house.manage.url = {
	queryUrl : '/house/house!query.do',
	del : '/house/house!delete.do',
	getReseau : '/area/area!getReseauByCommunityId.do',
	printBarcode: '/house/house!printBarcode.do'
	
};

house.manage.record = [
	 {name:"houseId"}
	 ,{name:"address"}
	 ,{name:"policeId"}
	 ,{name:"policeName"}
	 ,{name:"communityId"}
	 ,{name:"communityName"}
	 ,{name:"reseauId"}
	 ,{name:"reseauName"}
	 ,{name:"structure"}
	 ,{name:"separateMode"}
	 ,{name:"use"}
	 ,{name:"areaAmount"}
	 ,{name:"propertyNo"}
	 ,{name:"grantDate"}
	 ,{name:"ownerId"}
	 ,{name:"ownerName"}
	 ,{name:"ownerTelphone"}
	 ,{name:"jointOwnerId"}
	 ,{name:"jointOwnerName"}
	 ,{name:"jointOwnerTelphone"}
	 ,{name:"ownerIdCard"}
	 ,{name:"houseFileNumber"}
	 ,{name:"gmtCreate"}
	 ,{name:"creator"}
	 ,{name:"gmtModify"}
	 ,{name:"modifier"}
];

/**
 * 主面板
 * @param {} config
 * @return {}
 */
house.manage.MainPanel = function(config) {
	this.config = config;
	this.structureTypeStore = hs.StoreFactory.getComboStore(comboJsonData["STRUCTURE_TYPE"], true);
};

house.manage.MainPanel.prototype = {
	showViewPort: function(){
		var topBar = this.getTopBar();
		var grid = this.getGrid();
		new Ext.Viewport({
				layout : "border",
				margins : '0 0 0 0',
				defaults : {
					border : false
				},
				items : [{
						region : "north",
						layout : 'fit',
						height: 55,
						//split: true,
						border : false,
						items : [topBar]
					}, {
						region : "center",
						layout : 'fit',
						id: 'gridArea',
						items : [grid]
					}]
			});
		this.searchFn();
	}	
	,getTopBar: function(){
		if(!this.tBar){
			var _this = this;
			
			this.communityIdText = new Ext.form.ComboBox({
				fieldLabel: '所属社区'
				,hiddenName : 'communityId'
				,displayField: 'text'
				,width: 150
				,value:this_user_CommunityId
				,disabled:this_user_CommunityId==null?false:true
				,store: hs.StoreFactory.getComboStore(comboJsonData["COMMUNITY"],true)
				,listeners : {
					 select : function(combo, record, index) {
						 _this.reseauIdText.setValue('');
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
			
			var r = new reseauStore.recordType({ id: '', text: ' - 不限 - ',cid:''}, Ext.id());
			reseauStore.insert(0, r);
			
			var reseauIdText = new Ext.form.ComboBox({
				 fieldLabel: '所属网格'
				,hiddenName : 'reseauId'
				,displayField: 'text'
				,width: 150
				,store:reseauStore
				,listeners : {
					'expand' : function(combo, record, index) {
						var _cid = _this.communityIdText.getValue();
						 reseauStore.filterBy(function(record, id) {
							 if(_cid =='' && record.get("cid") == '') {return true;}
							 else {
								 var cid = record.get("cid");
								 if (cid == _cid || cid =="")
									 return true;
								 else
									 return false;
							 }
						 });
					 }
				}
			});
			this.reseauIdText = reseauIdText;
			
			this.useField = new Ext.form.ComboBox({
				fieldLabel : '用途'
				,hiddenName : 'use'
				,displayField: 'text'
				,store: hs.StoreFactory.getComboStore(comboJsonData["USE_TYPE"],true)
				,width: 80
			});
			
			this.structureTypeComb =  new Ext.form.ComboBox({
				fieldLabel : '房屋结构',
				name : 'structure',
				hiddenName : 'structure',
				displayField : 'text',
				store: _this.structureTypeStore,
				editable:false,
				width: 110
			});
			
			
			this.addressText = new Ext.form.TextField({
				fieldLabel : '地址'
				,name : 'address'
				,width: 240
				,colspan: 4
				,listeners : {
					'specialkey' : {
						fn : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								_this.searchFn();
							}
						}
					}
				}
			});
			
			this.ownerNameText = new Ext.form.TextField({
				fieldLabel : '产权人'
				,name : 'ownerName'
				,width: 120
				,listeners : {
					'specialkey' : {
						fn : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								_this.searchFn();
							}
						}
					}
				}
			});
			
			this.propertyNoText = new Ext.form.TextField({
				fieldLabel : '产权证号'
				,name : 'propertyNo'
				,width: 120
				,listeners : {
					'specialkey' : {
						fn : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								_this.searchFn();
							}
						}
					}
				}
			});
			
			var toolbar = new Ext.Panel({
				frame : false,
				border : false,
				labelSeparator:'：',
				labelAlign:'right',
				tbar:[{
                   xtype: 'buttongroup',
                   columns: 13,
                   border: false,
                   labelAlign:'right',
                   //height:56,
                   defaults: {
                	   labelAlign:'right',
                	   scale: 'small'
                   },
                   items:[
                        new Ext.form.Label({text : "　社区："})
                        ,this.communityIdText
                        ,{xtype:'tbspacer',width:15}
                   		,new Ext.form.Label({text : "地址："})
                   		,this.addressText
                        ,{xtype:'tbspacer',width:15}
                        ,new Ext.form.Label({text : "　产权人："})
                        ,this.ownerNameText
                        ,{xtype:'tbspacer',width:15}
                        ,{
			                text: '查询',
			                scale: 'medium',
			                rowspan: 2,
							iconCls: 'hs-button-search',
			                cls: 'x-btn-as-arrow',
			                handler:function() {
			                	_this.searchFn();
							}
			            }
                   		,new Ext.form.Label({text : "　网格："})
                   		,this.reseauIdText
                   		,{xtype:'tbspacer',width:15}
                   		,new Ext.form.Label({text : "用途："})
                   		,this.useField
                   		,{xtype:'tbspacer',width:15}
                        ,new Ext.form.Label({text : "结构："})
                        ,this.structureTypeComb
                   		,{xtype:'tbspacer',width:15}
                   		,new Ext.form.Label({text : "产权证号："})
                   		,this.propertyNoText
                   ]
                  
				}]
			});
			this.tBar = toolbar;
		}
		return this.tBar;
	}
	,searchFn: function(){
		var store = this.getGrid().getStore();
		var paggingBar = this.getGrid().getBottomToolbar();
		var structure = this.structureTypeComb.getValue();
		if(structure == '-1'){
			structure = null;
		}
		hs.StoreHelper.beforeload(store, {
				 structure: structure
				,communityId: this.communityIdText.getValue()
				,address: this.addressText.getValue()
				,reseauId: this.reseauIdText.getValue()
				,ownerName: this.ownerNameText.getValue()
				,use: this.useField.getValue()
				,propertyNo: this.propertyNoText.getValue()
			});
		store.load({
			params : {
				start : 0,
				limit : paggingBar.pageSize || PAGE_SIZE
			}
		});
	}
	,initGridStore: function() {
		if (!this.gridStore) {
	        this.gridStore = hs.StoreFactory.getStore(
	        		house.manage.url.queryUrl, house.manage.record);
		};
		return this.gridStore;
	}
	,getGrid: function(){
		if (!this.houseGrid) {
			var _this = this;
			var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true });
			var gridStore = this.initGridStore();
			var grid = new Ext.grid.GridPanel({
				id: 'houseGrid',
				region: 'center',
                store: gridStore,
                autoScroll: true,
                cm: new Ext.grid.ColumnModel([ 
					new Ext.grid.RowNumberer()
					,sm
					,{header : "地址ID",dataIndex : 'houseId',width : 60 ,hidden:true}
					,{header : "地址",dataIndex : 'address',width : 170 ,sortable:true}
					,{header : "所属社区ID",dataIndex : 'communityId',width : 60  ,hidden:true}
					,{header : "所属社区",dataIndex : 'communityName',width : 140 ,sortable:true}
					,{header : "所属网格ID",dataIndex : 'reseauId',width : 60  ,hidden:true }
					,{header : "所属网格",dataIndex : 'reseauName',width : 140 ,sortable:true}
					,{header : "房屋结构",dataIndex : 'structure',width : 98
						,renderer: dolphin.resource.Util.render(_this.structureTypeStore)}
					,{header : "用途",dataIndex : 'use',width : 68
						,renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["USE_TYPE"]))}
					,{header : "产权人",dataIndex : 'ownerName',width : 60 }
					,{header : "产权证号",dataIndex : 'propertyNo',width : 90,hidden:true }
					,{header : "身份证",dataIndex : 'ownerIdCard',width : 110 ,hidden:true}
					,{header : "电话",dataIndex : 'ownerTelphone',width : 90 }
					,{header : "登记时间",dataIndex : 'gmtCreate',width : 76 }
					,{header : "登记人",dataIndex : 'creator',width : 76 }
					,{header : "更新时间",dataIndex : 'gmtModify',width : 76 ,sortable:true}
					,{header : "更新人",dataIndex : 'modifier',width : 76 }
				]),
                sm: sm,
                enableColumnHide: false,
				enableColumnMove: false,
				enableColumnResize: true,
				enableHdMenu: false,
				enableRowHeightSync: false,
				monitorWindowResize: false,
				stripeRows:true,
				trackMouseOver: true,
		        loadMask: {msg:'数据加载中，请稍候...'},
		        enableColLock: false,
		        bbar: new Ext.PagingToolbar({
		            pageSize: PAGE_SIZE,
		            store: gridStore,
		            displayInfo: true,
		            plugins: [
		            	new Ext.ux.plugins.PageComboResizer()
		            ]
		        }),
	        	tbar:[{
						text : "地址详情",
						iconCls : 'hs-button-view',
						scope : this,
						handler : function() {
							_this.showCard();
						}
					},'-', {
						text : "登记地址",
						id:'010101',
						iconCls : 'hs-button-add',
						scope : this,
						handler : function() {
							_this.showAddWin();
						}
					},'-', {
						text : "修改地址信息",
						id:'010102',
						iconCls : 'hs-button-edit',
						scope : this,
						handler : function() {
							_this.showUpdateWin();
						}
					},'-', {
						text : "删除",
						iconCls : 'hs-button-remove',
						scope : this,
						handler : function() {
							// 企业注销
							var record = this.getSelected();
							if(!record){
								return;
							}
							var _this = this;
							hs.MessageHelper.confirm('确定要删除？', function() {
								hs.ActionHelper.request(
										house.manage.url.del
										,{houseId: record.get('houseId')}
										,function(){_this.searchFn();}
								);
							});
						}
					},'-', {
						text : "查询日志",
						id:'010103',
						iconCls : 'icon_form_view',
						scope : this,
						handler : function() {
							var record = this.getSelected();
							if(!record){
								return;
							}
							this.getLogWin().show(record.get('houseId'), record.get('address'));
						}
					},'-', {
						text : "打印条码",
						iconCls : 'hs-button-print',
						scope : this,
						handler : function() {
							var record = this.getSelected();
							if(!record){
								return;
							}
							
							var houseId = record.get('houseId');
							var address = record.get('address');
							var printer = new Ext.ux.Printer({
								title : '打印条码-'+address,
								width : 700,
								height : 460,
								url : house.manage.url.printBarcode,
								param : {
									houseId : houseId
								},
								callback : function() {
									//do nothing
								}
							});
							printer.show();
						}
					}
	        	],
		        load: function(baseParams) {
		        	hs.StoreHelper.beforeload(gridStore, baseParams);
					gridStore.load({
						params: {
							start: 0,
							limit: PAGE_SIZE
						}
					});
				}
            });
			grid.addListener('rowdblclick', function(){_this.showCard();});
			this.houseGrid = grid;
		}
		return this.houseGrid;
	}
	,getSelected: function(){
		var records = this.getGrid().getSelectionModel().getSelections();
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
	,showCard: function(){
		var record = this.getSelected();
		if(!record){
			return;
		}
		if(!this.cardWin){
			var cardWin = new house.card.Win();
			this.cardWin = cardWin;
		}
		this.cardWin.show(record.get('houseId'));
	}
	,showAddWin: function(){
		if(!this.addWin){
			var _this = this;
			var addWin = new house.add.Win({
				callback:function() {
					_this.searchFn();
				}
			});
			this.addWin = addWin;
		}
		this.addWin.show();
	}
	,showUpdateWin: function(){
		var record = this.getSelected();
		if(!record){
			return;
		}
		if(!this.updateWin){
			var _this = this;
			var updateWin = new house.add.Win({
				callback:function() {
					_this.searchFn();
				}
			});
			this.updateWin = updateWin;
		}
		this.updateWin.show(record.get('houseId'));
	}
	,getPeopleWin: function(){
		var _this = this;
		if(!this.peopleWin){
			var peopleWin = new people.info.query.MainPanel({
				callback : _this.searchFn
			});
			this.peopleWin = peopleWin;
		}
		return this.peopleWin;
	}
	,getSelected: function(){
		var records = this.getGrid().getSelectionModel().getSelections();
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
	,getLogWin: function(){
		if(!this.logWin){
			var logWin = new house.log.logWin();
			this.logWin = logWin;
		}
		return this.logWin;
	}
};