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
import com.hundsun.ctim.domain.material.Material;
import com.hundsun.ctim.service.material.MaterialServiceImp;
import com.hundsun.ctim.service.stock.MaterialStockServiceImp;

/**
 * 原料管理
 * @author qiaoel@gmail.com
 *
 */
@SuppressWarnings("serial")
public class MaterialAction extends StrutsAction {
	@Autowired
	private MaterialServiceImp materialService;
	@Autowired
	private MaterialStockServiceImp materialStockService;

	public void setMaterialStockService(MaterialStockServiceImp materialStockService){
		this.materialStockService = materialStockService;
	}
	public void setMaterialService(MaterialServiceImp materialService){
		this.materialService = materialService;
	}
	/**
	 * 原料查询
	 */
	public void queryPaged() throws Exception {
		
		Map<String, String> paramMap = bindMap();
		Page page = materialService.queryPaged(paramMap);

		printJson(JsonUtils.bean2Json(page));
	}
	
	/**
	 * 获取原料详细信息
	 */
	public void getMaterial() throws Exception{
		
		String materialId = this.getRequest().getParameter("materialId");
		Material material = materialService.getById(Long.valueOf(materialId));
		
		printJson(messageSuccuseWrap(JsonUtils.bean2Json(material)));
	}
	

	/**
	 * 保存原料信息
	 * @throws Exception
	 */
	public void saveMaterial() throws Exception{
		
		Material material = bindEntity(Material.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		material.setGmtModify(now);
		material.setModifier(user.getUserName());
		material.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(material.getMaterialId() == null){
			material.setGmtCreate(now);
			material.setCreator(user.getUserName());
			material.setCreatorId(Long.valueOf(user.getId()));
			materialService.insert(material);
		}
		
		// 更新
		else{
			materialService.update(material);
		}
		
		printText(messageSuccuseWrap());
	}
	
	/**
	 * 删除原料信息
	 */
	public void delete() throws Exception {
		String strmaterialId = this.getRequest().getParameter("materialId");
		if(strmaterialId == null) {
			printText(messageFailureWrap("该原料已被删除，请刷新后再试！"));
			return;
		}
		Long materialId = Long.valueOf(strmaterialId);
		
		Material material = materialService.getById(materialId);
		
		if(material == null) {
			printText(messageFailureWrap("该原料信息已被删除，请刷新后再试！"));
			return;
		}
		material.setIsDeleted("2");
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		material.setGmtModify(now);
		material.setModifier(user.getUserName());
		material.setModifierId(Long.valueOf(user.getId()));
		// 删除
		material.setIsDeleted(Params.STATUS_ONE);
		materialService.update(material);
		
		printText(messageSuccuseWrap());
	}
	/**
	 * 原料台帐
	 * @throws Exception
	 */
	public void materialStock()throws Exception {
		Map<String, String> paramMap = bindMap();
		Page page = materialStockService.queryPaged(paramMap);
		printJson(JsonUtils.bean2Json(page));
	}
}
