package com.hundsun.ctim.domain.stock;

import com.core.BaseEntity;
import java.util.Date;

/*Record=[{ name:"stockInId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "null",dataIndex : 'stockInId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : 'null',id : 'stockInId',allowBlank : false})
/*Record=[{ name:"stockInNo"},{ name:"orderId"},{ name:"orderNo"},{ name:"supplierId"},{ name:"supplierName"},{ name:"supplierNo"},{ name:"purchaseDate"},{ name:"ordersDate"},{ name:"stockInDate"},{ name:"supplierOfficer"},{ name:"purchaseOfficer"},{ name:"remark"},{ name:"isDeleted"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "null",dataIndex : 'stockInNo',width : 60 },{header : "null",dataIndex : 'orderId',width : 60 },{header : "null",dataIndex : 'orderNo',width : 60 },{header : "null",dataIndex : 'supplierId',width : 60 },{header : "null",dataIndex : 'supplierName',width : 60 },{header : "null",dataIndex : 'supplierNo',width : 60 },{header : "null",dataIndex : 'purchaseDate',width : 60 },{header : "null",dataIndex : 'ordersDate',width : 60 },{header : "null",dataIndex : 'stockInDate',width : 60 },{header : "null",dataIndex : 'supplierOfficer',width : 60 },{header : "null",dataIndex : 'purchaseOfficer',width : 60 },{header : "null",dataIndex : 'remark',width : 60 },{header : "null",dataIndex : 'isDeleted',width : 60 },{header : "null",dataIndex : 'gmtCreate',width : 60 },{header : "null",dataIndex : 'creator',width : 60 },{header : "null",dataIndex : 'creatorId',width : 60 },{header : "null",dataIndex : 'gmtModify',width : 60 },{header : "null",dataIndex : 'modifier',width : 60 },{header : "null",dataIndex : 'modifierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : 'null',id : 'stockInNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'orderId',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'orderNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'supplierId',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'supplierName',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'supplierNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'purchaseDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'ordersDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'stockInDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'supplierOfficer',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'purchaseOfficer',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'remark',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'isDeleted',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'modifierId',allowBlank : false})
/**
 * TbMaterialStockIn Entity类<br>.
 * @author abator Wed Jan 23 21:41:48 CST 2013
 */
public class MaterialStockIn extends BaseEntity {
    private static final long serialVersionUID = -8744113258264720486L;

    /** stockInId */
    private Long stockInId;

    /** stockInNo */
    private String stockInNo;

    /** orderId */
    private Long orderId;

    /** orderNo */
    private String orderNo;

    /** supplierId */
    private Long supplierId;

    /** supplierName */
    private String supplierName;

    /** supplierNo */
    private String supplierNo;

    /** purchaseDate */
    private Date purchaseDate;

    /** ordersDate */
    private Date ordersDate;

    /** stockInDate */
    private Date stockInDate;

    /** supplierOfficer */
    private String supplierOfficer;

    /** purchaseOfficer */
    private String purchaseOfficer;

    /** remark */
    private String remark;

    /** isDeleted */
    private String isDeleted ="0";

    /** gmtCreate */
    private Date gmtCreate;

    /** creator */
    private String creator;

    /** creatorId */
    private Long creatorId;

    /** gmtModify */
    private Date gmtModify;

    /** modifier */
    private String modifier;

    /** modifierId */
    private Long modifierId;

    /**
     * 获取stockInId.
     * <p>
     * @return stockInId
     */
    public Long getStockInId() {
        return stockInId;
    }

    /**
     * 设置stockInId.
     * <p>
     * @param stockInId Long
     */
    public void setStockInId(Long stockInId) {
        this.stockInId = stockInId;
    }

    /**
     * 获取stockInNo.
     * <p>
     * @return stockInNo
     */
    public String getStockInNo() {
        return stockInNo;
    }

    /**
     * 设置stockInNo.
     * <p>
     * @param stockInNo String
     */
    public void setStockInNo(String stockInNo) {
        this.stockInNo = stockInNo;
    }

    /**
     * 获取orderId.
     * <p>
     * @return orderId
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置orderId.
     * <p>
     * @param orderId Long
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取orderNo.
     * <p>
     * @return orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置orderNo.
     * <p>
     * @param orderNo String
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取supplierId.
     * <p>
     * @return supplierId
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * 设置supplierId.
     * <p>
     * @param supplierId Long
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 获取supplierName.
     * <p>
     * @return supplierName
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 设置supplierName.
     * <p>
     * @param supplierName String
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 获取supplierNo.
     * <p>
     * @return supplierNo
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 设置supplierNo.
     * <p>
     * @param supplierNo String
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 获取purchaseDate.
     * <p>
     * @return purchaseDate
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * 设置purchaseDate.
     * <p>
     * @param purchaseDate Date
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * 获取ordersDate.
     * <p>
     * @return ordersDate
     */
    public Date getOrdersDate() {
        return ordersDate;
    }

    /**
     * 设置ordersDate.
     * <p>
     * @param ordersDate Date
     */
    public void setOrdersDate(Date ordersDate) {
        this.ordersDate = ordersDate;
    }

    /**
     * 获取stockInDate.
     * <p>
     * @return stockInDate
     */
    public Date getStockInDate() {
        return stockInDate;
    }

    /**
     * 设置stockInDate.
     * <p>
     * @param stockInDate Date
     */
    public void setStockInDate(Date stockInDate) {
        this.stockInDate = stockInDate;
    }

    /**
     * 获取supplierOfficer.
     * <p>
     * @return supplierOfficer
     */
    public String getSupplierOfficer() {
        return supplierOfficer;
    }

    /**
     * 设置supplierOfficer.
     * <p>
     * @param supplierOfficer String
     */
    public void setSupplierOfficer(String supplierOfficer) {
        this.supplierOfficer = supplierOfficer;
    }

    /**
     * 获取purchaseOfficer.
     * <p>
     * @return purchaseOfficer
     */
    public String getPurchaseOfficer() {
        return purchaseOfficer;
    }

    /**
     * 设置purchaseOfficer.
     * <p>
     * @param purchaseOfficer String
     */
    public void setPurchaseOfficer(String purchaseOfficer) {
        this.purchaseOfficer = purchaseOfficer;
    }

    /**
     * 获取remark.
     * <p>
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置remark.
     * <p>
     * @param remark String
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取isDeleted.
     * <p>
     * @return isDeleted
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * 设置isDeleted.
     * <p>
     * @param isDeleted String
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 获取gmtCreate.
     * <p>
     * @return gmtCreate
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置gmtCreate.
     * <p>
     * @param gmtCreate Date
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取creator.
     * <p>
     * @return creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置creator.
     * <p>
     * @param creator String
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取creatorId.
     * <p>
     * @return creatorId
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * 设置creatorId.
     * <p>
     * @param creatorId Long
     */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取gmtModify.
     * <p>
     * @return gmtModify
     */
    public Date getGmtModify() {
        return gmtModify;
    }

    /**
     * 设置gmtModify.
     * <p>
     * @param gmtModify Date
     */
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    /**
     * 获取modifier.
     * <p>
     * @return modifier
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置modifier.
     * <p>
     * @param modifier String
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * 获取modifierId.
     * <p>
     * @return modifierId
     */
    public Long getModifierId() {
        return modifierId;
    }

    /**
     * 设置modifierId.
     * <p>
     * @param modifierId Long
     */
    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }
}