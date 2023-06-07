package com.iobuilders.bank.infra.rest.adapters;

import com.iobuilders.bank.application.ports.inbound.wallets.CreateDepositPort;
import com.iobuilders.bank.application.ports.inbound.wallets.CreateTransferPort;
import com.iobuilders.bank.application.ports.inbound.wallets.CreateWalletPort;
import com.iobuilders.bank.application.ports.inbound.wallets.FindWalletByIdPort;
import com.iobuilders.bank.domain.aggregate.Wallet;
import com.iobuilders.bank.infra.rest.api.WalletsApi;
import com.iobuilders.bank.infra.rest.api.model.DepositRequestDTO;
import com.iobuilders.bank.infra.rest.api.model.TransferRequestDTO;
import com.iobuilders.bank.infra.rest.api.model.WalletCreationRequestDTO;
import com.iobuilders.bank.infra.rest.api.model.WalletInformationDTO;
import com.iobuilders.bank.infra.rest.mappers.WalletRestMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WalletsRestController implements WalletsApi {

	@NonNull
	private WalletRestMapper walletRestMapper;

	@NonNull
	private CreateWalletPort createWalletPort;

	@NonNull
	private CreateDepositPort createDepositPort;

	@NonNull
	private CreateTransferPort createTransferPort;

	@NonNull
	private FindWalletByIdPort findWalletByIdPort;

	@Override
	public ResponseEntity<WalletInformationDTO> createWallet(
			final WalletCreationRequestDTO walletCreationRequestDTO) {
		final Wallet wallet = this.createWalletPort.execute(
				this.walletRestMapper.toWallet(walletCreationRequestDTO));
		return ResponseEntity.ok(this.walletRestMapper.toWalletInformationDto(wallet));
	}

	@Override
	public ResponseEntity<WalletInformationDTO> findWalletById(final Long walletId) {
		final Wallet wallet = this.findWalletByIdPort.execute(walletId);
		return ResponseEntity.ok(this.walletRestMapper.toWalletInformationDto(wallet));
	}

	@Override
	public ResponseEntity<Void> createDeposit(final DepositRequestDTO depositRequestDto) {
		this.createDepositPort.execute(this.walletRestMapper.toDeposit(depositRequestDto));
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> createTransference(final TransferRequestDTO transferRequestDto) {
		this.createTransferPort.execute(this.walletRestMapper.toTransfer(transferRequestDto));
		return ResponseEntity.ok().build();
	}

}