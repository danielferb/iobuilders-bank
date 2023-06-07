package com.iobuilders.bank.application.ports.inbound.wallets;

import com.iobuilders.bank.domain.entities.Deposit;

public interface CreateDepositPort {

	/**
	 * Creates a new deposit to a wallet.
	 *
	 * @param deposit The deposit information with the wallet id to deposit the money to.
	 */
	void execute(Deposit deposit);

}
