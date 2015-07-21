package com.dolphin.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.core.BaseService;
import com.core.dao.support.Page;
import com.core.exception.BusinessException;
import com.core.exception.MultiBusinessException;
import com.core.utils.CacheTool;
import com.core.utils.StringUtils;
import com.dolphin.dao.DatadictDao;
import com.dolphin.domain.Datadict;

/**
 * ***********************************************
 * 
 * @author: lilq
 * @since: 2008-5-26-11:31:34
 * @history:
 */
@Service
public class DatadictService extends BaseService {
	@Autowired
	private DatadictDao entityDao;

	/**
	 * 根据资源类别，取得该类别下代码与名称map;
	 * @param resType  资源类别
	 * @return 代码与名称map
	 * @author chenzy
	 * @since 2010-09-04
	 */
	public Map<String, String> getDatadictMap(String resType) {
		List<Datadict> list = getListByResType(resType);
		Map<String, String> map = new HashMap<String, String>();
		if(list!=null) {
			for (Datadict datadict : list) {
				//过滤掉根节点数据
				if(!datadict.getParentId().equals(Datadict.rootNode)){
				   map.put(datadict.getResCode(), datadict.getResName());
			    }
			}
		}
		return map;		
	}
	/**
	 * 根据资源类别，取得该类别下代码与名称map;
	 * @param resType  资源类别
	 * @return 代码与名称map
	 * @author chenzy
	 * @since 2010-09-04
	 */
	public List<Datadict> getDatadictMapList(String resType) {
		
		List<Datadict> list = getListByResType(resType);
		if(list!=null) {
			for (int i = 0; i < list.size(); i++) {
				
				Datadict datadict = list.get(i);
				//过滤掉根节点数据
				if(datadict.getParentId() == 0){
					//map.put(datadict.getResCode(), datadict.getResName());
					list.remove(datadict);
				}
			}
		}
		return list;		
	}
	
	/**
	 * 根据资源类别，取得该类别下代码与名称map;
	 * @param resType  资源类别
	 * @return 代码与名称map
	 * @author zhudp
	 * @since 2012-05-22
	 */
	private Map<String, Datadict> getDatadictObjMap(String resType) {
		List<Datadict> list = getListByResType(resType);
		Map<String, Datadict> map = new HashMap<String, Datadict>();
		if(list!=null) {
			for (Datadict datadict : list) {
				//过滤掉根节点数据
				if(!datadict.getParentId().equals(Datadict.rootNode)){
					map.put(datadict.getResCode(), datadict);
				}
			}
		}
		return map;		
	}
	
	/**
	 * 根据资源类别和CODE，取得资源名称
	 * @param code 资源CODE
	 * @param type 资源类别
	 * @return 
	 * @create  2010-10-13 下午03:28:49 zhudp
	 * @history
	 */
	public String getResName(String code, String type) {
		
		Map<String, String> resMap = getDatadictMap(type);
		String name = resMap.get(code);
		
		if(name!=null){
			return name;
		}else{
			return "";
		}
	}
	
	/**
	 * 根据资源类别和CODE，取得资源名称
	 * @param code 资源CODE
	 * @param type 资源类别
	 * @return 
	 * @create  2012-05-22 下午03:28:49 zhudp
	 * @history
	 */
	public String getResSubName(String code, String type) {
		
		Map<String, Datadict> resMap = getDatadictObjMap(type);
		Datadict dobj = resMap.get(code);
		String subName = dobj.getResNameSub();
		
		if(subName!=null){
			return subName;
		}else{
			return "";
		}
	}
	
	/**
	 * 根据资源类别，取得该类别下代码与名称map;
	 * @param resType  资源类别
	 * @param dataRange 资源作用范围，Status 字段在不同的表其定义不一样，通过在DataRange 解释同一个代码不同的含义；
	 * @return 代码与名称map
	 * @author chenzy
	 * @since 2010-09-04
	 */
	public Map<String, String> getDatadictMap(String resType,String dataRange) {
		List<Datadict> list = getListByResType(resType);
		Map<String, String> map = new HashMap<String, String>();
		if(list!=null) {
			for (Datadict datadict : list) {
				//过滤掉根节点数据
				if(!datadict.getParentId().equals(Datadict.rootNode)){
				  //过滤数据范围
					if (org.apache.commons.lang.StringUtils.isNotBlank(dataRange)) {
					  if (dataRange.equals(datadict.getDataRange())) {
						  map.put(datadict.getResCode(), datadict.getResName());
					  }
					}else{
				       map.put(datadict.getResCode(), datadict.getResName());
					}
			    }
			}
		}
		return map;
	}

	/**
	 * 将列表对象中指定字段对应的类属性，设置编码对应的中文名
	 * @param clazz 对象类名称
	 * @param obj   转换的列表数据对象
	 * @param fieldName 字段名
	 */
	public void setPropertyLabelValue(Class clazz, Object obj, String fieldName) {
	
		Map<String, String> map = getDatadictMap(fieldName);
		// 转换字段名为属性名
		String attrName = StringUtils.nameMapping(fieldName);
		String attrLabel = attrName+"Label";
		try {
			Field attrNameField  =  clazz.getDeclaredField(attrName);
			Field attrLabelField = clazz.getDeclaredField(attrLabel);
			attrNameField.setAccessible(true);
			attrLabelField.setAccessible(true);
			for (Object o : (List) obj) {
				clazz.cast(o);
				attrLabelField.set(o, map.get(attrNameField.get(o)));
			}
			attrNameField.setAccessible(false);
			attrLabelField.setAccessible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    /**
     * 将列表对象中指定字段对应的类属性，设置编码对应的中文名
     * @param clazz
     * @param obj
     * @param fieldNameArray 字段名数组
     */
	public void setPropertyLabelValue(Class clazz, Object obj, String[] fieldNameArray) {
		for (String fieldName : fieldNameArray) {
			setPropertyLabelValue(clazz,obj,fieldName);
		}
	}

	public Datadict getDatadict(Long id) {
		Assert.notNull(id);
		return entityDao.get(id);
	}

	public Long insertDatadict(Datadict dataDict) {
		Assert.notNull(dataDict);
		Long id = null;
		// Datadict tmp = getDatadict(dataDict.getParentId());
		// 新增结点后是否超过一级结点最大的深度限制
		// System.out.println("");
		// if (tmp != null && tmp.getCodeLevel() >= tmp.getMaxLevel()) {
		// throw new BusinessException("Datadict_0004", tmp.getName());
		// }
		if ("0".equals(dataDict.getParentId())) {
			// dataDict.setCodeLevel(1);
			// dataDict.setFlag(AppConstant.DATA_DICT_READONLY);
			id = Long.valueOf(entityDao.getDataDictTopLevelId());
			dataDict.setId(id);
		} else {
			Datadict parentDatadict = entityDao.get(dataDict.getParentId());
			parentDatadict.setChildnum(parentDatadict.getChildnum() + 1);
			entityDao.update(parentDatadict);
			// dataDict.setFlag(AppConstant.DATA_DICT_CAN_MODIFY_AND_DEL);
			// dataDict.setCodeLevel(parentDatadict.getCodeLevel() + 1);
			// dataDict.setMaxLevel(parentDatadict.getMaxLevel());
			// parentDatadict.getChildnum()
			// d-十进制 o-八进制 x或X-十六进制格式化为2位十进制数
			id = Long.valueOf(dataDict.getParentId().toString()
					+ String.format("%02d", parentDatadict.getChildnum()));
			dataDict.setId(id);
		}
		dataDict.setChildnum(Long.valueOf(0L));
		entityDao.insert(dataDict);
		CacheTool.getInstance().clear();
		return id;
	}

	public Long updateDatadict(Datadict o) {
		Assert.notNull(o);
		Datadict dict = getDatadict(o.getId());
		// if (dict.getFlag().equals(AppConstant.DATA_DICT_READONLY)) {
		// throw new BusinessException("Datadict_0003", dict.getName());
		// }
		entityDao.update(o);
		CacheTool.getInstance().clear();
		return dict.getId();
	}

	public void updateSelective(Datadict o) {
		Assert.notNull(o);
		Datadict tmp = getDatadict(o.getId());
		List<Datadict> list = getSubDatadictList(o.getId());
		MultiBusinessException multi = new MultiBusinessException();
		/*
		 * if (!AppConstant.DATA_DICT_CAN_MODIFY_AND_DEL.equals(tmp.getFlag()))
		 * { multi.put(new BusinessException("Datadict_0001", tmp.getName())); }
		 */
		if (list.size() > 0) {
			multi.put(new BusinessException("Datadict_0002", tmp.getResName()));
		}
		if (multi.hasExcption()) {
			throw multi;
		}
		entityDao.updateSelective(o);
		CacheTool.getInstance().clear();
	}

	public void deleteDatadict(Datadict o) {
		Assert.notNull(o);
		entityDao.remove(o);
	}

	public Page queryDatadict(Map<String, String> parameterObject) {
		Assert.notNull(parameterObject);
		return entityDao.queryPaged(parameterObject);
	}

	public List<Datadict> queryByLevel(Integer level) {
		return entityDao.queryByLevel(level);
	}

	public void setDatadictDao(DatadictDao entityDao) {
		this.entityDao = entityDao;
	}

	/**
	 * 根据上级字典id获取下级字典列表
	 */
	public List<Datadict> getSubDatadictList(Long parent_id) {
		HashMap<String, Long> nameMap = new HashMap<String, Long>();
		nameMap.put("parentId", parent_id);
		return entityDao.queryByParentId(nameMap);
	}

	/**
	 * 根据字典名获取字典列表
	 */
	public List<Datadict> getSubDatadictListByResType(String res_type) {
		return entityDao.queryByResType(res_type);
	}

	/**
	 * 根据上级字典id获取下面所有级字典列表.
	 */
	public List<Datadict> getAllSubDatadictList(String parentId) {
		HashMap<String, String> nameMap = new HashMap<String, String>();
		nameMap.put("parentId", parentId);
		List<Datadict> list = entityDao.queryAllByParentId(parentId);
		return list;
	}

	public String assemblyCurDatadictTree(Long id) {
		List<Datadict> subList = getSubDatadictList(id);
		StringBuffer treeStr = new StringBuffer("[");
		treeStr.append(assemblyCurSubDatadictTree(subList));
		treeStr.append("]");
		return treeStr.toString();
	}

	private String assemblyCurSubDatadictTree(List<Datadict> subDatadictList) {
		StringBuffer strBuf = new StringBuffer();
		for (Datadict datadict : subDatadictList) {
			strBuf.append("{\"text\" : \"" + datadict.getResName() + "\",");
			strBuf.append("\"id\" : \"" + datadict.getId() + "\",");
			// strBuf.append("\"marketCode\" : \"" + datadict.getMarketCode() +
			// "\",");
			// strBuf.append("\"status\" : \"" + datadict.getStatus() + "\",");
			// strBuf.append("\"parentName\" : \"" + datadict.getParentName() +
			// "\",");
			HashMap<String, Long> nameMap = new HashMap<String, Long>();
			nameMap.put("parentId", datadict.getId());
			List<Datadict> list = entityDao.queryByParentId(nameMap);
			if (list != null && list.size() > 0) {
				strBuf.append("\"leaf\" : false ,");
				strBuf.append("\"cls\" :\"fold\" ");
			} else {
				strBuf.append("\"resCode\" :'" + datadict.getResCode() + "',");
				strBuf.append("\"cls\" :'file',");
				strBuf.append("\"leaf\" : true");
			}
			strBuf.append("},");
		}
		if (strBuf.length() > 0) {
			strBuf.delete(strBuf.length() - 1, strBuf.length());
		}
		return strBuf.toString();
	}

	public String deleteDataDict(Datadict o) {
		String message = "";
		// Datadict dict = entityDao.get(o.getId());
		/*
		 * if (dict.getFlag().equals(AppConstant.DATA_DICT_READONLY)) { return
		 * message = "该节点是只读数据,不可删除!"; } if
		 * (dict.getFlag().equals(AppConstant.DATA_DICT_CAN_MODIFY)) return
		 * message = "该节点只能编辑,不可删除!"; if
		 * (this.getAllSubDatadict(o.getId()).size() > 0) return message =
		 * "该节点下有子节点,不能删除!";
		 */
		entityDao.remove(o);
		return message;
	}

	private List<Datadict> getAllSubDatadict(Long id) {
		List<String> idList = new ArrayList<String>();
		List<Datadict> dataDictList = new ArrayList<Datadict>();
		List<Datadict> list = getSubDatadictList(id);
		StringTokenizer st = new StringTokenizer(getSubDatadict(list), "id:");
		while (st.hasMoreTokens()) {
			String dataDictId = st.nextToken();
			idList.add(dataDictId);
		}
		for (String tempId : idList) {
			dataDictList.add(entityDao.get(tempId));
		}
		return dataDictList;
	}

	private String getSubDatadict(List<Datadict> subDatadictList) {
		StringBuffer strBuf = new StringBuffer();
		for (Datadict datadict : subDatadictList) {
			strBuf.append("id:" + datadict.getId());
			List<Datadict> list = getSubDatadictList(datadict.getId());
			if (list != null && list.size() > 0) {
				strBuf.append(getSubDatadict(list));
			}
		}
		return strBuf.toString();
	}

	public String buildComboSelect(Long parentId) {
		List<Datadict> list = getSubDatadictList(parentId);
		StringBuffer treeStr = new StringBuffer("[");
		treeStr.append(buildComboSelectStr(list));
		treeStr.append("]");
		return treeStr.toString();
	}

	public String buildComboSelectStr(List<Datadict> list) {
		StringBuffer strBuf = new StringBuffer();
		for (Datadict datadict : list) {
			strBuf.append("{\"text\" : \"" + datadict.getResName() + "\",");
			strBuf.append("\"id\" : \"" + datadict.getId() + "\"},");
		}
		if (strBuf.length() > 0) {
			strBuf.delete(strBuf.length() - 1, strBuf.length());
		}
		return strBuf.toString();
	}

	public String buildComboSelectStrByCode(List<Datadict> list) {
		StringBuffer strBuf = new StringBuffer();
		for (Datadict datadict : list) {
			strBuf.append("{\"text\" : \"" + datadict.getResName() + "\",");
			strBuf.append("\"id\" : \"" + datadict.getResCode() + "\"},");
		}
		if (strBuf.length() > 0) {
			strBuf.delete(strBuf.length() - 1, strBuf.length());
		}
		return strBuf.toString();
	}

	public List<Datadict> query(Map map) {
		return entityDao.query(map);
	};
	/**
	 * 从系统缓存中根据类别取得类别下的所有数据列表
	 * @param resType
	 * @return
	 * @author chenzy
	 */
	@SuppressWarnings("unchecked")
	public List<Datadict> getListByResType(String resType) {
		if (resType == null) {
			return null;
		}
		List<Datadict> list = (List<Datadict>) CacheTool.getInstance().get(resType);
		if (list != null) {
			return list;
		} else {
			Map map = new HashMap();
			map.put(Page.START, "0");
			map.put(Page.LIMIT, "9999");
			map.put("resType",resType );
//			map.put("sort","id" );
//			map.put("dir","ASC" );
			list = entityDao.query(map);
			if (list != null && list.size() > 0) {
				CacheTool.getInstance().put(resType, list);
			}
			return list;
		}
	}
	/**
	 * 从系统缓存中根据类别取得类别下的指定记录数的数据列表
	 * @param resType
	 * @return
	 * @author chenzy
	 */
	@SuppressWarnings("unchecked")
	public List<Datadict> getListByResType(String resType,Integer limit) {
		if (resType == null) {
			return null;
		}
		List<Datadict> list = getListByResType(resType);
		if (list != null) {
			if(list.size()<=limit){
				return list;
			}else{
				List<Datadict> tempList = new ArrayList <Datadict>(limit); 
				for(int i=0;i<limit;i++){
					if(!list.get(i).getParentId().equals(Datadict.rootNode)){
						tempList.add(list.get(i));
					}
				}
				return tempList;
			}
		}
		return null;
	}
}
