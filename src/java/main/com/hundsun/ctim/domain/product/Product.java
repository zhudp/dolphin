package com.hundsun.ctim.domain.product;

import com.core.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;

/*Record=[{ name:"productId"},{ name:"productNo"},{ name:"productName"},{ name:"productType"},{ name:"standard"},{ name:"unit"},{ name:"customId"},{ name:"customName"},{ name:"price"},{ name:"primeCost"},{ name:"status"},{ name:"productPicpath"},{ name:"remark"},{ name:"minStore"},{ name:"maxStore"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "产品ID",dataIndex : 'productId',width : 60 },{header : "产品编号",dataIndex : 'productNo',width : 60 },{header : "产品名称",dataIndex : 'productName',width : 60 },{header : "产品类型",dataIndex : 'productType',width : 60 },{header : "产品规格",dataIndex : 'standard',width : 60 },{header : "产品单位",dataIndex : 'unit',width : 60 },{header : "所属客户ID",dataIndex : 'customId',width : 60 },{header : "所属客户名称",dataIndex : 'customName',width : 60 },{header : "产品报价",dataIndex : 'price',width : 60 },{header : "产品成本价",dataIndex : 'primeCost',width : 60 },{header : "产品状态：正常、停产",dataIndex : 'status',width : 60 },{header : "产品图片路径",dataIndex : 'productPicpath',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 },{header : "最小库存量",dataIndex : 'minStore',width : 60 },{header : "最大库存量",dataIndex : 'maxStore',width : 60 },{header : "创建时间",dataIndex : 'gmtCreate',width : 60 },{header : "创建人",dataIndex : 'creator',width : 60 },{header : "创建人ID",dataIndex : 'creatorId',width : 60 },{header : "更新时间",dataIndex : 'gmtModify',width : 60 },{header : "更新人",dataIndex : 'modifier',width : 60 },{header : "更新人ID",dataIndex : 'modifierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '产品ID',id : 'productId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品编号',id : 'productNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品名称',id : 'productName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品类型',id : 'productType',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品规格',id : 'standard',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品单位',id : 'unit',allowBlank : false}),new Ext.form.TextField({fieldLabel : '所属客户ID',id : 'customId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '所属客户名称',id : 'customName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品报价',id : 'price',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品成本价',id : 'primeCost',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品状态：正常、停产',id : 'status',allowBlank : false}),new Ext.form.TextField({fieldLabel : '产品图片路径',id : 'productPicpath',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false}),new Ext.form.TextField({fieldLabel : '最小库存量',id : 'minStore',allowBlank : false}),new Ext.form.TextField({fieldLabel : '最大库存量',id : 'maxStore',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建时间',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人ID',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新时间',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人ID',id : 'modifierId',allowBlank : false})
/**
 * TbProduct Entity类<br>.
 * @author abator Tue Jan 22 23:10:31 CST 2013
 */
public class Product extends BaseEntity {
    private static final long serialVersionUID = -5927424833000459524L;

    /** 产品ID */
    private Long productId;

    /** 产品编号 */
    private String productNo;

    /** 产品名称 */
    private String productName;

    /** 产品类型 */
    private String productType;

    /** 产品规格 */
    private String standard;

    /** 产品单位 */
    private String unit;

    /** 所属客户ID */
    private Long customId;

    /** 所属客户名称 */
    private String customName;
    
    /** 所属客户编号 */
    private String customNo;

    /** 产品报价 */
    private BigDecimal price;

    /** 产品成本价 */
    private BigDecimal primeCost;

    /** 产品状态：正常、停产 */
    private String status;

    /** 产品图片路径 */
    private String productPicpath;

	/** 产品图片路径 */
    private String pictrueFullUrl;

    /** 备注 */
    private String remark;

    /** 最小库存量 */
    private Integer minStore;

    /** 最大库存量 */
    private Integer maxStore;

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
     * 获取产品类型.
     * <p>
     * @return productType
     */
    public String getProductType() {
        return productType;
    }

    /**
     * 设置产品类型.
     * <p>
     * @param productType String
     */
    public void setProductType(String productType) {
        this.productType = productType;
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
     * 获取所属客户ID.
     * <p>
     * @return customId
     */
    public Long getCustomId() {
        return customId;
    }

    /**
     * 设置所属客户ID.
     * <p>
     * @param customId Long
     */
    public void setCustomId(Long customId) {
        this.customId = customId;
    }

    /**
     * 获取所属客户名称.
     * <p>
     * @return customName
     */
    public String getCustomName() {
        return customName;
    }

    /**
     * 设置所属客户名称.
     * <p>
     * @param customName String
     */
    public void setCustomName(String customName) {
        this.customName = customName;
    }
    
    /**
     * 获取所属客户编号.
     * <p>
     * @return customNo
     */
    public String getCustomNo() {
    	return customNo;
    }
    
    /**
     * 设置所属客户编号.
     * <p>
     * @param customNo String
     */
    public void setCustomNo(String customNo) {
    	this.customNo = customNo;
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

    /**
     * 获取产品成本价.
     * <p>
     * @return primeCost
     */
    public BigDecimal getPrimeCost() {
        return primeCost;
    }

    /**
     * 设置产品成本价.
     * <p>
     * @param primeCost BigDecimal
     */
    public void setPrimeCost(BigDecimal primeCost) {
        this.primeCost = primeCost;
    }

    /**
     * 获取产品状态：正常、停产.
     * <p>
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置产品状态：正常、停产.
     * <p>
     * @param status String
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取产品图片路径.
     * <p>
     * @return productPicpath
     */
    public String getProductPicpath() {
        return productPicpath;
    }

    /**
     * 设置产品图片路径.
     * <p>
     * @param productPicpath String
     */
    public void setProductPicpath(String productPicpath) {
        this.productPicpath = productPicpath;
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
     * 获取最小库存量.
     * <p>
     * @return minStore
     */
    public Integer getMinStore() {
        return minStore;
    }

    /**
     * 设置最小库存量.
     * <p>
     * @param minStore Integer
     */
    public void setMinStore(Integer minStore) {
        this.minStore = minStore;
    }

    /**
     * 获取最大库存量.
     * <p>
     * @return maxStore
     */
    public Integer getMaxStore() {
        return maxStore;
    }

    /**
     * 设置最大库存量.
     * <p>
     * @param maxStore Integer
     */
    public void setMaxStore(Integer maxStore) {
        this.maxStore = maxStore;
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
    
    
    public String getPictrueFullUrl() {
		return pictrueFullUrl;
	}

	public void setPictrueFullUrl(String pictrueFullUrl) {
		this.pictrueFullUrl = pictrueFullUrl;
	}
}