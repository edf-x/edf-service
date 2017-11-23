package com.mk.eap.portal.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.mk.eap.base.BusinessException;
import com.mk.eap.entity.impl.EntityServiceImpl;
import com.mk.eap.oid.IDGenerator;
import com.mk.eap.portal.dao.RoleMapper;
import com.mk.eap.portal.dto.MenuDto;
import com.mk.eap.portal.dto.MenuOperationDto;
import com.mk.eap.portal.dto.OperationDto;
import com.mk.eap.portal.dto.RoleDto;
import com.mk.eap.portal.dto.RoleInitDto;
import com.mk.eap.portal.itf.IMenuOperationService;
import com.mk.eap.portal.itf.IMenuService;
import com.mk.eap.portal.itf.IOperationService;
import com.mk.eap.portal.itf.IRoleService;
import com.mk.eap.portal.vo.RoleVo;

@Component
@Service
public class RoleServiceImpl extends EntityServiceImpl<RoleDto, RoleVo, RoleMapper> implements IRoleService {

	@Autowired
	IMenuService menuService;

	@Autowired
	IOperationService operationService;

	@Autowired
	IMenuOperationService menuOperationService;

	@Override
	public RoleDto create(RoleDto role) {
		Long id = Long.parseLong(IDGenerator.generateObjectID("eap-role"));
		role.setId(id);
		return super.create(role);
	}

	@Override
	public RoleInitDto init() throws BusinessException {
		RoleInitDto dto = new RoleInitDto();

		List<RoleDto> roles = this.query(new RoleDto());
		List<MenuDto> menus = menuService.queryFull(new MenuDto());
		List<OperationDto> operations = operationService.query(new OperationDto());
		Long roleId = 0L;
		if (roles.size() > 0) {
			roleId = roles.get(0).getId();
		} 
		MenuOperationDto menuOperation = new MenuOperationDto(); 
		menuOperation.setRoleId(roleId);
		List<MenuOperationDto> menuOperations = menuOperationService.query(menuOperation);

		dto.setRoles(roles);
		dto.setMenus(menus);
		dto.setOperations(operations);
		dto.setMenuOperations(menuOperations);

		return dto;
	}
}
