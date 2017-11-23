package com.mk.eap.component.enclosure.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.mk.eap.base.BusinessException;
import com.mk.eap.component.enclosure.itf.IEnclosureService;
import com.mk.eap.enclosure.vo.Enclosure;
import com.mk.eap.component.enclosure.dao.SetEnclosureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component @Service
public class SetEnclosureServiceImpl implements IEnclosureService {
	@Autowired
	private SetEnclosureMapper setEnclosureMapper;
	
	static final Logger logger = LoggerFactory.getLogger(SetEnclosureServiceImpl.class);

	@Override
	public List<Enclosure> findSetEnclosure(Long orgId) throws BusinessException, Exception {
	    Enclosure setEnclosureData = new Enclosure();
		setEnclosureData.setOrgId(orgId);
		List<Enclosure> ls = setEnclosureMapper.findSetEnclosure(setEnclosureData);
		return ls;
	}

	@Override
	public void insert(Enclosure setEnclosure) throws BusinessException {
		setEnclosureMapper.insert(setEnclosure);
		
	}

	@Override
	public Enclosure findSetEnclosureById(Enclosure setEnclosureData) throws BusinessException {
	    Enclosure setEnclosure = setEnclosureMapper.findSetEnclosureById(setEnclosureData);
		return setEnclosure;
	}
	
}
