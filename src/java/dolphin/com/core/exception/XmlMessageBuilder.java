package com.core.exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * 解析XML并对异常信息进行组装
 * 
 * @author: lilq
 * @since: 2008-6-24 13:30:14
 * @history: 2008年7月7日 13时20分21秒 袁聪 改用ErrorMessage类
 * 
 * @file: XmlMessageBuilder.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 *             ***********************************************
 */
public class XmlMessageBuilder {
	private static final String EXCEPTION_CONFIG_ELEMENT = "exceptioninfo";

	private static final String EXCEPTION_ELEMENT = "exception";

	public XmlMessageBuilder() {
	}

	/**
	 * 组装异常提示信息
	 * 
	 * @param inputstream
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 * @throws Exception
	 * @create 2008-6-24 13:54:00 lilq
	 * @history 2008年7月7日 13时20分21秒 袁聪 改用ErrorMessage类
	 * 
	 */
	public synchronized static ErrorMessage getMessageResources(InputStream inputstream)
			throws IOException, JDOMException, Exception {
		ErrorMessage errorMessage = new ErrorMessage();
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(inputstream);
		Element root = doc.getRootElement();
		String rootName = root.getName();
		if (rootName.equals(EXCEPTION_CONFIG_ELEMENT)) {
			List children = root.getChildren();
			for (int i = 0; i < children.size(); i++) {
				Element child = (Element) children.get(i);
				if (child.getName().equals(EXCEPTION_ELEMENT))
					errorMessage.putMsgFile(child.getText().trim());
			}
		} else {
			throw new IOException("The root tag of the ExceptionConfig XML document must be '"
					+ EXCEPTION_CONFIG_ELEMENT + "'.");
		}
		return errorMessage;
	}

	public void redeploy() throws IOException, JDOMException, Exception {
        InputStream instr = null;
        try{
        	instr = getClass().getResourceAsStream("/expinfo.xml");
        	getMessageResources(instr);
        }finally{
        	//added by yanghb20090116
			try{
				if(instr!=null){
					instr.close();
				}
			}catch(Exception e){
				throw new Exception("XmlMessageBuilder:/expinfo.xml 关闭错误");
			}
        }
        
    }
}
