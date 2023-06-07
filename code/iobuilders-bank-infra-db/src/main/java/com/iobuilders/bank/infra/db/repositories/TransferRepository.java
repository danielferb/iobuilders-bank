package com.iobuilders.bank.infra.db.repositories;

import static java.util.List.of;

import com.iobuilders.bank.infra.db.entities.TransferEntity;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class TransferRepository {

	private final ConcurrentHashMap<Long, LinkedList<TransferEntity>> transfers = new ConcurrentHashMap<>();

	public void persistTransfer(final TransferEntity transferEntity) {

		transferEntity.setCreatedAt(LocalDateTime.now());

		this.transfers.compute(transferEntity.getDestinationWalletId(), (walletId, transfers) -> {
			if (CollectionUtils.isEmpty(transfers)) {
				return new LinkedList<>(of(transferEntity));
			} else {
				transfers.add(transferEntity);
				return transfers;
			}
		});

		if (transferEntity.getOriginWalletId() != null) {
			this.transfers.compute(transferEntity.getOriginWalletId(), (walletId, transfers) -> {
				if (CollectionUtils.isEmpty(transfers)) {
					return new LinkedList<>(of(negateTransferAmount(transferEntity)));
				} else {
					transfers.add(negateTransferAmount(transferEntity));
					return transfers;
				}
			});
		}
	}

	private TransferEntity negateTransferAmount(final TransferEntity origin) {
		return TransferEntity.builder().originWalletId(origin.getOriginWalletId())
				.destinationWalletId(origin.getDestinationWalletId()).amount(-origin.getAmount()).build();
	}

	public List<TransferEntity> findTransfersByWalletId(final Long walletId) {
		return this.transfers.get(walletId);
	}

}