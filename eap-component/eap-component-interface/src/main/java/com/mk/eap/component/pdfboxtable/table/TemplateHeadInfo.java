package com.mk.eap.component.pdfboxtable.table;

import java.io.Serializable;

public class TemplateHeadInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6136541436058976088L;

	/**
	 * 核算单位
	 */
	private String orgName;
	/**
	 * 日期
	 */
	private String date;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * 模板类型的表头信息
	 * @param orgName 核算单位
	 * @param dateInfo  日期信息
	 */
	public TemplateHeadInfo(String orgName, String date) {
		super();
		this.orgName = orgName;
		this.date = date;
	}
	
	

}
