Ext.namespace("Commons.card");
/**
 * 通用编辑窗
 * @param {} config
 * @return {}
 */
Commons.card.Win = function(config) {
	this.config = config;
	if(Ext.isObject(config)){
		this.width = config.width;
		this.height = config.height;
		this.url = config.url;
		this.idName = config.idName;
		this.callback = config.callback;
		this.title = config.title;
		this.callbackClose = config.callbackClose;
		if(!Ext.isObject(this.url)){
			this.url={};
		};
	}
};

/**
 * 通用编辑窗口定义
 */
Commons.card.Win.prototype = {
	
	getCardForm: function(){
		if(!this.cardForm){
			var cardForm = new Ext.form.FormPanel({
				frame: true,
				labelAlign: 'right',
				region: "center",
				border: false,
				autoScroll: true,
	            defaults : {
					labelWidth : 70
				},
				items: this.createFormItem()
		     });
			this.cardForm = cardForm;
		}
		return this.cardForm;
	}
	/**
	 * 子类必须通过重写该方法来布局自有表单以及布局信息
	 * 创建表单元素，包含布局信息
	 * @return [field] 可以用于FormPanel的items配置项
	 */
	,createFormItem : function(){
		return [];
	}
	/**
	 * 顶层窗口
	 */
	,getWin : function() {
		if (!this.win) {
			var width =Ext.isNumber(this.config.width)? this.config.width : 680;
			var height =Ext.isNumber(this.config.height)? this.config.height : 500;
			var win = new Ext.Window({
				frame : true,
				width : width,
				height: height,
				closeAction : 'close',
				layout: 'border',
				items:this.getWinItems(),
		        tbar:this.getTbar()
			});
			this.win = win;
		}
		return this.win;
	}
	/**
	 * 窗口元素
	 */
	,getWinItems : function(){
		return [this.getCardForm()];
	}
	/**
	 * 获取功能按钮集合,该函数返回结果可以直接用于window的tbar配置项
	 * @ return [{}]
	 */
	,getTbar : function(){
		var _this = this;
		function eachItem(item,index,length) {
		    if (item.items && item.items.getCount() > 0) {
		       item.items.each(eachItem, this);
		    }
		    else if(!item.autoFill){
		    	item.setDisabled(!item.disabled);
		    }
		}
		var tbarArray =[{
			text : "编辑",
			iconCls : 'hs-button-edit',
			scope : this,
			enableToggle:true,
			handler : function() {
				_this.getCardForm().items.each(eachItem, this);//
				var saveBtn = _this.getWin().getTopToolbar().get(1);
				saveBtn.setDisabled(!saveBtn.disabled);
			}
		},{
			text : "保存",
			iconCls : 'icon_save2',
			scope : this,
			disabled:true,
			handler :function(){ this.submitFn();}
		}
        ];
		// 扩展函数执行
		tbarArray = this.expandTbar(tbarArray);
		return tbarArray;
	}
	/**
	 * 表单提交函数，调用配置项中的回调函数
	 */
	,submitFn : function(){
		var _this = this;
		var savUrl =  _this.url.save;//url 配置化
		
		hs.FormHelper.submit(savUrl,
				function(form, action) {
					hs.MessageHelper.succuss({
						callback : function() {
							if( _this.callback) {
								_this.callback(_this.mainPanel);
							}
							if(_this.callbackClose) {
								_this.getWin().close();
							}
						}
					});
				},null, _this.getCardForm().getForm());
	}
	/**
	 * 注入回调
	 */
	,setCallback : function(fn){
		this.callback = fn;
	}
	/**
	 * 注入父容器 
	 */
	,setMainPanel: function(panel){
		this.mainPanel = panel;
	}
	/**
	 * 操作按钮集合扩展方法，子类可以通过重写该方法来修改操作按钮逻辑（添加按钮、删除按钮、编辑按钮）
	 * @param [{}]
	 * @return [{}]
	 */
	,expandTbar : function(tbarArray){
		return tbarArray;
	}
	/**
	 * 显示编辑窗口
	 * @param id 
	 * 
	 */
	,show: function(id){
		this.getWin().show();
		this.getWin().setTitle(this.getWindTitle());
		//载入信息
		if(id) {
			this.id = id;//
			this.load();
		}
		// 新增
		else {
			this.getWin().getTopToolbar().get(0).toggle().handler();
		}
	}
	/**
	 * 表单数据加载参数
	 */
	,getLoadParam : function(_this){
		return {id: _this.id};//默认编号字段名称id，
	}
	/**
	 * 加载数据
	 */
	,load : function() {
		var _this = this;
		var formPanel = _this.getCardForm();
		hs.FormHelper.load(this.url.get,this.getLoadParam(_this), function(form, action) {
			var data = Ext.util.JSON.decode(action.result.data);
			form.clearInvalid();
			form.setValues(data);
			_this.getWin().setTitle(_this.getWindTitle(data));
		}, formPanel.getForm());
	}
	/**
	 * 编辑或加载数据后的窗口标题
	 * @param action.result.data ajax 请求返回数据
	 * @return String
	 */
	,getWindTitle : function(data){
		
		if(data){
			return this.title;
		}else{
			return this.title;
		}
	}
};

