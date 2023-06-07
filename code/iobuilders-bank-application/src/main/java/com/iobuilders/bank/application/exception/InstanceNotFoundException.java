package com.iobuilders.bank.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstanceNotFoundException extends Exception {

	private Error errorCode;

	public InstanceNotFoundException(final Error error, final String message) {
		super(message);
		this.errorCode = error;
	}

	public InstanceNotFoundException(final Error error, final String message, final Throwable cause) {
		super(message, cause);
		this.errorCode = error;
	}

}