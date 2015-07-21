package com.hundsun.ctim.domain.material;

import com.core.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;

/*Record=[{ name:"materialId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "原材料ID",dataIndex : 'materialId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '原材料ID',id : 'materialId',allowBlank : false})
/*Record=[{ name:"materialName"},{ name:"materialNo"},{ name:"standard"},{ name:"materialType"},{ name:"unit"},{ name:"weight"},{ name:"area"},{ name:"remark"},{ name:"minStore"},{ name:"maxStore"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "原材料名称",dataIndex : 'materialName',width : 60 },{header : "原材料编号",dataIndex : 'materialNo',width : 60 },{header : "规格",dataIndex : 'standard',width : 60 },{header : "物资分类：木器、五金、油漆等",dataIndex : 'materialType',width : 60 },{header : "单位",dataIndex : 'unit',width : 60 },{header : "单位重量(公斤、KG)",dataIndex : 'weight',width : 60 },{header : "单位表面积（平主米）",dataIndex : 'area',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 },{header : "最小库存量",dataIndex : 'minStore',width : 60 },{header : "最大库存量",dataIndex : 'maxStore',width : 60 },{header : "创建时间",dataIndex : 'gmtCreate',width : 60 },{header : "创建人",dataIndex : 'creator',width : 60 },{header : "创建人ID",dataIndex : 'creatorId',width : 60 },{header : "更新时间",dataIndex : 'gmtModify',width : 60 },{header : "更新人",dataIndex : 'modifier',width : 60 },{header : "更新人ID",dataIndex : 'modifierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '原材料名称',id : 'materialName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '原材料编号',id : 'materialNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '规格',id : 'standard',allowBlank : false}),new Ext.form.TextField({fieldLabel : '物资分类：木器、五金、油漆等',id : 'materialType',allowBlank : false}),new Ext.form.TextField({fieldLabel : '单位',id : 'unit',allowBlank : false}),new Ext.form.TextField({fieldLabel : '单位重量(公斤、KG)',id : 'weight',allowBlank : false}),new Ext.form.TextField({fieldLabel : '单位表面积（平主米）',id : 'area',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false}),new Ext.form.TextField({fieldLabel : '最小库存量',id : 'minStore',allowBlank : false}),new Ext.form.TextField({fieldLabel : '最大库存量',id : 'maxStore',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建时间',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人ID',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新时间',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人ID',id : 'modifierId',allowBlank : false})
/**
 * Material Entity类<br>.
 * @author abator Sun Jan 20 00:24:54 CST 2013
 */
public class Material extends BaseEntity {
    private static final long serialVersionUID = 3586788239455678107L;

    /** 原材料ID */
    private Long materialId;

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

    /** 单位重量(公斤、KG) */
    private BigDecimal weight;

    /** 单位表面积（平主米） */
    private BigDecimal area;
    /** 性质**/
    private String kind = "1";
    
    /**价格**/
    private BigDecimal price = new BigDecimal(0);

    /** 备注 */
    private String remark;

    /** 最小库存量 */
    private Integer minStore;

    /** 最大库存量 */
    private Integer maxStore;
    /** 删除标志*/
    private String isDeleted;

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
     * 获取原材料ID.
     * <p>
     * @return materialId
     */
    public Long getMaterialId() {
        return materialId;
    }

    /**
     * 设置原材料ID.
     * <p>
     * @param materialId Long
     */
    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
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
     * 获取物资分类：木器、五金、油漆等.
     * <p>
     * @return materialType
     */
    public String getMaterialType() {
        return materialType;
    }

    /**
     * 设置物资分类：木器、五金、油漆等.
     * <p>
     * @param materialType String
     */
    public void setMaterialType(String materialType) {
        this.materialType = materialType;
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
     * 获取单位重量(公斤、KG).
     * <p>
     * @return weight
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * 设置单位重量(公斤、KG).
     * <p>
     * @param weight BigDecimal
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * 获取单位表面积（平主米）.
     * <p>
     * @return area
     */
    public BigDecimal getArea() {
        return area;
    }

    /**
     * 设置单位表面积（平主米）.
     * <p>
     * @param area BigDecimal
     */
    public void setArea(BigDecimal area) {
        this.area = area;
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
	 * 性质
	 * @return
	 */
	public String getKind() {
		return kind;
	}
	/**
	 * 性质
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}
	/**
	 * 价格
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
    
}