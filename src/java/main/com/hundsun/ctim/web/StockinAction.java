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
import com.dolphin.service.DatadictService;
import com.hundsun.ctim.Params;
import com.hundsun.ctim.domain.stock.MaterialStockIn;
import com.hundsun.ctim.domain.stock.MaterialStockInDetail;
import com.hundsun.ctim.service.stock.MaterialStockInDetailServiceImp;
import com.hundsun.ctim.service.stock.MaterialStockInServiceImp;

/**
 * 原料入库管理
 *
 */
@SuppressWarnings("serial")
public class StockinAction extends StrutsAction {
	@Autowired
	private MaterialStockInServiceImp materialStockInService;
	@Autowired
	private MaterialStockInDetailServiceImp materialStockInDetailService;
	@Autowired
	private DatadictService datadictService;

	public void setDatadictService(DatadictService datadictService) {
		this.datadictService = datadictService;
	}
	public void setMaterialStockInService(MaterialStockInServiceImp materialStockInService){
		this.materialStockInService = materialStockInService;
	}
	public void setMaterialStockInDetailService(MaterialStockInDetailServiceImp materialStockInDetailService){
		this.materialStockInDetailService =materialStockInDetailService;
	}
	
	public void queryPaged() throws Exception {
		Map<String, String> paramMap = bindMap();
		Page page = materialStockInService.queryPaged(paramMap);
		printJson(JsonUtils.bean2Json(page));
	}
	
	public void getMaterialStockIn() throws Exception{
		
		String stockInId = this.getRequest().getParameter("stockInId");
		MaterialStockIn materialStockIn = materialStockInService.getById(Long.valueOf(stockInId));
		
		printJson(messageSuccuseWrap(JsonUtils.bean2Json(materialStockIn)));
	}
	public void saveMaterialStockIn() throws Exception{
		
		MaterialStockIn materialStockIn = bindEntity(MaterialStockIn.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		materialStockIn.setGmtModify(now);
		materialStockIn.setModifier(user.getUserName());
		materialStockIn.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(materialStockIn.getStockInId() == null){
			materialStockIn.setGmtCreate(now);
			materialStockIn.setCreator(user.getUserName());
			materialStockIn.setCreatorId(Long.valueOf(user.getId()));
			materialStockInService.insert(materialStockIn);
		}
		// 更新
		else{
			materialStockInService.update(materialStockIn);
		}
		printText(messageSuccuseWrap());
	}
	
	public void delete() throws Exception {
		String strmaterialId = this.getRequest().getParameter("stockInId");
		if(strmaterialId == null) {
			printText(messageFailureWrap("该入库已被删除，请刷新后再试！"));
			return;
		}
		Long materialStockInId = Long.valueOf(strmaterialId);
		
		MaterialStockIn materialStockIn = materialStockInService.getById(materialStockInId);
		if(materialStockIn == null) {
			printText(messageFailureWrap("该入库信息已被删除，请刷新后再试！"));
			return;
		}
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		materialStockIn.setGmtModify(now);
		materialStockIn.setModifier(user.getUserName());
		materialStockIn.setModifierId(Long.valueOf(user.getId()));
		// 删除
		materialStockIn.setIsDeleted(Params.STATUS_ONE);
		materialStockInService.update(materialStockIn);
		printText(messageSuccuseWrap());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportExcel() throws Exception {
		String stockInString =  this.getRequest().getParameter("stockInId");
		if(!StringUtils.isNumeric(stockInString)){
			throw new Exception("没有入库id或类型错误！请传入long类型的入库id");
		}
		MaterialStockIn  materialStockIn  = materialStockInService.getById(Long.valueOf(stockInString));
		Map<String, String> paramMap = bindMap();
		paramMap.put("start", "0");
		paramMap.put("limit", "99999");
		paramMap.put("stockInId", String.valueOf(materialStockIn.getStockInId()));
		Page page = materialStockInDetailService.queryPaged(paramMap);
		Map<String, String> materialTypeMap = datadictService.getDatadictMap("MATERIAL_TYPE");
		Map dataMap = new HashMap();
		dataMap.put("stockIn", materialStockIn);
		dataMap.put("stockInDetailList", page.getData());
		dataMap.put("materialTypeMap", materialTypeMap);
		return super.exportExcel("materialIn", "原料入库单", dataMap);
	}
	
	public void detailQueryPaged() throws Exception {
		Map<String, String> paramMap = bindMap();
		Page page = materialStockInDetailService.queryPaged(paramMap);
		printJson(JsonUtils.bean2Json(page));
	}
	
	public void getMaterialStockInDetail() throws Exception{
		
		String detailId = this.getRequest().getParameter("detailId");
		MaterialStockInDetail materialStockInDetail = materialStockInDetailService.getById(Long.valueOf(detailId));
		
		printJson(messageSuccuseWrap(JsonUtils.bean2Json(materialStockInDetail)));
	}
	public void saveMaterialStockInDetail() throws Exception{
		
		MaterialStockInDetail materialStockInDetail = bindEntity(MaterialStockInDetail.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		materialStockInDetail.setGmtModify(now);
		materialStockInDetail.setModifier(user.getUserName());
		materialStockInDetail.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(materialStockInDetail.getDetailId() == null){
			materialStockInDetail.setGmtCreate(now);
			materialStockInDetail.setCreator(user.getUserName());
			materialStockInDetail.setCreatorId(Long.valueOf(user.getId()));
			materialStockInDetailService.insert(materialStockInDetail);
		}
		// 更新
		else{
			materialStockInDetailService.update(materialStockInDetail);
		}
		printText(messageSuccuseWrap());
	}
	
	public void deleteDetail() throws Exception {
		String detailIdString = this.getRequest().getParameter("detailId");
		if(detailIdString == null) {
			printText(messageFailureWrap("该入库已被删除，请刷新后再试！"));
			return;
		}
		Long detailId = Long.valueOf(detailIdString);
		
		MaterialStockInDetail materialStockInDetail = materialStockInDetailService.getById(detailId);
		if(materialStockInDetail == null) {
			printText(messageFailureWrap("该入库信息已被删除，请刷新后再试！"));
			return;
		}
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		materialStockInDetail.setGmtModify(now);
		materialStockInDetail.setModifier(user.getUserName());
		materialStockInDetail.setModifierId(Long.valueOf(user.getId()));
		// 删除
		materialStockInDetail.setIsDeleted(Params.STATUS_ONE);
		materialStockInDetailService.update(materialStockInDetail);
		printText(messageSuccuseWrap());
	}
}
