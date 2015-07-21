// alpha：英文字母
// alphanum：数字
// time：时间
// IPAddress:ip地址
// url:网址
// email:邮箱地址
// chinese:中文
// phone:电话
// fax:传真
// mobile:手机
//zipcode:邮编
var alphaTest = /^[a-zA-Z_]+$/, alphanumTest = /^[a-zA-Z0-9_]+$/, emailTest = /^(\w+)([\-+.][\w]+)*@(\w[\-\w]*\.){1,5}([A-Za-z]){2,6}$/, urlTest = /(((^https?)|(^ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i,
timeTest = /^([1-9]|1[0-9]):([0-5][0-9])(\s[a|p]m)$/i, IPAddressTest = /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/, chineseTest = /^[\u4e00-\u9fa5]+$/i, phoneTest = /^((0[1-9]{3})?(0[12][0-9])?[-])?\d{6,8}$/, mobileTest = /(^0?[1][35][0-9]{9}$)/,
zipcodeTest=/^[1-9]\d{5}(?!\d)/
Ext.apply(Ext.form.VTypes, {
			alpha : function(val, field) {
				return alphaTest.test(val);
			},
			alphaText : '请输入英文字母',

			alphanum : function(val, field) {
				return alphanumTest.test(val);
			},
			alphanumText : '请输入英文字母或数字',

			url : function(val, field) {
				return urlTest.test(val);
			},
			urlText : '请输入正确的网址',

			time : function(val, field) {
				return timeTest.test(val);
			},
			timeText : '请输入正确的时间,如:12:34 PM',
			timeMask : /[\d\s:amp]/i,

			IPAddress : function(val, field) {
				return IPAddressTest.test(val);
			},
			IPAddressText : '请输入正确的IP地址,如:101.124.1.23',
			IPAddressMask : /[\d\.]/i,

			chinese : function(val, field) {
				return chineseTest.test(val);
			},
			chineseText : '必须输入中文',

			phone : function(val, field) {
				return phoneTest.test(val);
			},
			phoneText : '请输入正确的电话号码,如:0920-29392929',

			fax : function(val, field) {
				return phoneTest.test(val);
			},
			faxText : '请输入正确的传真号码,如:0920-29392929',

			mobile : function(val, field) {
				return mobileTest.test(val);
			},
			mobileText : '请输入正确的手机号码,如:13598723747',
			
			zipcode : function(val, field) {
				return zipcodeTest.test(val);
			},
			zipcodeText : '请输入正确的邮政编码,如:310045'

		});
