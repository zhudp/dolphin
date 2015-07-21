package com.hundsun.ctim.service.stock;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.stock.ProductStockOutDao;
import com.hundsun.ctim.domain.stock.ProductStockOut;

@Service
public class ProductStockOutServiceImp extends BaseService {
    @Autowired
    private ProductStockOutDao entityDao;

    public void setProductStockOutDao(ProductStockOutDao entityDao) {
        this.entityDao = entityDao;
    }
    
    /**
     * id 获取
     * @param id
     * @return
     */
    public ProductStockOut getById(Long id){
    	return entityDao.get(id);
    }
    /**
     * 查询
     * @param map
     * @return
     */
    public List<ProductStockOut> query(Map<String,Object> map){
    	return this.entityDao.query(map);
    }
    /**
     * 添加
     * @param productStockOut
     */
    public void insert(ProductStockOut productStockOut){
    	entityDao.insert(productStockOut);
    }
    /**
     * 更新
     * @param productStockOut
     */
    public void update(ProductStockOut productStockOut){
    	entityDao.update(productStockOut);
    }
    /**
     * 删除
     * @param productStockOut
     */
    public void remove(ProductStockOut productStockOut){
    	entityDao.remove(productStockOut);
    }
    public void removeById(Long id){
    	entityDao.removeById(id);
    }
	/**
	 * 分页查询
	 */
	public Page queryPaged(Map<String, String> paramsMap){
		return entityDao.queryPaged(paramsMap);
	}
    
}