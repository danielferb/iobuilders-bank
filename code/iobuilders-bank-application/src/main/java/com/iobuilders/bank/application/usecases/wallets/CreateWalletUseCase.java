package com.iobuilders.bank.application.usecases.wallets;

import static com.iobuilders.bank.application.exception.Error.VALIDATION;

import com.iobuilders.bank.application.exception.ApplicationException;
import com.iobuilders.bank.application.exception.InstanceNotFoundException;
import com.iobuilders.bank.application.ports.inbound.wallets.CreateWalletPort;
import com.iobuilders.bank.application.ports.outbound.persistence.UserPersistencePort;
import com.iobuilders.bank.application.ports.outbound.persistence.WalletPersistencePort;
import com.iobuilders.bank.domain.aggregate.Wallet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateWalletUseCase implements CreateWalletPort {

	@NonNull
	private WalletPersistencePort walletPersistencePort;

	@NonNull
	private UserPersistencePort userPersistencePort;

	@Override
	public Wallet execute(final Wallet wallet) {
		runValidations(wallet);
		return this.walletPersistencePort.persistWallet(wallet);
	}

	private void runValidations(final Wallet wallet) {

		if (wallet.getBalance().compareTo(0.0) < 0) {
			log.error("Error trying to create a new wallet, the balance is negative: {}", wallet);
			throw new ApplicationException(VALIDATION,
					"Error trying to create the wallet, the balance can not be negative");
		}

		try {
			this.userPersistencePort.findUserById(wallet.getUserId());
		} catch (InstanceNotFoundException e) {
			log.error("Error, the owner specified for the wallet, with the ID {}, does not exists",
					wallet.getUserId());
			throw new ApplicationException(VALIDATION,
					"Error trying to create the wallet, the owner specified does not exists");
		}
	}

}