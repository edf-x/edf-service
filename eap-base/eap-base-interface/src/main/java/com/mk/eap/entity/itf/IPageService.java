package com.mk.eap.entity.itf;

import com.mk.eap.entity.dto.PageQueryDto;
import com.mk.eap.entity.dto.PageResultDto;
import com.mk.eap.base.BusinessException;
import com.mk.eap.base.DTO;

/**
 * 分页服务接口基类
 * <p>定义了基本的分页查询方法
 * @author gaoxue
 * 
 * @param <D> dto 实体类型
 * @param <Q> 实体分页查询条件对象类型
 * @param <R> 实体分页查询结果对象类型
 */
public interface IPageService<D extends DTO, Q extends PageQueryDto<D>, R extends PageResultDto<D>> {

    /**
     * 分页查询实体
     * @param queryDto 分页查询条件
     * @return 分页查询结果
     * @throws NullPointerException 如果 {@code queryDto} 为 {@code null}
     * @throws BusinessException 
     */
    R queryPageList(Q queryDto) throws BusinessException;

}
