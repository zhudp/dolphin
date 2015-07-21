package com.hundsun.ctim.dao.stock;

import org.springframework.stereotype.Repository;

import com.core.dao.IBatisEntityDao;
import com.hundsun.ctim.domain.stock.ProductStock;

@Repository
public class ProductStockDao extends IBatisEntityDao<ProductStock> {
	public long addProductStock(long productId,long count){
		ProductStock productStock = this.get(productId);
		long q = productStock.getStock() + count;
		productStock.setStock(q);
		this.update(productStock);
		return q;
	}
	public long reduceProductStock(long productId,long count){
		ProductStock productStock = this.get(productId);
		long q = productStock.getStock() - count;
		productStock.setStock(q);
		this.update(productStock);
		return q;
	}
}