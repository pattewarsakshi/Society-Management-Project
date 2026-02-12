// ResetPasswordRequestDTO.java
package com.society.dto;

import lombok.Data;

@Data
public class ResetPasswordRequestDTO {
	private String email;
	private String newPassword;
}

