package com.hundsun.ctim;

public class Params {
	// 文章状态
	public static final String ARTICLE_STATUS_ADD = "0";							//待发布
	public static final String ARTICLE_STATUS_PUB = "1";							//已发布
	public static final Integer DEFAULT_SYSTEM_COUNT = 0;							//默认系统管理员账号
	public static final Integer DEFAULT_BROWERSUM = 0;								//新增文章浏览次数
	public static final String DEFAULT_IS_DELETE = "0";								//默认数据未删除
	public static final String DEFAULT_IS_COMMAND = "0";							//默认文章不推荐
	public static final String DEFAULT_IS_TOP = "0";
	public static final String DEFAULTVALUE_YES = "1";								//默认是否值 - 是
	public static final String DEFAULTVALUE_NO = "0";								//默认是否值 - 否
	// 数据字典树的根节点ID
	public static final String ARTICLE_TYPE_ROOTNODE = "05";						//文章类型
	public static final String ARTICLE_TYPE_NEWS = "0501";							//新闻
	public static final String ARTICLE_TYPE_NOTICE = "'050103', '050203'";				//系统公告
	public static final String ARTICLE_TYPE_POLICY = "0503";						//政策法规
	
	public static final String UPLOAD_IMAGE_NEWCLASS = "newspic";					//上传的新闻文件定义名称
	public static final String PAGETEMPLATE_PATH = "zip";							//模板相对路径的后续目录
	public static final String DEFAULT_ADVER_TEMPLATE = "advertise.vm";				//默认的广告Js文件生成模板文件
	public static final String DEFAULT_TARGETFILE_PATH = "advertisement/js/";		//默认的根据系统模板生成文件的相对路径的后续目录
	public static final String DEFAULT_ADVERBLOCK_DEFIMG = "advertisement/base/";	// 广告区块默认广告图片的上传路径
	public static final String DEFAULT_IMG_NAME = "img";							//默认图片格式对应的英文名称
	public static final String DEFAULT_IMG_FILETYPE = ".jpg";						//默认图片格式
	public static final String DEFAULT_LINKURL = "http://";							//默认链接地址的前缀
	public static final String DEFAULT_SPLIT_TARGET = "#";							//默认通过String组装数据的分割标志
	public static final String DEFAULT_IMG_TEXTMARK = "杭州旅游客运平台";				//默认上传图片打的文字水印
	public static final String DEFAULT_IMG_IMGMARKPATH = "NewsClass/base/logo.jpg";	//默认上传图片打的图片水印的路径
	public static final String DEFAULT_IMG_REDUCENAME = "_reduce";					//默认压缩图片文件名的标志名称
	public static final String DEFAULT_IMGNEWS_TEMPLATE = "imgNews.vm";	
	
	public static final String QUARTZ_ROBOT_NAME = "系统自动任务";
	
	
	/** 作废标记 */
	public static final String CHARTER_DELETE_STATUS = "y";
	
	/** 企业黄页模板路径 */
	public static final String COMPANY_TEMPLATE= "../template/sysTemplate/";
	
	/**
	 * 企业新闻类型
	 */
	public static final String COMPANY_NEWS_TYPE_NEW = "1";
	public static final String COMPANY_NEWS_TYPE_NOTE = "2";
	
	/**
	 * 企业人员信息列表
	 */
	public static final String COMPANY_PEOPLE_TEMPLATE = "/WEB-INF/excelTemplate/COMPANY_PEOPLE.xls";
	
	/**
	 * 在线支付相关参数
	 */
	public static final String BANK_NAME_ALI="支付宝";
	
	/**
	 * 企业参数类别名称
	 */
	public static final String BALANCE_PARAMS = "结算相关参数";
	public static final String COMPANY_SHOW_PARAMS = "企业黄页参数";
	public static final String PAY_ONLINE_PARAMS = "网上支付参数";
	
	/******************************* zhili ************************************/
	
	/** 状态0 */
	public static final String STATUS_ZERO = "0";
	/** 状态1 */
	public static final String STATUS_ONE = "1";
	/** 状态2 */
	public static final String STATUS_TWO = "2";
	/** 状态3 */
	public static final String STATUS_THREE = "3";
	
	public static final String BLANK_STRING = "";
	public static final Long BLANK_LONG = 0L;
	
	/**
	 * 住户类型
	 */
	public static final String TENANT_TYPE_COMPANY = "1";
	public static final String TENANT_TYPE_PEOPLE = "2";
	
	/**
	 * 编辑类型
	 */
	public static final String ALERT_TYPE_ADD = "1";
	public static final String ALERT_TYPE_ADD_NAME = "登记";
	public static final String ALERT_TYPE_UPDATE = "2";
	
	/**
	 * 巡查对象类型
	 */
	public static final String INSPECT_OBJECT_TYPE_HOUSE = "1";
	public static final String INSPECT_OBJECT_TYPE_PEOPLE = "2";
	public static final String INSPECT_OBJECT_TYPE_COMPANY = "3";
	
	/**
	 * 身份证状态:1一次未带，2二次未带，3假证，9、已确认
	 */
	public static final String IDCARD_STATUS_NOTCARRY_1 = "1";
	public static final String IDCARD_STATUS_NOTCARRY_2 = "2";
	public static final String IDCARD_STATUS_FAKE = "3";
	public static final String IDCARD_STATUS_CHECKED = "9";
	/**
	 * 回复状态：0未回复；1已回复
	 */
	public static final String CUSTOM_REPLY_STATUS_N = "0";
	public static final String CUSTOM_REPLY_STATUS_Y = "1";
	/**
	 * 发布状态：0未发布；1已发布；2已归档
	 */
	public static final String CUSTOM_PUBLISH_STATUS_N = "0";
	public static final String CUSTOM_PUBLISH_STATUS_Y = "1";
	public static final String CUSTOM_PUBLISH_STATUS_A = "2";
	
	/** 企业人员报表路径 */
	public static final String CO_PEOPLE_REPORT_PATH = "coReportTemp";
	
}
