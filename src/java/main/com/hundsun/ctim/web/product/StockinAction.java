package com.hundsun.ctim.web.product;

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
import com.hundsun.ctim.domain.stock.ProductStockIn;
import com.hundsun.ctim.domain.stock.ProductStockInDetail;
import com.hundsun.ctim.service.stock.ProductStockInDetailServiceImp;
import com.hundsun.ctim.service.stock.ProductStockInServiceImp;

@SuppressWarnings("serial")
public class StockinAction extends StrutsAction {
	@Autowired
	private ProductStockInServiceImp productStockInService;
	@Autowired
	private ProductStockInDetailServiceImp productStockInDetailService;
	@Autowired
	private DatadictService datadictService;

	public void setDatadictService(DatadictService datadictService) {
		this.datadictService = datadictService;
	}
	public void setProductStockInService(ProductStockInServiceImp productStockInService){
		this.productStockInService = productStockInService;
	}
	public void setProductStockInDetailService(ProductStockInDetailServiceImp productStockInDetailService){
		this.productStockInDetailService =productStockInDetailService;
	}
	
	public void queryPaged() throws Exception {
		Map<String, String> paramMap = bindMap();
		Page page = productStockInService.queryPaged(paramMap);
		printJson(JsonUtils.bean2Json(page));
	}
	
	public void getProductStockIn() throws Exception{
		
		String stockInId = this.getRequest().getParameter("stockInId");
		ProductStockIn productStockIn = productStockInService.getById(Long.valueOf(stockInId));
		
		printJson(messageSuccuseWrap(JsonUtils.bean2Json(productStockIn)));
	}
	public void saveProductStockIn() throws Exception{
		
		ProductStockIn productStockIn = bindEntity(ProductStockIn.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		productStockIn.setGmtModify(now);
		productStockIn.setModifier(user.getUserName());
		productStockIn.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(productStockIn.getStockInId() == null){
			productStockIn.setGmtCreate(now);
			productStockIn.setCreator(user.getUserName());
			productStockIn.setCreatorId(Long.valueOf(user.getId()));
			productStockInService.insert(productStockIn);
		}
		// 更新
		else{
			productStockInService.update(productStockIn);
		}
		printText(messageSuccuseWrap());
	}
	
	public void delete() throws Exception {
		String strproductId = this.getRequest().getParameter("stockInId");
		if(strproductId == null) {
			printText(messageFailureWrap("该入库已被删除，请刷新后再试！"));
			return;
		}
		Long productStockInId = Long.valueOf(strproductId);
		
		ProductStockIn productStockIn = productStockInService.getById(productStockInId);
		if(productStockIn == null) {
			printText(messageFailureWrap("该入库信息已被删除，请刷新后再试！"));
			return;
		}
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		productStockIn.setGmtModify(now);
		productStockIn.setModifier(user.getUserName());
		productStockIn.setModifierId(Long.valueOf(user.getId()));
		// 删除
		productStockIn.setIsDeleted(Params.STATUS_ONE);
		productStockInService.update(productStockIn);
		printText(messageSuccuseWrap());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportExcel() throws Exception {
		String stockInString =  this.getRequest().getParameter("stockInId");
		if(!StringUtils.isNumeric(stockInString)){
			throw new Exception("没有入库id或类型错误！请传入long类型的入库id");
		}
		ProductStockIn  productStockIn  = productStockInService.getById(Long.valueOf(stockInString));
		Map<String, String> paramMap = bindMap();
		paramMap.put("start", "0");
		paramMap.put("limit", "99999");
		paramMap.put("stockInId", String.valueOf(productStockIn.getStockInId()));
		Page page = productStockInDetailService.queryPaged(paramMap);
		Map<String, String> productTypeMap = datadictService.getDatadictMap("PRODUCT_TYPE");
		Map dataMap = new HashMap();
		dataMap.put("stockIn", productStockIn);
		dataMap.put("stockInDetailList", page.getData());
		dataMap.put("productTypeMap", productTypeMap);
		return super.exportExcel("productIn", "产品入库单", dataMap);
	}
	
	public void detailQueryPaged() throws Exception {
		Map<String, String> paramMap = bindMap();
		Page page = productStockInDetailService.queryPaged(paramMap);
		printJson(JsonUtils.bean2Json(page));
	}
	
	public void getProductStockInDetail() throws Exception{
		
		String detailId = this.getRequest().getParameter("detailId");
		ProductStockInDetail productStockInDetail = productStockInDetailService.getById(Long.valueOf(detailId));
		
		printJson(messageSuccuseWrap(JsonUtils.bean2Json(productStockInDetail)));
	}
	public void saveProductStockInDetail() throws Exception{
		
		ProductStockInDetail productStockInDetail = bindEntity(ProductStockInDetail.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		productStockInDetail.setGmtModify(now);
		productStockInDetail.setModifier(user.getUserName());
		productStockInDetail.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(productStockInDetail.getDetailId() == null){
			productStockInDetail.setGmtCreate(now);
			productStockInDetail.setCreator(user.getUserName());
			productStockInDetail.setCreatorId(Long.valueOf(user.getId()));
			productStockInDetailService.insert(productStockInDetail);
		}
		// 更新
		else{
			productStockInDetailService.update(productStockInDetail);
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
		
		ProductStockInDetail productStockInDetail = productStockInDetailService.getById(detailId);
		if(productStockInDetail == null) {
			printText(messageFailureWrap("该入库信息已被删除，请刷新后再试！"));
			return;
		}
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		productStockInDetail.setGmtModify(now);
		productStockInDetail.setModifier(user.getUserName());
		productStockInDetail.setModifierId(Long.valueOf(user.getId()));
		// 删除
		productStockInDetail.setIsDeleted(Params.STATUS_ONE);
		productStockInDetailService.update(productStockInDetail);
		printText(messageSuccuseWrap());
	}
}
