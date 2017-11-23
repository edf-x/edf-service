package com.mk.eap.portal.itf;

import java.util.ArrayList;

import com.mk.eap.discovery.dto.ClassDto;
import com.mk.eap.discovery.itf.IDiscoveryService;
 
public interface IPortalDiscoveryService extends IDiscoveryService { 
 
	public ArrayList<ClassDto> interfaceSerializer(String[] interfaceNames)throws Exception; 
}
