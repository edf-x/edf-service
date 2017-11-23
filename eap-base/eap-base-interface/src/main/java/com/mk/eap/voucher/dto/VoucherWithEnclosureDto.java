package com.mk.eap.voucher.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mk.eap.enclosure.dto.VoucherEnclosureDto;
import com.mk.eap.validate.YJNotNull;
import com.mk.eap.validate.group.Audit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author gaoxue
 *
 */
public class VoucherWithEnclosureDto<D extends VoucherDetailDto> extends VoucherDto<D> {

    private static final long serialVersionUID = 2965485955691041671L;

    /** 制单人姓名 */
    private String creatorName;

    /** 审核人 id 对应表 set_person 的 userid */
    @YJNotNull(alias = "审核人", groups = Audit.class)
    private Long auditorId;

    /** 审核人姓名 */
    private String auditorName;

    /** 审核时间 */
    private Date auditTime;

    /** 单据状态 枚举 scmBillStatus id */
    private Long status;

    /** 单据状态 */
    private String statusName;

    /** 凭证 id 对应表 fi_journal docId */
    private Long docId;

    /** 是否修改了附件 */
    private Boolean isEnclosureModify = false;

    /** 附件数 */
    private Integer enclosureCount;

    /** 单据附件列表 */
    private List<VoucherEnclosureDto> enclosures = new ArrayList<>();

    /**
     * 获取制单人姓名
     * @return creatorName 制单人姓名
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * 设置制单人姓名
     * @param creatorName 制单人姓名
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * 获取审核人 id 对应表 set_person 的 userid
     * @return 审核人 id 对应表 set_person 的 userid
     */
    public Long getAuditorId() {
        return auditorId;
    }

    /**
     * 设置审核人 id 对应表 set_person 的 userid
     * @param auditorId 审核人 id 对应表 set_person 的 userid
     */
    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }

    /**
     * 获取审核人姓名
     * @return auditorName 审核人姓名
     */
    public String getAuditorName() {
        return auditorName;
    }

    /**
     * 设置审核人姓名
     * @param auditorName 审核人姓名
     */
    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    /**
     * 获取审核时间
     * @return 审核时间
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 设置审核时间
     * @param auditTime 审核时间
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 获取单据状态 枚举 scmBillStatus id
     * @return 单据状态 枚举 scmBillStatus id
     */
    public Long getStatus() {
        return status;
    }

    /**
     * 设置单据状态 枚举 scmBillStatus id
     * @param status 单据状态 枚举 scmBillStatus id
     */
    public void setStatus(Long status) {
        this.status = status;
    }

    /**
     * 获取单据状态 
     * @return 单据状态 
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * 设置单据状态 
     * @param statusName 单据状态 
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * 获取凭证 id 对应表 fi_journal docId
     * @return 凭证 id 对应表 fi_journal docId
     */
    public Long getDocId() {
        return docId;
    }

    /**
     * 设置凭证 id 对应表 fi_journal docId
     * @param docId 凭证 id 对应表 fi_journal docId
     */
    public void setDocId(Long docId) {
        this.docId = docId;
    }

    /**
     * 获取是否修改了附件
     * @return 是否修改了附件
     */
    @JsonIgnore
    public Boolean getIsEnclosureModify() {
        return isEnclosureModify;
    }

    /**
     * 设置是否修改了附件
     * @param isEnclosureModify 是否修改了附件
     */
    @JsonProperty
    public void setIsEnclosureModify(Boolean isEnclosureModify) {
        this.isEnclosureModify = isEnclosureModify;
    }

    /**
     * 获取附件数
     * @return 附件数
     */
    public Integer getEnclosureCount() {
        return enclosureCount;
    }

    /**
     * 设置附件数
     * @param enclosureCount 附件数
     */
    public void setEnclosureCount(Integer enclosureCount) {
        this.enclosureCount = enclosureCount;
    }

    /**
     * 获取单据附件列表
     * @return 单据附件列表
     */
    public List<VoucherEnclosureDto> getEnclosures() {
        return enclosures;
    }

    /**
     * 设置单据附件列表
     * @param enclosures 单据附件列表
     */
    public void setEnclosures(List<VoucherEnclosureDto> enclosures) {
        if (enclosures == null) {
            this.enclosures = new ArrayList<>();
        } else {
            this.enclosures = enclosures;
        }
    }

}
