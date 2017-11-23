package com.mk.eap.component.enclosure.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.mk.eap.component.common.oid.itf.IOidService;
import com.mk.eap.enclosure.dto.VoucherEnclosureDto;
import com.mk.eap.component.enclosure.itf.IVoucherEnclosureService;
import com.mk.eap.enclosure.vo.VoucherEnclosure;
import com.mk.eap.base.BusinessException;
import com.mk.eap.entity.constant.ErrorCode;
import com.mk.eap.component.enclosure.dao.VoucherEnclosureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 单据附件服务接口实现
 * @author gaoxue
 *
 */
@Component
@Service
public class VoucherEnclosureService implements IVoucherEnclosureService {

    @Autowired
    private VoucherEnclosureMapper mapper;
    
    @Reference
    private IOidService oidService;

    @Override
    @Transactional
    public VoucherEnclosureDto create(VoucherEnclosureDto dto) throws BusinessException {
        checkCreate(dto);
        set2Create(dto);
        VoucherEnclosure vo = dto.toVo(VoucherEnclosure.class);
        int result = mapper.create(vo);
        if (1 != result) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_DB_OPERATE_FAIL,
                    ErrorCode.EXCEPTION_MESSAGE_DB_OPERATE_FAIL);
        }
        VoucherEnclosureDto current = queryById(dto);
        return current;
    }

    @Override
    @Transactional
    public List<VoucherEnclosureDto> createBatch(List<VoucherEnclosureDto> dtos) throws BusinessException {
        if (dtos == null || dtos.isEmpty()) {
            return dtos;
        }
        List<VoucherEnclosure> vos = new ArrayList<>();
        for (VoucherEnclosureDto dto : dtos) {
            checkCreate(dto);
            set2Create(dto);
            vos.add(dto.toVo(VoucherEnclosure.class));
        }
        int result = mapper.createBatch(vos);
        if (vos.size() != result) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_DB_OPERATE_FAIL,
                    ErrorCode.EXCEPTION_MESSAGE_DB_OPERATE_FAIL);
        }
        dtos = queryByIdList(dtos);
        return dtos;
    }

    private void checkCreate(VoucherEnclosureDto dto) {
        if (dto.getOrgId() == null || dto.getVoucherTypeId() == null || dto.getVoucherId() == null
                || dto.getEnclosureId() == null) {
            String message = ErrorCode.EXCEPTION_MESSAGE_ENCLOSURE_CREATE_FAIL;
            message = String.format(message, "企业 orgId 单据类型 voucherTypeId 单据 voucherId 附件 enclosure Id 不能为空");
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_ENCLOSURE_CREATE_FAIL, message);
        }
    }

    private void set2Create(VoucherEnclosureDto dto) {
        Long id = oidService.generateObjectID();
        dto.setId(id);
        dto.setCreateTime(new Date());
    }

    @Override
    @Transactional
    public VoucherEnclosureDto update(VoucherEnclosureDto dto) throws BusinessException {
        checkUpdate(dto);
        set2Update(dto);
        VoucherEnclosure vo = dto.toVo(VoucherEnclosure.class);
        int result = mapper.update(vo);
        if (1 != result) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_DB_OPERATE_FAIL,
                    ErrorCode.EXCEPTION_MESSAGE_DB_OPERATE_FAIL);
        }
        VoucherEnclosureDto current = queryById(dto);
        return current;
    }

    @Override
    @Transactional
    public List<VoucherEnclosureDto> updateBatch(List<VoucherEnclosureDto> dtos) throws BusinessException {
        if (dtos == null || dtos.isEmpty()) {
            return dtos;
        }
        List<VoucherEnclosure> vos = new ArrayList<>();
        for (VoucherEnclosureDto dto : dtos) {
            checkUpdate(dto);
            set2Update(dto);
            vos.add(dto.toVo(VoucherEnclosure.class));
        }
        mapper.updateBatch(vos);
        dtos = queryByIdList(dtos);
        return dtos;
    }

    private void checkUpdate(VoucherEnclosureDto dto) {
        if (dto.getOrgId() == null || dto.getId() == null || dto.getTs() == null) {
            String message = ErrorCode.EXCEPTION_MESSAGE_ENCLOSURE_UPDATE_FAIL;
            message = String.format(message, "企业 orgId 附件id 时间戳 ts 不能为空");
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_ENCLOSURE_UPDATE_FAIL, message);
        }
    }

    private void set2Update(VoucherEnclosureDto dto) {
        dto.setUpdateTime(new Date());
    }

    @Override
    @Transactional
    public VoucherEnclosureDto delete(VoucherEnclosureDto dto) throws BusinessException {
        checkDelete(dto);
        VoucherEnclosure vo = dto.toVo(VoucherEnclosure.class);
        int result = mapper.delete(vo);
        if (1 != result) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_DB_OPERATE_FAIL,
                    ErrorCode.EXCEPTION_MESSAGE_DB_OPERATE_FAIL);
        }
        return dto;
    }

    @Override
    @Transactional
    public List<VoucherEnclosureDto> deleteBatch(List<VoucherEnclosureDto> dtos) throws BusinessException {
        if (dtos == null || dtos.isEmpty()) {
            return dtos;
        }
        List<VoucherEnclosure> vos = new ArrayList<>();
        for (VoucherEnclosureDto dto : dtos) {
            checkDelete(dto);
            vos.add(dto.toVo(VoucherEnclosure.class));
        }
        int result = mapper.deleteBatch(vos);
        if (vos.size() != result) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_DB_OPERATE_FAIL,
                    ErrorCode.EXCEPTION_MESSAGE_DB_OPERATE_FAIL);
        }
        return dtos;
    }

    private void checkDelete(VoucherEnclosureDto dto) {
        if (dto.getOrgId() == null || dto.getId() == null || dto.getTs() == null) {
            String message = ErrorCode.EXCEPTION_MESSAGE_ENCLOSURE_DELETE_FAIL;
            message = String.format(message, "企业 orgId 附件id 时间戳 ts 不能为空");
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_ENCLOSURE_DELETE_FAIL, message);
        }
    }

    @Override
    @Transactional
    public int deleteByVoucherId(VoucherEnclosureDto dto) throws BusinessException {
        checkDeleteByVoucherId(dto);
        VoucherEnclosure vo = dto.toVo(VoucherEnclosure.class);
        int result = mapper.deleteByVoucherId(vo);
        return result;
    }

    @Override
    @Transactional
    public int deleteByVoucherIds(List<VoucherEnclosureDto> dtos) throws BusinessException {
        if (dtos == null || dtos.isEmpty()) {
            return 0;
        }
        List<VoucherEnclosure> vos = new ArrayList<>();
        for (VoucherEnclosureDto dto : dtos) {
            checkDeleteByVoucherId(dto);
            VoucherEnclosure vo = dto.toVo(VoucherEnclosure.class);
            vos.add(vo);
        }
        int result = mapper.deleteByVoucherIds(vos);
        return result;
    }

    private void checkDeleteByVoucherId(VoucherEnclosureDto dto) {
        if (dto.getOrgId() == null || dto.getVoucherTypeId() == null || dto.getVoucherId() == null) {
            String message = ErrorCode.EXCEPTION_MESSAGE_ENCLOSURE_DELETE_FAIL;
            message = String.format(message, "企业 orgId 单据类型 voucherTypeId 单据 voucherId 不能为空");
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_ENCLOSURE_DELETE_FAIL, message);
        }
    }

    @Override
    public VoucherEnclosureDto queryById(VoucherEnclosureDto dto) throws BusinessException {
        if (dto.getOrgId() == null || dto.getId() == null) {
            String message = ErrorCode.EXCEPTION_MESSAGE_ENCLOSURE_QUERY_FAIL;
            message = String.format(message, "企业 orgId 附件id 不能为空");
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_ENCLOSURE_QUERY_FAIL, message);
        }
        VoucherEnclosure vo = dto.toVo(VoucherEnclosure.class);
        List<VoucherEnclosureDto> list = mapper.query(vo);
        VoucherEnclosureDto result = null;
        if (!list.isEmpty()) {
            result = list.get(0);
        }
        return result;
    }

    @Override
    public List<VoucherEnclosureDto> queryByVoucherId(VoucherEnclosureDto dto) throws BusinessException {
        if (dto.getOrgId() == null || dto.getVoucherTypeId() == null || dto.getVoucherId() == null) {
            String message = ErrorCode.EXCEPTION_MESSAGE_ENCLOSURE_QUERY_FAIL;
            message = String.format(message, "企业 orgId 单据类型 voucherTypeId 单据 voucherId 不能为空");
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_ENCLOSURE_QUERY_FAIL, message);
        }
        VoucherEnclosure vo = dto.toVo(VoucherEnclosure.class);
        return mapper.query(vo);
    }

    private List<VoucherEnclosureDto> queryByIdList(List<VoucherEnclosureDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return dtos;
        }
        List<VoucherEnclosure> vos = new ArrayList<>();
        for (VoucherEnclosureDto dto : dtos) {
            if (dto.getOrgId() == null || dto.getId() == null) {
                String message = ErrorCode.EXCEPTION_MESSAGE_ENCLOSURE_QUERY_FAIL;
                message = String.format(message, "企业 orgId 附件id 不能为空");
                throw new BusinessException(ErrorCode.EXCEPTION_CODE_ENCLOSURE_QUERY_FAIL, message);
            }
            VoucherEnclosure vo = dto.toVo(VoucherEnclosure.class);
            vos.add(vo);
        }
        return mapper.queryByIdList(vos);
    }

}
