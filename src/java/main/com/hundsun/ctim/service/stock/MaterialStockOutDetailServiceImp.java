package com.hundsun.ctim.service.stock;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.stock.MaterialStockOutDetailDao;
import com.hundsun.ctim.domain.stock.MaterialStockOutDetail;

@Service
@Transactional
public class MaterialStockOutDetailServiceImp extends BaseService {
    @Autowired
    private MaterialStockOutDetailDao entityDao;
    @Autowired
    private MaterialStockServiceImp materialStockServiceImp;
    
    public void setmaterialStockOutDetailDao(MaterialStockOutDetailDao entityDao) {
        this.entityDao = entityDao;
    }
    public void setMaterialStockServiceImp(
			MaterialStockServiceImp materialStockServiceImp) {
		this.materialStockServiceImp = materialStockServiceImp;
	}
	public MaterialStockOutDetail getById(Long id){
    	return entityDao.get(id);
    }
    public List<MaterialStockOutDetail> query(Map<String,Object> map){
    	return this.entityDao.query(map);
    }
    public void insert(MaterialStockOutDetail materialStockOutDetail){
    	entityDao.insert(materialStockOutDetail);
    	//update materialStock
    	materialStockServiceImp.reduceStock(materialStockOutDetail.getMaterialId(), materialStockOutDetail.getQuantity());
    	
    }
    public void update(MaterialStockOutDetail materialStockOutDetail){
    	//update materialStock
    	MaterialStockOutDetail oldDetail = entityDao.get(materialStockOutDetail.getDetailId());
    	long difference = 0;
    	if(null != oldDetail && oldDetail.getIsDeleted().equals("0") && !materialStockOutDetail.getIsDeleted().equals("0")){
    		difference =oldDetail.getQuantity();
    	}else if(null != oldDetail){
    		difference =oldDetail.getQuantity()-materialStockOutDetail.getQuantity();
    	}
    	if(difference!=0){
    		materialStockServiceImp.addStock(oldDetail.getMaterialId(), difference);
    	}
    	entityDao.update(materialStockOutDetail);
    }
    public void remove(MaterialStockOutDetail materialStockOutDetail){
    	//update materialStock
    	MaterialStockOutDetail oldDetail = entityDao.get(materialStockOutDetail.getDetailId());
    	materialStockServiceImp.reduceStock(oldDetail.getMaterialId(), oldDetail.getQuantity());
    	entityDao.remove(materialStockOutDetail);
    }
    public void removeById(Long id){
    	//update materialStock
    	MaterialStockOutDetail oldDetail = entityDao.get(id);
    	materialStockServiceImp.reduceStock(oldDetail.getMaterialId(), oldDetail.getQuantity());
    	entityDao.removeById(id);
    }
	public Page queryPaged(Map<String, String> paramsMap){
		return entityDao.queryPaged(paramsMap);
	}
}