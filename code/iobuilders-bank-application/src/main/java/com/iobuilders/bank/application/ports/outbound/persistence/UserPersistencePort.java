package com.iobuilders.bank.application.ports.outbound.persistence;

import com.iobuilders.bank.application.exception.InstanceNotFoundException;
import com.iobuilders.bank.domain.aggregate.User;

public interface UserPersistencePort {

	/**
	 * Persists a new user with the information received.
	 *
	 * @param user Information of the user to persist.
	 * @return The user created.
	 */
	User persistUser(User user);

	/**
	 * Finds a user by its identifier.
	 *
	 * @param userId Unique identifier of the user to search by.
	 * @return User found.
	 */
	User findUserById(Long userId) throws InstanceNotFoundException;

}