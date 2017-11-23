package com.mk.eap.user.dto;

import java.io.Serializable;

public class MySettingDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1984085230921406094L;
	
	UserDto user;

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
}
