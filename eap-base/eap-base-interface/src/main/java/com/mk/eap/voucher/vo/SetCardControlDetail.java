package com.mk.eap.voucher.vo;

import com.mk.eap.base.VO;

import java.sql.Timestamp;

/**
 * 实体类编辑配置方案详细信息 vo 实体定义类
 * @author gaox0
 *
 */
public class SetCardControlDetail extends VO {

    private static final long serialVersionUID = 4903120365593162728L;

    /** 组织 id 对应表 set_org */
    private Long orgId;

    /** 配置方案 id 对应表 set_card_control */
    private Long controlId;

    /** id */
    private Long id;

    /** 实体类属性档案 id 对应表 set_dto_property */
    private Long propertyId;

    /** 是否可见 */
    private Boolean visible;

    /** 是否必填 */
    private Boolean required;

    /** 时间戳 */
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
