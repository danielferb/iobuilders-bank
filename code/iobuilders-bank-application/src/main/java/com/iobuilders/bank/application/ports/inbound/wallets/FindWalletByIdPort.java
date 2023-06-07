package com.iobuilders.bank.application.ports.inbound.wallets;

import com.iobuilders.bank.domain.aggregate.Wallet;

public interface FindWalletByIdPort {

	/**
	 * Finds a wallet by its identifier.
	 *
	 * @param walletId Unique identifier of the wallet to search by.
	 * @return The wallet found.
	 */
	Wallet execute(Long walletId);

}