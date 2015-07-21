package com.hundsun.ctim.service.stock;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.stock.ProductStockInDetailDao;
import com.hundsun.ctim.domain.stock.ProductStockInDetail;

@Service
@Transactional
public class ProductStockInDetailServiceImp extends BaseService {
    @Autowired
    private ProductStockInDetailDao entityDao;
    @Autowired
    private ProductStockServiceImp productStockService;

    public void setProductStockInDetailDao(ProductStockInDetailDao entityDao) {
        this.entityDao = entityDao;
    }
    
	public void setProductStockServiceImp(
			ProductStockServiceImp productStockService) {
		this.productStockService = productStockService;
	}

	public ProductStockInDetail getById(Long id){
    	return entityDao.get(id);
    }
    public List<ProductStockInDetail> query(Map<String,Object> map){
    	return this.entityDao.query(map);
    }
    public void insert(ProductStockInDetail productStockInDetail){
    	entityDao.insert(productStockInDetail);
    	// update stock
    	productStockService.addStock(productStockInDetail.getProductId(),(long) (productStockInDetail.getRealQuantity()));
    	
    }
    public void update(ProductStockInDetail productStockInDetail){
    	ProductStockInDetail oldDetail = entityDao.get(productStockInDetail.getDetailId());
    	long q = productStockInDetail.getRealQuantity() - oldDetail.getRealQuantity();
    	if(oldDetail.getIsDeleted().equals("0") && !productStockInDetail.getIsDeleted().equals("0")){
    		productStockService.reduceStock(oldDetail.getProductId(), (long)oldDetail.getRealQuantity());
    	}else if(q!=0){
    		productStockService.addStock(oldDetail.getProductId(), q);
    	}
    	entityDao.update(productStockInDetail);
    }
    public void remove(ProductStockInDetail productStockInDetail){
    	entityDao.remove(productStockInDetail);
    }
    public void removeById(Long id){
    	entityDao.removeById(id);
    }
	public Page queryPaged(Map<String, String> paramsMap){
		return entityDao.queryPaged(paramsMap);
	}
}