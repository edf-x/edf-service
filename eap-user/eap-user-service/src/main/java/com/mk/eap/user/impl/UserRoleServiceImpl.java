package com.mk.eap.user.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.mk.eap.entity.impl.EntityServiceImpl;
import com.mk.eap.oid.IDGenerator;
import com.mk.eap.user.dao.UserRoleMapper;
import com.mk.eap.user.dto.UserRoleDto;
import com.mk.eap.user.itf.IUserRoleService;
import com.mk.eap.user.itf.IUserService;
import com.mk.eap.user.vo.UserRoleVo;

@Component
@Service
public class UserRoleServiceImpl extends EntityServiceImpl<UserRoleDto, UserRoleVo, UserRoleMapper> implements IUserRoleService {

	@Autowired
	IUserService userService;	
	
	@Autowired
    UserRoleMapper userRoleMapper;
	
	@Override
	public UserRoleDto create(UserRoleDto userrole) {
		Long id = Long.parseLong(IDGenerator.generateObjectID("eap-userrole"));
		userrole.setId(id);
		return super.create(userrole);
	}
	
	public List<UserRoleDto> findById(Long userId) {
		UserRoleDto dto = new UserRoleDto();
		dto.setId(userId);
		List<UserRoleDto> list = this.query(dto);
		return list;
	}
	
	@Override
	public List<UserRoleDto> save(List<UserRoleDto> userRoles){
		List<UserRoleDto> newUserRoles = new ArrayList<>();
		
		if (userRoles.size() > 0)
		{
			UserRoleDto userRoleFirstDto = userRoles.get(0);
			this.deleteForUserId(userRoleFirstDto.getUserId());	
			
			for(UserRoleDto userRoleDto: userRoles) {
				UserRoleDto newuserRoleDto = new UserRoleDto();
						
				if (!this.isExists(userRoleDto.getUserId(), userRoleDto.getRoleId())) {
					newuserRoleDto = this.create(userRoleDto);
				}
				else {
					newuserRoleDto = this.update(userRoleDto);
				}
				newUserRoles.add(newuserRoleDto);
			}
		}
		return newUserRoles;
	}
	
	private void deleteForUserId(Long userId) {
		Map<String,Object> map = new HashMap<>();
		map.put("userId", userId);	
		userRoleMapper.deleteForUserId(map);	
	}

	private boolean isExists(long userId, long roleId) {
		Map<String,Object> map = new HashMap<>();
		map.put("userId", userId);	
		map.put("roleId", roleId);
		List<UserRoleDto> list = userRoleMapper.selectById(map);
		if (list.isEmpty())
			return false;
		return true;
	}
}
