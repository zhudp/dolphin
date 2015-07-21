package com.hundsun.ctim.domain.stock;

import com.core.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;

/*Record=[{ name:"detailId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "null",dataIndex : 'detailId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : 'null',id : 'detailId',allowBlank : false})
/*Record=[{ name:"stockInId"},{ name:"materialId"},{ name:"materialName"},{ name:"materialNo"},{ name:"standard"},{ name:"unit"},{ name:"price"},{ name:"planQuantity"},{ name:"realQuantity"},{ name:"isDeleted"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "null",dataIndex : 'stockInId',width : 60 },{header : "null",dataIndex : 'materialId',width : 60 },{header : "null",dataIndex : 'materialName',width : 60 },{header : "null",dataIndex : 'materialNo',width : 60 },{header : "null",dataIndex : 'standard',width : 60 },{header : "null",dataIndex : 'unit',width : 60 },{header : "null",dataIndex : 'price',width : 60 },{header : "null",dataIndex : 'planQuantity',width : 60 },{header : "null",dataIndex : 'realQuantity',width : 60 },{header : "null",dataIndex : 'isDeleted',width : 60 },{header : "null",dataIndex : 'gmtCreate',width : 60 },{header : "null",dataIndex : 'creator',width : 60 },{header : "null",dataIndex : 'creatorId',width : 60 },{header : "null",dataIndex : 'gmtModify',width : 60 },{header : "null",dataIndex : 'modifier',width : 60 },{header : "null",dataIndex : 'modifierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : 'null',id : 'stockInId',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'materialId',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'materialName',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'materialNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'standard',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'unit',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'price',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'planQuantity',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'realQuantity',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'isDeleted',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'modifierId',allowBlank : false})
/**
 * TbMaterialStockInDatail Entity类<br>.
 * @author abator Wed Jan 23 21:41:50 CST 2013
 */
public class MaterialStockInDetail extends BaseEntity {
    private static final long serialVersionUID = 3891206191849355906L;

    /** detailId */
    private Long detailId;

    /** stockInId */
    private Long stockInId;

    /** materialId */
    private Long materialId;

    /** materialName */
    private String materialName;

    /** materialNo */
    private String materialNo;

    /** standard */
    private String standard;

    /** unit */
    private String unit;

    /** price */
    private BigDecimal price;

    /** planQuantity */
    private Long planQuantity;

    /** realQuantity */
    private Long realQuantity;

    /** isDeleted */
    private String isDeleted ="0";

    /** gmtCreate */
    private Date gmtCreate;

    /** creator */
    private String creator;

    /** creatorId */
    private Long creatorId;

    /** gmtModify */
    private Date gmtModify;

    /** modifier */
    private String modifier;

    /** modifierId */
    private Long modifierId;

    /**
     * 获取detailId.
     * <p>
     * @return detailId
     */
    public Long getDetailId() {
        return detailId;
    }

    /**
     * 设置detailId.
     * <p>
     * @param detailId Long
     */
    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    /**
     * 获取stockInId.
     * <p>
     * @return stockInId
     */
    public Long getStockInId() {
        return stockInId;
    }

    /**
     * 设置stockInId.
     * <p>
     * @param stockInId Long
     */
    public void setStockInId(Long stockInId) {
        this.stockInId = stockInId;
    }

    /**
     * 获取materialId.
     * <p>
     * @return materialId
     */
    public Long getMaterialId() {
        return materialId;
    }

    /**
     * 设置materialId.
     * <p>
     * @param materialId Long
     */
    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    /**
     * 获取materialName.
     * <p>
     * @return materialName
     */
    public String getMaterialName() {
        return materialName;
    }

    /**
     * 设置materialName.
     * <p>
     * @param materialName String
     */
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    /**
     * 获取materialNo.
     * <p>
     * @return materialNo
     */
    public String getMaterialNo() {
        return materialNo;
    }

    /**
     * 设置materialNo.
     * <p>
     * @param materialNo String
     */
    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    /**
     * 获取standard.
     * <p>
     * @return standard
     */
    public String getStandard() {
        return standard;
    }

    /**
     * 设置standard.
     * <p>
     * @param standard String
     */
    public void setStandard(String standard) {
        this.standard = standard;
    }

    /**
     * 获取unit.
     * <p>
     * @return unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置unit.
     * <p>
     * @param unit String
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 获取price.
     * <p>
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置price.
     * <p>
     * @param price BigDecimal
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取planQuantity.
     * <p>
     * @return planQuantity
     */
    public Long getPlanQuantity() {
        return planQuantity;
    }

    /**
     * 设置planQuantity.
     * <p>
     * @param planQuantity Long
     */
    public void setPlanQuantity(Long planQuantity) {
        this.planQuantity = planQuantity;
    }

    /**
     * 获取realQuantity.
     * <p>
     * @return realQuantity
     */
    public Long getRealQuantity() {
        return realQuantity;
    }

    /**
     * 设置realQuantity.
     * <p>
     * @param realQuantity Long
     */
    public void setRealQuantity(Long realQuantity) {
        this.realQuantity = realQuantity;
    }

    /**
     * 获取isDeleted.
     * <p>
     * @return isDeleted
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * 设置isDeleted.
     * <p>
     * @param isDeleted String
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 获取gmtCreate.
     * <p>
     * @return gmtCreate
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置gmtCreate.
     * <p>
     * @param gmtCreate Date
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取creator.
     * <p>
     * @return creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置creator.
     * <p>
     * @param creator String
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取creatorId.
     * <p>
     * @return creatorId
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * 设置creatorId.
     * <p>
     * @param creatorId Long
     */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取gmtModify.
     * <p>
     * @return gmtModify
     */
    public Date getGmtModify() {
        return gmtModify;
    }

    /**
     * 设置gmtModify.
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
}