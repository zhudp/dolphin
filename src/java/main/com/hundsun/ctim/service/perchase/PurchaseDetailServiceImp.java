/**
 * 
 */
package com.hundsun.ctim.service.perchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.purchase.PurchaseDao;
import com.hundsun.ctim.dao.purchase.PurchaseDetailDao;
import com.hundsun.ctim.dao.purchase.PurchaseTaskDao;
import com.hundsun.ctim.domain.purchase.Purchase;
import com.hundsun.ctim.domain.purchase.PurchaseDetail;
import com.hundsun.ctim.domain.purchase.PurchaseTask;
import com.hundsun.ctim.service.SerialNoServiceImp;
@Service
@Transactional
public class PurchaseDetailServiceImp extends BaseService {

    @Autowired
    private PurchaseDetailDao entityDao;
    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private PurchaseTaskDao purchaseTaskDao;
    @Autowired
	private SerialNoServiceImp serialNoService;

    public void setPurchaseTaskDao(PurchaseTaskDao purchaseTaskDao) {
		this.purchaseTaskDao = purchaseTaskDao;
	}

	public void setSerialNoService(SerialNoServiceImp serialNoService) {
		this.serialNoService = serialNoService;
	}

	public void setPurchaseDetailDao(PurchaseDetailDao entityDao) {
        this.entityDao = entityDao;
    }

	public void setPurchaseDao(PurchaseDao purchaseDao) {
		this.purchaseDao = purchaseDao;
	}

	/**
     * id 获取
     * @param id
     * @return
     */
    public PurchaseDetail getById(Long id){
    	return entityDao.get(id);
    }
    /**
     * 查询
     * @param map
     * @return
     */
    public List<PurchaseDetail> query(Map<String,Object> map){
    	return this.entityDao.query(map);
    }
    /**
     * 根据采购任务批量插入采购以及明细
     * 1、list对供应商进行分组
     * 2、每一组插入一个采购
     * 3、相同供应的采购明细和对于采购关联
     * 4、插入明细
     * @param purchase
     * @param list
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void insertByTask(Purchase purchase,List<PurchaseDetail> list){
    	
    	Map<Long,List<PurchaseDetail>> surpplierMap = new HashMap<Long,List<PurchaseDetail>>();
    	//分组
    	for(PurchaseDetail detail:list){
    		Long surpplierId = detail.getSupplierId();
    		if(null == surpplierMap.get(surpplierId)){
    			surpplierMap.put(surpplierId, new ArrayList<PurchaseDetail>());
    		}
    		surpplierMap.get(surpplierId).add(detail);
    	}
    	//插入
    	for(Entry<Long,List<PurchaseDetail>> entry:surpplierMap.entrySet()){
    		
    		Purchase p = new Purchase();
    		List<PurchaseDetail> detailList = entry.getValue();
    		p.setOfficer(purchase.getOfficer());
    		p.setSupplierName(detailList.get(0).getSupplierName());
    		p.setOrderDate(purchase.getOrderDate());
    		p.setRealStoreinDate(purchase.getRealStoreinDate());
    		p.setStatus("dcg");//带采购
    		String no = null;
    		if(null == entry.getKey()){
    			p.setPurchaseNo("2");//无供应商的采购认为是临时采购
    			no = serialNoService.getNo("CG-"+serialNoService.getDate());
    		}else{
    			p.setPurchaseNo("1");// 定向采购
    			p.setSupplierId(entry.getKey());
    			no = serialNoService.getNo("CG-"+p.getSupplierId()+"-"+serialNoService.getDate());
    		}
    		p.setPurchaseNo(no);
    		purchaseDao.insert(p);
			Map map = new HashMap();
    		map.put("purchaseNo", no);
    		Long id = purchaseDao.query(map).get(0).getPurchaseId();
    		for(PurchaseDetail detail:detailList){
    			detail.setPurchaseId(id);
    		}
    		entityDao.insertBatch(detailList);
    	}
    	// 更新采购任务状态为采购中
    	for(PurchaseDetail detail:list){
    		Long taskId = detail.getTaskId();
    		PurchaseTask task =purchaseTaskDao.get(taskId);
    		task.setStatus("2");
    		purchaseTaskDao.update(task);
    	}
    }
    /**
     * 添加
     * @param PurchaseDetail
     */
    public void insert(PurchaseDetail purchaseDetail){
    	entityDao.insert(purchaseDetail);
    }
    /**
     * 更新
     * @param PurchaseDetail
     */
    public void update(PurchaseDetail purchaseDetail){
    	entityDao.update(purchaseDetail);
    }
    /**
     * 删除
     * @param PurchaseDetail
     */
    public void remove(PurchaseDetail purchaseDetail){
    	entityDao.remove(purchaseDetail);
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
