package com.hundsun.ctim.service.stock;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.stock.MaterialStockInDao;
import com.hundsun.ctim.domain.stock.MaterialStockIn;

@Service
public class MaterialStockInServiceImp extends BaseService {
    @Autowired
    private MaterialStockInDao entityDao;

    public void setMaterialStockInDao(MaterialStockInDao entityDao) {
        this.entityDao = entityDao;
    }
    
    /**
     * id 获取
     * @param id
     * @return
     */
    public MaterialStockIn getById(Long id){
    	return entityDao.get(id);
    }
    /**
     * 查询
     * @param map
     * @return
     */
    public List<MaterialStockIn> query(Map<String,Object> map){
    	return this.entityDao.query(map);
    }
    /**
     * 添加
     * @param material
     */
    public void insert(MaterialStockIn materialStockIn){
    	entityDao.insert(materialStockIn);
    }
    /**
     * 更新
     * @param material
     */
    public void update(MaterialStockIn materialStockIn){
    	entityDao.update(materialStockIn);
    }
    /**
     * 删除
     * @param material
     */
    public void remove(MaterialStockIn materialStockIn){
    	entityDao.remove(materialStockIn);
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