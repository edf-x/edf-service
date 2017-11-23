package com.mk.eap.portal.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.dubbo.config.annotation.Service;
import com.mk.eap.entity.impl.EntityCompositionInjector;
import com.mk.eap.entity.impl.EntityServiceImpl;
import com.mk.eap.entity.itf.IEntityInjector;
import com.mk.eap.oid.IDGenerator;
import com.mk.eap.portal.dao.EnumMapper;
import com.mk.eap.portal.dto.EnumDetailDto;
import com.mk.eap.portal.dto.EnumDto;
import com.mk.eap.portal.itf.IEnumDetailService;
import com.mk.eap.portal.itf.IEnumService;
import com.mk.eap.portal.vo.EnumVo;

@Component
@Service
public class EnumServiceImpl extends EntityServiceImpl<EnumDto, EnumVo, EnumMapper> implements IEnumService {

	@Autowired
	IEnumDetailService enumDetailService;
	
	@Override
	public EnumDto create(EnumDto enu) {
		Long id = Long.parseLong(IDGenerator.generateObjectID("eap-enum"));
		enu.setId(id);
		return super.create(enu);
	}
	
	List<IEntityInjector<EnumDto>> injectors;
	protected List<IEntityInjector<EnumDto>> getInjectors() {
		if (injectors == null) {
			injectors = new ArrayList<IEntityInjector<EnumDto>>();
			String className = EnumDetailDto.class.getName();
			IEntityInjector<EnumDto> enumDetailsInjector = new EntityCompositionInjector<EnumDto, EnumDetailDto>(
					"enumDetails", "enumId", enumDetailService, className);
			injectors.add(enumDetailsInjector);
		}
		return injectors;
	}
}
