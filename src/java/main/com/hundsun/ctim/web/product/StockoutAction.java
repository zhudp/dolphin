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
import com.hundsun.ctim.Params;
import com.hundsun.ctim.domain.stock.ProductStockOut;
import com.hundsun.ctim.domain.stock.ProductStockOutDetail;
import com.hundsun.ctim.service.stock.ProductStockOutDetailServiceImp;
import com.hundsun.ctim.service.stock.ProductStockOutServiceImp;

@SuppressWarnings("serial")
public class StockoutAction extends StrutsAction {
	@Autowired
	private ProductStockOutServiceImp productStockOutService;
	@Autowired
	private ProductStockOutDetailServiceImp productStockOutDetailService;

	public void setProductStockOutService(ProductStockOutServiceImp productStockOutService){
		this.productStockOutService = productStockOutService;
	}
	public void setProductStockOutDetailService(ProductStockOutDetailServiceImp productStockOutDetailService){
		this.productStockOutDetailService =productStockOutDetailService;
	}
	
	public void queryPaged() throws Exception {
		Map<String, String> paramMap = bindMap();
		Page page = productStockOutService.queryPaged(paramMap);
		printJson(JsonUtils.bean2Json(page));
	}
	
	public void getProductStockOut() throws Exception{
		
		String stockOutId = this.getRequest().getParameter("stockOutId");
		ProductStockOut productStockOut = productStockOutService.getById(Long.valueOf(stockOutId));
		
		printJson(messageSuccuseWrap(JsonUtils.bean2Json(productStockOut)));
	}
	public void saveProductStockOut() throws Exception{
		
		ProductStockOut productStockOut = bindEntity(ProductStockOut.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		productStockOut.setGmtModify(now);
		productStockOut.setModifier(user.getUserName());
		productStockOut.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(productStockOut.getStockOutId() == null){
			productStockOut.setGmtCreate(now);
			productStockOut.setCreator(user.getUserName());
			productStockOut.setCreatorId(Long.valueOf(user.getId()));
			productStockOutService.insert(productStockOut);
		}
		// 更新
		else{
			productStockOutService.update(productStockOut);
		}
		printText(messageSuccuseWrap());
	}
	
	public void delete() throws Exception {
		String strproductId = this.getRequest().getParameter("stockOutId");
		if(strproductId == null) {
			printText(messageFailureWrap("该出库已被删除，请刷新后再试！"));
			return;
		}
		Long productStockOutId = Long.valueOf(strproductId);
		
		ProductStockOut productStockOut = productStockOutService.getById(productStockOutId);
		if(productStockOut == null) {
			printText(messageFailureWrap("该出库信息已被删除，请刷新后再试！"));
			return;
		}
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		productStockOut.setGmtModify(now);
		productStockOut.setModifier(user.getUserName());
		productStockOut.setModifierId(Long.valueOf(user.getId()));
		// 删除
		productStockOut.setIsDeleted(Params.STATUS_ONE);
		productStockOutService.update(productStockOut);
		printText(messageSuccuseWrap());
	}
	
	// productStockOutDetail
	
	public void detailQueryPaged() throws Exception {
		Map<String, String> paramMap = bindMap();
		Page page = productStockOutDetailService.queryPaged(paramMap);
		printJson(JsonUtils.bean2Json(page));
	}
	
	public void getProductStockOutDetail() throws Exception{
		
		String detailId = this.getRequest().getParameter("detailId");
		ProductStockOutDetail productStockOutDetail = productStockOutDetailService.getById(Long.valueOf(detailId));
		
		printJson(messageSuccuseWrap(JsonUtils.bean2Json(productStockOutDetail)));
	}
	public void saveProductStockOutDetail() throws Exception{
		
		ProductStockOutDetail productStockOutDetail = bindEntity(ProductStockOutDetail.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		productStockOutDetail.setGmtModify(now);
		productStockOutDetail.setModifier(user.getUserName());
		productStockOutDetail.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(productStockOutDetail.getDetailId() == null){
			productStockOutDetail.setGmtCreate(now);
			productStockOutDetail.setCreator(user.getUserName());
			productStockOutDetail.setCreatorId(Long.valueOf(user.getId()));
			productStockOutDetailService.insert(productStockOutDetail);
		}
		// 更新
		else{
			productStockOutDetailService.update(productStockOutDetail);
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
		
		ProductStockOutDetail productStockOutDetail = productStockOutDetailService.getById(detailId);
		if(productStockOutDetail == null) {
			printText(messageFailureWrap("该出库信息已被删除，请刷新后再试！"));
			return;
		}
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		productStockOutDetail.setGmtModify(now);
		productStockOutDetail.setModifier(user.getUserName());
		productStockOutDetail.setModifierId(Long.valueOf(user.getId()));
		// 删除
		productStockOutDetail.setIsDeleted(Params.STATUS_ONE);
		productStockOutDetailService.update(productStockOutDetail);
		printText(messageSuccuseWrap());
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportExcel() throws Exception {
		String stockOutString =  this.getRequest().getParameter("stockOutId");
		if(!StringUtils.isNumeric(stockOutString)){
			throw new Exception("没有出库id或类型错误！请传入long类型的出库id");
		}
		ProductStockOut  productStockOut  = productStockOutService.getById(Long.valueOf(stockOutString));
		Map<String, String> paramMap = bindMap();
		paramMap.put("start", "0");
		paramMap.put("limit", "99999");
		paramMap.put("stockOutId", String.valueOf(productStockOut.getStockOutId()));
		Page page = productStockOutDetailService.queryPaged(paramMap);
		Map dataMap = new HashMap();
		dataMap.put("stockOut", productStockOut);
		dataMap.put("stockOutDetailList", page.getData());
		return super.exportExcel("productOut", "产品出库单", dataMap);
	}
}
