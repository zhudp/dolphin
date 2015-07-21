package com.hundsun.ctim.domain.sysupdate;

import com.core.BaseEntity;
import java.util.Date;

/*Record=[{ name:"id"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "null",dataIndex : 'id',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : 'null',id : 'id',allowBlank : false})
/*Record=[{ name:"updateTime"},{ name:"context"},{ name:"gmtCreate"},{ name:"creator"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "停机维护时间",dataIndex : 'updateTime',width : 60 },{header : "内容",dataIndex : 'context',width : 60 },{header : "创建时间",dataIndex : 'gmtCreate',width : 60 },{header : "创建者",dataIndex : 'creator',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '停机维护时间',id : 'updateTime',allowBlank : false}),new Ext.form.TextField({fieldLabel : '内容',id : 'context',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建时间',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建者',id : 'creator',allowBlank : false})
/**
 * SysUpdateLog Entity类<br>.
 * @author abator Thu Mar 03 13:45:42 CST 2011
 */
public class SysUpdateLog extends BaseEntity {
    private static final long serialVersionUID = -7852491034514930450L;

    /** id */
    private Long id;

    /** 停机维护时间 */
    private String updateTime;
    
    private String updateDate;

    /** 内容 */
    private String context;

    /** 创建时间 */
    private Date gmtCreate;

    /** 创建者 */
    private String creator;

    /**
     * 获取id.
     * <p>
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置id.
     * <p>
     * @param id Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取停机维护时间.
     * <p>
     * @return updateTime
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置停机维护时间.
     * <p>
     * @param updateTime Date
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取内容.
     * <p>
     * @return context
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置内容.
     * <p>
     * @param context String
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * 获取创建时间.
     * <p>
     * @return gmtCreate
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置创建时间.
     * <p>
     * @param gmtCreate Date
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取创建者.
     * <p>
     * @return creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建者.
     * <p>
     * @param creator String
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}
}