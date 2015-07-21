package com.hundsun.ctim.domain.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.core.BaseEntity;
import com.hundsun.ctim.domain.material.Material;


/*Record=[{ name:"productId"},{ name:"partId"},{ name:"materialId"},{ name:"materialNum"},{ name:"remark"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "产品ID",dataIndex : 'productId',width : 60 },{header : "部件ID",dataIndex : 'partId',width : 60 },{header : "原料ID",dataIndex : 'materialId',width : 60 },{header : "原料数量",dataIndex : 'materialNum',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '产品ID',id : 'productId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '部件ID',id : 'partId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '原料ID',id : 'materialId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '原料数量',id : 'materialNum',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false})
/**
 * Bom Entity类<br>.
 * @author abator Sun Jan 20 00:24:47 CST 2013
 */
public class ProductBom extends BaseEntity {
    private static final long serialVersionUID = -7386808298639727224L;

	/** bomId */
    private Long bomId;
    
    /** 产品ID */
    private Long productId;
    
    /** 部件ID */
    private Long partId;
    
    private Long parentId;
    
    /** 物料类别：part/material/work/manage/other */
    private String itemType;

    /** 物料ID */
    private Long itemId;

	/** 物料名称 */
    private String itemName;
    
    /** 编号 */
    private String itemNo;

	/** 部件/原材料数量 */
    private BigDecimal number;

    /** 单价 */
    private BigDecimal price;
    
	/** 合计价格 */
    private BigDecimal sumPrice;
    
    /** 备注 */
    private String remark;
    
    /** 规格 */
    private String standard;

	/** 单位 */
    private String unit;

    /** 部件重量 */
    private BigDecimal weight;

    /** 部件面积 */
    private BigDecimal area;

	/** 损耗率 */
    private BigDecimal wastageRate;
    
	/** 加工规格 */
    private String workSpec;

	/** 原料列表 */
    private List<Material> children = new ArrayList<Material>();
    
	/** 树样式 */
    private String iconCls ="task-folder";

	/** 是否展开 */
    private boolean expanded = true;
    

    public Long getBomId() {
		return bomId;
	}

	public void setBomId(Long bomId) {
		this.bomId = bomId;
	}

    /**
     * 获取产品ID.
     * <p>
     * @return productId
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置产品ID.
     * <p>
     * @param productId Long
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取部件ID.
     * <p>
     * @return partId
     */
    public Long getPartId() {
        return partId;
    }

    /**
     * 设置部件ID.
     * <p>
     * @param partId Long
     */
    public void setPartId(Long partId) {
        this.partId = partId;
    }
    
    /**
     * <p>
     * @return partId
     */
    public Long getParentId() {
    	return parentId;
    }
    
    /**
     * 设置部件ID.
     * <p>
     * @param partId Long
     */
    public void setParentId(Long parentId) {
    	this.parentId = parentId;
    }

    public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

    public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	   
    public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
    
    /**
     * 获取备注.
     * <p>
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注.
     * <p>
     * @param remark String
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
    public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
    /**
     * 获取产品单位.
     * <p>
     * @return unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置产品单位.
     * <p>
     * @param unit String
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public List<Material> getChildren() {
		return children;
	}

	public void setChildren(List<Material> children) {
		this.children = children;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	
    
    public BigDecimal getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(BigDecimal sumPrice) {
		this.sumPrice = sumPrice;
	}
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

    
    public BigDecimal getWastageRate() {
		return wastageRate;
	}

	public void setWastageRate(BigDecimal wastageRate) {
		this.wastageRate = wastageRate;
	}

	public String getWorkSpec() {
		return workSpec;
	}

	public void setWorkSpec(String workSpec) {
		this.workSpec = workSpec;
	}
}