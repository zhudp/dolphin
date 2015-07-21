package com.hundsun.ctim.service.stock;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hundsun.ctim.domain.stock.ProductStock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring/applicationContext.xml"})
public class ProductStockServiceImpTest {
	@Autowired
	private ProductStockServiceImp productStockService;
	public void setProductStockService(ProductStockServiceImp productStockService){
		this.productStockService =productStockService;
	}
	@Test
	public void testQueryPaged() {
		Map<String,Object> map = new HashMap<String,Object>();
		 List<ProductStock>  list = this.productStockService.query(map);
		 assertTrue(list.size()>0);
	}

	@Test
	public void testAddStockLongLong() {
		long q = this.productStockService.getById(4l).getStock();
		this.productStockService.addStock(4l);
		long q1 = this.productStockService.getById(4l).getStock();
		assertTrue((q+1) == q1);
	}

	@Test
	public void testAddStockLong() {
		long q = this.productStockService.getById(4l).getStock();
		this.productStockService.addStock(4l,5l);
		long q1 = this.productStockService.getById(4l).getStock();
		assertTrue((q+5) == q1);
	}

	@Test
	public void testReduceStockLongLong() {
		long q = this.productStockService.getById(4l).getStock();
		this.productStockService.reduceStock(4l);
		long q1 = this.productStockService.getById(4l).getStock();
		assertTrue((q-1) == q1);
	}

	@Test
	public void testReduceStockLong() {
		long q = this.productStockService.getById(4l).getStock();
		this.productStockService.reduceStock(4l,5l);
		long q1 = this.productStockService.getById(4l).getStock();
		assertTrue((q-5) == q1);
	}

}
