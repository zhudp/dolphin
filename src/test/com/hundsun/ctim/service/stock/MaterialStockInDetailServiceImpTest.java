package com.hundsun.ctim.service.stock;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hundsun.ctim.domain.stock.MaterialStock;
import com.hundsun.ctim.domain.stock.MaterialStockInDetail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring/applicationContext.xml"})
public class MaterialStockInDetailServiceImpTest {
	@Autowired
	private MaterialStockServiceImp materialStockService;
	@Autowired
	private MaterialStockInDetailServiceImp materialStockInDetailService;
	
	@Test
	public void testInsert() {
		MaterialStock stock = materialStockService.getById(11l);
		long s = stock.getStock();
		MaterialStockInDetail detail = new MaterialStockInDetail();
		
		detail.setMaterialId(11l);
		detail.setRealQuantity(10l);
		materialStockInDetailService.insert(detail);
		MaterialStock newStock = materialStockService.getById(11l);
		assertTrue(s+10 == newStock.getStock());
	}

	@Test
	public void testUpdate() {
		MaterialStock stock = materialStockService.getById(11l);
		MaterialStockInDetail detail = materialStockInDetailService.getById(1l);
		long s = stock.getStock();
		long q = detail.getRealQuantity();
		detail.setRealQuantity(11l);
		materialStockInDetailService.update(detail);
		MaterialStock newStock = materialStockService.getById(11l);
		assertTrue(11-q==newStock.getStock()-s);
		
	}

}
