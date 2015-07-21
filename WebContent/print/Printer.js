if(!Ext.ux) {
	Ext.namespace("Ext.ux");
}

/**
 * 打印控件：web浏览器的自身的打印
 * @author zhudp 2010-11-03
 * @param config: {
 * 		title		打印予预览页的title
 * 		width		打印予预览页的宽度
 * 		height		打印予预览页的高度
 * 		url			打印页地址
 * 		param		参数
 * 		callback	打印完成的回调方法
 * }
 */
Ext.ux.Printer = function(config) {
	this.config = config;
};
Ext.ux.Printer.prototype = {

	show: function() {
		if(!this.win) {
			var win = new Ext.Window({
				renderTo: Ext.getBody(),
				title: this.config.title || '打印',
				layout: 'border',
				width: this.config.width  || 580,
				height:this.config.height || 480,
				maximizable:true,
				minWidth:300,
				minHeight:300,
				items:[{
					region: "center",
					layout: 'fit',
					border:false,
					items: [this.getPrintArea()]
				}],
				tbar:this.getToobar()
			});
			this.win = win;
		}
		this.win.show();
	},
	getToobar: function() {
		if(!this.toolbar) {
			var toolbar = new Ext.Toolbar({
				items:[{
						text: '打印'
						,iconCls: 'hs-button-search'
						,scope: this
						,handler:function() {
							var printFrame = document.getElementById('_print_area').contentWindow;
							printFrame.focus();
							printFrame.print();
							this.config.callback();
						}
					},{
				    	text: '取消',
				    	iconCls : 'icon_delete',
						scope: this,
				    	handler: function() { 
				    		this.win.close();
				    	}
					}
				]
			});
		
			this.toolbar = toolbar;
		}
		
		return this.toolbar;
	},
	getPrintArea: function() {
		if(!this.printArea) {
			var url = Ext.getDom("root").value + this.config.url;
			if(this.config.param) {
				url = url + '?' + Ext.urlEncode(this.config.param);
			}
			else{
				url = "";
			}
			var mark = "";
			if(this.config.mark){
				mark = this.config.mark;
			}
			var printArea = new Ext.Panel({
				border: false,
				//title: this.config.title,
				html: '<iframe id="_print_area'+ mark +'" scrolling="auto" frameborder="0" width="100%" height="100%" src="' + url + '"></iframe>'
			});
			this.printArea = printArea;
		}
		
		return this.printArea;
	}
	,reload: function(id){
		var url = Ext.getDom("root").value + this.config.url;
		url = url + '?id=' + id;
		Ext.getDom('_print_area'+this.config.mark).src = url;
	}
};
