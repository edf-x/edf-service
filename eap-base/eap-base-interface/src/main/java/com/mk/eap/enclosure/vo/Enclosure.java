package com.mk.eap.enclosure.vo;

import com.mk.eap.base.VO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;

/** 附件文件信息存储表 */
@Table(name = "set_enclosure")
public class Enclosure extends VO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;//组织ID
	@Id
	private Long orgId;//客户组织机构id\n[set_org]
	private Long elType;//附件类型: 1、图片；2、doc；3、pdf；4、excel
	private String elSize;//默认为0，附件大小（单位: kb）
	private String oldName;//上传前文件名
	private String newName;//上传后文件名
	private String elSuffix;//文件后缀（如: .jpg）
	private String elMd5;//文件 MD5 签名信息（预留字段，目前暂不处理）
	@Column(insertable = false, updatable = false)
	private Timestamp ts;//时间戳
	@Transient
	private Long TSLong;//Long型毫秒时间戳
	private Long creator;//上传人
	
	public Long getCreator() {
		return creator;
	}
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	public Long getTSLong() {
		return TSLong;
	}
	public void setTSLong(Long tSLong) {
		TSLong = tSLong;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getElType() {
		return elType;
	}
	public void setElType(Long elType) {
		this.elType = elType;
	}
	public String getElSize() {
		return elSize;
	}
	public void setElSize(String elSize) {
		this.elSize = elSize;
	}
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	public String getElSuffix() {
		return elSuffix;
	}
	public void setElSuffix(String elSuffix) {
		this.elSuffix = elSuffix;
	}
	public String getElMd5() {
		return elMd5;
	}
	public void setElMd5(String elMd5) {
		this.elMd5 = elMd5;
	}
	public Timestamp getTs() {
		return ts;
	}
	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

}
