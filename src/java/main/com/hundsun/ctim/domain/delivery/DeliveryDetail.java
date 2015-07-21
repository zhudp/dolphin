package com.hundsun.ctim.domain.delivery;

import com.core.BaseEntity;
import java.math.BigDecimal;

/*Record=[{ name:"deliveryId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "发货单ID",dataIndex : 'deliveryId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '发货单ID',id : 'deliveryId',allowBlank : false})
/*Record=[{ name:"productId"},{ name:"productName"},{ name:"productStandard"},{ name:"productNum"},{ name:"productPrice"},{ name:"productUnit"},{ name:"totalAmount"},{ name:"boxNo"},{ name:"remark"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "产品ID",dataIndex : 'productId',width : 60 },{header : "产品名称",dataIndex : 'productName',width : 60 },{header : "产品规格",dataIndex : 'productStandard',width : 60 },{header : "产品数量",dataIndex : 'productNum',width : 60 },{header : "产品单价",dataIndex : 'productPrice',width : 60 },{header : "产品单位",dataIndex : 'productUnit',width : 60 },{header : "合计金额",dataIndex : 'totalAmount',width : 60 },{header : "箱号",dataIndex : 'boxNo',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '产品ID',id : 'productId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品名称',id : 'productName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品规格',id : 'productStandard',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品数量',id : 'productNum',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品单价',id : 'productPrice',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品单位',id : 'productUnit',allowBlank : false}),new Ext.form.TextField({fieldLabel : '合计金额',id : 'totalAmount',allowBlank : false}),new Ext.form.TextField({fieldLabel : '箱号',id : 'boxNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false})
/**
 * DeliveryDetail Entity类<br>.
 * @author abator Sun Jan 20 00:24:52 CST 2013
 */
public class DeliveryDetail extends BaseEntity {
    private static final long serialVersionUID = 3146109456231567313L;

    /** 发货单ID */
    private Long deliveryId;

    /** 产品ID */
    private Long productId;

    /** 产品名称 */
    private String productName;

    /** 产品规格 */
    private String productStandard;

    /** 产品数量 */
    private Integer productNum;

    /** 产品单价 */
    private BigDecimal productPrice;

    /** 产品单位 */
    private String productUnit;

    /** 合计金额 */
    private BigDecimal totalAmount;

    /** 箱号 */
    private String boxNo;

    /** 备注 */
    private String remark;

    /**
     * 获取发货单ID.
     * <p>
     * @return deliveryId
     */
    public Long getDeliveryId() {
        return deliveryId;
    }

    /**
     * 设置发货单ID.
     * <p>
     * @param deliveryId Long
     */
    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
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
     * 获取产品规格.
     * <p>
     * @return productStandard
     */
    public String getProductStandard() {
        return productStandard;
    }

    /**
     * 设置产品规格.
     * <p>
     * @param productStandard String
     */
    public void setProductStandard(String productStandard) {
        this.productStandard = productStandard;
    }

    /**
     * 获取产品数量.
     * <p>
     * @return productNum
     */
    public Integer getProductNum() {
        return productNum;
    }

    /**
     * 设置产品数量.
     * <p>
     * @param productNum Integer
     */
    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    /**
     * 获取产品单价.
     * <p>
     * @return productPrice
     */
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    /**
     * 设置产品单价.
     * <p>
     * @param productPrice BigDecimal
     */
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * 获取产品单位.
     * <p>
     * @return productUnit
     */
    public String getProductUnit() {
        return productUnit;
    }

    /**
     * 设置产品单位.
     * <p>
     * @param productUnit String
     */
    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    /**
     * 获取合计金额.
     * <p>
     * @return totalAmount
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * 设置合计金额.
     * <p>
     * @param totalAmount BigDecimal
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * 获取箱号.
     * <p>
     * @return boxNo
     */
    public String getBoxNo() {
        return boxNo;
    }

    /**
     * 设置箱号.
     * <p>
     * @param boxNo String
     */
    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
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
}