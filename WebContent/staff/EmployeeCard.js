Ext.namespace("employee.card");

employee.card.url = {
	 get: '/staff/employee!getEmployee.do'
	,save: '/staff/employee!saveEmployee.do'
};

/**
 * 信息卡片
 * @param {} config
 * @return {}
 */
employee.card.Win = function(config) {
	this.config = config;
};

employee.card.Win.prototype = {
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
			
			var employeeIdField = new Ext.form.Hidden({fieldLabel: '员工ID',name: 'employeeId',disabled:true});
			var employeeNoField = new Ext.form.TextField({fieldLabel: '员工编号',name: 'employeeNo',maxLength:5,allowBlank: false,emptyText:'自动编号',autoFill:true});
			
			var nameField                  = new Ext.form.TextField({fieldLabel:'姓名',name:'name',allowBlank:false});
			var nationField                 = new Ext.form.TextField({fieldLabel:'民族',name:'nation'});
			var nativePlaceField          = new Ext.form.TextField({fieldLabel:'籍贯',name:'nativePlace'});
			var birthDateField             = new Ext.form.DateField({fieldLabel:'出生日期',name:'birthDate'});
			var idCodeField                = new Ext.form.TextField({fieldLabel:'身份证号',name:'idCode'});
			//var pictureField                = new Ext.form.TextField({fieldLabel:'员工照片',name:'picture'});
			var entryDateField            = new Ext.form.DateField({fieldLabel:'入职时间',name:'entryDate'});
			var leaveDateField            = new Ext.form.DateField({fieldLabel:'离职时间',name:'leaveDate'});
			var homeAddressField       = new Ext.form.TextField({fieldLabel:'家庭住址',name:'homeAddress'});
			var mobileField                 = new Ext.form.TextField({fieldLabel:'手机号码',name:'mobile'});
			var phoneField                 = new Ext.form.TextField({fieldLabel:'电话',name:'phone'});
			var jobField                      = new Ext.form.TextField({fieldLabel:'工种',name:'job'});
//			var emergencyPhoneField = new Ext.form.TextField({fieldLabel:'应急电话',name:'emergencyPhone'});
//			var emergencyPeopleField = new Ext.form.TextField({fieldLabel:'应急联系人',name:'emergencyPeople'});
			var remarkField = new Ext.form.TextArea({fieldLabel: '备注',name: 'remark',height:44});

			var sexField = new Ext.form.ComboBox({
				fieldLabel : '性别'
				,hiddenName : 'sex'
				,displayField: 'text'
				,value:'1'
				,store: hs.StoreFactory.getComboStore(comboJsonData["SEX"])
			});
			var statusField = new Ext.form.ComboBox({
				fieldLabel : '在职状态'
				,hiddenName : 'status'
				,displayField: 'text'
				,value:'1'
				,store: hs.StoreFactory.getComboStore(comboJsonData["ISWORK"])
			});
			var maritalStatusField = new Ext.form.ComboBox({
				fieldLabel : '婚姻状况'
				,hiddenName : 'maritalStatus'
				,displayField: 'text'
				,value:'10'
				,store: hs.StoreFactory.getComboStore(comboJsonData["MARITAL_STATUS"])
			});
			var educationField = new Ext.form.ComboBox({
				fieldLabel : '文化程度'
				,hiddenName : 'education'
				,displayField: 'text'
				,value:'70'
				,store: hs.StoreFactory.getComboStore(comboJsonData["EDUCATION"])
			});
			
			var departmentIdField = new QM.ui.TreeCombo({
		        clearCls: 'allow-float', 
				xtype : 'treecombo',
				name : "departmentId",
				hiddenName:'departmentId',
				fieldLabel : '所属部门',
				lazyInit : true,
				editable : false, // 如果树中结点不是一次全部加载请请此项设为false
				ignoreFolder : false,
				tree : {
					 loader : dolphin.resource.Util.getDeptComboTreeLoader(),
					 root : new Ext.tree.AsyncTreeNode({
						text : '所有部门',
						draggable : false,
						id : '-1'
					})
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
				items: [
					{
					    xtype:'fieldset',
					    title: '基本信息',
						labelAlign: 'right',
						bodyBorder: false,
						anchor: '97%',
					    items :[employeeIdField
					        ,hs.FormLayout.threeColumnedRow(nameField,employeeNoField,sexField,defaultsCfg,0.36,0.31,0.31)
					        ,hs.FormLayout.threeColumnedRow(idCodeField,birthDateField,nationField,defaultsCfg,0.36,0.31,0.31)
					        ,hs.FormLayout.threeColumnedRow(nativePlaceField,educationField,maritalStatusField,defaultsCfg,0.36,0.31,0.31)
			             ]
					}
					,{
	                	xtype:'fieldset',
	                	title: '工作信息',
	                	labelAlign: 'right',
	                	collapsible:true,
	                	bodyBorder: false,
	                	anchor: '97%',
	                	items :[
	                	         hs.FormLayout.threeColumnedRow(statusField,entryDateField,leaveDateField,defaultsCfg)
	                	        ,hs.FormLayout.threeColumnedRow(departmentIdField,jobField,null,defaultsCfg)
	                	]
	                }
	                ,{
			            xtype:'fieldset',
			            title: '联系方式',
						labelAlign: 'right',
						collapsible:true,
						bodyBorder: false,
						anchor: '97%',
			            items :[
			                 hs.FormLayout.twoColumnedRow(mobileField,phoneField,defaultsCfg)
			                //,hs.FormLayout.twoColumnedRow(emergencyPeopleField,emergencyPhoneField,defaultsCfg)
			                ,hs.FormLayout.wholeOneColumnedRow(homeAddressField,oneClmCfg)
			                ,hs.FormLayout.wholeOneColumnedRow(remarkField,oneClmCfg)
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
				height: 490,
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
						hs.FormHelper.submit(employee.card.url.save,
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
	,show: function(employeeId){
		this.getWin().show();
		this.getWin().setTitle("员工信息");
		//载入信息
		if(employeeId) {
			this.employeeId = employeeId;
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
		hs.FormHelper.load(employee.card.url.get, {
			employeeId: _this.employeeId
		}, function(form, action) {
			var data = action.result.data;
			form.clearInvalid();
			form.setValues(data);
			var departmentIdFiled = form.findField('departmentId');
			departmentIdFiled.value=data.departmentId;
			departmentIdFiled.el.dom.value = data.departmentName;
			_this.getWin().setTitle("员工信息："+data.name);
		}, formPanel.getForm());
	}
};

