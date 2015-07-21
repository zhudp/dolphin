package com.core.excel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.core.exception.BusinessException;
import com.dolphin.Constants;
import com.ibatis.sqlmap.client.SqlMapClient;


/** 
 * 导游Excel文件数据导入到数据库
 * @author: guozq
 * @since: 2010-10-8  下午04:57:35
 * @history:
 ************************************************
 * @file: TourGuiderInputDB.java
 * @Copyright: 2010 HundSun Electronics Co.,Ltd.
 * All right reserved.
 ************************************************/
public class TourGuiderInputDB extends ListenerPOIAbs {

	private SqlMapClient sqlMapClient;
	private int count = 0;

	/**
	 * 
	 * @param fs 文件流
	 * @throws SQLException
	 */
	public TourGuiderInputDB(POIFSFileSystem fs) throws SQLException {
		super(fs);
	}

	/* (non-Javadoc)
	 * @see com.core.excel.ListenerPOIAbs#optRows(int, int, java.util.List)
	 */
	public void optRows(int sheetIndex, int curRow, List<String> rowlist) {
		if (curRow==0){
			if(!"导游卡号".equals(rowlist.get(0)))
				throw new BusinessException("第1列不是“导游卡号”，请用规定结构的excel文件");
			if(!"导游证号".equals(rowlist.get(1)))
				throw new BusinessException("第2列不是“导游证号”，请用规定结构的excel文件");
			if(!"姓名".equals(rowlist.get(2)))
				throw new BusinessException("第3列不是“姓名”，请用规定结构的excel文件");
			if(!"性别".equals(rowlist.get(3)))
				throw new BusinessException("第4列不是“性别”，请用规定结构的excel文件");
			if(!"身份证件".equals(rowlist.get(4)))
				throw new BusinessException("第5列不是“身份证件”，请用规定结构的excel文件");
			if(!"资格证号".equals(rowlist.get(5)))
				throw new BusinessException("第6列不是“资格证号”，请用规定结构的excel文件");
			if(!"区域".equals(rowlist.get(7)))
				throw new BusinessException("第8列不是“区域”，请用规定结构的excel文件");
			if(!"年审日期".equals(rowlist.get(8)))
				throw new BusinessException("第9列不是“年审日期”，请用规定结构的excel文件");
			if(!"发卡日期".equals(rowlist.get(9)))
				throw new BusinessException("第10列不是“发卡日期”，请用规定结构的excel文件");
			if(!"等级".equals(rowlist.get(13)))
				throw new BusinessException("第14列不是“等级”，请用规定结构的excel文件");
			if(!"旅行社简称".equals(rowlist.get(15)))
				throw new BusinessException("第16列不是“旅行社简称”，请用规定结构的excel文件");
			if(!"民族".equals(rowlist.get(16)))
				throw new BusinessException("第17列不是“民族”，请用规定结构的excel文件");
			if(!"学历".equals(rowlist.get(17)))
				throw new BusinessException("第18列不是“学历”，请用规定结构的excel文件");
			if(!"出生日期".equals(rowlist.get(19)))
				throw new BusinessException("第20列不是“出生日期”，请用规定结构的excel文件");
			if(!"联系电话".equals(rowlist.get(20)))
				throw new BusinessException("第21列不是“联系电话”，请用规定结构的excel文件");
			if(!"邮件".equals(rowlist.get(21)))
				throw new BusinessException("第22列不是“邮件”，请用规定结构的excel文件");
			if(!"工作性质".equals(rowlist.get(22)))
				throw new BusinessException("第23列不是“工作性质”，请用规定结构的excel文件");
		}
		if (curRow > 0) {
			List<String> row = rowlist;
			Map<String, String> map = new HashMap<String, String>();
			/** 导游卡号 */
			String guideCardNo = row.get(0);
			map.put("guideCardNo", guideCardNo);

			/** 导游证号 */
			String guideLicenseNo = row.get(1);
			map.put("guideLicenseNo", guideLicenseNo);

			/** 姓名 */
			String guideName = row.get(2);
			map.put("guideName", guideName);

			/** 性别: 1男 0 女 */
			String sex = row.get(3);
			String sexFormat;
			if(sex.equals("男")){
				sexFormat = "1";
			}else{
				sexFormat = "0";
				
			}
			map.put("sex", sexFormat);

			/** 身份证 */
			String idcard = row.get(4);
			map.put("idcard", idcard);

			/** 资格证号 */
			String certificateNo = row.get(5);
			map.put("certificateNo", certificateNo);

			/** 区域 */
			String area = row.get(7);
			map.put("area", area);

			/** 年审日期 */
			String yearCheckDate = row.get(8);
			String yearCheckDateFormat = dateFormat(yearCheckDate);
			map.put("yearCheckDate", yearCheckDateFormat);

			/** 发卡日期 */
			String sendCardDate = row.get(9);
			String sendCardDateFormat = dateFormat(sendCardDate);
			map.put("sendCardDate", sendCardDateFormat);

			/** 等级：初级、中级、高级 */
			String guideLevel = row.get(13);
			map.put("guideLevel", guideLevel);

			/** 旅行社简称 */
			String companyName = row.get(15);
			map.put("companyName", companyName);
			 /** 民族 */
			String nation = row.get(16);
			map.put("nation", nation);

			/** 学历:博士、研究生、大学、大专、中专、中技、高中 */
			String education = row.get(17);
			map.put("education", education);

			/** 出生日期 */
			String birthdate = row.get(19);
			String birthdateFormat = dateFormat(birthdate);
			map.put("birthdate", birthdateFormat);

			/** 联系电话 */
			String phone = row.get(20);
			map.put("phone", phone);

			/** 电子邮箱 */
			String email = row.get(21);
			map.put("email", email);

			/** 工作性质:专职、兼职 */
			String workKind = row.get(22);
			map.put("workKind", workKind);
			map.put(Constants.IS_DELETED, Constants.IS_DELETED_NO);
			count ++;
			try {
				if(count==1){
					sqlMapClient.startBatch();
				}
				sqlMapClient.insert("TourGuider_importExcel",map);
				if(count==1000){
					sqlMapClient.executeBatch();
					count = 0;
				}
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
	};
	
	/** 
	 * 格式化日期
	 * @param str
	 * @return 
	* @create  2010-10-8 下午05:03:04 guozq
	* @history  
	*/
	public String dateFormat(String str){
		String date = str.substring(0, 4) +str.substring(5, 7) + str.substring(8, 10);
		 return date;
	}
}
