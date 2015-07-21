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




/**
 * @class hs.StoreFactory
 * Store工厂类 * @singleton
 * @author: wanglf
 */
hs.StoreFactory = function() {
	
	return {
		/**
		* 获取Combox数据源		* @param {String} url
		* 其中记录集 record  为：['id','text']形式
		*/
		getSelectDs:function(url){
			var proxy  = new Ext.data.HttpProxy({
				method: 'POST',
				url:  Ext.getDom("root").value + url
			});
			proxy.on("loadexception", function (httpProxy, o, response) {
				hs.MessageHelper.error({
		        	msg: response.responseText || "您的请求服务器未响应！请刷新页面或重新登陆后重试；如问题依然存在，请与管理员联系！"
				});
			});
			var reader =  new Ext.data.JsonReader({
				totalProperty: "totalCount",
			    root: "result",
			    id: "id"
		    }, Ext.data.Record.create(['id','text']));
		    var store = new Ext.data.Store({
		        proxy:  proxy,
		        reader: reader,
		        remoteSort: true
		    });
		    return store;
	      },
		/**
		 * 获取Combox数据源
		 * 
		 * @param {String}
		 *            url 其中记录集 record 为：['id','text']形式
		 */
		getComboStore : function(jsonData,selectAll) {
			var reader = new Ext.data.JsonReader({
					totalProperty : "totalCount",
					root : "result",
					id : "id"
				}, Ext.data.Record.create(['id', 'text']));
			var store = new Ext.data.Store({
				reader : reader,
				//remoteSort : false,
				data:jsonData
//				sortInfo: {
//				    field: 'id',
//				    direction: 'ASC'
//				}
			});
			if(selectAll) {
				var r;
				if(typeof(selectAll) == "string") {
					r = new store.recordType({ id: '', text: selectAll}, Ext.id());
				}
				else {
					r = new store.recordType({ id: '', text: ' - 不限 - '}, Ext.id());
				}
				store.insert(0, r);
		    }
			return store;
		},		
		/**
		* 获取Grid数据源		* @param {String} url
		* @param {String} record
		* @wanglf
		*/
		getStore: function (url, record) {
			var proxy  = new Ext.data.HttpProxy({
				method: 'POST',
				url:  Ext.getDom("root").value + url
			});
			
			proxy.on("loadexception", function (httpProxy, o, response) {
				hs.MessageHelper.error({
		        	msg: response.responseText || "您的请求服务器未响应！请刷新页面或重新登陆后重试；如问题依然存在，请与管理员联系！"
				});
			});
	
			var reader =  new Ext.data.JsonReader({
				totalProperty: "totalCount",
			    root: "result",
			    id: "id"
		    }, Ext.data.Record.create(record));
		     
		    var store = new Ext.data.Store({
		        proxy:  proxy,
		        reader: reader,
		        remoteSort: true
		    });
		  
		    return store;
		}
		/**
		* 获取Grid数据源不带翻页
		* @param {String} url
		* @param {String} record
		* @wanglf
		*/
		,getStoreNoPaging: function (url, record) {
			var proxy  = new Ext.data.HttpProxy({
				method: 'POST',
				url:  Ext.getDom("root").value + url
			});
			
			proxy.on("loadexception", function (httpProxy, o, response) {
				hs.MessageHelper.error({
		        	msg: response.responseText || "您的请求服务器未响应！请刷新页面或重新登陆后重试；如问题依然存在，请与管理员联系！"
				});
			});
	
			var reader =  new Ext.data.JsonReader({
			    id: "id"
		    }, Ext.data.Record.create(record));
		     
		    var store = new Ext.data.Store({
		        proxy:  proxy,
		        reader: reader,
		        remoteSort: true
		    });
		  
		    return store;
		}
		
		/**
		 * 获取Grid的测试数据,暂时存放,用于ywmimsDemo获取静态测试数据
		 * @param {String} url
		 * @param {String} record
		 */
		,getTestStore: function(testData, record){
			var proxy = new Ext.data.MemoryProxy(testData);
        	var reader = new Ext.data.ArrayReader({},record);
	    	var store = new Ext.data.Store({
	    		autoLoad :true,
	    		proxy: proxy
	    		,reader: reader
	    	});
	    	return store;
    	}
	}
}();

/**
 * @class hs.GridFactory
 * Grid工厂类 * @singleton
 * @author: wanglf
 */
hs.GridFactory = function() {
	return {
		/**
		* 创建一个分页Grid对象
		* @param: config.el cannot be null.
		* @param: config.store cannot be null.
		* @param: config.cm cannot be null.
		* @param: config.sm
		* @param: config.pageSize
		* @param: config.width
		* @param: config.height
		* @param: config.title
		* @return a new GridPanel object
		*/
		getGridPanelOld: function (config) {
			if(!config.el) {alert("<hs.GridFactory.getGridPanel> el is null...");return;}
			if(!config.store) {alert("<hs.GridFactory.getGridPanel> store is null...");return;}
			if(!config.cm) {alert("<hs.GridFactory.getGridPanel> cm is null...");return;}
			
			var width = config.width ? config.width : screen.availWidth-224;
			var height = config.height ? config.height : screen.availHeight-224;
			var cm = new Ext.grid.ColumnModel(config.cm); cm.defaultSortable = true;
			var sm = config.sm ? config.sm : new Ext.grid.RowSelectionModel({singleSelect: true});
			var pageSize = config.pageSize ? config.pageSize : PAGE_SIZE;
			
			var grid = new Ext.grid.GridPanel({
				region:config.region,
				el: config.el,
		        store: config.store,
		        cm: cm,
		        sm: sm,
		        width: width,
		        height: height,
		        title: config.title,
		        enableColumnHide: false,
				enableColumnMove: false,
				enableColumnResize: true,
				enableHdMenu: false,
				enableRowHeightSync: false,
				monitorWindowResize: false,
				trackMouseOver: false,
		        loadMask: {msg:'数据加载中，请稍候...'},
		        enableColLock: false,
		        bbar: new Ext.PagingToolbar({
		            pageSize:pageSize,
		            store: config.store,
		            displayInfo: true
		        }),
				plugins: config.plugins,
		        collapsible: false, // 定义是否可收缩
		        tbar: config.tbar // 是否有grid顶部操作栏
		    });
		    grid.render();
		   
		    return grid;
		},
		/**
		* jinnie edit 创建一个分页Grid对象
		* @param: config.store cannot be null.
		* @param: config.cm cannot be null.
		* @param: config.sm
		* @param: config.pageSize
		* @param: config.width
		* @param: config.height
		* @param: config.title
		* @return a new GridPanel object
		*/
		getGridPanel: function (config) {
			if(!config.store) {alert("<hs.GridFactory.getGridPanel> store is null...");return;}
			if(!config.cm) {alert("<hs.GridFactory.getGridPanel> cm is null...");return;}
			
			var width = config.width ? config.width : 0;
			var height = config.height ? config.height : 0; 
			var cm = new Ext.grid.ColumnModel(config.cm); cm.defaultSortable = true;
			var sm = config.sm ? config.sm : new Ext.grid.RowSelectionModel({singleSelect: true});
			var pageSize = config.pageSize ? config.pageSize : PAGE_SIZE;
			var forceFit = config.forceFit ? config.forceFit : false;
			
			var grid = new Ext.grid.GridPanel({
				region:config.region?config.region:'',
		        store: config.store,
		        cm: cm,
		        sm: sm,
		        width: width,
		        height: height,
		        title: config.title,
		        view: config.view,
		        border : config.border,
		        enableColumnHide: false,
				enableColumnMove: false,
				enableColumnResize: true,
				enableHdMenu: false,   // 控制是否隐藏列操作菜单
				trackMouseOver: true,
		        loadMask: {msg:'数据加载中，请稍候...'},
		        bbar: new Ext.PagingToolbar({
		            pageSize:pageSize,
		            store: config.store,
		            displayInfo: true,
		            plugins: [
		            	new Ext.ux.plugins.PageComboResizer()
		            ]
		        }),
		        listeners:config.listeners,
		        viewConfig: {forceFit: forceFit},
				plugins: config.plugins,
		        collapsible: false, 
		        tbar: config.tbar // 是否有grid顶部操作栏
		    });
		    return grid;
		}
	}
}();

/**
 *  下拉树列表工厂类
 * 	ComboTreeFactory
 *  @author huangrh
 */
hs.ComboTreeFactory = function(){
	return {
		/**
		 * 生成下拉列表选择树
		 * @param
		 * title (String) 根节点名称
		 * rootNode (String) 根节点值 (必填)
		 * url (String) 获取下拉列表数据的URL (必填)
		 * treeName (String) 页面放置树的DIV的名称,避免与其它下拉树重叠 (必填)
		 * hiddenName (String) 作为combox的字段名,默认为空
		 * fieldName (String) 一般与hiddenName同,默认为空
		 * rootVisible (Boolean) 根节点是否可见,默认可见
		 * maxHeight (number) 下拉的最大高度,默认200
		 * allowBlank (Boolean) 选择是否可为空,默认为空
		 * lableText (String) Form面板中显示的名称,默认为空 
		 */
		getComboTree : function(config){
			if(!config.rootNode) {alert("<hs.ComboTreeFactory.getComboTree> config.rootNode is null...");return;}
			if(!config.url) {alert("<hs.ComboTreeFactory.getComboTree> config.url is null...");return;}
			if(!config.treeName) {alert("<hs.ComboTreeFactory.getComboTree> config.treeName is null...");return;}

			var rootNodeId = config.rootNode;
			var comboxWithTree = new Ext.form.ComboBox({ 
				store: new Ext.data.SimpleStore({fields:[],data:[[]]}),
				fieldLabel: config.lableText ? config.lableText : '',
				hiddenName: config.hiddenName ? config.hiddenName : '', 
				name: config.fieldName ? config.fieldName : '',	
				triggerAction:'all',   
				width: config.width ? config.width : 128,
				anchor: config.anchor ? config.anchor : '80%',
				maxHeight: config.maxHeight ? config.maxHeight : 200,
				allowBlank: config.allowBlank==false ? config.allowBlank : true, 
				disabled: config.disabled ? config.disabled : false,
				tpl: "<tpl for='.'><div style='height:200px'><div id='" + config.treeName + "'></div></div></tpl>"   
			}); 
			var rootTreeNode = config.rootTreeNode ? config.rootTreeNode : new Ext.tree.AsyncTreeNode({ text: config.title, id: rootNodeId});
			var treeLoader = config.treeLoader ? config.treeLoader : new Ext.tree.TreeLoader({
				dataUrl : Ext.getDom("root").value + config.url,
				baseParams :{node:rootNodeId}
			})		
			var treeCom = new Ext.tree.TreePanel({   
				loader : treeLoader,
				border : false,   
				root : rootTreeNode
				,rootVisible : config.rootVisible==false ? config.rootVisible : true
			}); 
	
			// 添加'beforeload'监听方法转变下级节点向后台传递的节点ID参数
			treeCom.on('beforeload',function(node){
				if(node.id != rootNodeId){
		        	treeCom.getLoader().baseParams = {node:node.id};
				}
		    }, this);
			treeCom.on('click',function(node){
				if(node != null){
					comboxWithTree.setValue(node.id); 
					comboxWithTree.setRawValue(node.text); 
					comboxWithTree.collapse();   
				}
			});
			if(!config.hadExpand){
				comboxWithTree.on('expand',function(){
					treeCom.render(config.treeName);   
				});
			}
			return {
				getComboxWithTree: function(){
					return comboxWithTree;
				}
				,getTreeCom: function(){
					return treeCom;
				}
			}
		}
	}
}();

var DsFactory  = {	
	recordName : 'Item',
	idName : 'id',
	selRecord: ['id','value'],
	getSelectDs:function(dsurl){
		return new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
	            url: dsurl
	        }),
			reader: new Ext.data.XmlReader({
				record: this.recordName,
	        	id: this.idName
			}, this.selRecord)
    	});
	},
	totalResults : 'TotalResults',
	/*
	 * 参数说明：	 * dsurl:      ds的url
	 * recordName: ds里保存数据的record格式
	 */
	getGridDs:function(config) {
	
		var httpProxy  = new Ext.data.HttpProxy({
			method: 'POST',
			url: config.dsurl
		});
        
		httpProxy.on("loadexception", function (httpProxy, o, response) {
			Ext.MessageBox.alert("操作失败", '对不起，获取数据失败，请刷新页面或重新登陆后再进行操作！');
		});
	
		return new Ext.data.Store({
			proxy: httpProxy,
			reader: new Ext.data.XmlReader({
				record: this.recordName,
				totalRecords: this.totalResults, 
				id: config.id	
			}, config.recordName),
			remoteSort: true
    	});
	}
}
