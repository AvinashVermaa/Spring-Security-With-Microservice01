package com.authservice.dto;

public class ApiResponse<T> {

	private String message;
	private int status;
	private T data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ApiResponse [message=" + message + ", status=" + status + ", data=" + data + "]";
	}

	public ApiResponse(String message, int status, T data) {
		super();
		this.message = message;
		this.status = status;
		this.data = data;
	}

	public ApiResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

}
