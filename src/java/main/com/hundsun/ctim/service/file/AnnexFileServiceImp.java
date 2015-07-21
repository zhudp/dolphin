package com.hundsun.ctim.service.file;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.hundsun.ctim.dao.file.AnnexFileDao;
import com.hundsun.ctim.domain.file.AnnexFile;

@Service
public class AnnexFileServiceImp extends BaseService {
	
    @Autowired
    private AnnexFileDao annexFileDao;
    
    /**
     * 根据ID获取附件信息
     * @param id
     * @return
     */
	public AnnexFile getById(Long id) {
		return annexFileDao.get(id);
	}
	
	/**
	 * 列表查询
	 */
	public List<AnnexFile> query(Map<String, Object> paramsMap) {
		return annexFileDao.query(paramsMap);
	}
	
	/**
	 * 分页查询
	 */
	public Page queryPaged(Map<String, String> paramsMap){
		return annexFileDao.queryPaged(paramsMap);
	}
	
	
    /**
     * 新增附件信息
     * @return
     */
	public void insert(AnnexFile file) {
		annexFileDao.insert(file);
	}
	
	/**
	 * 更新附件信息
	 * @return
	 */
	public void update(AnnexFile file) {
		annexFileDao.update(file);
	}
	/**
	 * 更新附件信息
	 * @return
	 */
	public void updateSelective(AnnexFile file) {
		annexFileDao.updateSelective(file);
	}
	
	/**
	 * 根据ID删除
	 */
	public void removeById(Long customId) {
		annexFileDao.removeById(customId);
	}
}