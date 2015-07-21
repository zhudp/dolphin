package com.hundsun.ctim.domain.order;

import com.core.BaseEntity;
import java.math.BigDecimal;

/*Record=[{ name:"detailId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "明细ID",dataIndex : 'detailId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '明细ID',id : 'detailId',allowBlank : false})
/*Record=[{ name:"orderId"},{ name:"productId"},{ name:"productNo"},{ name:"productName"},{ name:"standard"},{ name:"unit"},{ name:"price"},{ name:"productNum"},{ name:"remark"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "订单ID",dataIndex : 'orderId',width : 60 },{header : "产品ID",dataIndex : 'productId',width : 60 },{header : "产品编号",dataIndex : 'productNo',width : 60 },{header : "产品名称",dataIndex : 'productName',width : 60 },{header : "规格",dataIndex : 'standard',width : 60 },{header : "单位",dataIndex : 'unit',width : 60 },{header : "单价",dataIndex : 'price',width : 60 },{header : "数量",dataIndex : 'productNum',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '订单ID',id : 'orderId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品ID',id : 'productId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品编号',id : 'productNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品名称',id : 'productName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '规格',id : 'standard',allowBlank : false}),new Ext.form.TextField({fieldLabel : '单位',id : 'unit',allowBlank : false}),new Ext.form.TextField({fieldLabel : '单价',id : 'price',allowBlank : false}),new Ext.form.TextField({fieldLabel : '数量',id : 'productNum',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false})
/**
 * TbOrderDetail Entity类<br>.
 * @author abator Sun Jan 27 23:14:34 CST 2013
 */
public class OrderDetail extends BaseEntity {
    private static final long serialVersionUID = -39753213777480883L;

    /** 明细ID */
    private Long detailId;

    /** 订单ID */
    private Long orderId;

    /** 产品ID */
    private Long productId;

    /** 产品编号 */
    private String productNo;

    /** 产品名称 */
    private String productName;

    /** 规格 */
    private String standard;

    /** 单位 */
    private String unit;

    /** 单价 */
    private BigDecimal price;

    /** 数量 */
    private Integer productNum;

    /** 备注 */
    private String remark;

	/** 当前库存量 */
    private Integer stock;

	/** 最小库存量 */
    private Integer minStore;

	/** 还需要生产量 */
    private Integer produceTask;

    /**
     * 获取明细ID.
     * <p>
     * @return detailId
     */
    public Long getDetailId() {
        return detailId;
    }

    /**
     * 设置明细ID.
     * <p>
     * @param detailId Long
     */
    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    /**
     * 获取订单ID.
     * <p>
     * @return orderId
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单ID.
     * <p>
     * @param orderId Long
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
     * 获取产品编号.
     * <p>
     * @return productNo
     */
    public String getProductNo() {
        return productNo;
    }

    /**
     * 设置产品编号.
     * <p>
     * @param productNo String
     */
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    /**
     * 获取产品名称.
     * <p>
     * @return productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置产品名称.
     * <p>
     * @param productName String
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 获取规格.
     * <p>
     * @return standard
     */
    public String getStandard() {
        return standard;
    }

    /**
     * 设置规格.
     * <p>
     * @param standard String
     */
    public void setStandard(String standard) {
        this.standard = standard;
    }

    /**
     * 获取单位.
     * <p>
     * @return unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置单位.
     * <p>
     * @param unit String
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 获取单价.
     * <p>
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置单价.
     * <p>
     * @param price BigDecimal
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取数量.
     * <p>
     * @return productNum
     */
    public Integer getProductNum() {
        return productNum;
    }

    /**
     * 设置数量.
     * <p>
     * @param productNum Integer
     */
    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
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
    
    /**
     * 当前库存量
     * @return
     */
    public Integer getStock() {
		return stock;
	}

    /**
     * 当前库存量
     * @param stock
     */
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
    
    public Integer getMinStore() {
		return minStore;
	}

	public void setMinStore(Integer minStore) {
		this.minStore = minStore;
	}
	
    
    public Integer getProduceTask() {
		return produceTask;
	}

	public void setProduceTask(Integer produceTask) {
		this.produceTask = produceTask;
	}
}