package com.mk.eap.entity.dto;

import java.util.List;

import com.mk.eap.base.PageObject;
import com.mk.eap.base.DTO;

/**
 * 分页查询结果基类
 * @author gaoxue
 * 
 * @param <D> dto 实体类型
 */
public class PageResultDto<D extends DTO> extends DTO {

    private static final long serialVersionUID = -5221287484153071450L;

    /** 查询结果列表 */
    private List<D> list;

    /** 分页结果 */
    private PageObject page;

    /**
     * 获取查询结果列表
     * @return 查询结果列表
     */
    public List<D> getList() {
        return list;
    }

    /**
     * 设置查询结果列表
     * @param list 查询结果列表
     */
    public void setList(List<D> list) {
        this.list = list;
    }

    /**
     * 获取分页结果
     * @return 分页结果
     */
    public PageObject getPage() {
        return page;
    }

    /**
     * 设置分页结果
     * @param page 分页结果
     */
    public void setPage(PageObject page) {
        this.page = page;
    }

}
