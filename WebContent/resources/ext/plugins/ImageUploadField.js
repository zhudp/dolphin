Ext.ns('Ext.ux.form');

Ext.ux.form.ImageUploadField = Ext.extend(Ext.ux.form.FileUploadField, {
			bindListeners : function() {
				this.fileInput.on({
							scope : this,
							mouseenter : function() {
								this.button.addClass(['x-btn-over',
										'x-btn-focus'])
							},
							mouseleave : function() {
								this.button.removeClass(['x-btn-over',
										'x-btn-focus', 'x-btn-click'])
							},
							mousedown : function() {
								this.button.addClass('x-btn-click')
							},
							mouseup : function() {
								this.button.removeClass(['x-btn-over',
										'x-btn-focus', 'x-btn-click'])
							},
							change : function() {
								var v = this.fileInput.dom.value;
								var file = this.fileInput.dom.files[0];
								if (this.checkFileSize(file, 200)&&this.checkFileType(file.type)) {
									this.setValue(v);
									this.fireEvent('fileselected', this, v);
								}else{
									this.reset();
								}
							}
						});
			},
			checkFileType : function(fileType) {
				if (fileType.substring(0, 6) == "image/") {
					return true;
				} else {
					alert("上传文件的类型错误,请更换!");
					return false;
				}
			},
			checkFileSize : function(file, size) {
				if (file == null || file == '')
					return false;
				var limit = 1024 * size;
				if (file.fileSize > limit) {
					alert("上传文件的大小不能超过" + size + "K,请更换!");
					return false;
				}
				return true;
			}
		});
Ext.reg('imageuploadfield', Ext.ux.form.ImageUploadField);
