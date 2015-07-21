package com.hundsun.ctim.domain.produce;

import com.core.BaseEntity;
import java.util.Date;

/*Record=[{ name:"taskId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "生产任务ID",dataIndex : 'taskId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '生产任务ID',id : 'taskId',allowBlank : false})
/*Record=[{ name:"taskName"},{ name:"customId"},{ name:"customName"},{ name:"planBeginDate"},{ name:"planEndDate"},{ name:"realBeginDate"},{ name:"realEndDate"},{ name:"storeInDate"},{ name:"taskStatus"},{ name:"officer"},{ name:"remark"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "任务名称",dataIndex : 'taskName',width : 60 },{header : "客户ID",dataIndex : 'customId',width : 60 },{header : "客户名称",dataIndex : 'customName',width : 60 },{header : "计划开始时间",dataIndex : 'planBeginDate',width : 60 },{header : "计划结束时间",dataIndex : 'planEndDate',width : 60 },{header : "实际开始时间",dataIndex : 'realBeginDate',width : 60 },{header : "实际结束时间",dataIndex : 'realEndDate',width : 60 },{header : "入库时间",dataIndex : 'storeInDate',width : 60 },{header : "任务状态：待执行、执行中、生产完成、已入库、中止",dataIndex : 'taskStatus',width : 60 },{header : "接单负责人",dataIndex : 'officer',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 },{header : "创建时间",dataIndex : 'gmtCreate',width : 60 },{header : "创建人",dataIndex : 'creator',width : 60 },{header : "创建人ID",dataIndex : 'creatorId',width : 60 },{header : "更新时间",dataIndex : 'gmtModify',width : 60 },{header : "更新人",dataIndex : 'modifier',width : 60 },{header : "更新人ID",dataIndex : 'modifierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '任务名称',id : 'taskName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '客户ID',id : 'customId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '客户名称',id : 'customName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '计划开始时间',id : 'planBeginDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '计划结束时间',id : 'planEndDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '实际开始时间',id : 'realBeginDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '实际结束时间',id : 'realEndDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '入库时间',id : 'storeInDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '任务状态：待执行、执行中、生产完成、已入库、中止',id : 'taskStatus',allowBlank : false}),new Ext.form.TextField({fieldLabel : '接单负责人',id : 'officer',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建时间',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人ID',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新时间',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人ID',id : 'modifierId',allowBlank : false})
/**
 * ProduceTask Entity类<br>.
 * @author abator Sun Jan 20 00:28:07 CST 2013
 */
public class ProduceTask extends BaseEntity {
    private static final long serialVersionUID = -7552430295366365692L;

    /** 生产任务ID */
    private Long taskId;

    /** 任务名称 */
    private String taskName;

    /** 客户ID */
    private Long customId;

    /** 客户名称 */
    private String customName;
    
    /** 客户编号 */
    private String customNo;
    
    /** 订单ID */
    private Long orderId;
    
    /** 订单编号 */
    private String orderNo;

    /** 订单名称（店铺名称/订单标记等） */
    private String orderName;

    /** 计划开始时间 */
    private String planBeginDate;

    /** 计划结束时间 */
    private String planEndDate;

    /** 实际开始时间 */
    private String realBeginDate;

    /** 实际结束时间 */
    private String realEndDate;

    /** 入库时间 */
    private String storeInDate;

    /** 任务状态：待执行、执行中、生产完成、已入库、中止 */
    private String taskStatus;

    /** 接单负责人 */
    private String officer;

    /** 备注 */
    private String remark;

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
    
    
    /** '0' 未删除 ‘1’ 已删除 */
    private String isDeleted;


    /**
     * 获取生产任务ID.
     * <p>
     * @return taskId
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * 设置生产任务ID.
     * <p>
     * @param taskId Long
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * 获取任务名称.
     * <p>
     * @return taskName
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 设置任务名称.
     * <p>
     * @param taskName String
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 获取客户ID.
     * <p>
     * @return customId
     */
    public Long getCustomId() {
        return customId;
    }

    /**
     * 设置客户ID.
     * <p>
     * @param customId Long
     */
    public void setCustomId(Long customId) {
        this.customId = customId;
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
     * 获取计划开始时间.
     * <p>
     * @return planBeginDate
     */
    public String getPlanBeginDate() {
        return planBeginDate;
    }

    /**
     * 设置计划开始时间.
     * <p>
     * @param planBeginDate String
     */
    public void setPlanBeginDate(String planBeginDate) {
        this.planBeginDate = planBeginDate;
    }

    /**
     * 获取计划结束时间.
     * <p>
     * @return planEndDate
     */
    public String getPlanEndDate() {
        return planEndDate;
    }

    /**
     * 设置计划结束时间.
     * <p>
     * @param planEndDate String
     */
    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    /**
     * 获取实际开始时间.
     * <p>
     * @return realBeginDate
     */
    public String getRealBeginDate() {
        return realBeginDate;
    }

    /**
     * 设置实际开始时间.
     * <p>
     * @param realBeginDate String
     */
    public void setRealBeginDate(String realBeginDate) {
        this.realBeginDate = realBeginDate;
    }

    /**
     * 获取实际结束时间.
     * <p>
     * @return realEndDate
     */
    public String getRealEndDate() {
        return realEndDate;
    }

    /**
     * 设置实际结束时间.
     * <p>
     * @param realEndDate String
     */
    public void setRealEndDate(String realEndDate) {
        this.realEndDate = realEndDate;
    }

    /**
     * 获取入库时间.
     * <p>
     * @return storeInDate
     */
    public String getStoreInDate() {
        return storeInDate;
    }

    /**
     * 设置入库时间.
     * <p>
     * @param storeInDate String
     */
    public void setStoreInDate(String storeInDate) {
        this.storeInDate = storeInDate;
    }

    /**
     * 获取任务状态：待执行、执行中、生产完成、已入库、中止.
     * <p>
     * @return taskStatus
     */
    public String getTaskStatus() {
        return taskStatus;
    }

    /**
     * 设置任务状态：待执行、执行中、生产完成、已入库、中止.
     * <p>
     * @param taskStatus String
     */
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    /**
     * 获取接单负责人.
     * <p>
     * @return officer
     */
    public String getOfficer() {
        return officer;
    }

    /**
     * 设置接单负责人.
     * <p>
     * @param officer String
     */
    public void setOfficer(String officer) {
        this.officer = officer;
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
    
    /**
     * 获取订单ID.
     * <p>
     * @return orderId
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单ID.
     * <p>
     * @param orderId Long
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    /**
     * 获取'0' 未删除 ‘1’ 已删除.
     * <p>
     * @return isDeleted
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * 设置'0' 未删除 ‘1’ 已删除.
     * <p>
     * @param isDeleted String
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    /**
     * 获取订单编号.
     * <p>
     * @return orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置订单编号.
     * <p>
     * @param orderNo String
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取订单名称（店铺名称/订单标记等）.
     * <p>
     * @return orderName
     */
    public String getOrderName() {
        return orderName;
    }

    /**
     * 设置订单名称（店铺名称/订单标记等）.
     * <p>
     * @param orderName String
     */
    public void setOrderName(String orderName) {
        this.orderName = orderName;
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