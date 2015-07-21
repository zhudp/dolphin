package com.hundsun.ctim.domain.stock;

import com.core.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;

/*Record=[{ name:"detailId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "id",dataIndex : 'detailId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : 'id',id : 'detailId',allowBlank : false})
/*Record=[{ name:"stockInId"},{ name:"productId"},{ name:"productName"},{ name:"productNo"},{ name:"productType"},{ name:"standard"},{ name:"unit"},{ name:"price"},{ name:"planQuantity"},{ name:"realQuantity"},{ name:"isDeleted"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "入库单id",dataIndex : 'stockInId',width : 60 },{header : "产品ID",dataIndex : 'productId',width : 60 },{header : "产品名称",dataIndex : 'productName',width : 60 },{header : "产品编号",dataIndex : 'productNo',width : 60 },{header : "类型",dataIndex : 'productType',width : 60 },{header : "规格",dataIndex : 'standard',width : 60 },{header : "单位",dataIndex : 'unit',width : 60 },{header : "单价",dataIndex : 'price',width : 60 },{header : "计划数量",dataIndex : 'planQuantity',width : 60 },{header : "数量",dataIndex : 'realQuantity',width : 60 },{header : "删除标志",dataIndex : 'isDeleted',width : 60 },{header : "创建时间",dataIndex : 'gmtCreate',width : 60 },{header : "创建人",dataIndex : 'creator',width : 60 },{header : "创建人ID",dataIndex : 'creatorId',width : 60 },{header : "更新时间",dataIndex : 'gmtModify',width : 60 },{header : "更新人",dataIndex : 'modifier',width : 60 },{header : "更新人编号",dataIndex : 'modifierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '入库单id',id : 'stockInId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品ID',id : 'productId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品名称',id : 'productName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品编号',id : 'productNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '类型',id : 'productType',allowBlank : false}),new Ext.form.TextField({fieldLabel : '规格',id : 'standard',allowBlank : false}),new Ext.form.TextField({fieldLabel : '单位',id : 'unit',allowBlank : false}),new Ext.form.TextField({fieldLabel : '单价',id : 'price',allowBlank : false}),new Ext.form.TextField({fieldLabel : '计划数量',id : 'planQuantity',allowBlank : false}),new Ext.form.TextField({fieldLabel : '数量',id : 'realQuantity',allowBlank : false}),new Ext.form.TextField({fieldLabel : '删除标志',id : 'isDeleted',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建时间',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人ID',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新时间',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人编号',id : 'modifierId',allowBlank : false})
/**
 * TbProductStockInDetail Entity类<br>.
 * @author abator Mon Feb 04 21:08:35 CST 2013
 */
public class ProductStockInDetail extends BaseEntity {
    private static final long serialVersionUID = -7065968312421288278L;

    /** id */
    private Long detailId;

    /** 入库单id */
    private Long stockInId;

    /** 产品ID */
    private Long productId;

    /** 产品名称 */
    private String productName;

    /** 产品编号 */
    private String productNo;

    /** 类型 */
    private String productType;

    /** 规格 */
    private String standard;

    /** 单位 */
    private String unit;

    /** 单价 */
    private BigDecimal price;

    /** 计划数量 */
    private Integer planQuantity;

    /** 数量 */
    private Integer realQuantity;

    /** 删除标志 */
    private String isDeleted ="0";

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

    /** 更新人编号 */
    private Long modifierId;

    /**
     * 获取id.
     * <p>
     * @return detailId
     */
    public Long getDetailId() {
        return detailId;
    }

    /**
     * 设置id.
     * <p>
     * @param detailId Long
     */
    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    /**
     * 获取入库单id.
     * <p>
     * @return stockInId
     */
    public Long getStockInId() {
        return stockInId;
    }

    /**
     * 设置入库单id.
     * <p>
     * @param stockInId Long
     */
    public void setStockInId(Long stockInId) {
        this.stockInId = stockInId;
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
     * 获取类型.
     * <p>
     * @return productType
     */
    public String getProductType() {
        return productType;
    }

    /**
     * 设置类型.
     * <p>
     * @param productType String
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * 获取规格.
     * <p>
     * @return standard
     */
    public String getStandard() {
        return standard;
    }

    /**
     * 设置规格.
     * <p>
     * @param standard String
     */
    public void setStandard(String standard) {
        this.standard = standard;
    }

    /**
     * 获取单位.
     * <p>
     * @return unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置单位.
     * <p>
     * @param unit String
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 获取单价.
     * <p>
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置单价.
     * <p>
     * @param price BigDecimal
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取计划数量.
     * <p>
     * @return planQuantity
     */
    public Integer getPlanQuantity() {
        return planQuantity;
    }

    /**
     * 设置计划数量.
     * <p>
     * @param planQuantity Integer
     */
    public void setPlanQuantity(Integer planQuantity) {
        this.planQuantity = planQuantity;
    }

    /**
     * 获取数量.
     * <p>
     * @return realQuantity
     */
    public Integer getRealQuantity() {
        return realQuantity;
    }

    /**
     * 设置数量.
     * <p>
     * @param realQuantity Integer
     */
    public void setRealQuantity(Integer realQuantity) {
        this.realQuantity = realQuantity;
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
     * 获取更新人编号.
     * <p>
     * @return modifierId
     */
    public Long getModifierId() {
        return modifierId;
    }

    /**
     * 设置更新人编号.
     * <p>
     * @param modifierId Long
     */
    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }
}