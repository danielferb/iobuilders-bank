package com.iobuilders.bank.domain.aggregate;

import com.iobuilders.bank.domain.entities.Transfer;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

	private Long id;

	private Long userId;

	private Double balance;

	private List<Transfer> transfers;

}