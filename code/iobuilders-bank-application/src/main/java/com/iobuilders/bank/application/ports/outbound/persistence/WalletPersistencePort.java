package com.iobuilders.bank.application.ports.outbound.persistence;

import com.iobuilders.bank.application.exception.InstanceNotFoundException;
import com.iobuilders.bank.domain.aggregate.Wallet;

public interface WalletPersistencePort {

	/**
	 * Adds the amount indicated to the wallet with the identifier received.
	 *
	 * @param walletId Identifier of the wallet to add the money to.
	 * @param amount   Amount of money to add.
	 */
	void addAmountToWallet(Long walletId, Double amount) throws InstanceNotFoundException;

	/**
	 * Withdraws the amount indicated to the wallet with the identifier received.
	 *
	 * @param walletId Identifier of the wallet to withdraw the money to.
	 * @param amount   Amount of money to withdraw.
	 */
	void withdrawAmountToWallet(Long walletId, Double amount) throws InstanceNotFoundException;

	/**
	 * Persist a new wallet with the information received.
	 *
	 * @param wallet Information of the wallet to persist.
	 * @return The wallet persisted.
	 */
	Wallet persistWallet(Wallet wallet);

	/**
	 * Finds a wallet by its identifier.
	 *
	 * @param walletId Identifier of the wallet to search by.
	 * @return The wallet found.
	 */
	Wallet findWalletById(Long walletId) throws InstanceNotFoundException;

}