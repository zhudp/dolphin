package com.core.exception;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ***********************************************
 * 
 * @file: MultiBusinessException.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 *             ***********************************************
 * @package: com.hs.core.exception
 * @class: MultiBusinessException
 * @description:
 * 
 * @author: lilq
 * @since: 2008-6-13-14:18:44
 * @history:
 */
@SuppressWarnings("serial")
public class MultiBusinessException extends RuntimeException {
	private List<BusinessException> exceptionList = new ArrayList<BusinessException>();

	public MultiBusinessException() {
	}

	public MultiBusinessException(String messageList) {
		super(messageList);
	}

	public boolean put(BusinessException e) {
		return exceptionList.add(e);
	}

	public void remove(BusinessException e) {
		exceptionList.remove(e);
	}

	@Override
	public String getMessage() {
		StringBuffer msgBuf = new StringBuffer();
		for (int i = 0; i < exceptionList.size(); i++) {
			msgBuf.append(exceptionList.get(i).getMessage());
		}
		return msgBuf.toString();
	}

	public boolean hasExcption() {
		return exceptionList.size() > 0;
	}

	public Iterator iterator() {
		return exceptionList.iterator();
	}
}
