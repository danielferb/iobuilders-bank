package com.iobuilders.bank.application.usecases.users;

import com.iobuilders.bank.application.ports.inbound.users.CreateUserPort;
import com.iobuilders.bank.application.ports.outbound.persistence.UserPersistencePort;
import com.iobuilders.bank.domain.aggregate.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserUseCase implements CreateUserPort {

	@NonNull
	private UserPersistencePort userPersistencePort;

	@Override
	public User execute(final User user) {
		return this.userPersistencePort.persistUser(user);
	}

}