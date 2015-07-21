package com.dolphin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.core.dao.support.Page;
import com.core.utils.CacheTool;
import com.dolphin.dao.DatadictDao;
import com.dolphin.domain.Datadict;

/**
 * @author yanghb util for velocity
 */
@SuppressWarnings("unchecked")
public class DatadictUtil {
	@Autowired
	private DatadictDao datadictDao;

	public List<Datadict> getList(String rescourceType) {
		if (rescourceType == null) {
			return null;
		}
		List<Datadict> list = (List<Datadict>) CacheTool.getInstance().get(
				rescourceType);
		if (list != null) {
			return list;
		} else {
			Map map = new HashMap();
			map.put(Page.START, "0");
			map.put(Page.LIMIT, "9999");
			map.put("resType", rescourceType);
			list = datadictDao.query(map);
			if (list != null && list.size() > 0) {
				CacheTool.getInstance().put(rescourceType, list);
			}
			return list;
		}
	}

	public Map<Datadict, Datadict> getListMap(String rescourceType,
			String dataRange) {
		Map<Datadict, Datadict> map = new HashMap<Datadict, Datadict>();
		List<Datadict> list = getList(rescourceType);
		for (Datadict o : list) {
			if (StringUtils.isNotBlank(dataRange)) {
				if (dataRange.equals(o.getDataRange())) {
					map.put(o, o);
				}
			} else {
				map.put(o, o);
			}
		}
		return map;
	}

	public TreeMap<Datadict, Datadict> getListTreeMap(String rescourceType,
			String dataRange) {
		return new TreeMap(getListMap(rescourceType, dataRange));
	}

}
