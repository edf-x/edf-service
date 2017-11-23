package com.mk.eap.component.enclosure.itf;

import com.mk.eap.base.BusinessException;
import com.mk.eap.enclosure.dto.VoucherEnclosureDto;

import java.util.List;

/**
 * 单据附件服务接口定义
 * @author gaoxue
 */
public interface IVoucherEnclosureService {

    /**
     * 新增单据附件
     * @param dto 要新增的单据附件信息，传入组织 orgId、单据类型 voucherTypeId、单据 voucherId、附件 enclosureId
     * @return 单据附件信息
     * @throws BusinessException
     */
    VoucherEnclosureDto create(VoucherEnclosureDto dto) throws BusinessException;

    /**
     * 批量新增单据附件
     * @param dtos 要新增的单据附件信息列表，传入组织 orgId、单据类型 voucherTypeId、单据 voucherId、附件 enclosureId
     * @return 单据附件信息列表
     * @throws BusinessException
     */
    List<VoucherEnclosureDto> createBatch(List<VoucherEnclosureDto> dtos) throws BusinessException;

    /**
     * 更新单据附件
     * @param dto 要更新的单据附件信息，传入组织 orgId、id、时间戳 ts 确定要更新的单据附件，传入其他属性确定要更新的内容
     * @return 单据附件信息
     * @throws BusinessException
     */
    VoucherEnclosureDto update(VoucherEnclosureDto dto) throws BusinessException;

    /**
     * 批量更新单据附件
     * @param dtos 要更新的单据附件信息列表，传入组织 orgId、id、时间戳 ts 确定要更新的单据附件，传入其他属性确定要更新的内容
     * @return 单据附件信息列表
     * @throws BusinessException
     */
    List<VoucherEnclosureDto> updateBatch(List<VoucherEnclosureDto> dtos) throws BusinessException;

    /**
     * 删除单据附件
     * @param dto 要删除的单据附件信息，传入组织 orgId、id、时间戳 ts 确定要删除的单据附件
     * @return 单据附件信息
     * @throws BusinessException
     */
    VoucherEnclosureDto delete(VoucherEnclosureDto dto) throws BusinessException;

    /**
     * 批量删除单据附件
     * @param dtos 要删除的单据附件信息列表，传入组织 orgId、id、时间戳 ts 确定要删除的单据附件
     * @return 单据附件信息列表
     * @throws BusinessException
     */
    List<VoucherEnclosureDto> deleteBatch(List<VoucherEnclosureDto> dtos) throws BusinessException;

    /**
     * 删除单据的附件信息
     * @param dto 要删除单据附件的单据信息，传入组织 orgId、单据类型 voucherTypeId、单据 voucherId 确定要删除的单据附件
     * @return 删除的单据附件数量
     * @throws BusinessException
     */
    int deleteByVoucherId(VoucherEnclosureDto dto) throws BusinessException;

    /**
     * 删除单据的附件信息
     * @param dtos 要删除单据附件的单据信息列表，传入组织 orgId、单据类型 voucherTypeId、单据 voucherId 确定要删除的单据附件
     * @return 删除的单据附件数量
     * @throws BusinessException
     */
    int deleteByVoucherIds(List<VoucherEnclosureDto> dtos) throws BusinessException;

    /**
     * 查询单据附件信息
     * @param dto 要查询的单据附件信息，传入组织 orgId、id 确定要查询的单据附件
     * @return 单据附件信息
     * @throws BusinessException
     */
    VoucherEnclosureDto queryById(VoucherEnclosureDto dto) throws BusinessException;

    /**
     * 查询单据的单据附件信息
     * @param dto 要查询单据附件的单据信息，传入组织 orgId、单据类型 voucherTypeId、单据 voucherId 确定要查询的单据信息
     * @return 单据附件信息列表
     * @throws BusinessException
     */
    List<VoucherEnclosureDto> queryByVoucherId(VoucherEnclosureDto dto) throws BusinessException;

}
