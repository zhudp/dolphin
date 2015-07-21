package com.hundsun.ctim.domain.stock;

import com.core.BaseEntity;
import java.util.Date;

/*Record=[{ name:"stockInId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "id",dataIndex : 'stockInId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : 'id',id : 'stockInId',allowBlank : false})
/*Record=[{ name:"stockInNo"},{ name:"orderId"},{ name:"orderNo"},{ name:"ordersDate"},{ name:"stockInDate"},{ name:"officer"},{ name:"remark"},{ name:"isDeleted"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "入库id",dataIndex : 'stockInNo',width : 60 },{header : "入库编号",dataIndex : 'orderId',width : 60 },{header : "订单编号",dataIndex : 'orderNo',width : 60 },{header : "下单日期",dataIndex : 'ordersDate',width : 60 },{header : "入库日期",dataIndex : 'stockInDate',width : 60 },{header : "经办人",dataIndex : 'officer',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 },{header : "删除标志",dataIndex : 'isDeleted',width : 60 },{header : "创建时间",dataIndex : 'gmtCreate',width : 60 },{header : "创建人",dataIndex : 'creator',width : 60 },{header : "创建人ID",dataIndex : 'creatorId',width : 60 },{header : "更新时间",dataIndex : 'gmtModify',width : 60 },{header : "修改人",dataIndex : 'modifier',width : 60 },{header : "修改人编号",dataIndex : 'modifierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '入库id',id : 'stockInNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '入库编号',id : 'orderId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '订单编号',id : 'orderNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '下单日期',id : 'ordersDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '入库日期',id : 'stockInDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '经办人',id : 'officer',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false}),new Ext.form.TextField({fieldLabel : '删除标志',id : 'isDeleted',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建时间',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人ID',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新时间',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : '修改人',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : '修改人编号',id : 'modifierId',allowBlank : false})
/**
 * TbProductStockIn Entity类<br>.
 * @author abator Mon Feb 04 21:08:33 CST 2013
 */
public class ProductStockIn extends BaseEntity {
    private static final long serialVersionUID = 9082455082060833097L;

    /** id */
    private Long stockInId;

    /** 入库id */
    private String stockInNo;

    /** 入库编号 */
    private Long orderId;

    /** 订单编号 */
    private String orderNo;

    /** 下单日期 */
    private Date ordersDate;

    /** 入库日期 */
    private Date stockInDate;

    /** 经办人 */
    private String officer;

    /** 备注 */
    private String remark;

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
     * @return stockInId
     */
    public Long getStockInId() {
        return stockInId;
    }

    /**
     * 设置id.
     * <p>
     * @param stockInId Long
     */
    public void setStockInId(Long stockInId) {
        this.stockInId = stockInId;
    }

    /**
     * 获取入库id.
     * <p>
     * @return stockInNo
     */
    public String getStockInNo() {
        return stockInNo;
    }

    /**
     * 设置入库id.
     * <p>
     * @param stockInNo String
     */
    public void setStockInNo(String stockInNo) {
        this.stockInNo = stockInNo;
    }

    /**
     * 获取入库编号.
     * <p>
     * @return orderId
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置入库编号.
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
     * 获取下单日期.
     * <p>
     * @return ordersDate
     */
    public Date getOrdersDate() {
        return ordersDate;
    }

    /**
     * 设置下单日期.
     * <p>
     * @param ordersDate Date
     */
    public void setOrdersDate(Date ordersDate) {
        this.ordersDate = ordersDate;
    }

    /**
     * 获取入库日期.
     * <p>
     * @return stockInDate
     */
    public Date getStockInDate() {
        return stockInDate;
    }

    /**
     * 设置入库日期.
     * <p>
     * @param stockInDate Date
     */
    public void setStockInDate(Date stockInDate) {
        this.stockInDate = stockInDate;
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