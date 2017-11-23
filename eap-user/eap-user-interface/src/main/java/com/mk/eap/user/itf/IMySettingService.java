package com.mk.eap.user.itf;

import org.springframework.web.bind.annotation.RequestMapping;

import com.mk.eap.base.BusinessException;
import com.mk.eap.discovery.annotation.ApiContext;
import com.mk.eap.user.dto.MySettingDto;

@RequestMapping("/mySetting")
public interface IMySettingService {

	@RequestMapping("/init")
	@ApiContext("userId")
	MySettingDto init(Long userId)throws BusinessException;
}
