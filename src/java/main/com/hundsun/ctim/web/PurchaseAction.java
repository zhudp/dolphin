package com.hundsun.ctim.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.hundsun.ctim.domain.purchase.Purchase;
import com.hundsun.ctim.domain.purchase.PurchaseDetail;
import com.hundsun.ctim.service.perchase.PurchaseDetailServiceImp;
import com.hundsun.ctim.service.perchase.PurchaseServiceImp;
import com.hundsun.ctim.service.perchase.PurchaseTaskServiceImp;
import com.hundsun.ctim.service.supplier.SupplierQuoteServiceImp;
import com.hundsun.ctim.service.supplier.SupplierServiceImp;

/**
 * 采购管理
 *
 */
@SuppressWarnings("serial")
public class PurchaseAction extends StrutsAction {
	@Autowired
	private PurchaseServiceImp purchaseService;
	@Autowired
	private PurchaseDetailServiceImp purchaseDetailService;
	@Autowired
	private DatadictService datadictService;
	@Autowired
	private PurchaseTaskServiceImp purchaseTaskService;
	@Autowired
	private SupplierServiceImp supplierService;
	@Autowired
	private SupplierQuoteServiceImp supplierQuoteService;

	public void setSupplierQuoteService(SupplierQuoteServiceImp supplierQuoteService) {
		this.supplierQuoteService = supplierQuoteService;
	}
	public void setDatadictService(DatadictService datadictService) {
		this.datadictService = datadictService;
	}
	public void setPurchaseService(PurchaseServiceImp purchaseService){
		this.purchaseService = purchaseService;
	}
	public void setPurchaseDetailService(PurchaseDetailServiceImp purchaseDetailService){
		this.purchaseDetailService =purchaseDetailService;
	}
	public void setPurchaseTaskService(PurchaseTaskServiceImp purchaseTaskService) {
		this.purchaseTaskService = purchaseTaskService;
	}
	public void setSupplierService(SupplierServiceImp supplierService) {
		this.supplierService = supplierService;
	}
	
	public void queryPaged() throws Exception {
		Map<String, String> paramMap = bindMap();
		Page page = purchaseService.queryPaged(paramMap);
		printJson(JsonUtils.bean2Json(page));
	}
	
	public void getPurchase() throws Exception{
		
		String purchaseId = this.getRequest().getParameter("purchaseId");
		Purchase purchase = purchaseService.getById(Long.valueOf(purchaseId));
		
		printJson(messageSuccuseWrap(JsonUtils.bean2Json(purchase)));
	}
	public void savePurchase() throws Exception{
		
		Purchase purchase = bindEntity(Purchase.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		purchase.setGmtModify(now);
		purchase.setModifier(user.getUserName());
		purchase.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(purchase.getPurchaseId() == null){
			purchase.setGmtCreate(now);
			purchase.setCreator(user.getUserName());
			purchase.setCreatorId(Long.valueOf(user.getId()));
			purchaseService.insert(purchase);
		}
		// 更新
		else{
			purchaseService.update(purchase);
		}
		printText(messageSuccuseWrap());
	}
	
	public void delete() throws Exception {
		String strmaterialId = this.getRequest().getParameter("purchaseId");
		if(strmaterialId == null) {
			printText(messageFailureWrap("该入库已被删除，请刷新后再试！"));
			return;
		}
		Long purchaseId = Long.valueOf(strmaterialId);
		
		Purchase purchase = purchaseService.getById(purchaseId);
		if(purchase == null) {
			printText(messageFailureWrap("该入库信息已被删除，请刷新后再试！"));
			return;
		}
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		purchase.setGmtModify(now);
		purchase.setModifier(user.getUserName());
		purchase.setModifierId(Long.valueOf(user.getId()));
		// 删除
		purchase.setIsDeleted(Params.STATUS_ONE);
		purchaseService.update(purchase);
		printText(messageSuccuseWrap());
	}
	
	// purchaseDetail
	
	public void detailQueryPaged() throws Exception {
		Map<String, String> paramMap = bindMap();
		Page page = purchaseDetailService.queryPaged(paramMap);
		printJson(JsonUtils.bean2Json(page));
	}
	
	public void getPurchaseDetail() throws Exception{
		
		String detailId = this.getRequest().getParameter("detailId");
		PurchaseDetail purchaseDetail = purchaseDetailService.getById(Long.valueOf(detailId));
		
		printJson(messageSuccuseWrap(JsonUtils.bean2Json(purchaseDetail)));
	}
	public void savePurchaseDetail() throws Exception{
		
		PurchaseDetail purchaseDetail = bindEntity(PurchaseDetail.class);
		
		// 新增
		if(purchaseDetail.getDetailId() == null){
			purchaseDetailService.insert(purchaseDetail);
		}
		// 更新
		else{
			purchaseDetailService.update(purchaseDetail);
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
		
		PurchaseDetail purchaseDetail = purchaseDetailService.getById(detailId);
		if(purchaseDetail == null) {
			printText(messageFailureWrap("该入库信息已被删除，请刷新后再试！"));
			return;
		}
		
		// 删除
		purchaseDetail.setIsDeleted(Params.STATUS_ONE);
		purchaseDetailService.update(purchaseDetail);
		printText(messageSuccuseWrap());
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportExcel() throws Exception {
		String purchaseString =  this.getRequest().getParameter("purchaseId");
		if(!StringUtils.isNumeric(purchaseString)){
			throw new Exception("没有入库id或类型错误！请传入long类型的入库id");
		}
		Purchase  purchase  = purchaseService.getById(Long.valueOf(purchaseString));
		Map<String, String> paramMap = bindMap();
		paramMap.put("start", "0");
		paramMap.put("limit", "99999");
		paramMap.put("purchaseId", String.valueOf(purchase.getPurchaseId()));
		Page page = purchaseDetailService.queryPaged(paramMap); 
		Map<String, String> purchaseTypeMap = datadictService.getDatadictMap("PURCHASE_TYPE");
		Map<String, String> purchaseStatus = datadictService.getDatadictMap("PURCHASE_STATUS");
		Map dataMap = new HashMap();
		dataMap.put("purchase", purchase);
		dataMap.put("purchaseDetailList", page.getData());
		dataMap.put("purchaseTypeMap", purchaseTypeMap);
		dataMap.put("purchaseStatus", purchaseStatus);
		return super.exportExcel("purchase", "采购单", dataMap);
	}
	/**
	 * 保存通过采购任务生产的采购和采购明细
	 * @throws Exception
	 */
	public void savePurchaseByTask() throws Exception{
		
		Purchase purchase = bindEntity(Purchase.class);
		String detailJson = this.getRequest().getParameter("detailJson");
		List<PurchaseDetail> detailList = JsonUtils.json2List(detailJson, PurchaseDetail.class);
		purchaseDetailService.insertByTask(purchase, detailList);
		printText(messageSuccuseWrap());
	}
	/**
	 * 获取采购任务集合
	 * @throws IOException
	 */
	public void getPurchasesTask() throws IOException{
		Map<String, String> paramMap = bindMap();
		paramMap.put("start", "0");
		paramMap.put("limit", "1000");
		paramMap.put("status", "1");
		Page page = purchaseTaskService.queryPaged(paramMap);
		printJson(JsonUtils.bean2Json(page));
	}
	/**
	 * 判断是否有待采购的采购任务
	 * @throws IOException
	 */
	public void hasPurchasesTask() throws IOException{
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("p", "0");
		paramMap.put("n", "1000");
		Page page = purchaseTaskService.queryPaged(paramMap);
		if(page.getTotalCount()>0){
			printText("{hasPurchases:true}");
		}else{
			printText("{hasPurchases:false}");
		}
	}
	/**
	 * 供应商报价
	 * @throws IOException 
	 */
	public void getSupplierCombox() throws IOException{
		Map<String, String> paramMap = bindMap();//原料id
		String materiaId = this.getRequest().getParameter("materialId");
//		limit=20, purchaseNo=, start=0,merialId
		paramMap.put("limit","20");
		paramMap.put("start","0");
		
		Page page = supplierQuoteService.queryPaged(paramMap);

		printJson(JsonUtils.bean2Json(page));
	}
}
