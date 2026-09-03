package com.example.evolutionerp.dtos;


public class JwtRequestDTO  {
	private String username;
		private String password;
	public JwtRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JwtRequestDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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