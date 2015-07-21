package com.hundsun.ctim.service.supplier;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.supplier.SupplierDao;
import com.hundsun.ctim.domain.supplier.Supplier;

@Service
@Transactional
public class SupplierServiceImp extends BaseService {
    @Autowired
    private SupplierDao entityDao;

    public void setSupplierDao(SupplierDao entityDao) {
        this.entityDao = entityDao;
    }
    /**
     * id 获取
     * @param id
     * @return
     */
    public Supplier getById(Long id){
    	return entityDao.get(id);
    }
    /**
     * 查询
     * @param map
     * @return
     */
    public List<Supplier> query(Map<String,Object> map){
    	return this.entityDao.query(map);
    }
    /**
     * 添加
     * @param Supplier
     */
    public void insert(Supplier supplier){
    	entityDao.insert(supplier);
    }
    /**
     * 更新
     * @param Supplier
     */
    public void update(Supplier supplier){
    	entityDao.update(supplier);
    }
    /**
     * 删除
     * @param Supplier
     */
    public void remove(Supplier supplier){
    	entityDao.remove(supplier);
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