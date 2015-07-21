package com.core.utils;

import java.util.Map;

import com.dolphin.sys.BaseConfigObj;

/*******************************************************************************
 * 从配置取处理后文件名 
 * 处理方法：在url的最后一个"."前加配置中的append字串 
 * 使用范围:前后台都要使用
 * @author: yanghb
 * @since: 2008-12-17 下午04:45:34
 * @history: ***********************************************
 * @file: ImageNameTool.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 ******************************************************************************/
public class ImageNameTool {
	public ImageNameTool(){
		
	}
	/** 
	 * 产品图片
	 */
	public String getSmallProductPicName(String url) {
//		Map map = BaseConfigObj.getInstance().getProductDealPicMap();
//		Map img = (Map)map.get("smallProductPic");
//		String append = getAppendFromMap(img);
//		if (append != null) {
//			return convertAppendToUrl(append, url);
//		}
		return url;
	}

	public String getBigProductPicName(String url) {
//		Map map = BaseConfigObj.getInstance().getProductDealPicMap();
//		Map img = (Map)map.get("bigProductPic");
//		String append = getAppendFromMap(img);
//		if (append != null) {
//			return convertAppendToUrl(append, url);
//		}
		return url;
	}

	/** 
	 * 商铺图片
	 */
	public String getSmallBoothPicName(String url) {
//		Map map = BaseConfigObj.getInstance().getBoothDealPicMap();
//		Map img = (Map)map.get("smallBoothPic");
//		String append = getAppendFromMap(img);
//		if (append != null) {
//			return convertAppendToUrl(append, url);
//		}
		return url;
	}

	public String getBigBoothPicName(String url) {
//		Map map = BaseConfigObj.getInstance().getBoothDealPicMap();
//		Map img = (Map)map.get("bigBoothPic");
//		String append = getAppendFromMap(img);
//		if (append != null) {
//			return convertAppendToUrl(append, url);
//		}
		return url;
	}

	/** 
	 * 供求图片
	 */
	public String getSmallTradePicName(String url) {
//		Map map = BaseConfigObj.getInstance().getTradeDealPicMap();
//		Map img = (Map)map.get("smallTradePic");
//		String append = getAppendFromMap(img);
//		if (append != null) {
//			return convertAppendToUrl(append, url);
//		}
		return url;
	}

	public String getBigTradePicName(String url) {
//		Map map = BaseConfigObj.getInstance().getTradeDealPicMap();
//		Map img = (Map)map.get("bigTradePic");
//		String append = getAppendFromMap(img);
//		if (append != null) {
//			return convertAppendToUrl(append, url);
//		}
		return url;
	}

	/** 
	 * 新闻图片
	 */
	public String getSmallNewsPic(String url) {
//		Map map = BaseConfigObj.getInstance().getNewsDealPicMap();
//		Map img = (Map)map.get("smallNewsPic");
//		String append = getAppendFromMap(img);
//		if (append != null) {
//			return convertAppendToUrl(append, url);
//		}
		return url;
	}
	public String getImgNewsBigPic(String url) {
//		Map map = BaseConfigObj.getInstance().getNewsDealPicMap();
//		Map img = (Map)map.get("imgNewsBig");
//		String append = getAppendFromMap(img);
//		if (append != null) {
//			return convertAppendToUrl(append, url);
//		}
		return url;
	}
	public String getImgNewsSmallPic(String url) {
//		Map map = BaseConfigObj.getInstance().getNewsDealPicMap();
//		Map img = (Map)map.get("imgNewsSmall");
//		String append = getAppendFromMap(img);
//		if (append != null) {
//			return convertAppendToUrl(append, url);
//		}
		return url;
	}
	/**
	 * 广告图片
	 */
	public String getAdverClassPic(String url) {
//		Map map = BaseConfigObj.getInstance().getNewsDealPicMap();
//		Map img = (Map)map.get("adverClass");
//		String append = getAppendFromMap(img);
//		if (append != null) {
//			return convertAppendToUrl(append, url);
//		}
		return url;
	}

	protected String getAppendFromMap(Map image) {
		if (image != null && image.get("append") != null) {
			return (String) image.get("append");
		}
		return null;
	}

	protected String convertAppendToUrl(String append, String url) {
		if(url!=null){
			int index = url.lastIndexOf(".");
			if(index>0){
				StringBuffer sb = new StringBuffer();
				sb.append(url.substring(0, index));
				sb.append(append);
				sb.append(url.substring(index));
				return sb.toString();
			}
		}
		return "";
	}

	public static void main(String[]args){
		ImageNameTool imageNameTool = new ImageNameTool();
		System.out.println(imageNameTool.getSmallBoothPicName("D:/test/testb.jpg"));
		System.out.println(imageNameTool.getSmallNewsPic("D:/test/testn.jpg"));
		System.out.println(imageNameTool.getSmallProductPicName("D:/test/testp.jpg"));
		System.out.println(imageNameTool.getSmallTradePicName("D:/test/testt.jpg"));
		System.out.println(imageNameTool.getBigProductPicName("D:/test/testp.jpg"));
		System.out.println(imageNameTool.getBigBoothPicName("D:/test/testb.jpg"));
		System.out.println(imageNameTool.getBigTradePicName("D:/test/testt.jpg"));
		
	}
}
