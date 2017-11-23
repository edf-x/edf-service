package com.mk.eap.component.enclosure.dao;

import com.mk.eap.enclosure.dto.VoucherEnclosureDto;
import com.mk.eap.enclosure.vo.VoucherEnclosure;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 单据附件数据库访问接口定义
 * @author gaoxue
 *
 */
@Repository
public interface VoucherEnclosureMapper {

    /**
     * 新增单据附件
     * @param vo 要新增的单据附件信息
     * @return 新增记录数
     */
    int create(VoucherEnclosure vo);

    /**
     * 批量新增单据附件
     * @param vos 要新增的单据附件信息列表
     * @return 新增记录数
     */
    int createBatch(List<VoucherEnclosure> vos);

    /**
     * 更新单据附件
     * @param vo 要更新的单据附件信息，传入组织 orgId、id、时间戳 ts 确定要更新的单据附件，传入其他属性确定要更新的内容
     * @return 更新记录数
     */
    int update(VoucherEnclosure vo);

    /**
     * 批量更新单据附件
     * @param vos 要更新的单据附件信息列表，传入组织 orgId、id、时间戳 ts 确定要更新的单据附件，传入其他属性确定要更新的内容
     */
    void updateBatch(List<VoucherEnclosure> vos);

    /**
     * 删除单据附件
     * @param vo 要删除的单据附件信息，传入组织 orgId、id、时间戳 ts 确定要删除的单据附件
     * @return 删除记录数
     */
    int delete(VoucherEnclosure vo);

    /**
     * 批量删除单据附件
     * @param vos 要删除的单据附件信息列表，传入组织 orgId、id、时间戳 ts 确定要删除的单据附件
     * @return 删除记录数
     */
    int deleteBatch(List<VoucherEnclosure> vos);

    /**
     * 删除单据的单据附件
     * @param vo 要删除单据附件的单据信息，传入组织 orgId、单据类型 voucherTypeId、单据 voucherId 确定要删除的单据附件
     * @return 删除记录数
     */
    int deleteByVoucherId(VoucherEnclosure vo);

    /**
     * 删除单据的单据附件
     * @param vos 要删除单据附件的单据信息列表，传入组织 orgId、单据类型 voucherTypeId、单据 voucherId 确定要删除的单据附件
     * @return 删除记录数
     */
    int deleteByVoucherIds(List<VoucherEnclosure> vos);

    /**
     * 查询单据附件
     * @param vo 查询条件，传入组织 orgId 以及其他属性作为查询条件
     * @return 单据附件信息列表
     */
    List<VoucherEnclosureDto> query(VoucherEnclosure vo);

    /**
     * 根据 id 列表查询单据附件
     * @param vos id 列表，需要传入 id 、组织 orgId
     * @return 单据附件信息列表
     */
    List<VoucherEnclosureDto> queryByIdList(List<VoucherEnclosure> vos);

}
