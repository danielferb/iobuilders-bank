package com.iobuilders.bank.infra.rest.adapters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import com.iobuilders.bank.application.ports.inbound.wallets.CreateDepositPort;
import com.iobuilders.bank.application.ports.inbound.wallets.CreateTransferPort;
import com.iobuilders.bank.application.ports.inbound.wallets.CreateWalletPort;
import com.iobuilders.bank.application.ports.inbound.wallets.FindWalletByIdPort;
import com.iobuilders.bank.domain.aggregate.Wallet;
import com.iobuilders.bank.domain.entities.Deposit;
import com.iobuilders.bank.domain.entities.Transfer;
import com.iobuilders.bank.infra.rest.api.model.DepositRequestDTO;
import com.iobuilders.bank.infra.rest.api.model.TransferRequestDTO;
import com.iobuilders.bank.infra.rest.api.model.WalletCreationRequestDTO;
import com.iobuilders.bank.infra.rest.api.model.WalletInformationDTO;
import com.iobuilders.bank.infra.rest.mappers.WalletRestMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class WalletsRestControllerTest {

	@InjectMocks
	private WalletsRestController walletsRestController;

	@Mock
	private WalletRestMapper walletRestMapper;

	@Mock
	private CreateWalletPort createWalletPort;

	@Mock
	private CreateDepositPort createDepositPort;

	@Mock
	private CreateTransferPort createTransferPort;

	@Mock
	private FindWalletByIdPort findWalletByIdPort;

	@Test
	@DisplayName("Create a new wallet successfully")
	public void createWalletSuccessfully() {

		final Wallet walletCreated = mock(Wallet.class);
		final Wallet walletToCreate = mock(Wallet.class);
		final WalletInformationDTO responseDto = mock(WalletInformationDTO.class);
		final WalletCreationRequestDTO requestDto = mock(WalletCreationRequestDTO.class);

		when(this.walletRestMapper.toWallet(requestDto)).thenReturn(walletToCreate);
		when(this.createWalletPort.execute(walletToCreate)).thenReturn(walletCreated);
		when(this.walletRestMapper.toWalletInformationDto(walletCreated)).thenReturn(responseDto);

		final ResponseEntity<WalletInformationDTO> response = this.walletsRestController.createWallet(
				requestDto);

		verify(this.walletRestMapper).toWallet(requestDto);
		verify(this.createWalletPort).execute(walletToCreate);
		verify(this.walletRestMapper).toWalletInformationDto(walletCreated);

		assertThat(response.getStatusCode()).isEqualTo(OK);
		assertThat(response.getBody()).isEqualTo(responseDto);
	}

	@Test
	@DisplayName("Find a wallet by its identifier")
	public void findWalletByIdSuccessfully() {

		final Long walletId = 1L;
		final Wallet wallet = mock(Wallet.class);
		final WalletInformationDTO responseDto = mock(WalletInformationDTO.class);

		when(this.walletRestMapper.toWalletInformationDto(wallet)).thenReturn(responseDto);
		when(this.findWalletByIdPort.execute(walletId)).thenReturn(wallet);

		final ResponseEntity<WalletInformationDTO> response = this.walletsRestController.findWalletById(
				walletId);

		verify(this.findWalletByIdPort).execute(walletId);
		verify(this.walletRestMapper).toWalletInformationDto(wallet);

		assertThat(response.getStatusCode()).isEqualTo(OK);
		assertThat(response.getBody()).isEqualTo(responseDto);
	}

	@Test
	@DisplayName("Create a new deposit successfully")
	public void createDepositSuccessfully() {

		final Deposit deposit = mock(Deposit.class);
		final DepositRequestDTO requestDto = mock(DepositRequestDTO.class);

		when(this.walletRestMapper.toDeposit(requestDto)).thenReturn(deposit);

		final ResponseEntity<Void> response = this.walletsRestController.createDeposit(requestDto);

		verify(this.walletRestMapper).toDeposit(requestDto);
		verify(this.createDepositPort).execute(deposit);

		assertThat(response.getStatusCode()).isEqualTo(OK);
	}

	@Test
	@DisplayName("Create a new transfer successfully")
	public void createTransferenceSuccessfully() {

		final Transfer transfer = mock(Transfer.class);
		final TransferRequestDTO requestDto = mock(TransferRequestDTO.class);

		when(this.walletRestMapper.toTransfer(requestDto)).thenReturn(transfer);

		final ResponseEntity<Void> response = this.walletsRestController.createTransference(requestDto);

		verify(this.walletRestMapper).toTransfer(requestDto);
		verify(this.createTransferPort).execute(transfer);

		assertThat(response.getStatusCode()).isEqualTo(OK);
	}

}