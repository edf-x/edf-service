package com.mk.eap.component.enclosure.dao;

import com.mk.eap.enclosure.vo.Enclosure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetEnclosureMapper {

	/**
	 * 添加附件表
	 * @param setEnclosure
	 * @return
	 */
	public int insert(Enclosure setEnclosure);

	/**
	 * 查询附件表数据
	 * @param setEnclosureData
	 * @return
	 */
	public Enclosure findSetEnclosureById(Enclosure setEnclosureData);

	/**
	 * 根据组织ID查询附件列表
	 * @param setEnclosureData
	 * @return
	 */
	public List<Enclosure> findSetEnclosure(Enclosure setEnclosureData);

}