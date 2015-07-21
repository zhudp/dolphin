package com.dolphin.domain;

import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import com.core.BaseEntity;



/**
 * 部门Entity.
 * 
 * @author: zhangyw
 */
public class Department extends BaseEntity implements Comparable<Department> {

	private static final long serialVersionUID = 3737670500238778583L;
	public static final Integer rootNode = 0;
	
	private Integer id;
	
	private String isDeleted;

    private Date gmtCreate;

    private String creator;

    private Date gmtModify;

    private String modifier;

    private String parentName;

    private String deptName;

    private String deptType;

    private String tel;

    private String fax;

    private String address;

    private String orgCode;

    private Integer sortNum;

    private String isSystem;
    
	private Department parentDepartment;
	
	private Integer parentId;
	
	private String fullIdPath;
	
	private String fullNamePath;
	
	private String isLeaf;
	
	private Integer orgLever;

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sort) {
		this.sortNum = sort;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(String isSystem) {
		this.isSystem = isSystem;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Department getParentDepartment() {
		return parentDepartment;
	}

	public void setParentDepartment(Department parentDepartment) {
		this.parentDepartment = parentDepartment;
	}
	
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getFullIdPath() {
		return fullIdPath;
	}

	public void setFullIdPath(String fullIdPath) {
		this.fullIdPath = fullIdPath;
	}

	public String getFullNamePath() {
		return fullNamePath;
	}

	public void setFullNamePath(String fullNamePath) {
		this.fullNamePath = fullNamePath;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getOrgLever() {
		return orgLever;
	}

	public void setOrgLever(Integer orgLever) {
		this.orgLever = orgLever;
	}
	
	/**
	 * 以下为2012-01-11扩展内容，edit by jinnie 
	 */
	/**
	 * 匿名内部类实现Comparator接口  
	 */
	private Set<Department> children = new TreeSet<Department>(
			new Comparator<Department>(){
	            public int compare(Department o1, Department o2) {                     
	                return o1.compareTo(o2);   
	            }
	    });
	
	/**
	 * 添加节点
	 * @param node 
	 * @create  2012-1-11 上午10:56:03 jinrey
	 * @history
	 */
    public void addChild(Department node) {   
        children.add(node);   
    }
    
    /**
     * 转换为 JSON 格式
     * @return 
     * @create  2012-1-11 上午10:56:24 jinrey
     * @history
     */
    public String toJsonString() {
        StringBuffer buf = new StringBuffer();
        buf.append("{\"id\": \"" + this.getId() + "\", \"text\": \"" + this.getDeptName()
                        + "\", ");
        if(this.getParentId() == 1){
        	buf.append("\"disabled\": true ,");
        }
        if (children.size() > 0) {
            buf.append("\"children\": [");
            for (Department node : children) {
                buf.append(node.toJsonString() + ",");  //对子结点递归
            }
            buf.deleteCharAt(buf.length() - 1);
            buf.append("]}");
        } else {
        	//如果没有子结点，就设置leaf为true，指定显示图标为文件夹。  
            if (this.getIsLeaf().equals("y")) {
                buf.append("\"cls\": 'file', ");
            }
            buf.append("\"leaf\": true }");
        }
        return buf.toString();
    }

    /**
     * Comparable接口具体实现   
     */
    public int compareTo(Department o) {
        if (this.getOrgLever().equals(o.getOrgLever())) {
            return this.getSortNum() > o.getSortNum() ? 1
                    : (this.getSortNum() == o.getSortNum() ? 0 : -1);
        } else if (getIsLeaf().equals("y")) {
            return 1;
        } else {
            return -1;
        }
    }
}
