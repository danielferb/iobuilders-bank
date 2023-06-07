package com.iobuilders.bank.application.usecases.wallets;

import com.iobuilders.bank.application.exception.ApplicationException;
import com.iobuilders.bank.application.exception.InstanceNotFoundException;
import com.iobuilders.bank.application.ports.inbound.wallets.FindWalletByIdPort;
import com.iobuilders.bank.application.ports.outbound.persistence.TransferPersistencePort;
import com.iobuilders.bank.application.ports.outbound.persistence.WalletPersistencePort;
import com.iobuilders.bank.domain.aggregate.Wallet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindWalletByIdUseCase implements FindWalletByIdPort {

	@NonNull
	private WalletPersistencePort walletPersistencePort;

	@NonNull
	private TransferPersistencePort transferPersistencePort;

	@Override
	public Wallet execute(final Long walletId) {
		try {
			final Wallet wallet = this.walletPersistencePort.findWalletById(walletId);
			wallet.setTransfers(this.transferPersistencePort.findByWalletId(walletId));
			return wallet;
		} catch (final InstanceNotFoundException exception) {
			log.error(exception.getMessage(), exception);
			throw new ApplicationException(exception.getErrorCode(), exception.getMessage());
		}
	}

}