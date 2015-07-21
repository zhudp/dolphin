/**
 * @class null
 * 扩展部分公用的组件，方便项目中的使用
 * @singleton
 * @author: jinnie
 */
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}
Ext.onReady(function() {
			Ext.QuickTips.init();
		});

Ext.BLANK_IMAGE_URL = '../resources/ext/images/default/s.gif';

/**
 * 扩展日期控件，固定其大小
 */
Ext.override(Ext.menu.DateMenu, {
			render : function() {
				Ext.menu.DateMenu.superclass.render.call(this);
				if (Ext.isGecko) {
					this.picker.el.dom.childNodes[0].style.width = '178px';
					this.picker.el.dom.style.width = '178px';
				}
			}
		});
/**
 * 扩展文本输入框属性，使其可以监听输入的同时，去掉前后空格
 */
Ext.apply(Ext.form.TextField.prototype, {
			listeners : {
				'change' : function(obj) {
					var trimStr = obj.getValue().toString().replace(
							/^\s+|\s+$/g, "");
					if (trimStr != obj.getValue())
						obj.setValue(trimStr); // 去掉字符串前后空格
				}
			}
		});

Ext.util.Format.cnMoney = function(v){
    v = (Math.round((v-0)*100))/100;
    v = (v == Math.floor(v)) ? v + ".00" : ((v*10 == Math.floor(v*10)) ? v + "0" : v);
    v = String(v);
    var ps = v.split('.'),
        whole = ps[0],
        sub = ps[1] ? '.'+ ps[1] : '.00',
        r = /(\d+)(\d{3})/;
    while (r.test(whole)) {
        whole = whole.replace(r, '$1' + ',' + '$2');
    }
    v = whole + sub;
    if(v.charAt(0) == '-'){
        return '-$' + v.substr(1);
    }
    return v;
};
/**
 * 扩展信息提示框属性，可以传入参数：标题、信息内容、方法、是否可折叠 
 */
Ext.apply(Ext.MessageBox.prototype, {
			confirm : function(title, msg, fn, scope) {
				this.show({
							title : title,
							msg : msg,
							buttons : this.YESNO,
							fn : fn,
							scope : scope,
							minWidth : 200,
							icon : this.QUESTION
						});
				return this;
			}
		});
/**
 * 扩展BasicForm属性
 */
Ext.apply(Ext.form.BasicForm.prototype, {
			/**
			 * @cfg {Boolean} trackResetOnLoad If set to true, form.reset()
			 *      resets to the last loaded or setValues() data instead of
			 *      when the form was first created.
			 */
			trackResetOnLoad : true
		});

Ext.apply(Ext.form.BasicForm.prototype, {
			setValues : function(values) {
				if (values instanceof Array) {
					for (var i = 0, len = values.length; i < len; i++) {
						var v = values[i];
						var f = this.findField(v.id);
						if (f) {
							if (v.value)
								f.setValue(v.value.toString().replace(
										/^\s+|\s+$/g, ""));
							else
								f.setValue(v.value);
							if (this.trackResetOnLoad) {
								f.originalValue = f.getValue();
							}
						}
					}
				} else {
					var field, id;
					for (id in values) {
						if (typeof values[id] != 'function'
								&& (field = this.findField(id))) {
							if (values[id])
								field.setValue(values[id].toString().replace(
										/^\s+|\s+$/g, ""));
							else
								field.setValue(values[id]);
							if (this.trackResetOnLoad) {
								field.originalValue = field.getValue();
							}
						}
					}
				}
				return this;
			}
			,clearValues: function(){
				 this.items.each(function(f){
					 f.setValue(null);
				}); 
			}
		});

/**
 * 扩展TextArea属性，固定大小并去掉输入内容的前后空格
 */
Ext.apply(Ext.form.TextArea.prototype, {
			width : 520,
			height : 100,
			listeners : {
				'change' : function(obj) {
					var trimStr = obj.getValue().toString().replace(
							/^\s+|\s+$/g, "");
					if (trimStr != obj.getValue())
						obj.setValue(trimStr); // 去掉字符串前后空格
				}
			}
		});

/**
 * 扩展日期控件属性，默认显示格式
 */
Ext.apply(Ext.form.DateField.prototype, {
			altFormats : 'Y-m-d\\TH:i:s',
			format : 'Y-m-d'
		});

/**
 * 扩展下拉框属性，默认显示格式
 */
Ext.apply(Ext.form.ComboBox.prototype, {
			//emptyText : '-请选择-',
			loadingText : '加载中...',
			selectOnFocus : true,
			editable : false,
			triggerAction : 'all',
			typeAhead : true,
			valueField : 'id',
			mode : 'local',
			displayField : 'name'
		});

/**
 * 扩展FormPanel属性，默认显示格式
 */
//Ext.apply(Ext.form.FormPanel.prototype, {
//			defaultType : 'fieldset',
//			defaults : {
//				labelWidth : 75
//			},
//			labelAlign : 'right'
//		});

/**
 * 扩展FieldSet属性，默认自动高度
 */
//Ext.apply(Ext.form.FieldSet.prototype, {
//			autoHeight : true
//		});
		
/**
 * 扩展Field属性，默认允许为空，中间分隔用“：”号，信息提示在旁边
 */
Ext.apply(Ext.form.Field.prototype, {
			allowBlank : true,
			labelSeparator : '',
			msgTarget : 'qtip'
		});

/**
 * 扩展Window属性，默认以下属性
 */
Ext.apply(Ext.Window.prototype, {
			modal : true,
			shadow : false,
			autoScroll : true,
			resizable : true,
			maximizable : false,
			constrainHeader : true,
			buttonAlign : 'center',
			closeAction : 'hide'
		});
		
/**
 * 扩展Ajax提交，默认缓存开启，过时时间：3000秒
 */
Ext.apply(Ext.Ajax, {
			disableCaching : false,
			timeout : 3000000
		});

/**
 * 自定义方法
 */
hs.publicFun = function() {
	return {
		/**
		 * 根据所要显示的列字段传入对应的列ID,通过从columnModel中抽取obj用以组装全新的列模型
		 * 
		 * @param {array}
		 *            columnModel 调用的列模型 {array} dataIndexArray
		 *            列模型中要显示的字段所对应的dataIndex
		 */
		creatColumnModel : function(columnModel, dataIndexArray) {
			var returnCm = new Array();
			returnCm[0] = new Ext.grid.RowNumberer();
			for (var j = 0; j < dataIndexArray.length; j++) {
				for (var i = 1; i < columnModel.length; i++) {
					if (columnModel[i].dataIndex == dataIndexArray[j]) {
						returnCm[j + 1] = columnModel[i];
					}
				}
			}
			return returnCm;
		}

		/**
		 * 从grid中选值置入form的方法
		 * 
		 * @param {obj}
		 *    record 数据源 {array} getProperty 从grid中选择的参数组 {array}
		 *    returnValueFor 要置入的form中对应的参数组 {obj} win 放置grid的弹出窗口
		 */
		,
		selectValueFun : function(config) {
			record = config.record;
			for (i = 0; i < config.getProperty.length; i++) {
				Ext.getDom(config.returnValueFor[i]).value = record
						.get(config.getProperty[i]);
			}
			config.win.hide();
		}

		/**
		 * 身份证中取生日
		 * 
		 * @param {obj}
		 *            idCart 身份证号
		 */
		,
		changeToBirth : function(idCart) {
			var idStr = "";
			var birthStr = "";
			if (null != idCart && idCart != '') {
				idStr = idCart.length == 18 ? idCart.substring(0, 17) : idCart
						.substring(0, 6)
						+ "19" + idCart.substring(6, 16);
				birthStr = idStr.substring(6, 10) + "-"
						+ idStr.substring(10, 12) + "-"
						+ idStr.substring(12, 14);
			}
			return birthStr;
		}
		/**
		 * 身份证中籍贯
		 * 
		 * @param {obj}
		 *            idCart 身份证号
		 */
		,
		changeToArea : function(idCart) {
			var idStr = "";
			var birthStr = "";
			if (null != idCart && idCart != '') {
				birthStr = idCart.substring(0, 6);

			}
			return birthStr;
		}

		/**
		 * 身份证中取性别 其中1为男、2为女
		 * 
		 * @param {obj}
		 *            idCart 身份证号
		 */
		,
		changeToSex : function(idCart) {
			var sex = "";
			if (null != idCart && idCart != '') {
				sex = idCart.length == 18 ? idCart.substring(16, 17) : idCart
						.substring(14, 15);
			}
			if (parseInt(sex) % 2 == 1)
				sex = 1;
			else
				sex = 0;
			return sex;
		}

		// Ajax读取后台数据
		,
		ajaxRead : function(sUrl, callback, data) {
			var xmlObj = null;
			if (window.XMLHttpRequest) {
				xmlObj = new XMLHttpRequest();
			} else if (window.ActiveXObject) {
				xmlObj = new ActiveXObject("Microsoft.XMLHTTP");
			} else {
				return;
			}
			xmlObj.onreadystatechange = function() {
				if (xmlObj.readyState == 4) {
					callback(xmlObj);
				}
			};

			xmlObj.open('GET', sUrl, true);
			if (typeof data != 'undefined') {
				xmlObj.send(data);
			} else {
				xmlObj.send(null);
			}
		}

		// 给form里的所有元素赋值，元素名称要和传入record对象的名字对应，在编辑对话框弹出前调用
		,
		fillForm : function(form, record) {
			form.clearInvalid();
			form.setValues(record.data);
		},

		
		/**
		 * 校验上传文件大小的方法
		 * 
		 * @author huangrh , checkFileSize : function (file){ if(file == null ||
		 *         file == ''|| file.indexOf(":\\")== -1){ alert("不能上传空文件!");
		 *         return true; } var fso,f; fso = new
		 *         ActiveXObject("Scripting.FileSystemObject"); f =
		 *         fso.GetFile(file); if(f.size >
		 *         appConstants.UPLOAD_FILE_MAXSIZE){ alert("上传文件的大小不能超过" +
		 *         appConstants.UPLOAD_FILE_MAXSIZE + "Bytes
		 *         (1M)!\n\n您现在上传的文件大小为：" + f.size + "Bytes,请更换!"); return true; }
		 *         return false; }
		 */
		/**
		 * 校验上传的文件(根据文件全路径fileName)是否与所选文件类型checkType一致
		 * 
		 * @author huangrh
		 */
		
		checkFileType : function(fileName, checkType) {
			var last = fileName.lastIndexOf(".");
			var lastname = fileName.substring(last, fileName.length)
			var array = new Array;
			if (checkType == "1") {
				// 图片文件格式 jpg,gif,bmp,tif,png,svg
				array[0] = ".jpg";
				array[1] = ".gif";
				array[2] = ".bmp";
			} else if (checkType == "2") {
				// Flash文件格式 swf,jpg,mp3,png,gif
				array[0] = ".swf";
			} else if (checkType == "3") {
				// 视频文件格式 rm,rmvb,mpeg1-4,mov,mtv,dat,wmv,avi,3gp,amv,dmv
				array[0] = ".rm";
				array[1] = ".rmvb";
				array[2] = ".wmv";
			}
			for (var i = 0; i < array.length; i++) {
				if (lastname.toLowerCase() == array[i])
					return true;
			}
			return false;
		}
		/**
		 * 校验上传文件(含图片、文档等类型)大小的统一方法
		 * 
		 * @Modify huangrh
		 */
		,
		checkFileSize : function(file, size) {
			if (file == null || file == '')
				return false;
			var limit = 1024 * size;
			var img = new Image();
			img.dynsrc = file;
			if (img.fileSize > limit) {
				alert("上传文件的大小不能超过" + size + "K,请更换!");
				return true;
			}
			return false;
		}
		/**
		 * 去掉字符串前后空格
		 * 
		 * @author huangrh
		 */
		,
		stringToTrim : function(v) {
			return v.toString().replace(/^\s+|\s+$/g, "");
		}
		/**
		 * 校验输入值是否为空格的监听方法
		 * 
		 * @author huangrh
		 */
		,
		checkTrim : function() {
			return {
				'change' : {
					fn : function(obj) {
						obj.setValue(hs.publicFun.stringToTrim(obj.getValue()));
					}
				}
			}
		}
		/**
		 * 校验上传的文件是否正确
		 */
		,
		fileIsExit : function(fileName) {
			if (fileName.indexOf(':\\') < 1)
				return true;
			else
				return false;
		}
		/**
		 * 获取Grid列表选择多条arrayRecord记录的ID值
		 * 
		 * @author huangrh
		 */
		,
		getGridIdStr : function(arrayRecord) {
			var idStr;
			for (var i = 0; i < arrayRecord.length; i++) {
				if (i == 0)
					idStr = arrayRecord[i].get('id') + '#';
				else
					idStr = idStr + arrayRecord[i].get('id') + '#';
			}
			return idStr;
		}
		/**
		 * 回车事件触发
		 */
		,
		enterEvent : function(inputField, fun) {
			new Ext.KeyMap(inputField, {
						key : Ext.EventObject.ENTER,
						fn : function() {
							// alert(inputField.isTextEdit); //是否编辑过
							// alert(inputField.type); //输入框类型
							if (inputField.type == 'text') {
								inputField.value = hs.publicFun
										.stringToTrim(inputField.value); // 如果是文本框,则去掉字符串前后空格
							}
							fun.call();
						},
						scope : this
					})
		}
	}
}();

/**
 * yuancong 2008年10月14日13时17分46秒
 * 扩展VTypes
 */
Ext.applyIf(Ext.form.VTypes, {
	"blank" : function(_v) {
		return _v != "" && _v.indexOf('选择') == -1;
	},
	"postalcode" : function(_v) {
		return /^[1-9]\d{5}$/.test(_v);
	},
	"strTrim" : function(_v) {
		vStr = _v.toString().replace(/^\s+|\s+$/g, "");
		return vStr != "";
	},
	// 验证函数，正则表达式
	"postalcodeText" : "该输入项必须是邮政编码格式，长度为6位，格式如：310000",
	// 错误提示内容
	"postalcodeMask" : /[0-9]/i,
	// 指定只能输入的的格式

	"telphone" : function(_v) {
		return /(^\d{3}\-\d{7,8}$)|(^\d{4}\-\d{7,8}$)|(^\d{3}\d{7,8}$)|(^\d{4}\d{7,8}$)|(^\d{7,8})/
				.test(_v);
	},
	"telphoneText" : "该输入项必须是电话号码格式，区号部分为3位或者4位，号码部分为7位或者8位，格式如：0571-12345678，057112345678，12345678",
	"telphoneMask" : /[0-9]/i,

	"mobile" : function(_v) {
		return /^1[1-9][0-9]\d{8}$/.test(_v);
	},
	"mobileText" : "该输入项必须是手机号码格式，长度为11位，格式如：13912345678",
	"mobileMask" : /[0-9]/i,

	"telOrMobile" : function(_v) {
		return /(^\d{3}\-\d{7,8}$)|(^\d{4}\-\d{7,8}$)|(^\d{3}\d{7,8}$)|(^\d{4}\d{7,8}$)|(^\d{7,8})|(^1[1-9][0-9]\d{8}$)/
				.test(_v);
	},
	"telOrMobileText" : "该输入项必须是电话或者手机号码格式，电话号码格式，区号部分为3位或者4位，号码部分为7位或者8位，手机号码格式，长度为11位，格式如：0571-12345678,13912345678",
	"telOrMobileMask" : /[0-9]/i,

	"IDCard" : function(_v) {
		return /(^[0-9]{17}([0-9]|[Xx])$)|(^[0-9]{14}([0-9]|[Xx])$)/.test(_v);
	},
	"IDCardText" : "该输入项必须是身份证号码格式，长度为15位或者18位，格式如：34052419800101001X",
	"IDCardMask" : /[0-9]|[Xx]/i

	,
	"artTitle" : function(_v) {
		return /[^<*>]/.test(_v);
	},
	"artTitleText" : "新闻标题中请不要输入HTML代码!",
	"noChinese" : function(v) {
		return /^[A-Za-z0-9_]*$/.test(v);
	},
	"noChineseText" : "该输入项只能输入数字、字母或者下划线",
	"email" : function(v) {
		return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(v);
	},
	"emailText" : "请输入正确的email格式,格式如sjq123@sjq.com!",
	"account" : function(v) {
		return /^[a-zA-Z]{1}[_0-9a-zA-Z]{2,19}$/.test(v);
	},
	"accountText" : "3-20个字符(包括字母、数字、下划线)。必须以英文字母开头，一旦注册成功会员帐号不能修改。",
	"chinese" : function(v) {
		return /^[\u0391-\uFFE5]{2,10}$/.test(v);
	},
	"chineseText" : "该输入项只能输入2-10个中文字!",
	"qq" : function(v) {
		return /^[0-9]\d{4,11}$/.test(v);
	},
	"qqText" : "该输入项只能输入5-11位数字,格式如：12345678910",
	"fax" : function(v) {
		return /^[-0-9]{0,15}$/.test(v);
	},
	"faxText" : "该输入项只能输入数字的传真号码,格式如：0571-123456789",
	"urlSelf" : function(v) {
		return /^(http:\/\/){0,1}[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/
				.test(v);
	},
	"urlSelfText" : "该输入项只能输入网站地址,格式如：www.hundsun.com",
	"date":
		function(v) {
		return /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29))$/.test(v);
	},
	"dateText" : "该输入项日期,格式如：2010-09-01",
	"dateMask" : /[0-9]/i
});

/**
 * 根据权限设置 功能按钮键？
 */
checkButonAuthor = function() {
	var mainStr, userStr;
	if (parent && parent.document
			&& parent.document.getElementById("mainResource")) {
		mainStr = parent.document.getElementById("mainResource").value;
	}
	if (parent && parent.document
			&& parent.document.getElementById("userResource")) {
		userStr = parent.document.getElementById("userResource").value;
	}

	if (mainStr && userStr) {
		mainList = eval(mainStr);
		userList = eval(userStr);
		for (i = 0; i < mainList.length; i++) {
			idstr = mainList[i];
			var b = Ext.get(idstr);
			if (b != null) {
				b.setVisible(false);
			}
		}

		for (i = 0; i < userList.length; i++) {
			idstr = userList[i];
			var b = Ext.get(idstr);
			if (b != null) {
				b.setVisible(true);
			}
		}
	}
}

/**
 * 设置所属社区
 */
setCommunityACL = function(communityExt){
	if(communityExt) {
		var this_user_CommunityId = window.parent.this_user_CommunityId;
		communityExt.setValue(this_user_CommunityId);
		communityExt.readOnly=true;
	}
}


/**
 * 默认选择对话框的样式
 * @param {} testForm
 * @return {}
 */
SelectWindow = function(testForm) {

	testWin = new Ext.Window({
				title : testForm.title,
				width : (screen.availWidth - 244) * 0.8,
				height : (screen.availHeight - 244) * 0.8,
				margins : '3 3 3 3',
				cmargins : '3 3 3 3',
				bodyStyle : 'padding:3px;',
				closeAction : 'hide',
				maximizable : true,
				items : testForm
			});
	testWin.show();
	return testWin;
}

/**
 * 默认样式全局变量
 * @type 
 */
Constant = {
	required : "<span style='color:#ff3300;'><b>*</b></span>"
};
/**
 * 扩展文本输入框
 */
Ext.namespace('Ext.ux.form');
Ext.ux.form.Int2DateField = Ext.extend(Ext.form.TextField, {
	constructor: function(config) {
	    Ext.ux.form.Int2DateField.superclass.constructor.apply(this, arguments);
	},
	getValue: function () {
		var v = this.deCodeValue(this.value);
    	return v;
  	},
  	setValue: function (v) {
	    this.value = v;
	   	this.setRawValue(this.enCodeValue(v));
	    return this;
  	},
  	getRawValue : function(){
        var v = this.rendered ? this.el.getValue() : Ext.value(this.value, '');
        if(v === this.emptyText){
            v = '';
        }
        v = this.deCodeValue(v);
        this.el.dom.value = (Ext.isEmpty(v) ? '' : v);
        return v;
    },
	enCodeValue: function (v) {
		if(v == null || v.length<8){
			return "";
		}
	    v = v.substring(0,4) + "-" + v.substring(4,6) + "-" + v.substring(6);
	    return v;
  	},
  	deCodeValue: function (v) {
  		if(v != null && v != "") {
  			return v.replaceAll("-", "");
  		}
  		else {
  			return v;
  		}
  	}
});
// register xtype
Ext.reg('int2datefield', Ext.ux.form.Int2DateField);

/**
 * 
 * @param url 新tab的路径（相对路径）
 * @param tabName 新tab名称
 * @return
 */
function openUrlInTab(url, tabName) {
	if(parent.mainPanel) {
		parent.mainPanel.loadClass(encodeURI(url),tabName);
	} else {
		Ext.MessageBox.show({
			title: '错误',
			msg: '打开窗口错误，请与系统管理员联系！',
			buttons: Ext.MessageBox.OK,
			icon: Ext.MessageBox.ERROR
		});
		
	}
};

/**
 * 获取输入字符串的长度，一个中文占两个字节 
 * @param val
 * @returns {Number}
 */
function getByteLen(val) {    //传入一个字符串
    var len = 0;
    for (var i = 0; i < val.length; i++) {
        if (val.charCodeAt(i) > 255) //.match('/[^\x00-\xff]/ig') != null) //全角 
            len += 2; 
        else
            len += 1; 
    }
    return len;
}; 

/**
 * @class  Ext.form.TextField
 * @override  Ext.form.TextField
 * @description  修改TextField的默认提示信息,并支持中文2位的计算
 
Ext.override(Ext.form.TextField,{
      //重写验证涵数
    validateValue : function(value){
          if(this.allowBlank == false){ //不允许为空
        	  if(value == null || value == ''){
                this.markInvalid(String.format(this.blankText,value));
                return false;
              }
           }
           var maxLen = this.maxLength;
           var maxLenText = this.maxLengthText;
           if(maxLenText.indexOf('{0}') != -1){
                 if(maxLen != null && maxLen != 'undefined' && maxLen > 0){ 
                       var regex = /[^\x00-\xff]/g;  //中文正则
                       var len ;
                       if(typeof value == "string"){
                           //将中文替换成2位字符
                    	   len = value.replace(regex,'**').length;
                       }
                       else{
                    	   len = value.length;
                       }
                       var label = this.fieldLabel;
                       if(label != null && label != 'undefined'){
                           //去掉fieldLabel中生成的不必要字符
                    	   if(label.indexOf('</') != -1 ){
                    		   label = label.substring(label.lastIndexOf('>')+1, label.length);
                           }
                           if(len > maxLen){
		                        //验证未通过,并设置提示信息
		                    	this.markInvalid(String.format(label+'长度不能大于'+maxLen+'位!(中文占2位)'));
		                        return false;
                           }
                           return true;
                       }
                  }
            }
           else{
                  var len = value.length;
                  if(len > maxLen){
                       this.markInvalid(String.format(maxLenText ,value));
                       return false;
                  }
                return true;
           }
    }
});
*/