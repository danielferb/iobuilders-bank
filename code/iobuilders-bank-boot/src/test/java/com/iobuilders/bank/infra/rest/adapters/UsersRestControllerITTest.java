package com.iobuilders.bank.infra.rest.adapters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

import com.iobuilders.bank.config.ITTest;
import com.iobuilders.bank.infra.rest.api.model.UserInformationDTO;
import com.iobuilders.bank.infra.rest.api.model.UserSignUpRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class UsersRestControllerITTest extends ITTest {

	@Autowired
	private UsersRestController usersRestController;

	@Test
	public void createUserAndFindSuccessfully() {

		final UserSignUpRequestDTO requestDto = new UserSignUpRequestDTO().name("Ronaldo Nazario")
				.email("ronnie@gmail.com").fiscalAddress("Fake Street 123").nif("11223344A");

		final ResponseEntity<UserInformationDTO> creationResponse = this.usersRestController.signUpUser(
				requestDto);

		assertThat(creationResponse.getStatusCode()).isEqualTo(OK);
		assertThat(creationResponse.getBody()).isNotNull();
		assertThat(creationResponse.getBody().getId()).isNotNull();

		final ResponseEntity<UserInformationDTO> userFound = this.usersRestController.findUserById(
				creationResponse.getBody().getId());

		assertThat(userFound.getStatusCode()).isEqualTo(OK);
		assertThat(userFound.getBody()).isNotNull();
		assertThat(userFound.getBody().getId()).isEqualTo(creationResponse.getBody().getId());
		assertThat(userFound.getBody().getName()).isEqualTo(requestDto.getName());
		assertThat(userFound.getBody().getEmail()).isEqualTo(requestDto.getEmail());
		assertThat(userFound.getBody().getFiscalAddress()).isEqualTo(requestDto.getFiscalAddress());
		assertThat(userFound.getBody().getNif()).isEqualTo(requestDto.getNif());
	}

}