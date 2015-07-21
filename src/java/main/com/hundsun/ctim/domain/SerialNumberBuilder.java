package com.hundsun.ctim.domain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class SerialNumberBuilder {
	private int nextNumber;
	private int leftNumber;
	private String date;
	
	protected static final Log log = LogFactory
			.getLog(SerialNumberBuilder.class);
	private static SerialNumberBuilder instance = null;

	public static synchronized SerialNumberBuilder getInstance() {
		if (null == SerialNumberBuilder.instance) {
			String configPathString = "/baseConfig/serialConfigContext.xml";
			log.debug("/*****************************/\n\tBaseConfigObj getInstance():\n\tconfigPathString:"
							+ configPathString);
			SerialNumberBuilder numberBuilder = new SerialNumberBuilder();
			numberBuilder.setNextNum("0");
			numberBuilder.setLeftNumber(0);
			java.net.URL url = numberBuilder.getClass().getResource(
					configPathString);
			Resource res = new UrlResource(url);
			XmlBeanFactory factory = new XmlBeanFactory(res);
			SerialNumberBuilder.instance = (SerialNumberBuilder) factory
					.getBean("serialNumberBuilder");
		}
		return SerialNumberBuilder.instance;
	}

	public void setLeftNumber(int leftNumber) {
		this.leftNumber = leftNumber;
	}

	public int getLeftNum() {
		return this.leftNumber;
	}

	/* 每次累加个数 */
	private int numAdd;
	/* 流水号长度 */
	private int numLength;
	
	public void addLeftNum() {
		this.leftNumber += numAdd;
	}

	

	public String getNextNum() {
		nextNumber += 1;
		String nextStr = nextNumber + "";
		
		for (int i = 0; nextStr.length() < numLength; i++) {
			nextStr = "0" + nextStr;
		}
		leftNumber -= 1;
		return nextStr;
	}
	
	

	public void setNextNum(String nextNum) {
		this.nextNumber = Integer.parseInt(nextNum);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
/**
 * 重置该单例
* @create  2010-7-29 上午10:45:27 shenj
* @history
 */
	public void reset(String date) {
		instance.setNextNum("0");
		instance.setLeftNumber(0);
		instance.setDate(date);
	}

public int getNumAdd() {
	return numAdd;
}

public void setNumAdd(int numAdd) {
	this.numAdd = numAdd;
}

public int getNumLength() {
	return numLength;
}

public void setNumLength(int numLength) {
	this.numLength = numLength;
}
	
	
}
