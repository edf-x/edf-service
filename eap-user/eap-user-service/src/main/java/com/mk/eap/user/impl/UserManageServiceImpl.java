package com.mk.eap.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.mk.eap.entity.impl.EntityServiceImpl;
import com.mk.eap.user.dao.UserMapper;
import com.mk.eap.user.dto.UserDto;
import com.mk.eap.user.dto.UserRoleDto;
import com.mk.eap.user.itf.IUserManageService;
import com.mk.eap.user.itf.IUserRoleService;
import com.mk.eap.user.itf.IUserService;
import com.mk.eap.user.vo.UserVo;

@Component
@Service
public class UserManageServiceImpl extends EntityServiceImpl<UserDto, UserVo, UserMapper> implements IUserManageService {
	
	@Autowired
	IUserRoleService userRoleService;
	
	@Autowired
	IUserService userService;
	
	@Override
	public UserDto findById(Long id) {
		UserDto userDto = super.queryByPrimaryKey(id);
		if (userDto == null)
			return userDto;
		UserRoleDto userRoleDto = new UserRoleDto();
		userRoleDto.setUserId(userDto.getId());
		List<UserRoleDto> userRoleList = userRoleService.query(userRoleDto);
		userDto.setUserRoles(userRoleList);
		return userDto;
	}
	
	@Override
	public UserDto create(UserDto user) {
		userRoleService.save(user.getUserRoles());
		return userService.create(user);
	}
	
	@Override
	public UserDto update(UserDto user) {
		userRoleService.save(user.getUserRoles());
		return userService.update(user);
	}
	
	public void batchDelete(List<Long> ids)
	{
		for (Long id : ids)
		{
			UserDto userDto = new UserDto();
			userDto.setId(id);
			userService.delete(userDto);
		}
	}
}
