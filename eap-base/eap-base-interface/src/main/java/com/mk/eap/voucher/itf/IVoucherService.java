package com.mk.eap.voucher.itf;

import com.mk.eap.entity.dto.PageQueryDto;
import com.mk.eap.entity.dto.PageResultDto;
import com.mk.eap.entity.itf.IEntityService;
import com.mk.eap.entity.itf.IPageService;
import com.mk.eap.base.BusinessException;
import com.mk.eap.voucher.dto.VoucherDetailDto;
import com.mk.eap.voucher.dto.VoucherDto;

/**
 * 单据实体服务接口
 * @author gaoxue
 * 
 * @param <D> 单据实体 dto 对象类型
 * @param <DD> 单据明细 dto 类型
 */
public interface IVoucherService<D extends VoucherDto<DD>, DD extends VoucherDetailDto>
        extends IEntityService<D>, IPageService<D, PageQueryDto<D>, PageResultDto<D>> {

    /**
     * 根据单据 id 查询单据信息
     * @param orgId 组织 id
     * @param id 单据 id
     * @return 单据信息
     * @throws BusinessException
     */
    D queryById(Long orgId, Long id) throws BusinessException;

    /**
     * 获取上一张单据，根据单据编码排序
     * @param current 当前单据信息，需要传入组织 id，单据编码
     * @return 上一张单据
     * @throws NullPointerException 如果 {@code current} 为 {@code null}
     * @throws BusinessException
     */
    D previous(D current) throws BusinessException;

    /**
     * 获取下一张单据，根据单据编码排序
     * @param current 当前单据信息，需要传入组织 id，单据编码
     * @return 下一张单据
     * @throws NullPointerException 如果 {@code current} 为 {@code null}
     * @throws BusinessException
     */
    D next(D current) throws BusinessException;

}
