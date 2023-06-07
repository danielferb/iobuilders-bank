package com.iobuilders.bank.application.usecases.wallets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.iobuilders.bank.application.exception.ApplicationException;
import com.iobuilders.bank.application.exception.InstanceNotFoundException;
import com.iobuilders.bank.application.ports.outbound.persistence.TransferPersistencePort;
import com.iobuilders.bank.application.ports.outbound.persistence.WalletPersistencePort;
import com.iobuilders.bank.domain.aggregate.Wallet;
import com.iobuilders.bank.domain.entities.Transfer;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FindWalletByIdUseCaseTest {

	@InjectMocks
	private FindWalletByIdUseCase findWalletByIdUseCase;

	@Mock
	private WalletPersistencePort walletPersistencePort;

	@Mock
	private TransferPersistencePort transferPersistencePort;

	@Test
	@DisplayName("Execute finding of a wallet with its transfers by its ID successfully")
	public void executeSuccessfully() throws InstanceNotFoundException {

		final Long walletId = 1L;
		final Wallet wallet = mock(Wallet.class);
		final Transfer firstTransfer = mock(Transfer.class);
		final Transfer secondTransfer = mock(Transfer.class);
		final List<Transfer> transfers = List.of(firstTransfer, secondTransfer);

		when(this.walletPersistencePort.findWalletById(walletId)).thenReturn(wallet);
		when(this.transferPersistencePort.findByWalletId(walletId)).thenReturn(transfers);

		final Wallet result = this.findWalletByIdUseCase.execute(walletId);

		verify(this.walletPersistencePort).findWalletById(walletId);
		verify(this.transferPersistencePort).findByWalletId(walletId);

		assertThat(result).isEqualTo(wallet);
	}

	@Test
	@DisplayName("Throw exception when the wallet to find does not exists")
	public void throwExceptionWhenNotExists() throws InstanceNotFoundException {

		final Long walletId = 1L;

		when(this.walletPersistencePort.findWalletById(walletId)).thenThrow(
				InstanceNotFoundException.class);

		assertThrows(ApplicationException.class, () -> this.findWalletByIdUseCase.execute(walletId));

		verify(this.walletPersistencePort).findWalletById(walletId);
		verify(this.transferPersistencePort, never()).findByWalletId(walletId);
	}

}