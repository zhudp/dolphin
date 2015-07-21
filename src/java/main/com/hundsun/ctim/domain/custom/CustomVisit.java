package com.hundsun.ctim.domain.custom;

import com.core.BaseEntity;
import java.util.Date;

/*Record=[{ name:"visitId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "拜访记录ID",dataIndex : 'visitId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '拜访记录ID',id : 'visitId',allowBlank : false})
/*Record=[{ name:"customId"},{ name:"visitDate"},{ name:"visitContext"},{ name:"visitPeople"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "客户id",dataIndex : 'customId',width : 60 },{header : "拜访日期",dataIndex : 'visitDate',width : 60 },{header : "拜访内容",dataIndex : 'visitContext',width : 60 },{header : "拜访人",dataIndex : 'visitPeople',width : 60 },{header : "创建时间",dataIndex : 'gmtCreate',width : 60 },{header : "创建人",dataIndex : 'creator',width : 60 },{header : "创建人ID",dataIndex : 'creatorId',width : 60 },{header : "更新时间",dataIndex : 'gmtModify',width : 60 },{header : "null",dataIndex : 'modifier',width : 60 },{header : "null",dataIndex : 'modifierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '客户id',id : 'customId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '拜访日期',id : 'visitDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '拜访内容',id : 'visitContext',allowBlank : false}),new Ext.form.TextField({fieldLabel : '拜访人',id : 'visitPeople',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建时间',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人ID',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新时间',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'modifierId',allowBlank : false})
/**
 * CustomVisit Entity类<br>.
 * @author abator Sun Jan 20 00:24:50 CST 2013
 */
public class CustomVisit extends BaseEntity {
    private static final long serialVersionUID = 9038349638563945841L;

    /** 拜访记录ID */
    private Long visitId;

    /** 客户id */
    private Long customId;
    
    /** 客户名称 */
    private String customName;

    /** 客户编号 */
    private String customNo;

    /** 拜访日期 */
    private Date visitDate;

    /** 拜访内容 */
    private String visitContext;

    /** 拜访人 */
    private String visitPeople;

    /** 创建时间 */
    private Date gmtCreate;

    /** 创建人 */
    private String creator;

    /** 创建人ID */
    private Long creatorId;

    /** 更新时间 */
    private Date gmtModify;

    /** modifier */
    private String modifier;

    /** modifierId */
    private Long modifierId;

    /**
     * 获取拜访记录ID.
     * <p>
     * @return visitId
     */
    public Long getVisitId() {
        return visitId;
    }

    /**
     * 设置拜访记录ID.
     * <p>
     * @param visitId Long
     */
    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    /**
     * 获取客户id.
     * <p>
     * @return customId
     */
    public Long getCustomId() {
        return customId;
    }

    /**
     * 设置客户id.
     * <p>
     * @param customId Long
     */
    public void setCustomId(Long customId) {
        this.customId = customId;
    }

    /**
     * 获取拜访日期.
     * <p>
     * @return visitDate
     */
    public Date getVisitDate() {
        return visitDate;
    }

    /**
     * 设置拜访日期.
     * <p>
     * @param visitDate Date
     */
    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    /**
     * 获取拜访内容.
     * <p>
     * @return visitContext
     */
    public String getVisitContext() {
        return visitContext;
    }

    /**
     * 设置拜访内容.
     * <p>
     * @param visitContext String
     */
    public void setVisitContext(String visitContext) {
        this.visitContext = visitContext;
    }

    /**
     * 获取拜访人.
     * <p>
     * @return visitPeople
     */
    public String getVisitPeople() {
        return visitPeople;
    }

    /**
     * 设置拜访人.
     * <p>
     * @param visitPeople String
     */
    public void setVisitPeople(String visitPeople) {
        this.visitPeople = visitPeople;
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
     * 获取modifier.
     * <p>
     * @return modifier
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置modifier.
     * <p>
     * @param modifier String
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * 获取modifierId.
     * <p>
     * @return modifierId
     */
    public Long getModifierId() {
        return modifierId;
    }

    /**
     * 设置modifierId.
     * <p>
     * @param modifierId Long
     */
    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }
    
    /**
     * 获取客户名称.
     * <p>
     * @return customName
     */
    public String getCustomName() {
        return customName;
    }

    /**
     * 设置客户名称.
     * <p>
     * @param customName String
     */
    public void setCustomName(String customName) {
        this.customName = customName;
    }

    /**
     * 获取客户编号.
     * <p>
     * @return customNo
     */
    public String getCustomNo() {
        return customNo;
    }

    /**
     * 设置客户编号.
     * <p>
     * @param customNo String
     */
    public void setCustomNo(String customNo) {
        this.customNo = customNo;
    }
}