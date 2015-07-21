package com.core.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;

import com.core.exception.BusinessException;
import com.dolphin.sys.BaseConfigObj;
import com.jspsmart.upload.File;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/*******************************************************************************
 * @author: yanghb
 * @since: 2008-10-27 下午04:49:47
 * @history: ***********************************************
 * @file: UploadUtils.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 ******************************************************************************/
public class UploadUtils {
	private static final int BUFFERLEN = 1024 * 526;

	/**
	 * struts FormFile上传文件时使用
	 * 
	 * @param inputStream
	 *            图片InputStream
	 * @param path
	 *            图片存储路径
	 */
	public static void saveFile(InputStream inputStream, String path) {
		byte[] buffer = new byte[BUFFERLEN];
		int readedLen = 0;
		try {
			OutputStream out = new FileOutputStream(path);
			while ((readedLen = inputStream.read(buffer, 0, BUFFERLEN)) != -1) {
				try {
					out.write(buffer, 0, readedLen);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return;
				}
			}
			out.close();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			return;
		}
	}

	/**
	 * 根据输入流、输出流来拷贝文件
	 * 
	 * @param file
	 *            保存的文件
	 * @param path
	 *            保存的文件路径下
	 * @param fileName
	 *            保存的文件名
	 * @create 2010-10-20 上午10:33:44 shenj
	 * @history
	 */
	public static void copyFile(java.io.File file, String path, String fileName) {
		path = path.replace("/", java.io.File.separator).replace("\\",
				java.io.File.separator);
		new java.io.File(path).mkdirs();
		java.io.File newFile = new java.io.File(path + fileName);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel inC = null;
		FileChannel outC = null;
		try {
			fis = new FileInputStream(file);
			fos = new FileOutputStream(newFile);
			inC = fis.getChannel();
			outC = fos.getChannel();
			int length = BUFFERLEN;
			ByteBuffer b = null;
			while (true) {
				if (inC.position() == inC.size()) {
					return;
				}
				if ((inC.size() - inC.position()) < length) {
					length = (int) (inC.size() - inC.position());
				} else
					length = BUFFERLEN;
				b = ByteBuffer.allocateDirect(length);
				inC.read(b);
				b.flip();
				outC.write(b);
				outC.force(false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				inC.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				outC.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			file = null;
			newFile = null;
		}
	}

	/**
	 * 按BASE目录/模块区/年(全称,如:2008)月(如:01)日/文件名.类型来存文件,自动重命名(并使用参数fileName的后缀名)
	 * 
	 * @param inputStream
	 *            需要存文件的输入流 不为空
	 * @param modulePath
	 *            模块区名 不可为空，以/结尾，前面无分隔符
	 * @param fileName
	 *            原文件名(请至少传入后缀名(带点号,如 .jpg))
	 * @return 相对BaseConfigObj中物理路径的文件路径
	 */
	public static synchronized String saveWebFile(InputStream inputStream,
			String modulePath, String fileName) {
		return saveWebFile(inputStream, modulePath, fileName, true);
	}

	/**
	 * 按BASE目录/模块区/年(全称,如:2008)月(如:01)日/文件名.类型来存文件
	 * 
	 * @param inputStream
	 *            需要存文件的输入流 不为空
	 * @param modulePath
	 *            模块区名 不可为空，以/结尾，前面无分隔符
	 * @param fileName
	 *            原文件名(请至少传入后缀名(带点号,如 .jpg))
	 * @param isReName
	 *            是否重命名,true表示自动重命名(并使用参数fileName的后缀名),false表示使用传入的fileName做名称
	 * @return 相对BaseConfigObj中物理路径的文件路径
	 */
	public static synchronized String saveWebFile(InputStream inputStream,
			String modulePath, String fileName, boolean isReName) {
		return saveWebFile(inputStream, modulePath, fileName, true, true);
	}

	/**
	 * 按BASE目录/模块区/年(全称,如:2008)月(如:01)日/文件名.类型来存文件
	 * 
	 * @param inputStream
	 *            需要存文件的输入流 不为空
	 * @param modulePath
	 *            模块区名 不可为空，以/结尾，前面无分隔符
	 * @param fileName
	 *            原文件名(请至少传入后缀名(带点号,如 .jpg))
	 * @param isReName
	 *            是否重命名,true表示自动重命名(并使用参数fileName的后缀名),false表示使用传入的fileName做名称
	 * @param moreFilePath
	 *            是否使用更深目录,如使用年月日做子目录,默认为true,使用年月日做子目录
	 * @return 相对BaseConfigObj中物理路径的文件路径
	 */
	public static synchronized String saveWebFile(InputStream inputStream,
			String modulePath, String fileName, boolean isReName,
			boolean moreFilePath) {
		try {
			String dataFileName = "";
			// 配置的物理路径
			String basePath = BaseConfigObj.getInstance()
					.getUploadFileBasePath();
			if (inputStream == null || basePath == null) {
				return null;
			}
			basePath = basePath.replace("/", java.io.File.separator);
			basePath = basePath.replace("\\", java.io.File.separator);
			if (!basePath.endsWith(java.io.File.separator))
				basePath = basePath + java.io.File.separator;
			modulePath = modulePath.replace("/", java.io.File.separator);
			modulePath = modulePath.replace("\\", java.io.File.separator);
			if (modulePath.startsWith(java.io.File.separator)) {
				modulePath = modulePath.substring(1);
			}
			dataFileName = basePath + modulePath;
			if (!dataFileName.endsWith(java.io.File.separator)) {
				dataFileName = dataFileName + java.io.File.separator;
			}
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			DateFormat timeFormat = new SimpleDateFormat("HHmmss");
			String dateFolder = dateFormat.format(date);
			String timeFile = isReName ? timeFormat.format(date)
					+ RandomStringUtils.randomAlphabetic(4) + "-" : fileName;
			try {
				dataFileName = moreFilePath ? dataFileName + dateFolder
						+ java.io.File.separator : dataFileName;
				makedir(dataFileName);
				dataFileName = dataFileName + timeFile;
				if (isReName && fileName != null
						&& fileName.trim().length() > 0) {
					// 取后缀
					dataFileName = dataFileName
							+ fileName.substring(fileName.lastIndexOf('.'));
				}
				saveFile(inputStream, dataFileName);
				return dataFileName.substring(basePath.length()).replace("\\",
						"/");
				// 数据库中保存的文件路径统一用/来做文件路径分隔符号,请不要手动修改
			} catch (Exception e) {
			}

		} finally {
			// added by yanghb20090116
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				// log.error("MailConfig:getMailResource()is 关闭错误");
			}
		}
		return null;

	}

	public static void makedir(String path) {
		java.io.File fileCreateF = new java.io.File(path.replace("\\",
				java.io.File.separator).replace("/", java.io.File.separator));
		if (!fileCreateF.exists()) {
			try {
				fileCreateF.mkdirs();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @param servlet
	 * @param request
	 * @param response
	 * @param path
	 *            TODO smartUpload上传文件时使用
	 */
	public static void saveFile(File file, String path) {
		try {
			file.saveAs(path.replace("\\", java.io.File.separator).replace("/",
					java.io.File.separator));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 获取文件扩展名/后缀名
	 * 
	 * @param fileName
	 * @return
	 * @create 2010-10-19 下午04:50:07 shenj
	 * @history
	 */
	public static String getExt(String fileName) throws BusinessException {
		int index = fileName.lastIndexOf('.');
		if (index == -1) {
			throw new BusinessException("上传的文件必须有扩展名");
		}
		return fileName.substring(index + 1, fileName.length());
	}

	/**
	 * **********************************************
	 * 
	 * @method：checkFileType
	 * @description：判断文件类型(checkType)是否与上传的文件后缀(fileExt)类型相符
	 * @create:Nov 6, 2008-2:58:44 PM huangrh
	 */
	public static boolean checkFileType(String fileExt, String checkType) {
		String[] array = new String[3];
		if (checkType.equals("1")) {
			// 图片文件格式 jpg,gif,bmp,tif,png,svg
			array[0] = "jpg";
			array[1] = "gif";
			array[2] = "jpeg";
		} else if (checkType.equals("2")) {
			// excel文件
			array[0] = "xls";
			array[1] = "xlsx";
		} else if (checkType.equals("3")) {
			// 视频文件格式 rm,rmvb,mpeg1-4,mov,mtv,dat,wmv,avi,3gp,amv,dmv
			array[0] = "rm";
			array[1] = "rmvb";
			array[2] = "wmv";
		} else if (checkType.equals("4")) {
			// Flash文件格式 swf,jpg,mp3,png,gif
			array[0] = "swf";
		}
		for (int i = 0; i < array.length; i++) {
			if (fileExt.equals(array[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断文件格式是否与上传的文件格式相同
	 * 
	 * @param fileType
	 * @param checkType
	 * @return
	 * @create 2010-10-19 下午04:48:16 shenj
	 * @history
	 */
	public static boolean checkFileContentType(String fileType, String checkType) {
		String[] array = new String[4];
		if (checkType.equals("1")) {
			// 图片文件格式 jpg,gif,bmp,tif,png,svg
			array[0] = "image/jpeg";
			array[1] = "image/jpg";
			array[2] = "image/gif";
			array[3] = "image/pjpeg";
		} else if (checkType.equals("2")) {
			// excel文件 2003，2007
			array[0] = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			array[1] = "application/vnd.ms-excel";
		}
		for (int i = 0; i < array.length; i++) {
			if (fileType.equals(array[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 校验文件是否存在
	 * 
	 * @param File
	 * @return
	 * @create Jan 20, 2009 11:05:14 AM zhangyw
	 * @history
	 */
	private static boolean isExit(File file) {
		String pathName = file.getFilePathName();
		java.io.File f = new java.io.File(pathName);
		if (f.exists())
			return true;
		else
			return false;
	}

	/**
	 * **********************************************
	 * 
	 * @method：getSmartUpload
	 * @description：添加fileType(文件类型)的参数以判断所上传的文件格式是否与所选择的文件类型一致
	 * @create:Nov 6, 2008-2:03:13 PM huangrh
	 */
	public static String getSmartUpload(javax.servlet.ServletConfig servlet,
			HttpServletRequest request, HttpServletResponse response,
			String path, String fileNamePrefix) {
		return getSmartUpload(servlet, request, response, path, fileNamePrefix,
				null);
	}

	/**
	 * **********************************************
	 * 
	 * @method：getSmartUpload
	 * @description：一次上传单个文件
	 * @Modify:Dec 16, 2008-4:05:48 PM huangrh
	 */
	public static String getSmartUpload(javax.servlet.ServletConfig servlet,
			HttpServletRequest request, HttpServletResponse response,
			String path, String fileNamePrefix, String fileType) {
		String returnPath = "";
		String filePath = "";
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		DateFormat timeFormat = new SimpleDateFormat("HHmmss");
		String dateFolder = dateFormat.format(date);
		String timeFile = timeFormat.format(date);
		try {
			SmartUpload smartUpload = new SmartUpload();
			smartUpload.initialize(servlet, request, response);
			smartUpload.setMaxFileSize(BUFFERLEN * 2);
			smartUpload.setTotalMaxFileSize(BUFFERLEN * 6);
			smartUpload.upload();
			File file = smartUpload.getFiles().getFile(0);
			if (fileType != null
					&& !checkFileType(file.getFileExt().toLowerCase(), fileType)) {
				return "error";
			}
			if (!file.isMissing()) {
				filePath = path + dateFolder + java.io.File.separator;
				makedir(filePath);
				String tmpPath = filePath + fileNamePrefix + "_" + timeFile;
				filePath = tmpPath + "." + file.getFileExt();
				saveFile(file, filePath);
			}
		} catch (SmartUploadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		String delStr = BaseConfigObj.getInstance().getUploadFileBasePath();
		returnPath = filePath.substring(delStr.length());
		returnPath = returnPath.replace("\\", "/");
		return returnPath;
	}

	/**
	 * 上传模板文件
	 * 
	 * @param servlet
	 * @param request
	 * @param response
	 * @param path
	 * @param fileNamePrefix
	 * @param fileType
	 * @return
	 * @create Nov 26, 2008 2:44:41 PM zhangyw
	 * @history
	 */
	public static String getSmartTemplateUpload(
			javax.servlet.ServletConfig servlet, HttpServletRequest request,
			HttpServletResponse response, String path, String fileNamePrefix,
			String fileType) {
		String returnPath = "";
		String filePath = "";
		Date date = new Date();
		String tmpPath = "";
		// DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		DateFormat timeFormat = new SimpleDateFormat("HHmmss");
		String dateFolder = "tempzip";
		String timeFile = timeFormat.format(date);
		try {
			SmartUpload smartUpload = new SmartUpload();
			smartUpload.initialize(servlet, request, response);
			smartUpload.setMaxFileSize(BUFFERLEN * 2);
			smartUpload.setTotalMaxFileSize(BUFFERLEN * 6);
			smartUpload.upload();
			File file = smartUpload.getFiles().getFile(0);
			// if(isExit(file)==false)
			// return "noExit";
			if (smartUpload.getSize() > 500 * 1024) {
				return "errorSize";
			}
			if (fileType != null
					&& !checkFileType(file.getFileExt().toLowerCase(), fileType)) {
				return "error";
			}
			if (!file.isMissing()) {
				filePath = path + dateFolder + java.io.File.separator;
				makedir(filePath);
				tmpPath = fileNamePrefix + "_" + timeFile;
				filePath = path + dateFolder + java.io.File.separator + tmpPath
						+ "." + file.getFileExt();
				returnPath = tmpPath + "." + file.getFileExt();
				saveFile(file, filePath);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
			return "errorSize";
		} catch (SmartUploadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return returnPath;
	}

	/**
	 * **********************************************
	 * 
	 * @method：uploadFiles
	 * @description：一次上传多个文件
	 * @Modify:Dec 16, 2008-4:08:21 PM huangrh
	 */
	public static Map uploadFiles(javax.servlet.ServletConfig servlet,
			HttpServletRequest request, HttpServletResponse response,
			String path, String fileNamePrefix) {
		String returnPath = "";
		String filePath = "";
		Map<String, String> resultMap = new HashMap<String, String>();
		Date date = new Date();
		String delStr = BaseConfigObj.getInstance().getUploadFileBasePath();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		DateFormat timeFormat = new SimpleDateFormat("HHmmss");
		String dateFolder = dateFormat.format(date);
		String timeFile = timeFormat.format(date);
		try {
			SmartUpload smartUpload = new SmartUpload();
			smartUpload.initialize(servlet, request, response);
			smartUpload.setMaxFileSize(BUFFERLEN * 2);
			smartUpload.setTotalMaxFileSize(BUFFERLEN * 6);
			smartUpload.upload();
			int length = smartUpload.getFiles().getCount();
			for (int i = 0; i < length; i++) {
				File file = smartUpload.getFiles().getFile(i);
				if (!file.isMissing()) {
					filePath = path + java.io.File.separator + dateFolder
							+ java.io.File.separator;
					makedir(filePath);
					filePath = filePath + fileNamePrefix + "_" + timeFile + "_"
							+ i + "." + file.getFileExt();
					saveFile(file, filePath);
					returnPath = filePath.substring(delStr.length());
					returnPath = returnPath.replace("\\", "/");
					resultMap.put("file" + i, returnPath);
				}
			}
		} catch (SmartUploadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 删除目录下的所有文件
	 * 
	 * @param path
	 * @create 2011-1-5 下午05:24:17 shenj
	 * @history
	 */
	public static void deleteDirectory(java.io.File path) {
		if (path.isDirectory()) {
			java.io.File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				java.io.File child = files[i];
				deleteDirectory(child);
			}
		}
		path.delete();
	}
}
