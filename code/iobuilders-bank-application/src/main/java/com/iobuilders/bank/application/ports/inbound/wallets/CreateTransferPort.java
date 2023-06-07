package com.iobuilders.bank.application.ports.inbound.wallets;

import com.iobuilders.bank.domain.entities.Transfer;

public interface CreateTransferPort {

	/**
	 * Creates a new transfer between two wallets.
	 *
	 * @param transfer The transfer information with the wallets ids and the amount involved in.
	 */
	void execute(Transfer transfer);

}
