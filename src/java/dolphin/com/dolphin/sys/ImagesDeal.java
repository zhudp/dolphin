package com.dolphin.sys;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class ImagesDeal {
	private Map imagesMap;
	
	public void setImagesMap(Map imagesMap) {
		this.imagesMap = imagesMap;
	}

	protected void dealPicByMap(String srcImgPath){
		if (imagesMap != null) {
			Set set = imagesMap.entrySet();
			Iterator keySet = set.iterator();
			while (keySet.hasNext()) {
				Entry entry = (Entry) keySet.next();
				ImageDeal imageDeal = new ImageDeal();
				imageDeal.setImageMap((Map)entry.getValue());
				imageDeal.dealPicByMap(srcImgPath);				
			}
		}
	}
	
	public void dealPicByMapRelate(String picPath){
		String srcImgPath = BaseConfigObj.getInstance().getFileLocalPath(picPath);
		this.dealPicByMap(srcImgPath);
	}
}
