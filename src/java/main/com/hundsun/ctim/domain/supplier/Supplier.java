package com.hundsun.ctim.domain.supplier;

import com.core.BaseEntity;
import java.util.Date;

/*Record=[{ name:"supplierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "供应商ID",dataIndex : 'supplierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '供应商ID',id : 'supplierId',allowBlank : false})
/*Record=[{ name:"supplierName"},{ name:"supplierNo"},{ name:"supplyRange"},{ name:"supplyStatus"},{ name:"bankAccount"},{ name:"accountName"},{ name:"depositBank"},{ name:"address"},{ name:"webSite"},{ name:"contacts1"},{ name:"contacts1Job"},{ name:"contacts1Phone1"},{ name:"contacts1Phone2"},{ name:"contacts1Remark"},{ name:"contacts2"},{ name:"contacts2Job"},{ name:"contacts2Phone1"},{ name:"contacts2Phone2"},{ name:"contacts2Remark"},{ name:"officer"},{ name:"remark"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "供应商名称",dataIndex : 'supplierName',width : 60 },{header : "供应商编号",dataIndex : 'supplierNo',width : 60 },{header : "供应范围",dataIndex : 'supplyRange',width : 60 },{header : "合作阶段：价格谈判、正在合作、过期",dataIndex : 'supplyStatus',width : 60 },{header : "银行账号",dataIndex : 'bankAccount',width : 60 },{header : "银行账号户名",dataIndex : 'accountName',width : 60 },{header : "开户行",dataIndex : 'depositBank',width : 60 },{header : "地址",dataIndex : 'address',width : 60 },{header : "网址",dataIndex : 'webSite',width : 60 },{header : "联系人1",dataIndex : 'contacts1',width : 60 },{header : "联系人1_职务",dataIndex : 'contacts1Job',width : 60 },{header : "联系人1_电话1",dataIndex : 'contacts1Phone1',width : 60 },{header : "联系人1_电话2",dataIndex : 'contacts1Phone2',width : 60 },{header : "联系人1_备注",dataIndex : 'contacts1Remark',width : 60 },{header : "联系人2",dataIndex : 'contacts2',width : 60 },{header : "联系人2_职务",dataIndex : 'contacts2Job',width : 60 },{header : "联系人2_电话1",dataIndex : 'contacts2Phone1',width : 60 },{header : "联系人2_电话2",dataIndex : 'contacts2Phone2',width : 60 },{header : "联系人2_备注",dataIndex : 'contacts2Remark',width : 60 },{header : "客户经理",dataIndex : 'officer',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 },{header : "创建时间",dataIndex : 'gmtCreate',width : 60 },{header : "创建人",dataIndex : 'creator',width : 60 },{header : "创建人ID",dataIndex : 'creatorId',width : 60 },{header : "更新时间",dataIndex : 'gmtModify',width : 60 },{header : "null",dataIndex : 'modifier',width : 60 },{header : "null",dataIndex : 'modifierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '供应商名称',id : 'supplierName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '供应商编号',id : 'supplierNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '供应范围',id : 'supplyRange',allowBlank : false}),new Ext.form.TextField({fieldLabel : '合作阶段：价格谈判、正在合作、过期',id : 'supplyStatus',allowBlank : false}),new Ext.form.TextField({fieldLabel : '银行账号',id : 'bankAccount',allowBlank : false}),new Ext.form.TextField({fieldLabel : '银行账号户名',id : 'accountName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '开户行',id : 'depositBank',allowBlank : false}),new Ext.form.TextField({fieldLabel : '地址',id : 'address',allowBlank : false}),new Ext.form.TextField({fieldLabel : '网址',id : 'webSite',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人1',id : 'contacts1',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人1_职务',id : 'contacts1Job',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人1_电话1',id : 'contacts1Phone1',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人1_电话2',id : 'contacts1Phone2',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人1_备注',id : 'contacts1Remark',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人2',id : 'contacts2',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人2_职务',id : 'contacts2Job',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人2_电话1',id : 'contacts2Phone1',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人2_电话2',id : 'contacts2Phone2',allowBlank : false}),new Ext.form.TextField({fieldLabel : '联系人2_备注',id : 'contacts2Remark',allowBlank : false}),new Ext.form.TextField({fieldLabel : '客户经理',id : 'officer',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建时间',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人ID',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新时间',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'modifierId',allowBlank : false})
/**
 * Supplier Entity类<br>.
 * @author abator Sun Jan 20 00:28:39 CST 2013
 */
public class Supplier extends BaseEntity {
    private static final long serialVersionUID = -7848811918950459089L;

    /** 供应商ID */
    private Long supplierId;

    /** 供应商名称 */
    private String supplierName;

    /** 供应商编号 */
    private String supplierNo;

    /** 供应范围 */
    private String supplyRange;

    /** 合作阶段：价格谈判、正在合作、过期 */
    private String supplyStatus;

    /** 银行账号 */
    private String bankAccount;

    /** 银行账号户名 */
    private String accountName;

    /** 开户行 */
    private String depositBank;

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

    /** 备注 */
    private String remark;
    
    /** 删除标记**/
    private String isDeleted="0";

    /** 创建时间 */
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

    /**
     * 获取供应商ID.
     * <p>
     * @return supplierId
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * 设置供应商ID.
     * <p>
     * @param supplierId Long
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 获取供应商名称.
     * <p>
     * @return supplierName
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 设置供应商名称.
     * <p>
     * @param supplierName String
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 获取供应商编号.
     * <p>
     * @return supplierNo
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 设置供应商编号.
     * <p>
     * @param supplierNo String
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 获取供应范围.
     * <p>
     * @return supplyRange
     */
    public String getSupplyRange() {
        return supplyRange;
    }

    /**
     * 设置供应范围.
     * <p>
     * @param supplyRange String
     */
    public void setSupplyRange(String supplyRange) {
        this.supplyRange = supplyRange;
    }

    /**
     * 获取合作阶段：价格谈判、正在合作、过期.
     * <p>
     * @return supplyStatus
     */
    public String getSupplyStatus() {
        return supplyStatus;
    }

    /**
     * 设置合作阶段：价格谈判、正在合作、过期.
     * <p>
     * @param supplyStatus String
     */
    public void setSupplyStatus(String supplyStatus) {
        this.supplyStatus = supplyStatus;
    }

    /**
     * 获取银行账号.
     * <p>
     * @return bankAccount
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * 设置银行账号.
     * <p>
     * @param bankAccount String
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * 获取银行账号户名.
     * <p>
     * @return accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置银行账号户名.
     * <p>
     * @param accountName String
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取开户行.
     * <p>
     * @return depositBank
     */
    public String getDepositBank() {
        return depositBank;
    }

    /**
     * 设置开户行.
     * <p>
     * @param depositBank String
     */
    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
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
     * 设置删除标记
     * @param isDeleted
     */
    public void setIsDeleted(String isDeleted){
    	this.isDeleted = isDeleted;
    }
    /**
     * 获取删除标记
     */
    public String getIsDeleted(){
    	return this.isDeleted;
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