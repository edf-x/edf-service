package com.mk.eap.voucher.vo;

import com.mk.eap.base.VO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * 实体类编辑配置方案 vo 实体定义类
 * @author gaoxue
 *
 */
@Table(name = "set_card_control")
public class SetCardControl extends VO {

    private static final long serialVersionUID = 6788297099750569153L;

    /** 组织 id 对应表 set_org */
    @Id
    private Long orgId;

    /** 实体类档案 id 对应表 set_dto */
    @Id
    private Long dtoId;

    /** id */
    @Id
    private Long id;

    /** 类型：0，列表；1，卡片 */
    @Column(updatable = false)
    private Integer type;

    /** 名称 */
    private String name;

    /** 是否默认方案 */
    private Boolean isDefault;

    /** 时间戳 */
    @Column(insertable = false, updatable = false)
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
     * 获取类型：0，列表；1，卡片
     * @return 类型：0，列表；1，卡片
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型：0，列表；1，卡片
     * @param type 类型：0，列表；1，卡片
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取名称
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取是否默认方案
     * @return 是否默认方案
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * 设置是否默认方案
     * @param isDefault 是否默认方案
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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
