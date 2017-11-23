package com.mk.eap.portal.itf;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mk.eap.base.BusinessException;
import com.mk.eap.entity.itf.IEntityService;
import com.mk.eap.portal.dto.RoleDto;
import com.mk.eap.portal.dto.RoleInitDto;

@RequestMapping("/role")
public interface IRoleService extends IEntityService<RoleDto> {

	@RequestMapping("/create")
	RoleDto create(RoleDto role) throws BusinessException;
	
	@RequestMapping("/update")
	RoleDto update(RoleDto role) throws BusinessException;
	
	@RequestMapping("/delete")
	RoleDto delete(RoleDto role) throws BusinessException;
	
	@RequestMapping("/query")
	List<RoleDto> query(RoleDto role) throws BusinessException;
	
	@RequestMapping("/init")
	RoleInitDto init() throws BusinessException;
	
	@RequestMapping("/findById")
	RoleDto queryByPrimaryKey(Long key) throws BusinessException;
}