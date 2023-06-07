package com.iobuilders.bank.application.usecases.wallets;

import static com.iobuilders.bank.application.exception.Error.VALIDATION;

import com.iobuilders.bank.application.exception.ApplicationException;
import com.iobuilders.bank.application.exception.InstanceNotFoundException;
import com.iobuilders.bank.application.ports.inbound.wallets.CreateTransferPort;
import com.iobuilders.bank.application.ports.outbound.persistence.TransferPersistencePort;
import com.iobuilders.bank.application.ports.outbound.persistence.WalletPersistencePort;
import com.iobuilders.bank.domain.entities.Transfer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateTransferUseCase implements CreateTransferPort {

	@NonNull
	private WalletPersistencePort walletPersistencePort;

	@NonNull
	private TransferPersistencePort transferPersistencePort;

	@Override
	@Transactional
	public void execute(final Transfer transfer) {
		try {
			runValidations(transfer);
			this.transferPersistencePort.persistTransfer(transfer);
			this.walletPersistencePort.addAmountToWallet(transfer.getDestinationWalletId(),
					transfer.getAmount());
			this.walletPersistencePort.withdrawAmountToWallet(transfer.getOriginWalletId(),
					transfer.getAmount());
		} catch (final InstanceNotFoundException exception) {
			log.error(exception.getMessage(), exception);
			throw new ApplicationException(exception.getErrorCode(), exception.getMessage());
		}
	}

	private void runValidations(final Transfer transfer) {

		if (transfer.getAmount().compareTo(0.0) < 0) {
			log.error("Error trying to create a new transfer, the amount is negative: {}", transfer);
			throw new ApplicationException(VALIDATION,
					"Error trying to create the transfer, the amount can not be negative");
		}

	}
}