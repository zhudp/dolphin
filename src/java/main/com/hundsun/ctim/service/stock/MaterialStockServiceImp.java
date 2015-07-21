package com.hundsun.ctim.service.stock;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.stock.MaterialStockDao;
import com.hundsun.ctim.domain.stock.MaterialStock;

@Service
@Transactional
public class MaterialStockServiceImp extends BaseService {
    @Autowired
    private MaterialStockDao entityDao;

    public void setMaterialStockDao(MaterialStockDao entityDao) {
        this.entityDao = entityDao;
    }
    public MaterialStock getById(Long id){
    	return entityDao.get(id);
    }
    public List<MaterialStock> query(Map<String,Object> map){
    	return this.entityDao.query(map);
    }
    public void insert(MaterialStock materialStock){
    	entityDao.insert(materialStock);
    }
    public void update(MaterialStock materialStock){
    	entityDao.update(materialStock);
    }
    public void remove(MaterialStock materialStock){
    	entityDao.remove(materialStock);
    }
    public void removeById(Long id){
    	entityDao.removeById(id);
    }
	public Page queryPaged(Map<String, String> paramsMap){
		return entityDao.queryPaged(paramsMap);
	}
	/**
	 * count 可以为负数据，等同于减
	 * @param materialId
	 * @param count
	 * @return
	 */
	public long addStock(Long materialId,Long count){
		MaterialStock materialStock = entityDao.get(materialId);
		if(null ==materialStock){
			MaterialStock newMaterialStock = new MaterialStock();
			newMaterialStock.setMaterialId(materialId);
			newMaterialStock.setStock(0l);
			entityDao.insert(newMaterialStock);
			materialStock = newMaterialStock;
		}
		long q = materialStock.getStock()+count;
		materialStock.setStock(q);
		entityDao.update(materialStock);
		return q;
	}
	public long addStock(Long materialId){
		return this.addStock(materialId, 1l);
	}
	public long reduceStock(Long materialId,Long count){
		MaterialStock materialStock = entityDao.get(materialId);
		if(null ==materialStock){
			MaterialStock newMaterialStock = new MaterialStock();
			newMaterialStock.setMaterialId(materialId);
			newMaterialStock.setStock(0l);
			entityDao.insert(newMaterialStock);
			materialStock = newMaterialStock;
		}
		long q = materialStock.getStock()-count;
		materialStock.setStock(q);
		entityDao.update(materialStock);
		return q;
	}
	public long reduceStock(Long materialId){
		return this.reduceStock(materialId,1l);
	}
}