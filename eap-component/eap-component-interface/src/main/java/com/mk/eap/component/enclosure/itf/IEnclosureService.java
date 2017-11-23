package com.mk.eap.component.enclosure.itf;

import com.mk.eap.base.BusinessException;
import com.mk.eap.enclosure.vo.Enclosure;

import java.util.List;

public interface IEnclosureService {

	/**
	 * 查询附件列表
	 * @param orgId
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 */
	public List<Enclosure> findSetEnclosure(Long orgId) throws BusinessException,Exception;

	/**
	 * 新增附件
	 * @param setEnclosure
	 * @throws BusinessException
	 */
	public void insert(Enclosure setEnclosure) throws BusinessException;

	/**
	 * 查询新增的附件 
	 * @param setEnclosureData
	 * @return
	 * @throws BusinessException
	 */
	public Enclosure findSetEnclosureById(Enclosure setEnclosureData) throws BusinessException;
}
