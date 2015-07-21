Ext.ns('Ext.ux.form');

Ext.ux.form.ExcelUploadField = Ext.extend(Ext.ux.form.FileUploadField, {
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
								var v = this.fileInput.dom;
								if (this.checkFileSize(v, 10)
										&& this.checkFileType(v.value)) {
									this.setValue(v.value);
									this.fireEvent('fileselected', this, v);
								} else {
									this.reset();
								}
							}
						});
			},
			checkFileType : function(fileName) {
				var last = fileName.lastIndexOf(".");
				var lastname = fileName.substring(last, fileName.length)
				var array = new Array;
				array[0] = ".xls";
				array[1] = ".xlsx";
				for (var i = 0; i < array.length; i++) {
					if (lastname.toLowerCase() == array[i])
						return true;
				}
				alert("上传文件的类型错误，只能上传xls，xlsx文件,请更换!");
				return false;
			},
			checkFileSize : function(v, size) {
				var fileSize;
				if (navigator.appName == "Microsoft Internet Explorer") {
					if (navigator.appVersion.match(/6./i) == '6.') {
						var image = new Image();
						image.dynsrc = v.value;
						fileSize = image.fileSize;
					} else {
						var fso = new ActiveXObject("Scripting.FileSystemObject");// 服务器不能创建对象
						var file = fso.getFile(v.value);
						fileSize = file.size;
					}
				} else if (v.files[0] != undefined) {
					fileSize = v.files[0].fileSize;
				} else {
					return true;
				}
				var limit = 1024 *1024* size;
				if (fileSize > limit) {
					alert("上传文件的大小不能超过" + size + "K,请更换!");
					return false;
				}
				return true;
			}
		});
Ext.reg('exceluploadfield', Ext.ux.form.ExcelUploadField);
