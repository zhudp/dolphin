package com.hundsun.ctim.domain.purchase;

import java.math.BigDecimal;

import com.core.BaseEntity;

/*Record=[{ name:"materialName"},{ name:"standard"},{ name:"materialNo"},{ name:"unit"},{ name:"planNumber"},{ name:"storeinNumber"},{ name:"remark"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "原材料名称",dataIndex : 'materialName',width : 60 },{header : "原材料规格",dataIndex : 'standard',width : 60 },{header : "原材料编号",dataIndex : 'materialNo',width : 60 },{header : "产品单位",dataIndex : 'unit',width : 60 },{header : "计划采购数量",dataIndex : 'planNumber',width : 60 },{header : "实际入库数量",dataIndex : 'storeinNumber',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '原材料名称',id : 'materialName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '原材料规格',id : 'standard',allowBlank : false}),new Ext.form.TextField({fieldLabel : '原材料编号',id : 'materialNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品单位',id : 'unit',allowBlank : false}),new Ext.form.TextField({fieldLabel : '计划采购数量',id : 'planNumber',allowBlank : false}),new Ext.form.TextField({fieldLabel : '实际入库数量',id : 'storeinNumber',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false})
/**
 * PurchaseDetail Entity类<br>.
 * @author abator Sun Jan 20 00:28:27 CST 2013
 */
public class PurchaseDetail extends BaseEntity {
    private static final long serialVersionUID = 6161307754862781364L;

    private Long detailId;
    
    private Long materialId;
    
    private Long purchaseId;
    
    /** 原材料名称 */
    private String materialName;

    /** 原材料规格 */
    private String standard;

    /** 原材料编号 */
    private String materialNo;

    /** 产品单位 */
    private String unit;
    private Long supplierId;
    private String supplierName;
    private BigDecimal price;
    
    /** 计划采购数量 */
    private Integer planNumber;

    /** 实际入库数量 */
    private Integer storeinNumber;

    /** 备注 */
    private String remark;
    
    private String isDeleted = "0";
    
    private Long taskId;
    
    
    public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public Long getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}
	public Long getDetailId() {
		return detailId;
	}
	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
     * 获取原材料名称.
     * <p>
     * @return materialName
     */
    public String getMaterialName() {
        return materialName;
    }

    /**
     * 设置原材料名称.
     * <p>
     * @param materialName String
     */
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    /**
     * 获取原材料规格.
     * <p>
     * @return standard
     */
    public String getStandard() {
        return standard;
    }

    /**
     * 设置原材料规格.
     * <p>
     * @param standard String
     */
    public void setStandard(String standard) {
        this.standard = standard;
    }

    /**
     * 获取原材料编号.
     * <p>
     * @return materialNo
     */
    public String getMaterialNo() {
        return materialNo;
    }

    /**
     * 设置原材料编号.
     * <p>
     * @param materialNo String
     */
    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    /**
     * 获取产品单位.
     * <p>
     * @return unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置产品单位.
     * <p>
     * @param unit String
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 获取计划采购数量.
     * <p>
     * @return planNumber
     */
    public Integer getPlanNumber() {
        return planNumber;
    }

    /**
     * 设置计划采购数量.
     * <p>
     * @param planNumber Integer
     */
    public void setPlanNumber(Integer planNumber) {
        this.planNumber = planNumber;
    }

    /**
     * 获取实际入库数量.
     * <p>
     * @return storeinNumber
     */
    public Integer getStoreinNumber() {
        return storeinNumber;
    }

    /**
     * 设置实际入库数量.
     * <p>
     * @param storeinNumber Integer
     */
    public void setStoreinNumber(Integer storeinNumber) {
        this.storeinNumber = storeinNumber;
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
}