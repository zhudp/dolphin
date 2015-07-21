package com.hundsun.ctim.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.core.BaseService;
import com.hundsun.ctim.dao.SerialNoDao;
import com.hundsun.ctim.domain.SerialNo;

@Service
@Transactional
public class SerialNoServiceImp extends BaseService {
    @Autowired
    private SerialNoDao entityDao;

	public String getNo(String serialPrefix){
		return getNo(serialPrefix,4);
	}
	@Transactional(isolation=Isolation.SERIALIZABLE)
	public synchronized String getNo(String serialPrefix,int noSize, String conn){
		SerialNo serialNo = this.entityDao.get(serialPrefix);
		String no = null;
		if(serialNo == null){
			// add
			SerialNo newSerialNo = new SerialNo();
			newSerialNo.setNoSize((long) noSize);
			newSerialNo.setSerialPrefix(serialPrefix);
			insert(newSerialNo);
			serialNo  = this.entityDao.get(serialPrefix);
		}
		no = serialNo.getNo(conn);
		update(serialNo);
		return no;
	}
   
	public synchronized String getNo(String serialPrefix,int noSize){
		return getNo(serialPrefix,noSize, "-");
	}
	public String getDate(String fmt){
		SimpleDateFormat sf=new SimpleDateFormat(fmt);
		return sf.format(new Date());
	}
	public String getDate(){
		String fmt = "yy-MM-dd";
		return getDate(fmt);
	}
	
	/**
     * 添加
     * @param material
     */
    public void insert(SerialNo material){
    	entityDao.insert(material);
    }
    /**
     * 更新
     * @param material
     */
    public void update(SerialNo material){
    	entityDao.update(material);
    }
    public SerialNoDao getEntityDao() {
		return entityDao;
	}
	public void setEntityDao(SerialNoDao entityDao) {
		this.entityDao = entityDao;
	}
}