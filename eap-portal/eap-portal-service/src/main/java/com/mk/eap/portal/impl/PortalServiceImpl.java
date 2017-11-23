package com.mk.eap.portal.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.mk.eap.base.PageObject;
import com.mk.eap.discovery.dto.Token;
import com.mk.eap.entity.dto.PageQueryDto;
import com.mk.eap.entity.dto.PageResultDto;
import com.mk.eap.portal.dto.MenuDto;
import com.mk.eap.portal.dto.PortalDto;
import com.mk.eap.portal.itf.IMenuService;
import com.mk.eap.portal.itf.IPortalService;
import com.mk.eap.user.dto.UserDto;
import com.mk.eap.user.itf.IUserService;

@Component
@Service
public class PortalServiceImpl implements IPortalService {

	@Reference
	IUserService userService;

	@Autowired
	IMenuService menuService;

	@Override
	public PortalDto init(Token token) {
		PortalDto dto = new PortalDto();
		UserDto user = userService.findById(token.getUserId());
		dto.setUser(user);
		PageQueryDto<MenuDto> queryDto = new PageQueryDto<MenuDto>();
		PageObject page = new PageObject();
		page.setCurrentPage(0);
		page.setPageSize(300);
		page.setOrderBy("code");
		queryDto.setPage(page);
		queryDto.setEntity(new MenuDto());
		PageResultDto<MenuDto> menus = menuService.queryPageList(queryDto);
		dto.setMenu(menus.getList());
		return dto;
	}

}