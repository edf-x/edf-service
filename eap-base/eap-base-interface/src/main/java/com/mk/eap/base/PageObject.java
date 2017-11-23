/*      						
 * Copyright 2015 Beijing T-star, Inc. All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    			|  		Who  			|  		What  
 * 2015-01-12		| 	 lihaitao 			| 	create the file                       
 */
package com.mk.eap.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * 
 * 分页功能实体类
 * 
 * <p>
 * 	分页功能实体
 * </p> 
 * 
 * @author lihaitao
 * 
 */

public class PageObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 855778573334816193L;
	 
    /**
     * 定义常量分页偏移量
     */
    public static final String OFFSET = "offset";

    /**
     * 定义常量分页显示条数
     */
    public static final String PAGESIZE = "pageSize";
    

	private int pageSize = 10; 		// 每页显示记录数
	
	private int currentPage = 1;	// 当前页码
	
	private int totalPage = 1;		// 总页数
	
//	private int sumCloum = 0;		// 数据总数量
	
	@JsonIgnore
	private int offset = 0;			// 分页时需要偏移的数据总量(MySQL、PgSQL 等特殊数据库)

	private String orderBy;			//排序字段

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
    /**
     * 分页合理化参数，默认false
     * <p>当该参数设置为 true 时，方法 {@code getCurrentPage()} 在 currentPage < 1 时返回 1， currentPage > totalPage 时返回 totalPage
     */
    @JsonIgnore
    private Boolean reasonable = false;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if(pageSize < 1){
			this.pageSize = 1;
		}else{
			this.pageSize = pageSize;
		}
	}

    /**
     * 获取当前页码
     * <p>当参数 reasonable 设置为 true 时，currentPage < 1 返回 1， currentPage > totalPage 返回 totalPage
     * @return
     */
    public int getCurrentPage() {
        if (reasonable) {
            if (currentPage < 1) {
                return 1;
            } else if (currentPage > totalPage) {
                return totalPage;
            }
        }
        return currentPage;
    }

	public void setCurrentPage(int currentPage) {
		if(currentPage < 1){
			this.currentPage = 1;
		}else{
			this.currentPage = currentPage;
		}
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getSumCloum() {
		return totalPage;
	}

	public void setSumCloum(int sumCloum) {
		this.totalPage = sumCloum;
		if(sumCloum > 0) {
			int totalPageTmp = sumCloum/pageSize;
			this.setTotalPage(sumCloum % pageSize == 0 ? totalPageTmp : totalPageTmp + 1);
			this.getOffset();
		}
	}

	public int getOffset() {
		this.setOffset((getCurrentPage()-1)*pageSize);
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

    /**
     * 获取分页合理化参数，默认false
     * <p>当参数设置为 true 时，方法 {@code getCurrentPage()} 在 currentPage < 1 时返回 1， currentPage > totalPage 时返回 totalPage
     * @return reasonable 分页合理化参数
     */
    public Boolean getReasonable() {
        return reasonable;
    }

    /**
     * 设置分页合理化参数
     * <p>当参数设置为 true 时，方法 {@code getCurrentPage()} 在 currentPage < 1 时返回 1， currentPage > totalPage 时返回 totalPage
     * @param reasonable 分页合理化参数
     */
    public void setReasonable(Boolean reasonable) {
        this.reasonable = reasonable;
    }

}
