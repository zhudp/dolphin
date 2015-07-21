package com.hundsun.ctim.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author qiaoel@gmail.com
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"../../../../spring/applicationContext.xml","../../../../spring/applicationContext-security.xml"})
public class SerialNoServiceImpTest {
	@Autowired
	private SerialNoServiceImp serialNoService;

	/**
	 * Test method for {@link com.hundsun.ctim.service.SerialNoServiceImp#getNo(java.lang.String)}.
	 */
	@Test
	public void testGetNoString() {
		//默认偏移量长度获取编号
		String no = serialNoService.getNo("yl_wj");
		Assert.assertEquals("", "yl_wj_0001", no);
	}

	/**
	 * Test method for {@link com.hundsun.ctim.service.SerialNoServiceImp#getNo(java.lang.String, int)}.
	 */
	@Test
	public void testGetNoStringInt() {
		//指定偏移量长度
		String no = serialNoService.getNo("yl_ml",3);
		Assert.assertEquals("", "yl_ml_001", no);
	}

}
