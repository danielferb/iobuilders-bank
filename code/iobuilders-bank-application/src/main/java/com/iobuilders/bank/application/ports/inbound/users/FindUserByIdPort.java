package com.iobuilders.bank.application.ports.inbound.users;

import com.iobuilders.bank.domain.aggregate.User;

public interface FindUserByIdPort {

	/**
	 * Finds a user by its identifier.
	 *
	 * @param userId Unique identifier of the user to search by.
	 * @return The user found.
	 */
	User execute(Long userId);

}