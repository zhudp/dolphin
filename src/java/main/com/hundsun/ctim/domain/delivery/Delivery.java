package com.hundsun.ctim.domain.delivery;

import com.core.BaseEntity;
import java.util.Date;

/*Record=[{ name:"deliveryId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "发货单ID",dataIndex : 'deliveryId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '发货单ID',id : 'deliveryId',allowBlank : false})
/*Record=[{ name:"deliveryNo"},{ name:"orderId"},{ name:"customId"},{ name:"deliveryDate"},{ name:"deliveryAddress"},{ name:"customContacts"},{ name:"customContactsPhone"},{ name:"transportType"},{ name:"officer"},{ name:"officerPhone"},{ name:"remark"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "发货单编号",dataIndex : 'deliveryNo',width : 60 },{header : "订单ID",dataIndex : 'orderId',width : 60 },{header : "客户ID",dataIndex : 'customId',width : 60 },{header : "发货日期",dataIndex : 'deliveryDate',width : 60 },{header : "收款地址",dataIndex : 'deliveryAddress',width : 60 },{header : "客户联系人",dataIndex : 'customContacts',width : 60 },{header : "客户联系人电话",dataIndex : 'customContactsPhone',width : 60 },{header : "运送方式",dataIndex : 'transportType',width : 60 },{header : "经办人",dataIndex : 'officer',width : 60 },{header : "经办人电话",dataIndex : 'officerPhone',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '发货单编号',id : 'deliveryNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '订单ID',id : 'orderId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '客户ID',id : 'customId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '发货日期',id : 'deliveryDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '收款地址',id : 'deliveryAddress',allowBlank : false}),new Ext.form.TextField({fieldLabel : '客户联系人',id : 'customContacts',allowBlank : false}),new Ext.form.TextField({fieldLabel : '客户联系人电话',id : 'customContactsPhone',allowBlank : false}),new Ext.form.TextField({fieldLabel : '运送方式',id : 'transportType',allowBlank : false}),new Ext.form.TextField({fieldLabel : '经办人',id : 'officer',allowBlank : false}),new Ext.form.TextField({fieldLabel : '经办人电话',id : 'officerPhone',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false})
/**
 * Delivery Entity类<br>.
 * @author abator Sun Jan 20 00:24:51 CST 2013
 */
public class Delivery extends BaseEntity {
    private static final long serialVersionUID = 1629142925619065114L;

    /** 发货单ID */
    private Long deliveryId;

    /** 发货单编号 */
    private String deliveryNo;

    /** 订单ID */
    private Long orderId;

    /** 客户ID */
    private Long customId;

    /** 发货日期 */
    private Date deliveryDate;

    /** 收款地址 */
    private String deliveryAddress;

    /** 客户联系人 */
    private String customContacts;

    /** 客户联系人电话 */
    private String customContactsPhone;

    /** 运送方式 */
    private String transportType;

    /** 经办人 */
    private String officer;

    /** 经办人电话 */
    private String officerPhone;

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
     * 获取发货单编号.
     * <p>
     * @return deliveryNo
     */
    public String getDeliveryNo() {
        return deliveryNo;
    }

    /**
     * 设置发货单编号.
     * <p>
     * @param deliveryNo String
     */
    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
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
     * 获取发货日期.
     * <p>
     * @return deliveryDate
     */
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * 设置发货日期.
     * <p>
     * @param deliveryDate Date
     */
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     * 获取收款地址.
     * <p>
     * @return deliveryAddress
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * 设置收款地址.
     * <p>
     * @param deliveryAddress String
     */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * 获取客户联系人.
     * <p>
     * @return customContacts
     */
    public String getCustomContacts() {
        return customContacts;
    }

    /**
     * 设置客户联系人.
     * <p>
     * @param customContacts String
     */
    public void setCustomContacts(String customContacts) {
        this.customContacts = customContacts;
    }

    /**
     * 获取客户联系人电话.
     * <p>
     * @return customContactsPhone
     */
    public String getCustomContactsPhone() {
        return customContactsPhone;
    }

    /**
     * 设置客户联系人电话.
     * <p>
     * @param customContactsPhone String
     */
    public void setCustomContactsPhone(String customContactsPhone) {
        this.customContactsPhone = customContactsPhone;
    }

    /**
     * 获取运送方式.
     * <p>
     * @return transportType
     */
    public String getTransportType() {
        return transportType;
    }

    /**
     * 设置运送方式.
     * <p>
     * @param transportType String
     */
    public void setTransportType(String transportType) {
        this.transportType = transportType;
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
     * 获取经办人电话.
     * <p>
     * @return officerPhone
     */
    public String getOfficerPhone() {
        return officerPhone;
    }

    /**
     * 设置经办人电话.
     * <p>
     * @param officerPhone String
     */
    public void setOfficerPhone(String officerPhone) {
        this.officerPhone = officerPhone;
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