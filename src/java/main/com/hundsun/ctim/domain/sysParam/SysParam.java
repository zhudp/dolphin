package com.hundsun.ctim.domain.sysParam;

import com.core.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;

/*Record=[{ name:"id"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "null",dataIndex : 'id',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : 'null',id : 'id',allowBlank : false})
/*Record=[{ name:"date1"},{ name:"date2"},{ name:"string1"},{ name:"string2"},{ name:"int1"},{ name:"int2"},{ name:"remark"}]*/
/*cm : [new Ext.grid.RowNumberer(),{header : "null",dataIndex : 'date1',width : 60 },{header : "null",dataIndex : 'date2',width : 60 },{header : "null",dataIndex : 'string1',width : 60 },{header : "null",dataIndex : 'string2',width : 60 },{header : "null",dataIndex : 'int1',width : 60 },{header : "null",dataIndex : 'int2',width : 60 },{header : "null",dataIndex : 'remark',width : 60 }]*/
/*new Ext.form.TextField({fieldLabel : 'null',id : 'date1',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'date2',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'string1',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'string2',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'int1',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'int2',allowBlank : false}),new Ext.form.TextField({fieldLabel : 'null',id : 'remark',allowBlank : false})
/**
 * SysParam Entity类<br>.
 * @author abator Sat Nov 24 14:26:00 CST 2012
 */
public class SysParam extends BaseEntity {
    private static final long serialVersionUID = 8999732111168460944L;

    /** id */
    private String id;

    /** date1 */
    private Date date1;

    /** date2 */
    private Date date2;

    /** string1 */
    private String string1;

    /** string2 */
    private String string2;

    /** int1 */
    private BigDecimal int1;

    /** int2 */
    private BigDecimal int2;

    /** remark */
    private String remark;

    /**
     * 获取id.
     * <p>
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id.
     * <p>
     * @param id String
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取date1.
     * <p>
     * @return date1
     */
    public Date getDate1() {
        return date1;
    }

    /**
     * 设置date1.
     * <p>
     * @param date1 Date
     */
    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    /**
     * 获取date2.
     * <p>
     * @return date2
     */
    public Date getDate2() {
        return date2;
    }

    /**
     * 设置date2.
     * <p>
     * @param date2 Date
     */
    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    /**
     * 获取string1.
     * <p>
     * @return string1
     */
    public String getString1() {
        return string1;
    }

    /**
     * 设置string1.
     * <p>
     * @param string1 String
     */
    public void setString1(String string1) {
        this.string1 = string1;
    }

    /**
     * 获取string2.
     * <p>
     * @return string2
     */
    public String getString2() {
        return string2;
    }

    /**
     * 设置string2.
     * <p>
     * @param string2 String
     */
    public void setString2(String string2) {
        this.string2 = string2;
    }

    /**
     * 获取int1.
     * <p>
     * @return int1
     */
    public BigDecimal getInt1() {
        return int1;
    }

    /**
     * 设置int1.
     * <p>
     * @param int1 BigDecimal
     */
    public void setInt1(BigDecimal int1) {
        this.int1 = int1;
    }

    /**
     * 获取int2.
     * <p>
     * @return int2
     */
    public BigDecimal getInt2() {
        return int2;
    }

    /**
     * 设置int2.
     * <p>
     * @param int2 BigDecimal
     */
    public void setInt2(BigDecimal int2) {
        this.int2 = int2;
    }

    /**
     * 获取remark.
     * <p>
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置remark.
     * <p>
     * @param remark String
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}