package com.iobuilders.bank.application.usecases.wallets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.iobuilders.bank.application.exception.ApplicationException;
import com.iobuilders.bank.application.exception.InstanceNotFoundException;
import com.iobuilders.bank.application.ports.outbound.persistence.UserPersistencePort;
import com.iobuilders.bank.application.ports.outbound.persistence.WalletPersistencePort;
import com.iobuilders.bank.domain.aggregate.Wallet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateWalletUseCaseTest {

	@InjectMocks
	private CreateWalletUseCase createWalletUseCase;

	@Mock
	private UserPersistencePort userPersistencePort;

	@Mock
	private WalletPersistencePort walletPersistencePort;

	@Test
	@DisplayName("Executes the persistence of a new wallet successfully")
	public void executeSuccessfully() throws InstanceNotFoundException {

		final Long userId = 1L;
		final Wallet walletToPersist = mock(Wallet.class);
		final Wallet walletPersisted = mock(Wallet.class);

		when(walletToPersist.getUserId()).thenReturn(userId);
		when(this.walletPersistencePort.persistWallet(walletToPersist)).thenReturn(walletPersisted);

		final Wallet result = this.createWalletUseCase.execute(walletToPersist);

		verify(this.userPersistencePort).findUserById(userId);
		verify(this.walletPersistencePort).persistWallet(walletToPersist);

		assertThat(result).isEqualTo(walletPersisted);
	}

	@Test
	@DisplayName("Throws exception when the balance indicated is not valid")
	public void throwExceptionWhenNotValid() throws InstanceNotFoundException {

		final Long userId = 1L;
		final Double balance = -10.00;
		final Wallet walletToPersist = mock(Wallet.class);

		when(walletToPersist.getBalance()).thenReturn(balance);

		assertThrows(ApplicationException.class,
				() -> this.createWalletUseCase.execute(walletToPersist));

		verify(this.walletPersistencePort, never()).persistWallet(walletToPersist);
		verify(this.userPersistencePort, never()).findUserById(userId);
	}

}