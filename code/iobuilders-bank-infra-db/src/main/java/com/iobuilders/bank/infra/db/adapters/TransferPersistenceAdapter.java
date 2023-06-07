package com.iobuilders.bank.infra.db.adapters;

import com.iobuilders.bank.application.ports.outbound.persistence.TransferPersistencePort;
import com.iobuilders.bank.domain.entities.Transfer;
import com.iobuilders.bank.infra.db.mappers.TransferMapper;
import com.iobuilders.bank.infra.db.repositories.TransferRepository;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferPersistenceAdapter implements TransferPersistencePort {

	@NonNull
	private TransferMapper transferMapper;

	@NonNull
	private TransferRepository transferRepository;

	@Override
	public void persistTransfer(final Transfer transfer) {
		this.transferRepository.persistTransfer(this.transferMapper.toTransferEntity(transfer));
	}

	@Override
	public List<Transfer> findByWalletId(final Long walletId) {
		return this.transferMapper.toTransferList(
				this.transferRepository.findTransfersByWalletId(walletId));
	}

}