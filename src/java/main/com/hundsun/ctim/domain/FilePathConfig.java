package com.hundsun.ctim.domain;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

/**
 * 系统上传、访问文件路径配置参数对象
 * 
 * @author: chenzy
 * @since: 2010-10-19 上午09:55:16
 * @history:
 ************************************************ 
 * @file: FilePathConfig.java
 * @Copyright: 2010 HundSun Electronics Co.,Ltd. All right reserved.
 *********************************************** 
 */
public class FilePathConfig {
	protected static final Log log = LogFactory.getLog(FilePathConfig.class);
	private static FilePathConfig instance = null;

	//文件夹图标图片
	private String fileImageUrl;
	// 文件上传根路径
	private String fileUploadRootPath;
	// 上传文件web访问地址
	private String fileUploadWebUrl;
	// 平台门户发布的URL地址
	private String webUrl;
	// 默认图片路径
	private String defaultImageUrl;
	// 本机图片地址
	private String imageService;
	// 服务图片地址
	private String imageServiceLocal;
	// 清除企业参数请求地址
	private String clearCompParamsCacheUrl;
	// 企业人员报表
	private String companyPeopleExcelRootPath;
	private String companyPeopleExcelPath;
	
	// 证件识别URL与证件图片存放绝对路径
	private String ocrUrl;
	private String ocrPicPath;
	
	//压缩文件字符串
	private String endString;
	
	//压缩宽
	private String cutWidth;
	//压缩高
	private String cutHeight;
	
	

	public String getCutWidth() {
		return cutWidth;
	}

	public void setCutWidth(String cutWidth) {
		this.cutWidth = cutWidth;
	}

	public String getCutHeight() {
		return cutHeight;
	}

	public void setCutHeight(String cutHeight) {
		this.cutHeight = cutHeight;
	}

	private FilePathConfig() {
	}

	public static synchronized FilePathConfig getInstance() {
		if (null == FilePathConfig.instance) {
			String configPathString = "/baseConfig/filePathConfigContext.xml";
			log.debug("/*****************************/\n\tBaseConfigObj getInstance():\n\tconfigPathString:"
							+ configPathString);
			FilePathConfig pathConfig = new FilePathConfig();
			java.net.URL url = pathConfig.getClass().getResource(
					configPathString);
			Resource res = new UrlResource(url);
			XmlBeanFactory factory = new XmlBeanFactory(res);
			FilePathConfig.instance = (FilePathConfig) factory
					.getBean("filePathConfig");
			return FilePathConfig.instance;
		}
		return FilePathConfig.instance;
	}

	/**
	 * 得到公司文件存放的一级目录
	 * 
	 * @param companyId
	 * @return
	 * @create 2010-10-19 上午10:30:36 chenzy
	 * @history
	 */
	public String getCompanyBaseDir(Long companyId) {
		return "C" + companyId.toString() + File.separator;
	}


	/**
	 * 传入文件路径，返回文件的全路径
	 * 
	 * @param path
	 *            文件路径
	 * @return
	 * @create 2010-10-20 上午09:23:56 chenzy
	 * @history
	 */
	public String getFileFullPath(String path) {
		String pathString = path;
		pathString = pathString.replace("/", java.io.File.separator);
		pathString = pathString.replace("\\", java.io.File.separator);
		if (null == pathString)
			pathString = "";
		String realPath = fileUploadRootPath.replace("\\",
				java.io.File.separator).replace("/", java.io.File.separator);
		if (pathString.startsWith(realPath)) {// 如果传入的是绝对路径,就直接返回
			return path;
		}
		if (!realPath.endsWith(File.separator))
			realPath = realPath + File.separator;
		if (pathString.startsWith(File.separator))
			pathString = pathString.substring(1);
		realPath = realPath + pathString;
		log.debug("realPath：" + realPath);
		return realPath;
	}

	/**
	 * 传入数据库中这个在存储的文件地址，返回文件的网页全地址
	 * 
	 * @return
	 * @create Oct 11, 2008 11:08:03 AM yuancong
	 * @history （Oct 11, 2008 11:08:03 AM yuancong ）
	 */
	public String getFileWebUrl(String path) {
		if (null == path)
			path = "";
		if (path.startsWith("http://") || path.startsWith("ftp://")
				|| path.startsWith("https://")) {
			return path;
		}
		String fileWebUrl = this.getFileUploadWebUrl();// uploadFileBaseUrl;
		if (!fileWebUrl.endsWith("/"))
			fileWebUrl = fileWebUrl + "/";
		if (path.startsWith("/"))
			fileWebUrl = fileWebUrl + path.substring(1);
		else
			fileWebUrl = fileWebUrl + path;
		return fileWebUrl.replace('\\', '/');
	}
	/**
	 * 获取压缩图片url
	 * @param path
	 * @return 
	* @create  2011-3-8 下午04:58:41 shenj
	* @history
	 */
	public String getCutFileWebUrl(String path) {
		if (null == path)
			path = "";
		if (path.startsWith("http://") || path.startsWith("ftp://")
				|| path.startsWith("https://")) {
			return path;
		}
		String fileWebUrl = this.getFileUploadWebUrl();// uploadFileBaseUrl;
		if (!fileWebUrl.endsWith("/"))
			fileWebUrl = fileWebUrl + "/";
		if (path.startsWith("/"))
			fileWebUrl = fileWebUrl + path.substring(1);
		else
			fileWebUrl = fileWebUrl + path;
		
		//无后缀文件名
		String clearName = fileWebUrl.replaceAll("[.][^.]+$", "");
		//后缀名
		String endStr=fileWebUrl.substring(fileWebUrl.lastIndexOf("."));
		
		String returnName=clearName+this.getEndString()+endStr;
		return returnName.replace('\\', '/');
	}
	
	public String getFileUploadRootPath() {
		return fileUploadRootPath;
	}

	public void setFileUploadRootPath(String fileUploadRootPath) {
		this.fileUploadRootPath = fileUploadRootPath;
	}

	public String getFileUploadWebUrl() {
		return fileUploadWebUrl;
	}

	public void setFileUploadWebUrl(String fileUploadWebUrl) {
		this.fileUploadWebUrl = fileUploadWebUrl;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getDefaultImageUrl() {
		return defaultImageUrl;
	}

	public void setDefaultImageUrl(String defaultImageUrl) {
		this.defaultImageUrl = defaultImageUrl;
	}
	
	public String getCompanyPeopleExcelRootPath() {
		return companyPeopleExcelRootPath;
	}

	public void setCompanyPeopleExcelRootPath(String companyPeopleExcelRootPath) {
		this.companyPeopleExcelRootPath = companyPeopleExcelRootPath;
	}

	public String getCompanyPeopleExcelPath() {
		return companyPeopleExcelPath;
	}

	public void setCompanyPeopleExcelPath(String companyPeopleExcelPath) {
		this.companyPeopleExcelPath = companyPeopleExcelPath;
	}

	public String getClearCompParamsCacheUrl() {
		return clearCompParamsCacheUrl;
	}

	public void setClearCompParamsCacheUrl(String clearCompParamsCacheUrl) {
		this.clearCompParamsCacheUrl = clearCompParamsCacheUrl;
	}


	public String getImageService() {
		return imageService;
	}

	public void setImageService(String imageService) {
		this.imageService = imageService;
	}

	public String getEndString() {
		return endString;
	}

	public void setEndString(String endString) {
		this.endString = endString;
	}

	public String getImageServiceLocal() {
		return imageServiceLocal;
	}

	public void setImageServiceLocal(String imageServiceLocal) {
		this.imageServiceLocal = imageServiceLocal;
	}
	public String getFileImageUrl() {
		return fileImageUrl;
	}

	public void setFileImageUrl(String fileImageUrl) {
		this.fileImageUrl = fileImageUrl;
	}
	
	public String getOcrUrl() {
		return ocrUrl;
	}

	public void setOcrUrl(String ocrUrl) {
		this.ocrUrl = ocrUrl;
	}

	public String getOcrPicPath() {
		return ocrPicPath;
	}

	public void setOcrPicPath(String ocrPicPath) {
		this.ocrPicPath = ocrPicPath;
	}
}
