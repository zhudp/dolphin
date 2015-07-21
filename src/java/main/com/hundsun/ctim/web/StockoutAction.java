package com.hundsun.ctim.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.core.dao.support.Page;
import com.core.utils.DateUtils;
import com.core.utils.JsonUtils;
import com.core.web.StrutsAction;
import com.dolphin.domain.RemoteUser;
import com.dolphin.domain.User;
import com.hundsun.ctim.Params;
import com.hundsun.ctim.domain.stock.MaterialStockOut;
import com.hundsun.ctim.domain.stock.MaterialStockOutDetail;
import com.hundsun.ctim.service.stock.MaterialStockOutDetailServiceImp;
import com.hundsun.ctim.service.stock.MaterialStockOutServiceImp;
@SuppressWarnings("serial")
public class StockoutAction extends StrutsAction {
	@Autowired
	private MaterialStockOutServiceImp materialStockOutService;
	@Autowired
	private MaterialStockOutDetailServiceImp materialStockOutDetailService;

	public void setMaterialStockOutService(MaterialStockOutServiceImp materialStockOutService){
		this.materialStockOutService = materialStockOutService;
	}
	public void setMaterialStockOutDetailService(MaterialStockOutDetailServiceImp materialStockOutDetailService){
		this.materialStockOutDetailService =materialStockOutDetailService;
	}
	
	public void queryPaged() throws Exception {
		Map<String, String> paramMap = bindMap();
		Page page = materialStockOutService.queryPaged(paramMap);
		printJson(JsonUtils.bean2Json(page));
	}
	
	public void getMaterialStockOut() throws Exception{
		
		String stockOutId = this.getRequest().getParameter("stockOutId");
		MaterialStockOut materialStockOut = materialStockOutService.getById(Long.valueOf(stockOutId));
		
		printJson(messageSuccuseWrap(JsonUtils.bean2Json(materialStockOut)));
	}
	public void saveMaterialStockOut() throws Exception{
		
		MaterialStockOut materialStockOut = bindEntity(MaterialStockOut.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		materialStockOut.setGmtModify(now);
		materialStockOut.setModifier(user.getUserName());
		materialStockOut.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(materialStockOut.getStockOutId() == null){
			materialStockOut.setGmtCreate(now);
			materialStockOut.setCreator(user.getUserName());
			materialStockOut.setCreatorId(Long.valueOf(user.getId()));
			materialStockOutService.insert(materialStockOut);
		}
		// 更新
		else{
			materialStockOutService.update(materialStockOut);
		}
		printText(messageSuccuseWrap());
	}
	
	public void delete() throws Exception {
		String strmaterialId = this.getRequest().getParameter("stockOutId");
		if(strmaterialId == null) {
			printText(messageFailureWrap("该出库已被删除，请刷新后再试！"));
			return;
		}
		Long materialStockOutId = Long.valueOf(strmaterialId);
		
		MaterialStockOut materialStockOut = materialStockOutService.getById(materialStockOutId);
		if(materialStockOut == null) {
			printText(messageFailureWrap("该出库信息已被删除，请刷新后再试！"));
			return;
		}
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		materialStockOut.setGmtModify(now);
		materialStockOut.setModifier(user.getUserName());
		materialStockOut.setModifierId(Long.valueOf(user.getId()));
		// 删除
		materialStockOut.setIsDeleted(Params.STATUS_ONE);
		materialStockOutService.update(materialStockOut);
		printText(messageSuccuseWrap());
	}
	
	// materialStockOutDetail
	
	public void detailQueryPaged() throws Exception {
		Map<String, String> paramMap = bindMap();
		Page page = materialStockOutDetailService.queryPaged(paramMap);
		printJson(JsonUtils.bean2Json(page));
	}
	
	public void getMaterialStockOutDetail() throws Exception{
		
		String detailId = this.getRequest().getParameter("detailId");
		MaterialStockOutDetail materialStockOutDetail = materialStockOutDetailService.getById(Long.valueOf(detailId));
		
		printJson(messageSuccuseWrap(JsonUtils.bean2Json(materialStockOutDetail)));
	}
	public void saveMaterialStockOutDetail() throws Exception{
		
		MaterialStockOutDetail materialStockOutDetail = bindEntity(MaterialStockOutDetail.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		materialStockOutDetail.setGmtModify(now);
		materialStockOutDetail.setModifier(user.getUserName());
		materialStockOutDetail.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(materialStockOutDetail.getDetailId() == null){
			materialStockOutDetail.setGmtCreate(now);
			materialStockOutDetail.setCreator(user.getUserName());
			materialStockOutDetail.setCreatorId(Long.valueOf(user.getId()));
			materialStockOutDetailService.insert(materialStockOutDetail);
		}
		// 更新
		else{
			materialStockOutDetailService.update(materialStockOutDetail);
		}
		printText(messageSuccuseWrap());
	}
	
	public void deleteDetail() throws Exception {
		String detailIdString = this.getRequest().getParameter("detailId");
		if(detailIdString == null) {
			printText(messageFailureWrap("该出库已被删除，请刷新后再试！"));
			return;
		}
		Long detailId = Long.valueOf(detailIdString);
		
		MaterialStockOutDetail materialStockOutDetail = materialStockOutDetailService.getById(detailId);
		if(materialStockOutDetail == null) {
			printText(messageFailureWrap("该出库信息已被删除，请刷新后再试！"));
			return;
		}
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		materialStockOutDetail.setGmtModify(now);
		materialStockOutDetail.setModifier(user.getUserName());
		materialStockOutDetail.setModifierId(Long.valueOf(user.getId()));
		// 删除
		materialStockOutDetail.setIsDeleted(Params.STATUS_ONE);
		materialStockOutDetailService.update(materialStockOutDetail);
		printText(messageSuccuseWrap());
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportExcel() throws Exception {
		String stockOutString =  this.getRequest().getParameter("stockOutId");
		if(!StringUtils.isNumeric(stockOutString)){
			throw new Exception("没有出库id或类型错误！请传入long类型的出库id");
		}
		MaterialStockOut  materialStockOut  = materialStockOutService.getById(Long.valueOf(stockOutString));
		Map<String, String> paramMap = bindMap();
		paramMap.put("start", "0");
		paramMap.put("limit", "99999");
		paramMap.put("stockOutId", String.valueOf(materialStockOut.getStockOutId()));
		Page page = materialStockOutDetailService.queryPaged(paramMap);
		Map dataMap = new HashMap();
		dataMap.put("stockOut", materialStockOut);
		dataMap.put("stockOutDetailList", page.getData());
		return super.exportExcel("materialOut", "原料出库单", dataMap);
	}
}
