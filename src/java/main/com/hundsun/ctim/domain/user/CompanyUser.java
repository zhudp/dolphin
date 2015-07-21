package com.hundsun.ctim.domain.user;

import java.util.Date;

import com.dolphin.domain.UserSecurity;


/**
 * 用户安全信息Entity.
 * 
 * @author: zhudp
 * @since: Feb 18, 2008 10:12:02 AM
 */
public class CompanyUser extends UserSecurity {
	private static final long serialVersionUID = 9220940901009930480L;
	
	/** 公司ID */
    private Long companyId;

    /** 公司名称 */
    private String companyName;
    
	/** 用户名称 */
    private String userName;
    
	private String email;

	private String phone;

	private String mobile;

	private String sex;
	
	private Date gmtCreate;

	private String creator;

	private Date gmtModify;

	private String modifier;
	
    public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

}