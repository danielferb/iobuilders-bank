package com.iobuilders.bank.application.usecases.wallets;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.iobuilders.bank.application.exception.ApplicationException;
import com.iobuilders.bank.application.exception.InstanceNotFoundException;
import com.iobuilders.bank.application.ports.outbound.persistence.TransferPersistencePort;
import com.iobuilders.bank.application.ports.outbound.persistence.WalletPersistencePort;
import com.iobuilders.bank.domain.entities.Transfer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateTransferUseCaseTest {

	@InjectMocks
	private CreateTransferUseCase createTransferUseCase;

	@Mock
	private WalletPersistencePort walletPersistencePort;

	@Mock
	private TransferPersistencePort transferPersistencePort;

	@Test
	@DisplayName("Executes the creation of a new deposit successfully")
	public void executeSuccessfully() throws InstanceNotFoundException {

		final Double amount = 10.00;
		final Long originWalletId = 1L;
		final Long destinationWalletId = 2L;
		final Transfer transfer = mock(Transfer.class);

		when(transfer.getAmount()).thenReturn(amount);
		when(transfer.getOriginWalletId()).thenReturn(originWalletId);
		when(transfer.getDestinationWalletId()).thenReturn(destinationWalletId);

		this.createTransferUseCase.execute(transfer);

		verify(this.transferPersistencePort).persistTransfer(transfer);
		verify(this.walletPersistencePort).addAmountToWallet(destinationWalletId, amount);
		verify(this.walletPersistencePort).withdrawAmountToWallet(originWalletId, amount);
	}

	@Test
	@DisplayName("Throws exception when the wallet to make the transfer to does not exists")
	public void throwExceptionWhenWalletNotExists() throws InstanceNotFoundException {

		final Double amount = 10.00;
		final Long originWalletId = 1L;
		final Long destinationWalletId = 2L;
		final Transfer transfer = mock(Transfer.class);

		when(transfer.getAmount()).thenReturn(amount);
		when(transfer.getDestinationWalletId()).thenReturn(destinationWalletId);
		doThrow(InstanceNotFoundException.class).when(this.walletPersistencePort)
				.addAmountToWallet(destinationWalletId, amount);

		assertThrows(ApplicationException.class, () -> this.createTransferUseCase.execute(transfer));

		verify(this.transferPersistencePort).persistTransfer(transfer);
		verify(this.walletPersistencePort).addAmountToWallet(destinationWalletId, amount);
		verify(this.walletPersistencePort, never()).withdrawAmountToWallet(originWalletId, amount);
	}

	@Test
	@DisplayName("Throws exception when the amount indicated is not valid")
	public void throwExceptionWhenNotValid() throws InstanceNotFoundException {

		final Double amount = -10.00;
		final Long originWalletId = 1L;
		final Long destinationWalletId = 2L;
		final Transfer transfer = mock(Transfer.class);

		when(transfer.getAmount()).thenReturn(amount);

		assertThrows(ApplicationException.class, () -> this.createTransferUseCase.execute(transfer));

		verify(this.transferPersistencePort, never()).persistTransfer(transfer);
		verify(this.walletPersistencePort, never()).addAmountToWallet(destinationWalletId, amount);
		verify(this.walletPersistencePort, never()).withdrawAmountToWallet(originWalletId, amount);
	}

}