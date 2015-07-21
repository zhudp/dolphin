package com.hundsun.ctim.domain.file;

import java.util.Date;

import com.core.BaseEntity;

/*Record=[{ name:"fileId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "附件ID",dataIndex : 'fileId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '附件ID',id : 'fileId',allowBlank : false})
/*Record=[{ name:"fileName"},{ name:"fileSuffix"},{ name:"objectType"},{ name:"objectId"},{ name:"path"},{ name:"fileSize"},{ name:"remark"},{ name:"isDeleted"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "附件名称",dataIndex : 'fileName',width : 60 },{header : "文件后缀",dataIndex : 'fileSuffix',width : 60 },{header : "文件所属类别：产品/订单/原料等",dataIndex : 'objectType',width : 60 },{header : "所属对象ID",dataIndex : 'objectId',width : 60 },{header : "存放路径（相对）",dataIndex : 'path',width : 60 },{header : "文件大小",dataIndex : 'fileSize',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 },{header : "删除标志",dataIndex : 'isDeleted',width : 60 },{header : "创建时间",dataIndex : 'gmtCreate',width : 60 },{header : "创建人",dataIndex : 'creator',width : 60 },{header : "创建人ID",dataIndex : 'creatorId',width : 60 },{header : "更新时间",dataIndex : 'gmtModify',width : 60 },{header : "更新人",dataIndex : 'modifier',width : 60 },{header : "更新人ID",dataIndex : 'modifierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '附件名称',id : 'fileName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '文件后缀',id : 'fileSuffix',allowBlank : false}),new Ext.form.TextField({fieldLabel : '文件所属类别：产品/订单/原料等',id : 'objectType',allowBlank : false}),new Ext.form.TextField({fieldLabel : '所属对象ID',id : 'objectId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '存放路径（相对）',id : 'path',allowBlank : false}),new Ext.form.TextField({fieldLabel : '文件大小',id : 'fileSize',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false}),new Ext.form.TextField({fieldLabel : '删除标志',id : 'isDeleted',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建时间',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人ID',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新时间',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人ID',id : 'modifierId',allowBlank : false})
/**
 * TbAnnexFile Entity类<br>.
 * @author abator Tue Mar 05 23:21:11 CST 2013
 */
public class AnnexFile extends BaseEntity {
    private static final long serialVersionUID = 6959959484967336346L;

    /** 附件ID */
    private Long fileId;

    /** 附件名称 */
    private String fileName;

    /** 文件后缀 */
    private String fileSuffix;

    /** 文件所属类别：产品/订单/原料等 */
    private String objectType;

    /** 所属对象ID */
    private Long objectId;

    /** 存放路径（相对） */
    private String path;

	/** 文件URL */
    private String fullPath;

    /** 文件大小 */
    private String fileSize;

    /** 备注 */
    private String remark;

    /** 删除标志 */
    private String isDeleted;

    /** 创建时间 */
    private Date gmtCreate;

    /** 创建人 */
    private String creator;

    /** 创建人ID */
    private Long creatorId;

    /** 更新时间 */
    private Date gmtModify;

    /** 更新人 */
    private String modifier;

    /** 更新人ID */
    private Long modifierId;

    /**
     * 获取附件ID.
     * <p>
     * @return fileId
     */
    public Long getFileId() {
        return fileId;
    }

    /**
     * 设置附件ID.
     * <p>
     * @param fileId Long
     */
    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    /**
     * 获取附件名称.
     * <p>
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置附件名称.
     * <p>
     * @param fileName String
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取文件后缀.
     * <p>
     * @return fileSuffix
     */
    public String getFileSuffix() {
        return fileSuffix;
    }

    /**
     * 设置文件后缀.
     * <p>
     * @param fileSuffix String
     */
    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    /**
     * 获取文件所属类别：产品/订单/原料等.
     * <p>
     * @return objectType
     */
    public String getObjectType() {
        return objectType;
    }

    /**
     * 设置文件所属类别：产品/订单/原料等.
     * <p>
     * @param objectType String
     */
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    /**
     * 获取所属对象ID.
     * <p>
     * @return objectId
     */
    public Long getObjectId() {
        return objectId;
    }

    /**
     * 设置所属对象ID.
     * <p>
     * @param objectId Long
     */
    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    /**
     * 获取存放路径（相对）.
     * <p>
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置存放路径（相对）.
     * <p>
     * @param path String
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取文件大小.
     * <p>
     * @return fileSize
     */
    public String getFileSize() {
        return fileSize;
    }

    /**
     * 设置文件大小.
     * <p>
     * @param fileSize String
     */
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 获取备注.
     * <p>
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注.
     * <p>
     * @param remark String
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取删除标志.
     * <p>
     * @return isDeleted
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * 设置删除标志.
     * <p>
     * @param isDeleted String
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
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
     * 获取创建人.
     * <p>
     * @return creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人.
     * <p>
     * @param creator String
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取创建人ID.
     * <p>
     * @return creatorId
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人ID.
     * <p>
     * @param creatorId Long
     */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取更新时间.
     * <p>
     * @return gmtModify
     */
    public Date getGmtModify() {
        return gmtModify;
    }

    /**
     * 设置更新时间.
     * <p>
     * @param gmtModify Date
     */
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    /**
     * 获取更新人.
     * <p>
     * @return modifier
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置更新人.
     * <p>
     * @param modifier String
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * 获取更新人ID.
     * <p>
     * @return modifierId
     */
    public Long getModifierId() {
        return modifierId;
    }

    /**
     * 设置更新人ID.
     * <p>
     * @param modifierId Long
     */
    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }
    
    public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
}