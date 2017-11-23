package com.mk.eap.portal.dto;

import java.util.List;

import com.mk.eap.base.DTO;
import com.mk.eap.user.dto.UserDto;

public class PortalDto  extends DTO {
 

	/**
	 * 
	 */
	private static final long serialVersionUID = 9076233039123269292L;
	
	UserDto user;
	List<MenuDto> menu;

	public List<MenuDto> getMenu() {
		return menu;
	}

	public void setMenu(List<MenuDto> menu) {
		this.menu = menu;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
}
