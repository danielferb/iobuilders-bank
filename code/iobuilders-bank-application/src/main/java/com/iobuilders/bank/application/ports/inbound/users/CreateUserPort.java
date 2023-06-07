package com.iobuilders.bank.application.ports.inbound.users;

import com.iobuilders.bank.domain.aggregate.User;

public interface CreateUserPort {

	/**
	 * Creates the user with the information received.
	 *
	 * @param user The user information to create a new one.
	 * @return The user created.
	 */
	User execute(User user);

}