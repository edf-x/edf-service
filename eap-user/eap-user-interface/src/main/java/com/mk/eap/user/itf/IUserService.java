package com.mk.eap.user.itf;


import org.springframework.web.bind.annotation.RequestMapping;

import com.mk.eap.base.BusinessException;
import com.mk.eap.discovery.annotation.ApiContext;
import com.mk.eap.discovery.annotation.ApiResult;
import com.mk.eap.discovery.dto.Token;
import com.mk.eap.entity.dto.PageQueryDto;
import com.mk.eap.entity.dto.PageResultDto;
import com.mk.eap.entity.itf.IEntityService;
import com.mk.eap.user.dto.UserDto;
import com.mk.eap.user.dto.UserResetPasswordDto;

@RequestMapping("/user")
public interface IUserService extends IEntityService<UserDto> {

	@RequestMapping("/login")
	@ApiResult("token")
	UserDto login(UserDto user)throws BusinessException;

	@RequestMapping("/logout")
	@ApiContext("token")
	Boolean logout(Token token);

	@RequestMapping("/existsMobile")
	Boolean existsMobile(String mobile);

	@RequestMapping("/modifyPassword")
	@ApiContext("id:userId")
	Boolean modifyPassword(UserDto user)throws BusinessException;
	
	@RequestMapping("/resetPassword") 
	Boolean resetPassword(UserResetPasswordDto user)throws BusinessException;

	@RequestMapping("/update")
	@ApiContext("id:userId")
	UserDto update(UserDto user)throws BusinessException;

	@RequestMapping("/create")
	UserDto create(UserDto user)throws BusinessException;

	@RequestMapping("/queryPageList")
	PageResultDto<UserDto> queryPageList(PageQueryDto<UserDto> queryDto) throws BusinessException;
	
	@RequestMapping("/findById")
	@ApiContext("userId")
	UserDto findById(Long userId);

}
