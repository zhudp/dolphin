package com.hundsun.ctim.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.core.dao.support.Page;
import com.core.utils.DateUtils;
import com.core.utils.JsonUtils;
import com.core.web.StrutsAction;
import com.dolphin.domain.RemoteUser;
import com.dolphin.domain.User;
import com.hundsun.ctim.Params;
import com.hundsun.ctim.domain.order.Order;
import com.hundsun.ctim.domain.order.OrderDetail;
import com.hundsun.ctim.service.SerialNoServiceImp;
import com.hundsun.ctim.service.order.OrderServiceImp;

/**
 * 订单管理
 * 
 */
@SuppressWarnings("serial")
public class OrderAction extends StrutsAction {
	
	@Autowired
	private OrderServiceImp orderService;
	@Autowired
	private SerialNoServiceImp serialNoService;
	
	/**
	 * 订单查询
	 */
	public void queryPaged() throws Exception {
		
		Map<String, String> paramMap = bindMap();
		Page page = orderService.queryPaged(paramMap);

		printJson(JsonUtils.bean2Json(page));
	}
	
	/**
	 * 获取订单信息
	 */
	public void getOrder() throws Exception{
		
		String orderId = this.getRequest().getParameter("orderId");
		Order order = orderService.getById(Long.valueOf(orderId));
		
		printJson(messageSuccuseWrapObj(JsonUtils.bean2Json(order)));
	}
	

	/**
	 * 保存订单信息
	 * @throws Exception
	 */
	public void saveOrder() throws Exception{
		
		Order order = bindEntity(Order.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		order.setGmtModify(now);
		order.setModifier(user.getUserName());
		order.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(order.getOrderId() == null){
			
			String yyMM = DateUtils.toDateString(now, "yyMM");
			//TODO:订单编号生成
			String orderNo = serialNoService.getNo("DD-"+order.getCustomNo()+"-"+yyMM, 3, "");
			order.setOrderNo(orderNo);
			order.setGmtCreate(now);
			order.setCreator(user.getUserName());
			order.setCreatorId(Long.valueOf(user.getId()));
			orderService.insert(order);
		}
		
		// 更新
		else{
			orderService.updateSelective(order);
		}
		
		printText(messageSuccuseWrap());
	}
	
	/**
	 * 删除订单信息
	 */
	public void remove() throws Exception {
		String strOrderId = this.getRequest().getParameter("orderId");
		Order order = orderService.getById(Long.valueOf(strOrderId));
		if(order == null) {
			printText(messageFailureWrap("该订单信息已被删除，请刷新后再试！"));
			return;
		}
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		order.setGmtModify(now);
		order.setModifier(user.getUserName());
		order.setModifierId(Long.valueOf(user.getId()));
		// 删除
		order.setIsDeleted(Params.STATUS_ONE);
		orderService.update(order);
		
		printText(messageSuccuseWrap());
	}
	
	
	/**
	 * 查询订单详情记录
	 */
	public void queryPagedDetail() throws Exception {
		Map<String, String> paramMap = bindMap();
		List<OrderDetail> list = orderService.queryDetail(paramMap);

		printJson(JsonUtils.bean2JsonArray(list));
	}
	
	/**
	 * 获取订单详情信息
	 */
	public void getOrderDetail() throws Exception{
		
		String detailId = this.getRequest().getParameter("detailId");
		OrderDetail detail = orderService.getDetailById(Long.valueOf(detailId));
		
		printJson(messageSuccuseWrapObj(JsonUtils.bean2Json(detail)));
	}
	
	/**
	 * 导出订单详情
	 * @throws Exception
	 */
	public String exportOrderDetail() throws Exception {
		String orderId = this.getRequest().getParameter("orderId");
		Order order = orderService.getById(Long.valueOf(orderId));
		Map<String, String> paramMap = bindMap();
		List<OrderDetail> detailList = orderService.queryDetail(paramMap);
		
		Map<String, Object> viewMap = new HashMap<String, Object>();
		viewMap.put("order", order);
		viewMap.put("detailList", detailList);
		
		return super.exportExcel("orderDetail", "订单信息_"+order.getOrderNo(), viewMap);
	}
	
	/**
	 * 保存订单详情记录
	 */
	public void saveOrderDetail() throws Exception {
		
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		
		List<OrderDetail> detailList = bindEntityList(OrderDetail.class);
		
		List<OrderDetail> list4Insert = new ArrayList<OrderDetail>();
		List<OrderDetail> list4Update = new ArrayList<OrderDetail>();
		
		BigDecimal totalPrice = BigDecimal.ZERO;
		Order order = null;
		for (OrderDetail orderDetail : detailList) {
			
			if(order == null) {
				order = orderService.getById(orderDetail.getOrderId());
			}
			if(orderDetail.getDetailId() == null) {
				list4Insert.add(orderDetail);
			}else {
				list4Update.add(orderDetail);
			}
			
			totalPrice = totalPrice.add(orderDetail.getPrice().multiply(BigDecimal.valueOf(orderDetail.getProductNum())));
		}
		order.setOrderPrice(totalPrice);
		order.setModifier(user.getUserName());
		order.setModifierId(Long.valueOf(user.getId()));
		order.setGmtModify(now);
		orderService.saveDetailBatch(order, list4Insert, list4Update);
		printText(messageSuccuseWrap());
	}
	
	/**
	 * 删除订单详情信息
	 */
	public void removeDetail() throws Exception {
		String strDetailId = this.getRequest().getParameter("detailId");
		
		// 删除
		orderService.removeDetailById(Long.valueOf(strDetailId));
		
		printText(messageSuccuseWrap());
	}
}
