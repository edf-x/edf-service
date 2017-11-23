package com.mk.eap.voucher.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mk.eap.base.OperateStatus;
import com.mk.eap.validate.group.Delete;
import com.mk.eap.validate.YJNotNull;
import com.mk.eap.validate.group.Create;
import com.mk.eap.validate.group.Overrule;
import com.mk.eap.base.DTO;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 单据 dto 实体基类
 * @author gaoxue
 *
 */
public abstract class VoucherBaseDto extends DTO {

    private static final long serialVersionUID = -4187859500123656746L;

    /** 组织机构 id 对应表[set_org] */
    @YJNotNull(alias = "企业 id ")
    private Long orgId;

    /** id */
    @YJNotNull(alias = "id", groups = { Delete.class, Overrule.class })
    private Long id;

    /** 制单人 id 对应表 set_person 的 userid */
    @YJNotNull(alias = "制单人", groups = { Create.class })
    private Long creator;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 时间戳 */
    @YJNotNull(alias = "时间戳", groups = { Delete.class })
    private Timestamp ts;

    /** 操作状态，此字段用于前端调用 webapi 时传递参数 */
    @JsonIgnore
    private OperateStatus operateStatus;

    /**
     * 获取组织机构 id 对应表[set_org]
     * @return 组织机构 id 对应表[set_org]
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置组织机构 id 对应表[set_org]
     * @param orgId 组织机构 id 对应表[set_org]
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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
     * 获取制单人 id 对应表 set_person 的 userid
     * @return 制单人 id 对应表 set_person 的 userid
     */
    public Long getCreator() {
        return creator;
    }

    /**
     * 设置制单人 id 对应表 set_person 的 userid
     * @param creator 制单人 id 对应表 set_person 的 userid
     */
    public void setCreator(Long creator) {
        this.creator = creator;
    }

    /**
     * 获取创建时间
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取updateTime
     * @return updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置updateTime
     * @param updateTime updateTime
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
     * 获取操作状态，此字段用于前端调用 webapi 时传递参数
     * @return 操作状态
     */
    public OperateStatus getOperateStatus() {
        return operateStatus;
    }

    /**
     * 设置操作状态，此字段用于前端调用 webapi 时传递参数
     * @param operateStatus 操作状态
     */
    public void setOperateStatus(OperateStatus operateStatus) {
        this.operateStatus = operateStatus;
    }

}
