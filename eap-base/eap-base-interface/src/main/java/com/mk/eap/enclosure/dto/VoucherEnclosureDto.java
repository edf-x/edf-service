package com.mk.eap.enclosure.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mk.eap.base.DTO;
import com.mk.eap.base.OperateStatus;
import com.mk.eap.validate.group.Delete;
import com.mk.eap.validate.YJNotNull;
import com.mk.eap.validate.group.Create;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 单据附件 dto 实体定义
 * @author gaoxue
 *
 */
public class VoucherEnclosureDto extends DTO {

    private static final long serialVersionUID = 2021001380194725939L;

    /** 组织id 对应表 set_org */
    @YJNotNull(alias = "企业")
    protected Long orgId;

    /** 单据类型 枚举 voucherType id，进、销货单，收款单、付款单等 */
    @YJNotNull(alias = "单据类型", groups = { Create.class })
    protected Long voucherTypeId;

    /** 对应单据表头 id */
    @YJNotNull(alias = "单据 id ", groups = { Create.class })
    protected Long voucherId;

    /** 附件id 对应表 set_enclosure */
    @YJNotNull(alias = "附件 id ", groups = { Create.class })
    protected Long enclosureId;

    /** id */
    @YJNotNull(alias = " id ", groups = { Delete.class })
    protected Long id;

    /** 编码 */
    protected String code;

    /** 顺序号 */
    protected Integer orderNumber;

    /** 制单人 id 对应表 set_person 的 userid */
    protected Long creator;

    /** 制单时间 */
    protected Date createTime;

    /** 更新时间 */
    protected Date updateTime;

    /** 时间戳 */
    @YJNotNull(alias = "时间戳", groups = { Delete.class })
    protected Timestamp ts;
    
    /** 操作状态，此字段用于前端调用 webapi 时传递参数 */
    protected Byte operateStatus;

    /** 附件类型 */
    @JsonProperty("fileType")
    protected Long elType;

    @JsonProperty("fileSize")
    protected String elSize;

    @JsonProperty("displayName")
    protected String oldName;

    @JsonProperty("name")
    protected String newName;

    @JsonProperty("suffix")
    protected String elSuffix;

    protected String elMd5;

    /**
     * 获取操作类型
     * @return 操作类型
     */
    @JsonIgnore
    public OperateStatus getOperateStatusEnum() {
        OperateStatus[] list = OperateStatus.values();
        if (operateStatus == null || operateStatus < 0 || operateStatus >= list.length) {
            return OperateStatus.Unchanged;
        }
        return list[operateStatus];
    }
    
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

    /**
     * 获取操作状态，此字段用于前端调用 webapi 时传递参数
     * @see getOperateStatusEnum
     * @return 操作状态
     */
    public Byte getOperateStatus() {
        return operateStatus;
    }

    /**
     * 设置操作状态，此字段用于前端调用 webapi 时传递参数
     * @param operateStatus 操作状态
     */
    public void setOperateStatus(Byte operateStatus) {
        this.operateStatus = operateStatus;
    }

    /**
     * 获取elType
     * @return elType
     */
    public Long getElType() {
        return elType;
    }

    /**
     * 设置elType
     * @param elType elType
     */
    public void setElType(Long elType) {
        this.elType = elType;
    }

    /**
     * 获取elSize
     * @return elSize
     */
    public String getElSize() {
        return elSize;
    }

    /**
     * 设置elSize
     * @param elSize elSize
     */
    public void setElSize(String elSize) {
        this.elSize = elSize;
    }

    /**
     * 获取oldName
     * @return oldName
     */
    public String getOldName() {
        return oldName;
    }

    /**
     * 设置oldName
     * @param oldName oldName
     */
    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    /**
     * 获取newName
     * @return newName
     */
    public String getNewName() {
        return newName;
    }

    /**
     * 设置newName
     * @param newName newName
     */
    public void setNewName(String newName) {
        this.newName = newName;
    }

    /**
     * 获取elSuffix
     * @return elSuffix
     */
    public String getElSuffix() {
        return elSuffix;
    }

    /**
     * 设置elSuffix
     * @param elSuffix elSuffix
     */
    public void setElSuffix(String elSuffix) {
        this.elSuffix = elSuffix;
    }

    /**
     * 获取elMd5
     * @return elMd5
     */
    public String getElMd5() {
        return elMd5;
    }

    /**
     * 设置elMd5
     * @param elMd5 elMd5
     */
    public void setElMd5(String elMd5) {
        this.elMd5 = elMd5;
    }

}
