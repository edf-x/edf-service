package com.mk.eap.voucher.vo;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import com.mk.eap.base.VO;

/**
 * 单据 vo 基类
 * @author gaoxue
 *
 */
public abstract class VoucherBase extends VO {

    private static final long serialVersionUID = -8923332986808473624L;

    /** 组织机构 id 对应表[set_org] */
    @Id
    @Column(updatable = false)
    private Long orgId;

    /** id */
    @Id
    @Column(updatable = false)
    private Long id;

    /** 制单人 id 对应表 set_person 的 userid */
    @Column(updatable = false)
    private Long creator;

    /** 创建时间 */
    @Column(updatable = false)
    private Date createTime;

    /** 更新时间 */
    @Column(insertable = false)
    private Date updateTime;

    /** 时间戳 */
    @Column(insertable = false, updatable = false)
    private Timestamp ts;

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

}
