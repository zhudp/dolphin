package com.hundsun.ctim.service.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.order.OrderDao;
import com.hundsun.ctim.dao.order.OrderDetailDao;
import com.hundsun.ctim.domain.order.Order;
import com.hundsun.ctim.domain.order.OrderDetail;

@Service
public class OrderServiceImp extends BaseService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderDetailDao orderDetailDao;
    
    /**
     * 根据ID获取订单信息
     * @param id
     * @return
     */
	public Order getById(Long id) {
		return orderDao.get(id);
	}
	
	/**
	 * 列表查询
	 */
	public List<Order> query(Map<String, Object> paramsMap) {
		return orderDao.query(paramsMap);
	}
	
	/**
	 * 分页查询
	 */
	public Page queryPaged(Map<String, String> paramsMap){
		return orderDao.queryPaged(paramsMap);
	}
	
	
    /**
     * 新增订单信息
     * @return
     */
	public void insert(Order cutom) {
		orderDao.insert(cutom);
	}
	
	/**
	 * 更新订单信息
	 * @return
	 */
	public void update(Order cutom) {
		orderDao.update(cutom);
	}
	/**
	 * 更新订单信息
	 * @return
	 */
	public void updateSelective(Order cutom) {
		orderDao.updateSelective(cutom);
	}
	
	/**
	 * 根据ID删除
	 */
	public void removeById(Long customId) {
		orderDao.removeById(customId);
	}
	
	
	///////////-------------订单详细信息-----------///////////////
	/**
	 * 分页查询详情记录
	 */
	public Page queryPagedDetail(Map<String, String> paramsMap){
		return orderDetailDao.queryPaged(paramsMap);
	}
	
	/**
	 * 根据ID获取订单信息
	 * @param id
	 * @return
	 */
	public List<OrderDetail> queryDetail(Map<String, String> paramsMap) {
		return orderDetailDao.query(paramsMap);
	}
	
    /**
     * 根据ID获取订单详情信息
     * @param id
     * @return
     */
	public OrderDetail getDetailById(Long id) {
		return orderDetailDao.get(id);
	}
	
    /**
     * 新增订单详情信息
     * @return
     */
	public void insert(OrderDetail detail) {
		orderDetailDao.insert(detail);
	}
	
	/**
	 * 更新订单详情信息
	 * @return
	 */
	public void updateDetailSelective(OrderDetail detail) {
		orderDetailDao.updateSelective(detail);
	}
	
	/**
	 * 批量保存订单详情
	 * @param list4Insert
	 * @param list4Update
	 */
	public void saveDetailBatch(Order order, List<OrderDetail> list4Insert, List<OrderDetail> list4Update){
		orderDetailDao.insertBatch(list4Insert);
		orderDetailDao.updateSelectiveBatch(list4Update);
		orderDao.updateSelective(order);
	}
	
	/**
	 * 根据ID删除订单详情信息
	 */
	public void removeDetailById(Long detailId) {
		orderDetailDao.removeById(detailId);
	}

}