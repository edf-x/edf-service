package com.mk.eap.user.itf;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import com.mk.eap.entity.itf.IEntityService;
import com.mk.eap.user.dto.UserDto;


@RequestMapping("/userManage")
public interface IUserManageService extends IEntityService<UserDto> {

	@RequestMapping("/findById")
	UserDto findById(Long id);
	
	@RequestMapping("/update")
	UserDto update(UserDto user);

	@RequestMapping("/create")
	UserDto create(UserDto user);
	@RequestMapping("/batchDelete")
	void batchDelete(List<Long> ids);

}
 