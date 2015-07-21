package com.hundsun.ctim.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.core.dao.support.Page;
import com.core.utils.DateUtils;
import com.core.utils.JsonUtils;
import com.core.utils.WebUtils;
import com.core.web.StrutsAction;
import com.dolphin.domain.RemoteUser;
import com.dolphin.domain.User;
import com.hundsun.ctim.Params;
import com.hundsun.ctim.domain.file.AnnexFile;
import com.hundsun.ctim.service.FileService;
import com.hundsun.ctim.service.file.AnnexFileServiceImp;

/**
 * 附件管理
 * 
 */
@SuppressWarnings("serial")
public class FileAction extends StrutsAction {
	
	@Autowired
	private AnnexFileServiceImp annexFileService;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 附件查询
	 */
	public void queryPaged() throws Exception {
		
		Map<String, String> paramMap = bindMap();
		Page page = annexFileService.queryPaged(paramMap);
		
		List<AnnexFile> fileList = (List<AnnexFile>)page.getData();
		for (AnnexFile annexFile : fileList) {
			
			String fullURL = fileService.getFullURL(annexFile.getPath(), WebUtils.getIpAddrByRequest(this.getRequest()));
			annexFile.setFullPath(fullURL);
		}

		printJson(JsonUtils.bean2Json(page));
	}
	
	/**
	 * 下载附件信息
	 */
	public void downFile() throws Exception{
		
		String fileId = this.getRequest().getParameter("fileId");
		AnnexFile annexFile = annexFileService.getById(Long.valueOf(fileId));
		
		printJson(messageSuccuseWrapObj(JsonUtils.bean2Json(annexFile)));
	}
	

	/**
	 * 保存附件信息
	 * @throws Exception
	 */
	public void uploadAnnexFile() throws Exception{
		
		AnnexFile annexFile = bindEntity(AnnexFile.class);
		Date now = DateUtils.getCurrentDate();
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		
		annexFile.setGmtModify(now);
		annexFile.setModifier(user.getUserName());
		annexFile.setModifierId(Long.valueOf(user.getId()));
		
		// 新增
		if(annexFile.getFileId() == null){
			annexFile.setGmtCreate(now);
			annexFile.setCreator(user.getUserName());
			annexFile.setCreatorId(Long.valueOf(user.getId()));
			annexFileService.insert(annexFile);
		}
		
		// 更新
		else{
			annexFileService.updateSelective(annexFile);
		}
		
		printText(messageSuccuseWrap());
	}
	
	/**
	 * 删除附件信息
	 */
	public void remove() throws Exception {
		String strFileId = this.getRequest().getParameter("fileId");
		AnnexFile annexFile = annexFileService.getById(Long.valueOf(strFileId));
		if(annexFile == null) {
			printText(messageFailureWrap("该附件信息已被删除，请刷新后再试！"));
			return;
		}
		
		// 登录的社区管理用户
		User user = RemoteUser.get();
		Date now = DateUtils.getCurrentDate();
		annexFile.setGmtModify(now);
		annexFile.setModifier(user.getUserName());
		annexFile.setModifierId(Long.valueOf(user.getId()));
		// 删除
		annexFile.setIsDeleted(Params.STATUS_ONE);
		annexFileService.update(annexFile);
		
		printText(messageSuccuseWrap());
	}
}
