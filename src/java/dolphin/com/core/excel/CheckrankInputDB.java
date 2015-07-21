package com.core.excel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.core.exception.BusinessException;
import com.ibatis.sqlmap.client.SqlMapClient;


/** 
 * 客运企业Excel文件数据导入到数据库
 * @author: guozq
 * @since: 2010-10-8  下午04:57:35
 * @history:
 ************************************************
 * @file: 
 * @Copyright: 2010 HundSun Electronics Co.,Ltd.
 * All right reserved.
 ************************************************/
public class CheckrankInputDB extends ListenerPOIAbs {

	private SqlMapClient sqlMapClient;
	private int periodidIndex;
	private int companyNameIndex;
	private int enterprisecodeIndex;
	private int rankIndex;

	public int getPeriodidIndex() {
		return periodidIndex;
	}

	public void setPeriodidIndex(int periodidIndex) {
		this.periodidIndex = periodidIndex;
	}

	public int getCompanyNameIndex() {
		return companyNameIndex;
	}

	public void setCompanyNameIndex(int companyNameIndex) {
		this.companyNameIndex = companyNameIndex;
	}

	public int getEnterprisecodeIndex() {
		return enterprisecodeIndex;
	}

	public void setEnterprisecodeIndex(int enterprisecodeIndex) {
		this.enterprisecodeIndex = enterprisecodeIndex;
	}

	public int getRankIndex() {
		return rankIndex;
	}

	public void setRankIndex(int rankIndex) {
		this.rankIndex = rankIndex;
	}

	/**
	 * 
	 * @param fs 文件流
	 * @throws SQLException
	 */
	public CheckrankInputDB(POIFSFileSystem fs) throws SQLException {
		super(fs);
	}

	/* (non-Javadoc)
	 * @see com.core.excel.ListenerPOIAbs#optRows(int, int, java.util.List)
	 */
	public void optRows(int sheetIndex, int curRow, List<String> rowlist)
			 {
		if (curRow == 0) {
			for(int i=0;i<rowlist.size();i++){
				if("考核周期名称".equals(rowlist.get(i))){
					setPeriodidIndex(i);
					continue;
				}
				if("考核公司名称".equals(rowlist.get(i))){
					setCompanyNameIndex(i);
					continue;
				}
				if("运营许可证号".equals(rowlist.get(i))){
					setEnterprisecodeIndex(i);
					continue;
				}
				if("企业名称".equals(rowlist.get(i))){
					setCompanyNameIndex(i);
					continue;
				}
				if("信用等级".equals(rowlist.get(i))){
					setRankIndex(i);
				}
			}
		}
		else {
			List<String> row = rowlist;
			Map<String, String> map = new HashMap<String, String>();

			String periodid = row.get(getPeriodidIndex());
			map.put("periodid", periodid);

			String companyName = row.get(getCompanyNameIndex());
			map.put("companyName", companyName);

			String enterprisecode = row.get(getEnterprisecodeIndex());
			map.put("enterprisecode", enterprisecode);

			String rank = row.get(getRankIndex());
			map.put("rank", rank);
			try {
				sqlMapClient.insert("CompanyCheckrank_inputExcel",map);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BusinessException("数据库出错，导入失败！");
			}
			map.clear();
		}
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}
}
