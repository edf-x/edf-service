package com.mk.eap.discovery.impl;

import java.util.ArrayList;

import com.mk.eap.discovery.dto.ClassDto;
import com.mk.eap.discovery.itf.IDiscoveryService;

public class DiscoveryServiceImpl implements IDiscoveryService {

	@Override
	public ArrayList<ClassDto> interfaceSerializer(String[] interfaceNames) throws Exception {
		return InterfaceSerializer.serialize(interfaceNames);
	}
}
