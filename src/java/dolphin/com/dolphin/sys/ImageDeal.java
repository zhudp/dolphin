package com.dolphin.sys;

import java.util.Map;

import com.core.utils.ImgUtils;

/*******************************************************************************
 * 按一个配置的Map处理图片
 * 
 * @author: yanghb
 * @since: 2008-12-18 上午09:41:42
 * @history: ***********************************************
 * @file: ImageDeal.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 ******************************************************************************/
public class ImageDeal {
	/**
	 * 需要处理的图片map 按配置处理图片 width=?, height=?,append=?
	 */
	private Map imageMap;

	public void setImageMap(Map imageMap) {
		this.imageMap = imageMap;
	}

	private String convertAppendToUrl(String append, String url) {
		if (url != null) {
			int index = url.lastIndexOf(".");
			if(index>0){
				StringBuffer sb = new StringBuffer();
				sb.append(url.substring(0, index));
				sb.append(append);
				sb.append(url.substring(index));
				return sb.toString();
			}
		}
		return null;
	}

	private String getAppendFromMap() {
		if (imageMap != null) {
			String append = (String)imageMap.get("append");
			if(append!=null){
				return append;
			}
		}
		return null;
	}

	private int getWidthFromMap() {
		if (imageMap != null) {
			String width = (String)imageMap.get("width");
			if(width!=null){
				return Integer.parseInt(width);
			}
		}
		return 0;
	}

	private int getHeightFromMap() {
		if (imageMap != null) {
			String height = (String)imageMap.get("height");
			if(height!=null){
				return Integer.parseInt(height);
			}
		}
		return 0;
	}

	/** 
	 * @param srcImgPath 原图片物理路径
	 * @create  2008-12-18 上午09:50:14 yanghb
	 * @history  
	 */
	public void dealPicByMap(String srcImgPath) {
		if (srcImgPath != null && srcImgPath.indexOf(".")>0) {
			if(srcImgPath.indexOf(BaseConfigObj.getInstance().getFileLocalPath("")) == -1){
				srcImgPath = BaseConfigObj.getInstance().getFileLocalPath(srcImgPath);
			}
			String targetImgPath = convertAppendToUrl(this.getAppendFromMap(),srcImgPath);
			if(targetImgPath!=null){
				(new ImgUtils()).reduce(srcImgPath, targetImgPath, this.getWidthFromMap(), this.getHeightFromMap());
			}
		}
	}

}
