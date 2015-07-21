package com.hundsun.ctim.dao;

import org.springframework.stereotype.Repository;

import com.core.dao.IBatisEntityDao;
import com.hundsun.ctim.domain.SerialNo;

@Repository
public class SerialNoDao extends IBatisEntityDao<SerialNo> {
	public SerialNo get(String serialPrefix){
		return get(getEntityClass(),serialPrefix);
	}
}