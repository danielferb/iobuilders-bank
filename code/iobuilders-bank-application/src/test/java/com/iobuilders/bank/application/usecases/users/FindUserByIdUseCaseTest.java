package com.iobuilders.bank.application.usecases.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.iobuilders.bank.application.exception.ApplicationException;
import com.iobuilders.bank.application.exception.InstanceNotFoundException;
import com.iobuilders.bank.application.ports.outbound.persistence.UserPersistencePort;
import com.iobuilders.bank.domain.aggregate.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FindUserByIdUseCaseTest {

	@InjectMocks
	private FindUserByIdUseCase findUserByIdUseCase;

	@Mock
	private UserPersistencePort userPersistencePort;

	@Test
	@DisplayName("Execute finding of a user by its ID successfully")
	public void executeSuccessfully() throws InstanceNotFoundException {

		final Long userId = 1L;
		final User user = mock(User.class);

		when(this.userPersistencePort.findUserById(userId)).thenReturn(user);

		final User result = this.findUserByIdUseCase.execute(userId);

		verify(this.userPersistencePort).findUserById(userId);

		assertThat(result).isEqualTo(user);
	}

	@Test
	@DisplayName("Throw exception when the user to find does not exists")
	public void throwExceptionWhenNotExists() throws InstanceNotFoundException {

		final Long userId = 1L;

		when(this.userPersistencePort.findUserById(userId)).thenThrow(InstanceNotFoundException.class);

		assertThrows(ApplicationException.class, () -> this.findUserByIdUseCase.execute(userId));

		verify(this.userPersistencePort).findUserById(userId);
	}

}