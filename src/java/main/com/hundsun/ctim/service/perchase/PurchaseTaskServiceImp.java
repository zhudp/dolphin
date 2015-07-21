package com.hundsun.ctim.service.perchase;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.purchase.PurchaseTaskDao;
import com.hundsun.ctim.domain.purchase.PurchaseTask;

@Service
@Transactional
public class PurchaseTaskServiceImp extends BaseService {
    @Autowired
    private PurchaseTaskDao entityDao;

    public void setPurchaseTask(PurchaseTaskDao entityDao) {
        this.entityDao = entityDao;
    }
    
    /**
     * id 获取
     * @param id
     * @return
     */
    public PurchaseTask getById(Long id){
    	return entityDao.get(id);
    }
    /**
     * 查询
     * @param map
     * @return
     */
    public List<PurchaseTask> query(Map<String,Object> map){
    	return this.entityDao.query(map);
    }
    /**
     * 添加
     * @param purchaseTask
     */
    public void insert(PurchaseTask purchaseTask){
    	entityDao.insert(purchaseTask);
    }
    /**
     * 更新
     * @param purchaseTask
     */
    public void update(PurchaseTask purchaseTask){
    	entityDao.update(purchaseTask);
    }
    /**
     * 删除
     * @param purchaseTask
     */
    public void remove(PurchaseTask purchaseTask){
    	entityDao.remove(purchaseTask);
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