package com.hundsun.ctim.service.stock;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.stock.MaterialStockOutDao;
import com.hundsun.ctim.domain.stock.MaterialStockOut;

@Service
public class MaterialStockOutServiceImp extends BaseService {
    @Autowired
    private MaterialStockOutDao entityDao;

    public void setMaterialStockOutDao(MaterialStockOutDao entityDao) {
        this.entityDao = entityDao;
    }
    
    /**
     * id 获取
     * @param id
     * @return
     */
    public MaterialStockOut getById(Long id){
    	return entityDao.get(id);
    }
    /**
     * 查询
     * @param map
     * @return
     */
    public List<MaterialStockOut> query(Map<String,Object> map){
    	return this.entityDao.query(map);
    }
    /**
     * 添加
     * @param materialStockOut
     */
    public void insert(MaterialStockOut materialStockOut){
    	entityDao.insert(materialStockOut);
    }
    /**
     * 更新
     * @param materialStockOut
     */
    public void update(MaterialStockOut materialStockOut){
    	entityDao.update(materialStockOut);
    }
    /**
     * 删除
     * @param materialStockOut
     */
    public void remove(MaterialStockOut materialStockOut){
    	entityDao.remove(materialStockOut);
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