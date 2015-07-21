/* Grid默认每页记录数 */
var PAGE_SIZE = 20;

/* 前台常量约定 注意：要与后台的baseConfigContext.xml的常量约定同步(以后台为准) */
var appConstants = {
	// 原系统管理模块的约定(轻易勿动)
	DATA_DICT_FLOOR : '4',
	DATA_ROLE_ID : '2',
	DATA_DICT_PARENTID : '0',
	DATA_DICT_STATUE_FIRST : '1',
	DATA_DICT_STATUE_SECOND : '2'

	,
	TABPAGE_MAX_SUM : 10 // 后台能打开tab页的最大数
	,
	CLOB_MAXLENGTH : 30000 // clob字段的最大长度限制
	,
	UPLOAD_FILE_MAXSIZE : 1024 * 1024 // 单个文件上传限制最大值
	,
	DEFAULT_SYSTEM_COUNT : 0 // 默认系统管理员账号ID
	,
	DEFAULT_IMG_PATH : "../../resources/hs/images/no_pic.gif" // 默认无图片时的显示图
	,
	ISVALID_STRING : '您填写的数据有误,请查证!'

	// 数据字典根节点ID
	,
	// TODO: 需要确认是否正确 jinnie 2010-09-26 edit
	ARTICLE_TYPE_ROOTNODE : '05' // 新闻类型
//	ARTICLE_TYPE_ROOTNODE : '6' 
	,
	AREA_ROOTNODE : '7' // 地理区域
	,
	NOT_AUDIT : '0' // 审核状态 待审核
	,
	AUDIT_PASS : '1' // 审核状态 审核通过
	,
	AUDIT_NO_PASS : '2' // 审核状态 审核不通过
	,
	// 2010-09-01 JINNIE EDIT 0、1 值统一为该两个常量
	DEFAULTVALUE_ZERO: '0' // 0
	,
	DEFAULTVALUE_ONE: '1' // 1
	,
	DEFAULTVALUE_TWO: '2' // 2
	,
	DEFAULTVALUE_THREE: '3' // 3
}

/* Label value 列表显示值展示 */
hs.LabelValue = function() {

	var DEFAULT_FIELDS = ['id', 'name'];
	var ARRAY_PRE = ['', '- 请选择 -'];
	function store(array) {
		var _array = new Array();
		_array[0] = ARRAY_PRE;
		_array = _array.concat(array);

		return new Ext.data.SimpleStore({
					fields : DEFAULT_FIELDS,
					data : _array
				});
	}
	function storeNoSelection(array) {
		var _array = new Array();
		_array = _array.concat(array);

		return new Ext.data.SimpleStore({
					fields : DEFAULT_FIELDS,
					data : _array
				});
	}
	function render(value, array) {
		for (var i = 0; i < array.length; i++) {
			var o = array[i];
			if (value == o[0])
				return o[1];
		}
		return "";
	}

	/* 性别 */
	var SEX_ARRAY = [[appConstants.DEFAULTVALUE_ONE, '男'], [appConstants.DEFAULTVALUE_ZERO, '女']];

	/* 启用、不启用 */
	var APPLY_ARRAY = [[appConstants.DEFAULTVALUE_ONE, '启用'], [appConstants.DEFAULTVALUE_TWO, '不启用']];

	/* 用户状态 正常、失效 */
	var STATUS_ARRAY = [[appConstants.DEFAULTVALUE_ONE, '正常'], [appConstants.DEFAULTVALUE_ZERO, '失效']];

	/* 证件类型 */
	this.CARD_S = 0;
	this.CARD_H = 1;
	this.CARD_J = 2;
	this.CARD_M = 3;
	this.CARD_Q = 9;
	var CARD_ARRAY = [[CARD_M, '身份证'], [CARD_S, '学生证'], [CARD_H, '护照'],
			[CARD_J, '驾驶证'], [CARD_Q, '其他']];

	var AUDIT_ARRAY = [['00', '申请'], ['11', '通过'], ['10', '不通过']];

	var FLAG_ARRAY = [[appConstants.DEFAULTVALUE_ONE, '只读'], [appConstants.DEFAULTVALUE_TWO, '可修改不可删除'],
			[appConstants.DEFAULTVALUE_THREE, '可修改可删除']];

	/* 文章状态 */
//	var ARTICLE_ARRAY = [[appConstants.DEFAULTVALUE_ZERO, '待发布'],
//			[appConstants.DEFAULTVALUE_ONE, '已发布']];

	/* 数据是否已删除 */
	this.DELETE_NO = 0;
	this.DELETE_YES = 1;
	var DELETE_ARRAY = [[appConstants.DEFAULTVALUE_ONE, '已删除'], [appConstants.DEFAULTVALUE_ZERO, '未删除']];

	/* 默认是否值 */
	var DEFAULT_WHETHER = [[appConstants.DEFAULTVALUE_ONE, '是'],
			[appConstants.DEFAULTVALUE_ZERO, '否']];
	return {
		FORMATE_UTILDATE : function() {
			return function(v) {
				if (v != null && v != '')
					v = new Date(v["time"]).format('Y-m-d H:i:s');
				return v;
			};
		},
		FORMATE_UTILDATE_NOTIME : function() {
			return function(v) {
				if (v != null && v != '')
					v = new Date(v["time"]).format('Y-m-d');
				return v;
			};
		}
		/* STORE */
		,
		APPLY_STORE : function() {
			return store(APPLY_ARRAY);
		},
		APPLY_NOSEL_STORE : function() {
			return storeNoSelection(APPLY_ARRAY);
		},
		FLAG_STORE : function() {
			return store(FLAG_ARRAY);
		},
		BOOL_STORE:function(){
			return store(DEFAULT_WHETHER);
		}
		/* RENDER */
		,
		
		APPLY_RENDER : function() {
			return function(v) {
				var result = render(v, APPLY_ARRAY);
				if (v == appConstants.DEFAULTVALUE_ONE)
					return '<span style="color:green;">' + result + '</span>';
				else
					return '<span style="color:red;">' + result + '</span>';
			};
		}
		,
		CARD_TYPE_RENDER : function() {
			return function(v) {
				return render(v, CARD_ARRAY);
			};
		}

		,
		AUDIT_RENDER : function() {
			return function(v) {
				return render(v, AUDIT_ARRAY);
			};
		},
		DELETE_STATUS_RENDER : function() {
			return function(v) {
				var result = render(v, DELETE_ARRAY);
				if (v == appConstants.DEFAULTVALUE_ZERO)
					return '<span style="color:green;">' + result + '</span>';
				else
					return '<span style="color:red;">' + result + '</span>';
			};
		}
		,
		DEFAULT_WHETHER_RENDER : function() {
			return function(v) {
				var result = render(v, DEFAULT_WHETHER);
				if (v == appConstants.DEFAULTVALUE_ONE)
					return '<span style="color:green;">' + result + '</span>';
				else
					return '<span style="color:red;">' + result + '</span>';
			};
		}
		,
		// int日期 转换为 日期字符串
		INTTIME_TO_DATASTR_RENDER: function(v){
			if(v == null || v.length<8){
				return "";
			}
			return dateStr = v.substring(0,4) + "-" + v.substring(4,6) + "-" + v.substring(6);
		}

	}
}();

hs.strutsUrl = {
	DATADICT_COMMON_URL : "/dolphin/datadict!buildComboSelect.do",// 根据类型从后台组装Combox下拉列表URL
	DATADICT_COMBOX_TREE_STORE : "/dolphin/datadict!datadictTreeList.do",// 根据节点获取数据字典树的URL(取到的树包含了父节点及其子节点的所有值)
	DATA_DICTIONARY : "/mims/dataDictionary!comboxJT.do",
	DATA_REGION : "/mims/dataDictionary!comboxRegion.do",
	DATA_DIC_LOCAL : "/mims/dataDictionary!combox.do"
};

var DataDic = {

};


function getTextFromStore(p_store, p_id) {
	if (p_store) {
		p_record = p_store.getById(p_id);
		if (p_record) {
			return p_record.get("text");
		}
	}
	return "";
}
