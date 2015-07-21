/*!
 * Ext JS Library 3.2.1
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.ns('Ext.ux.form');

Ext.ux.form.SearchField = Ext.extend(Ext.form.TwinTriggerField, {
    initComponent : function(){
        Ext.ux.form.SearchField.superclass.initComponent.call(this);
        this.on('specialkey', function(f, e){
            if(e.getKey() == e.ENTER){
                this.onTrigger2Click();
            }
        }, this);
    },
    validationEvent:false,
    validateOnBlur:false,
    trigger1Class:'x-form-clear-trigger',
    trigger2Class:'x-form-search-trigger',
    hideTrigger1:true,
    width:180,
    paramName : 'query',

    onTrigger1Click : function(){
    	if(this.disabled){return false;}
    	if(this.doClear) {
    		this.doClear();
    	}
        this.el.dom.value = '';
        this.triggers[0].hide();
    },

    onTrigger2Click : function(){
    	if(this.disabled){return false;}
        var v = this.getRawValue();
        if(v.length < 1){
            this.onTrigger1Click();
            this.doSearch(v);
            this.triggers[0].show();
        }else{
	        this.triggers[0].show();
	        this.doSearch(v);
        }
    }
});

Ext.reg('searchfield', Ext.ux.form.SearchField);