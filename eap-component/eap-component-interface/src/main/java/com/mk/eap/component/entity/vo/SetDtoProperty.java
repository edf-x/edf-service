package com.mk.eap.component.entity.vo;

import com.mk.eap.base.VO;

import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * 实体类属性档案 vo 实体定义类
 * @author gaoxue
 *
 */
@Table(name = "set_dto_property")
public class SetDtoProperty extends VO {

    private static final long serialVersionUID = -7342552236714114116L;

    /** 实体类档案 id 对应表 set_dto */
    private Long dtoId;

    /** id */
    private Long id;

    /** 属性名称 */
    private String name;

    /** 标题 */
    private String title;

    /** 字段 id 对应表 set_columns */
    private Long columnId;

    /** 时间戳 */
    private Timestamp ts;

    /**
     * 获取实体类档案 id 对应表 set_dto
     * @return 实体类档案 id 对应表 set_dto
     */
    public Long getDtoId() {
        return dtoId;
    }

    /**
     * 设置实体类档案 id 对应表 set_dto
     * @param dtoId 实体类档案 id 对应表 set_dto
     */
    public void setDtoId(Long dtoId) {
        this.dtoId = dtoId;
    }

    /**
     * 获取id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置id
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取属性名称
     * @return 属性名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置属性名称
     * @param name 属性名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取标题
     * @return 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取字段 id 对应表 set_columns
     * @return 字段 id 对应表 set_columns
     */
    public Long getColumnId() {
        return columnId;
    }

    /**
     * 设置字段 id 对应表 set_columns
     * @param columnId 字段 id 对应表 set_columns
     */
    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    /**
     * 获取时间戳
     * @return 时间戳
     */
    public Timestamp getTs() {
        return ts;
    }

    /**
     * 设置时间戳
     * @param ts 时间戳
     */
    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

}
