package com.hundsun.ctim.service.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.product.ProductDao;
import com.hundsun.ctim.domain.product.Product;

@Service
@Transactional
public class ProductServiceImp extends BaseService {
    @Autowired
    private ProductDao productDao;

    /**
     * 根据ID获取产品信息
     * @param id
     * @return
     */
	public Product getById(Long id) {
		return productDao.get(id);
	}
	
	/**
	 * 列表查询
	 */
	public List<Product> query(Map<String, Object> paramsMap) {
		return productDao.query(paramsMap);
	}
	
	/**
	 * 分页查询
	 */
	public Page queryPaged(Map<String, String> paramsMap){
		return productDao.queryPaged(paramsMap);
	}
	
	
    /**
     * 新增产品信息
     * @return
     */
	public void insert(Product product) {
		productDao.insert(product);
	}
	
	/**
	 * 更新产品信息
	 * @return
	 */
	public void update(Product product) {
		productDao.update(product);
	}
	/**
	 * 更新产品信息
	 * @return
	 */
	public void updateSelective(Product product) {
		productDao.updateSelective(product);
	}
	
	/**
	 * 根据ID删除
	 */
	public void removeById(Long customId) {
		productDao.removeById(customId);
	}
}