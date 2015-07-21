Ext.namespace("Ext.ux.chooser");

/**
 * 文件上传
 * @param  config <br>
 * .title 	窗口名称 （默认）<br>
 * .objectType 	文件所属类别：产品/客户等 （必填）<br>
 * .objectId 	文件所属对象ID：产品ID/客户ID等 （必填）<br>
 * .callback 	回调方法 （必填）<br>
 */
Ext.ux.chooser.UploadFileWindow = function(config) {
	this.config = config?config:{};
	this.config.uploadUrl = '/fileupload/file-upload!uploadFile.do';
};

Ext.ux.chooser.UploadFileWindow.prototype = {
	show : function(){
		if(!this.win){
			var _this = this;
			var fileSelector = new Ext.ux.form.FileUploadField({
				layout : 'fit',
				height: 50,
				columnWidth : .7,
				hideLabel:true,
				emptyText : '选择文件...',
				name : 'file',
				buttonText : '',
				buttonCfg : {
					iconCls : 'upload-icon'
				}
			});
			
			var relativeURLFiled = new Ext.form.Hidden({id: 'relativeURL'});
			var uploadBtn = new Ext.Button({
				text : '上传文件',
				width:70,
				scope:this,
				handler : function() {
					
					hs.FormHelper.submit(_this.config.uploadUrl,
							function(form, action) {
								hs.MessageHelper.succuss({
									noSuccessTip:true,
									callback : function() {
										_this.win.close();
										_this.config.callback();
									}
								});
							},_this.config, panel.getForm());
				}
			});

			var panel = new Ext.form.FormPanel({
				fileUpload : true,
			    frame: true,
			    autoScroll: true,
			    labelAlign: 'right',
			    region: "center",
			    border:false,
			    items:[{
					layout: 'column',
					items : [{
				        columnWidth: .7,
				        items:[fileSelector]
				    },{
				        columnWidth: .2,
				        items:[uploadBtn]
				    }]}
				]
			});
			
		    var win = new Ext.Window({
				title: this.config.title || '上传文件...',
				layout: 'border',
				width : 500,
				height: 100,
				maximizable:true,
				items:[panel]
			});
		    
		    this.win = win;
		}
		this.win.show();
	}
};
