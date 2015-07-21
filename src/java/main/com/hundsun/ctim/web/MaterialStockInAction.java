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
import com.hundsun.ctim.domain.stock.MaterialStockIn;
import com.hundsun.ctim.service.stock.MaterialStockInServiceImp;

/**
 * 原料入库管理
 *
 */
@SuppressWarnings("serial")
public class MaterialStockInAction extends StrutsAction {
	@Autowired
	private MaterialStockInServiceImp materialStockInService;

	public void setMaterialStockInService(MaterialStockInServiceImp materialStockInService){
		this.materialStockInService = materialStockInService;
	}
	public void queryPaged() throws Exception {
		Map<String, String> paramMap = bindMap();
		Page page = materialStockInService.queryPaged(paramMap);
		printJson(JsonUtils.bean2Json(page));
	}
	
	public void getMaterial() throws Exception{
		
		String stockInId = this.getRequest().getParameter("stockInId");
		MaterialStockIn materialStockIn = materialStockInService.getById(Long.valueOf(stockInId));
		
		printJson(messageSuccuseWrap(JsonUtils.bean2Json(materialStockIn)));
	}
	public void saveMaterial() throws Exception{
		
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
		String strmaterialId = this.getRequest().getParameter("materialId");
		if(strmaterialId == null) {
			printText(messageFailureWrap("该入库已被删除，请刷新后再试！"));
			return;
		}
		Long materialId = Long.valueOf(strmaterialId);
		
		MaterialStockIn materialStockIn = materialStockInService.getById(materialId);
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
}
