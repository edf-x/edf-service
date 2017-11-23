package com.mk.eap.user.dto;

import java.util.List;

import com.mk.eap.base.DTO;
import com.mk.eap.discovery.dto.Token;

public class UserDto extends DTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3393061230636170783L;

	Long id;

	String password;
	String mobile;
	String nickname;
	String birthday;
	Long sex;
		
	Token token;
	String oldPassword;
	
	List<UserRoleDto> roles; 

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}
	public List<UserRoleDto> getUserRoles(){
		return roles;
	}
	public void setUserRoles(List<UserRoleDto> roles) {
		this.roles = roles;
	}
}
