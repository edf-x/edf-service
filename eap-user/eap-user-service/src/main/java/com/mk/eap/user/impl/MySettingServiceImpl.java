package com.mk.eap.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.mk.eap.user.dto.MySettingDto;
import com.mk.eap.user.dto.UserDto;
import com.mk.eap.user.itf.IMySettingService;
import com.mk.eap.user.itf.IUserService;

@Component
@Service
public class MySettingServiceImpl implements IMySettingService {

	@Autowired
	IUserService userService;
	
	@Override
	public MySettingDto init(Long userId) {
		MySettingDto dto = new MySettingDto();
		UserDto user = userService.findById(userId);
		dto.setUser(user);
		return dto;
	}

}
