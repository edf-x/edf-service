package com.mk.eap.portal.itf;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mk.eap.base.BusinessException;
import com.mk.eap.entity.itf.IEntityService;
import com.mk.eap.portal.dto.MenuOperationDto;

@RequestMapping("/menuOperation")
public interface IMenuOperationService extends IEntityService<MenuOperationDto> {

	@RequestMapping("/create")
	MenuOperationDto create(MenuOperationDto menuOperation) throws BusinessException;
	
	@RequestMapping("/update")
	MenuOperationDto update(MenuOperationDto menuOperation) throws BusinessException;
	
	@RequestMapping("/delete")
	MenuOperationDto delete(MenuOperationDto menuOperation) throws BusinessException;

	@RequestMapping("/query")
	List<MenuOperationDto> query(MenuOperationDto menuOperation) throws BusinessException;
	
	@RequestMapping("/save")
	List<MenuOperationDto> save(List<MenuOperationDto> menuOperations) throws BusinessException;

	List<MenuOperationDto> findByUserId(Long userId) throws BusinessException;
}
