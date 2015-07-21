package com.hundsun.ctim.domain.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.core.BaseEntity;
import com.hundsun.ctim.domain.material.Material;

/*Record=[{ name:"partId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "配件ID",dataIndex : 'partId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '配件ID',id : 'partId',allowBlank : false})
/*Record=[{ name:"partName"},{ name:"partNo"},{ name:"productId"},{ name:"standard"},{ name:"weight"},{ name:"area"},{ name:"remark"},{ name:"gmtCreate"},{ name:"creator"},{ name:"creatorId"},{ name:"gmtModify"},{ name:"modifier"},{ name:"modifierId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "配件名称",dataIndex : 'partName',width : 60 },{header : "配件编号",dataIndex : 'partNo',width : 60 },{header : "所属产品ID",dataIndex : 'productId',width : 60 },{header : "规格",dataIndex : 'standard',width : 60 },{header : "部件重量",dataIndex : 'weight',width : 60 },{header : "部件面积",dataIndex : 'area',width : 60 },{header : "备注",dataIndex : 'remark',width : 60 },{header : "创建时间",dataIndex : 'gmtCreate',width : 60 },{header : "创建人",dataIndex : 'creator',width : 60 },{header : "创建人ID",dataIndex : 'creatorId',width : 60 },{header : "更新时间",dataIndex : 'gmtModify',width : 60 },{header : "更新人",dataIndex : 'modifier',width : 60 },{header : "更新人ID",dataIndex : 'modifierId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '配件名称',id : 'partName',allowBlank : false}),new Ext.form.TextField({fieldLabel : '配件编号',id : 'partNo',allowBlank : false}),new Ext.form.TextField({fieldLabel : '所属产品ID',id : 'productId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '规格',id : 'standard',allowBlank : false}),new Ext.form.TextField({fieldLabel : '部件重量',id : 'weight',allowBlank : false}),new Ext.form.TextField({fieldLabel : '部件面积',id : 'area',allowBlank : false}),new Ext.form.TextField({fieldLabel : '备注',id : 'remark',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建时间',id : 'gmtCreate',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人',id : 'creator',allowBlank : false}),new Ext.form.TextField({fieldLabel : '创建人ID',id : 'creatorId',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新时间',id : 'gmtModify',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人',id : 'modifier',allowBlank : false}),new Ext.form.TextField({fieldLabel : '更新人ID',id : 'modifierId',allowBlank : false})
/**
 * ProductParts Entity类<br>.
 * @author abator Sun Jan 20 00:28:15 CST 2013
 */
public class ProductParts extends BaseEntity {
    private static final long serialVersionUID = -862161803209564090L;

    /** 配件ID */
    private Long partId;

    /** 配件名称 */
    private String partName;

    /** 配件编号 */
    private String partNo;

    /** 所属产品ID */
    private Long productId;
    
    /** 部门ID */
    private Long departmentId;
    
    /** 部门 */
    private String departmentName;

    /** 规格 */
    private String standard;

    /** 部件重量 */
    private BigDecimal weight;

    /** 部件面积 */
    private BigDecimal area;

    /** 备注 */
    private String remark;
    
	/** 单位 */
    private String unit;
    
    /** 原材料成本 */
    private BigDecimal materialCost;
    
    /** 工艺成本 */
    private BigDecimal workCost;

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

	/** 原料列表 */
    private List<Material> children = new ArrayList<Material>();
    
	/** 树样式 */
    private String iconCls ="task-folder";
	/** 是否展开 */
    private boolean expanded = true;

    /**
     * 获取配件ID.
     * <p>
     * @return partId
     */
    public Long getPartId() {
        return partId;
    }

    /**
     * 设置配件ID.
     * <p>
     * @param partId Long
     */
    public void setPartId(Long partId) {
        this.partId = partId;
    }

    /**
     * 获取配件名称.
     * <p>
     * @return partName
     */
    public String getPartName() {
        return partName;
    }

    /**
     * 设置配件名称.
     * <p>
     * @param partName String
     */
    public void setPartName(String partName) {
        this.partName = partName;
    }

    /**
     * 获取配件编号.
     * <p>
     * @return partNo
     */
    public String getPartNo() {
        return partNo;
    }

    /**
     * 设置配件编号.
     * <p>
     * @param partNo String
     */
    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    /**
     * 获取所属产品ID.
     * <p>
     * @return productId
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置所属产品ID.
     * <p>
     * @param productId String
     */
    public void setProductId(Long productId) {
        this.productId = productId;
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
     * 获取部件重量.
     * <p>
     * @return weight
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * 设置部件重量.
     * <p>
     * @param weight BigDecimal
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * 获取部件面积.
     * <p>
     * @return area
     */
    public BigDecimal getArea() {
        return area;
    }

    /**
     * 设置部件面积.
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
    
    
    public List<Material> getChildren() {
		return children;
	}

	public void setChildren(List<Material> children) {
		this.children = children;
	}
	
	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	
    public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getMaterialCost() {
		return materialCost;
	}

	public void setMaterialCost(BigDecimal materialCost) {
		this.materialCost = materialCost;
	}

	public BigDecimal getWorkCost() {
		return workCost;
	}

	public void setWorkCost(BigDecimal workCost) {
		this.workCost = workCost;
	}
	
    /**
     * 获取部门ID.
     * <p>
     * @return departmentName
     */
    public String getDepartmentName() {
    	return departmentName;
    }
    
    /**
     * 设置部门ID.
     * <p>
     * @param departmentName String
     */
    public void setDepartmentName(String departmentName) {
    	this.departmentName = departmentName;
    }
}