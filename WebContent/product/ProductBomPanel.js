Ext.namespace("product.bom");

product.bom.url = {
	 queryTree : '/product/product-bom!queryTree.do'
	,save : '/product/product-bom!saveBom.do'
	,remove : '/product/product-bom!removeBom.do'
	,exportExcel : '/product/product-bom!exportExcel.do'
	
};

product.bom.record = [
	 {name:"bomId"}
	,{name:"productId"}
	,{name:"partId"}
	,{name:"itemType"}
	,{name:"itemId"}
	,{name:"itemName"}
	,{name:"parentId"}
	,{name:"unit"}
	,{name:"price"}
	,{name:"number",type: 'int'}
	,{name:"sumPrice",type: 'int'}
	,{name:"remark"}
];

product.bom.MainPanel = function(config) {
	this.config = config?config:{};
};

product.bom.MainPanel.prototype = {
	getDetailGrid: function(){
		if (!this.detailGrid) {
			var _this = this;
			
			var ms = new Ext.tree.MultiSelectionModel();
			var tree = new Ext.ux.tree.EditorGrid({
				region: "center",
				border:false,
		        height: 560,
		        enableDD: true,
		        clicksToEdit: 1,
		        loadMask:{msg: '正在加载数据...'},
		        animate:true,
		        selMode: ms,
		        columns: [
					 {header:"名称",dataIndex:'itemName',width:120}
					,{header:"bomId",dataIndex:'bomId',width:60,hidden:true}
					,{header:"type",dataIndex:'itemType',width:60,hidden:true}
					,{header:"产品ID",dataIndex:'productId',width:60,hidden:true}
					,{header:"部件ID",dataIndex:'partId',width:60,hidden:true}
					,{header:"部件/原材料ID ",dataIndex:'id',width:60,hidden:true}
					//,{header:"编号 ",dataIndex:'no',width:90}
					,{header:"规格 ",dataIndex:'standard',width:60}
					,{header:"单位",dataIndex:'unit',width:44,align:'center'}
					,{header:"单位重量",dataIndex:'weight',width:60,align:'right'}
					,{header:"单位面积",dataIndex:'area',width:60,align:'right'}
					,{header:"数量",dataIndex:'number',width:50,align:'right',
						editor: new Ext.form.TextField({
			                allowBlank: false,
			                width:80,
			                selectOnFocus: true
			            })
					}
					,{header:"备注",dataIndex:'remark',width:100,
						editor: new Ext.form.TextArea({
			                width:80,
			                selectOnFocus: true
			            })
					}
		        ],
		        loader: new Ext.tree.TreeLoader({
		        	baseParams:{productId:_this.productId},
		            url: Ext.getDom("root").value + product.bom.url.queryTree,
		            preloadChildren: true,
		            listeners: {
		                loadexception: function(loader, node, response) {
		                    var msg = 'unable to fetch data. response.status:' + response.status + (e ? (', error: ' + e.message) : '');
		                    Ext.Msg.alert('Demo', msg, 10);
		                },
		                load: function(loader, node, response) {
		                    //Ext.Msg.alert('Demo', 'Loading complete...',3);
		                }
		            }
		        }),
		        listeners: {
		            'cellclick':function(node,e) {
		            },
		            'beforeedit': function(e) {
//		            	if (e.node.attributes.type=='part')
//		            		return false;
		            }
		        },
		        tbar:[{
					text : "编辑",
					iconCls : 'hs-button-edit',
					scope : this,
					enableToggle:true,
					handler : function() {
						var saveBtn = tree.getTopToolbar().get(2);
						var addBtn = tree.getTopToolbar().get(4);
						var add2Btn = tree.getTopToolbar().get(5);
						var delBtn = tree.getTopToolbar().get(7);
						saveBtn.setDisabled(!saveBtn.disabled);
						addBtn.setDisabled(!addBtn.disabled);
						add2Btn.setDisabled(!add2Btn.disabled);
						delBtn.setDisabled(!delBtn.disabled);
					}
				},'-', {
					text : "保存",
					iconCls : 'icon_save2',
					disabled:true,
					scope : this,
					handler : function() {
						var rootNode = tree.getRootNode();
						var param = {};
						var fileds=['productId','itemType', 'partId', 'id', 'number', 'remark'];
						
						function traverseTree(node1) {
							var childNodes = node1.childNodes;
							if(childNodes && childNodes.length > 0) {
								Ext.each(childNodes, function(node,i){
									Ext.each(fileds, function(filed,index){
										if(!param[filed]) {
											param[filed] = new Array();
										}
										param[filed].push(node.attributes[filed]);
									});
									if(node.hasChildNodes()) {
										traverseTree(node);
									}
								});
							}
						}
						traverseTree(rootNode);
						hs.ActionHelper.request(
							product.bom.url.save,
							param,
							function(){
								
							}
						);
					}
				},'-',{
					text : "添加部件",
					iconCls : 'hs-button-add',
					scope : this,
					disabled:true,
					handler : function() {
						var _this = this;
						var addpartWin = new product.part.card.Win({
							productId:this.productId,
							callback:function() {
								tree.getRootNode().reload();
							},
							callbackClose:true
						});
						addpartWin.show();
					}
				},{
					text : "添加原材料",
					iconCls : 'hs-button-add',
					scope : this,
					disabled:true,
					handler : function() {
						var node = this.getSelected();
						if(!node || node.attributes['itemType']!='part'){
						   hs.MessageHelper.info({msg : '请选择一个部件后再进行操作！'});
						   return;
						}
						
						var materialChooser = new MaterialChooser({
							scope:_this,
							callBack:function(record){
								node.appendChild(new Ext.tree.TreeNode({
									itemType:	'material',
									productId:	_this.productId,
									partId:	node.attributes['id'],
									itemId:		record.get('materialId'),
									itemNo:		record.get('materialNo'),
									itemName:	record.get('materialName'),
									unit:	record.get('unit'),
									standard:record.get('standard'),
									weight:	record.get('weight'),
									area:	record.get('area'),
									price:	record.get('price'),
									number:	1,
									remark:	record.get('remark')
								}));
								node.expand(true,true);
					    	}
						});
						materialChooser.show();
						
					}
				},'-', {
					text : "删除",
					iconCls : 'hs-button-remove',
					scope : this,
					disabled:true,
					handler : function() {
						var node = this.getSelected();
						if(!node){
							return;
						}
						var _this = this;
						
						hs.MessageHelper.confirm('确定要删除？', function() {
							if(node.get('bomId')) {
								hs.ActionHelper.request(
										product.bom.url.remove
										,{bomId: node.get('bomId'),itemType:node.get('itemType'),partId:node.get('partId')}
										,function(){tree.getRootNode().reload();}
								);
							}
							else {
								node.remove();
							}
						});
					}
				},'-', {
					text : "刷新",
					iconCls : 'icon_refresh',
					scope : this,
					handler : function() {
						tree.getRootNode().reload();
					}
				}, {
					text : "导出Excel",
					iconCls : 'icon_excel',
					scope : this,
					handler : function() {
						window.open(Ext.getDom('root').value+product.bom.url.exportExcel+'?productId='+this.productId);
					}
				}
        	]
		    });
				
			this.detailGrid = tree;
		}
		return this.detailGrid;
	}
	,show : function(productId, productName, productNo) {
		if (!this.win) {
			var _this = this;
			this.productId = productId;
			var title = '产品BOM清单';
			if(productName) title+='__'+productName;
			if(productNo) title+='__'+productNo;
			var win = new Ext.Window({
				title:title,
				frame : true,
				width : 680,
				height: 500,
				closeAction : 'hide',
				layout: 'border',
				items:[this.getDetailGrid()]
			});
			
			this.win = win;
		}
		this.win.show();
		//this.getDetailGrid().load({productId: productId});
	}
	,getSelected: function(){
		var node = this.getDetailGrid().getSelectionModel().selNode;
		
		return node;
	}
};