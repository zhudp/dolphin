package com.hundsun.ctim.domain.order;

import com.core.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;

/*Record=[{ name:"orderId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "订单ID",dataIndex : 'orderId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '订单ID',id : 'orderId',allowBlank : false})
/*Record=[{ name:"orderNo"},{ name:"orderName"},{ name:"status"},{ name:"customId"},{ name:"customName"},{ name:"planDeliveryDate"},{ name:"deliveryAddress"},{ name:"orderPrice"},{ name:"officer"},{ name:"remark"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"},{ name:"customNo"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "订单编号",dataIndex : 'orderNo',width : 60 },{header : "订单名称（店铺名称/订单标记等）",dataIndex : 'orderName',width : 60 },{header : "订单状态：执行中、已发货、中止",dataIndex : 'status',width : 60 },{header : "客户ID",dataIndex : 'customId',width : 60 },{header : "客户名称",dataIndex : 'customName',width : 60 },{header : "发货日期",dataIndex : 'planDeliveryDate',width : 60 },{header : "发货地址",dataIndex : 'deliveryAddress',width : 60 },{header : "订单总价",dataIndex : 'orderPrice',width : 60 },{header : "经办人",dataIndex : 'officer',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 },{header : "创建时间",dataIndex : 'gmtCreate',width : 60 },{header : "创建人",dataIndex : 'creator',width : 60 },{header : "创建人ID",dataIndex : 'creatorId',width : 60 },{header : "更新时间",dataIndex : 'gmtModify',width : 60 },{header : "更新人",dataIndex : 'modifier',width : 60 },{header : "更新人ID",dataIndex : 'modifierId',width : 60 },{header : "客户编号",dataIndex : 'customNo',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '订单编号',id : 'orderNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '订单名称（店铺名称/订单标记等）',id : 'orderName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '订单状态：执行中、已发货、中止',id : 'status',allowBlank : false}),new Ext.form.TextField({fieldLabel : '客户ID',id : 'customId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '客户名称',id : 'customName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '发货日期',id : 'planDeliveryDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '发货地址',id : 'deliveryAddress',allowBlank : false}),new Ext.form.TextField({fieldLabel : '订单总价',id : 'orderPrice',allowBlank : false}),new Ext.form.TextField({fieldLabel : '经办人',id : 'officer',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建时间',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人ID',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新时间',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人ID',id : 'modifierId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '客户编号',id : 'customNo',allowBlank : false})
/**
 * TbOrder Entity类<br>.
 * @author abator Sun Jan 27 23:14:33 CST 2013
 */
public class Order extends BaseEntity {
    private static final long serialVersionUID = 5069137445730812975L;

    /** 订单ID */
    private Long orderId;

    /** 订单编号 */
    private String orderNo;

    /** 订单名称（店铺名称/订单标记等） */
    private String orderName;

    /** 订单状态：执行中、已发货、中止 */
    private String status;

    /** 客户ID */
    private Long customId;

    /** 客户编号 */
    private String customNo;
    
    /** 客户名称 */
    private String customName;

    /** 发货日期 */
    private Date planDeliveryDate;

    /** 发货地址 */
    private String deliveryAddress;

    /** 订单总价 */
    private BigDecimal orderPrice;

    /** 经办人 */
    private String officer;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    private Date gmtCreate;

    /** 创建人 */
    private String creator;

    /** 创建人ID */
    private Long creatorId;

    /** 更新时间 */
    private Date gmtModify;

    /** 更新人 */
    private String modifier;

    /** 更新人ID */
    private Long modifierId;

    
    /** '0' 未删除 ‘1’ 已删除 */
    private String isDeleted;

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
     * 获取订单名称（店铺名称/订单标记等）.
     * <p>
     * @return orderName
     */
    public String getOrderName() {
        return orderName;
    }

    /**
     * 设置订单名称（店铺名称/订单标记等）.
     * <p>
     * @param orderName String
     */
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    /**
     * 获取订单状态：执行中、已发货、中止.
     * <p>
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置订单状态：执行中、已发货、中止.
     * <p>
     * @param status String
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取客户ID.
     * <p>
     * @return customId
     */
    public Long getCustomId() {
        return customId;
    }

    /**
     * 设置客户ID.
     * <p>
     * @param customId Long
     */
    public void setCustomId(Long customId) {
        this.customId = customId;
    }

    /**
     * 获取客户名称.
     * <p>
     * @return customName
     */
    public String getCustomName() {
        return customName;
    }

    /**
     * 设置客户名称.
     * <p>
     * @param customName String
     */
    public void setCustomName(String customName) {
        this.customName = customName;
    }

    /**
     * 获取发货日期.
     * <p>
     * @return planDeliveryDate
     */
    public Date getPlanDeliveryDate() {
        return planDeliveryDate;
    }

    /**
     * 设置发货日期.
     * <p>
     * @param planDeliveryDate Date
     */
    public void setPlanDeliveryDate(Date planDeliveryDate) {
        this.planDeliveryDate = planDeliveryDate;
    }

    /**
     * 获取发货地址.
     * <p>
     * @return deliveryAddress
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * 设置发货地址.
     * <p>
     * @param deliveryAddress String
     */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * 获取订单总价.
     * <p>
     * @return orderPrice
     */
    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    /**
     * 设置订单总价.
     * <p>
     * @param orderPrice BigDecimal
     */
    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
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
     * 获取更新人.
     * <p>
     * @return modifier
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置更新人.
     * <p>
     * @param modifier String
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * 获取更新人ID.
     * <p>
     * @return modifierId
     */
    public Long getModifierId() {
        return modifierId;
    }

    /**
     * 设置更新人ID.
     * <p>
     * @param modifierId Long
     */
    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }

    /**
     * 获取客户编号.
     * <p>
     * @return customNo
     */
    public String getCustomNo() {
        return customNo;
    }

    /**
     * 设置客户编号.
     * <p>
     * @param customNo String
     */
    public void setCustomNo(String customNo) {
        this.customNo = customNo;
    }
    /**
     * 获取'0' 未删除 ‘1’ 已删除.
     * <p>
     * @return isDeleted
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * 设置'0' 未删除 ‘1’ 已删除.
     * <p>
     * @param isDeleted String
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
}