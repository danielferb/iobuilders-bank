package com.iobuilders.bank.infra.db.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.iobuilders.bank.infra.db.entities.TransferEntity;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TransferRepositoryTest {

	@InjectMocks
	private TransferRepository transferRepository;

	@Test
	public void persistTransferSuccessfully() {

		final TransferEntity transfer = TransferEntity.builder().originWalletId(1L)
				.destinationWalletId(2L).amount(100.00).build();
		final TransferEntity secondTransfer = TransferEntity.builder().originWalletId(2L)
				.destinationWalletId(1L).amount(10.00).build();

		this.transferRepository.persistTransfer(transfer);
		this.transferRepository.persistTransfer(secondTransfer);

		final List<TransferEntity> transfersFound = this.transferRepository.findTransfersByWalletId(1L);
		assertThat(transfersFound).hasSize(2);
		assertThat(transfersFound.get(0).getAmount()).isEqualTo(-100.00);
		assertThat(transfersFound.get(1).getAmount()).isEqualTo(10.00);

		final List<TransferEntity> secondTransfersFound = this.transferRepository.findTransfersByWalletId(
				2L);
		assertThat(secondTransfersFound).hasSize(2);
		assertThat(secondTransfersFound.get(0).getAmount()).isEqualTo(100.00);
		assertThat(secondTransfersFound.get(1).getAmount()).isEqualTo(-10.00);
	}

}