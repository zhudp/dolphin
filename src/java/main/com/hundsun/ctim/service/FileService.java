package com.hundsun.ctim.service;

import java.io.File;

import org.springframework.stereotype.Service;

/**
 * 文件上传、访问服务类
 * @author: chenzy
 * @since: 2010-10-19  上午11:08:33
 * @history:
 ************************************************
 * @file: FileService.java
 * @Copyright: 2010 HundSun Electronics Co.,Ltd.
 * All right reserved.
 ***********************************************
 */
@Service
public interface FileService {
	
	/**
	 * 获取文件夹图片路径
	 * @return 
	 * @create  2010-12-30 下午02:11:27 shenj
	 * @history
	 */
	public String getFileImageUrl();
	
	/**
	 * 获取缺省图片路径
	 * @return 
	 * @create  2010-10-21 上午09:01:10 shenj
	 * @history
	 */
	public String getDefaultImageUrl();
	
	
	/**
	 * 上传图片
	 * @param file 上传文件
	 * @param contentType 上传文件类型
	 * @param fileName 上传文件名
	 * @param companyId 公司id
	 * @param path 上传路径
	 * @return 
	 * @create  2010-12-30 下午02:16:26 shenj
	 * @history
	 */
	public String uploadImage(File file, String contentType,
			String fileName, Long companyId,String path,String parentId);
	

	/**
	 * 获取文件URL
	 * @param relativeUrl 相对路径
	 * @param ip 客户端IP
	 * @return
	 */
	public String getFullURL(String relativeUrl, String ip);
	
	/**
	 * 获得文件web的root路径
	 * @param fileName(数据库存的文件名)
	 * @return 
	 * @create  2010-10-22 下午04:51:13 shenj
	 * @history
	 */
	public String getImageUrl(String fileName) ;
	
	public String getImageCut(String fileName);
	
	/**
	 * 显示相对路径
	 * @param fileName
	 * @return 
	* @create  2011-2-21 下午04:04:43 shenj
	* @history
	 */
	public String getImageUrlField(String fileName);
	
	/**
	 * 获得文件path的全路径
	 * @param fileName(数据库存的文件名)
	 * @return 
	 * @create  2010-10-22 下午04:51:13 shenj
	 * @history
	 */
	public String getImagePath(String fileName) ;
	
	/**
	 * 获取图片文件全路径
	 * @param path
	 * @return
	 * @author: jinrey
	 * @since: 2012-10-28 下午06:01:40
	 */
	public String getFileFullPath(String path);
}
