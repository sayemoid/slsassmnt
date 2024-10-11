package dev.sayem.selis.exceptions.handler;

public class ErrorResponse {

	private int code;
	private String status;
	private String message;
	private Throwable exception;

	public ErrorResponse(int code, String status, String message) {
		this.code = code;
		this.status = status;
		this.message = message;
		this.exception = null;
	}

	public ErrorResponse(int code, String status, String message, Throwable exception) {
		this.code = code;
		this.status = status;
		this.message = message;
		this.exception = exception;
	}


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}
}
