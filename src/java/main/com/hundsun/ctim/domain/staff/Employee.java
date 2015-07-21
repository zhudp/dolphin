package com.hundsun.ctim.domain.staff;

import com.core.BaseEntity;
import java.util.Date;

/*Record=[{ name:"employeeId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "员工ID",dataIndex : 'employeeId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '员工ID',id : 'employeeId',allowBlank : false})
/*Record=[{ name:"employeeNo"},{ name:"name"},{ name:"sex"},{ name:"nation"},{ name:"nativePlace"},{ name:"birthDate"},{ name:"maritalStatus"},{ name:"idCode"},{ name:"education"},{ name:"picture"},{ name:"status"},{ name:"entryDate"},{ name:"leaveDate"},{ name:"homeAddress"},{ name:"mobile"},{ name:"phone"},{ name:"job"},{ name:"emergencyPhone"},{ name:"emergencyPeople"},{ name:"departmentId"},{ name:"remark"},{ name:"isDeleted"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "员工编号",dataIndex : 'employeeNo',width : 60 },{header : "姓名",dataIndex : 'name',width : 60 },{header : "性别",dataIndex : 'sex',width : 60 },{header : "民族",dataIndex : 'nation',width : 60 },{header : "籍贯",dataIndex : 'nativePlace',width : 60 },{header : "出生日期",dataIndex : 'birthDate',width : 60 },{header : "婚姻状况",dataIndex : 'maritalStatus',width : 60 },{header : "身份证号",dataIndex : 'idCode',width : 60 },{header : "文化程度",dataIndex : 'education',width : 60 },{header : "员工照片",dataIndex : 'picture',width : 60 },{header : "状态：在职/离职",dataIndex : 'status',width : 60 },{header : "入职时间",dataIndex : 'entryDate',width : 60 },{header : "离职时间",dataIndex : 'leaveDate',width : 60 },{header : "家庭住址",dataIndex : 'homeAddress',width : 60 },{header : "手机号码",dataIndex : 'mobile',width : 60 },{header : "电话",dataIndex : 'phone',width : 60 },{header : "工种",dataIndex : 'job',width : 60 },{header : "应急联系电话",dataIndex : 'emergencyPhone',width : 60 },{header : "应急联系人",dataIndex : 'emergencyPeople',width : 60 },{header : "部门ID",dataIndex : 'departmentId',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 },{header : "删除标志",dataIndex : 'isDeleted',width : 60 },{header : "创建时间",dataIndex : 'gmtCreate',width : 60 },{header : "创建人",dataIndex : 'creator',width : 60 },{header : "创建人ID",dataIndex : 'creatorId',width : 60 },{header : "更新时间",dataIndex : 'gmtModify',width : 60 },{header : "更新人",dataIndex : 'modifier',width : 60 },{header : "更新人ID",dataIndex : 'modifierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '员工编号',id : 'employeeNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '姓名',id : 'name',allowBlank : false}),new Ext.form.TextField({fieldLabel : '性别',id : 'sex',allowBlank : false}),new Ext.form.TextField({fieldLabel : '民族',id : 'nation',allowBlank : false}),new Ext.form.TextField({fieldLabel : '籍贯',id : 'nativePlace',allowBlank : false}),new Ext.form.TextField({fieldLabel : '出生日期',id : 'birthDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '婚姻状况',id : 'maritalStatus',allowBlank : false}),new Ext.form.TextField({fieldLabel : '身份证号',id : 'idCode',allowBlank : false}),new Ext.form.TextField({fieldLabel : '文化程度',id : 'education',allowBlank : false}),new Ext.form.TextField({fieldLabel : '员工照片',id : 'picture',allowBlank : false}),new Ext.form.TextField({fieldLabel : '状态：在职/离职',id : 'status',allowBlank : false}),new Ext.form.TextField({fieldLabel : '入职时间',id : 'entryDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '离职时间',id : 'leaveDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '家庭住址',id : 'homeAddress',allowBlank : false}),new Ext.form.TextField({fieldLabel : '手机号码',id : 'mobile',allowBlank : false}),new Ext.form.TextField({fieldLabel : '电话',id : 'phone',allowBlank : false}),new Ext.form.TextField({fieldLabel : '工种',id : 'job',allowBlank : false}),new Ext.form.TextField({fieldLabel : '应急联系电话',id : 'emergencyPhone',allowBlank : false}),new Ext.form.TextField({fieldLabel : '应急联系人',id : 'emergencyPeople',allowBlank : false}),new Ext.form.TextField({fieldLabel : '部门ID',id : 'departmentId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false}),new Ext.form.TextField({fieldLabel : '删除标志',id : 'isDeleted',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建时间',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人ID',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新时间',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人ID',id : 'modifierId',allowBlank : false})
/**
 * TbEmployee Entity类<br>.
 * @author abator Fri Mar 08 00:01:59 CST 2013
 */
public class Employee extends BaseEntity {
    private static final long serialVersionUID = -219289518810469706L;

    /** 员工ID */
    private Long employeeId;

    /** 员工编号 */
    @Deprecated
    private String employeeNo;

    /** 姓名 */
    private String name;

    /** 性别 */
    private String sex;

    /** 民族 */
    private String nation;

    /** 籍贯 */
    private String nativePlace;

    /** 出生日期 */
    private String birthDate;

    /** 婚姻状况 */
    private String maritalStatus;

    /** 身份证号 */
    private String idCode;

    /** 文化程度 */
    private String education;

    /** 员工照片 */
    private String picture;

    /** 状态：在职/离职 */
    private String status;

    /** 入职时间 */
    private Date entryDate;

    /** 离职时间 */
    private Date leaveDate;

    /** 家庭住址 */
    private String homeAddress;

    /** 手机号码 */
    private String mobile;

    /** 电话 */
    private String phone;

    /** 工种 */
    private String job;

    /** 应急联系电话 */
    private String emergencyPhone;

    /** 应急联系人 */
    private String emergencyPeople;

    /** 部门ID */
    private Long departmentId;
    /** 部门 */
    private String departmentName;

    /** 备注 */
    private String remark;

    /** 删除标志 */
    private String isDeleted;

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

    /**
     * 获取员工ID.
     * <p>
     * @return employeeId
     */
    public Long getEmployeeId() {
        return employeeId;
    }

    /**
     * 设置员工ID.
     * <p>
     * @param employeeId Long
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * 获取员工编号.
     * <p>
     * @return employeeNo
     */
    public String getEmployeeNo() {
        return employeeId+"";
    }

    /**
     * 设置员工编号.
     * <p>
     * @param employeeNo String
     */
    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    /**
     * 获取姓名.
     * <p>
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名.
     * <p>
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取性别.
     * <p>
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别.
     * <p>
     * @param sex String
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取民族.
     * <p>
     * @return nation
     */
    public String getNation() {
        return nation;
    }

    /**
     * 设置民族.
     * <p>
     * @param nation String
     */
    public void setNation(String nation) {
        this.nation = nation;
    }

    /**
     * 获取籍贯.
     * <p>
     * @return nativePlace
     */
    public String getNativePlace() {
        return nativePlace;
    }

    /**
     * 设置籍贯.
     * <p>
     * @param nativePlace String
     */
    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    /**
     * 获取出生日期.
     * <p>
     * @return birthDate
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * 设置出生日期.
     * <p>
     * @param birthDate String
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * 获取婚姻状况.
     * <p>
     * @return maritalStatus
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * 设置婚姻状况.
     * <p>
     * @param maritalStatus String
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * 获取身份证号.
     * <p>
     * @return idCode
     */
    public String getIdCode() {
        return idCode;
    }

    /**
     * 设置身份证号.
     * <p>
     * @param idCode String
     */
    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    /**
     * 获取文化程度.
     * <p>
     * @return education
     */
    public String getEducation() {
        return education;
    }

    /**
     * 设置文化程度.
     * <p>
     * @param education String
     */
    public void setEducation(String education) {
        this.education = education;
    }

    /**
     * 获取员工照片.
     * <p>
     * @return picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * 设置员工照片.
     * <p>
     * @param picture String
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * 获取状态：在职/离职.
     * <p>
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态：在职/离职.
     * <p>
     * @param status String
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取入职时间.
     * <p>
     * @return entryDate
     */
    public Date getEntryDate() {
        return entryDate;
    }

    /**
     * 设置入职时间.
     * <p>
     * @param entryDate Date
     */
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    /**
     * 获取离职时间.
     * <p>
     * @return leaveDate
     */
    public Date getLeaveDate() {
        return leaveDate;
    }

    /**
     * 设置离职时间.
     * <p>
     * @param leaveDate Date
     */
    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    /**
     * 获取家庭住址.
     * <p>
     * @return homeAddress
     */
    public String getHomeAddress() {
        return homeAddress;
    }

    /**
     * 设置家庭住址.
     * <p>
     * @param homeAddress String
     */
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    /**
     * 获取手机号码.
     * <p>
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号码.
     * <p>
     * @param mobile String
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取电话.
     * <p>
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话.
     * <p>
     * @param phone String
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取工种.
     * <p>
     * @return job
     */
    public String getJob() {
        return job;
    }

    /**
     * 设置工种.
     * <p>
     * @param job String
     */
    public void setJob(String job) {
        this.job = job;
    }

    /**
     * 获取应急联系电话.
     * <p>
     * @return emergencyPhone
     */
    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    /**
     * 设置应急联系电话.
     * <p>
     * @param emergencyPhone String
     */
    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    /**
     * 获取应急联系人.
     * <p>
     * @return emergencyPeople
     */
    public String getEmergencyPeople() {
        return emergencyPeople;
    }

    /**
     * 设置应急联系人.
     * <p>
     * @param emergencyPeople String
     */
    public void setEmergencyPeople(String emergencyPeople) {
        this.emergencyPeople = emergencyPeople;
    }

    /**
     * 获取部门ID.
     * <p>
     * @return departmentId
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * 设置部门ID.
     * <p>
     * @param departmentId Long
     */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
    /**
     * 获取部门ID.
     * <p>
     * @return departmentName
     */
    public String getDepartmentName() {
    	return departmentName;
    }
    
    /**
     * 设置部门ID.
     * <p>
     * @param departmentName String
     */
    public void setDepartmentName(String departmentName) {
    	this.departmentName = departmentName;
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
}