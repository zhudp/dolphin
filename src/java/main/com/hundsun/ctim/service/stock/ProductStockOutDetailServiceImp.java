package com.hundsun.ctim.service.stock;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.stock.ProductStockOutDetailDao;
import com.hundsun.ctim.domain.stock.ProductStockOutDetail;

@Service
public class ProductStockOutDetailServiceImp extends BaseService {
    @Autowired
    private ProductStockOutDetailDao entityDao;

    public void setProductStockOutDetailDao(ProductStockOutDetailDao entityDao) {
        this.entityDao = entityDao;
    }
    
    /**
     * id 获取
     * @param id
     * @return
     */
    public ProductStockOutDetail getById(Long id){
    	return entityDao.get(id);
    }
    /**
     * 查询
     * @param map
     * @return
     */
    public List<ProductStockOutDetail> query(Map<String,Object> map){
    	return this.entityDao.query(map);
    }
    /**
     * 添加
     * @param product
     */
    public void insert(ProductStockOutDetail productStockOutDetail){
    	entityDao.insert(productStockOutDetail);
    }
    /**
     * 更新
     * @param product
     */
    public void update(ProductStockOutDetail productStockOutDetail){
    	entityDao.update(productStockOutDetail);
    }
    /**
     * 删除
     * @param product
     */
    public void remove(ProductStockOutDetail productStockOutDetail){
    	entityDao.remove(productStockOutDetail);
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