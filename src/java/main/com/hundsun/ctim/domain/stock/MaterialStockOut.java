package com.hundsun.ctim.domain.stock;

import com.core.BaseEntity;
import java.util.Date;

/*Record=[{ name:"stockOutId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "null",dataIndex : 'stockOutId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : 'null',id : 'stockOutId',allowBlank : false})
/*Record=[{ name:"stockOutNo"},{ name:"orderId"},{ name:"orderNo"},{ name:"departmentId"},{ name:"departmentName"},{ name:"officer"},{ name:"remark"},{ name:"stockOutDate"},{ name:"isDeleted"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "null",dataIndex : 'stockOutNo',width : 60 },{header : "null",dataIndex : 'orderId',width : 60 },{header : "null",dataIndex : 'orderNo',width : 60 },{header : "null",dataIndex : 'departmentId',width : 60 },{header : "null",dataIndex : 'departmentName',width : 60 },{header : "null",dataIndex : 'officer',width : 60 },{header : "null",dataIndex : 'remark',width : 60 },{header : "null",dataIndex : 'stockOutDate',width : 60 },{header : "null",dataIndex : 'isDeleted',width : 60 },{header : "null",dataIndex : 'gmtCreate',width : 60 },{header : "null",dataIndex : 'creator',width : 60 },{header : "null",dataIndex : 'creatorId',width : 60 },{header : "null",dataIndex : 'gmtModify',width : 60 },{header : "null",dataIndex : 'modifier',width : 60 },{header : "null",dataIndex : 'modifierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : 'null',id : 'stockOutNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'orderId',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'orderNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'departmentId',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'departmentName',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'officer',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'remark',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'stockOutDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'isDeleted',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'modifierId',allowBlank : false})
/**
 * TbMaterialStockOut Entity类<br>.
 * @author abator Wed Jan 23 21:41:51 CST 2013
 */
public class MaterialStockOut extends BaseEntity {
    private static final long serialVersionUID = -7070060964887306065L;

    /** stockOutId */
    private Long stockOutId;

    /** stockOutNo */
    private String stockOutNo;

    /** orderId */
    private Long orderId;

    /** orderNo */
    private String orderNo;

    /** departmentId */
    private Long departmentId;

    /** departmentName */
    private String departmentName;

    /** officer */
    private String officer;

    /** remark */
    private String remark;

    /** stockOutDate */
    private Date stockOutDate;

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
     * 获取stockOutId.
     * <p>
     * @return stockOutId
     */
    public Long getStockOutId() {
        return stockOutId;
    }

    /**
     * 设置stockOutId.
     * <p>
     * @param stockOutId Long
     */
    public void setStockOutId(Long stockOutId) {
        this.stockOutId = stockOutId;
    }

    /**
     * 获取stockOutNo.
     * <p>
     * @return stockOutNo
     */
    public String getStockOutNo() {
        return stockOutNo;
    }

    /**
     * 设置stockOutNo.
     * <p>
     * @param stockOutNo String
     */
    public void setStockOutNo(String stockOutNo) {
        this.stockOutNo = stockOutNo;
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
     * 获取departmentId.
     * <p>
     * @return departmentId
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * 设置departmentId.
     * <p>
     * @param departmentId Long
     */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * 获取departmentName.
     * <p>
     * @return departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * 设置departmentName.
     * <p>
     * @param departmentName String
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * 获取officer.
     * <p>
     * @return officer
     */
    public String getOfficer() {
        return officer;
    }

    /**
     * 设置officer.
     * <p>
     * @param officer String
     */
    public void setOfficer(String officer) {
        this.officer = officer;
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
     * 获取stockOutDate.
     * <p>
     * @return stockOutDate
     */
    public Date getStockOutDate() {
        return stockOutDate;
    }

    /**
     * 设置stockOutDate.
     * <p>
     * @param stockOutDate Date
     */
    public void setStockOutDate(Date stockOutDate) {
        this.stockOutDate = stockOutDate;
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