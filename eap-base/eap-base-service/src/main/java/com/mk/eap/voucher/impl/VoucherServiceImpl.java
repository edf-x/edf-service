package com.mk.eap.voucher.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.mk.eap.base.OperateStatus;
import com.mk.eap.component.common.oid.itf.IOidService;
import com.mk.eap.base.BusinessException;
import com.mk.eap.utils.StringUtil;
import com.mk.eap.validate.ValidateUtil;
import com.mk.eap.validate.group.Delete;
import com.mk.eap.validate.group.Update;
import com.mk.eap.voucher.dao.VoucherDetailMapper;
import com.mk.eap.voucher.dao.VoucherMapper;
import com.mk.eap.voucher.dto.VoucherDto;
import com.mk.eap.voucher.itf.IVoucherService;
import com.mk.eap.voucher.vo.VoucherDetail;
import com.mk.eap.validate.group.Create;
import com.mk.eap.entity.constant.ErrorCode;
import com.mk.eap.entity.impl.EntityServiceImpl;
import com.mk.eap.voucher.dto.VoucherDetailDto;
import com.mk.eap.voucher.vo.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 单据服务实现基类
 * @author gaoxue
 *
 * @param <D> 单据 dto 类型
 * @param <DD> 单据明细 dto 类型
 * @param <V> 单据 vo 类型
 * @param <DV> 单据明细 vo 类型
 * @param <M> 单据 mapper 类型
 */
public abstract class VoucherServiceImpl<D extends VoucherDto<DD>, DD extends VoucherDetailDto, V extends Voucher, DV extends VoucherDetail, M extends VoucherMapper<V>>
        extends EntityServiceImpl<D, V, M> implements IVoucherService<D, DD> {

    @Autowired
    protected VoucherDetailMapper<DV> detailMapper;

    protected IOidService oidService;

    protected Class<DD> detailDtoClazz;

    protected Class<DV> detailVoClazz;

    /** 明细数据是否允许为空 */
    protected boolean detailAllowNull = false;

    /** 单据类型 枚举 voucherType id */
    protected Long voucherTypeId;

    /** 单据编码前缀 */
    protected String codePrefix = "";

    /** 实体档案 id */
    protected Long dtoId;

    /** 是否有单据明细，默认 true */
    protected boolean hasDetail = true;

    @SuppressWarnings("unchecked")
    public VoucherServiceImpl() {
        super();
        Class<?> clazz = getClass();
        Type genType = clazz.getGenericSuperclass();
        while (!(genType instanceof ParameterizedType) && clazz != Object.class) {
            clazz = clazz.getSuperclass();
            genType = clazz.getGenericSuperclass();
        }
        if (genType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            dtoClazz = (Class<D>) params[0];
            detailDtoClazz = (Class<DD>) params[1];
            voClazz = (Class<V>) params[2];
            detailVoClazz = (Class<DV>) params[3];
        }
    }

    @Override
    protected void setDefaultValue4Create(D createDto) {
        super.setDefaultValue4Create(createDto);
        createDto.setId(oidService.generateObjectID());
        createDto.setCreateTime(new Date());
        for (DD detail : createDto.getDetails()) {
            detail.setId(oidService.generateObjectID());
            detail.setVoucherId(createDto.getId());
            detail.setOrgId(createDto.getOrgId());
            if (createDto.getUpdator() != null) {
                detail.setCreator(createDto.getUpdator());
                detail.setCreateTime(createDto.getUpdateTime());
            } else {
                detail.setCreator(createDto.getCreator());
                detail.setCreateTime(createDto.getCreateTime());
            }
        }
        createDto.setOperateStatus(OperateStatus.Created);
    }

    @Override
    protected void beforeCreate(D createDto) {
        super.beforeCreate(createDto);
        List<DD> details = createDto.getDetails();
        if (!detailAllowNull && details.isEmpty()) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_CREATE_FAIL, "单据明细数据不能为空");
        }
        if (voucherTypeId == null) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_CREATE_FAIL,
                    "单据类型 voucherTypeId 不能为空，请在各服务实现类的构造函数中设置此属性的值，或者预置单据 set_dto 数据");
        }
    }

    @Override
    protected D afterCreate(D createDto) {
        D newDto = super.afterCreate(createDto);
        checkCodeUnique(createDto);
        List<DD> details = createDto.getDetails();
        int orderNumber = 1;
        for (DD detail : details) {
            ValidateUtil.validate(detail, Create.class);
            detail.setOrderNumber(orderNumber);
            orderNumber++;
        }
        if (!details.isEmpty()) {
            int result = createDetails(details);
            if (result != details.size()) {
                throw ErrorCode.EXCEPTION_CONCURRENCY_OPERATE_FAIL;
            }
        }
        newDto = queryByPrimaryKey(createDto);
        return newDto;
    }

    private int createDetails(List<DD> detail2Create) {
        List<DV> detailVos = new ArrayList<>();
        for (DD detail : detail2Create) {
            ValidateUtil.validate(detail, Create.class);
            detailVos.add(detail.toVo(detailVoClazz));
        }
        int result = detailMapper.insertBatch(detailVos);
        return result;
    }

    @Override
    protected void beforeDelete(D deleteDto) {
        super.beforeDelete(deleteDto);
        deleteDto.setOperateStatus(OperateStatus.Deleted);
        innerDto = queryByPrimaryKey(innerDto);
    }

    @Override
    protected void afterDelete(D deleteDto) {
        super.afterDelete(deleteDto);

        // 处理明细数据
        int result = deleteDetailsByVoucher(innerDto.getOrgId(), innerDto.getId());
        if (result != innerDto.getDetails().size()) {
            throw ErrorCode.EXCEPTION_CONCURRENCY_OPERATE_FAIL;
        }
    }

    /**
     * 删除单据明细数据
     * @param orgId 组织 id
     * @param voucherId 单据 id
     * @return 删除的明细数量
     */
    protected int deleteDetailsByVoucher(long orgId, long voucherId) {
        if (!hasDetail) {
            return 0;
        }
        DV detailVo = getInstance(detailVoClazz);
        detailVo.setOrgId(orgId);
        detailVo.setVoucherId(voucherId);
        int result = detailMapper.delete(detailVo);
        return result;
    }

    @Override
    protected void setDefaultValue4Update(D updateDto) {
        super.setDefaultValue4Update(updateDto);
        for (DD detail : updateDto.getDetails()) {
            detail.setOrgId(updateDto.getOrgId());
            detail.setVoucherId(updateDto.getId());
        }
        updateDto.setOperateStatus(OperateStatus.Updated);
    }

    @Override
    protected void beforeUpdate(D updateDto) {
        super.beforeUpdate(updateDto);
        innerDto = queryByPrimaryKey(innerDto);
        updateDto.setUpdateTime(new Date());
        int orderNumber = 1;
        Set<Long> updateId = new HashSet<>();
        for (DD detail : updateDto.getDetails()) {
            Long id = detail.getId();
            OperateStatus status;
            if (id == null) {
                status = OperateStatus.Created;
                detail.setId(oidService.generateObjectID());
            } else {
                updateId.add(id);
                status = OperateStatus.Updated;
            }
            detail.setOperateStatus(status);
            if (detail.getCreator() == null) {
                detail.setCreator(innerDto.getCreator());
            }
            detail.setOrderNumber(orderNumber);
            orderNumber++;
        }
        List<DD> details = updateDto.getDetails();
        for (DD detail : innerDto.getDetails()) {
            Long id = detail.getId();
            if (!updateId.contains(id)) {
                detail.setOperateStatus(OperateStatus.Deleted);
                details.add(detail);
            }
        }
    }

    @Override
    protected D afterUpdate(D updateDto) {
        super.afterUpdate(updateDto);
        checkCodeUnique(updateDto);
        List<DD> detail2Create = new ArrayList<>();
        List<DD> detail2Update = new ArrayList<>();
        List<DD> detail2Delete = new ArrayList<>();
        for (DD detail : updateDto.getDetails()) {
            OperateStatus status = detail.getOperateStatus();
            if (status == OperateStatus.Deleted) {
                detail2Delete.add(detail);
            } else if (status == OperateStatus.Created) {
                detail2Create.add(detail);
            } else if (status == OperateStatus.Updated) {
                detail2Update.add(detail);
            }
        }
        int result;
        // delete
        if (!detail2Delete.isEmpty()) {
            List<DV> detailVos = new ArrayList<>();
            for (DD detail : detail2Delete) {
                ValidateUtil.validate(detail, Delete.class);
                DV detailVo = detail.toVo(detailVoClazz);
                detailVos.add(detailVo);
            }
            result = detailMapper.deleteBatchByPrimaryKey(detailVos);
            if (detail2Delete.size() != result) {
                throw ErrorCode.EXCEPTION_CONCURRENCY_OPERATE_FAIL;
            }
        }

        // update
        if (!detail2Update.isEmpty()) {
            for (DD detail : detail2Update) {
                DV detailVo = detail.toVo(detailVoClazz);
                if (detail.getNullUpdate()) {
                    ValidateUtil.validate(detail, Create.class);
                }
                ValidateUtil.validate(detail, Update.class);
                detailVo.setUpdateTime(updateDto.getUpdateTime());
                if (detail.getNullUpdate()) {
                    detailMapper.updateByPrimaryKey(detailVo);
                } else {
                    detailMapper.updateByPrimaryKeySelective(detailVo);
                }
            }
        }

        // create
        if (!detail2Create.isEmpty()) {
            result = createDetails(detail2Create);
            if (result != detail2Create.size()) {
                throw ErrorCode.EXCEPTION_CONCURRENCY_OPERATE_FAIL;
            }
        }
        return queryByPrimaryKey(updateDto);
    }

    @Override
    protected List<D> afterQuery(List<D> resultList) {
        resultList = super.afterQuery(resultList);
        if (hasDetail) {
            // 处理明细信息
            for (D item : resultList) {
                DV detailVo = getInstance(detailVoClazz);
                detailVo.setOrgId(item.getOrgId());
                detailVo.setVoucherId(item.getId());
                List<DD> details = getInstance(detailDtoClazz).fromVo(detailMapper.select(detailVo));
                item.setDetails(details);
            }
        }
        return resultList;
    }

    @Override
    protected D afterQueryByPrimaryKey(D queryDto) {
        queryDto = super.afterQueryByPrimaryKey(queryDto);
        if (hasDetail) {
            // 处理明细信息
            DV detailVo = getInstance(detailVoClazz);
            detailVo.setOrgId(queryDto.getOrgId());
            detailVo.setVoucherId(queryDto.getId());
            List<DD> details = getInstance(detailDtoClazz).fromVo(detailMapper.select(detailVo));
            queryDto.setDetails(details);
        }
        return queryDto;
    }

    @Override
    public D queryById(Long orgId, Long id) throws BusinessException {
        if (orgId == null || id == null) {
            throw new NullPointerException("企业 id、单据 id 不能为空");
        }
        D dto = getInstance(dtoClazz);
        dto.setOrgId(orgId);
        dto.setId(id);
        List<D> list = query(dto);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public D previous(D current) throws BusinessException {
        checkNull(current);
        ValidateUtil.validate(current);
        D result = null;
        String code = current.getCode();
        if (StringUtil.isEmtryStr(code)) {
            result = last(current);
        } else {
            Example example = new Example(voClazz, true, true);
            Criteria criteria = example.createCriteria();
            criteria.andLessThan("code", current.getCode());
            criteria.andEqualTo("orgId", current.getOrgId());
            example.setOrderByClause("code desc");
            handlePreviousNext(example, current);
            PageHelper.startPage(1, 1);
            List<V> resultList = mapper.selectByExample(example);
            if (!resultList.isEmpty()) {
                result = current.fromVo(resultList.get(0));
            }
        }
        if (result == null) {
            throw ErrorCode.EXCEPTION_PREVIOUS_NOT_EXIST;
        }
        return queryByPrimaryKey(result);
    }

    @Override
    public D next(D current) throws BusinessException {
        checkNull(current);
        ValidateUtil.validate(current);
        D result = null;
        String code = current.getCode();
        if (StringUtil.isEmtryStr(code)) {
            result = last(current);
        } else {
            Example example = new Example(voClazz, true, true);
            Criteria criteria = example.createCriteria();
            criteria.andGreaterThan("code", current.getCode());
            criteria.andEqualTo("orgId", current.getOrgId());
            example.setOrderByClause("code asc");
            handlePreviousNext(example, current);
            PageHelper.startPage(1, 1);
            List<V> resultList = mapper.selectByExample(example);
            if (!resultList.isEmpty()) {
                result = current.fromVo(resultList.get(0));
            }
        }
        if (result == null) {
            throw ErrorCode.EXCEPTION_NEXT_NOT_EXIST;
        }
        return queryByPrimaryKey(result);
    }

    private D last(D current) {
        Example example = new Example(voClazz, true, true);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orgId", current.getOrgId());
        example.setOrderByClause("code desc");
        handlePreviousNext(example, current);
        PageHelper.startPage(1, 1);
        List<V> result = mapper.selectByExample(example);
        if (result.isEmpty()) {
            return null;
        }
        return current.fromVo(result.get(0));
    }

    /**
     * 处理翻页查询时的过滤条件
     * @param example 过滤条件
     */
    protected void handlePreviousNext(Example example, D dto) {
        // 基类翻页查询只做了组织 id 的过滤，其他条件需要重写此方法进行处理
    }

    protected int getMaxSN(D dto) throws BusinessException {
        Example example = new Example(voClazz, true, true);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo(dto);
        String codePrefix = this.codePrefix;
        if (!StringUtil.isEmpty(dto.getCodePrefix())) {
            codePrefix = dto.getCodePrefix();
        }
        if (!StringUtil.isEmtryStr(codePrefix)) {
            criteria.andLike("code", codePrefix + "%");
        }
        example.setOrderByClause("code desc");
        PageHelper.startPage(1, 1);
        List<V> resultList = mapper.selectByExample(example);
        if (resultList.isEmpty()) {
            return 0;
        }
        String code = resultList.get(0).getCode();
        if (StringUtil.isEmtryStr(code) || code.length() < 4) {
            return 0;
        }
        code = code.substring(code.length() - 4);
        return Integer.parseInt(code);
    }

    private void checkCodeUnique(D dto) {
        // TODO 默认事务隔离级别 Read committed 是不允许脏读的，并发校验不到 mssql select with nolock
        if (StringUtil.isEmtryStr(dto.getCode())) {
            return;
        }
        Example example = new Example(voClazz, true, true);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orgId", dto.getOrgId());
        criteria.andEqualTo("code", dto.getCode());
        int count = mapper.selectCountByExample(example);
        if (count > 1) {
            throw ErrorCode.EXCEPTION_CONCURRENCY_OPERATE_FAIL;
        }
    }

    @Reference
    public void setOidService(IOidService oidService) {
        this.oidService = oidService;
    }

}
