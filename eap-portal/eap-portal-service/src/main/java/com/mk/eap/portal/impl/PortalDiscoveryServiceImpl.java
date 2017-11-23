package com.mk.eap.portal.impl;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.mk.eap.discovery.impl.DiscoveryServiceImpl;
import com.mk.eap.portal.itf.IPortalDiscoveryService;

@Component
@Service
public class PortalDiscoveryServiceImpl extends DiscoveryServiceImpl implements IPortalDiscoveryService {

}
