package com.dolphin.sys;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.core.utils.StringTool;

/**
 * 系统运行时的基本常量配置对象，提供系统的基础常量数据
 * 
 * @author: yuancong
 * @since: Oct 11, 2008 11:08:15 AM
 * @history: @***********************************************
 * @file: BaseConfigObj.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd.
 * @All right reserved. @**********************************************
 */
public class BaseConfigObj implements Serializable {
	private String uploadFileBasePath;// 文件上传的物理BASE地址
	private String uploadFileBaseUrl;// 文件访问时的网页BASE地址，这个地址就是访问上述物理BASE目录的地址
	private String webUrl;
	private String indexHomePath;
	private Map<String, String> publicStatus;// 帮助常见问答help_faq 表==>题目状态 status
												// 新闻文章发布状态共用
	// private Map<String, String> sysDepartmentDeptType;
	private Map<String, String> sex;
	private static BaseConfigObj instance = null;
	protected static final Log log = LogFactory.getLog(BaseConfigObj.class);

	private BaseConfigObj() {
	}

	public static synchronized BaseConfigObj getInstance() {
		if (null == BaseConfigObj.instance) {
			String configPathString = "/baseConfig/baseConfigContext.xml";
			log.debug("/*****************************/\n\tBaseConfigObj getInstance():\n\tconfigPathString:"
							+ configPathString);
			BaseConfigObj baseConfigObj = new BaseConfigObj();
			java.net.URL url = baseConfigObj.getClass().getResource(
					configPathString);
			Resource res = new UrlResource(url);
			XmlBeanFactory factory = new XmlBeanFactory(res);
			BaseConfigObj.instance = (BaseConfigObj) factory
					.getBean("baseConfigObj");
			log.debug("baseConfigObj.UploadFileBasePath: "
					+ instance.getUploadFileBasePath()
					+ "; \n baseConfigObj.UploadFileBaseUrl:"
					+ instance.getUploadFileBaseUrl());
			return BaseConfigObj.instance;
		}
		log
				.debug("\n\nbaseConfigObj bean has in this beanFactory. now it is get from this beanFactory.");
		log.debug("\n\t\tbaseConfigObj.UploadFileBasePath: "
				+ instance.getUploadFileBasePath()
				+ "; \n\t\t baseConfigObj.UploadFileBaseUrl:"
				+ instance.getUploadFileBaseUrl());
		return BaseConfigObj.instance;
	}

	/**
	 * 传入数据库中这个在存储的文件地址，返回文件的存储物理全地址
	 * 
	 * @return
	 * @create Oct 11, 2008 11:07:49 AM yuancong
	 * @history （Oct 11, 2008 11:07:49 AM yuancong ） /var/
	 */
	public String getFileLocalPath(String path) {
		String pathString = path;
		pathString = pathString.replace("/", java.io.File.separator);
		pathString = pathString.replace("\\", java.io.File.separator);
		if (null == pathString)
			pathString = "";
		String realPath = uploadFileBasePath.replace("\\",
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
		String fileWebUrl = this.getUploadFileBaseUrl();// uploadFileBaseUrl;
		if (!fileWebUrl.endsWith("/"))
			fileWebUrl = fileWebUrl + "/";
		if (path.startsWith("/"))
			fileWebUrl = fileWebUrl + path.substring(1);
		else
			fileWebUrl = fileWebUrl + path;
		return fileWebUrl.replace('\\', '/');
	}

	public String getUploadFileBasePath() {
		if (null == uploadFileBasePath) {
			return null;
		}
		return uploadFileBasePath.replace("\\", java.io.File.separator)
				.replace("/", java.io.File.separator);
	}

	public void setUploadFileBasePath(String uploadFileBasePath) {
		this.uploadFileBasePath = uploadFileBasePath;
	}

	public String getUploadFileBaseUrl() {
		return StringTool.unite2UrlStr(this.getWebUrl(), uploadFileBaseUrl);
	}

	public void setUploadFileBaseUrl(String uploadFileBaseUrl) {
		this.uploadFileBaseUrl = uploadFileBaseUrl;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getIndexHomePath() {
		return indexHomePath;
	}

	public void setIndexHomePath(String indexHomePath) {
		this.indexHomePath = indexHomePath;
	}

	/**
	 * 传入数据库中这个在存储的文件地址，返回文件的存储物理全地址
	 * 
	 * @return
	 * @create Oct 11, 2008 11:07:49 AM yuancong
	 * @history （Oct 11, 2008 11:07:49 AM yuancong ） /var/
	 */
	public String getIndexHomePath(String path) {
		String pathString = path;
		pathString = pathString.replace("/", java.io.File.separator);
		pathString = pathString.replace("\\", java.io.File.separator);
		if (null == pathString)
			pathString = "";
		String realPath = indexHomePath.replace("\\", java.io.File.separator)
				.replace("/", java.io.File.separator);
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

	// public Map<String, String> getSysDepartmentDeptType() {
	// return sysDepartmentDeptType;
	// }
	//
	// public void setSysDepartmentDeptType(
	// Map<String, String> sysDepartmentDeptType) {
	// this.sysDepartmentDeptType = sysDepartmentDeptType;
	// }

	public Map<String, String> getSex() {
		return sex;
	}

	public void setSex(Map<String, String> sex) {
		this.sex = sex;
	}

	public Map<String, String> getPublicStatus() {
		return publicStatus;
	}

	public void setPublicStatus(Map<String, String> publicStatus) {
		this.publicStatus = publicStatus;
	}

	/**
	 * main start
	 */

	/**
	 * main end
	 */

}
