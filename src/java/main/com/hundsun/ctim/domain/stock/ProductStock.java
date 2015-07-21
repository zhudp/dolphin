package com.hundsun.ctim.domain.stock;

import java.math.BigDecimal;

import com.core.BaseEntity;

/*Record=[{ name:"productId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "产品ID",dataIndex : 'productId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '产品ID',id : 'productId',allowBlank : false})
/*Record=[{ name:"stock"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "当前库存量",dataIndex : 'stock',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '当前库存量',id : 'stock',allowBlank : false})
/**
 * ProductStock Entity类<br>.
 * @author abator Sun Jan 20 00:28:18 CST 2013
 */
public class ProductStock extends BaseEntity {
    private static final long serialVersionUID = 1353664256979737752L;

    /** 产品ID */
    private Long productId;

    /** 当前库存量 */
    private Long stock;
    
    /** 产品编号 */
    private String productNo;

    /** 产品名称 */
    private String productName;

    /** 产品类型 */
    private String productType;

    /** 产品规格 */
    private String standard;

    /** 产品单位 */
    private String unit;

    /** 产品报价 */
    private BigDecimal price;

    /** 最小库存量 */
    private Integer minStore;

    /** 最大库存量 */
    private Integer maxStore;


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
     * 获取当前库存量.
     * <p>
     * @return stock
     */
    public Long getStock() {
        return stock;
    }

    /**
     * 设置当前库存量.
     * <p>
     * @param stock BigDecimal
     */
    public void setStock(Long stock) {
        this.stock = stock;
    }

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}


	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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
}