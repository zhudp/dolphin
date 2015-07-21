package com.hundsun.ctim.domain.stock;

import com.core.BaseEntity;
import java.util.Date;

/*Record=[{ name:"stockOutId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "id",dataIndex : 'stockOutId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : 'id',id : 'stockOutId',allowBlank : false})
/*Record=[{ name:"stockOutNo"},{ name:"orderId"},{ name:"orderNo"},{ name:"officer"},{ name:"remark"},{ name:"stockOutDate"},{ name:"isDeleted"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "出库编号",dataIndex : 'stockOutNo',width : 60 },{header : "订单id",dataIndex : 'orderId',width : 60 },{header : "订单编号",dataIndex : 'orderNo',width : 60 },{header : "经办人",dataIndex : 'officer',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 },{header : "出库日期",dataIndex : 'stockOutDate',width : 60 },{header : "删除标志",dataIndex : 'isDeleted',width : 60 },{header : "创建时间",dataIndex : 'gmtCreate',width : 60 },{header : "创建人",dataIndex : 'creator',width : 60 },{header : "创建人ID",dataIndex : 'creatorId',width : 60 },{header : "更新时间",dataIndex : 'gmtModify',width : 60 },{header : "修改人",dataIndex : 'modifier',width : 60 },{header : "修改人编号",dataIndex : 'modifierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '出库编号',id : 'stockOutNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '订单id',id : 'orderId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '订单编号',id : 'orderNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '经办人',id : 'officer',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false}),new Ext.form.TextField({fieldLabel : '出库日期',id : 'stockOutDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '删除标志',id : 'isDeleted',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建时间',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人ID',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新时间',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : '修改人',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : '修改人编号',id : 'modifierId',allowBlank : false})
/**
 * TbProductStockOut Entity类<br>.
 * @author abator Mon Feb 04 21:08:36 CST 2013
 */
public class ProductStockOut extends BaseEntity {
    private static final long serialVersionUID = 649954215658015689L;

    /** id */
    private Long stockOutId;

    /** 出库编号 */
    private String stockOutNo;

    /** 订单id */
    private Long orderId;

    /** 订单编号 */
    private String orderNo;

    /** 经办人 */
    private String officer;

    /** 备注 */
    private String remark;

    /** 出库日期 */
    private Date stockOutDate;

    /** 删除标志 */
    private String isDeleted = "0";

    /** 创建时间 */
    private Date gmtCreate;

    /** 创建人 */
    private String creator;

    /** 创建人ID */
    private Long creatorId;

    /** 更新时间 */
    private Date gmtModify;

    /** 修改人 */
    private String modifier;

    /** 修改人编号 */
    private Long modifierId;

    /**
     * 获取id.
     * <p>
     * @return stockOutId
     */
    public Long getStockOutId() {
        return stockOutId;
    }

    /**
     * 设置id.
     * <p>
     * @param stockOutId Long
     */
    public void setStockOutId(Long stockOutId) {
        this.stockOutId = stockOutId;
    }

    /**
     * 获取出库编号.
     * <p>
     * @return stockOutNo
     */
    public String getStockOutNo() {
        return stockOutNo;
    }

    /**
     * 设置出库编号.
     * <p>
     * @param stockOutNo String
     */
    public void setStockOutNo(String stockOutNo) {
        this.stockOutNo = stockOutNo;
    }

    /**
     * 获取订单id.
     * <p>
     * @return orderId
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id.
     * <p>
     * @param orderId Long
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取订单编号.
     * <p>
     * @return orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置订单编号.
     * <p>
     * @param orderNo String
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取经办人.
     * <p>
     * @return officer
     */
    public String getOfficer() {
        return officer;
    }

    /**
     * 设置经办人.
     * <p>
     * @param officer String
     */
    public void setOfficer(String officer) {
        this.officer = officer;
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
     * 获取出库日期.
     * <p>
     * @return stockOutDate
     */
    public Date getStockOutDate() {
        return stockOutDate;
    }

    /**
     * 设置出库日期.
     * <p>
     * @param stockOutDate Date
     */
    public void setStockOutDate(Date stockOutDate) {
        this.stockOutDate = stockOutDate;
    }

    /**
     * 获取删除标志.
     * <p>
     * @return isDeleted
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * 设置删除标志.
     * <p>
     * @param isDeleted String
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 获取创建时间.
     * <p>
     * @return gmtCreate
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置创建时间.
     * <p>
     * @param gmtCreate Date
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取创建人.
     * <p>
     * @return creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人.
     * <p>
     * @param creator String
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取创建人ID.
     * <p>
     * @return creatorId
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人ID.
     * <p>
     * @param creatorId Long
     */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取更新时间.
     * <p>
     * @return gmtModify
     */
    public Date getGmtModify() {
        return gmtModify;
    }

    /**
     * 设置更新时间.
     * <p>
     * @param gmtModify Date
     */
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    /**
     * 获取修改人.
     * <p>
     * @return modifier
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置修改人.
     * <p>
     * @param modifier String
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * 获取修改人编号.
     * <p>
     * @return modifierId
     */
    public Long getModifierId() {
        return modifierId;
    }

    /**
     * 设置修改人编号.
     * <p>
     * @param modifierId Long
     */
    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }
}