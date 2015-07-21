package com.hundsun.ctim.service.material;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.material.MaterialDao;
import com.hundsun.ctim.domain.material.Material;

@Service
public class MaterialServiceImp extends BaseService {
    @Autowired
    private MaterialDao entityDao;

    public void setMaterialDao(MaterialDao entityDao) {
        this.entityDao = entityDao;
    }
    /**
     * id 获取
     * @param id
     * @return
     */
    public Material getById(Long id){
    	return entityDao.get(id);
    }
    /**
     * 查询
     * @param map
     * @return
     */
    public List<Material> query(Map<String,Object> map){
    	return this.entityDao.query(map);
    }
    /**
     * 添加
     * @param material
     */
    public void insert(Material material){
    	entityDao.insert(material);
    }
    /**
     * 更新
     * @param material
     */
    public void update(Material material){
    	entityDao.update(material);
    }
    /**
     * 删除
     * @param material
     */
    public void remove(Material material){
    	entityDao.remove(material);
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