package com.iobuilders.bank.infra.rest.mappers;

import com.iobuilders.bank.domain.aggregate.Wallet;
import com.iobuilders.bank.domain.entities.Deposit;
import com.iobuilders.bank.domain.entities.Transfer;
import com.iobuilders.bank.infra.rest.api.model.DepositRequestDTO;
import com.iobuilders.bank.infra.rest.api.model.TransferRequestDTO;
import com.iobuilders.bank.infra.rest.api.model.WalletCreationRequestDTO;
import com.iobuilders.bank.infra.rest.api.model.WalletInformationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface WalletRestMapper {

	@Mapping(source = "ownerId", target = "userId")
	Wallet toWallet(WalletCreationRequestDTO creationRequestDto);

	@Mapping(source = "userId", target = "ownerId")
	@Mapping(source = "transfers", target = "movements")
	WalletInformationDTO toWalletInformationDto(Wallet wallet);

	Deposit toDeposit(DepositRequestDTO depositRequestDto);

	Transfer toTransfer(TransferRequestDTO transferRequestDto);

}