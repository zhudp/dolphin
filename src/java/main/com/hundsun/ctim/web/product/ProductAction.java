package com.hundsun.ctim.web.product;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.core.dao.support.Page;
import com.core.utils.DateUtils;
import com.core.utils.JsonUtils;
import com.core.utils.StringUtils;
import com.core.utils.WebUtils;
import com.core.web.StrutsAction;
import com.dolphin.domain.RemoteUser;
import com.dolphin.domain.User;
import com.dolphin.service.DatadictService;
import com.hundsun.ctim.Params;
import com.hundsun.ctim.domain.product.Product;
import com.hundsun.ctim.service.FileService;
import com.hundsun.ctim.service.SerialNoServiceImp;
import com.hundsun.ctim.service.product.ProductServiceImp;
import com.hundsun.ctim.service.stock.ProductStockServiceImp;

/**
 * 产品管理
 * 
 */
@SuppressWarnings("serial")
public class ProductAction extends StrutsAction {
	
	@Autowired
	private ProductServiceImp productService;
	@Autowired
	private FileService fileService;
	@Autowired
	private DatadictService datadictService;
	@Autowired
	private SerialNoServiceImp serialNoService;
	@Autowired
	private ProductStockServiceImp productStockService;
	
	/**
	 * 产品查询
	 */
	public void queryPaged() throws Exception {
		
		Map<String, String> paramMap = bindMap();
		Page page = productService.queryPaged(paramMap);

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
		
		Page page = productService.queryPaged(paramMap);
		
		Map<String, String> productTypeMap = datadictService.getDatadictMap("PRODUCT_TYPE");
		
		Map dataMap = new HashMap();
		dataMap.put("requestParam", paramMap);
		dataMap.put("dataList", page.getData());
		dataMap.put("productTypeMap", productTypeMap);
		
		return super.exportExcel("../excel/productPrice", "产品报价单", dataMap);
	}
	
	/**
	 * 条码打印页
	 * @throws Exception
	 */
	public String printBarcode() throws Exception{
		
		String productId = this.getRequest().getParameter("productId");
		Product product = productService.getById(Long.valueOf(productId));
		
		this.getRequest().setAttribute("product", product);
		
		return "productBarcode";
	}
	
	/**
	 * 获取产品详细信息
	 */
	public void getProduct() throws Exception{
		
		String productId = this.getRequest().getParameter("productId");
		Product product = productService.getById(Long.valueOf(productId));
		
		if(!StringUtils.isBlank(product.getProductPicpath())) {
			String pictrueFullUrl = fileService.getFullURL(product.getProductPicpath(), WebUtils.getIpAddrByRequest(this.getRequest()));
			product.setPictrueFullUrl(pictrueFullUrl);
		}
		
		printJson(messageSuccuseWrapObj(JsonUtils.bean2Json(product)));
	}
	

	/**
	 * 保存产品信息
	 * @throws Exception
	 */
	public void saveProduct() throws Exception{
		
		Product product = bindEntity(Product.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		product.setGmtModify(now);
		product.setModifier(user.getUserName());
		product.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(product.getProductId() == null){
			String yy = DateUtils.toDateString(now, "yy");
			//TODO:产品编号生成
			String productNo = serialNoService.getNo("CP-"+product.getCustomNo()+"-"+yy, 3,"");
			product.setProductNo(productNo);
			product.setGmtCreate(now);
			product.setCreator(user.getUserName());
			product.setCreatorId(Long.valueOf(user.getId()));
			productService.insert(product);
		}
		
		// 更新
		else{
			productService.updateSelective(product);
		}
		
		printText(messageSuccuseWrap());
	}
	
	/**
	 * 删除产品信息
	 */
	public void remove() throws Exception {
		String strProductId = this.getRequest().getParameter("productId");
		Product product = productService.getById(Long.valueOf(strProductId));
		if(product == null) {
			printText(messageFailureWrap("该产品信息已被删除，请刷新后再试！"));
			return;
		}
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		product.setGmtModify(now);
		product.setModifier(user.getUserName());
		product.setModifierId(Long.valueOf(user.getId()));
		// 删除
		product.setIsDeleted(Params.STATUS_ONE);
		productService.update(product);
		
		printText(messageSuccuseWrap());
	}
	/**
	 * 产品台帐
	 * @throws Exception
	 */
	public void productStock()throws Exception {
		Map<String, String> paramMap = bindMap();
		Page page = productStockService.queryPaged(paramMap);
		printJson(JsonUtils.bean2Json(page));
	}
}
