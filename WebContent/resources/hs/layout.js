/**
 * @class hs.formLayout 布局辅助类
 * @singleton
 */
hs.FormLayout = function() {
	return {
		/**
		 * oneColumnedRow 占据的是一列的位置
		 */
		oneColumnedRow : function(columnItem) {
			return {
//				baseCls : 'x-plain',
				layout : 'column',
				defaults : {
					defaults : {
						anchor : '93%'
					},
					layout : 'form'
				},
				items : [{
							items : columnItem,
							columnWidth : 0.5
						}]
			};
		},
		/**
		 * 二列布局：twoColumnedRow
		 */
		twoColumnedRow : function(leftColumnItem, rightColumnItem, defaultsCfg, w1, w2) {
			var cfg = defaultsCfg ? defaultsCfg : {anchor : '98%'};
			var w11 = w1 ? w1 : 0.5;
			var w22 = w2 ? w2 : 0.5;
			return {
				baseCls : 'x-plain',
				layout: 'column',
				defaults: {
					baseCls : 'x-plain',
					defaults: cfg,
					layout: 'form'
				},
				items: [{
							items: leftColumnItem,
							columnWidth: w11
						}, {
							items: rightColumnItem,
							columnWidth: w22
						}]
			};
		},
		/**
		 * 三列布局
		 */
		threeColumnedRow : function(leftColumnItem, middleColumnItem,
				rightColumnItem,defaultsCfg, w1, w2, w3) {
			var w11 = w1 ? w1 : 0.33;
			var w22 = w2 ? w2 : 0.33;
			var w33 = w3 ? w3 : 0.33;
			var cfg = defaultsCfg ? defaultsCfg : {anchor : '98%'};
			return {
				baseCls : 'x-plain',
				layout : 'column',
				defaults : {
					baseCls : 'x-plain',
					defaults : cfg,
					layout : 'form'
				},
				items : [{
							items : leftColumnItem,
							columnWidth : w11
						}, {
							items : middleColumnItem,
							columnWidth : w22
						}, {
							items : rightColumnItem,
							columnWidth : w33
						}]
			};
		},
		/**
		 * 四列布局
		 */
		fourColumnedRow : function(leftColumnItem, rightColumnItem,
				leftColumnItem1, rightColumnItem1, defaultsCfg, w1, w2, w3, w4) {
			var w11 = w1 ? w1 : 0.25;
			var w22 = w2 ? w2 : 0.25;
			var w33 = w3 ? w3 : 0.25;
			var w44 = w4 ? w4 : 0.25;
			var cfg = defaultsCfg ? defaultsCfg : {anchor : '98%'};
			return {
				baseCls : 'x-plain',
				layout : 'column',
				defaults : {
					baseCls : 'x-plain',
					defaults : cfg,
					layout : 'form'
				},
				items : [{
							items : leftColumnItem,
							columnWidth : w11
						}, {
							items : rightColumnItem,
							columnWidth : w22
						}, {
							items : leftColumnItem1,
							columnWidth : w33
						}, {
							items : rightColumnItem1,
							columnWidth : w44
						}]
			};
		},
		/**
		 * wholeOneColumnedRow 占据的是两列的位置,一般是用于TextArea组件
		 */
		wholeOneColumnedRow : function(columnItem, defaultsCfg) {
			var cfg = defaultsCfg ? defaultsCfg : {anchor : '95%'};
			return {
				baseCls : 'x-plain',
				layout : 'column',
				defaults : {
					baseCls : 'x-plain',
					defaults : cfg,
					layout : 'form'
				},
				items : [{
							items : columnItem,
							columnWidth : 1
						}]
			};
		},
		/**
		 * hiddenColumns
		 * @param {Array}
		 * hiddenColumns
		 */
		hiddenColumns : function(hiddenColumns) {
			return {
				title : '隐藏信息(你不应该看到的)',
				hidden : true,
				items : hiddenColumns
			};
		},
		// table 布局辅助
		tableColumns: function(columnItem) {
			return{
				layout : 'form',
				items : [columnItem]
			};
		},
		/**
		 * 两个TABPANEL布局
		 */
		twoTabPanelForForm: function(form1, form2, config) {
			var tabsForm = new Ext.TabPanel({
	        	activeTab: 0,
	        	id: config.id,
	        	layoutOnTabChange: true, 
	        	autoHeight: true,
	        	items:[{
					layout: 'form', 
			        title: config.title1,
		        	frame: true,
		        	width: '100%',
			        autoHeight : true,
			        items : [
			        	form1
			 		]
				},{
					layout: 'form', 
			        title : config.title2,
		        	frame: true,
		        	width:'100%',
			        autoScroll : true,
			        autoHeight : true,
			        items : [
			 			form2
			 		]
				}]
	   		});
	   		return tabsForm;
		},
		/**
		 * 三个TABPANEL布局
		 */
		threeTabPanelForForm: function(form1, form2, form3, config) {
			var tabsForm = new Ext.TabPanel({
	        	activeTab: 0,
	        	id: config.id,
	        	layoutOnTabChange: true, 
	        	autoHeight: true,
	        	items:[{
					layout: 'form', 
			        title: config.title1,
		        	frame: true,
		        	width: '100%',
			        autoHeight : true,
			        items : [
			        	form1
			 		]
				},{
					layout: 'form', 
			        title : config.title2,
		        	frame: true,
		        	width:'100%',
			        autoScroll : true,
			        autoHeight : true,
			        items : [
			 			form2
			 		]
				},{
					layout: 'form', 
			        title : config.title3,
		        	frame: true,
		        	width:'100%',
			        autoScroll : true,
			        autoHeight : true,
			        items : [
			 			form3
			 		]
				}]
	   		});
	   		return tabsForm;
		}
	}
}();

/**
 * @class hs.panelLayout 布局辅助类
 * @singleton
 * @author: jinnie
 */
hs.panelLayout = function() {
	return {
		/**
		 * 标准的panel布局
		 */
		normPanel: function(searchPanel, GridPanel) {
			hs.ELFactory.getTabPanelEL({
				id : "mainPanel"
			});
			return new Ext.Panel({
				/* 标准布局 */
				frame: true,
	          	labelAlign: 'right',
	           	width: document.body.offsetWidth,
	           	height: '100%',
	           	renderTo: 'mainPanel',
	           	items: [searchPanel, GridPanel]
			});
		},
		/**
		 * border 形式的Panel布局
		 */
		borderPanel: function(westPanel, eastPanel, topPanel, centerPanel) {
			hs.ELFactory.getTabPanelEL({
				id : "mainPanel"
			});
			return new Ext.Panel({
				/* 标准布局 */
				layout: "border",
				frame: true,
	          	labelAlign: 'right',
	           	width: document.body.offsetWidth,
	           	height: '100%',
	           	renderTo: 'mainPanel',
	           	items: [westPanel, eastPanel, topPanel, centerPanel]
			});
		}
	}
}();

/**
 * @class hs.ELFactory
 * 创建Grid/Form/Dialog/TabPanel/Tab/Toolbar/Seachbar/Wrap渲染的DIV容器
 * @singleton
 * @author: wanglf
 */

hs.ELFactory = function(){
	/**
	 * private
	 */
	return {
		/**
		 * 创建TitleEL，一般在页面最上方
		 * @param {String} title: (optional)当前创建的Title的名称；
		 * @param {String} parentEL: (optional)当前创建的Title追加到的dom id；如果为空，则为当前页面的body
		 * @param {Number} width：(optional) 当前创建的Title容器的宽度；无需定义单位；如果为空，则为100%
		 * @return {Ext.Panel}
		 */
		getTitleEL : function (config){
			var title = config.title ? config.title: "未定义表头";
			var width = config.width ? config.width : "100%";
			var parentEL = config.parentEL ? config.parentEL : Ext.getBody();
			
			return new Ext.Panel({
				hideMode: 'visibility',
				title: title,
		    	width: width,
		    	hideCollapseTool: true,
		    	titleCollapse: false,
		    	collapsible: true,
		    	collapsed: true,
				autoScroll: true,
				renderTo: parentEL,
				single: true
			});
		},
		
		/**
		 * 创建Bar DIV容器，一般可以在创建Toolbar/Searchbar时使用
		 * @param {String} id：Bar渲染的容器id；不可为空
		 * @param {String} parentEL: (optional)当前创建的Bar DIV容器追加到的dom id；如果为空，则为当前页面的body
		 * @param {Number} width：(optional) 当前创建的Bar DIV容器的宽度；无需定义单位；如果为空，则为100%
		 * @return {String} id：Bar渲染的容器id
		 */
		getBarEL : function (config){
			if(!config.id){
				alert("创建commonEL时，id不能为null...");return;
			}
			
			var width = config.width ? config.width : "100%";
			var height = config.height ? config.height : "26";
			var parentEL = config.parentEL ? config.parentEL : Ext.getBody();
			var ELHTML = 
				'<div style="width: ' + width + '; height: ' + height + ';">'+
					'<div id="' + config.id + '"></div>'+
				'</div>';
			Ext.DomHelper.append(parentEL, ELHTML, true);
			return config.id;
		},
		
		/**
		 * 创建Grid DIV容器
		 * @param {String} id：Grid渲染的容器id；不可为空
		 * @param {String} parentEL: (optional)当前创建的Grid DIV容器追加到的dom id；如果为空，则为当前页面的body
		 * @return {String} id：返回Grid渲染的容器id
		 */
		getGridEL : function (config){
			if(!config.id){
				alert("创建gridEL时，id不能为null...");return;
			}
			var width = config.width ? config.width : "100%";
			var parentEL = config.parentEL ? config.parentEL : Ext.getBody();
			var ELHTML = 
				'<div>'+
					'<div id="' + config.id + '" style="width: ' + width + ';"></div>'+
				'</div>';
			Ext.DomHelper.append(parentEL, ELHTML, true);
			return config.id;
		},
		
		/**
		 * 创建TabPanel外包容器：Tab的外包
		 * @param {String} id：TabPanel渲染的容器id；不可为空
		 * @param {String} parentEL: (optional)当前创建的TabPanel外包容器追加到的dom id；如果为空，则为当前页面的body
		 * @return {String} id：TabPanel渲染的容器id
		 */
		getTabPanelEL : function (config){
			if(!config.id){
				alert("创建tabEL时，id不能为null...");return;
			}
			
			var parentEL = config.parentEL ? config.parentEL : Ext.getBody();
			var ELHTML = 
				'<div id="' + config.id + '"></div>';
			Ext.DomHelper.append(parentEL, ELHTML, true);
			
			return config.id;
		},
		
		/**
		 * 创建Tab外包容器
		 * @param {String} id：Tab渲染的容器id；不可为空
		 * @param {String} parentEL: (optional)当前创建的Tab外包容器追加到的dom id；如果为空，则为当前页面的body
		 * @return {String} id：Tab渲染的容器id
		 */
		getTabEL : function (config){
			if(!config.id){
				alert("创建tabEL时，id不能为null...");return;
			}
			
			var title = config.title ? config.title : "未定义Tab名称";
			var parentEL = config.parentEL ? config.parentEL : Ext.getBody();
			var ELHTML =  
				'<div class="x-tab" id="' + config.id + '" title="' + title + '"></div>';
			Ext.DomHelper.append(parentEL, ELHTML, true);
			
			return config.id;
		}
	}
}();
