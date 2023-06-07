package com.iobuilders.bank.infra.rest.adapters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

import com.iobuilders.bank.config.ITTest;
import com.iobuilders.bank.infra.rest.api.model.DepositRequestDTO;
import com.iobuilders.bank.infra.rest.api.model.TransferRequestDTO;
import com.iobuilders.bank.infra.rest.api.model.UserInformationDTO;
import com.iobuilders.bank.infra.rest.api.model.UserSignUpRequestDTO;
import com.iobuilders.bank.infra.rest.api.model.WalletCreationRequestDTO;
import com.iobuilders.bank.infra.rest.api.model.WalletInformationDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class WalletRestControllerITTest extends ITTest {

	@Autowired
	private WalletsRestController walletsRestController;

	@Autowired
	private UsersRestController usersRestController;

	@Test
	@DisplayName("Create new wallet and find it by its identifier")
	public void createWalletAndFindSuccessfully() {

		final ResponseEntity<UserInformationDTO> userCreationResponse = this.usersRestController.signUpUser(
				new UserSignUpRequestDTO().name("Ronaldo Nazario").email("ronnie@gmail.com")
						.fiscalAddress("Fake Street 123").nif("11223344A"));

		assertThat(userCreationResponse.getStatusCode()).isEqualTo(OK);
		assertThat(userCreationResponse.getBody()).isNotNull();
		assertThat(userCreationResponse.getBody().getId()).isNotNull();

		final WalletCreationRequestDTO requestDto = new WalletCreationRequestDTO().ownerId(
				userCreationResponse.getBody().getId()).balance(1000.0);

		final ResponseEntity<WalletInformationDTO> creationResponse = this.walletsRestController.createWallet(
				requestDto);

		assertThat(creationResponse.getStatusCode()).isEqualTo(OK);
		assertThat(creationResponse.getBody()).isNotNull();
		assertThat(creationResponse.getBody().getId()).isNotNull();

		final ResponseEntity<WalletInformationDTO> walletFound = this.walletsRestController.findWalletById(
				creationResponse.getBody().getId());

		assertThat(walletFound.getStatusCode()).isEqualTo(OK);
		assertThat(walletFound.getBody()).isNotNull();
		assertThat(walletFound.getBody().getId()).isEqualTo(creationResponse.getBody().getId());
	}

	@Test
	@DisplayName("Create a new deposit successfully")
	public void createDepositSuccessfully() {

		final ResponseEntity<UserInformationDTO> userCreationResponse = this.usersRestController.signUpUser(
				new UserSignUpRequestDTO().name("Ronaldo Nazario").email("ronnie@gmail.com")
						.fiscalAddress("Fake Street 123").nif("11223344A"));

		assertThat(userCreationResponse.getStatusCode()).isEqualTo(OK);
		assertThat(userCreationResponse.getBody()).isNotNull();
		assertThat(userCreationResponse.getBody().getId()).isNotNull();

		final WalletCreationRequestDTO walletRequestDto = new WalletCreationRequestDTO().ownerId(
				userCreationResponse.getBody().getId()).balance(1000.0);

		final ResponseEntity<WalletInformationDTO> walletResponse = this.walletsRestController.createWallet(
				walletRequestDto);

		assertThat(walletResponse.getStatusCode()).isEqualTo(OK);
		assertThat(walletResponse.getBody()).isNotNull();
		assertThat(walletResponse.getBody().getId()).isNotNull();

		final DepositRequestDTO depositRequestDto = new DepositRequestDTO().destinationWalletId(
				walletResponse.getBody().getId()).amount(250.22);

		final ResponseEntity<Void> depositResponse = this.walletsRestController.createDeposit(
				depositRequestDto);

		assertThat(depositResponse.getStatusCode()).isEqualTo(OK);

		final ResponseEntity<WalletInformationDTO> walletFound = this.walletsRestController.findWalletById(
				walletResponse.getBody().getId());

		assertThat(walletFound.getStatusCode()).isEqualTo(OK);
		assertThat(walletFound.getBody()).isNotNull();
		assertThat(walletFound.getBody().getId()).isEqualTo(walletResponse.getBody().getId());
		assertThat(walletFound.getBody().getBalance()).isEqualTo(1250.22);
		assertThat(walletFound.getBody().getMovements()).hasSize(1);
		assertThat(walletFound.getBody().getMovements().get(0).getAmount()).isEqualTo(250.22);
		assertThat(walletFound.getBody().getMovements().get(0).getDestinationWalletId()).isEqualTo(
				walletResponse.getBody().getId());
		assertThat(walletFound.getBody().getMovements().get(0).getOriginWalletId()).isNull();
	}

	@Test
	@DisplayName("Create a new transfer successfully")
	public void createTransferSuccessfully() {

		// USERS CREATION
		final ResponseEntity<UserInformationDTO> firstUserResponse = this.usersRestController.signUpUser(
				new UserSignUpRequestDTO().name("Ronaldo Nazario").email("ronnie@gmail.com")
						.fiscalAddress("Fake Street 123").nif("11223344A"));
		final ResponseEntity<UserInformationDTO> secondUserResponse = this.usersRestController.signUpUser(
				new UserSignUpRequestDTO().name("Rafael Nadal").email("rafa@gmail.com")
						.fiscalAddress("Fake Street 321").nif("11223344B"));

		assertThat(firstUserResponse.getStatusCode()).isEqualTo(OK);
		assertThat(firstUserResponse.getBody()).isNotNull();
		assertThat(firstUserResponse.getBody().getId()).isNotNull();
		assertThat(secondUserResponse.getStatusCode()).isEqualTo(OK);
		assertThat(secondUserResponse.getBody()).isNotNull();
		assertThat(secondUserResponse.getBody().getId()).isNotNull();

		// WALLETS CREATION
		final ResponseEntity<WalletInformationDTO> firstWalletResponse = this.walletsRestController.createWallet(
				new WalletCreationRequestDTO().ownerId(firstUserResponse.getBody().getId())
						.balance(1000.0));
		final ResponseEntity<WalletInformationDTO> secondWalletResponse = this.walletsRestController.createWallet(
				new WalletCreationRequestDTO().ownerId(secondUserResponse.getBody().getId())
						.balance(1000.0));

		assertThat(firstWalletResponse.getStatusCode()).isEqualTo(OK);
		assertThat(firstWalletResponse.getBody()).isNotNull();
		assertThat(firstWalletResponse.getBody().getId()).isNotNull();
		assertThat(secondWalletResponse.getStatusCode()).isEqualTo(OK);
		assertThat(secondWalletResponse.getBody()).isNotNull();
		assertThat(secondWalletResponse.getBody().getId()).isNotNull();

		// TRANSFERS CREATION
		final ResponseEntity<Void> firstTransfer = this.walletsRestController.createTransference(
				new TransferRequestDTO().destinationWalletId(secondWalletResponse.getBody().getId())
						.originWalletId(firstWalletResponse.getBody().getId()).amount(250.00));
		final ResponseEntity<Void> secondTransfer = this.walletsRestController.createTransference(
				new TransferRequestDTO().destinationWalletId(firstWalletResponse.getBody().getId())
						.originWalletId(secondWalletResponse.getBody().getId()).amount(200.00));

		assertThat(firstTransfer.getStatusCode()).isEqualTo(OK);
		assertThat(secondTransfer.getStatusCode()).isEqualTo(OK);

		// ASSERTIONS
		final ResponseEntity<WalletInformationDTO> walletFound = this.walletsRestController.findWalletById(
				firstWalletResponse.getBody().getId());

		assertThat(walletFound.getStatusCode()).isEqualTo(OK);
		assertThat(walletFound.getBody()).isNotNull();
		assertThat(walletFound.getBody().getId()).isEqualTo(firstWalletResponse.getBody().getId());
		assertThat(walletFound.getBody().getBalance()).isEqualTo(950.00);
		assertThat(walletFound.getBody().getMovements()).hasSize(2);
		assertThat(walletFound.getBody().getMovements().get(0).getAmount()).isEqualTo(-250.00);
		assertThat(walletFound.getBody().getMovements().get(0).getDestinationWalletId()).isEqualTo(
				secondWalletResponse.getBody().getId());
		assertThat(walletFound.getBody().getMovements().get(0).getOriginWalletId()).isEqualTo(
				firstWalletResponse.getBody().getId());
		assertThat(walletFound.getBody().getMovements().get(1).getAmount()).isEqualTo(200.00);
		assertThat(walletFound.getBody().getMovements().get(1).getDestinationWalletId()).isEqualTo(
				firstWalletResponse.getBody().getId());
		assertThat(walletFound.getBody().getMovements().get(1).getOriginWalletId()).isEqualTo(
				secondWalletResponse.getBody().getId());
	}

}