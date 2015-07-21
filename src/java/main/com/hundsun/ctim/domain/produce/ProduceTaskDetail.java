package com.hundsun.ctim.domain.produce;

import java.math.BigDecimal;

import com.core.BaseEntity;

/*Record=[{ name:"productName"},{ name:"productNo"},{ name:"unit"},{ name:"planNumber"},{ name:"storeinNumber"},{ name:"remark"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "产品名称",dataIndex : 'productName',width : 60 },{header : "产品编号",dataIndex : 'productNo',width : 60 },{header : "产品单位",dataIndex : 'unit',width : 60 },{header : "计划生产数量",dataIndex : 'planNumber',width : 60 },{header : "实际入库数量",dataIndex : 'storeinNumber',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '产品名称',id : 'productName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品编号',id : 'productNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品单位',id : 'unit',allowBlank : false}),new Ext.form.TextField({fieldLabel : '计划生产数量',id : 'planNumber',allowBlank : false}),new Ext.form.TextField({fieldLabel : '实际入库数量',id : 'storeinNumber',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false})
/**
 * ProduceTaskDetail Entity类<br>.
 * @author abator Sun Jan 20 00:28:09 CST 2013
 */
public class ProduceTaskDetail extends BaseEntity {
    private static final long serialVersionUID = -8105925647528499826L;

    /** 明细ID */
    private Long detailId;
    
    /** 生产任务ID */
    private Long taskId;
    
    /** 产品ID */
    private Long productId;
    
    /** 产品名称 */
    private String productName;

    /** 产品编号 */
    private String productNo;

    /** 产品单位 */
    private String unit;
    
    /** 产品规格 */
    private String standard;
    
    /** 产品报价 */
    private BigDecimal price;

    /** 计划生产数量 */
    private Integer planNumber;

    /** 实际入库数量 */
    private Integer storeinNumber;

    /** 备注 */
    private String remark;

    /**
     * 获取产品名称.
     * <p>
     * @return productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置产品名称.
     * <p>
     * @param productName String
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 获取产品编号.
     * <p>
     * @return productNo
     */
    public String getProductNo() {
        return productNo;
    }

    /**
     * 设置产品编号.
     * <p>
     * @param productNo String
     */
    public void setProductNo(String productNo) {
        this.productNo = productNo;
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
     * 获取产品规格.
     * <p>
     * @return standard
     */
    public String getStandard() {
        return standard;
    }

    /**
     * 设置产品规格.
     * <p>
     * @param standard String
     */
    public void setStandard(String standard) {
        this.standard = standard;
    }
    /**
     * 获取计划生产数量.
     * <p>
     * @return planNumber
     */
    public Integer getPlanNumber() {
        return planNumber;
    }

    /**
     * 设置计划生产数量.
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
    
    /**
     * 获取明细ID.
     * <p>
     * @return detailId
     */
    public Long getDetailId() {
        return detailId;
    }

    /**
     * 设置明细ID.
     * <p>
     * @param detailId Long
     */
    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }
    

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
     * 获取产品ID.
     * <p>
     * @return productId
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置产品ID.
     * <p>
     * @param productId Long
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    

    /**
     * 获取产品报价.
     * <p>
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置产品报价.
     * <p>
     * @param price BigDecimal
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}