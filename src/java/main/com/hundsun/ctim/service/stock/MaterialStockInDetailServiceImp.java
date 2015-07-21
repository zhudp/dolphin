package com.hundsun.ctim.service.stock;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.stock.MaterialStockInDetailDao;
import com.hundsun.ctim.domain.stock.MaterialStockInDetail;

@Service
@Transactional
public class MaterialStockInDetailServiceImp extends BaseService {
    @Autowired
    private MaterialStockInDetailDao entityDao;
    @Autowired
    private MaterialStockServiceImp materialStockServiceImp;
    public void setMaterialStockServiceImp(
			MaterialStockServiceImp materialStockServiceImp) {
		this.materialStockServiceImp = materialStockServiceImp;
	}
	public void setMaterialStockInDetailDao(MaterialStockInDetailDao entityDao) {
        this.entityDao = entityDao;
    }
    public MaterialStockInDetail getById(Long id){
    	return entityDao.get(id);
    }
    public List<MaterialStockInDetail> query(Map<String,Object> map){
    	return this.entityDao.query(map);
    }
    public void insert(MaterialStockInDetail materialStockInDetail){
    	entityDao.insert(materialStockInDetail);
    	// update materialStock
    	materialStockServiceImp.addStock(materialStockInDetail.getMaterialId(), materialStockInDetail.getRealQuantity());
    }
    public void update(MaterialStockInDetail materialStockInDetail){
    	// update materialStock
    	MaterialStockInDetail oldDetail = entityDao.get(materialStockInDetail.getDetailId());
    	long q =0;
    	if(null !=oldDetail && oldDetail.getIsDeleted().equals("") && materialStockInDetail.getIsDeleted().equals("")){//逻辑删除
    		q = oldDetail.getRealQuantity();
    	}else if(null !=oldDetail){
    		q = materialStockInDetail.getRealQuantity()-oldDetail.getRealQuantity();
    	}
    	if(q!=0){
    		materialStockServiceImp.addStock(oldDetail.getMaterialId(), q);
    	}
    	entityDao.update(materialStockInDetail);
    }
    public void remove(MaterialStockInDetail materialStockInDetail){
    	// update materialStock
    	MaterialStockInDetail oldDetail = entityDao.get(materialStockInDetail.getDetailId());
    	
    	long q = 0l;
    	if(null !=oldDetail){
    		q=oldDetail.getRealQuantity();
    	}
    	if(q!=0){
    		materialStockServiceImp.reduceStock(oldDetail.getMaterialId(), q);
    	}
    	entityDao.remove(materialStockInDetail);
    }
    public void removeById(Long id){
    	// update materialStock
    	MaterialStockInDetail oldDetail = entityDao.get(id);
    	
    	long q = 0l;
    	if(null !=oldDetail){
    		q=oldDetail.getRealQuantity();
    	}
    	if(q!=0){
    		materialStockServiceImp.reduceStock(oldDetail.getMaterialId(), q);
    	}
    	entityDao.removeById(id);
    }
	public Page queryPaged(Map<String, String> paramsMap){
		return entityDao.queryPaged(paramsMap);
	}
}