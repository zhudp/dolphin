package com.hundsun.ctim.service.stock;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring/applicationContext.xml"})
public class MaterialStockServiceImpTest {
	@Autowired
	private MaterialStockServiceImp materialStockService;

	
	@Test
	public void testAddStockLongLong() {
		long q = materialStockService.getById(12l).getStock();
		materialStockService.addStock(12l, 5l);
		long q1 = materialStockService.getById(12l).getStock();
		assertTrue("添加测试失败", q+5 == q1);
	}

	@Test
	public void testAddStockLong() {
		long q = materialStockService.getById(12l).getStock();
		materialStockService.addStock(12l);
		long q1 = materialStockService.getById(12l).getStock();
		assertTrue("添加测试失败", q+1 == q1);
	}

	@Test
	public void testReduceStockLongLong() {
		long q = materialStockService.getById(12l).getStock();
		materialStockService.reduceStock(12l, 5l);
		long q1 = materialStockService.getById(12l).getStock();
		assertTrue("减少测试失败", q-5 == q1);
	}

	@Test
	public void testReduceStockLong() {
		long q = materialStockService.getById(12l).getStock();
		materialStockService.reduceStock(12l);
		long q1 = materialStockService.getById(12l).getStock();
		assertTrue("减少测试失败", q-1 == q1);
	}

}
