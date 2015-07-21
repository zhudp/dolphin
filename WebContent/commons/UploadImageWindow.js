Ext.namespace("Ext.ux.chooser");

/**
 * 图片上传
 * @param  config <br>
 * .title 	窗口名称 （默认）<br>
 * .callback 	回调方法 （必填）<br>
 * .pageSize 	显示行数 （默认15）<br>
 */
Ext.ux.chooser.UploadImageWindow = function(config) {
	this.config = config?config:{};
	this.config.uploadUrl = '/fileupload/file-upload!uploadPicture.do';
};

Ext.ux.chooser.UploadImageWindow.prototype = {
	show : function(){
		if(!this.win){
			var _this = this;
			var fileSelector = new Ext.ux.form.ImageUploadField({
				layout : 'fit',
				height: 50,
				columnWidth : .7,
				hideLabel:true,
				emptyText : '选择图片...',
				name : 'file',
				buttonText : '',
				buttonCfg : {
					iconCls : 'upload-icon-image'
				}
			});
			
			var pictureView = new Ext.BoxComponent({
			    autoEl: {
			    	height:'80%',
			        tag: 'img',
			        src: ''
			    }
			});
			var relativeURLFiled = new Ext.form.Hidden({id: 'relativeURL'});
			var uploadBtn = new Ext.Button({
				text : '上传图片',
				width:70,
				scope:this,
				handler : function() {
					
					hs.FormHelper.submit(_this.config.uploadUrl,
							function(form, action) {
								hs.MessageHelper.succuss({
									noSuccessTip:true,
									callback : function() {
										var fullURL = action.result.data[0].fullURL;
										var relativeURL = action.result.data[0].relativeURL;
										pictureView.el.dom.src = fullURL;
										relativeURLFiled.setValue(relativeURL);
									}
								});
							},_this.config, panel.getForm());
					//pictureView.el.dom.src =window.URL.createObjectURL(fileSelector.fileInput.dom.files[0]);  
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
				    }]},pictureView
				]
			});
			
		    var win = new Ext.Window({
				title: this.config.title || '上传图片...',
				layout: 'border',
				width : 600,
				height: 464,
				maximizable:true,
				items:[panel],
				buttons:[{
					text:'确定',
					handler : function() {
						_this.config.callback(pictureView.el.dom.src, relativeURLFiled.getValue());
						win.close();
					}
				}]
			});
		    
		    this.win = win;
		}
		this.win.show();
	}
};
