package com.hundsun.ctim.service.stock;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hundsun.ctim.domain.stock.MaterialStock;
import com.hundsun.ctim.domain.stock.MaterialStockOutDetail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring/applicationContext.xml"})
public class MaterialStockOutDetailServiceImpTest {
	@Autowired
	private MaterialStockOutDetailServiceImp materialStockOutDetailService;
	@Autowired
	private MaterialStockServiceImp materialStockService;
	@Test
	public void testInsert() {
		MaterialStockOutDetail materialStockOutDetail = new MaterialStockOutDetail();
		materialStockOutDetail.setMaterialId(11l);
		materialStockOutDetail.setStockOutId(1l);
		materialStockOutDetail.setQuantity(10l);
		MaterialStock  stock = materialStockService.getById(materialStockOutDetail.getMaterialId());
		materialStockOutDetailService.insert(materialStockOutDetail);
		MaterialStock  newStock = materialStockService.getById(materialStockOutDetail.getMaterialId());
		if(stock == null){
			assertTrue("",newStock.getStock() == 10);
		}else{
			assertTrue("",newStock.getStock() == stock.getStock()-10);
		}
	}

	@Test
	public void testUpdate() {
		MaterialStockOutDetail detail = materialStockOutDetailService.getById(1l);
		MaterialStock stock = materialStockService.getById(detail.getMaterialId());
		long s = stock.getStock();
		long q = detail.getQuantity();
		// update q
		detail.setQuantity(20l);
		materialStockOutDetailService.update(detail);
		MaterialStock newStock = materialStockService.getById(detail.getMaterialId());
		assertTrue(20-q == s-newStock.getStock());

		// del
		s = newStock.getStock();
		q = detail.getQuantity();
		detail.setIsDeleted("2");
		materialStockOutDetailService.update(detail);
		newStock = materialStockService.getById(detail.getMaterialId());
		assertTrue(s+q == newStock.getStock());
	}

}
