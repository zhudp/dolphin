package com.hundsun.ctim.service.file;

import java.io.File;
import org.springframework.stereotype.Service;

import com.core.utils.UploadUtils;
import com.hundsun.ctim.domain.FilePathConfig;
import com.hundsun.ctim.service.FileService;

@Service
public class FileServiceImp implements FileService {
	
	private static final String LOCAL_IP_REGEX = "(127[.]0[.]0[.]1)|" + "(localhost)|" + 
		"(10[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3})|" + "(172[.]((1[6-9])|(2\\d)|(3[01]))[.]\\d{1,3}[.]\\d{1,3})|" +
		"(192[.]168[.]\\d{1,3}[.]\\d{1,3})";

	public String getFileImageUrl() {
		FilePathConfig filePathConfig = FilePathConfig.getInstance();
		return filePathConfig.getFileImageUrl();
	}

	public String getDefaultImageUrl() {
		FilePathConfig filePathConfig = FilePathConfig.getInstance();
		return filePathConfig.getDefaultImageUrl();
	}
	
	public String getImageServiceUrl() {
		FilePathConfig filePathConfig = FilePathConfig.getInstance();
		return filePathConfig.getImageService();
	}
	
	public String getImageServiceUrlLocal() {
		FilePathConfig filePathConfig = FilePathConfig.getInstance();
		return filePathConfig.getImageServiceLocal();
	}

	public String uploadImage(File file, String contentType, String fileName,
			Long companyId, String path, String parentId) {
		/** 保存文件到绝对路径 **/
		String fullpath = this.getFileFullPath(path, companyId.toString());
		UploadUtils.copyFile(file, fullpath, fileName);
		return null;
	}

	/**
	 * 获取文件相对路径
	 * 
	 * @param path 文件夹相对路径
	 * @param fileName 上传文件名
	 * @param companyId 公司id
	 * @return
	 * @create 2010-12-30 下午02:26:27 shenj
	 * @history
	 */
	private String getFilePath(String path, String companyId) {
		StringBuffer sb = new StringBuffer();
		return sb.append(companyId).append("\\").append(path).append("\\")
				.toString();

	}
	/**
	 * 取得所需要的照片
	 * ip : 客户端IP地址
	 */
	public String getFullURL(String relativePic,String ip){
		String picUrl = null;
		String absolutePic = null;
		if(ip.matches(LOCAL_IP_REGEX)){
			absolutePic = getImageServiceUrlLocal();
			picUrl = absolutePic + "/" + relativePic;
		}else{
			absolutePic = getImageServiceUrl();
			picUrl = absolutePic + "/" +relativePic;
		}
		return picUrl;
	}
	
	/**
	 * 获取文件存放路径
	 * 
	 * @param path
	 * @param companyId
	 * @return
	 * @create 2010-12-30 下午03:32:15 shenj
	 * @history
	 */
	private String getFileFullPath(String path, String companyId) {
		FilePathConfig v = FilePathConfig.getInstance();
		return v.getFileFullPath(this.getFilePath(path, companyId));
	}

	public String getImageUrl(String fileName) {
		FilePathConfig v = FilePathConfig.getInstance();
		return v.getFileWebUrl(fileName);
	}

	public String getImageCut(String fileName) {
		// 如果为非法的文件路径格式
		String fileAbsName;
		if (fileName.lastIndexOf("/") != -1) {
			fileAbsName = fileName.substring(fileName.lastIndexOf("/"),
					fileName.length());
		} else {
			fileAbsName = fileName.substring(fileName.lastIndexOf("\\"),
					fileName.length());
		}
		if (!fileAbsName.contains(".")) {
			return this.getDefaultImageUrl();
		}
		FilePathConfig v = FilePathConfig.getInstance();
		return v.getCutFileWebUrl(fileName);
	}

	public String getImagePath(String fileName) {
		FilePathConfig v = FilePathConfig.getInstance();
		return v.getFileUploadRootPath() + fileName;
	}

	public String getImageUrlField(String fileName) {
		return fileName.substring(fileName.indexOf("/") + 1, fileName.length());
	}
	
	/**
	 * 获取图片文件全路径
	 * @param path
	 * @return
	 * @author: jinrey
	 * @since: 2012-10-28 下午06:01:10
	 */
	public String getFileFullPath(String path) {
		FilePathConfig v = FilePathConfig.getInstance();
		return v.getFileFullPath(path);
	}

}
