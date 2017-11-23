package com.mk.eap.entity.dto;

import com.mk.eap.base.PageObject;
import com.mk.eap.base.DTO;

/**
 * 分页查询条件基类
 * @author gaoxue
 * 
 * @param <D> dto 实体类型
 */
public class PageQueryDto<D extends DTO> extends DTO {

    private static final long serialVersionUID = -8609194488567733278L;

    /** 分页条件 */
    private PageObject page;
    
    /** 简单实体条件 */
    private D entity;

    /**
     * 获取简单实体条件
     * @return 简单实体条件
     */
    public D getEntity() {
        return entity;
    }

    /**
     * 设置简单实体条件
     * @param entity 简单实体条件
     */
    public void setEntity(D entity) {
        this.entity = entity;
    }

    /**
     * 获取分页条件
     * @return 分页条件
     */
    public PageObject getPage() {
        return page;
    }

    /**
     * 设置分页条件
     * @param page 分页条件
     */
    public void setPage(PageObject page) {
        this.page = page;
    }

}
