package com.hundsun.ctim.domain.product;

import com.core.BaseEntity;
import java.util.Date;

/*Record=[{ name:"sampleId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "样品ID",dataIndex : 'sampleId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '样品ID',id : 'sampleId',allowBlank : false})
/*Record=[{ name:"sampleName"},{ name:"orderId"},{ name:"instruction"},{ name:"status"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "样品名称",dataIndex : 'sampleName',width : 60 },{header : "订单ID",dataIndex : 'orderId',width : 60 },{header : "规格说明",dataIndex : 'instruction',width : 60 },{header : "状态：待打样、打样中、已确认、中止",dataIndex : 'status',width : 60 },{header : "创建时间",dataIndex : 'gmtCreate',width : 60 },{header : "创建人",dataIndex : 'creator',width : 60 },{header : "创建人ID",dataIndex : 'creatorId',width : 60 },{header : "更新时间",dataIndex : 'gmtModify',width : 60 },{header : "更新人",dataIndex : 'modifier',width : 60 },{header : "更新人ID",dataIndex : 'modifierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '样品名称',id : 'sampleName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '订单ID',id : 'orderId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '规格说明',id : 'instruction',allowBlank : false}),new Ext.form.TextField({fieldLabel : '状态：待打样、打样中、已确认、中止',id : 'status',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建时间',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人ID',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新时间',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人ID',id : 'modifierId',allowBlank : false})
/**
 * ProductSample Entity类<br>.
 * @author abator Sun Jan 20 00:28:17 CST 2013
 */
public class ProductSample extends BaseEntity {
    private static final long serialVersionUID = -7507423210043907095L;

    /** 样品ID */
    private Long sampleId;

    /** 样品名称 */
    private String sampleName;

    /** 订单ID */
    private Long orderId;

    /** 规格说明 */
    private String instruction;

    /** 状态：待打样、打样中、已确认、中止 */
    private String status;

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
     * 获取样品ID.
     * <p>
     * @return sampleId
     */
    public Long getSampleId() {
        return sampleId;
    }

    /**
     * 设置样品ID.
     * <p>
     * @param sampleId Long
     */
    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    /**
     * 获取样品名称.
     * <p>
     * @return sampleName
     */
    public String getSampleName() {
        return sampleName;
    }

    /**
     * 设置样品名称.
     * <p>
     * @param sampleName String
     */
    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
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
     * 获取规格说明.
     * <p>
     * @return instruction
     */
    public String getInstruction() {
        return instruction;
    }

    /**
     * 设置规格说明.
     * <p>
     * @param instruction String
     */
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    /**
     * 获取状态：待打样、打样中、已确认、中止.
     * <p>
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态：待打样、打样中、已确认、中止.
     * <p>
     * @param status String
     */
    public void setStatus(String status) {
        this.status = status;
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
}