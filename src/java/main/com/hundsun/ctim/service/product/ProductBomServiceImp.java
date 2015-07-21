package com.hundsun.ctim.service.product;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.product.ProductBomDao;
import com.hundsun.ctim.domain.product.BomTreeNode;
import com.hundsun.ctim.domain.product.ProductBom;

@Service
public class ProductBomServiceImp extends BaseService {
	
    @Autowired
    private ProductBomDao bomDao;
 
    /**
     * 根据ID获取产品BOM信息
     * @param id
     * @return
     */
	public ProductBom getById(Long id) {
		return bomDao.get(id);
	}
	
	/**
	 * 查询BOM树
	 */
	@SuppressWarnings("unchecked")
	public List<BomTreeNode> queryTree(Map<String, String> paramsMap) {
		return bomDao.getSqlMapClientTemplate().queryForList("ProductBom_tree", paramsMap);
	}
	
	/**
	 * 分页查询
	 */
	public Page queryPaged(Map<String, String> paramsMap){
		return bomDao.queryPaged(paramsMap);
	}
	
	
    /**
     * 新增产品BOM信息
     * @return
     */
	public void insert(ProductBom bom) {
		bomDao.insert(bom);
	}
	
	/**
	 * 更新产品BOM信息
	 * @return
	 */
	public void update(ProductBom bom) {
		bomDao.update(bom);
	}
	
	/**
	 * 保存BOM
	 * @param bomList
	 */
	public void save(Long productId, List<ProductBom> bomList) {
		
		// 删除原BOM信息
		ProductBom bom = new ProductBom();
		bom.setProductId(productId);
		bomDao.remove(bom);
		
		// 批量插入新信息
		for (Iterator<ProductBom> iterator = bomList.iterator(); iterator.hasNext();) {
			ProductBom productBom = iterator.next();
			bomDao.insert(productBom);
		}
		
		bomDao.getSqlMapClientTemplate().update("ProductBom_countPartsWeigthCost", productId);
	}
	
	/**
	 * 更新产品BOM信息
	 * @return
	 */
	public void updateSelective(ProductBom bom) {
		bomDao.updateSelective(bom);
	}
	
	/**
	 * 根据ID删除
	 */
	public void removeById(Long customId) {
		bomDao.removeById(customId);
	}
}