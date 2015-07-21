package com.hundsun.ctim.web.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.core.utils.JsonUtils;
import com.core.utils.StringUtils;
import com.core.web.StrutsAction;
import com.dolphin.service.DatadictService;
import com.hundsun.ctim.domain.product.BomTreeNode;
import com.hundsun.ctim.domain.product.Product;
import com.hundsun.ctim.domain.product.ProductBom;
import com.hundsun.ctim.service.product.ProductBomServiceImp;
import com.hundsun.ctim.service.product.ProductPartsServiceImp;
import com.hundsun.ctim.service.product.ProductServiceImp;

/**
 * 产品BOM管理
 * 
 */
@SuppressWarnings("serial")
public class ProductBomAction extends StrutsAction {
	
	@Autowired
	private ProductBomServiceImp productBomService;
	@Autowired
	private ProductServiceImp productService;
	@Autowired
	private ProductPartsServiceImp productPartsService;
	@Autowired
	private DatadictService datadictService;
	
	/**
	 * 产品BOM查询
	 */
	public void queryTree() throws Exception {
		
		Map<String, String> paramMap = bindMap();
		List<BomTreeNode> list = productBomService.queryTree(paramMap);

		printJson(JsonUtils.bean2JsonArray(list));
	}
	
	/**
	 * 导出excel
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String exportExcel() throws Exception {
		
		Map<String, String> paramMap = bindMap();
		
		Product product = productService.getById(Long.valueOf(paramMap.get("productId")));
		List<BomTreeNode> list = productBomService.queryTree(paramMap);
		
		Map dataMap = new HashMap();
		dataMap.put("requestParam", paramMap);
		dataMap.put("dataList", list);
		dataMap.put("product", product);
		
		return super.exportExcel("../excel/productBom", "产品BOM清单", dataMap);
	}
	
	/**
	 * 保存产品BOM信息
	 * @throws Exception
	 */
	public void saveBom() throws Exception{
		
		List<ProductBom> bomList = bindEntityList(ProductBom.class);
		
		if(bomList != null && bomList.size() > 0) {
			Long productId = bomList.get(0).getProductId();
			productBomService.save(productId, bomList);
		}
		
		printText(messageSuccuseWrap());
	}
	
	/**
	 * 删除产品信息
	 */
	public void removeBom() throws Exception {
		String strBomId = this.getRequest().getParameter("bomId");
		String strPartId = this.getRequest().getParameter("partId");
		String bomType = this.getRequest().getParameter("bomType");
		
		if(!StringUtils.isBlank(strBomId)) {
			productBomService.removeById(Long.valueOf(strBomId));
		}
		
		if("part".equals(bomType)) {
			productPartsService.removeById(Long.valueOf(strPartId));
		}
		
		printText(messageSuccuseWrap());
	}
	
}
