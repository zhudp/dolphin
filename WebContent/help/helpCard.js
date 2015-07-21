Ext.namespace("help.card");

help.card.url = {
	getHelpInfo: '/help/help-faq!get.do'
};

/**
 * 知识库信息卡片
 * @param {} config
 * @return {}
 */
help.card.infoWin = function(config) {
	this.config = config;
	this.helpTypeStore = hs.StoreFactory.getComboStore(comboJsonData["COMPANY_TYPE"], false);
};

help.card.infoWin.prototype = {
	getCardForm: function(){
		if(!this.tabPanel){
			
			var titleField = new Ext.form.TextField({
				fieldLabel : '标题'
				,name : 'title'
				,readOnly: true
				,width: 220
				,cls:'text-readonly'
			});
			this.titleField = titleField;
			
			var helpTypeField = new Ext.form.ComboBox({
				fieldLabel: '主题'
				,hiddenName : 'helpType'
				,displayField: 'text'
				,readOnly:true
				,cls:'text-readonly'
				,width: 140
				,store: dolphin.resource.Util.getHelpTypeComboStore()
			});
			this.helpTypeField = helpTypeField;
			
			var keyWord = new Ext.form.TextField({
				fieldLabel : '关键字'
				,name : 'keyWord'
				,readOnly: true
				,width : 140
				,cls:'text-readonly'
			});
			this.keyWord = keyWord;
			
			var content = new Ext.form.TextArea({
				fieldLabel : '描述'
				,name : 'content'
				,readOnly: true
				,cls:'text-readonly'
				,height: 120
				,width : 260
			});
			this.content = content;
			
			var statusField = new Ext.form.ComboBox({
				fieldLabel: '状态'
				,hiddenName : 'status'
				,displayField: 'text'
				,readOnly:true
				,cls:'text-readonly'
				,width: 100
				,store: dolphin.resource.Util.getYesOrNoComboStore()
			});
			this.statusField = statusField;
			
			var browseCount = new Ext.form.TextField({
				fieldLabel : '浏览次数'
				,name : 'browseCount'
				,readOnly: true
				,cls:'text-readonly'
			});
			this.browseCount = browseCount;
			
			var tabPanel = new Ext.form.FormPanel({
				frame: true,
			    layout: 'form',
			    autoScroll: true,
			    baseCls: 'x-panel',
			    bodyStyle: 'padding:5px 5px 0',
			    labelAlign: 'right',
			    height : 220,
			    defaults : {
					labelWidth : 80
				},
				items: [
				        hs.FormLayout.wholeOneColumnedRow(
				        	helpTypeField
					    	,{anchor: '90%'}
						)
						,hs.FormLayout.wholeOneColumnedRow(
							titleField
					    	,{anchor: '90%'}
						)
						,hs.FormLayout.wholeOneColumnedRow(
							keyWord
					    	,{anchor: '90%'}
						)
		                ,hs.FormLayout.wholeOneColumnedRow(
	                		content
					    	,{anchor: '90%'}
						)
		                ,hs.FormLayout.wholeOneColumnedRow(
							browseCount
							,{anchor: '90%'}
		                )
		           ]
			});
			this.tabPanel = tabPanel;
		}
		return this.tabPanel;
	}	
	,getWin : function() {
		if (!this.win) {
			var tabPanel = this.getCardForm();
			var win = new Ext.Window({
				frame : true,
				width : 500,
				height : 290,
				layout : 'fit',
				closeAction : 'hide',
				items : tabPanel
			});
			this.win = win;
		}
		return this.win;
	}
	,show: function(id,title){
		this.getWin().show();
		if(title){
			this.getWin().setTitle(title);
		}
		else{}
		// 加载数据
		this.load(id);
	}
	// 加载数据
	,load : function(id) {
		var tabPanel = this.getCardForm();
		hs.FormHelper.load(help.card.url.getHelpInfo, {
			id: id
		}, this.successFn, tabPanel.getForm());
	}
	
	,successFn: function(form, action){
		var data = action.result.data;
		if(data instanceof Array){
			var deCodeData = Ext.util.JSON.decode(data);
			form.clearInvalid();
			form.setValues(deCodeData);
		}
	}
	
};