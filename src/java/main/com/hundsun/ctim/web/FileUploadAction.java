package com.hundsun.ctim.web;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.core.utils.DateUtils;
import com.core.utils.FileTool;
import com.core.utils.JsonUtils;
import com.core.utils.StringUtils;
import com.core.utils.WebUtils;
import com.core.web.StrutsAction;
import com.dolphin.domain.RemoteUser;
import com.dolphin.domain.User;
import com.hundsun.ctim.domain.FilePathConfig;
import com.hundsun.ctim.domain.file.AnnexFile;
import com.hundsun.ctim.service.FileService;
import com.hundsun.ctim.service.file.AnnexFileServiceImp;

public class FileUploadAction extends StrutsAction{
	
	private static final long serialVersionUID = 1L;
	
	/** 上传照片的大小限制 单位KB */
	private static int LIMIT_SIZE_PICTURE = 512;
	
	/** 上传文件的大小限制 单位MB */
	private static int LIMIT_SIZE_FILE = 5;
	@Autowired
	private FileService fileService;
	@Autowired
	private AnnexFileServiceImp annexFileService;
	
	private File file;
	private String fileContentType;
	private String fileFileName;
	private String fileSuffix;

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public void setFileFileName(String fileName) {
		this.fileFileName = fileName;
		if(!StringUtils.isBlank(fileName)) {
			int dot = fileName.lastIndexOf('.'); 
			if ((dot >-1) && (dot < (fileName.length() - 1))) { 
				fileSuffix = fileName.substring(dot + 1); 
			} 
		}
	}
	
	public void setFile(File fileFacePic) {
		this.file = fileFacePic;
	}
	
	
	/**
	 * 上传图片
	 * @throws Exception
	 */
	public void uploadPicture() throws Exception {
		
		Date now = DateUtils.getCurrentDate();
		String pictureType = this.getRequest().getParameter("pictureType");
		String pictureName = this.getRequest().getParameter("pictureName");
		
		if(StringUtils.isBlank(pictureType)) {
			pictureType = "notype";
		}
		
		if(StringUtils.isBlank(pictureName)) {
			pictureName = DateUtils.toDateString(now, "yyyyMMddHHmmssSSS")+".jpg";
		}
		else {
			pictureName = pictureName + ".jpg";
		}
		
		String relativeURL = null;
		if(file != null){
			if(file.length() > LIMIT_SIZE_PICTURE*1024){
				printHtml(messageFailureWrap("所上传的图片太大，图片大小不要超过"+LIMIT_SIZE_PICTURE+"K！"));
				return;
			}
			String fileRoot = FilePathConfig.getInstance().getFileUploadRootPath();
			String filePath = "picture\\"+pictureType+"\\"+DateUtils.toDateString(now, "yyyyMM")+"\\";
			FileTool.saveFile(file, fileRoot+filePath, pictureName);
			
			relativeURL = (filePath + pictureName).replace("\\","/");
		}
		else {
			
			printHtml(messageFailureWrap("图片上传失败！"));
			return;
		}
		
		String fullURL = fileService.getFullURL(relativeURL, WebUtils.getIpAddrByRequest(this.getRequest()));
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("relativeURL", relativeURL);
		map.put("fullURL", fullURL);
		
		printHtml(messageSuccuseWrapObj(JsonUtils.bean2JsonArray(map)));
	}
	
	/**
	 * 上传文件
	 * @throws Exception
	 */
	public void uploadFile() throws Exception {
		
		Date now = DateUtils.getCurrentDate();
		User user = RemoteUser.get();
		
		String objectType = this.getRequest().getParameter("objectType");
		String objectId = this.getRequest().getParameter("objectId");
		String fileName = fileFileName;
		AnnexFile annexFile = bindEntity(AnnexFile.class);
		
		if(StringUtils.isBlank(objectType)) {
			objectType = "notype";
		}
		
		if(StringUtils.isBlank(fileName)) {
			fileName = DateUtils.toDateString(now, "yyyyMMddHHmmssSSS")+"."+fileSuffix;
		}
		else {
			fileName = fileName.replace("."+fileSuffix, "_"+DateUtils.toDateString(now, "yyyyMMddHHmmssSSS")+"."+fileSuffix);
		}
		String relativeURL = null;
		if(file != null){
			if(file.length() > LIMIT_SIZE_FILE*1024*1024){
				printHtml(messageFailureWrap("所上传的文件太大，文件大小不要超过"+LIMIT_SIZE_FILE+"M！"));
				return;
			}
			String fileRoot = FilePathConfig.getInstance().getFileUploadRootPath();
			String filePath = "file\\"+objectType+"\\"+objectId+"\\";
			
			File file1 = new File(fileRoot+filePath+"/" + fileName);
			
			FileTool.saveFile(file, fileRoot+filePath, fileName);
			
			relativeURL = (filePath + fileName).replace("\\","/");
			
			String strSize = null;
			double size = Math.round((file.length()/1024)*100)/100.0;
			if(size < 1024) {
				strSize = size+"K";
			}else {
				size = Math.round((file.length()/(1024*1024))*100)/100.0;
				strSize = size+"M";
			}
			annexFile.setFileSize(strSize);
			annexFile.setFileName(fileFileName);
			annexFile.setFileSuffix(fileSuffix);
			annexFile.setPath(relativeURL);
			annexFile.setCreator(user.getUserName());
			annexFile.setCreatorId(Long.valueOf(user.getId()));
			annexFile.setModifier(user.getUserName());
			annexFile.setModifierId(Long.valueOf(user.getId()));
			annexFile.setGmtCreate(now);
			annexFile.setGmtModify(now);
			
			annexFileService.insert(annexFile);
		}
		else {
			
			printHtml(messageFailureWrap("文件上传失败！"));
			return;
		}
		
		String fullURL = fileService.getFullURL(relativeURL, WebUtils.getIpAddrByRequest(this.getRequest()));
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("relativeURL", relativeURL);
		map.put("fullURL", fullURL);
		
		printHtml(messageSuccuseWrapObj(JsonUtils.bean2JsonArray(map)));
	}
	
}