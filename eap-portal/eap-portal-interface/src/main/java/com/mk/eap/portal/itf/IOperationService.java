package com.mk.eap.portal.itf;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mk.eap.base.BusinessException;
import com.mk.eap.entity.itf.IEntityService;
import com.mk.eap.portal.dto.OperationDto;

@RequestMapping("/operation")
public interface IOperationService extends IEntityService<OperationDto> {

	@RequestMapping("/create")
	OperationDto create(OperationDto operation) throws BusinessException;
	
	@RequestMapping("/update")
	OperationDto update(OperationDto operation) throws BusinessException;
	
	@RequestMapping("/delete")
	OperationDto delete(OperationDto operation) throws BusinessException;
	
	@RequestMapping("/query")
	List<OperationDto> query(OperationDto operation) throws BusinessException;
	
	@RequestMapping("/save")
	List<OperationDto> save(List<OperationDto> operationList) throws BusinessException;	
}
