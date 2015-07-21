package com.hundsun.ctim.service.stock;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.stock.ProductStockInDao;
import com.hundsun.ctim.domain.stock.ProductStockIn;

@Service
@Transactional
public class ProductStockInServiceImp extends BaseService {
    @Autowired
    private ProductStockInDao entityDao;

    public void setProductStockInDao(ProductStockInDao entityDao) {
        this.entityDao = entityDao;
    }
    public ProductStockIn getById(Long id){
    	return entityDao.get(id);
    }
    public List<ProductStockIn> query(Map<String,Object> map){
    	return this.entityDao.query(map);
    }
    public void insert(ProductStockIn productStockIn){
    	entityDao.insert(productStockIn);
    }
    public void update(ProductStockIn productStockIn){
    	entityDao.update(productStockIn);
    }
    public void remove(ProductStockIn productStockIn){
    	entityDao.remove(productStockIn);
    }
    public void removeById(Long id){
    	entityDao.removeById(id);
    }
	public Page queryPaged(Map<String, String> paramsMap){
		return entityDao.queryPaged(paramsMap);
	}
    
}