package com.mk.eap.portal.dao;

import java.util.List;
import java.util.Map;

import com.mk.eap.entity.dao.EntityMapper;
import com.mk.eap.portal.dto.MenuOperationDto;
import com.mk.eap.portal.vo.MenuOperationVo;

public interface MenuOperationMapper extends EntityMapper<MenuOperationVo>  {

	List<MenuOperationDto> findByUserId(Map<String, Object> map);

	List<MenuOperationDto> selectById(Map<String, Object> map);

}