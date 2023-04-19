package com.jk.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class ApiResponseMessage {
  
	private String message;
	private boolean success;
	private HttpStatus status;
}
