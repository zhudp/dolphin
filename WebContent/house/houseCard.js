Ext.namespace("house.card");

house.card.url = {
	getHouseInfo: '/house/house!getHouseInfo.do'
	,getTenantInfo:'/house/house!getTenantInfo.do'
};

/**
 * 房产信息卡片
 * @param {} config
 * @return {}
 */
house.card.Win = function(config) {
	this.config = config;
	this.numberStatusStore = hs.StoreFactory.getComboStore(comboJsonData["NUMBER_STATUS"], false);
};

house.card.Win.prototype = {
	getCardForm: function(){
		if(!this.tabPanel){
			var _this = this;
			var defaultsCfg = {
				readOnly: true,
				anchor: '95%',
				cls: 'text-readonly'
			};
			var oneClmCfg = {
				readOnly: true,
				anchor: '97%',
				cls: 'text-readonly'
			};
			
			var addressField = new Ext.form.TextField({fieldLabel : '地址',name : 'address'});
			this.addressField = addressField;
			var policeNameField = new Ext.form.TextField({fieldLabel : '派出所',name : 'policeName'});
			var communityNameField = new Ext.form.TextField({fieldLabel : '所属社区',name : 'communityName'});
			var reseauNameField = new Ext.form.TextField({fieldLabel : '所属网格',name : 'reseauName'});
			var areaAmountField = new Ext.form.TextField({fieldLabel : '总面积(㎡)',name : 'areaAmount'});
			var propertyNoField = new Ext.form.TextField({fieldLabel : '产权证号',name : 'propertyNo'});
			var grantDateField = new Ext.form.TextField({fieldLabel : '发证日期',name : 'grantDate'});
			var ownerNameField = new Ext.form.TextField({fieldLabel : '产权人',name : 'ownerName'});
			var ownerTelphoneField = new Ext.form.TextField({fieldLabel : '联系电话',name : 'ownerTelphone'});
			var jointOwnerNameField = new Ext.form.TextField({fieldLabel : '共有产权人',name : 'jointOwnerName'});
			var jointOwnerTelphoneField = new Ext.form.TextField({fieldLabel : '联系电话',name : 'jointOwnerTelphone'});
			var companyIdField = new Ext.form.Hidden({fieldLabel : '关联公司Id',name : 'companyId'});
			var companyNameField = new Ext.form.TextField ({
				fieldLabel : '关联公司',
				name : 'companyName',
				labelStyle:'color: #15428B;font: bold 12px tahoma,arial,helvetica,sans-serif;',
				readOnly: true,
				anchor: '97%',
				cls: 'text-readonly'
			});
			var companyInfoBtn = new Ext.Button({
				text : '查看详情',
				width:100,
                handler : function() {
                	if(companyIdField.getValue()) {
                		showCompanyCard(companyIdField.getValue(),companyNameField.getValue());
                	}else {
                		hs.MessageHelper.error({msg : "未关联公司"});
                	}
                }
            });
			
			var structureField = new Ext.form.ComboBox({
				fieldLabel : '房屋结构'
				,hiddenName : 'structure'
				,displayField: 'text'
				,store: hs.StoreFactory.getComboStore(comboJsonData["STRUCTURE_TYPE"])
			});
			var separateModeField = new Ext.form.ComboBox({
				 fieldLabel : '隔离方式'
				,hiddenName : 'separateMode'
				,displayField: 'text'
				,store: hs.StoreFactory.getComboStore(comboJsonData["SEPARATE_TYPE"])
			});
			var useField = new Ext.form.ComboBox({
				fieldLabel : '用途'
				,hiddenName : 'use'
				,displayField: 'text'
				,store: hs.StoreFactory.getComboStore(comboJsonData["USE_TYPE"])
			});
			
			var houseNumberComb = new Ext.form.ComboBox({
				fieldLabel : '户牌状态',
				displayField : 'text',
				hiddenName : 'houseNumberStatus',
				width: 120,
				readOnly: true,
    			cls: 'text-readonly',
				store: _this.numberStatusStore
			});
			this.houseNumberComb = houseNumberComb;
			
			var houseNumberText = new Ext.form.TextField({
    			fieldLabel : '专有房牌号',
    			name : 'houseNumber',
    			readOnly: true,
    			cls: 'text-readonly'
    		});
			this.houseNumberText = houseNumberText;
			
			var tabPanel = new Ext.form.FormPanel({
				frame: true,
				labelAlign: 'right',
				region: "center",
				border: false,
	            defaults : {
					labelWidth : 60
				},
				items: [
					{
					    xtype:'fieldset',
					    title: '基本信息',
						labelAlign: 'right',
						bodyBorder: false,
					    items :[
							 hs.FormLayout.wholeOneColumnedRow(addressField,oneClmCfg)
							,hs.FormLayout.threeColumnedRow(policeNameField,communityNameField,reseauNameField,defaultsCfg)
							,hs.FormLayout.twoColumnedRow(houseNumberText,houseNumberComb,defaultsCfg)
			                ,hs.FormLayout.twoColumnedRow(structureField,separateModeField,defaultsCfg)
				            ,hs.FormLayout.twoColumnedRow(useField,areaAmountField,defaultsCfg)
			             ]
					}
	                ,{
			            xtype:'fieldset',
			            title: '产权信息',
						labelAlign: 'right',
						bodyBorder: false,
			            items :[
			                 hs.FormLayout.twoColumnedRow(propertyNoField,grantDateField,defaultsCfg)
			                ,hs.FormLayout.twoColumnedRow(ownerNameField,ownerTelphoneField,defaultsCfg)
			                ,hs.FormLayout.twoColumnedRow(jointOwnerNameField,jointOwnerTelphoneField,defaultsCfg)
			            ]
					}
	                ,companyIdField
	                ,hs.FormLayout.twoColumnedRow(companyNameField,companyInfoBtn,0.15)
		        ]
		     });
			this.tabPanel = tabPanel;
		}
		return this.tabPanel;
	}
	,getTenantGrid:function() {
		if(!this.tenantGrid) {
			var _this = this;
			var gridStore = hs.StoreFactory.getStore(house.card.url.getTenantInfo, [
            	{name:"tenantType"}
            	,{name:"tenantId"}
            	,{name:"tenantName"}
            	,{name:"idcode"}
            ]);
			gridStore.on('load', function() {
				var arr = Ext.query('a[class="unlink_people"]');
			    for(var i = 0;i < arr.length; i++){  
			        var id = Ext.id(); 
			        arr[i].id = id;  
		            Ext.get(id).on('click',function(){
		            	_this.unlinkPeople();
		            });  
			    }});
			var grid = new Ext.grid.GridPanel({
				title:"住户/员工信息",
				width:280,
				store: gridStore,
				autoScroll: true,
				collapsible: true,
                split: true,
                region: "east",
				layout: 'fit',
				tbar:[{
					text : "登记人员",
					iconCls : 'hs-button-add',
					handler : function() {
						openUrlInTab("/people/peopleInfoAdd.do?from=house&houseId="+_this.houseId+"&address="+_this.addressField.getValue(),"登记人员");
					}
				},{
					text : "关联人员",
					iconCls : 'icon_group_link',
					handler : function() {
						var peopleChooser = new Ext.ux.chooser.PeopleChooser({
							linkTo:'house',
							house:{
								id:_this.houseId,
								address:_this.addressField.getValue()
							},
							callBack: function() {
								grid.load();
							}
						});
						
						peopleChooser.show();
					}
				},'-', {
					text : "刷新",
					iconCls : 'icon_refresh',
					scope : this,
					handler : function() {
						grid.load();
					}
				}],
				cm: new Ext.grid.ColumnModel([ 
				  new Ext.grid.RowNumberer()
	              ,{header : "住户ID",dataIndex : 'tenantId',width : 60 ,hidden:true }
	              ,{header : "姓名",dataIndex : 'tenantName',width : 60,
	            	  renderer: function(value, meta, record){
	            		  if(value == '') value = '&lt;无名&gt;';
            			  return '<a href="javascript:void(0)" class="link" onClick=showPeopleCard(\"'+record.get('tenantId')+'\",\"'+value+'\") >'+value+' </a>';
						}}
	               ,{header : "身份证号",dataIndex : 'idcode',width : 120}
	               ,{header : "　",dataIndex : 'tenantId',width : 46,align:'center',
		       			renderer: function(val){
		    				return '<a href="#" class="unlink_people" peopleId='+val+'>删除</a>';
		    			}
	                }
	            ]),
		          enableColumnHide: false,
		          enableColumnMove: false,
		          enableColumnResize: true,
		          enableHdMenu: false,
		          enableRowHeightSync: false,
		          monitorWindowResize: false,
		          trackMouseOver: false,
		          loadMask: {msg:'数据加载中，请稍候...'},
		          enableColLock: false,
		          load: function() {
		        	  hs.StoreHelper.beforeload(gridStore, {houseId: _this.houseId});
		        	  gridStore.load({
						params: {
								start: 0,
								limit: 15
							}
						});
		          },
		          viewConfig: {
		        	  forceFit:true
		          },
		          bbar: new Ext.PagingToolbar({
		            pageSize: 15,
		            store: gridStore,
		            displayInfo: true,
		            displayMsg:'{2}条',
		            emptyMsg:'0条',
		            hideBorders:true
		        })
			});
			this.tenantGrid = grid;
		}
		
		return this.tenantGrid;
	}
	,getWin : function() {
		if (!this.win) {
			var win = new Ext.Window({
				frame : true,
				width : 860,
				height:454,
				closeAction : 'hide',
				layout: 'border',
				items:[this.getCardForm(),this.getTenantGrid()]
			});
			
			this.win = win;
		}
		return this.win;
	}
	,show: function(houseId){
		this.getWin().show();
		this.getWin().setTitle("地址信息");
		this.houseId = houseId;
		// 加载数据
		this.load();
	}
	// 加载数据
	,load : function() {
		var _this = this;
		var formPanel = this.getCardForm();
		hs.FormHelper.load(house.card.url.getHouseInfo, {
			houseId: _this.houseId
		}, function(form, action) {
			var data = Ext.util.JSON.decode(action.result.data);
			form.clearInvalid();
			form.setValues(data);
			_this.getWin().setTitle(data.address);
			if(_this.houseNumberComb.getValue() != '2'){ 
				_this.houseNumberComb.addClass('red');
				if(_this.houseNumberText.getValue() == ''){
					_this.houseNumberText.setValue('无牌号');
				}
				_this.houseNumberText.addClass('red');
			}
		}, formPanel.getForm());
		
		// 加载住户信息 jinnie 2012-09-05
		this.getTenantGrid().load();
	},
	unlinkPeople:function() {
		var _this = this;
		var record = this.getTenantGrid().getSelectionModel().getSelected();
		var peopleId = record.get('tenantId');
		var peopleName = record.get('tenantName');
		
		hs.MessageHelper.confirm('确定删除『'+peopleName+'』与当前住址的关联？', function() {
			_this.getTenantGrid().el.mask("正在解除人员关联，请稍候...");
			if(!_this.tipw) {
				_this.tipw = new Ext.ux.TipsWindow({
					border:false,
					html:'<div class="x-window-dlg"><div class="x-window-body"><div class="x-dlg-icon"><div class="ext-mb-icon ext-mb-success"></div><div class="ext-mb-content"><span class="ext-mb-text" style="line-height:30px;">操作成功！</span><br></div></div></div></div>'
				});  
			}
			hs.ActionHelper.request('/people/people-info!unlinkPeople.do',
					{peopleId:peopleId,houseId:_this.houseId},
					null,
					function(result,request){
						_this.getTenantGrid().el.unmask();
						var re = Ext.util.JSON.decode(result.responseText);
						if(re.success){
							_this.getTenantGrid().load();
							_this.tipw.show();
						}
						else{
							
						}
					}
			);
		});
	}
};

var companyCard,peopleCard;
/**
 * 显示企业卡片
 * @param companyId
 * @param companyName
 */
function showCompanyCard(companyId,companyName) {
	if(!companyCard) {
		companyCard = new company.card.infoWin();
	}
	companyCard.show(companyId, companyName);
}

/**
 * 显示人员卡片
 * @param peopleId
 */
function showPeopleCard(peopleId) {
	if(!peopleCard) {
		peopleCard = new people.detail.DetailPanel({});
	}
	peopleCard.load(peopleId);
	peopleCard.getWin().show();
}
