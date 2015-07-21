/********************************************
 * 数据字典数管理模块
 * 作者
 * 日期：2008-07-8
 * 修改历史：（修改人 修改日期 修改内容）
 * *****************************************/


/**
 * 数据字典
 */
var datadictRecord = [
    {name: "id"}
	,{name: "resName"}
	,{name: "parentId"}
	,{name: "remark"}
	,{name: "resType"}
	,{name: "resCode"}
	,{name: "resNameSub"}
	,{name: "childnum"}
 ];
 /**
  * 数据字典数的链接
  */
var DataDictURL={
	treeUrl:'/dolphin/datadict!datadictTreeList.do',
	formUrl:'/dolphin/datadict!get.do',
	saveUrl:'/dolphin/datadict!save.do',
	deleteDataDictURL:'/dolphin/datadict!delete.do',
	treeRootId:''
};
var DataDictChooser= function(config){
	this.config=config;
};
DataDictChooser.prototype={
	show:function(el,callback){
		if(!this.win){
         this.getTree();
         this.getForm();
		 var cfg={
			title:this.config.title||'选择数据字典',
			layout:'border',
			width: 600,
			height: 500,
			modal: true,
			closeAction: 'hide',
			tbar:[
				  {text:'名称:'},
				  {xtype:'textfield',id:'dataDictName',selectOnFocus:true,width:100},
				  {xtype:'button',id:'query_dataDict',text:'查询'}
				],
		   border: true,
		   items:[this.tree,this.form],
		   buttons:[{id:'ok_btn',text:'确定',handler:this.docallback,scope: this},
			      {id:'cancel_btn',text:'取消',scope:this,handler: function(){this.win.hide(); }}],
		   keys: {
					key: 27, // Esc key
					handler: function(){ this.win.hide(); },
					scope: this
				}
		 };
		 Ext.apply(cfg, this.config);
		 this.win = new Ext.Window(cfg);
		}
//		this.reset();
	    this.win.show(el);
		this.callback = callback;
		this.animateTarget = el;
	},
	showViewPort: function(){
	  if(!this.viewPort){
		  this.getTree();
	      this.getForm();
          var cfg={
			layout:'border',
		    border: true,
		    items:[this.tree,this.form]
		 };

//		 Ext.apply(cfg, this.config);
		   this.viewPort = new Ext.Viewport(cfg)
		}
         return this.viewPort;
	},
	/**
	 * 返回数据回调函数
	 */
	doCallback:function(node){
	},
	/**
	 * 新增一个子节点
	 */
	addDataDict:function(){
		var selNode = this.tree.getSelectionModel().getSelectedNode();
		if(!selNode){
			hs.MessageHelper.info({
    			msg: "请选择节点,添加其子节点!"
    		});
			return ;
		}
		if(selNode.id && selNode.id == -1){
			hs.MessageHelper.info({
    			msg: "请先保存当前节点,再添加其子节点!"
    		});
			return ;
		}
		if (selNode){
		  selNode.expand(false, false)
		  var node = selNode.appendChild(new Ext.tree.TreeNode({
			text:'<span style="color:red;">请输入名称 </span>',
			cls: 'album-node',
			isLeaf:true,
            allowDrag     : true,
            allowDelete   : true,
            allowEdit     : true,
            allowChildren : true}));
           node.id=-1;
		   this.tree.getSelectionModel().select(node);
		   var tempForm = this.form.getForm();
		   tempForm.clearInvalid();
           tempForm.setValues({parentId:selNode.id,resName:'',resNameSub:'',id:'',resCode:'',remark:''});
		   tempForm.findField('resName').focus(true);
		}else{
		   hs.MessageHelper.alert('请选择一个节点！');
		}

	},
	 deleteDataDict:function(){
		var selNode = this.tree.getSelectionModel().getSelectedNode();
		var tempForm = this.form.getForm();

		if(!selNode){
		 	hs.MessageHelper.info({
    			msg: "请选择要删除的节点!"
    		});
		 return ;
		}
		var aParentNode=selNode.parentNode;
		var aTree=this.tree;
		if (selNode){
		 if(selNode.id==-1){
		 	selNode.remove();
		 }else{
 	        hs.MessageHelper.confirm(
	      	    "确认删除所选节点吗？", function() {
	      	       Ext.Ajax.request({
			   	         url: Ext.getDom('root').value+DataDictURL.deleteDataDictURL,
			             method:'post',
			             success: function(result,request){
			           	if(Ext.util.JSON.decode(result.responseText).data!=null&&Ext.util.JSON.decode(result.responseText).data!=''){
			             		  Ext.Msg.alert("提示", Ext.util.JSON.decode(result.responseText).data);
			             	}else{
			             		 //Ext.Msg.alert("成功", '操作成功！');
			             		 selNode.remove();
			             		 aTree.getSelectionModel().select(aParentNode);
                                 tempForm.setValues({parentId:selNode.id,resName:'',resNameSub:'',id:'',resCode:'',remark:''});
			             	}
			             },
			             failure: function(result,request){
			   		         Ext.Msg.alert("失败", Ext.util.JSON.decode(result.responseText).data);
			   	         } ,params:{id:selNode.id}
			        });
	      		}
	      	) ;
		 }
		 }else{
			 hs.MessageHelper.alert('请选择一个部门节点！');
		}
	},
	/**
	 * 保存修改的数据字典数据
	 */
	saveDataDict:function(){
		var selNode= this.tree.getSelectionModel().getSelectedNode();
			if(!selNode){
		 	hs.MessageHelper.info({
    			msg: "请选择节点!"
    		});
		 return ;
		}
		var aTree=this.tree;
		var aParentNode=selNode.parentNode;
		Ext.getDom('id').value=selNode.id;
		hs.FormHelper.submit(
    	    DataDictURL.saveUrl,
    	    function(form, action) {
    		  hs.MessageHelper.succuss({
    			 callback: function() {
    			 	if(selNode.id==-1){
    			 	   aTree.getLoader().load(aParentNode ,function(){
    			 		    aTree.expandPath(Ext.getDom('root').value+DataDictURL.treeUrl);
					        aParentNode.expand();
					        aTree.getSelectionModel().select(aParentNode);
    			 	   });
    		        }
    			  }
			  });
		    },
		 {},
		 this.form.getForm());

	},


	/**
	 * 重新初始化窗口
	 * //TODO
	 */
	reset : function(){

	},
	/**
	 * 生产一棵很基础的数据字典数，TODO 该方法有问题。
	 */
	makeTree:function(config){
		 var treeLoader = new Ext.tree.TreeLoader({dataUrl:this.config.treeUrl,
		    baseParams:{parentId:'0'}
//		    listeners:{
//            		'load':{fn:function(){this.tree.root.firstChild.select();this.showDetails(this.tree.root.firstChild)},scope:this,single:true}
//            	}}
		 });
		 var tree =  new Ext.tree.TreePanel({
            loader: treeLoader,
		    rootVisible: false,
		    root:new Ext.tree.AsyncTreeNode()
          });
        Ext.apply(tree,config);
        return tree;
	},
	/**
	 * 生产一个数据字典Form
	 */
	makeForm:function(config){
		var form = new Ext.FormPanel({
            buttonAlign:'left',
            frame:true,
            labelAlign: 'right',
			items: [{hidden: true,
	               items: [
	        	   new Ext.form.TextField({name: 'id',id:'id'}),
	               new Ext.form.TextField({name: 'parentId',id:'parentId'})
	              // new Ext.form.TextField({name: 'codeLevel'})
			    ]},
                { title: this.config.formTitle ? this.config.formTitle : '数据字典信息',
                xtype:'fieldset',
                layout:'form',
                 items: [
                    new Ext.form.TextField({fieldLabel: '名称', name: 'resName',width:300, allowBlank: false,
                     listeners:{
            		'change':{fn:function(o){this.tree.getSelectionModel().getSelectedNode().setText(String(o.getValue()));},scope:this}
            	     }}),
                    new Ext.form.TextField({fieldLabel: '别名', name: 'resNameSub',width:163}),
                     new Ext.form.TextField({fieldLabel: '资源类型', name: 'resType',width:163}),
	        		new Ext.form.TextField({fieldLabel: '资源码', name :'resCode',width:163}),
	        		{fieldLabel: '排序号', name :'sortNum',width:163,allowBlank: false,xtype:'numberfield'},
	        		{fieldLabel: '是否禁用', hiddenName :'isDisable',hiddenValue :'0',width:163,xtype:'combo',store:hs.LabelValue.BOOL_STORE(),typeAhead: true},
				  new Ext.form.TextArea({fieldLabel: '备注', name: 'remark',width:350,hight:10})
                 ]
                 
               }
             ],
             buttons:[{text:'确定保存',
        			id:'900602',
        			handler:this.saveDataDict,scope:this}]
        	
          });

        Ext.apply(form, config);
        return form;
	},
	/**
	 *  刷新数据字典form详细信息
	 */
	resetFormDetail:function(node){
		var _params = Ext.apply({},{formOrGrid: "form",id:node.id});
//		hs.FormHelper.load(this.config.formUrl,_params,function(form,action){
//				var data = Ext.util.JSON.decode(action.result.data);
//				form.clearInvalid();
//				form.setValues(data);
//			},this.form.getForm());
		this.form.getForm().doAction('load',{
			failure: function(form, action) {
				var msg;
				if(action.result)
					msg = action.result.data;
				if(!msg)
					msg = "您的请求服务器未响应！请刷新页面或重新登陆后重试；如问题依然存在，请与管理员联系！";
				if('true'==action.result.multi){
				  hs.MessageHelper.multiError({
		        	msg: msg
				});
			 }else {
			 	hs.MessageHelper.error({
		        	msg: msg
				});
			 }
			},
			method: 'POST',
			params: _params,
			success: function(form,action){
				var data = Ext.util.JSON.decode(action.result.data);
				form.clearInvalid();
				form.setValues(data);
			},
			url: this.config.formUrl,
			waitMsg: '',
			waitTitle: '请稍候'

		});
	},
	/**
	 * 获取DataDictChooser数据字典树
	 */
	getTree:function(){
		if(!this.tree){
			this.tree=  new Ext.tree.TreePanel({
		  	title:this.config.treeTitle||'数据字典树',
		  	bodyStyle:'background:white',
            animate:true,
            region:'west',
            split: true,
            frame: true,
            containerScroll: true,
            autoScroll:true,
            width:225,
            loader: new Ext.tree.TreeLoader({
            	dataUrl:this.config.treeurl,
//            	baseParams:{parentId:'0'},
            	listeners:{
            		'load':{fn:function(){this.tree.root.firstChild.select();this.showDetails(this.tree.root.firstChild)},scope:this,single:true}
            	}
            	}),
		    buttons:[{text:'添加下级节点',id:'900601',handler:this.addDataDict,scope: this},
		             {text:'删除节点',id:'900603',handler:this.deleteDataDict,scope:this}],
		    rootVisible: this.config.viewRoot ? this.config.viewRoot : false,
		    listeners: {
			     'click': {fn: this.showDetails, scope:this},
		    	 'dblclick': {fn: this.doCallback, scope:this}
			    },
		      root:new Ext.tree.AsyncTreeNode({
		       text : this.config.rootText ? this.config.rootText : '数据结构设置',
               draggable : true,
               allowDrag :true,
               id : this.config.rootId ? this.config.rootId : '0'
		    })
          })
		}
		return this.tree;
	},
	/**
	 * 获取DataDictChooser数据字典Form
	 */
	getForm:function(){
		if(!this.form){
			var formCfg={
				//bodyStyle: 'padding-bottom:15px;background:#eee;',
                region:'center',
               // frame: true,
                title:'',
                autoScroll:true
                
               
               
			};
			this.form = this.makeForm(formCfg);
		}
		return this.form;
	},
    showDetails : function(node){
       var detailEl =this.form.getEl();
       if(node.id && node.id == -1) return;
       if(node){
//                detailEl.hide();  // 动画效果
                this.resetFormDetail(node);
//                detailEl.slideIn('l', {stopFx:true,duration:.2});
	    }else{
		detailEl.update('');
	    }
	}

};