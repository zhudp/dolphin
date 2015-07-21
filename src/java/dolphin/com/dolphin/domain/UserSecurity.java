package com.dolphin.domain;

import java.util.Date;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.core.BaseEntity;


/**
 * 用户安全信息Entity.
 * 
 * @author: wanglf
 * @since: Feb 18, 2008 10:12:02 AM
 */
public class UserSecurity extends BaseEntity {
	private static final long serialVersionUID = 9220940901009930480L;
	
	/** 用户默认密码. */
	public static final String ACCOUNT_PWD_INIT = "00000000";//"admin" MD5加密:867e59c3ba0c915bc92bce0c6a8155e2
	/**
	 * 以用户帐号为盐值，对用户密码进行MD5加密
	 * @param rawPass
	 * @param salt
	 * @return 
	* @create  2010-11-30 下午02:17:49 chenzy
	* @history
	 */
	public static String getMD5Pwd(String rawPass,Object account){
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		return encoder.encodePassword(rawPass, account);
	}
	
	/** 帐号状态-初始化 */
	public static final String STATUS_INIT = "1";//有效
	
	public static final String STATUS_DEL = "0";//无效

	private Integer userId;

	private String account;

	private String pwd;

	private String description;

	private String status;

	private String lastLoginIp;
	private Date lastLoginTime;
	private Integer loginCount;
	
	
	/** 用户类型 */
	private String userType;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}