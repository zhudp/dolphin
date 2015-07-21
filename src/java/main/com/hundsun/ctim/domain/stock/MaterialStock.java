package com.hundsun.ctim.domain.stock;

import java.math.BigDecimal;

import com.core.BaseEntity;

/*Record=[{ name:"materialId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "原材料ID",dataIndex : 'materialId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '原材料ID',id : 'materialId',allowBlank : false})
/*Record=[{ name:"stock"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "库存量",dataIndex : 'stock',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '库存量',id : 'stock',allowBlank : false})
/**
 * MaterialStock Entity类<br>.
 * @author abator Sun Jan 20 00:24:54 CST 2013
 */
public class MaterialStock extends BaseEntity {
    private static final long serialVersionUID = -757681934896922936L;

    /** 原材料ID */
    private Long materialId;

    /** 库存量 */
    private Long stock;
    
    private String materialName;
    
    /** 原材料编号 */
    private String materialNo;

    /** 规格 */
    private String standard;

    /** 物资分类：木器、五金、油漆等 */
    private String materialType;

    /** 单位 */
    private String unit;

    /** 单位重量(公斤、KG) */
    private BigDecimal weight;

    /** 单位表面积（平主米） */
    private BigDecimal area;
    
    /** 性质**/
    private String kind;
    
    /**价格**/
    private BigDecimal price = new BigDecimal(0);

    /** 备注 */
    private String remark;

    /** 最小库存量 */
    private Integer minStore;

    /** 最大库存量 */
    private Integer maxStore;
    /** 删除标志*/
    private String isDeleted;
    
    /**
     * 获取原材料ID.
     * <p>
     * @return materialId
     */
    public Long getMaterialId() {
        return materialId;
    }

    /**
     * 设置原材料ID.
     * <p>
     * @param materialId Long
     */
    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    /**
     * 获取库存量.
     * <p>
     * @return stock
     */
    public Long getStock() {
        return stock;
    }

    /**
     * 设置库存量.
     * <p>
     * @param stock Long
     */
    public void setStock(Long stock) {
        this.stock = stock;
    }

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getMinStore() {
		return minStore;
	}

	public void setMinStore(Integer minStore) {
		this.minStore = minStore;
	}

	public Integer getMaxStore() {
		return maxStore;
	}

	public void setMaxStore(Integer maxStore) {
		this.maxStore = maxStore;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
}