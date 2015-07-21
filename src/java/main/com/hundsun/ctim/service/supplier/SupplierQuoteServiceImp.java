package com.hundsun.ctim.service.supplier;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.supplier.SupplierQuoteDao;
import com.hundsun.ctim.domain.supplier.SupplierQuote;

@Service
@Transactional
public class SupplierQuoteServiceImp extends BaseService {
    @Autowired
    private SupplierQuoteDao entityDao;

    public void setSupplierQuoteDao(SupplierQuoteDao entityDao) {
        this.entityDao = entityDao;
    }
    
    /**
     * id 获取
     * @param id
     * @return
     */
    public SupplierQuote getById(Long id){
    	return entityDao.get(id);
    }
    /**
     * 查询
     * @param map
     * @return
     */
    public List<SupplierQuote> query(Map<String,Object> map){
    	return this.entityDao.query(map);
    }
    /**
     * 添加
     * @param SupplierQuote
     */
    public void insert(SupplierQuote supplierQuote){
    	entityDao.insert(supplierQuote);
    }
    /**
     * 更新
     * @param SupplierQuote
     */
    public void update(SupplierQuote supplierQuote){
    	entityDao.update(supplierQuote);
    }
    /**
     * 删除
     * @param SupplierQuote
     */
    public void remove(SupplierQuote supplierQuote){
    	entityDao.remove(supplierQuote);
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