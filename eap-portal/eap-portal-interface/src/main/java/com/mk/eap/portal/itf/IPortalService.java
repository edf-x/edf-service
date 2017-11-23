package com.mk.eap.portal.itf;

import org.springframework.web.bind.annotation.RequestMapping;

import com.mk.eap.base.BusinessException;
import com.mk.eap.discovery.annotation.ApiContext;
import com.mk.eap.discovery.dto.Token;
import com.mk.eap.portal.dto.PortalDto;

@RequestMapping("/portal")
public interface IPortalService {

	@RequestMapping("/init")
	PortalDto init(@ApiContext("token") Token token)throws BusinessException;
}
