package com.iobuilders.bank.infra.rest.adapters;

import com.iobuilders.bank.application.ports.inbound.users.CreateUserPort;
import com.iobuilders.bank.application.ports.inbound.users.FindUserByIdPort;
import com.iobuilders.bank.domain.aggregate.User;
import com.iobuilders.bank.infra.rest.api.UsersApi;
import com.iobuilders.bank.infra.rest.api.model.UserInformationDTO;
import com.iobuilders.bank.infra.rest.api.model.UserSignUpRequestDTO;
import com.iobuilders.bank.infra.rest.mappers.UserRestMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsersRestController implements UsersApi {

	@NonNull
	private UserRestMapper userRestMapper;

	@NonNull
	private CreateUserPort createUserPort;

	@NonNull
	private FindUserByIdPort findUserByIdPort;

	@Override
	public ResponseEntity<UserInformationDTO> signUpUser(
			final UserSignUpRequestDTO userSignUpRequestDTO) {
		final User user = this.createUserPort.execute(this.userRestMapper.toUser(userSignUpRequestDTO));
		return ResponseEntity.ok(this.userRestMapper.toUserInformationDto(user));
	}

	@Override
	public ResponseEntity<UserInformationDTO> findUserById(final Long userId) {
		final User user = this.findUserByIdPort.execute(userId);
		return ResponseEntity.ok(this.userRestMapper.toUserInformationDto(user));
	}
}