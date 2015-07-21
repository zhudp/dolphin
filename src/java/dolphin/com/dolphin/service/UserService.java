package com.dolphin.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.core.exception.BusinessException;
import com.dolphin.Constants;
import com.dolphin.dao.DepartmentDao;
import com.dolphin.dao.UserDao;
import com.dolphin.dao.UserSecurityDao;
import com.dolphin.domain.Department;
import com.dolphin.domain.RemoteUser;
import com.dolphin.domain.User;
import com.dolphin.domain.UserSecurity;
import com.dolphin.domain.resource.Resource;

/**
 * 用户业务实现.
 * 
 * @author: wanglf
 * @since: Jan 15, 2008 3:47:20 PM
 */
@Service
@Transactional
public class UserService extends BaseService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserSecurityDao userSecurityDao;
	@Autowired
	private RoleService roleService;
	@Autowired
	private DepartmentDao departmentDao;

	public User getUser(Integer id) {
		Assert.notNull(id);
		return userDao.get(id);
	}

	/**
	 * 递归取组织id
	 * 
	 * @param dept
	 * @return
	 * @create 2009-6-23 上午10:18:47 yanghb
	 * @history
	 */
	private Integer getOrgId(Department dept) {
		if ("organ".equals(dept.getDeptType())) {
			return dept.getId();
		} else if ("department".equals(dept.getDeptType())) {
			Department parentDept = departmentDao.get(dept.getParentId());
			return getOrgId(parentDept);
		} else {
			// 下属单位
			return dept.getId();
		}
	}

	private User setOrgId(User user) {
		Department dept = departmentDao.get(user.getDeptId());
		user.setOrgId(this.getOrgId(dept));
		return user;
	}

	public Integer insertUser(User o, String account, String[] reseauIds) {
		Assert.notNull(o);
		Assert.notNull(account);
		//检查账号是否存在
		if (isExistAccount(0, account)) {
			throw new BusinessException("User_0003", account);
		}
		o = this.setOrgId(o);
		o.setIsDeleted(Constants.IS_DELETED_NO);
		o.setGmtCreate(new Date());
		Integer newUserId = Integer.valueOf(userDao.insert(o).toString());
		UserSecurity userSecurity = new UserSecurity();
		userSecurity.setUserId(newUserId);
		userSecurity.setAccount(account);
		userSecurity.setPwd(UserSecurity.getMD5Pwd(userSecurity.ACCOUNT_PWD_INIT, null));
		userSecurity.setStatus(UserSecurity.STATUS_INIT);
		userSecurityDao.insert(userSecurity);
		
		return newUserId;
	}

	public void updateUser(User user, String account, String[] reseauIds) {
		Assert.notNull(user);
		Assert.notNull(account);
		//检查账号是否存在
		if (isExistAccount(user.getId(), account)) {
			throw new BusinessException("User_0003", account);
		}
		user = this.setOrgId(user);
		UserSecurity userSecurity = userSecurityDao.getByUserId(user.getId());
		userSecurity.setStatus(user.getStatus());
		userSecurity.setAccount(account);
		userSecurityDao.update(userSecurity);
		userDao.update(user);
		
	}
	
	public void deleteUser(User o) {
		Assert.notNull(o);
		User user = userDao.get(o.getId());
		user.setStatus(UserSecurity.STATUS_DEL);
		user.setIsDeleted(Constants.IS_DELETED_YES);
		userDao.remove(user);
		//CacheTool.getInstance().remove();

		 UserSecurity
		 userSecurity=(UserSecurity)userSecurityDao.get(o.getId());
		 userSecurity.setStatus(UserSecurity.STATUS_DEL);
		 userSecurityDao.update(userSecurity);
	}

	public Page queryPagedUser(Map<String, String> parameterObject) {
		Assert.notNull(parameterObject);
		// 数据过滤
		parameterObject.put(Constants.IS_DELETED, Constants.IS_DELETED_NO);
		return userDao.queryPaged(parameterObject);
	}
	
	public List<User> queryUser(Map<String, String> parameterObject) {
		Assert.notNull(parameterObject);
		// 数据过滤
		parameterObject.put(Constants.IS_DELETED, Constants.IS_DELETED_NO);
		return userDao.query(parameterObject);
	}

	public User findUserByLoginName(String loginName) {
		Assert.notNull(loginName);
		User user = null;
		UserSecurity us = userSecurityDao.queryByAccount(loginName);
		if (us != null) {
			user = userDao.get(us.getUserId());
			if (user != null) {
				user.setUserSecurity(us);
			}
		}
		return user;
	}

	/**
	 * 经过加密的密码
	 * @param account
	 * @param password
	 * @return
	 */
	public User getByAccountAndPassword(String account, String password) {
		Assert.notNull(account);
		Assert.notNull(password);
		String paramList = "";
		UserSecurity us = userSecurityDao.queryByAccount(account);
		if (us == null) {
			throw new BusinessException("User_0001", account);
		}
		if (!us.getPwd().equals(password)) {
			paramList += password;
			throw new BusinessException("User_0002", paramList);
		}
		return userDao.get(us.getUserId());
	}
	

	/**
	 * 未加密密码
	 * @param account
	 * @param password
	 * @return
	 */
	public User getByAccountAndPwd(String account, String password) {
		Assert.notNull(account);
		Assert.notNull(password);
		String paramList = "";
		Md5PasswordEncoder en = new Md5PasswordEncoder();
		password = en.encodePassword(password, null);
		UserSecurity us = userSecurityDao.queryByAccount(account);
		if (us == null) {
			throw new BusinessException("User_0001", account);
		}
		if (!us.getPwd().equals(password)) {
			paramList += password;
			throw new BusinessException("User_0002", paramList);
		}
		return userDao.get(us.getUserId());
	}

	public List<Resource> getMenusByUser(User user) {
		Assert.notNull(user);
		Assert.notNull(user.getId(), "user.id is null");

		return roleService.getRoleMenusListByUserId(user.getId());
	}

	public String getUrlMainResourceMapJsByUser(User user) {
		Assert.notNull(user);
		Assert.notNull(user.getId(), "user.id is null");

		return roleService.getUrlMainResourceMapJsByUserId(user.getId());
	}

	public UserSecurity getUserSecurity(Integer id) {
		return userSecurityDao.get(id);
	}

	public String changePassword(Integer userId, String oldPassword,
			String newPassword) {
		String message = "";
		Assert.notNull(userId);
		Assert.notNull(oldPassword);
		Assert.notNull(newPassword);
		
		UserSecurity userSecurity = userSecurityDao.get(userId);
		
		oldPassword =UserSecurity.getMD5Pwd(oldPassword,null);
		newPassword = UserSecurity.getMD5Pwd(newPassword,null);
		if (oldPassword.trim().equals(userSecurity.getPwd())) {
			userSecurity.setPwd(newPassword);
			userSecurityDao.update(userSecurity);
			User user = RemoteUser.get();
			user.getUserSecurity().setPwd(newPassword);
		} else {
			message = "您输入的原密码有误！";
		}
		return message;
	}

	public void initPassword(Integer userId) {
		Assert.notNull(userId);

		UserSecurity userSecurity = userSecurityDao.get(userId);
		//基于md5加密
		userSecurity.setPwd(UserSecurity.getMD5Pwd(UserSecurity.ACCOUNT_PWD_INIT,null));
		userSecurityDao.update(userSecurity);
	}
	
	
	/**
	 * 判断用户账号是否存在
	 * @param userid
	 * @param account
	 * @return
	 */
	public boolean isExistAccount(Integer userid ,String account){
	   UserSecurity userSecurity =userSecurityDao.queryByAccount(account);
	   if(userSecurity==null){
		   return false;
	   }
	   if(userSecurity.getUserId().compareTo(userid)!=0){
		   return true;
	   }  
	   return false ;
		
	}
	
	public void update(User u) {
		userDao.update(u);
	}
}
