 /********************************************
 *选择用户
 * 作者：changyw
 * 日期：2008-06-15
 * 修改历史：（修改人 修改日期 修改内容）

 * *****************************************/
  /**
  * 该级部门以上的部门列表信息的链接
  */
var selectManagerURL={
   comBoxTreeRUL:'/dolphin/department!omBoxTreeList.do'
};
  /**
  * TOOLBAL信息包括查询、新增、修改
  */
selectManagerBar=function(config){
	var grid, userWin,deptName,deptCode;
	var comboxWithTree = new Ext.form.ComboBox({ 
		store:new Ext.data.SimpleStore({fields:[],data:[[]]}),   
		editable:false,   
		mode: 'local',   
		triggerAction:'all',   
		maxHeight: 200, 
		emptyText:'请选择',
		blankText:'请选择', 
		tpl: "<tpl for='.'><div style='height:200px'><div id='tree'></div></div></tpl>",   
		selectedClass:'',   
		onSelect:Ext.emptyFn   
	}); 			
	var treeCom = new Ext.tree.TreePanel({   
		loader : new Ext.tree.TreeLoader({
			   dataUrl :Ext.getDom("root").value+selectManagerURL.comBoxTreeRUL,
			   baseParams :{curNode:config.curNode}
		}),
		border:false,   
		root:new Ext.tree.AsyncTreeNode({ text : '所有部门',draggable : false,id : '-1'}) 
		 
	}); 
	treeCom.on('click',function(node){
		comboxWithTree.setValue(node.text); 
		Ext.getDom("s_deptId").value=node.id; 
		comboxWithTree.collapse();   
	});   
	comboxWithTree.on('expand',function(){  
		treeCom.render('tree');   
	});
	var sexSelectDs = hs.StoreFactory.getSelectDs(hs.strutsUrl.DATADICT_COMMON_URL);
	sexSelectDs.load({params : {typeStr : 'sex'}});
	
	var searchBar=new Ext.Toolbar(hs.ELFactory.getBarEL({id: "searchbarEL"}));				
		searchBar.add("姓名", new Ext.form.TextField({ name:'s_name', id:'s_name',width:100}),'-',
		              "身份证号码", new Ext.form.TextField({ name:'s_idCard', id:'s_idCard',width:100}),'-',
		              "性别", new Ext.form.ComboBox({hiddenName: 's_sex',displayField:'text',store: sexSelectDs , width: 70}),
				      "所在部门",comboxWithTree,new Ext.form.TextField({id: 's_deptId',hiddenName: 's_deptId', hidden: true}),'-',
				      {text:'查询', iconCls: 'hs-button-search', handler: search}
		);
	var addBar=new Ext.Toolbar(hs.ELFactory.getBarEL({id: "addbarEL"}));		
	addBar.add({text:'新增', iconCls: 'hs-button-add', handler: function() {
				   if(!userWin){
				      userWin = common.UserWin (search);
				   }
				   userWin.show("新增用户");
				   userWin.setFormValue({curNode:deptCode,deptName:deptName});
		          }
	          }
	         ,{ 
		          text:'修改', iconCls: 'hs-button-edit', handler: function() {
			         var record = grid.getSelected();
	    	         if(!record){ 
	       		       hs.MessageHelper.info({msg: '请选择记录后再进行操作！'});
	       		       return;
	      	         }
			         if(!userWin){
				      userWin = common.UserWin(search);
	                 }
	                 userWin.show("修改用户", record);
		            }
	         }
	
	     );
	 function search() {
	   	  grid.load({ 
		  		  userName: Ext.getDom("s_name").value
				 ,idCard: Ext.getDom("s_idCard").value
			     ,sex: Ext.getDom("s_sex").value
				 ,parentId:deptCode
				 ,deptId: Ext.getDom("s_deptId").value
		  });
	 }
    return {
			init:function(config){
				deptName=config.deptName;
	            deptCode=config.curNode;
				  grid = config.grid;
				  search();
			    }
			,getTreeCom:function(){
				 return treeCom;
				}
			,getSearchBar:function(){
			     return searchBar;
			    }
			 ,getAddBar:function(){
			     return addBar;
			    }
			 ,setValue:function(config){
			     deptName=config.deptName;
			     deptCode=config.curNode;
			    }
	}
}
 /**
  * 选择部门经理信息
  */
selectManager=function(){
	var userBar;
	var selectManagerWin;
	var userGrid;
	return {
	      showWin:function(config){
		    if(!selectManagerWin){
		       userBar=selectManagerBar(config);
			   userGrid=common.UserGrid({height:440,width:665,userURL:userURL.queryUserByDepartment});
			   var managerPanel = new Ext.Panel({
				   border:true,
				   autoScroll:true,
				   items:[userBar.getSearchBar(),userBar.getAddBar(),userGrid.getUserGrid()]});
			   selectManagerWin = new Ext.Window({
				   title:config.title||'部门经理列表'
				  ,width: config.width||720
				  ,height:config.height||570
				  ,hideBorders :true
				  ,items:managerPanel
			   });
		  }
		  userBar.init({grid:userGrid,curNode:config.curNode});  
		  userBar.setValue({deptName:config.deptName,curNode:config.curNode});
		  var treeCom=userBar.getTreeCom();
		  var treeLoader= treeCom.getLoader();
		  treeLoader.baseParams.curNode=config.curNode;
		  treeLoader.load(treeCom.root);
		  selectManagerWin.show();	
		  userGrid.getUserGrid().on("dblclick",function(){
			   var recordData= userGrid.getUserGrid().getSelectionModel().getSelected();
			   if(recordData){
				   Ext.getDom('deptManagerName').value=recordData.get('userName');
				   Ext.getDom('deptManager').value=recordData.get('id');
				   selectManagerWin.hide();
			   }
	      });	    
	    }
	 }
}


 
 
	



