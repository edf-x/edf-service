package com.mk.eap.user.impl;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.mk.eap.base.BusinessException;
import com.mk.eap.user.itf.ICaptchaService;

@Component
@Service
public class CaptchaServiceImpl implements ICaptchaService {

	@Override
	public String fetch() {
		throw new BusinessException("100", "aaa");
	}

	@Override
	public Boolean validate(String captcha) {
		return true;
	}

}
