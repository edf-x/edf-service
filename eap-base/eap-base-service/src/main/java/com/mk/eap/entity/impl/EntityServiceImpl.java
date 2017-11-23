package com.mk.eap.entity.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mk.eap.base.VO;
import com.mk.eap.base.BusinessException;
import com.mk.eap.entity.constant.ErrorCode;
import com.mk.eap.entity.dao.EntityMapper;
import com.mk.eap.entity.dto.PageQueryDto;
import com.mk.eap.entity.dto.PageResultDto;
import com.mk.eap.entity.itf.IPageService;
import com.mk.eap.utils.StringUtil;
import com.mk.eap.validate.ValidateUtil;
import com.mk.eap.validate.group.Create;
import com.mk.eap.validate.group.Delete;
import com.mk.eap.validate.group.Update;
import com.mk.eap.entity.common.Const;
import com.mk.eap.base.PageObject;
import com.mk.eap.entity.itf.IEntityService;
import com.mk.eap.base.DTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 实体服务实现基类
 * <p>实现了实体服务基类接口  {@link IEntityService} 和分页服务基类接口 {@link IPageService}
 * @author gaoxue
 * 
 * @param <D> dto 实体类型
 * @param <V> vo 实体类型
 * @param <M> mapper 实体数据库访问接口类型
 */
public abstract class EntityServiceImpl<D extends DTO, V extends VO, M extends EntityMapper<V>>
        implements IEntityService<D>, IPageService<D, PageQueryDto<D>, PageResultDto<D>> {

    protected Logger logger;

    /** 泛型 D 的具体类型 */
    protected Class<D> dtoClazz;

    /** 泛型 V 的具体类型 */
    protected Class<V> voClazz;

    /** 操作的 dto 对象 */
    protected D innerDto;

    @Autowired
    protected M mapper;

    @SuppressWarnings("unchecked")
    public EntityServiceImpl() {
        Class<?> clazz = getClass();
        Type genType = clazz.getGenericSuperclass();
        while (!(genType instanceof ParameterizedType) && clazz != Object.class) {
            clazz = clazz.getSuperclass();
            genType = clazz.getGenericSuperclass();
        }
        if (genType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            dtoClazz = (Class<D>) params[0];
            voClazz = (Class<V>) params[1];
        }
        if (logger == null) {
            logger = LoggerFactory.getLogger(getClass());
        }
    }

    @PostConstruct
    protected void postConstruct() {
        // init method
    }

    @Override
    @Transactional
    public D create(D dto) throws BusinessException {
        this.innerDto = dto;
        checkNull(dto);
        setDefaultValue4Create(dto);
        ValidateUtil.validate(dto, Create.class);
        beforeCreate(dto);
        V vo = dto.toVo(voClazz);
        int result;
        try {
            result = mapper.insertSelective(vo);
        } catch (Exception ex) {
            String message = "新增失败，原因：" + ex.getMessage();
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_CREATE_FAIL, message, null, ex);
        }
        if (1 != result) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_CREATE_FAIL, "新增失败，数据库执行失败");
        }
        D newDto = afterCreate(dto);
        return newDto;
    }

    protected void setDefaultValue4Create(D createDto) {
        // 新增设置默认数据，在新增校验前执行
    }

    protected void beforeCreate(D createDto) {
        // 进行数据库新增前执行
    }

    protected D afterCreate(D createDto) {
        // 进行数据库新增后执行
        return createDto;
    }

    @Override
    @Transactional
    public D delete(D dto) throws BusinessException {
        checkNull(dto);
        ValidateUtil.validate(dto, Delete.class);
        D deleteDto = getInstance(dtoClazz);
        Set<EntityColumn> pkColumns = EntityHelper.getPKColumns(voClazz);
        for (EntityColumn pkColumn : pkColumns) {
            String propertyName = pkColumn.getProperty();
            deleteDto.setFieldValue(propertyName, dto.getFieldValue(propertyName));
        }
        this.innerDto = queryByPrimaryKey4Update(dto);
        checkConcurrency(this.innerDto, dto);

        beforeDelete(dto);
        if (dto.existTs()) {
            deleteDto.setFieldValue(DTO.TS_FILED_NAME, dto.getFieldValue(DTO.TS_FILED_NAME));
        }
        V deleteVo = deleteDto.toVo(voClazz);
        int result;
        try {
            result = mapper.delete(deleteVo);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_DELETE_FAIL, "删除失败，原因：" + ex.getMessage(), null, ex);
        }
        if (1 != result) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_DELETE_FAIL, "删除失败，请检查 vo 实体的主键配置");
        }
        afterDelete(dto);
        return dto;
    }

    protected void checkConcurrency(D oldDto, D newDto) {
        if (oldDto == null) {
            throw ErrorCode.EXCEPTION_CONCURRENCY_OPERATE_FAIL;
        }
        if (oldDto.existTs()) {
            if (!oldDto.getFieldValue(DTO.TS_FILED_NAME).equals(newDto.getFieldValue(DTO.TS_FILED_NAME))) {
                throw ErrorCode.EXCEPTION_CONCURRENCY_OPERATE_FAIL;
            }
        }
    }

    protected void beforeDelete(D deleteDto) {
        // 进行数据库删除前执行
    }

    protected void afterDelete(D deleteDto) {
        // 进行数据库删除后执行
    }

    @Override
    @Transactional
    public D update(D dto) throws BusinessException {
        checkNull(dto);
        setDefaultValue4Update(dto);
        ValidateUtil.validate(dto, Update.class);
        this.innerDto = queryByPrimaryKey4Update(dto);
        checkConcurrency(this.innerDto, dto);
        beforeUpdate(dto);
        Example example = getPrimaryKeyExample(dto, true);
        if (dto.getNullUpdate()) {
            ValidateUtil.validate(dto, Create.class);
        }
        V vo = dto.toVo(voClazz);
        int result;
        try {
            if (dto.getNullUpdate()) {
                result = mapper.updateByExample(vo, example);
            } else {
                result = mapper.updateByExampleSelective(vo, example);
            }
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_UPDATE_FAIL, "更新失败，原因：" + ex.getMessage(), null, ex);
        }
        if (1 != result) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_UPDATE_FAIL, "更新失败，请检查 vo 实体的主键配置");
        }
        D newDto = afterUpdate(dto);
        return newDto;
    }

    protected void setDefaultValue4Update(D updateDto) {

    }

    protected Example getPrimaryKeyExample(D dto, boolean withTs) {
        Set<EntityColumn> pkColumns = EntityHelper.getPKColumns(voClazz);
        Example example = new Example(voClazz, true, true);
        Criteria criteria = example.createCriteria();
        for (EntityColumn pkColumn : pkColumns) {
            String propertyName = pkColumn.getProperty();
            criteria.andEqualTo(propertyName, dto.getFieldValue(propertyName));
        }
        if (withTs && dto.existTs()) {
            criteria.andEqualTo(DTO.TS_FILED_NAME, dto.getFieldValue(DTO.TS_FILED_NAME));
        }
        return example;
    }

    protected void beforeUpdate(D updateDto) {
        // 进行数据库更新前操作
    }

    protected D afterUpdate(D updateDto) {
        // 进行数据库更新后操作
        return updateDto;
    }

    @Override
    public List<D> query(D dto) throws BusinessException {
        checkNull(dto);
        ValidateUtil.validate(dto);
        V vo = dto.toVo(voClazz);
        List<V> result;
        try {
            result = mapper.select(vo);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_QUERY_FAIL, "查询失败，原因：" + ex.getMessage(), null, ex);
        }
        List<D> resultList = dto.fromVo(result);
        resultList = afterQuery(resultList);
        return resultList;
    }

    protected List<D> afterQuery(List<D> resultList) {
        // 按照主键查询实体后执行
        return resultList;
    }

    @Override
    public D queryByPrimaryKey(Long key) throws BusinessException {
        V result;
        try {
            result = mapper.selectByPrimaryKey(key);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_QUERY_FAIL, "查询失败，原因：" + ex.getMessage(), null, ex);
        }
        D queryDto = getInstance(dtoClazz);
        queryDto = queryDto.fromVo(result);
        queryDto = afterQueryByPrimaryKey(queryDto);
        return queryDto;
    }

    @Override
    public D queryByPrimaryKey(D dto) throws BusinessException {
        checkNull(dto);
        // TODO ValidateUtil.validate(dto, Update.class);
        V vo = dto.toVo(voClazz);
        V result;
        try {
            result = mapper.selectByPrimaryKey(vo);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_QUERY_FAIL, "查询失败，原因：" + ex.getMessage(), null, ex);
        }
        D queryDto = dto.fromVo(result);
        queryDto = afterQueryByPrimaryKey(queryDto);
        return queryDto;
    }

    protected D afterQueryByPrimaryKey(D queryDto) {
        // 按照主键查询实体后执行
        return queryDto;
    }

    @Override
    @Deprecated
    public List<D> queryByWhereSql(String whereSql) throws BusinessException {
        // 存在 sql 注入的情况
        // "id = 1); delete from xxx; --"
        checkNull(whereSql);
        Example example = new Example(voClazz, true, true);
        Criteria criteria = example.createCriteria();
        criteria.andCondition(whereSql);
        // example.isForUpdate();
        List<V> result;
        try {
            result = mapper.selectByExample(example);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_QUERY_FAIL, "查询失败，原因：" + ex.getMessage(), null, ex);
        }
        return getInstance(dtoClazz).fromVo(result);
    }

    @Override
    public PageResultDto<D> queryPageList(PageQueryDto<D> queryDto) throws BusinessException {
        // TODO ValidateUtil.validate(queryDto, groups);
        checkNull(queryDto);
        PageObject page = queryDto.getPage();
        if (page == null) {
            page = new PageObject();
            queryDto.setPage(page);
        }
        D dto = queryDto.getEntity();
        V vo = dto.toVo(voClazz);
        PageHelper.startPage(page);
        List<V> resultList = mapper.select(vo);
        page.setSumCloum((int) ((Page<V>) resultList).getTotal());
        PageResultDto<D> pageResult = new PageResultDto<>();
        pageResult.setPage(page);
        List<D> dtoList = dto.fromVo(resultList);
        pageResult.setList(dtoList);
        return pageResult;
    }

    /**
     * 检查对象是否非空
     * @param obj 检查的对象
     * @throws NullPointerException 如果 {@code obj} 为 {@code null}
     */
    protected void checkNull(Object obj) throws NullPointerException {
        checkNull(obj, "");
    }

    /**
     * 检查对象是否非空
     * @param obj 检查的对象
     * @param message 异常提示信息
     * @throws NullPointerException 如果 {@code obj} 为 {@code null}
     */
    protected void checkNull(Object obj, String message) throws NullPointerException {
        if (StringUtil.isEmtryStr(message)) {
            message = "参数不能为空";
        }
        if (obj == null) {
            throw new NullPointerException(message);
        }
    }

    protected <T> T getInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new BusinessException("", "", null, ex);
        }
    }

    /**
     * 按照主键查询实体，查询 sql 增加排他锁，没有找到返回 null
     * @param dto 需要传入主键对应的属性
     * @return 查询到的实体信息
     */
    protected D queryByPrimaryKey4Update(D dto) {
        Example example = getPrimaryKeyExample(dto, false);
        example.isForUpdate();
        List<V> resultList;
        try {
            resultList = mapper.selectByExample(example);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_QUERY_FAIL, "查询失败，原因：" + ex.getMessage(), null, ex);
        }
        if (resultList.isEmpty()) {
            return null;
        }
        return dto.fromVo(resultList.get(0));
    }

    @Override
    @Transactional
    public List<D> createBatch(List<D> dtos) throws BusinessException {
        if (dtos == null || dtos.isEmpty()) {
            return dtos;
        }
        List<V> vos = new ArrayList<>();
        for (D dto : dtos) {
            checkNull(dto);
            setDefaultValue4Create(dto);
            ValidateUtil.validate(dto, Create.class);
            beforeCreate(dto);
            V vo = dto.toVo(voClazz);
            vos.add(vo);
        }
        beforeCreateBatch(dtos);
        int result = 0;
        try {
            for (int fromIndex = 0, size = dtos.size(); fromIndex < size;) {
                int toIndex = fromIndex + Const.DB_BATCH_OP_MAX_COUNT;
                if (toIndex > size) {
                    toIndex = size;
                }
                result += mapper.insertBatch(vos.subList(fromIndex, toIndex));
                fromIndex = toIndex;
            }
        } catch (Exception ex) {
            String message = "批量新增失败，原因：" + ex.getMessage();
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_CREATE_FAIL, message, null, ex);
        }
        if (result != dtos.size()) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_CREATE_FAIL, "批量新增失败，原因：没有全部新增成功");
        }
        List<D> newDtos = afterCreateBatch(dtos);
        return newDtos;
    }

    protected void beforeCreateBatch(List<D> createDtos) {
        // 进行数据库新增前执行
    }

    protected List<D> afterCreateBatch(List<D> createDtos) {
        // 进行数据库新增后执行
        return createDtos;
    }

    @Override
    @Transactional
    public List<D> deleteBatch(List<D> dtos) throws BusinessException {
        if (dtos == null || dtos.isEmpty()) {
            return dtos;
        }
        List<V> vos = new ArrayList<>();
        for (D dto : dtos) {
            checkNull(dto);
            ValidateUtil.validate(dto, Delete.class);
            beforeDelete(dto);
            V vo = dto.toVo(voClazz);
            vos.add(vo);
        }
        int result = 0;
        for (int fromIndex = 0, size = dtos.size(); fromIndex < size;) {
            int toIndex = fromIndex + Const.DB_BATCH_OP_MAX_COUNT;
            if (toIndex > size) {
                toIndex = size;
            }
            result += mapper.deleteBatch(vos.subList(fromIndex, toIndex));
            fromIndex = toIndex;
        }
        if (result != dtos.size()) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_DELETE_FAIL, "批量删除失败");
        }
        return dtos;
    }

    @Override
    @Transactional
    public List<D> deleteBatchByPrimaryKey(List<D> dtos) throws BusinessException {
        if (dtos == null || dtos.isEmpty()) {
            return dtos;
        }
        List<V> vos = new ArrayList<>();
        for (D dto : dtos) {
            checkNull(dto);
            ValidateUtil.validate(dto, Delete.class);
            beforeDelete(dto);
            V vo = dto.toVo(voClazz);
            vos.add(vo);
        }
        int result = 0;
        for (int fromIndex = 0, size = dtos.size(); fromIndex < size;) {
            int toIndex = fromIndex + Const.DB_BATCH_OP_MAX_COUNT;
            if (toIndex > size) {
                toIndex = size;
            }
            result += mapper.deleteBatchByPrimaryKey(vos.subList(fromIndex, toIndex));
            fromIndex = toIndex;
        }
        if (result != dtos.size()) {
            throw new BusinessException(ErrorCode.EXCEPTION_CODE_DELETE_FAIL, "批量删除失败");
        }
        return dtos;
    }

}
