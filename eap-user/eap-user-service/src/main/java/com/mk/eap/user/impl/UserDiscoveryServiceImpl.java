package com.mk.eap.user.impl;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.mk.eap.discovery.impl.DiscoveryServiceImpl;
import com.mk.eap.user.itf.IUserDiscoveryService;

@Component
@Service
public class UserDiscoveryServiceImpl extends DiscoveryServiceImpl implements IUserDiscoveryService {

}
