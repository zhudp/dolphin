package com.hundsun.ctim.domain.purchase;

import com.core.BaseEntity;
import java.math.BigDecimal;

/*Record=[{ name:"taskId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "id",dataIndex : 'taskId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : 'id',id : 'taskId',allowBlank : false})
/*Record=[{ name:"produceTaskId"},{ name:"purchaseId"},{ name:"materialId"},{ name:"status"},{ name:"quantity"},{ name:"remark"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "生产任务单id",dataIndex : 'produceTaskId',width : 60 },{header : "采购单ID",dataIndex : 'purchaseId',width : 60 },{header : "原料ID",dataIndex : 'materialId',width : 60 },{header : "任务状态：1：未采购，2：已采购",dataIndex : 'status',width : 60 },{header : "数量",dataIndex : 'quantity',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '生产任务单id',id : 'produceTaskId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '采购单ID',id : 'purchaseId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '原料ID',id : 'materialId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '任务状态：1：未采购，2：已采购',id : 'status',allowBlank : false}),new Ext.form.TextField({fieldLabel : '数量',id : 'quantity',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false})
/**
 * TbPurchaseTask Entity类<br>.
 * @author abator Sat Feb 23 22:28:40 CST 2013
 */
public class PurchaseTask extends BaseEntity {
    private static final long serialVersionUID = -5317588066462347088L;

    /** id */
    private Long taskId;

    /** 生产任务单id */
    private Long produceTaskId;

    /** 采购单ID */
    private Long purchaseId;

    /** 原料ID */
    private Long materialId;
    
    /** 供应商ID */
    private Long supplierId;
    
    private String supplierName;
    
    /** 原材料名称 */
    private String materialName;

    /** 原材料编号 */
    private String materialNo;

    /** 规格 */
    private String standard;

    /** 物资分类：木器、五金、油漆等 */
    private String materialType;

    /** 单位 */
    private String unit;

    /** 任务状态：1：未采购，2：已采购 */
    private String status;

    /** 数量 */
    private BigDecimal quantity;

    /** 备注 */
    private String remark;

    /**
     * 获取id.
     * <p>
     * @return taskId
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * 设置id.
     * <p>
     * @param taskId Long
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * 获取生产任务单id.
     * <p>
     * @return produceTaskId
     */
    public Long getProduceTaskId() {
        return produceTaskId;
    }

    /**
     * 设置生产任务单id.
     * <p>
     * @param produceTaskId Long
     */
    public void setProduceTaskId(Long produceTaskId) {
        this.produceTaskId = produceTaskId;
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
     * 获取原料ID.
     * <p>
     * @return materialId
     */
    public Long getMaterialId() {
        return materialId;
    }

    /**
     * 设置原料ID.
     * <p>
     * @param materialId Long
     */
    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    /**
     * 获取任务状态：1：未采购，2：已采购.
     * <p>
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置任务状态：1：未采购，2：已采购.
     * <p>
     * @param status String
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取数量.
     * <p>
     * @return quantity
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * 设置数量.
     * <p>
     * @param quantity BigDecimal
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
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

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}