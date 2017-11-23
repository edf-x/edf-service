package com.mk.eap.entity.common;

import com.mk.eap.voucher.vo.VoucherWithEnclosure;
import org.apache.ibatis.annotations.UpdateProvider;


/**
 * 实体审核操作的通用 Mapper 接口
 * @author gaoxue
 *
 * @param <V> vo 实体类型
 */
public interface VoucherAuditMapper<V extends VoucherWithEnclosure> {

    /**
     * 单据审核更新，审核人 {@code auditorId}，审核人姓名 {@code auditorName}，审核时间 {@code auditTime}，单据状态 {@code status}，凭证 {@code docId}
     * @param vo 需要审核的单据
     * @return 更新的记录数
     */
    @UpdateProvider(type = VoucherAuditProvider.class, method = "dynamicSQL")
    int auditUpdate(V vo);

    /**
     * 单据反审核更新，审核人 {@code auditorId}，审核人姓名 {@code auditorName}，审核时间 {@code auditTime}，单据状态 {@code status}，凭证 {@code docId}
     * @param vo 需要反审核的单据
     * @return 更新的记录数
     */
    @UpdateProvider(type = VoucherAuditProvider.class, method = "dynamicSQL")
    int unauditUpdate(V vo);

}
