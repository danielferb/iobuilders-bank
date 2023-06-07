package com.iobuilders.bank.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

	private Long originWalletId;

	private Long destinationWalletId;

	private Double amount;

}