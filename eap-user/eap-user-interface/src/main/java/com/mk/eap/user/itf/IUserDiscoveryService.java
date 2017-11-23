package com.mk.eap.user.itf;

import java.util.ArrayList;

import com.mk.eap.discovery.dto.ClassDto;
import com.mk.eap.discovery.itf.IDiscoveryService;

public interface IUserDiscoveryService extends IDiscoveryService { 
	
	public ArrayList<ClassDto> interfaceSerializer(String[] interfaceNames)throws Exception; 
}
