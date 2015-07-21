package com.hundsun.ctim.domain.stock;

import com.core.BaseEntity;
import java.util.Date;

/*Record=[{ name:"detailId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "id",dataIndex : 'detailId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : 'id',id : 'detailId',allowBlank : false})
/*Record=[{ name:"stockOutId"},{ name:"productId"},{ name:"productName"},{ name:"productNo"},{ name:"standard"},{ name:"unit"},{ name:"boxNo"},{ name:"quantity"},{ name:"isDeleted"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "出库单编号",dataIndex : 'stockOutId',width : 60 },{header : "产品ID",dataIndex : 'productId',width : 60 },{header : "产品名称",dataIndex : 'productName',width : 60 },{header : "产品编号",dataIndex : 'productNo',width : 60 },{header : "规格",dataIndex : 'standard',width : 60 },{header : "单位",dataIndex : 'unit',width : 60 },{header : "箱号",dataIndex : 'boxNo',width : 60 },{header : "数量",dataIndex : 'quantity',width : 60 },{header : "删除标志",dataIndex : 'isDeleted',width : 60 },{header : "创建时间",dataIndex : 'gmtCreate',width : 60 },{header : "创建人",dataIndex : 'creator',width : 60 },{header : "创建人ID",dataIndex : 'creatorId',width : 60 },{header : "更新时间",dataIndex : 'gmtModify',width : 60 },{header : "更新人",dataIndex : 'modifier',width : 60 },{header : "更新人编号",dataIndex : 'modifierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '出库单编号',id : 'stockOutId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品ID',id : 'productId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品名称',id : 'productName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品编号',id : 'productNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '规格',id : 'standard',allowBlank : false}),new Ext.form.TextField({fieldLabel : '单位',id : 'unit',allowBlank : false}),new Ext.form.TextField({fieldLabel : '箱号',id : 'boxNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '数量',id : 'quantity',allowBlank : false}),new Ext.form.TextField({fieldLabel : '删除标志',id : 'isDeleted',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建时间',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人ID',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新时间',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人编号',id : 'modifierId',allowBlank : false})
/**
 * TbProductStockOutDetail Entity类<br>.
 * @author abator Mon Feb 04 21:08:38 CST 2013
 */
public class ProductStockOutDetail extends BaseEntity {
    private static final long serialVersionUID = -1922421450678142928L;

    /** id */
    private Long detailId;

    /** 出库单编号 */
    private Long stockOutId;

    /** 产品ID */
    private Long productId;

    /** 产品名称 */
    private String productName;

    /** 产品编号 */
    private String productNo;

    /** 规格 */
    private String standard;

    /** 单位 */
    private String unit;

    /** 箱号 */
    private String boxNo;

    /** 数量 */
    private Integer quantity;

    /** 删除标志 */
    private String isDeleted = "0";

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
     * 获取出库单编号.
     * <p>
     * @return stockOutId
     */
    public Long getStockOutId() {
        return stockOutId;
    }

    /**
     * 设置出库单编号.
     * <p>
     * @param stockOutId Long
     */
    public void setStockOutId(Long stockOutId) {
        this.stockOutId = stockOutId;
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
     * 获取箱号.
     * <p>
     * @return boxNo
     */
    public String getBoxNo() {
        return boxNo;
    }

    /**
     * 设置箱号.
     * <p>
     * @param boxNo String
     */
    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    /**
     * 获取数量.
     * <p>
     * @return quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 设置数量.
     * <p>
     * @param quantity Integer
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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