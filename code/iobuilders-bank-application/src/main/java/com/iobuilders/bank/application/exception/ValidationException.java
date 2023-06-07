package com.iobuilders.bank.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationException extends Exception {

	private Error errorCode;

	public ValidationException(final Error error, final String message) {
		super(message);
		this.errorCode = error;
	}

	public ValidationException(final Error error, final String message, final Throwable cause) {
		super(message, cause);
		this.errorCode = error;
	}

}