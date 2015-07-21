package com.hundsun.ctim.service.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.product.ProductPartsDao;
import com.hundsun.ctim.domain.product.ProductParts;

@Service
public class ProductPartsServiceImp extends BaseService {
	
    @Autowired
    private ProductPartsDao productPartsDao;
    
    /**
     * 根据ID获取产品配件信息
     * @param id
     * @return
     */
	public ProductParts getById(Long id) {
		return productPartsDao.get(id);
	}
	
	/**
	 * 列表查询
	 */
	public List<ProductParts> query(Map<String, Object> paramsMap) {
		return productPartsDao.query(paramsMap);
	}
	
	/**
	 * 分页查询
	 */
	public Page queryPaged(Map<String, String> paramsMap){
		return productPartsDao.queryPaged(paramsMap);
	}
	
	
    /**
     * 新增产品配件信息
     * @return
     */
	public void insert(ProductParts productParts) {
		productPartsDao.insert(productParts);
	}
	
	/**
	 * 更新产品配件信息
	 * @return
	 */
	public void update(ProductParts productParts) {
		productPartsDao.update(productParts);
	}
	/**
	 * 更新产品配件信息
	 * @return
	 */
	public void updateSelective(ProductParts productParts) {
		productPartsDao.updateSelective(productParts);
	}
	
	/**
	 * 根据ID删除
	 */
	public void removeById(Long partId) {
		productPartsDao.removeById(partId);
	}
}