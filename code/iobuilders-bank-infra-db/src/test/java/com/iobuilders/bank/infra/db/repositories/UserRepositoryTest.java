package com.iobuilders.bank.infra.db.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.iobuilders.bank.application.exception.InstanceNotFoundException;
import com.iobuilders.bank.infra.db.entities.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

	@InjectMocks
	private UserRepository userRepository;

	@Test
	@DisplayName("Persist a new user and find it by its identifier")
	public void persistAndFindSuccessfully() throws InstanceNotFoundException {

		final String name = "Perico";
		final String email = "perico@gmail.com";
		final UserEntity user = UserEntity.builder().name(name).email(email).build();

		final UserEntity userPersisted = this.userRepository.persistUser(user);

		assertThat(userPersisted.getId()).isEqualTo(1L);

		final UserEntity userFound = this.userRepository.findById(userPersisted.getId());

		assertThat(userFound.getId()).isEqualTo(1L);
		assertThat(userFound.getName()).isEqualTo(name);
		assertThat(userFound.getEmail()).isEqualTo(email);
	}

	@Test
	@DisplayName("Throws exception when the user does not exists")
	public void throwExceptionWhenNotExists() {
		assertThrows(InstanceNotFoundException.class, () -> this.userRepository.findById(1L));
	}

}