package com.hundsun.ctim.web.product;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.core.dao.support.Page;
import com.core.utils.DateUtils;
import com.core.utils.JsonUtils;
import com.core.web.StrutsAction;
import com.dolphin.domain.RemoteUser;
import com.dolphin.domain.User;
import com.dolphin.service.DatadictService;
import com.hundsun.ctim.domain.product.ProductParts;
import com.hundsun.ctim.service.product.ProductPartsServiceImp;

/**
 * 产品部件管理
 * 
 */
@SuppressWarnings("serial")
public class ProductPartAction extends StrutsAction {
	
	@Autowired
	private ProductPartsServiceImp productPartService;
	@Autowired
	private DatadictService datadictService;
	
	/**
	 * 部件查询
	 */
	public void queryPaged() throws Exception {
		
		Map<String, String> paramMap = bindMap();
		Page page = productPartService.queryPaged(paramMap);

		printJson(JsonUtils.bean2Json(page));
	}
	
	/**
	 * 导出excel
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String exportExcel() throws Exception {
		
		Map<String, String> paramMap = bindMap();
		
		paramMap.put("start", "0");
		paramMap.put("limit", "99999");
		
		Page page = productPartService.queryPaged(paramMap);
		
		Map<String, String> productTypeMap = datadictService.getDatadictMap("PRODUCT_TYPE");
		
		Map dataMap = new HashMap();
		dataMap.put("requestParam", paramMap);
		dataMap.put("dataList", page.getData());
		dataMap.put("productTypeMap", productTypeMap);
		
		return super.exportExcel("../excel/productPrice", "产品报价单", dataMap);
	}
	
	/**
	 * 获取部件详细信息
	 */
	public void getProductPart() throws Exception{
		
		String productId = this.getRequest().getParameter("productId");
		ProductParts product = productPartService.getById(Long.valueOf(productId));
		
		printJson(messageSuccuseWrapObj(JsonUtils.bean2Json(product)));
	}
	

	/**
	 * 保存部件信息
	 * @throws Exception
	 */
	public void saveProductPart() throws Exception{
		
		ProductParts part = bindEntity(ProductParts.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		part.setGmtModify(now);
		part.setModifier(user.getUserName());
		part.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(part.getPartId() == null){
			part.setGmtCreate(now);
			part.setCreator(user.getUserName());
			part.setCreatorId(Long.valueOf(user.getId()));
			productPartService.insert(part);
		}
		
		// 更新
		else{
			productPartService.updateSelective(part);
		}
		
		printText(messageSuccuseWrap());
	}
	
	/**
	 * 删除部件信息
	 */
	public void remove() throws Exception {
		String strProductPartsId = this.getRequest().getParameter("productId");
		productPartService.removeById(Long.valueOf(strProductPartsId));
		
		printText(messageSuccuseWrap());
	}
}
