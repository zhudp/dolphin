package com.core.utils;

import java.util.Map;

import com.dolphin.sys.BaseConfigObj;
import com.dolphin.sys.ImageDeal;
import com.dolphin.sys.ImagesDeal;

/*******************************************************************************
 * 图片处理工具
 * 
 * @author: yanghb
 * @since: 2008-12-17 下午05:47:36
 * @history: ***********************************************
 * @file: ImageDealTool.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 ******************************************************************************/
public class ImageDealTool {
	/** 
	 * 产品图片处理，上传产品图片后调用
	 * @param picPath 上传图片的相对地址
	 * @create  2008-12-18 上午08:56:16 yanghb
	 * @history  
	 */
	public void ProductPicDeal(String picPath) {
//		Map imagesMap = BaseConfigObj.getInstance().getProductDealPicMap();
//		ImagesDeal imagesDeal = new ImagesDeal();
//		imagesDeal.setImagesMap(imagesMap);
//		imagesDeal.dealPicByMapRelate(picPath);
	}

	/** 
	 * 商铺图片处理，上传商铺图片后调用
	 * @param picPath 上传图片的相对地址
	 * @create  2008-12-18 上午08:57:01 yanghb
	 * @history  
	 */
	public void BoothPicDeal(String picPath) {
//		Map imagesMap = BaseConfigObj.getInstance().getBoothDealPicMap();
//		ImagesDeal imagesDeal = new ImagesDeal();
//		imagesDeal.setImagesMap(imagesMap);
//		imagesDeal.dealPicByMapRelate(picPath);
	}

	/** 
	 * 供求图片处理，上传供求图片后调用
	 * @param picPath 上传图片的相对地址
	 * @create  2008-12-18 上午08:57:04 yanghb
	 * @history  
	 */
	public void TradePicDeal(String picPath) {
//		Map imagesMap = BaseConfigObj.getInstance().getTradeDealPicMap();
//		ImagesDeal imagesDeal = new ImagesDeal();
//		imagesDeal.setImagesMap(imagesMap);
//		imagesDeal.dealPicByMapRelate(picPath);
	}

	/** 
	 * 新闻图片处理，上传新闻图片后调用
	 * @param picPath 上传图片的相对地址
	 * @create  2008-12-18 上午08:57:06 yanghb
	 * @history  
	 */
	public void NewsPicDeal(String picPath) {
//		Map imagesMap = BaseConfigObj.getInstance().getNewsDealPicMap();
//		ImagesDeal imagesDeal = new ImagesDeal();
//		imagesDeal.setImagesMap(imagesMap);
//		imagesDeal.dealPicByMapRelate(picPath);
	}
	
	/**
	 * 新闻图片处理 根据keyStr匹配要生成的图片格式处理
	 * @param picPath 上传图片的相对地址/绝对地址皆可
	 * 		  keyStr 传进来的key参数必须与配置项中的要生成的图片格式的key相同
	 * @create:Dec 18, 2008-3:36:32 PM huangrh
	 */
	public void NewsPicDealByParams(String picPath, String keyStr) {
//		Map imagesMap = BaseConfigObj.getInstance().getNewsDealPicMap();
//		if(imagesMap.containsKey(keyStr)){
//			ImageDeal imageDeal = new ImageDeal();
//			imagesMap = (Map)imagesMap.get(keyStr);
//			imageDeal.setImageMap(imagesMap);
//			imageDeal.dealPicByMap(picPath);
//		}
	}

	/**
	 * ********************************************** 
	 * @method：NewsPicDeal  
	 * @param picPath 上传图片的相对地址
	 * @description：广告图片处理，上传广告图片后调用
	 * @create:Dec 19, 2008-9:43:34 AM huangrh
	 */
	public void AdverPicDeal(String picPath) {
//		Map imagesMap = BaseConfigObj.getInstance().getAdverDealPicMap();
//		ImagesDeal imagesDeal = new ImagesDeal();
//		imagesDeal.setImagesMap(imagesMap);
//		imagesDeal.dealPicByMapRelate(picPath);
	}
}
