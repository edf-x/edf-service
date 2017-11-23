package com.mk.eap.base;


import java.io.Serializable;
import java.util.List;


/**
* @ClassName: PageInfoDTO
* @Description: 分页返回对象
* @author zhangbaosi
* @date 2016年7月25日 下午3:56:03
* @fileName com.rrtimes.acm.util.PageInfoDTO.java
*/

public class PageInfoDTO implements Serializable {

    /**
     * serialVersionUID:(用一句话描述这个变量表示什么).
     * 
     * @since JDK 1.6
     */
    private static final long serialVersionUID = 1L;

    private Integer results;

    private List rows;



	public Integer getResults() {
		return results;
	}

	public void setResults(Integer results) {
		this.results = results;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}


}
