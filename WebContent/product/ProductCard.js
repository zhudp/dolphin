Ext.namespace("product.card");

product.card.url = {
	 get: '/product/product!getProduct.do'
	,save: '/product/product!saveProduct.do'
};

/**
 * 信息卡片
 * @param {} config
 * @return {}
 */
product.card.Win = function(config) {
	this.config = config;
};

product.card.Win.prototype = {
	getCardForm: function(){
		
		if(!this.form1){
			var _this = this;
			var defaultsCfg = {
				disabled:true,
				anchor: '95%'
			};
			var oneClmCfg = {
				disabled:true,
				anchor: '97%'
			};
			
			var productIdField = new Ext.form.Hidden({fieldLabel: '产品ID',name: 'productId'});
			var productPicpathField = new Ext.form.Hidden({fieldLabel: '产品照片',name: 'productPicpath'});
			var productNoField = new Ext.form.TextField({fieldLabel: '产品编号',name: 'productNo',emptyText:'自动编号',autoFill:true});
			var productNameField = new Ext.form.TextField({fieldLabel: '产品名称',name: 'productName',allowBlank: false});
			var standardField = new Ext.form.TextField({fieldLabel: '规格',name: 'standard',allowBlank: false});
			var priceField  = new Ext.form.NumberField({fieldLabel: '产品价格',name: 'price',value:0,allowBlank: false,minValue:0,maxValue:9999999,selectOnFocus:true});
			var primeCostField  = new Ext.form.NumberField({fieldLabel: '成本价',name: 'primeCost',value:0,allowBlank: false,minValue:0,maxValue:9999999,selectOnFocus:true});
			var minStoreField = new Ext.form.NumberField({fieldLabel: '最小库存量',name: 'minStore',value:0,allowBlank: false,minValue:0,maxValue:9999999,selectOnFocus:true,decimalPrecision:0});
			//var maxStoreField = new Ext.form.TextField({fieldLabel: '最大库存量',name: 'maxStore',allowBlank: false});
			
			var unitField = new Ext.form.ComboBox({
				 fieldLabel : '单位'
				,hiddenName : 'unit'
				,displayField: 'text'
				,allowBlank: false
				,editable:true
				,store: hs.StoreFactory.getComboStore(comboJsonData["USUAL_UNIT"])
			});
			
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

			var remarkField = new Ext.form.TextArea({fieldLabel: '备注',name: 'remark',height:44});

			var productTypeField = new Ext.form.ComboBox({
				 fieldLabel : '产品类别'
				,hiddenName : 'productType'
				,displayField: 'text'
				,allowBlank: false
				,store: hs.StoreFactory.getComboStore(comboJsonData["PRODUCT_TYPE"])
			});
			var productStatusField = new Ext.form.ComboBox({
				 fieldLabel : '产品状态'
				,hiddenName : 'productStatus'
				,displayField: 'text'
				,value:'1'
				,store: hs.StoreFactory.getComboStore(comboJsonData["PRODUCT_STATUS"])
			});
			
			var pictureView = new Ext.BoxComponent({
				fieldLabel : '　',
				labelSeparator:'',
			    autoEl: {
			    	height:200,
			        tag: 'img',
			        src: ''
			    }
			});
			
			var uploadBtn = new Ext.Button({
				fieldLabel : '产品照片',
				text : '上传图片',
				width:70,
				disabled:true,
				handler : function() {
					var uploadImageWindow = new Ext.ux.chooser.UploadImageWindow({
						pictureType:'product',
						callback:function(fullUrl, value) {
							pictureView.el.dom.src = fullUrl;
							productPicpathField.setValue(value); 
						}
					});
					uploadImageWindow.show();
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
				items: [productIdField,customIdField,productPicpathField
			        ,hs.FormLayout.twoColumnedRow(customNameField,customNoField,defaultsCfg)
			        ,hs.FormLayout.twoColumnedRow(productNoField,productNameField,defaultsCfg)
			        ,hs.FormLayout.twoColumnedRow(standardField,productStatusField,defaultsCfg)
					,hs.FormLayout.twoColumnedRow(unitField,minStoreField,defaultsCfg)
					,hs.FormLayout.twoColumnedRow(priceField,primeCostField,defaultsCfg)
					,hs.FormLayout.wholeOneColumnedRow(remarkField,oneClmCfg)
					,hs.FormLayout.wholeOneColumnedRow(uploadBtn,{})
					,hs.FormLayout.wholeOneColumnedRow(pictureView,{})
		        ]
		     });
			form1.pictureView = pictureView;
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
			    	if(item.isXType("textfield") || item.isXType("button") ||  item.isXType("searchfield")) {
			    		item.setDisabled(!item.disabled);
			    	}
			    }
			}
			
			var win = new Ext.Window({
				frame : true,
				width : 640,
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
						hs.FormHelper.submit(product.card.url.save,
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
	,show: function(productId){
		this.getWin().show();
		this.getWin().setTitle("产品信息");
		//载入信息
		if(productId) {
			this.productId = productId;
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
		hs.FormHelper.load(product.card.url.get, {
			productId: _this.productId
		}, function(form, action) {
			var data = action.result.data;
			form.clearInvalid();
			form.setValues(data);
			_this.getCardForm().pictureView.el.dom.src = data.pictrueFullUrl;
			_this.getWin().setTitle("产品信息："+data.productNo+"__"+data.productName);
		}, formPanel.getForm());
	}
};

