package com.mk.eap.user.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.mk.eap.base.BusinessException;
import com.mk.eap.discovery.dto.Token;
import com.mk.eap.entity.impl.EntityServiceImpl;
import com.mk.eap.oid.IDGenerator;
import com.mk.eap.user.dao.UserMapper;
import com.mk.eap.user.dto.UserDto;
import com.mk.eap.user.dto.UserResetPasswordDto;
import com.mk.eap.user.itf.IUserService;
import com.mk.eap.user.vo.UserVo;

@Component
@Service
public class UserServiceImpl extends EntityServiceImpl<UserDto, UserVo, UserMapper> implements IUserService {

	@Override
	public UserDto login(UserDto user) {
		UserDto dto = new UserDto();
		dto.setMobile(user.getMobile());
		List<UserDto> dtos = this.query(dto);
		if (dtos == null || dtos.size() == 0) {
			throw new BusinessException("1000", "用户不存在");
		}
		if (!dtos.get(0).getPassword().equals(user.getPassword())) {
			throw new BusinessException("1000", "密码不正确");
		}
		Token token = new Token();
		token.setUserId(dtos.get(0).getId());
		user.setToken(token);
		return user;
	}

	@Override
	public Boolean logout(Token token) {
		return true;
	}

	@Override
	public Boolean existsMobile(String mobile) {
		UserDto dto = new UserDto();
		dto.setMobile(mobile);
		List<UserDto> dtos = this.query(dto);
		return dtos != null && dtos.size() > 0;
	}

	@Override
	public Boolean modifyPassword(UserDto user) {
		UserDto dto = this.findById(user.getId());
		if (dto.getPassword().equals(user.getOldPassword())) {
			dto.setPassword(user.getPassword());  
			this.update(dto);
		} else {
			throw new BusinessException("100", "密码不正确！");
		}
		return true;
	}

	@Override
	public UserDto findById(Long userId) {
		UserDto dto = new UserDto();
		dto.setId(userId);
		dto = this.queryByPrimaryKey(userId);
		return dto;
	}

	@Override
	public Boolean resetPassword(UserResetPasswordDto user) throws BusinessException {
		UserDto dto = new UserDto();
		dto.setMobile(user.getMobile());
		List<UserDto> dtos = this.query(dto);
		if (dtos == null || dtos.size() == 0) {
			throw new BusinessException("1000", "用户不存在！");
		}
		dtos.get(0).setPassword(user.getPassword());
		this.update(dtos.get(0));
		return true;
	}

	@Override
	public UserDto create(UserDto user) {
		Long id = Long.parseLong(IDGenerator.generateObjectID("eap-user"));
		user.setId(id);
		return super.create(user);
	}
}
