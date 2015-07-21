package com.hundsun.ctim.domain.material;

import com.core.BaseEntity;

/*Record=[{ name:"materialId"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "原材料ID",dataIndex : 'materialId',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '原材料ID',id : 'materialId',allowBlank : false})
/*Record=[{ name:"stock"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "库存量",dataIndex : 'stock',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : '库存量',id : 'stock',allowBlank : false})
/**
 * MaterialStock Entity类<br>.
 * @author abator Sun Jan 20 00:24:54 CST 2013
 */
public class MaterialStock extends BaseEntity {
    private static final long serialVersionUID = -757681934896922936L;

    /** 原材料ID */
    private Long materialId;

    /** 库存量 */
    private Long stock;

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
     * 获取库存量.
     * <p>
     * @return stock
     */
    public Long getStock() {
        return stock;
    }

    /**
     * 设置库存量.
     * <p>
     * @param stock Long
     */
    public void setStock(Long stock) {
        this.stock = stock;
    }
}