package com.iobuilders.bank.infra.db.adapters;

import com.iobuilders.bank.application.exception.InstanceNotFoundException;
import com.iobuilders.bank.application.ports.outbound.persistence.WalletPersistencePort;
import com.iobuilders.bank.domain.aggregate.Wallet;
import com.iobuilders.bank.infra.db.entities.WalletEntity;
import com.iobuilders.bank.infra.db.mappers.WalletMapper;
import com.iobuilders.bank.infra.db.repositories.WalletRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletPersistenceAdapter implements WalletPersistencePort {

	@NonNull
	private WalletMapper walletMapper;

	@NonNull
	private WalletRepository walletRepository;

	@Override
	public void addAmountToWallet(final Long walletId, final Double amount)
			throws InstanceNotFoundException {
		this.walletRepository.modifyAmountToWallet(walletId, amount, true);
	}

	@Override
	public void withdrawAmountToWallet(final Long walletId, final Double amount)
			throws InstanceNotFoundException {
		this.walletRepository.modifyAmountToWallet(walletId, amount, false);
	}

	@Override
	public Wallet persistWallet(final Wallet wallet) {
		final WalletEntity walletEntity = this.walletRepository.persistWallet(
				this.walletMapper.toWalletEntity(wallet));
		return this.walletMapper.toWallet(walletEntity);
	}

	@Override
	public Wallet findWalletById(final Long walletId) throws InstanceNotFoundException {
		return this.walletMapper.toWallet(this.walletRepository.findById(walletId));
	}
}
