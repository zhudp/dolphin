package com.hundsun.ctim.domain.custom;

import com.core.BaseEntity;
import java.util.Date;

/*Record=[{ name:"customId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "ID",dataIndex : 'customId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : 'ID',id : 'customId',allowBlank : false})
/*Record=[{ name:"customName"},{ name:"shortName"},{ name:"customNo"},{ name:"customType"},{ name:"customStatus"},{ name:"customIndustry"},{ name:"shopNumber"},{ name:"shopNumberPlan"},{ name:"address"},{ name:"webSite"},{ name:"contacts1"},{ name:"contacts1Job"},{ name:"contacts1Phone1"},{ name:"contacts1Phone2"},{ name:"contacts1Remark"},{ name:"contacts2"},{ name:"contacts2Job"},{ name:"contacts2Phone1"},{ name:"contacts2Phone2"},{ name:"contacts2Remark"},{ name:"officer"},{ name:"remark"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "客户名称",dataIndex : 'customName',width : 60 },{header : "客户简称",dataIndex : 'shortName',width : 60 },{header : "客户编号",dataIndex : 'customNo',width : 60 },{header : "客户类别：潜在客户、普通客户、重要客户、失效客户",dataIndex : 'customType',width : 60 },{header : "合作阶段：意向跟踪、打样谈判、大货生产",dataIndex : 'customStatus',width : 60 },{header : "客户所属行业：服装、鞋包、百货、道具",dataIndex : 'customIndustry',width : 60 },{header : "目前店铺数量",dataIndex : 'shopNumber',width : 60 },{header : "计划店铺数量",dataIndex : 'shopNumberPlan',width : 60 },{header : "地址",dataIndex : 'address',width : 60 },{header : "网址",dataIndex : 'webSite',width : 60 },{header : "联系人1",dataIndex : 'contacts1',width : 60 },{header : "联系人1_职务",dataIndex : 'contacts1Job',width : 60 },{header : "联系人1_电话1",dataIndex : 'contacts1Phone1',width : 60 },{header : "联系人1_电话2",dataIndex : 'contacts1Phone2',width : 60 },{header : "联系人1_备注",dataIndex : 'contacts1Remark',width : 60 },{header : "联系人2",dataIndex : 'contacts2',width : 60 },{header : "联系人2_职务",dataIndex : 'contacts2Job',width : 60 },{header : "联系人2_电话1",dataIndex : 'contacts2Phone1',width : 60 },{header : "联系人2_电话2",dataIndex : 'contacts2Phone2',width : 60 },{header : "联系人2_备注",dataIndex : 'contacts2Remark',width : 60 },{header : "客户经理",dataIndex : 'officer',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 },{header : "null",dataIndex : 'gmtCreate',width : 60 },{header : "创建人",dataIndex : 'creator',width : 60 },{header : "创建人ID",dataIndex : 'creatorId',width : 60 },{header : "更新时间",dataIndex : 'gmtModify',width : 60 },{header : "null",dataIndex : 'modifier',width : 60 },{header : "null",dataIndex : 'modifierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '客户名称',id : 'customName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '客户简称',id : 'shortName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '客户编号',id : 'customNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '客户类别：潜在客户、普通客户、重要客户、失效客户',id : 'customType',allowBlank : false}),new Ext.form.TextField({fieldLabel : '合作阶段：意向跟踪、打样谈判、大货生产',id : 'customStatus',allowBlank : false}),new Ext.form.TextField({fieldLabel : '客户所属行业：服装、鞋包、百货、道具',id : 'customIndustry',allowBlank : false}),new Ext.form.TextField({fieldLabel : '目前店铺数量',id : 'shopNumber',allowBlank : false}),new Ext.form.TextField({fieldLabel : '计划店铺数量',id : 'shopNumberPlan',allowBlank : false}),new Ext.form.TextField({fieldLabel : '地址',id : 'address',allowBlank : false}),new Ext.form.TextField({fieldLabel : '网址',id : 'webSite',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人1',id : 'contacts1',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人1_职务',id : 'contacts1Job',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人1_电话1',id : 'contacts1Phone1',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人1_电话2',id : 'contacts1Phone2',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人1_备注',id : 'contacts1Remark',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人2',id : 'contacts2',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人2_职务',id : 'contacts2Job',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人2_电话1',id : 'contacts2Phone1',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人2_电话2',id : 'contacts2Phone2',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人2_备注',id : 'contacts2Remark',allowBlank : false}),new Ext.form.TextField({fieldLabel : '客户经理',id : 'officer',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人ID',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新时间',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'modifierId',allowBlank : false})
/**
 * Custom Entity类<br>.
 * @author abator Sun Jan 20 00:24:49 CST 2013
 */
public class Custom extends BaseEntity {
    private static final long serialVersionUID = -4573989124932316510L;

    /** ID */
    private Long customId;

    /** 客户名称 */
    private String customName;

    /** 客户简称 */
    private String shortName;

    /** 客户编号 */
    private String customNo;

    /** 客户类别：潜在客户、普通客户、重要客户、失效客户 */
    private String customType;

    /** 合作阶段：意向跟踪、打样谈判、大货生产 */
    private String customStatus;

    /** 客户所属行业：服装、鞋包、百货、道具 */
    private String customIndustry;

    /** 目前店铺数量 */
    private Integer shopNumber;

    /** 计划店铺数量 */
    private Integer shopNumberPlan;

    /** 地址 */
    private String address;

    /** 网址 */
    private String webSite;

    /** 联系人1 */
    private String contacts1;

    /** 联系人1_职务 */
    private String contacts1Job;

    /** 联系人1_电话1 */
    private String contacts1Phone1;

    /** 联系人1_电话2 */
    private String contacts1Phone2;

    /** 联系人1_备注 */
    private String contacts1Remark;

    /** 联系人2 */
    private String contacts2;

    /** 联系人2_职务 */
    private String contacts2Job;

    /** 联系人2_电话1 */
    private String contacts2Phone1;

    /** 联系人2_电话2 */
    private String contacts2Phone2;

    /** 联系人2_备注 */
    private String contacts2Remark;

    /** 客户经理 */
    private String officer;
    
    /** 业务发展员 */
    private String salesman;

    /** 备注 */
    private String remark;

    /** gmtCreate */
    private Date gmtCreate;

    /** 创建人 */
    private String creator;

    /** 创建人ID */
    private Long creatorId;

    /** 更新时间 */
    private Date gmtModify;

    /** modifier */
    private String modifier;

    /** modifierId */
    private Long modifierId;
    
    /** '0' 未删除 ‘1’ 已删除 */
    private String isDeleted;

    /**
     * 获取ID.
     * <p>
     * @return customId
     */
    public Long getCustomId() {
        return customId;
    }

    /**
     * 设置ID.
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
     * 获取客户简称.
     * <p>
     * @return shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 设置客户简称.
     * <p>
     * @param shortName String
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
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
     * 获取客户类别：潜在客户、普通客户、重要客户、失效客户.
     * <p>
     * @return customType
     */
    public String getCustomType() {
        return customType;
    }

    /**
     * 设置客户类别：潜在客户、普通客户、重要客户、失效客户.
     * <p>
     * @param customType String
     */
    public void setCustomType(String customType) {
        this.customType = customType;
    }

    /**
     * 获取合作阶段：意向跟踪、打样谈判、大货生产.
     * <p>
     * @return customStatus
     */
    public String getCustomStatus() {
        return customStatus;
    }

    /**
     * 设置合作阶段：意向跟踪、打样谈判、大货生产.
     * <p>
     * @param customStatus String
     */
    public void setCustomStatus(String customStatus) {
        this.customStatus = customStatus;
    }

    /**
     * 获取客户所属行业：服装、鞋包、百货、道具.
     * <p>
     * @return customIndustry
     */
    public String getCustomIndustry() {
        return customIndustry;
    }

    /**
     * 设置客户所属行业：服装、鞋包、百货、道具.
     * <p>
     * @param customIndustry String
     */
    public void setCustomIndustry(String customIndustry) {
        this.customIndustry = customIndustry;
    }

    /**
     * 获取目前店铺数量.
     * <p>
     * @return shopNumber
     */
    public Integer getShopNumber() {
        return shopNumber;
    }

    /**
     * 设置目前店铺数量.
     * <p>
     * @param shopNumber Integer
     */
    public void setShopNumber(Integer shopNumber) {
        this.shopNumber = shopNumber;
    }

    /**
     * 获取计划店铺数量.
     * <p>
     * @return shopNumberPlan
     */
    public Integer getShopNumberPlan() {
        return shopNumberPlan;
    }

    /**
     * 设置计划店铺数量.
     * <p>
     * @param shopNumberPlan Integer
     */
    public void setShopNumberPlan(Integer shopNumberPlan) {
        this.shopNumberPlan = shopNumberPlan;
    }

    /**
     * 获取地址.
     * <p>
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址.
     * <p>
     * @param address String
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取网址.
     * <p>
     * @return webSite
     */
    public String getWebSite() {
        return webSite;
    }

    /**
     * 设置网址.
     * <p>
     * @param webSite String
     */
    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    /**
     * 获取联系人1.
     * <p>
     * @return contacts1
     */
    public String getContacts1() {
        return contacts1;
    }

    /**
     * 设置联系人1.
     * <p>
     * @param contacts1 String
     */
    public void setContacts1(String contacts1) {
        this.contacts1 = contacts1;
    }

    /**
     * 获取联系人1_职务.
     * <p>
     * @return contacts1Job
     */
    public String getContacts1Job() {
        return contacts1Job;
    }

    /**
     * 设置联系人1_职务.
     * <p>
     * @param contacts1Job String
     */
    public void setContacts1Job(String contacts1Job) {
        this.contacts1Job = contacts1Job;
    }

    /**
     * 获取联系人1_电话1.
     * <p>
     * @return contacts1Phone1
     */
    public String getContacts1Phone1() {
        return contacts1Phone1;
    }

    /**
     * 设置联系人1_电话1.
     * <p>
     * @param contacts1Phone1 String
     */
    public void setContacts1Phone1(String contacts1Phone1) {
        this.contacts1Phone1 = contacts1Phone1;
    }

    /**
     * 获取联系人1_电话2.
     * <p>
     * @return contacts1Phone2
     */
    public String getContacts1Phone2() {
        return contacts1Phone2;
    }

    /**
     * 设置联系人1_电话2.
     * <p>
     * @param contacts1Phone2 String
     */
    public void setContacts1Phone2(String contacts1Phone2) {
        this.contacts1Phone2 = contacts1Phone2;
    }

    /**
     * 获取联系人1_备注.
     * <p>
     * @return contacts1Remark
     */
    public String getContacts1Remark() {
        return contacts1Remark;
    }

    /**
     * 设置联系人1_备注.
     * <p>
     * @param contacts1Remark String
     */
    public void setContacts1Remark(String contacts1Remark) {
        this.contacts1Remark = contacts1Remark;
    }

    /**
     * 获取联系人2.
     * <p>
     * @return contacts2
     */
    public String getContacts2() {
        return contacts2;
    }

    /**
     * 设置联系人2.
     * <p>
     * @param contacts2 String
     */
    public void setContacts2(String contacts2) {
        this.contacts2 = contacts2;
    }

    /**
     * 获取联系人2_职务.
     * <p>
     * @return contacts2Job
     */
    public String getContacts2Job() {
        return contacts2Job;
    }

    /**
     * 设置联系人2_职务.
     * <p>
     * @param contacts2Job String
     */
    public void setContacts2Job(String contacts2Job) {
        this.contacts2Job = contacts2Job;
    }

    /**
     * 获取联系人2_电话1.
     * <p>
     * @return contacts2Phone1
     */
    public String getContacts2Phone1() {
        return contacts2Phone1;
    }

    /**
     * 设置联系人2_电话1.
     * <p>
     * @param contacts2Phone1 String
     */
    public void setContacts2Phone1(String contacts2Phone1) {
        this.contacts2Phone1 = contacts2Phone1;
    }

    /**
     * 获取联系人2_电话2.
     * <p>
     * @return contacts2Phone2
     */
    public String getContacts2Phone2() {
        return contacts2Phone2;
    }

    /**
     * 设置联系人2_电话2.
     * <p>
     * @param contacts2Phone2 String
     */
    public void setContacts2Phone2(String contacts2Phone2) {
        this.contacts2Phone2 = contacts2Phone2;
    }

    /**
     * 获取联系人2_备注.
     * <p>
     * @return contacts2Remark
     */
    public String getContacts2Remark() {
        return contacts2Remark;
    }

    /**
     * 设置联系人2_备注.
     * <p>
     * @param contacts2Remark String
     */
    public void setContacts2Remark(String contacts2Remark) {
        this.contacts2Remark = contacts2Remark;
    }

    /**
     * 获取客户经理.
     * <p>
     * @return officer
     */
    public String getOfficer() {
        return officer;
    }

    /**
     * 设置客户经理.
     * <p>
     * @param officer String
     */
    public void setOfficer(String officer) {
        this.officer = officer;
    }
    
    /**
     * 获取业务发展员.
     * <p>
     * @return officer
     */
    public String getSalesman() {
    	return salesman;
    }
    
    /**
     * 设置业务发展员.
     * <p>
     * @param officer String
     */
    public void setSalesman(String salesman) {
    	this.salesman = salesman;
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