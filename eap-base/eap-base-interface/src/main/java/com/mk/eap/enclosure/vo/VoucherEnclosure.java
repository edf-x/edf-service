package com.mk.eap.enclosure.vo;

import com.mk.eap.base.VO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 单据附件 vo 实体定义
 * @author gaoxue
 *
 */
@Table(name = "set_voucher_enclosure")
public class VoucherEnclosure extends VO {

    private static final long serialVersionUID = 4520061485016936322L;

    /** 组织id 对应表 set_org */
    @Id
    protected Long orgId;

    /** 单据类型 枚举 voucherType id，进、销货单，收款单、付款单等 */
    protected Long voucherTypeId;

    /** 对应单据表头 id */
    protected Long voucherId;

    /** 附件id 对应表 set_enclosure */
    protected Long enclosureId;

    /** id */
    @Id
    protected Long id;

    /** 编码 */
    protected String code;

    /** 顺序号 */
    protected Integer orderNumber;

    /** 制单人 id 对应表 set_person 的 userid */
    @Column(updatable = false)
    protected Long creator;

    /** 制单时间 */
    @Column(updatable = false)
    protected Date createTime;

    /** 更新时间 */
    @Column(insertable = false)
    protected Date updateTime;

    /** 时间戳 */
    @Column(insertable = false, updatable = false)
    protected Timestamp ts;

    /**
     * 获取组织id 对应表 set_org
     * @return 组织id 对应表 set_org
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置组织id 对应表 set_org
     * @param orgId 组织id 对应表 set_org
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取单据类型 枚举 voucherType id，进、销货单，收款单、付款单等
     * @return 单据类型 枚举 voucherType id，进、销货单，收款单、付款单等
     */
    public Long getVoucherTypeId() {
        return voucherTypeId;
    }

    /**
     * 设置单据类型 枚举 voucherType id，进、销货单，收款单、付款单等
     * @param voucherTypeId 单据类型 枚举 voucherType id，进、销货单，收款单、付款单等
     */
    public void setVoucherTypeId(Long voucherTypeId) {
        this.voucherTypeId = voucherTypeId;
    }

    /**
     * 获取单据表头 id
     * @return 单据表头 id
     */
    public Long getVoucherId() {
        return voucherId;
    }

    /**
     * 设置单据表头 id
     * @param voucherId 单据表头 id
     */
    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    /**
     * 获取附件id 对应表 set_enclosure
     * @return 附件id 对应表 set_enclosure
     */
    public Long getEnclosureId() {
        return enclosureId;
    }

    /**
     * 设置附件id 对应表 set_enclosure
     * @param enclosureId 附件id 对应表 set_enclosure
     */
    public void setEnclosureId(Long enclosureId) {
        this.enclosureId = enclosureId;
    }

    /**
     * 获取 id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置 id
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取编码
     * @return 编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置编码
     * @param code 编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取顺序号
     * @return 顺序号
     */
    public Integer getOrderNumber() {
        return orderNumber;
    }

    /**
     * 设置顺序号
     * @param orderNumber 顺序号
     */
    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
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
     * 获取制单时间
     * @return 制单时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置制单时间
     * @param createTime 制单时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     * @return 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     * @param updateTime 更新时间
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
