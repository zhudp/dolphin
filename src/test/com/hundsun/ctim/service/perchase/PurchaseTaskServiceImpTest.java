package com.hundsun.ctim.service.perchase;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hundsun.ctim.domain.purchase.PurchaseTask;
import com.hundsun.ctim.service.JunitSpringBase;

public class PurchaseTaskServiceImpTest extends JunitSpringBase{
	@Autowired
	PurchaseTaskServiceImp purchaseTaskService;
	@Test
	public void testGetById() {
		PurchaseTask task = purchaseTaskService.getById((long)1);
		Assert.assertTrue(task!=null);
	}

	@Test
	public void testQuery() {
		Map<String,Object> parmp = new HashMap<String,Object>();
		parmp.put("status", "1");
		List<PurchaseTask> list = purchaseTaskService.query(parmp);
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testInsert() {
		PurchaseTask task = new PurchaseTask();
		task.setMaterialId((long)1);
		task.setProduceTaskId((long)1);
		task.setPurchaseId((long)1);
		task.setQuantity(new BigDecimal(10));
		task.setTaskId((long)1);
		purchaseTaskService.insert(task);
	}

	@Test
	public void testUpdate() {
		PurchaseTask task = purchaseTaskService.getById((long)1);
		task.setStatus("2");
		purchaseTaskService.update(task);
		task = purchaseTaskService.getById((long)1);
		Assert.assertTrue(task.getStatus().equals("2"));
	}

	@Test
	public void testRemove() {
	}

}
