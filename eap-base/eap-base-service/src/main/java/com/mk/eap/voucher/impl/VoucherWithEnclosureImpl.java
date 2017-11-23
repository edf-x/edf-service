package com.mk.eap.voucher.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mk.eap.base.BusinessException;
import com.mk.eap.component.enclosure.itf.IVoucherEnclosureService;
import com.mk.eap.component.entity.dto.SetDtoDto;
import com.mk.eap.component.entity.itf.ISetDtoService;
import com.mk.eap.constant.ConstCode;
import com.mk.eap.enclosure.dto.VoucherEnclosureDto;
import com.mk.eap.entity.constant.ErrorCode;
import com.mk.eap.user.dto.UserDto;
import com.mk.eap.user.itf.IUserService;
import com.mk.eap.utils.StringUtil;
import com.mk.eap.validate.ValidateUtil;
import com.mk.eap.validate.group.Audit;
import com.mk.eap.validate.group.Overrule;
import com.mk.eap.voucher.dao.VoucherWithEnclosureMapper;
import com.mk.eap.voucher.dto.VoucherDetailDto;
import com.mk.eap.voucher.dto.VoucherWithEnclosureDto;
import com.mk.eap.voucher.vo.VoucherDetail;
import com.mk.eap.voucher.vo.VoucherWithEnclosure;
import com.mk.eap.entity.common.Const;
import com.mk.eap.validate.group.UnAudit;
import com.mk.eap.voucher.itf.IVoucherWithEnclosureService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author gaoxue
 *
 */
public class VoucherWithEnclosureImpl<D extends VoucherWithEnclosureDto<DD>, DD extends VoucherDetailDto, V extends VoucherWithEnclosure, DV extends VoucherDetail, M extends VoucherWithEnclosureMapper<V>>
        extends VoucherServiceImpl<D, DD, V, DV, M> implements IVoucherWithEnclosureService<D, DD> {

    protected IUserService userService;

    protected IVoucherEnclosureService enclosureService;

    protected ISetDtoService dtoService;

    @Override
    protected void postConstruct() {
        super.postConstruct();
        String dtoFullName = dtoClazz.getName();
        SetDtoDto dto = new SetDtoDto();
        dto.setFullName(dtoFullName);
        List<SetDtoDto> dtos = dtoService.query(dto);
        if (dtos.isEmpty() || dtos.size() > 1) {
            logger.debug("Class name " + dtoFullName + " find mutil or no records in table set_dto.");
        } else {
            dto = dtos.get(0);
            voucherTypeId = dto.getVoucherTypeId();
            codePrefix = dto.getCodePrefix();
            dtoId = dto.getId();
        }
    }

    @Override
    protected void setDefaultValue4Create(D createDto) {
        super.setDefaultValue4Create(createDto);
        createDto.setEnclosureCount(createDto.getEnclosures().size());
        List<VoucherEnclosureDto> enclosures = createDto.getEnclosures();
        int orderNumber = 1;
        for (VoucherEnclosureDto enclosure : enclosures) {
            enclosure.setOrgId(createDto.getOrgId());
            enclosure.setVoucherTypeId(voucherTypeId);
            enclosure.setVoucherId(createDto.getId());
            enclosure.setCreator(createDto.getCreator());
            enclosure.setOrderNumber(orderNumber);
            orderNumber++;
        }
    }

    @Override
    protected void beforeCreate(D createDto) {
        super.beforeCreate(createDto);
        if (StringUtil.isEmtryStr(createDto.getCreatorName())) {
            UserDto user = userService.findById(createDto.getCreator());
            createDto.setCreatorName(user.getNickname());
        }
        List<VoucherEnclosureDto> enclosures = innerDto.getEnclosures();
        if (!enclosures.isEmpty()) {
            validateEnclosure(enclosures);
        }
    }

    @Override
    protected D afterCreate(D createDto) {
        D newDto = super.afterCreate(createDto);
        createEnclosure(createDto);
        newDto = queryByPrimaryKey(createDto);
        return newDto;
    }

    @Override
    protected void afterDelete(D deleteDto) {
        super.afterDelete(deleteDto);

        if (!innerDto.getEnclosures().isEmpty()) {
            // 处理单据附件信息
            VoucherEnclosureDto enclosureDto = new VoucherEnclosureDto();
            enclosureDto.setOrgId(innerDto.getOrgId());
            enclosureDto.setVoucherTypeId(voucherTypeId);
            enclosureDto.setVoucherId(innerDto.getId());
            int result = enclosureService.deleteByVoucherId(enclosureDto);
            if (result != innerDto.getEnclosures().size()) {
                throw ErrorCode.EXCEPTION_CONCURRENCY_OPERATE_FAIL;
            }
        }
    }

    @Override
    protected void beforeUpdate(D updateDto) {
        super.beforeUpdate(updateDto);
        if (updateDto.getIsEnclosureModify()) {
            List<VoucherEnclosureDto> enclosures = updateDto.getEnclosures();
            int orderNumber = 1;
            for (VoucherEnclosureDto enclosure : enclosures) {
                enclosure.setOrgId(updateDto.getOrgId());
                enclosure.setVoucherTypeId(voucherTypeId);
                enclosure.setVoucherId(updateDto.getId());
                enclosure.setCreator(updateDto.getUpdator());
                enclosure.setOrderNumber(orderNumber);
                orderNumber++;
            }
            if (!enclosures.isEmpty()) {
                validateEnclosure(enclosures);
            }
            updateDto.setEnclosureCount(enclosures.size());
        } else {
            updateDto.setEnclosureCount(innerDto.getEnclosureCount());
        }
    }

    @Override
    protected D afterUpdate(D updateDto) {
        D newDto = super.afterUpdate(updateDto);
        if (updateDto.getIsEnclosureModify()) {
            List<VoucherEnclosureDto> enclosures = updateDto.getEnclosures();
            Integer enclosureCount = innerDto.getEnclosureCount();
            if (enclosureCount != null && enclosureCount > 0) {
                VoucherEnclosureDto enclosureDto = new VoucherEnclosureDto();
                enclosureDto.setOrgId(innerDto.getOrgId());
                enclosureDto.setVoucherTypeId(voucherTypeId);
                enclosureDto.setVoucherId(innerDto.getId());
                enclosureService.deleteByVoucherId(enclosureDto);
            }
            if (!enclosures.isEmpty()) {
                enclosureService.createBatch(enclosures);
            }
            newDto = queryByPrimaryKey(updateDto);
        }
        return newDto;
    }

    @Override
    protected List<D> afterQuery(List<D> resultList) {
        resultList = super.afterQuery(resultList);
        for (D item : resultList) {
            if (item.getEnclosureCount() == null || item.getEnclosureCount() == 0) {
                continue;
            }
            // 处理附件信息
            VoucherEnclosureDto enclosureDto = new VoucherEnclosureDto();
            enclosureDto.setOrgId(item.getOrgId());
            enclosureDto.setVoucherTypeId(voucherTypeId);
            enclosureDto.setVoucherId(item.getId());
            item.setEnclosures(enclosureService.queryByVoucherId(enclosureDto));
        }
        return resultList;
    }

    @Override
    protected D afterQueryByPrimaryKey(D queryDto) {
        queryDto = super.afterQueryByPrimaryKey(queryDto);
        if (queryDto.getEnclosureCount() != null && queryDto.getEnclosureCount() > 0) {
            // 处理附件信息
            VoucherEnclosureDto enclosureDto = new VoucherEnclosureDto();
            enclosureDto.setOrgId(queryDto.getOrgId());
            enclosureDto.setVoucherTypeId(voucherTypeId);
            enclosureDto.setVoucherId(queryDto.getId());
            queryDto.setEnclosures(enclosureService.queryByVoucherId(enclosureDto));
            ;
        }
        return queryDto;
    }

    private void createEnclosure(D billDto) {
        List<VoucherEnclosureDto> enclosures = billDto.getEnclosures();
        if (enclosures.isEmpty()) {
            return;
        }
        int orderNumber = 1;
        for (VoucherEnclosureDto enclosure : enclosures) {
            enclosure.setOrgId(billDto.getOrgId());
            enclosure.setVoucherTypeId(voucherTypeId);
            enclosure.setVoucherId(billDto.getId());
            enclosure.setCreator(billDto.getCreator());
            enclosure.setOrderNumber(orderNumber);
            orderNumber++;
        }
        enclosureService.createBatch(enclosures);
    }

    /**
     * 校验附件数量是否超出限制
     * @param enclosures
     */
    private void validateEnclosure(List<VoucherEnclosureDto> enclosures) {
        boolean valid = true;
        if (enclosures.size() > Const.VOUCHER_MAX_ENCLOSURE_COUNT) {
            valid = false;
        } else {
            int imgCount = 0;
            int otherCount = 0;
            for (VoucherEnclosureDto enclosure : enclosures) {
                Long type = enclosure.getElType();
                if (type != null && type.longValue() == ConstCode.ELTYPE_0001) {
                    imgCount++;
                } else {
                    otherCount++;
                }
            }
            if (imgCount > Const.VOUCHER_MAX_IMG_ENCLOSURE_COUNT
                    || otherCount > Const.VOUCHER_MAX_OTHER_ENCLOSURE_COUNT) {
                valid = false;
            }
        }
        if (!valid) {
            throw ErrorCode.EXCEPTION_ENCLOSURE_OVERFLOW;
        }
    }

    @Override
    @Transactional
    public D audit(D dto) throws BusinessException {
        checkNull(dto);
        ValidateUtil.validate(dto, Audit.class);
        this.innerDto = queryByPrimaryKey(dto);
        checkConcurrency(this.innerDto, dto);
        if (StringUtil.isEmtryStr(dto.getAuditorName())) {
            UserDto user = userService.findById(dto.getAuditorId());
            dto.setAuditorName(user.getNickname());
        }
        dto.setAuditTime(new Date());
        dto.setStatus(ConstCode.SCMBILLSTATUS_Approved);
        beforeAudit(dto);
        V vo = dto.toVo(voClazz);
        int result = mapper.auditUpdate(vo);
        if (result != 1) {
            throw ErrorCode.EXCEPTION_CONCURRENCY_OPERATE_FAIL;
        }
        D newDto = afterAudit(dto);
        return newDto;
    }

    /**
     * 审核前操作
     * @param auditDto 待审核的单据
     */
    protected void beforeAudit(D auditDto) {

    }

    /**
     * 审核后操作
     * @param auditDto 待审核的单据
     * @return 已审核的单据
     */
    protected D afterAudit(D auditDto) {
        return queryByPrimaryKey(auditDto);
    }

    @Override
    @Transactional
    public D unaudit(D dto) throws BusinessException {
        checkNull(dto);
        ValidateUtil.validate(dto, UnAudit.class);
        this.innerDto = queryByPrimaryKey(dto);
        checkConcurrency(this.innerDto, dto);
        dto.setStatus(ConstCode.SCMBILLSTATUS_NotApprove);
        beforeUnaudit(dto);
        V vo = dto.toVo(voClazz);
        int result = mapper.unauditUpdate(vo);
        if (result != 1) {
            throw ErrorCode.EXCEPTION_CONCURRENCY_OPERATE_FAIL;
        }
        D newDto = afterUnaudit(dto);
        return newDto;
    }

    /**
     * 反审核前操作
     * @param unauditDto 待反审核的单据
     */
    protected void beforeUnaudit(D unauditDto) {

    }

    /**
     * 反审核后操作
     * @param unauditDto 待反审核的单据
     * @return 反审核后的单据
     */
    protected D afterUnaudit(D unauditDto) {
        return queryByPrimaryKey(unauditDto);
    }

    @Override
    @Transactional
    public D overrule(D dto) throws BusinessException {
        checkNull(dto);
        ValidateUtil.validate(dto, Overrule.class);
        this.innerDto = queryByPrimaryKey(dto);
        dto.setTs(this.innerDto.getTs());
        checkConcurrency(this.innerDto, dto);
        dto.setStatus(ConstCode.SCMBILLSTATUS_Rejected);
        beforeOverrule(dto);
        V vo = dto.toVo(voClazz);
        int result = mapper.unauditUpdate(vo);
        if (result != 1) {
            throw ErrorCode.EXCEPTION_CONCURRENCY_OPERATE_FAIL;
        }
        D newDto = afterOverrule(dto);
        return newDto;
    }

    /**
     * 驳回前操作
     * @param overruleDto 待驳回的单据
     */
    protected void beforeOverrule(D overruleDto) {

    }

    /**
     * 驳回后操作
     * @param overruleDto 待驳回的单据
     * @return 驳回后的单据
     */
    protected D afterOverrule(D overruleDto) {
        return queryByPrimaryKey(overruleDto);
    }

    @Override
    @Transactional
    public D enclosureCreate(D dto) throws BusinessException {
        List<VoucherEnclosureDto> enclosures = dto.getEnclosures();
        if (enclosures.isEmpty()) {
            return dto;
        }
        for (VoucherEnclosureDto enclosure : enclosures) {
            enclosure.setOrgId(dto.getOrgId());
            enclosure.setVoucherTypeId(voucherTypeId);
            enclosure.setVoucherId(dto.getId());
            enclosure.setCreator(dto.getUpdator());
        }
        D oldDto = queryByPrimaryKey(dto);
        Integer enclosureCount = oldDto.getEnclosureCount();
        if (enclosureCount == null) {
            enclosureCount = 0;
        }
        enclosureCount += enclosures.size();
        List<VoucherEnclosureDto> enclosures4Check = new ArrayList<>();
        enclosures4Check.addAll(enclosures);
        if (!oldDto.getEnclosures().isEmpty()) {
            enclosures4Check.addAll(oldDto.getEnclosures());
        }
        validateEnclosure(enclosures4Check);
        dto.setEnclosureCount(enclosureCount);
        dto = update(dto);
        enclosureService.createBatch(enclosures);
        return dto;
    }

    @Override
    @Transactional
    public D enclosureDelete(D dto) throws BusinessException {
        List<VoucherEnclosureDto> enclosures = dto.getEnclosures();
        if (enclosures.isEmpty()) {
            return dto;
        }
        for (VoucherEnclosureDto enclosure : enclosures) {
            enclosure.setOrgId(dto.getOrgId());
        }
        D oldDto = queryByPrimaryKey(dto);
        Integer enclosureCount = oldDto.getEnclosureCount();
        if (enclosureCount == null) {
            enclosureCount = 0;
        }
        dto.setEnclosureCount(enclosureCount - enclosures.size());
        dto = update(dto);
        enclosureService.deleteBatch(enclosures);
        return dto;
    }

    @Reference
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Reference
    public void setEnclosureService(IVoucherEnclosureService enclosureService) {
        this.enclosureService = enclosureService;
    }

    @Reference
    public void setDtoService(ISetDtoService dtoService) {
        this.dtoService = dtoService;
    }

}
