package com.hundsun.ctim.web.supplier;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.core.dao.support.Page;
import com.core.utils.DateUtils;
import com.core.utils.JsonUtils;
import com.core.web.StrutsAction;
import com.dolphin.domain.RemoteUser;
import com.dolphin.domain.User;
import com.hundsun.ctim.Params;
import com.hundsun.ctim.domain.supplier.SupplierQuote;
import com.hundsun.ctim.service.supplier.SupplierQuoteServiceImp;

/**
 * 供应商报价
 * @author qiaoel@gmail.com
 *
 */
@SuppressWarnings("serial")
public class QuoteAction extends StrutsAction {
	@Autowired
	private SupplierQuoteServiceImp supplierQuoteService;

	public void setSupplierQuoteService(SupplierQuoteServiceImp supplierQuoteService){
		this.supplierQuoteService = supplierQuoteService;
	}
	/**
	 * 供应商报价查询
	 */
	public void queryPaged() throws Exception {
		
		Map<String, String> paramMap = bindMap();
		Page page = supplierQuoteService.queryPaged(paramMap);

		printJson(JsonUtils.bean2Json(page));
	}
	
	/**
	 * 获取供应商报价详细信息
	 */
	public void getSupplierQuote() throws Exception{
		
		String quoteId = this.getRequest().getParameter("quoteId");
		SupplierQuote supplierQuote = supplierQuoteService.getById(Long.valueOf(quoteId));
		
		printJson(messageSuccuseWrap(JsonUtils.bean2Json(supplierQuote)));
	}
	

	/**
	 * 保存供应商报价信息
	 * @throws Exception
	 */
	public void saveSupplierQuote() throws Exception{
		
		SupplierQuote supplierQuote = bindEntity(SupplierQuote.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		supplierQuote.setGmtModify(now);
		supplierQuote.setModifier(user.getUserName());
		supplierQuote.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(supplierQuote.getQuoteId() == null){
			supplierQuote.setGmtCreate(now);
			supplierQuote.setCreator(user.getUserName());
			supplierQuote.setCreatorId(Long.valueOf(user.getId()));
			supplierQuoteService.insert(supplierQuote);
		}
		
		// 更新
		else{
			supplierQuoteService.update(supplierQuote);
		}
		
		printText(messageSuccuseWrap());
	}
	
	/**
	 * 删除供应商报价信息
	 */
	public void delete() throws Exception {
		String strQuoteId = this.getRequest().getParameter("quoteId");
		if(strQuoteId == null) {
			printText(messageFailureWrap("该供应商报价已被删除，请刷新后再试！"));
			return;
		}
		Long quoteId = Long.valueOf(strQuoteId);
		
		SupplierQuote supplierQuote = supplierQuoteService.getById(quoteId);
		
		if(supplierQuote == null) {
			printText(messageFailureWrap("该供应商报价信息已被删除，请刷新后再试！"));
			return;
		}
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		supplierQuote.setGmtModify(now);
		supplierQuote.setModifier(user.getUserName());
		supplierQuote.setModifierId(Long.valueOf(user.getId()));
		// 删除
		supplierQuote.setIsDeleted(Params.STATUS_ONE);
		supplierQuoteService.update(supplierQuote);
		
		printText(messageSuccuseWrap());
	}

}
