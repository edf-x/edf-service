package com.mk.eap.voucher.dto;

import com.mk.eap.validate.group.Delete;
import com.mk.eap.validate.group.Update;
import com.mk.eap.validate.YJNotNull;
import com.mk.eap.base.DTO;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 实体类列表显示/编辑配置方案 dto 实体定义类
 * @author gaoxue
 *
 */
public class SetCardControlDto extends DTO implements Comparable<SetCardControlDto> {

    private static final long serialVersionUID = 367333159390495528L;

    /** 组织 id 对应表 set_org */
    @YJNotNull(alias = "企业 id ")
    private Long orgId;

    /** 实体类档案 id 对应表 set_dto */
    @YJNotNull(alias = "实体档案 id", groups = { Update.class })
    private Long dtoId;

    /** id */
    @YJNotNull(alias = "id", groups = { Delete.class })
    private Long id;

    /** 类型：0，列表；1，卡片 */
    private Integer type;

    /** 名称 */
    private String name;

    /** 是否默认方案 */
    private Boolean isDefault;

    /** 修改时间 */
    private Date updateTime;

    /** 时间戳 */
    @YJNotNull(alias = "时间戳", groups = { Delete.class })
    private Timestamp ts;

    /** 实体类编辑配置方案详细信息列表 */
    @Valid
    private List<SetCardControlDetailDto> details = new ArrayList<>();

    @Override
    public int compareTo(SetCardControlDto o) {
        if (o == null) {
            return -1;
        }
        if (o.getId() == null && getId() == null) {
            return 0;
        }
        if (getId() == null) {
            return 1;
        }
        if (o.getId() == null) {
            return -1;
        }
        return getId().compareTo(o.getId());
    }

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
     * 获取修改时间
     * @return 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    /**
     * 获取实体类编辑配置方案详细信息列表
     * @return 实体类编辑配置方案详细信息列表
     */
    public List<SetCardControlDetailDto> getDetails() {
        return details;
    }

    /**
     * 设置实体类编辑配置方案详细信息列表
     * @param details 实体类编辑配置方案详细信息列表
     */
    public void setDetails(List<SetCardControlDetailDto> details) {
        if (details == null) {
            this.details = new ArrayList<>();
        } else {
            this.details = details;
        }
    }

}
