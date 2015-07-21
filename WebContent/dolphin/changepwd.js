/**
 * 修改密码.
 */

/**
common.ChangepwdWin = function() {
	var form = new common.ChangepwdForm();
    var win = new Ext.Window({title: "密码修改", width: 500, height: 250, closable: false, items: form.getForm() });
    return {
    	show: function() {
    		win.show();
    	}
    }
}
 */

common.ChangepwdForm = function() {
    var form = new Ext.form.FormPanel({
		frame : true,
		labelWidth : 95,
		layout:'form',
		region: "center",
		labelAlign : 'right',
		buttonAlign : 'center',
		items: [
          
	        	new Ext.form.TextField({fieldLabel: '原密码', id: 'oldPassword',name: "oldPassword",minLengthText:'密码最小位数为7位',width:140,inputType:'password',allowBlank: false})	     
	           ,new Ext.form.TextField({fieldLabel: '新密码', id: 'newPassword',name: "newPassword",minLengthText:'密码最小位数为7位',width:140,inputType:'password',allowBlank: false})
	           ,new Ext.form.TextField({fieldLabel: '新密码确认', id: 'newPwdConfirm',name: "newPwdConfirm",minLengthText:'密码最小位数为7位', width:140,inputType:'password',allowBlank: false})
	           ,{html:'<div style="color:#00f;margin:1px 10px">注意：为保障您的账户安全，请您及时修改初始密码。</div>'}
			
		]
    });
    form.addButton("保存", function() {
    	var newPassword = form.findById("newPassword");
    	var newPwdConfirm = form.findById("newPwdConfirm");
    	if(newPassword.getValue() != newPwdConfirm.getValue()) {
    		hs.MessageHelper.info({
    			msg: "新密码与新密码确认内容不相符，请重新录入后再保存."
    		});
    		newPassword.setValue("");
    		newPwdConfirm.setValue("");
    		return;
    	}
    	if(newPassword.getValue().length<=6||newPwdConfirm.getValue().length<=6){
    			hs.MessageHelper.info({
    			msg: "您输入的新密码的位数至少应该大于6位!"
    		});
    		return;
    	}
		 hs.FormHelper.submit('/dolphin/user!changePassword.do', function(form, action) {
    		 		hs.MessageHelper.succuss({
    			callback: function() {
    				hs.WindowHelper.hide(true);
    				//window.close();
    			}
			});
		});
    });
//    form.addButton("关闭", function() {
//    	hs.WindowHelper.hide(true);
//    });
    
    return {
    	getForm : function() {
    		return form;
    	}
    }
}
