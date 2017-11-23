package com.mk.eap.entity.itf;

import java.util.List;

import com.mk.eap.base.BusinessException;
import com.mk.eap.base.DTO;

/**
 * 实体服务接口基类
 * <p>定义了基本的增、删、改、查方法
 * @author gaoxue
 * 
 * @param <D> dto 实体类型
 */
public interface IEntityService<D extends DTO> {

    /**
     * 新增实体，null 属性使用数据库默认值
     * @param dto 要新增的实体
     * @return 新增的实体
     * @throws NullPointerException 如果 {@code dto} 为 {@code null}
     * @throws BusinessException
     */
    D create(D dto) throws BusinessException;

    /**
     * 根据主键删除实体
     * @param dto 要删除的实体，需要传入主键对应的属性，如果实体有时间戳 {@code ts} 属性，也需要传入
     * @return 删除的实体
     * @throws NullPointerException 如果 {@code dto} 为 {@code null}
     * @throws BusinessException
     */
    D delete(D dto) throws BusinessException;

    /**
     * 根据主键修改实体
     * @param dto 要修改的实体，需要传入主键对应的属性，如果实体有时间戳 {@code ts} 属性，也需要传入，
     * 其他属性按照传入值进行更新，根据传入参数的 {@code nullUpdate} 属性决定 {@code null} 值是否更新
     * @return 修改后的实体
     * @throws NullPointerException 如果 {@code dto} 为 {@code null}
     * @throws BusinessException
     */
    D update(D dto) throws BusinessException;

    /**
     * 查询实体
     * @param dto 查询条件，根据实体中的属性值进行查询，查询条件使用等号
     * @return 查询结果列表
     * @throws NullPointerException 如果 {@code dto} 为 {@code null}
     * @throws BusinessException
     */
    List<D> query(D dto) throws BusinessException;

    /**
     * 按照主键查询实体，没有找到返回 {@code null}
     * @param dto 需要传入主键对应的属性
     * @return 查询到的实体信息
     * @throws NullPointerException 如果 {@code dto} 为 {@code null}
     * @throws BusinessException
     */
    D queryByPrimaryKey(D dto) throws BusinessException;
    
    /**
     * 按照主键查询实体，没有找到返回 {@code null}
     * @param key 需要传入主键对应的值
     * @return 查询到的实体信息
     * @throws NullPointerException 如果 {@code dto} 为 {@code null}
     * @throws BusinessException
     */
    D queryByPrimaryKey(Long key) throws BusinessException;

    /**
     * 查询实体
     * @param whereSql 查询条件
     * @return 查询结果列表
     * @throws NullPointerException 如果 {@code dto} 为 {@code null}
     * @throws BusinessException
     */
    @Deprecated
    List<D> queryByWhereSql(String whereSql) throws BusinessException;

    /**
     * 批量新增实体，null 的属性也会保存，不会使用数据库默认值
     * @param dtos 需要新增的实体
     * @return 新增的实体
     * @throws BusinessException
     */
    List<D> createBatch(List<D> dtos) throws BusinessException;
    
    /**
     * 批量删除实体，根据实体属性作为条件进行删除，查询条件使用等号
     * @param dtos 需要删除的实体
     * @return 删除的实体
     * @throws BusinessException
     */
    List<D> deleteBatch(List<D> dtos) throws BusinessException;

    /**
     * 按照主键批量删除实体，需要传入主键
     * @param dtos 需要删除的实体
     * @return 删除的实体
     * @throws BusinessException
     */
    List<D> deleteBatchByPrimaryKey(List<D> dtos) throws BusinessException;
     

}
