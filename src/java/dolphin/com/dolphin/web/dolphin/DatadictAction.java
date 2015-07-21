package com.dolphin.web.dolphin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.core.dao.support.Page;
import com.core.utils.JsonUtils;
import com.core.web.StrutsAction;
import com.dolphin.Constants;
import com.dolphin.domain.Datadict;
import com.dolphin.service.DatadictService;


/*******************************************************************************
 * @description: 数据字典控制类
 * @author: yanghb
 * @since: 2009-5-13 下午07:48:16
 * @history: ***********************************************
 * @file: DatadictAction.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 ******************************************************************************/
@SuppressWarnings("serial")
public class DatadictAction extends StrutsAction {
	@Autowired
	private DatadictService entityService;

	/**
	 * 按照级别获取数据字典记录生成前台下拉框.
	 */
	public void combo() throws Exception {
		String levelStr = this.getRequest().getParameter("level");
		Integer level = StringUtils.isBlank(levelStr) ? 0 : Integer
				.valueOf(levelStr);
		List<Datadict> o = entityService.queryByLevel(level);
		Datadict empty = new Datadict();
		empty.setResName(Constants.COMBO_EMPTY_LABEL);
		o.add(0, empty);
		printJson(JsonUtils.bean2Json(new Page(o.size(), o)));
	}

	/**
	 * 根据id获取单条Datadict记录.
	 */
	public void get() throws Exception {
		// 添加一个数据字典节点时候点击该增加的数据字典的节点时候前台会传一个"id"为-1 数值
		String addOperaotorNode = "-1";

		if (!this.getRequest().getParameter("id").equals(addOperaotorNode)) {
			Datadict o = entityService.getDatadict(Long.valueOf(this.getRequest().getParameter("id")));
			printJson(messageSuccuseWrap(JsonUtils.bean2Json(o)));
		}
	}

	/**
	 * 分页查询Datadict列表.
	 */
	public void queryPaged() throws Exception {
		Map<String, String> queryParameter = bindMap();
		Page page = entityService.queryDatadict(queryParameter);
		printJson(JsonUtils.bean2Json(page));
	}

	/**
	 * 保存单条Datadict记录.
	 */
	@Override
	public String save() throws Exception {
		Datadict datadict = bindEntity(Datadict.class);
		// datadict.setStatus(Integer.parseInt(AppConstant.APPLY_YES));
		// 添加一个部门时候点击该增加的数据字典节点时候前台会传一个"id"为-1 数值
		String addOperaotorNode = "-1";
		if (this.getRequest().getParameter("id").equals(addOperaotorNode)) {
			entityService.insertDatadict(datadict);
		} else {
			entityService.updateDatadict(datadict);
		}
		printText(messageSuccuseWrap());
		return null;
	}

	/**
	 * 禁用、启用数据字典记录.
	 */
	public void updateSelective() throws Exception {
		Datadict o = bindEntity(Datadict.class);
		String disabled = this.getRequest().getParameter("disabled");
		if (disabled.equals("TRUE")) {
			entityService.updateSelective(o);
			printText(messageSuccuseWrap());
		} else if (disabled.equals("FALSE")) {
			entityService.updateSelective(o);
			printText(messageSuccuseWrap());
		}
	}

	public void setDatadictService(DatadictService entityService) {
		this.entityService = entityService;
	}

	/**
	 * 数据字典列表树的json数据显示
	 * @history  2010-9-26 上午11:22:47 jinrey
	 */
	public void datadictTreeList() throws Exception {
		String parentId = this.getRequest().getParameter("node");
		printJson(entityService.assemblyCurDatadictTree(Long.valueOf(parentId)));
	}


	/**
	 * @description:删除Datadict记录.
	 * @param mapping
	 * @param form
	 * @param this.getRequest()
	 * @param response
	 * @throws Exception void
	 * @create:Jul 10, 2008 3:34:05 PM hp
	 * @history:
	 */
	public void delete() throws Exception {
		Datadict o = bindEntity(Datadict.class);
		String message = entityService.deleteDataDict(o);
		if (!message.equals(""))
			printJson(messageFailureWrap(message));
		else
			printJson(messageSuccuseWrap());
	}

	protected static boolean isValid(String webRequestData) {
		return (null != webRequestData && !webRequestData.equals("0")
				&& !webRequestData.equals("null") && !webRequestData.equals(""));
	}

	/**
	 * 将SYS_RESOURCE 表中中定义的map组装成ext的下拉框
	 * @throws Exception 
	* @create  2010-11-8 下午02:30:05 chenzy
	* @history
	 */
	public void buildComboSelect() throws Exception {
		String resType = this.getRequest().getParameter("resType");
		String dataRange = this.getRequest().getParameter("dataRange");
		Map<String, String> map;
		if(StringUtils.isNotBlank(dataRange)) {
		   map = (Map<String, String>) entityService.getDatadictMap(resType,dataRange);
		}else{
		   map = (Map<String, String>) entityService.getDatadictMap(resType);
		}
		printJson(JsonUtils.getJsonCombox(map));
	}

}
