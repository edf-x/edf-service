package com.mk.eap.user.itf;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mk.eap.base.BusinessException;
import com.mk.eap.entity.itf.IEntityService;
import com.mk.eap.user.dto.UserRoleDto;

@RequestMapping("/userrole")
public interface IUserRoleService extends IEntityService<UserRoleDto> {

	@RequestMapping("/create")
	UserRoleDto create(UserRoleDto userrole) throws BusinessException;
	
	@RequestMapping("/update")
	UserRoleDto update(UserRoleDto userrole) throws BusinessException;
	
	@RequestMapping("/delete")
	UserRoleDto delete(UserRoleDto userrole) throws BusinessException;
		
	@RequestMapping("/query")
	List<UserRoleDto> query(UserRoleDto userrole) throws BusinessException;

	@RequestMapping("/findById")
	List<UserRoleDto> findById(Long userId) throws BusinessException;

	@RequestMapping("/save")
	List<UserRoleDto> save(List<UserRoleDto> userRoles);
}