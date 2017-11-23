package com.mk.eap.user.dao;

import java.util.List;
import java.util.Map;

import com.mk.eap.entity.dao.EntityMapper;
import com.mk.eap.user.dto.UserRoleDto;
import com.mk.eap.user.vo.UserRoleVo;


public interface UserRoleMapper extends EntityMapper<UserRoleVo>  {

	List<UserRoleDto> selectById(Map<String, Object> map);

	UserRoleDto deleteForUserId(Map<String, Object> map);

}
