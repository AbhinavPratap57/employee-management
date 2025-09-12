package com.codemyth.payload;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApiResponse<T> {

	private boolean success;
	private String message;
	private T data;

}
