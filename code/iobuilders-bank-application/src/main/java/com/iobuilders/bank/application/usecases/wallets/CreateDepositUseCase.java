package com.iobuilders.bank.application.usecases.wallets;

import static com.iobuilders.bank.application.exception.Error.VALIDATION;

import com.iobuilders.bank.application.exception.ApplicationException;
import com.iobuilders.bank.application.exception.InstanceNotFoundException;
import com.iobuilders.bank.application.ports.inbound.wallets.CreateDepositPort;
import com.iobuilders.bank.application.ports.outbound.persistence.TransferPersistencePort;
import com.iobuilders.bank.application.ports.outbound.persistence.WalletPersistencePort;
import com.iobuilders.bank.domain.entities.Deposit;
import com.iobuilders.bank.domain.entities.Transfer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateDepositUseCase implements CreateDepositPort {

	@NonNull
	private WalletPersistencePort walletPersistencePort;

	@NonNull
	private TransferPersistencePort transferPersistencePort;

	@Override
	public void execute(final Deposit deposit) {
		try {
			runValidations(deposit);
			this.walletPersistencePort.addAmountToWallet(deposit.getDestinationWalletId(),
					deposit.getAmount());
			this.transferPersistencePort.persistTransfer(Transfer.builder().destinationWalletId(
					deposit.getDestinationWalletId()).amount(deposit.getAmount()).build());
		} catch (final InstanceNotFoundException exception) {
			log.error(exception.getMessage(), exception);
			throw new ApplicationException(exception.getErrorCode(), exception.getMessage());
		}
	}

	private void runValidations(final Deposit deposit) {

		if (deposit.getAmount().compareTo(0.0) < 0) {
			log.error("Error trying to create a new deposit, the amount is negative: {}", deposit);
			throw new ApplicationException(VALIDATION,
					"Error trying to create the deposit, the amount can not be negative");
		}

	}

}