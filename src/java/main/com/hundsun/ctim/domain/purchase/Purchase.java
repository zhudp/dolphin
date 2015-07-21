package com.hundsun.ctim.domain.purchase;

import com.core.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;

/*Record=[{ name:"purchaseId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "采购单ID",dataIndex : 'purchaseId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '采购单ID',id : 'purchaseId',allowBlank : false})
/*Record=[{ name:"purchaseName"},{ name:"purchaseType"},{ name:"supplierId"},{ name:"supplierName"},{ name:"planStoreinDate"},{ name:"realStoreinDate"},{ name:"status"},{ name:"totalAmount"},{ name:"officer"},{ name:"remark"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "采购单名称",dataIndex : 'purchaseName',width : 60 },{header : "采购类别：定向采购、临时采购",dataIndex : 'purchaseType',width : 60 },{header : "供应商ID",dataIndex : 'supplierId',width : 60 },{header : "供应商名称",dataIndex : 'supplierName',width : 60 },{header : "计划入库时间",dataIndex : 'planStoreinDate',width : 60 },{header : "实际入库时间",dataIndex : 'realStoreinDate',width : 60 },{header : "状态：待采购、采购中、已入库、中止",dataIndex : 'status',width : 60 },{header : "总金额",dataIndex : 'totalAmount',width : 60 },{header : "采购负责人",dataIndex : 'officer',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 },{header : "创建时间",dataIndex : 'gmtCreate',width : 60 },{header : "创建人",dataIndex : 'creator',width : 60 },{header : "创建人ID",dataIndex : 'creatorId',width : 60 },{header : "更新时间",dataIndex : 'gmtModify',width : 60 },{header : "更新人",dataIndex : 'modifier',width : 60 },{header : "更新人ID",dataIndex : 'modifierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '采购单名称',id : 'purchaseName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '采购类别：定向采购、临时采购',id : 'purchaseType',allowBlank : false}),new Ext.form.TextField({fieldLabel : '供应商ID',id : 'supplierId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '供应商名称',id : 'supplierName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '计划入库时间',id : 'planStoreinDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '实际入库时间',id : 'realStoreinDate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '状态：待采购、采购中、已入库、中止',id : 'status',allowBlank : false}),new Ext.form.TextField({fieldLabel : '总金额',id : 'totalAmount',allowBlank : false}),new Ext.form.TextField({fieldLabel : '采购负责人',id : 'officer',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建时间',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人ID',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新时间',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人ID',id : 'modifierId',allowBlank : false})
/**
 * Purchase Entity类<br>.
 * @author abator Sun Jan 20 00:28:25 CST 2013
 */
public class Purchase extends BaseEntity {
    private static final long serialVersionUID = 6323727031024509525L;

    /** 采购单ID */
    private Long purchaseId;
    
    private String purchaseNo;

    /** 采购单名称 */
    private String purchaseName;

    /** 采购类别：定向采购、临时采购 */
    private String purchaseType;

    /** 供应商ID */
    private Long supplierId;

    /** 供应商名称 */
    private String supplierName;
    
    private Date orderDate;

    /** 计划入库时间 */
    private Date planStoreinDate;

    /** 实际入库时间 */
    private Date realStoreinDate;

    /** 状态：待采购、采购中、已入库、中止 */
    private String status;

    /** 总金额 */
    private BigDecimal totalAmount;

    /** 采购负责人 */
    private String officer;

    /** 备注 */
    private String remark;
    
    /** 删除标记**/
    private String isDeleted="0";

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
    

    public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	/**
     * 获取采购单ID.
     * <p>
     * @return purchaseId
     */
    public Long getPurchaseId() {
        return purchaseId;
    }

    /**
     * 设置采购单ID.
     * <p>
     * @param purchaseId Long
     */
    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    /**
     * 获取采购单名称.
     * <p>
     * @return purchaseName
     */
    public String getPurchaseName() {
        return purchaseName;
    }

    /**
     * 设置采购单名称.
     * <p>
     * @param purchaseName String
     */
    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }

    /**
     * 获取采购类别：定向采购、临时采购.
     * <p>
     * @return purchaseType
     */
    public String getPurchaseType() {
        return purchaseType;
    }

    /**
     * 设置采购类别：定向采购、临时采购.
     * <p>
     * @param purchaseType String
     */
    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    /**
     * 获取供应商ID.
     * <p>
     * @return supplierId
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * 设置供应商ID.
     * <p>
     * @param supplierId Long
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 获取供应商名称.
     * <p>
     * @return supplierName
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 设置供应商名称.
     * <p>
     * @param supplierName String
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 获取计划入库时间.
     * <p>
     * @return planStoreinDate
     */
    public Date getPlanStoreinDate() {
        return planStoreinDate;
    }

    /**
     * 设置计划入库时间.
     * <p>
     * @param planStoreinDate String
     */
    public void setPlanStoreinDate(Date planStoreinDate) {
        this.planStoreinDate = planStoreinDate;
    }

    /**
     * 获取实际入库时间.
     * <p>
     * @return realStoreinDate
     */
    public Date getRealStoreinDate() {
        return realStoreinDate;
    }

    /**
     * 设置实际入库时间.
     * <p>
     * @param realStoreinDate String
     */
    public void setRealStoreinDate(Date realStoreinDate) {
        this.realStoreinDate = realStoreinDate;
    }

    /**
     * 获取状态：待采购、采购中、已入库、中止.
     * <p>
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态：待采购、采购中、已入库、中止.
     * <p>
     * @param status String
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取总金额.
     * <p>
     * @return totalAmount
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * 设置总金额.
     * <p>
     * @param totalAmount BigDecimal
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * 获取采购负责人.
     * <p>
     * @return officer
     */
    public String getOfficer() {
        return officer;
    }

    /**
     * 设置采购负责人.
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
     * 设置删除标记
     * @param isDeleted
     */
    public void setIsDeleted(String isDeleted){
    	this.isDeleted = isDeleted;
    }
    /**
     * 获取删除标记
     * @return
     */
    public String getIsDeleted(){
    	return this.isDeleted;
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