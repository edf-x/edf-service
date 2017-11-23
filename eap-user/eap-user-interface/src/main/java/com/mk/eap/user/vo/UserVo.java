package com.mk.eap.user.vo;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import com.mk.eap.base.VO;

@Table(name = "eap_user")
public class UserVo extends VO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3295873086839159655L;

	@Id
	Long id;
	String password;
	String mobile;
	String nickname;
	String birthday;
	Long sex;

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
}
