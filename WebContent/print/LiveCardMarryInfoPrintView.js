Ext.namespace("people.marry");

/**
 * 居住证婚育信息打印窗口
 * @author gaowx 2012-11-23
 * @param config: {
 * 		title		打印予预览页的title
 * 		width		打印予预览页的宽度
 * 		height		打印予预览页的高度
 * 		url			打印页地址
 * 		param		参数
 * 		callback	打印完成的回调方法
 * }
 */
people.marry.PrintCard = function(config) {
	this.config = config;
};
people.marry.PrintCard.prototype = {

	show: function() {
		if(!this.win) {
			var win = new Ext.Window({
				renderTo: Ext.getBody(),
				title: '打印预览',
				layout: 'border',
				width: 580,
				height:480,
				maximizable:true,
				closable: true,
				closeAction: 'close',
				//maximized: this.config.maximized || false,
				minWidth:300,
				minHeight:300,
				items:[{
					region: "center",
					layout: 'fit',
					border: false,
					items: [this.getPrintArea()]
					//tbar: this.getToobar()
				}
				,this.getTipArea()]
			});
			this.win = win;
		}
		this.win.show();
		// 加载中...
		var progressBar = this.getProgress();
		progressBar.wait({
			text: "打印内容加载中，请稍候...",
			interval:100,   // 每次更新的间隔周期（默认为1000毫秒）   
	        increment:10    // 进度条每次更新的幅度大小（默认为10）。如果进度条达到最后，那么它会回到原点。  
		});
		var printIframe = document.getElementById('marry_print_charter_area');
		printIframe.onload = printIframe.onreadystatechange = function() {
		     if (this.readyState && this.readyState != 'complete'){
		    	 return;
		     }
		     else {
		    	 progressBar.reset(true);
		     }
		};
		/*
		if (printIframe.attachEvent){
			printIframe.attachEvent("onload", function(){
				progressBar.reset(true); // 隐藏进度条
			});
		}
		else {    
			printIframe.onload = function(){   
				progressBar.reset(true); // 隐藏进度条
			};
		}
		*/
	},
	getPrintArea: function() {
		if(!this.printArea) {
			var url = Ext.getDom("root").value + this.config.url;
			if(this.config.param) {
				url = url + '?' + Ext.urlEncode(this.config.param);
			}
			var printArea = new Ext.Panel({
				border: false,
				html: '<div id="progressShow" align="center"></div><iframe id="marry_print_charter_area" scrolling="auto" frameborder="0" width="100%" height="100%" src="' + url + '"></iframe>'
			});
			this.printArea = printArea;
		}
		return this.printArea;
	}
	,getTipArea: function() {
		if(!this.tipArea) {
			//var imgSrc = Ext.getDom("root").value+'/resources/images/printShow2.gif';
			var tipPanel = new Ext.form.FormPanel ({
				title:'打印说明',
				region: "east",
				layout: 'fit',
				width: 200,
				border: false,
				collapsible: true,
				collapsed:true,
                split: true,
				items:[{
					html: '<br /><span>　a、<a href="/zhili/resources/plugin/Adobe_Reader_X_10.0.0.396.exe" target="_blank">点击下载打印控件</a></span><br />'+
					      '<br /><span>　b、<a href="/zhili/resources/plugin/织里镇政府软件开发项目--居住证打印配置说明.html" target="_blank">点击打开配置说明</a></span><br />' +
						  '<br /><span>　c、<a href="/zhili/resources/plugin/织里镇政府软件开发项目--居住证打印配置说明.doc" target="_blank">点击下载配置说明</a></span><br />'
				}]
			});
			this.tipArea = tipPanel;
		}
		return this.tipArea;
	}
	,getProgress: function(){
		if(!this.progressArea){
			var progressBar = new Ext.ProgressBar({   
				renderTo: "progressShow",
                width: 400,
                style: "margin-top:30px;"
            });
            this.progressArea = progressBar;
		}
		return this.progressArea;
	}
};
