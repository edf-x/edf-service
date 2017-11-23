package com.mk.eap.portal.impl;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.dubbo.config.annotation.Service;
import com.mk.eap.entity.impl.EntityCompositionInjector;
import com.mk.eap.entity.impl.EntityServiceImpl;
import com.mk.eap.entity.itf.IEntityInjector;
import com.mk.eap.oid.IDGenerator;
import com.mk.eap.portal.dao.MenuOperationMapper;
import com.mk.eap.portal.dao.OperationMapper;
import com.mk.eap.portal.dto.MenuOperationDto;
import com.mk.eap.portal.dto.OperationDto;
import com.mk.eap.portal.itf.IMenuOperationService;
import com.mk.eap.portal.itf.IOperationService;
import com.mk.eap.portal.vo.OperationVo;

@Component
@Service
public class OperationServiceImpl extends EntityServiceImpl<OperationDto, OperationVo, OperationMapper> implements IOperationService {
	
	@Autowired
	IMenuOperationService menuOperationService;
	
	@Autowired
    OperationMapper operationMapper;
	@Autowired
	MenuOperationMapper menuOperationMapper;
	
	@Override
	public OperationDto create(OperationDto operation) {
		Long id = Long.parseLong(IDGenerator.generateObjectID("eap-operation"));
		operation.setId(id);
		return super.create(operation);
	}
	
	@Override
	public List<OperationDto> save(List<OperationDto> operationList){
		List<OperationDto> newOperationList = new ArrayList<>();
		
		List<OperationDto> list = this.queryAll();
		for (OperationDto operationDto : list) {
			Integer count = 0;
			for (OperationDto uDto : operationList) {
				if (operationDto.getId() != null && uDto.getId() != null && operationDto.getId().equals(uDto.getId()))
				{
					break;
				}
				count++;
			}
			if (count == operationList.size())
			{
				this.delete(operationDto);
			}
		}
		
		
		for(OperationDto operationDto: operationList) {
			OperationDto newOperation = new OperationDto();
			if (operationDto.getId() == null || !this.isExists(operationDto.getId())) {
				newOperation = this.create(operationDto);
			}
			else {
				newOperation = this.update(operationDto);
			}
			newOperationList.add(operationDto);
		}
		return newOperationList;
	}
	
	private List<OperationDto> queryAll() {
		List<OperationDto> list = operationMapper.queryAll();
		return list;
	}
	
/*	List<IEntityInjector<OperationDto>> injectors;
	protected List<IEntityInjector<OperationDto>> getInjectors() {
		if (injectors == null) {
			injectors = new ArrayList<IEntityInjector<OperationDto>>();
			String className = MenuOperationDto.class.getName();
			IEntityInjector<OperationDto> menuOperationInjector = new EntityCompositionInjector<OperationDto, MenuOperationDto>(
					"operations", "operationId", menuOperationService, className);
			injectors.add(menuOperationInjector);
		}
		return injectors;
	}*/
	
	private boolean isExists(long id) {
		List<OperationDto> list = operationMapper.selectById(id);
		if (list.isEmpty())
			return false;
		return true;
	}
}
