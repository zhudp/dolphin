package com.hundsun.ctim.service.stock;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.stock.ProductStockDao;
import com.hundsun.ctim.domain.stock.ProductStock;

@Service
@Transactional
public class ProductStockServiceImp extends BaseService {
	   @Autowired
	    private ProductStockDao entityDao;

	    public void setProductStockDao(ProductStockDao entityDao) {
	        this.entityDao = entityDao;
	    }
	    public ProductStock getById(Long id){
	    	return entityDao.get(id);
	    }
	    public List<ProductStock> query(Map<String,Object> map){
	    	return this.entityDao.query(map);
	    }
	    public void insert(ProductStock productStock){
	    	entityDao.insert(productStock);
	    }
	    public void update(ProductStock productStock){
	    	entityDao.update(productStock);
	    }
	    public void remove(ProductStock productStock){
	    	entityDao.remove(productStock);
	    }
	    public void removeById(Long id){
	    	entityDao.removeById(id);
	    }
		public Page queryPaged(Map<String, String> paramsMap){
			return entityDao.queryPaged(paramsMap);
		}
		/**
		 * count 可以为负数据，等同于减
		 * @param ProductId
		 * @param count
		 * @return
		 */
		public long addStock(Long productId,Long count){
			ProductStock ProductStock = entityDao.get(productId);
			if(null ==ProductStock){
				ProductStock newProductStock = new ProductStock();
				newProductStock.setProductId(productId);
				newProductStock.setStock(0l);
				entityDao.insert(newProductStock);
				ProductStock = newProductStock;
			}
			long q = ProductStock.getStock()+count;
			ProductStock.setStock(q);
			entityDao.update(ProductStock);
			return q;
		}
		public long addStock(Long productId){
			return this.addStock(productId, 1l);
		}
		public long reduceStock(Long productId,Long count){
			ProductStock productStock = entityDao.get(productId);
			if(null ==productStock){
				ProductStock newProductStock = new ProductStock();
				newProductStock.setProductId(productId);
				newProductStock.setStock(0l);
				entityDao.insert(newProductStock);
				productStock = newProductStock;
			}
			long q = productStock.getStock()-count;
			productStock.setStock(q);
			entityDao.update(productStock);
			return q;
		}
		public long reduceStock(Long ProductId){
			return this.reduceStock(ProductId,1l);
		}
}