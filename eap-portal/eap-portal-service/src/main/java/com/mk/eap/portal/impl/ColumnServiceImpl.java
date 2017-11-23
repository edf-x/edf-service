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
import com.mk.eap.portal.dao.ColumnMapper;
import com.mk.eap.portal.dto.ColumnDetailDto;
import com.mk.eap.portal.dto.ColumnDto;
import com.mk.eap.portal.itf.IColumnDetailService;
import com.mk.eap.portal.itf.IColumnService;
import com.mk.eap.portal.vo.ColumnVo;

@Component
@Service
public class ColumnServiceImpl extends EntityServiceImpl<ColumnDto, ColumnVo, ColumnMapper> implements IColumnService {

	@Autowired
	IColumnDetailService columnDetailService;
	
	@Override
	public ColumnDto create(ColumnDto column) {
		Long id = Long.parseLong(IDGenerator.generateObjectID("eap-column"));
		column.setId(id);
		return super.create(column);
	}
	
	List<IEntityInjector<ColumnDto>> injectors;
	protected List<IEntityInjector<ColumnDto>> getInjectors() {
		if (injectors == null) {
			injectors = new ArrayList<IEntityInjector<ColumnDto>>();
			String className = ColumnDetailDto.class.getName();
			IEntityInjector<ColumnDto> columnDetailsInjector = new EntityCompositionInjector<ColumnDto, ColumnDetailDto>(
					"columnDetails", "columnId", columnDetailService, className);
			injectors.add(columnDetailsInjector);
		}
		return injectors;
	}
}
