package com.mk.eap.voucher.itf;

import com.mk.eap.base.BusinessException;
import com.mk.eap.voucher.dto.VoucherDetailDto;
import com.mk.eap.voucher.dto.VoucherWithEnclosureDto;

/**
 * @author gaoxue
 *
 */
public interface IVoucherWithEnclosureService<D extends VoucherWithEnclosureDto<DD>, DD extends VoucherDetailDto>
        extends IVoucherService<D, DD> {

    /**
     * 单据审核
     * @param dto 待审核的单据，需要传入单据 id {@code id}、组织 {@code orgId}、审核人 {@code auditorId}、时间戳 {@code ts}
     * @return 审核后的单据
     * @throws BusinessException
     */
    D audit(D dto) throws BusinessException;

    /**
     * 反审核单据
     * @param dto 待反审核的单据，需要传入单据 id {@code id}、组织 {@code orgId}、时间戳 {@code ts}
     * @return 反审核后的单据
     * @throws BusinessException
     */
    D unaudit(D dto) throws BusinessException;

    /**
     * 驳回单据
     * @param dto 待驳回的单据，需要传入单据 id {@code id}、组织 {@code orgId}
     * @return 驳回后的单据
     * @throws BusinessException
     */
    D overrule(D dto) throws BusinessException;

    /**
     * 单据新增附件
     * @param dto 待新增附件的单据，必须传入主键对应的属性，时间戳 {@code ts}，传入 {@code updator} 作为附件 {@code creator}
     * <p>要新增的附件信息列表 {@code enclosures} 必须传入附件 id {@code enclosureId}
     * @return 
     * @throws BusinessException
     */
    D enclosureCreate(D dto) throws BusinessException;

    /**
     * 单据删除附件
     * @param dto 待删除附件的单据，必须传入主键对应的属性，时间戳 {@code ts}
     * <p>要删除的附件信息列表 {@code enclosures} 必须传入 id {@code id}、时间戳 {@code ts}
     * @return 
     * @throws BusinessException
     */
    D enclosureDelete(D dto) throws BusinessException;
}
