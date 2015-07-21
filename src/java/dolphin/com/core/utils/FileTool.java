package com.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileTool {

	/**
	 * 删除文件
	 * 
	 * @param f
	 * @return
	 * @create 2008-12-4 上午11:19:10 wanghh
	 * @history
	 */
	public static boolean deletefile(File f){
		if (f.isFile())
			return f.delete();
		return true;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param f
	 * @return
	 * @create 2008-12-4 上午11:19:32 wanghh
	 * @history
	 */
	public static boolean deletedir(File f) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory())
					deletedir(files[i]);
				else
					deletefile(files[i]);
			}
		}
		f.delete();
		return true;
	}

	/**
	 * 得到指定文件夹下，指定文件夹名前缀的所有文件夹,除了excludeDir
	 * 
	 * @param dir
	 * @param namePrefix
	 * @param excludeDir
	 * @return
	 * @create 2008-12-5 上午10:36:12 wanghh
	 * @history
	 */
	public static List getDirByNameprefix(File dir, String namePrefix,
			String excludeDir) {
		List dirList = new ArrayList();
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (!files[i].isDirectory()
						|| files[i].getName().equals(excludeDir))
					continue;
				if (files[i].getName().startsWith(namePrefix))
					dirList.add(files[i]);
			}
		}
		return dirList;
	}

	/**
	 * 文件重命名
	 * 
	 * @param oldFile
	 * @param newFileName
	 * @return
	 * @create 2008-12-4 上午11:36:44 wanghh
	 * @history
	 */
	public static boolean renameFile(File oldFile, String newFileName) {
		if (oldFile.isFile()) {
			oldFile.renameTo(new File(oldFile.getAbsolutePath().substring(0,
					oldFile.getAbsolutePath().lastIndexOf(File.separator) + 1)
					+ newFileName));
		}
		return true;
	}

	/**
	 * 文件夹重命名
	 * 
	 * @param oldDir
	 * @param newDirName
	 * @return
	 * @create 2008-12-4 上午11:39:10 wanghh
	 * @history
	 */
	public static boolean renameDir(File oldDir, String newDirName) {
		if (oldDir.isDirectory()) {
			oldDir.renameTo(new File(oldDir.getAbsolutePath().substring(0,
					oldDir.getAbsolutePath().lastIndexOf(File.separator) + 1)
					+ newDirName));
		}
		return true;
	}

	/**
	 * **********************************************
	 * 
	 * @method：copyFile
	 * @param srcFile
	 *            源文件全路径
	 * @param targetFile
	 *            目标文件全路径
	 * @description：将源文件复制一份到目标文件(主要是文件名不同)
     * @create:Dec 18, 2008-4:55:20
	 *                                                          PM huangrh
	 */
	public static void copyFile(String srcFile, String targetFile) {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(srcFile));
			FileOutputStream outputStream = new FileOutputStream(new File(
					targetFile));
			byte[] b = new byte[1024];
			while (inputStream.read(b) != -1) {
				outputStream.write(b);
			}
			inputStream.close();
			outputStream.close();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			// added by yanghb20090116
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				// FileUtils inputStream 关闭错误");
			}
		}
	}

	/**
	 * 保存文件
	 */
	public static void saveFile(File file, String path, String filename) throws IOException {
		
        File folder = new File(path);
        if (!folder.exists()) {
        	folder.mkdirs();
        }
		
		FileInputStream stream = new FileInputStream(file);
		FileOutputStream fs = new FileOutputStream(path + "/" + filename);
		byte[] buffer = new byte[1024 * 1024];
		int bytesum = 0;
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			bytesum += byteread;
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}
	
	public static void main(String[] ddd) {
		// File dir = new File("F:\\fff");
		// if(FileUtils.deletedir(dir))System.out.println("sucess");
	}

}
