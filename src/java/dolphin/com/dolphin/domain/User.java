package com.dolphin.domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.core.BaseEntity;

/**
 * 用户Entity.
 * 
 * @author: wanglf
 * @since: Jan 15, 2008 3:03:15 PM
 */

/**
 * @author: yanghb
 * @since: 2010-1-14 下午02:31:30
 * @history:
 ************************************************ 
 * @file: User.java
 * @Copyright: 2009 HundSun Electronics Co.,Ltd. All right reserved.
 ************************************************/
public class User extends BaseEntity implements UserDetails {
	private static final long serialVersionUID = -2024197284097453047L;

	private Integer id;

	private String userName;
	
	private Integer deptId;

	private Integer orgId;

	private String orgName;
	
	private String email;

	private String phone;

	private String mobile;

	private String address;

	private String zipCode;

	private String sex;

	private java.sql.Date birthday;

	private String status;

	private String dutyName;

	private String account;

	private Department department;// 用户所在部门.

	private UserSecurity userSecurity;

	private List dataList;
	
	private String idCard;
	
	private String isDeleted;

	private Date gmtCreate;

	private String creator;

	private Date gmtModify;

	private String modifier;

	/** 巡查员状态：0非巡查员；1一般巡查员；2全镇巡查员 */
	private String userType;
	
	/** 所属社区ID */
    private Long communityId;

    /** 所属社区名称（冗余） */
    private String communityName;
    
	/** 所巡查的网格信息(逗号拼接) */
    private String reseauInfo;
    
    /** 派出所ID */
    private Long policeId;

    /** 派出所名称 */
    private String policeName;


	/** 巡查员使用的手机串号绑定 */
    private String imeiNumber;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public java.sql.Date getBirthday() {
		return birthday;
	}

	public void setBirthday(java.sql.Date birthday) {
		this.birthday = birthday;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public UserSecurity getUserSecurity() {
		return userSecurity;
	}

	public void setUserSecurity(UserSecurity userSecurity) {
		this.userSecurity = userSecurity;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	private Collection<GrantedAuthority> authorities;

	public void setAuthorities(Collection<GrantedAuthority> as) {
		this.authorities = as;
	}


	public Collection<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}


	public String getPassword() {
		if(this.getUserSecurity()!=null){
			return this.getUserSecurity().getPwd();
		}
		return null;
	}

	
	public String getUsername() {
		return this.userName;
	}

	
	public boolean isAccountNonExpired() {
		return true;
	}

	
	public boolean isAccountNonLocked() {
		return true;
	}

	
	public boolean isCredentialsNonExpired() {
		return true;
	}

	
	public boolean isEnabled() {
		return true;
	}
	
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	

    public Long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
    public String getReseauInfo() {
		return reseauInfo;
	}

	public void setReseauInfo(String reseauInfo) {
		this.reseauInfo = reseauInfo;
	}

    public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}


	public Long getPoliceId() {
		return policeId;
	}

	public void setPoliceId(Long policeId) {
		this.policeId = policeId;
	}

	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}
}