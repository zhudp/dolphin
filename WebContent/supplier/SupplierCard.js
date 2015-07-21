Ext.namespace("Supplier.card");
/**
 * 原料信息编辑/添加窗口
 * @param config{}
 * @returns {supplier.card.Win}
 */
Supplier.card.Win = function(config) {
	Supplier.card.Win.superclass.constructor.call(this, config);
	if(!Ext.isDefined(this.url)){
		this.url={};
	};
	this.url.get = '/supplier/supplier!getSupplier.do';
	this.url.save ='/supplier/supplier!saveSupplier.do';
};
/**
 * 基于common.card.Win的编辑窗口
 */
Ext.extend(Supplier.card.Win,Commons.card.Win, {
	/**
	 * 窗口标题获取
	 * @param data
	 * @returns
	 */
	getWindTitle : function(data){
		if(data){
			return this.title +"："+data.supplierNo+"__"+data.supplierName;
		}else{
			return this.title;
		}
	}
	/**
	 * 表单数据加载参数
	 */
	,getLoadParam : function(_this){
		return {supplierId: _this.id};
	}
	/**
	 * 创建表单
	 * @returns {Array}
	 */
	,createFormItem : function(){
		var defaultsCfg = {
			disabled:true,
			anchor: '95%'
		};
		var oneClmCfg = {
			disabled:true,
			anchor: '97%'
		};
		this.supplierIdField = new Ext.form.Hidden({fieldLabel: '原料ID',name: 'supplierId',autoFill:true});
		this.supplierNameField = new Ext.form.TextField({fieldLabel : '供应商名称',id : 'supplierName',allowBlank : false});
		this.supplierNoField = new Ext.form.TextField({fieldLabel : '供应商编号',id : 'supplierNo',allowBlank : false});
		this.supplyRangeField = new Ext.form.TextField({fieldLabel : '供应范围',id : 'supplyRange'});
		this.bankAccountField = new Ext.form.TextField({fieldLabel : '银行账号',id : 'bankAccount'});
		this.accountNameField = new Ext.form.TextField({fieldLabel : '账号户名',id : 'accountName',});
		this.depositBankField = new Ext.form.TextField({fieldLabel : '开户行',id : 'depositBank'});
		this.addressField = new Ext.form.TextField({fieldLabel : '地址',id : 'address'});
		this.webSiteField = new Ext.form.TextField({fieldLabel : '网址',id : 'webSite'});
		this.contacts1Field = new Ext.form.TextField({fieldLabel : '联系人',id : 'contacts1'});
		this.contacts1JobField = new Ext.form.TextField({fieldLabel : '职务',id : 'contacts1Job'});
		this.contacts1Phone1Field = new Ext.form.TextField({fieldLabel : '电话1',id : 'contacts1Phone1'});
		this.contacts1Phone2Field = new Ext.form.TextField({fieldLabel : '电话2',id : 'contacts1Phone2'});
		this.contacts1RemarkField = new Ext.form.TextArea({fieldLabel : '备注',id : 'contacts1Remark',height:40});
		this.contacts2Field = new Ext.form.TextField({fieldLabel : '联系人',id : 'contacts2'});
		this.contacts2JobField = new Ext.form.TextField({fieldLabel : '职务',id : 'contacts2Job'});
		this.contacts2Phone1Field = new Ext.form.TextField({fieldLabel : '电话1',id : 'contacts2Phone1'});
		this.contacts2Phone2Field = new Ext.form.TextField({fieldLabel : '电话2',id : 'contacts2Phone2'});
		this.contacts2RemarkField = new Ext.form.TextArea({fieldLabel : '备注',id : 'contacts2Remark',height:40});
		this.officerField = new Ext.form.TextField({fieldLabel : '客户经理',id : 'officer'});
		this.remarkField = new Ext.form.TextArea({fieldLabel : '备注',id : 'remark',height:40});
		this.supplyStatusField = new Ext.form.ComboBox({	fieldLabel : '合作阶段',hiddenName : 'supplyStatus',displayField: 'text'
			,store: hs.StoreFactory.getComboStore(comboJsonData["SUPPLIER_TYPE"])
		});
		var items = [
				{
					xtype : 'fieldset',
					title : '基本信息',
					labelAlign : 'right',
					bodyBorder : false,
					anchor : '97%',
					items : [
							this.supplierIdField,
							hs.FormLayout.threeColumnedRow(
									this.supplierNoField,
									this.supplierNameField,
									this.supplyStatusField, defaultsCfg),
							hs.FormLayout.threeColumnedRow(
									this.depositBankField,
									this.accountNameField,
									this.bankAccountField, defaultsCfg),
							hs.FormLayout.threeColumnedRow(this.officerField,
									this.addressField, this.webSiteField,
									defaultsCfg), 
									hs.FormLayout.wholeOneColumnedRow(
											this.supplyRangeField , oneClmCfg) 
									]
				},
				{
					xtype : 'fieldset',
					title : '联系人1',
					labelAlign : 'right',
					collapsible : true,
					bodyBorder : false,
					anchor : '97%',
					items : [
							hs.FormLayout.twoColumnedRow(this.contacts1Field,
									this.contacts1JobField, defaultsCfg),
							hs.FormLayout.twoColumnedRow(
									this.contacts1Phone1Field,
									this.contacts1Phone2Field, defaultsCfg),
							hs.FormLayout.wholeOneColumnedRow(
									this.contacts1RemarkField, oneClmCfg) ]
				},
				{
					xtype : 'fieldset',
					title : '联系人2',
					labelAlign : 'right',
					collapsible : true,
					collapsed : true,
					bodyBorder : false,
					anchor : '97%',
					items : [
							hs.FormLayout.twoColumnedRow(this.contacts2Field,
									this.contacts2JobField, defaultsCfg),
							hs.FormLayout.twoColumnedRow(
									this.contacts2Phone1Field,
									this.contacts2Phone2Field, defaultsCfg),
							hs.FormLayout.wholeOneColumnedRow(
									this.contacts2RemarkField, oneClmCfg) ]
				}, hs.FormLayout.wholeOneColumnedRow(this.remarkField, oneClmCfg) ];
		return items;
	}
});
