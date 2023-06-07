package com.iobuilders.bank.application.usecases.wallets;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.iobuilders.bank.application.exception.ApplicationException;
import com.iobuilders.bank.application.exception.InstanceNotFoundException;
import com.iobuilders.bank.application.ports.outbound.persistence.TransferPersistencePort;
import com.iobuilders.bank.application.ports.outbound.persistence.WalletPersistencePort;
import com.iobuilders.bank.domain.entities.Deposit;
import com.iobuilders.bank.domain.entities.Transfer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateDepositUseCaseTest {

	@InjectMocks
	private CreateDepositUseCase createDepositUseCase;

	@Mock
	private WalletPersistencePort walletPersistencePort;

	@Mock
	private TransferPersistencePort transferPersistencePort;

	@Test
	@DisplayName("Executes the creation of a new deposit successfully")
	public void executeSuccessfully() throws InstanceNotFoundException {

		final Long walletId = 1L;
		final Double amount = 10.00;
		final Deposit deposit = mock(Deposit.class);

		when(deposit.getAmount()).thenReturn(amount);
		when(deposit.getDestinationWalletId()).thenReturn(walletId);

		this.createDepositUseCase.execute(deposit);

		verify(this.walletPersistencePort).addAmountToWallet(walletId, amount);
		verify(this.transferPersistencePort).persistTransfer(any(Transfer.class));
	}

	@Test
	@DisplayName("Throws exception when the wallet indicated does not exists")
	public void throwExceptionWhenWalletNotExists() throws InstanceNotFoundException {

		final Long walletId = 1L;
		final Double amount = 10.00;
		final Deposit deposit = mock(Deposit.class);

		when(deposit.getAmount()).thenReturn(amount);
		when(deposit.getDestinationWalletId()).thenReturn(walletId);
		doThrow(InstanceNotFoundException.class).when(this.walletPersistencePort)
				.addAmountToWallet(walletId, amount);

		assertThrows(ApplicationException.class, () -> this.createDepositUseCase.execute(deposit));

		verify(this.walletPersistencePort).addAmountToWallet(walletId, amount);
		verify(this.transferPersistencePort, never()).persistTransfer(any(Transfer.class));
	}

	@Test
	@DisplayName("Throws exception when the amount indicated is not valid")
	public void throwExceptionWhenNotValid() throws InstanceNotFoundException {

		final Long walletId = 1L;
		final Double amount = -10.00;
		final Deposit deposit = mock(Deposit.class);

		when(deposit.getAmount()).thenReturn(amount);

		assertThrows(ApplicationException.class, () -> this.createDepositUseCase.execute(deposit));

		verify(this.walletPersistencePort, never()).addAmountToWallet(walletId, amount);
		verify(this.transferPersistencePort, never()).persistTransfer(any(Transfer.class));
	}

}