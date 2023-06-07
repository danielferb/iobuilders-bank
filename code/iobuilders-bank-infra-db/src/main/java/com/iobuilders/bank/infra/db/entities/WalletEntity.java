package com.iobuilders.bank.infra.db.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletEntity {

	private Long id;

	private Long userId;

	private Double balance;

}