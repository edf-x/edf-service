package com.mk.eap.user.itf;

import org.springframework.web.bind.annotation.RequestMapping;

import com.mk.eap.base.BusinessException;

@RequestMapping("/captcha")
public interface ICaptchaService {

	@RequestMapping("/fetch")
	String fetch() throws BusinessException;

	@RequestMapping("/validate")
	Boolean validate(String captcha) throws BusinessException;
}
