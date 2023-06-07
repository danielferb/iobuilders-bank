package com.iobuilders.bank.infra.db.entities;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferEntity {

	private Long originWalletId;

	private Long destinationWalletId;

	private Double amount;

	private LocalDateTime createdAt;

}