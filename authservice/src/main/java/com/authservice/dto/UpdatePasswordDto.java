package com.authservice.dto;

public class UpdatePasswordDto {

	private String email;
	private String username;
	private String oldPassword;
	private String newPassword;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "UpdatePasswordDto [email=" + email + ", username=" + username + ", oldPassword=" + oldPassword
				+ ", newPassword=" + newPassword + "]";
	}

	public UpdatePasswordDto(String email, String username, String oldPassword, String newPassword) {
		super();
		this.email = email;
		this.username = username;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public UpdatePasswordDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
