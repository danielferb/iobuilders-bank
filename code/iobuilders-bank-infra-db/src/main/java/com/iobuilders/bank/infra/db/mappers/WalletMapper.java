package com.iobuilders.bank.infra.db.mappers;

import com.iobuilders.bank.domain.aggregate.Wallet;
import com.iobuilders.bank.infra.db.entities.WalletEntity;
import org.mapstruct.Mapper;

@Mapper
public interface WalletMapper {

	Wallet toWallet(WalletEntity walletEntity);

	WalletEntity toWalletEntity(Wallet wallet);

}