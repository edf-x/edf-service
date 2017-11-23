package com.mk.eap.portal.dao;

import java.util.List;
import java.util.Map;

import com.mk.eap.entity.dao.EntityMapper;
import com.mk.eap.portal.dto.MenuDto;
import com.mk.eap.portal.vo.MenuVo;

public interface MenuMapper extends EntityMapper<MenuVo>  {

	List<MenuDto> findByUserId(Map<String, Object> map);

}
