package com.iobuilders.bank.application.usecases.users;

import com.iobuilders.bank.application.exception.ApplicationException;
import com.iobuilders.bank.application.exception.InstanceNotFoundException;
import com.iobuilders.bank.application.ports.inbound.users.FindUserByIdPort;
import com.iobuilders.bank.application.ports.outbound.persistence.UserPersistencePort;
import com.iobuilders.bank.domain.aggregate.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindUserByIdUseCase implements FindUserByIdPort {

	@NonNull
	private UserPersistencePort userPersistencePort;

	@Override
	public User execute(final Long userId) {
		try {
			return this.userPersistencePort.findUserById(userId);
		} catch (final InstanceNotFoundException exception) {
			log.error(exception.getMessage(), exception);
			throw new ApplicationException(exception.getErrorCode(), exception.getMessage());
		}
	}

}