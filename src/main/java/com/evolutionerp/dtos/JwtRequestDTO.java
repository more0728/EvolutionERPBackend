package com.evolutionerp.dtos;

public class JwtRequestDTO {
	private String username;
	private String password;

	public JwtRequestDTO() {
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}