package com.iobuilders.bank.application.usecases.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.iobuilders.bank.application.ports.outbound.persistence.UserPersistencePort;
import com.iobuilders.bank.domain.aggregate.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseTest {

	@InjectMocks
	private CreateUserUseCase createUserUseCase;

	@Mock
	private UserPersistencePort userPersistencePort;

	@Test
	@DisplayName("Executes the persistence of a new user successfully")
	public void executeSuccessfully() {

		final User userToPersist = mock(User.class);
		final User userPersisted = mock(User.class);

		when(this.userPersistencePort.persistUser(userToPersist)).thenReturn(userPersisted);

		final User result = this.createUserUseCase.execute(userToPersist);

		verify(this.userPersistencePort).persistUser(userToPersist);

		assertThat(result).isEqualTo(userPersisted);
	}

}