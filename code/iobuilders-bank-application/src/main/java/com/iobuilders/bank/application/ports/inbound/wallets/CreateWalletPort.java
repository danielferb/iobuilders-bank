package com.iobuilders.bank.application.ports.inbound.wallets;

import com.iobuilders.bank.domain.aggregate.Wallet;

public interface CreateWalletPort {

	/**
	 * Creates a wallet with the information received.
	 *
	 * @param wallet The wallet information to create a new one.
	 * @return The wallet created.
	 */
	Wallet execute(Wallet wallet);

}