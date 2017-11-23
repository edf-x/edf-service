package com.mk.eap.voucher.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mk.eap.validate.group.Delete;
import com.mk.eap.validate.YJNotNull;
import com.mk.eap.base.DTO;

import java.sql.Timestamp;

/**
 * 实体类编辑配置方案详细信息 dto 实体定义类
 * @author gaoxue
 *
 */
public class SetCardControlDetailDto extends DTO {

    private static final long serialVersionUID = -8629600160465746690L;

    /** 组织 id 对应表 set_org */
    @YJNotNull(alias = "企业 id ")
    private Long orgId;

    /** 配置方案 id 对应表 set_card_control */
    @YJNotNull(alias = "方案 id", groups = { Delete.class })
    private Long controlId;

    /** id */
    @YJNotNull(alias = "id", groups = { Delete.class })
    private Long id;

    /** 实体类属性档案 id 对应表 set_dto_property */
    private Long propertyId;

    /** 属性名称 */
    private String propertyName;

    /** 属性标题 */
    private String propertyTitle;

    /** 字段 id 对应表 set_columns */
    @JsonIgnore
    private Long columnId;

    /** 是否表头属性 */
    private Boolean isHead;

    /** 是否可见 */
    private Boolean visible;

    /** 是否必填 */
    private Boolean required;

    /** 时间戳 */
    @YJNotNull(alias = "时间戳", groups = { Delete.class })
    private Timestamp ts;

    /**
     * 获取组织 id 对应表 set_org
     * @return 组织 id 对应表 set_org
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置组织 id 对应表 set_org
     * @param orgId 组织 id 对应表 set_org
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取配置方案 id 对应表 set_card_control
     * @return 配置方案 id 对应表 set_card_control
     */
    public Long getControlId() {
        return controlId;
    }

    /**
     * 设置配置方案 id 对应表 set_card_control
     * @param controlId 配置方案 id 对应表 set_card_control
     */
    public void setControlId(Long controlId) {
        this.controlId = controlId;
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
     * 获取实体类属性档案 id 对应表 set_dto_property
     * @return 实体类属性档案 id 对应表 set_dto_property
     */
    public Long getPropertyId() {
        return propertyId;
    }

    /**
     * 设置实体类属性档案 id 对应表 set_dto_property
     * @param propertyId 实体类属性档案 id 对应表 set_dto_property
     */
    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    /**
     * 获取属性名称
     * @return 属性名称
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * 设置属性名称
     * @param propertyName 属性名称
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * 获取属性标题
     * @return 属性标题
     */
    public String getPropertyTitle() {
        return propertyTitle;
    }

    /**
     * 设置属性标题
     * @param propertyTitle 属性标题
     */
    public void setPropertyTitle(String propertyTitle) {
        this.propertyTitle = propertyTitle;
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
     * 获取是否表头属性
     * @return 是否表头属性
     */
    public Boolean getIsHead() {
        return isHead;
    }

    /**
     * 设置是否表头属性
     * @param isHead 是否表头属性
     */
    public void setIsHead(Boolean isHead) {
        this.isHead = isHead;
    }

    /**
     * 获取是否可见
     * @return 是否可见
     */
    public Boolean getVisible() {
        return visible;
    }

    /**
     * 设置是否可见
     * @param visible 是否可见
     */
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    /**
     * 获取是否必填
     * @return 是否必填
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * 设置是否必填
     * @param required 是否必填
     */
    public void setRequired(Boolean required) {
        this.required = required;
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
