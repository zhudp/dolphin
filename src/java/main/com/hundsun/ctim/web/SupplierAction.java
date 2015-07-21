package com.hundsun.ctim.web;

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
import com.hundsun.ctim.domain.supplier.Supplier;
import com.hundsun.ctim.service.supplier.SupplierServiceImp;

/**
 * 供应商管理
 * @author qiaoel@gmail.com
 *
 */
@SuppressWarnings("serial")
public class SupplierAction extends StrutsAction {
	@Autowired
	private SupplierServiceImp supplierService;

	public void setSupplierService(SupplierServiceImp supplierService){
		this.supplierService = supplierService;
	}
	/**
	 * 原料查询
	 */
	public void queryPaged() throws Exception {
		
		Map<String, String> paramMap = bindMap();
		Page page = supplierService.queryPaged(paramMap);

		printJson(JsonUtils.bean2Json(page));
	}
	
	/**
	 * 获取原料详细信息
	 */
	public void getSupplier() throws Exception{
		
		String supplierId = this.getRequest().getParameter("supplierId");
		Supplier supplier = supplierService.getById(Long.valueOf(supplierId));
		
		printJson(messageSuccuseWrap(JsonUtils.bean2Json(supplier)));
	}
	

	/**
	 * 保存原料信息
	 * @throws Exception
	 */
	public void saveSupplier() throws Exception{
		
		Supplier supplier = bindEntity(Supplier.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		supplier.setGmtModify(now);
		supplier.setModifier(user.getUserName());
		supplier.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(supplier.getSupplierId() == null){
			supplier.setGmtCreate(now);
			supplier.setCreator(user.getUserName());
			supplier.setCreatorId(Long.valueOf(user.getId()));
			supplierService.insert(supplier);
		}
		
		// 更新
		else{
			supplierService.update(supplier);
		}
		
		printText(messageSuccuseWrap());
	}
	
	/**
	 * 删除原料信息
	 */
	public void delete() throws Exception {
		String strSupplierId = this.getRequest().getParameter("supplierId");
		if(strSupplierId == null) {
			printText(messageFailureWrap("该原料已被删除，请刷新后再试！"));
			return;
		}
		Long supplierId = Long.valueOf(strSupplierId);
		
		Supplier supplier = supplierService.getById(supplierId);
		
		if(supplier == null) {
			printText(messageFailureWrap("该原料信息已被删除，请刷新后再试！"));
			return;
		}
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		supplier.setGmtModify(now);
		supplier.setModifier(user.getUserName());
		supplier.setModifierId(Long.valueOf(user.getId()));
		// 删除
		supplier.setIsDeleted(Params.STATUS_ONE);
		supplierService.update(supplier);
		
		printText(messageSuccuseWrap());
	}

}
