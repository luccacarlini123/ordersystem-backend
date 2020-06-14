package com.mouzetech.ordersystem.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class EmailDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "O email não pode ficar em branco")
	@Email(message = "Email inválido")
	private String email;
	
	public EmailDTO() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
}
