package com.hundsun.ctim.service.perchase;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.purchase.PurchaseDao;
import com.hundsun.ctim.domain.purchase.Purchase;

@Service
@Transactional
public class PurchaseServiceImp extends BaseService {
    @Autowired
    private PurchaseDao entityDao;

    public void setPurchaseDao(PurchaseDao entityDao) {
        this.entityDao = entityDao;
    }
    
    /**
     * id 获取
     * @param id
     * @return
     */
    public Purchase getById(Long id){
    	return entityDao.get(id);
    }
    /**
     * 查询
     * @param map
     * @return
     */
    public List<Purchase> query(Map<String,Object> map){
    	return this.entityDao.query(map);
    }
    /**
     * 添加
     * @param purchase
     */
    public void insert(Purchase purchase){
    	entityDao.insert(purchase);
    }
    /**
     * 更新
     * @param purchase
     */
    public void update(Purchase purchase){
    	entityDao.update(purchase);
    }
    /**
     * 删除
     * @param purchase
     */
    public void remove(Purchase purchase){
    	entityDao.remove(purchase);
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