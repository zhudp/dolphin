Ext.namespace("file.list");

file.list.url = {
	 query : '/file/file!queryPaged.do'
	,save : '/file/file!saveFile.do'
	,remove : '/file/file!remove.do'
};

file.list.record = [
     {name:"fileId"}
	,{name:"fileName"}
	,{name:"fileSuffix"}
	,{name:"objectType"}
	,{name:"objectId"}
	,{name:"path"}
	,{name:"fullPath"}
	,{name:"fileSize"}
	,{name:"remark"}
	,{name:"isDeleted"}
	,{name:"gmtCreate"}
	,{name:"creator"}
	,{name:"creatorId"}
	,{name:"gmtModify"}
	,{name:"modifier"}
	,{name:"modifierId"}
];

/**
 * 附件列表
 * @param  config <br>
 * .title 	窗口名称 （默认）<br>
 * .objectType 	文件所属类别：产品/客户等 （必填）<br>
 * .objectId 	文件所属对象ID：产品ID/客户ID等 （必填）<br>
 * .callback 	回调方法 （必填）<br>
 */
file.list.MainPanel = function(config) {
	this.config = config?config:{};
};

file.list.MainPanel.prototype = {
	getWin : function() {
		if (!this.win) {
			var _this = this;
			
			var win = new Ext.Window({
				title:_this.config.title||'附件列表',
				frame : true,
				width : 640,
				layout : 'fit',
				autoHeight : true,
				closeAction : 'hide',
				items:[this.getGrid()]
		        
			});
			
			this.win = win;
		}
		return this.win;
	}
	,getGrid: function(){
		if (!this.listGrid) {
			var _this = this;
			var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true });
			var gridStore = hs.StoreFactory.getStore(file.list.url.query, file.list.record);
			_this.config.params = { start : 0, limit : 20};
			gridStore.sortInfo={field: "gmtCreate", direction: "DESC"};
			
			var grid = new Ext.grid.EditorGridPanel({
				height:360,
				border:false,
                store: gridStore,
                clicksToEdit: 1,
                cm: new Ext.grid.ColumnModel([ 
					new Ext.grid.RowNumberer()
					,sm
					,{header : "文件名",id:'fileName', dataIndex : 'fileName',width : 120,sortable:true
						,renderer:function(v, meta, record) {
							return "<a href='"+record.get('fullPath')+"' target='_blank'>"+v+"</a>";
						} 
					}
					//,{header : "文件后缀",dataIndex : 'fileSuffix',width : 60 ,hidden:true}
					,{header : "所属对象ID",dataIndex : 'objectId',width : 60 ,hidden:true}
					//,{header : "存放路径（相对）",dataIndex : 'path',width : 60 ,hidden:true}
					,{header : "文件大小",dataIndex : 'fileSize',width : 70 ,align:'right'}
					//,{header : "备注",dataIndex : 'remark',width : 60 }
					,{header : "删除标志",dataIndex : 'isDeleted',width : 60 ,hidden:true}
					,{header : "上传时间",dataIndex : 'gmtCreate',width : 80 ,sortable:true}
					,{header : "上传人",dataIndex : 'creator',width : 80 ,sortable:true}
					,{header : "创建人ID",dataIndex : 'creatorId',width : 60 ,hidden:true}
//					,{header : "更新时间",dataIndex : 'gmtModify',width : 80 }
//					,{header : "更新人",dataIndex : 'modifier',width : 80 }
//					,{header : "更新人ID",dataIndex : 'modifierId',width : 60 ,hidden:true}
				]),
                sm: sm,
                autoExpandColumn :'fileName',
                autoExpandMin : 90,
				enableColumnResize: true,
				stripeRows:true,
				trackMouseOver: true,
		        loadMask: {msg:'数据加载中，请稍候...'},
		        enableColLock: false,
		        bbar: new Ext.PagingToolbar({
		            pageSize: PAGE_SIZE,
		            store: gridStore,
		            displayInfo: true,
		            plugins: [new Ext.ux.plugins.PageComboResizer()]
		        }),
	        	tbar:[{
						text : "编辑",
						iconCls : 'hs-button-edit',
						scope : this,
						enableToggle:true,
						handler : function() {
							var addBtn = grid.getTopToolbar().get(2);
							var delBtn = grid.getTopToolbar().get(4);
							addBtn.setDisabled(!addBtn.disabled);
							delBtn.setDisabled(!delBtn.disabled);
						}
					}
//	        		,'-', {
//						text : "保存",
//						iconCls : 'icon_save2',
//						disabled:true,
//						scope : this,
//						handler : function() {
//							
//							var param = {};
//							var fileds=gridStore.reader.meta.fields;
//							if(gridStore.getCount() > 0) {
//								
//								gridStore.each(function(rec, index) {
//									Ext.each(fileds, function(filed,index){
//										if(!param[filed.name]) {
//											param[filed.name] = new Array();
//										}
//										param[filed.name].push(rec.get(filed.name));
//									});
//								});
//							}
//							
//							hs.ActionHelper.request(
//								file.list.url.save,
//								param,
//								function(){
//									
//								}
//							);
//						}
//					}
	        		,'-',{
						text : "添加",
						iconCls : 'hs-button-add',
						scope : this,
						disabled:true,
						handler : function() {
							var uploadFileWindow = new Ext.ux.chooser.UploadFileWindow({
								objectType:_this.config.objectType,
								objectId:_this.config.objectId,
								callback:function(fullUrl, value) {
									gridStore.reload();
								}
							});
							uploadFileWindow.show();
						}
					},'-', {
						text : "删除",
						iconCls : 'hs-button-remove',
						scope : this,
						disabled:true,
						handler : function() {
							var record = this.getSelected();
							if(!record){
								return;
							}
							var _this = this;
							hs.MessageHelper.confirm('确定要删除？', function() {
								hs.ActionHelper.request(
										file.list.url.remove
										,{fileId: record.get('fileId')}
										,function(){gridStore.reload();}
								);
							});
						}
					}
	        	],
		        load: function(baseParams) {
		        	hs.StoreHelper.beforeload(gridStore, baseParams);
					gridStore.load({
						params: {
							start: 0,
							limit: PAGE_SIZE
						}
					});
				}
            });
			grid.load(_this.config);
			this.listGrid = grid;
		}
		return this.listGrid;
	}
	,getSelected: function(){
		var records = this.getGrid().getSelectionModel().getSelections();
		if (!records || records.length == 0) {
			hs.MessageHelper.info({msg : '请选择记录后再进行操作！'});
			return;
		}
		if (records.length > 1) {
			hs.MessageHelper.info({msg : '请选择1条记录进行操作！'});
			return;
		}
		return records[0];
	}
};