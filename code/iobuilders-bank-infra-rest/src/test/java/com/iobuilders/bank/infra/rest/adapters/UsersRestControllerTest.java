package com.iobuilders.bank.infra.rest.adapters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import com.iobuilders.bank.application.ports.inbound.users.CreateUserPort;
import com.iobuilders.bank.application.ports.inbound.users.FindUserByIdPort;
import com.iobuilders.bank.domain.aggregate.User;
import com.iobuilders.bank.infra.rest.api.model.UserInformationDTO;
import com.iobuilders.bank.infra.rest.api.model.UserSignUpRequestDTO;
import com.iobuilders.bank.infra.rest.mappers.UserRestMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class UsersRestControllerTest {

	@InjectMocks
	private UsersRestController usersRestController;

	@Mock
	private UserRestMapper userRestMapper;

	@Mock
	private CreateUserPort createUserPort;

	@Mock
	private FindUserByIdPort findUserByIdPort;

	@Test
	@DisplayName("Sign Up a new user successfully")
	public void signUpUserSuccessfully() {

		final User userCreated = mock(User.class);
		final User userToCreate = mock(User.class);
		final UserInformationDTO responseDto = mock(UserInformationDTO.class);
		final UserSignUpRequestDTO requestDto = mock(UserSignUpRequestDTO.class);

		when(this.userRestMapper.toUser(requestDto)).thenReturn(userToCreate);
		when(this.createUserPort.execute(userToCreate)).thenReturn(userCreated);
		when(this.userRestMapper.toUserInformationDto(userCreated)).thenReturn(responseDto);

		final ResponseEntity<UserInformationDTO> response = this.usersRestController.signUpUser(
				requestDto);

		verify(this.userRestMapper).toUser(requestDto);
		verify(this.createUserPort).execute(userToCreate);
		verify(this.userRestMapper).toUserInformationDto(userCreated);

		assertThat(response.getStatusCode()).isEqualTo(OK);
		assertThat(response.getBody()).isEqualTo(responseDto);
	}

	@Test
	@DisplayName("Find a user by its identifier successfully")
	public void findUserByIdSuccessfully() {

		final Long userId = 1L;
		final User user = mock(User.class);
		final UserInformationDTO responseDto = mock(UserInformationDTO.class);

		when(this.userRestMapper.toUserInformationDto(user)).thenReturn(responseDto);
		when(this.findUserByIdPort.execute(userId)).thenReturn(user);

		final ResponseEntity<UserInformationDTO> response = this.usersRestController.findUserById(
				userId);

		verify(this.findUserByIdPort).execute(userId);
		verify(this.userRestMapper).toUserInformationDto(user);

		assertThat(response.getStatusCode()).isEqualTo(OK);
		assertThat(response.getBody()).isEqualTo(responseDto);
	}

}