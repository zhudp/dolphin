package com.hundsun.ctim.web;

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
import com.hundsun.ctim.domain.order.OrderDetail;
import com.hundsun.ctim.domain.produce.ProduceTask;
import com.hundsun.ctim.domain.produce.ProduceTaskDetail;
import com.hundsun.ctim.service.order.OrderServiceImp;
import com.hundsun.ctim.service.produce.ProduceTaskServiceImp;

/**
 * 生产任务管理
 * 
 */
@SuppressWarnings("serial")
public class ProduceAction extends StrutsAction {
	
	@Autowired
	private ProduceTaskServiceImp produceTaskService;
	@Autowired
	private OrderServiceImp orderService;

	/**
	 * 生产任务查询
	 */
	public void queryPaged() throws Exception {
		
		Map<String, String> paramMap = bindMap();
		Page page = produceTaskService.queryPaged(paramMap);

		printJson(JsonUtils.bean2Json(page));
	}
	
	/**
	 * 获取生产任务信息
	 */
	public void getProduceTask() throws Exception{
		
		String taskId = this.getRequest().getParameter("taskId");
		ProduceTask produceTask = produceTaskService.getById(Long.valueOf(taskId));
		
		printJson(messageSuccuseWrapObj(JsonUtils.bean2Json(produceTask)));
	}
	

	/**
	 * 保存生产任务信息
	 * @throws Exception
	 */
	public void saveProduceTask() throws Exception{
		
		ProduceTask produceTask = bindEntity(ProduceTask.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		produceTask.setGmtModify(now);
		produceTask.setModifier(user.getUserName());
		produceTask.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(produceTask.getTaskId() == null){
			Map<String, String> map = new HashMap<String, String>();
			map.put("orderId",produceTask.getOrderId().toString());
			
			List<ProduceTask> taskList = produceTaskService.query(map);
			
			if(taskList != null && taskList.size() > 0) {
				printText(messageFailureWrap("该订单已经被安排生产任务，请不要重复安排！"));
				return;
			}
			
			produceTask.setGmtCreate(now);
			produceTask.setCreator(user.getUserName());
			produceTask.setCreatorId(Long.valueOf(user.getId()));
			Long taskId = produceTaskService.insert(produceTask);
			
			// 订单批量插入生产任务明细
			List<OrderDetail> orderDetailList = orderService.queryDetail(map);
			List<ProduceTaskDetail> produceTaskDetailList = new ArrayList<ProduceTaskDetail>();
			for (OrderDetail orderDetail : orderDetailList) {
				
				ProduceTaskDetail produceTaskDetail = new ProduceTaskDetail();
				produceTaskDetail.setTaskId(taskId);
				produceTaskDetail.setProductId(orderDetail.getProductId());
				produceTaskDetail.setPlanNumber(orderDetail.getProductNum());
				produceTaskDetail.setRemark(orderDetail.getRemark());
				produceTaskDetailList.add(produceTaskDetail);
			}
			
			produceTaskService.insertDetailBatch(produceTaskDetailList);
		}
		
		// 更新
		else{
			produceTaskService.updateSelective(produceTask);
		}
		
		printText(messageSuccuseWrap());
	}
	
	/**
	 * 删除生产任务信息
	 */
	public void remove() throws Exception {
		String strTaskId = this.getRequest().getParameter("taskId");
		ProduceTask produceTask = produceTaskService.getById(Long.valueOf(strTaskId));
		if(produceTask == null) {
			printText(messageFailureWrap("该生产任务信息已被删除，请刷新后再试！"));
			return;
		}
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		produceTask.setGmtModify(now);
		produceTask.setModifier(user.getUserName());
		produceTask.setModifierId(Long.valueOf(user.getId()));
		// 删除
		produceTask.setIsDeleted(Params.STATUS_ONE);
		produceTaskService.update(produceTask);
		
		printText(messageSuccuseWrap());
	}
	
	
	/**
	 * 查询生产任务详情记录
	 */
	public void queryPagedDetail() throws Exception {
		Map<String, String> paramMap = bindMap();
		List<ProduceTaskDetail> list = produceTaskService.queryDetail(paramMap);

		printJson(JsonUtils.bean2JsonArray(list));
	}
	
	/**
	 * 获取生产任务详情信息
	 */
	public void getProduceTaskDetail() throws Exception{
		
		String detailId = this.getRequest().getParameter("detailId");
		ProduceTaskDetail detail = produceTaskService.getDetailById(Long.valueOf(detailId));
		
		printJson(messageSuccuseWrapObj(JsonUtils.bean2Json(detail)));
	}
	
	/**
	 * 导出生产任务详情
	 * @throws Exception
	 */
	public String exportProduceTask() throws Exception {
		String produceTaskId = this.getRequest().getParameter("taskId");
		ProduceTask produceTask = produceTaskService.getById(Long.valueOf(produceTaskId));
		Map<String, String> paramMap = bindMap();
		List<ProduceTaskDetail> detailList = produceTaskService.queryDetail(paramMap);
		
		Map<String, Object> viewMap = new HashMap<String, Object>();
		viewMap.put("produceTask", produceTask);
		viewMap.put("detailList", detailList);
		
		return super.exportExcel("produceTask", "生产任务信息_", viewMap);
	}
	
	/**
	 * 保存生产任务详情记录
	 */
	public void saveProduceTaskDetail() throws Exception {
		
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		
		List<ProduceTaskDetail> detailList = bindEntityList(ProduceTaskDetail.class);
		
		List<ProduceTaskDetail> list4Insert = new ArrayList<ProduceTaskDetail>();
		List<ProduceTaskDetail> list4Update = new ArrayList<ProduceTaskDetail>();
		
		ProduceTask produceTask = null;
		for (ProduceTaskDetail produceTaskDetail : detailList) {
			
			if(produceTask == null) {
				produceTask = produceTaskService.getById(produceTaskDetail.getTaskId());
			}
			if(produceTaskDetail.getDetailId() == null) {
				list4Insert.add(produceTaskDetail);
			}else {
				list4Update.add(produceTaskDetail);
			}
			
		}
		produceTask.setModifier(user.getUserName());
		produceTask.setModifierId(Long.valueOf(user.getId()));
		produceTask.setGmtModify(now);
		produceTaskService.saveDetailBatch(produceTask, list4Insert, list4Update);
		printText(messageSuccuseWrap());
	}
	
	/**
	 * 删除生产任务详情信息
	 */
	public void removeDetail() throws Exception {
		String strDetailId = this.getRequest().getParameter("detailId");
		// 删除
		produceTaskService.removeDetailById(Long.valueOf(strDetailId));
		printText(messageSuccuseWrap());
	}

}
