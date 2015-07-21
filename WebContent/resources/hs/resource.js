dolphin ={};
Ext.namespace("dolphin.resource");

/**参数服务URL**/
dolphin.resource.Url={
   datadictComboURL : "/dolphin/datadict!buildComboSelect.do",// 根据类型从后台组装Combox下拉列表URL
   datadictTreeComboURL : "/dolphin/datadict!datadictTreeList.do",// 根据节点获取数据字典树的URL(取到的树包含了父节点及其子节点的所有值)
   deptTreeComboURL : '/dolphin/department!departmentTreeList.do',// 异步方式读取部门下拉树
   deptWholeTreeURL:'/dolphin/department!buildWholeDeptTree.do', //读取整棵部门数
   busTypeWholeTreeURL:'/dolphin/define-type!buildWholeBusTypeTree.do' //读取整棵车辆类型树
}
/**数据资源常量**/
dolphin.resource.Constant={
	USER_STATUS_VALID:'1'  //正常用户
	
}
/**
 *  下拉选择服务工具类，提供获取下拉列表数据，转换Grid列代码为中文名 
 */

dolphin.resource.Util= function(){
	/**
	 * 将编码转换为名称，在页面上渲染出来
	 */	
	function _render(v, store) {
		for (var i = 0; i < store.getCount(); i++) {
			var record = store.getAt(i);
			if (record.get("id")!=""&& v == record.get("id") )
				return record.get("text");
		}
		return "";
	};
	function _renderColor(v, store,v1) {
		var tmp =_render(v, store);
	    if(v==v1){
	    	return '<span style="color:green;">' + tmp + '</span>';
	    }else{
	      return '<span style="color:red;">' + tmp + '</span>';
	    }
    };
  
  return{
  	//将8位整型转换为yyyy-MM-dd 日期格式
  	int2DateRender: function(v){
  		if(v.length()==8){
			return dateStr = v.substring(0,4) + "-" + v.substring(4,6) + "-" + v.substring(6);
  		}
  		return v;
	},
  	/**
  	 * 将GRID中的<代码>列转换为中文名称，显示出来  
  	 */
  	render:function(store){
		return function(v) {
				return _render(v, store);
		}
	},
	/**
	 * 渲染用户状态颜色
	 */
	renderUserStatusColor:function(store,v1){
		return function(v) {
				return _renderColor(v, store,dolphin.resource.Constant.USER_STATUS_VALID);
		};
	},
	/**
	 * 取得编码对应的中文名称
	 */
	getLabelByCode:function(code,store){
		return _render(code,store);
	},
  	
  	/**
  	 * 取数据字典表定义的下拉列表数据Store
  	 * config.resType：资源类别
  	 */
   	getComboStore:function(config,selectAll) {
  		return hs.StoreFactory.getComboStore(comboJsonData[config.resType], selectAll);
  	},
   //取性别Store
  	getSexComboStore:function(selectAll){
  		var cfg ={resType:"SEX"};
  		return this.getComboStore(cfg, selectAll);
  	},
  //取是或否
  	getYesOrNoComboStore:function(selectAll){
  		var cfg ={resType:"YESORNO"};
  		return this.getComboStore(cfg, selectAll);
  	},
  	//取政治面貌
  	getPolicitalStatusComboStore:function(selectAll){
  		var cfg ={resType:"POLICITAL_STATUS"};
  		return this.getComboStore(cfg,selectAll);
  	},
  	//取婚姻状况
  	getMaritalStatusComboStore:function(selectAll){
  		var cfg ={resType:"MARITAL_STATUS"};
  		return this.getComboStore(cfg,selectAll);
  	},
  	//取文化程度
  	getEducationComboStore:function(selectAll){
  		var cfg ={resType:"EDUCATION"};
  		return this.getComboStore(cfg,selectAll);
  	},
  	//职称技术等级
  	getTechnicalGradeComboStore:function(selectAll){
  		var cfg ={resType:"TECHNICAL_GRADE"};
  		return this.getComboStore(cfg,selectAll);
  	},
  	
  	//民族
  	getNationComboStore:function(selectAll){
  		var cfg ={resType:"NATION"};
  		return this.getComboStore(cfg,selectAll);
  	},
  	//操作类别
  	getOperTypeComboStore:function(selectAll){
  		var cfg ={resType:"OPER_TYPE"};
  		return this.getComboStore(cfg,selectAll);
  	},
  	//User表 用户状态
  	getUserStatusComboStore:function(selectAll){
  		var cfg ={resType:"USER_STATUS"};
  		return this.getComboStore(cfg,selectAll);
  	},
  	//公司状态
  	getCompanyStatusComboStore:function(selectAll){
  		var cfg ={resType:"COMPANY_STATUS"};
  		return this.getComboStore(cfg,selectAll);
  	},
  	//证件类型
  	getCardTypeComboStore:function(selectAll){
  		var cfg ={resType:"CARDS_TYPE"};
  		return this.getComboStore(cfg,selectAll);
  	},
  	//会员类别
  	getMemberTypeStore:function(selectAll){
  		var cfg ={resType:"MEMBER_TYPE"};
  		return this.getComboStore(cfg,selectAll);
  	},
  	//会员状态
  	getMemberStatusStore:function(selectAll){
  		var cfg ={resType:"MEMBER_STATUS"};
  		return this.getComboStore(cfg,selectAll);
  	},
  	//发布类型
  	getPublishTypeComboStore:function(selectAll){
  		var cfg ={resType:"PUBLISH_TYPE"};
  		return this.getComboStore(cfg,selectAll);
  	},
  	
  	//咨询投诉类别
  	getConsultTypeComboStore:function(selectAll){
  		var cfg ={resType:"CONSULT_TYPE"};
  		return this.getComboStore(cfg, selectAll);
  	},
  	
  //从事职业
  	getJobComboStore:function(selectAll){
  		var cfg ={resType:"JOB"};
  		return this.getComboStore(cfg, selectAll);
  	},
  	
  	 //从事职业
  	getIsActivityComboStore:function(selectAll){
  		var cfg ={resType:"IS_ACTIVITY"};
  		return this.getComboStore(cfg, selectAll);
  	},
  	
  	//帮助主题
  	getHelpTypeComboStore:function(selectAll){
  		var cfg ={resType:"HELP_TYPE"};
  		return this.getComboStore(cfg, selectAll);
  	},
  	//取部门下拉树数据loader
  	getDeptComboTreeLoader:function(){
  		var treeLoader = new Ext.tree.TreeLoader({
//  			                    listeners:{'load':{fn:function(){this.tree.root.firstChild.select()},scope:this,single:true}},
								url : Ext.getDom("root").value + dolphin.resource.Url.deptWholeTreeURL
							});
		
	    return treeLoader;
  	}
  	,
  	  	  	//取车辆类型下拉树数据loader
  	getBusTypeComboTree:function(){
  		var treeLoader = new Ext.tree.TreeLoader({
//  			                    listeners:{'load':{fn:function(){this.tree.root.firstChild.select()},scope:this,single:true}},
								url : Ext.getDom("root").value + dolphin.resource.Url.busTypeWholeTreeURL
							});
		
	    return treeLoader;
  	}
  };
}();



