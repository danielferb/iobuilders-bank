package com.iobuilders.bank.application.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationException extends RuntimeException {

	private Error errorCode;

	public ApplicationException(final Error error, final String message) {
		super(message);
		this.errorCode = error;
	}

	public ApplicationException(final Error error, final String message, final Throwable cause) {
		super(message, cause);
		this.errorCode = error;
	}

}