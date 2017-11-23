package com.mk.eap.discovery.itf;

import java.util.ArrayList;

import com.mk.eap.discovery.dto.ClassDto;
 
public interface IDiscoveryService {

	/**
	 * 接口类型序列化
	 * @param className
	 * @return
	 */ 
	public ArrayList<ClassDto> interfaceSerializer(String[] interfaceNames)throws Exception; 
}
