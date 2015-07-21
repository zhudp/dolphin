package com.core.tool;

import java.net.URL;
import java.util.Map;

public final class UpdateFromVm {
	private String velocityWebPath = "";
	public UpdateFromVm(){
		URL velocitypath = this.getClass().getResource("UpdateFromVm.class");
		velocityWebPath = velocitypath.getPath();
		int pos = velocityWebPath.indexOf("file:");   
	    if(pos>-1){
	    	velocityWebPath=velocityWebPath.substring(pos+5);
	    }
	    velocityWebPath = velocityWebPath.replaceAll("%20", " ");
	    velocityWebPath = velocityWebPath.substring(0, velocityWebPath.indexOf("WEB-INF"));
	}
	public void update(String templateFile,String targetFile,Map map) {
		VelocityTool vt = new VelocityTool(velocityWebPath);
		targetFile = velocityWebPath + targetFile;
		vt.translateFile(templateFile, targetFile, map);
	}
	
	public String translateString(String templateFile,Map map) {
		String str = "";
		VelocityTool vt = new VelocityTool(velocityWebPath);
		str = vt.translateString(templateFile, map, Boolean.TRUE);
		return str;
	}
}
