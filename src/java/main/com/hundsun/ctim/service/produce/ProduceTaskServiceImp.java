package com.hundsun.ctim.service.produce;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.produce.ProduceTaskDao;
import com.hundsun.ctim.dao.produce.ProduceTaskDetailDao;
import com.hundsun.ctim.domain.produce.ProduceTask;
import com.hundsun.ctim.domain.produce.ProduceTaskDetail;

@Service
public class ProduceTaskServiceImp extends BaseService {
    @Autowired
    private ProduceTaskDao produceTaskDao;
    @Autowired
    private ProduceTaskDetailDao produceTaskDetailDao;
    
    /**
     * 根据ID获取订单信息
     * @param id
     * @return
     */
	public ProduceTask getById(Long id) {
		return produceTaskDao.get(id);
	}
	
	/**
	 * 列表查询
	 */
	public List<ProduceTask> query(Map<String, String> paramsMap) {
		return produceTaskDao.query(paramsMap);
	}
	
	/**
	 * 分页查询
	 */
	public Page queryPaged(Map<String, String> paramsMap){
		return produceTaskDao.queryPaged(paramsMap);
	}
	
	
    /**
     * 新增订单信息
     * @return
     */
	public Long insert(ProduceTask object) {
		return (Long)produceTaskDao.insert(object);
	}
	
	/**
	 * 更新订单信息
	 * @return
	 */
	public void update(ProduceTask object) {
		produceTaskDao.update(object);
	}
	/**
	 * 更新订单信息
	 * @return
	 */
	public void updateSelective(ProduceTask object) {
		produceTaskDao.updateSelective(object);
	}
	
	/**
	 * 根据ID删除
	 */
	public void removeById(Long customId) {
		produceTaskDao.removeById(customId);
	}
	
	
	///////////-------------订单详细信息-----------///////////////
	/**
	 * 分页查询详情记录
	 */
	public Page queryPagedDetail(Map<String, String> paramsMap){
		return produceTaskDetailDao.queryPaged(paramsMap);
	}
	
	/**
	 * 根据ID获取订单信息
	 * @param id
	 * @return
	 */
	public List<ProduceTaskDetail> queryDetail(Map<String, String> paramsMap) {
		return produceTaskDetailDao.query(paramsMap);
	}
	
    /**
     * 根据ID获取订单信息
     * @param id
     * @return
     */
	public ProduceTaskDetail getDetailById(Long id) {
		return produceTaskDetailDao.get(id);
	}
	
    /**
     * 新增订单详情信息
     * @return
     */
	public void insert(ProduceTaskDetail detail) {
		produceTaskDetailDao.insert(detail);
	}
	
	/**
	 * 新增订单详情信息
	 * @return
	 */
	public void insertDetailBatch(List<ProduceTaskDetail> detailList) {
		produceTaskDetailDao.insertBatch(detailList);
	}
	
	/**
	 * 更新订单详情信息
	 * @return
	 */
	public void updateDetailSelective(ProduceTaskDetail detail) {
		produceTaskDetailDao.updateSelective(detail);
	}
	
	/**
	 * 批量保存订单详情
	 * @param list4Insert
	 * @param list4Update
	 */
	public void saveDetailBatch(ProduceTask produceTask, List<ProduceTaskDetail> list4Insert, List<ProduceTaskDetail> list4Update){
		produceTaskDetailDao.insertBatch(list4Insert);
		produceTaskDetailDao.updateSelectiveBatch(list4Update);
		produceTaskDao.updateSelective(produceTask);
	}
	
	/**
	 * 根据ID删除订单详情信息
	 */
	public void removeDetailById(Long detailId) {
		produceTaskDetailDao.removeById(detailId);
	}

}