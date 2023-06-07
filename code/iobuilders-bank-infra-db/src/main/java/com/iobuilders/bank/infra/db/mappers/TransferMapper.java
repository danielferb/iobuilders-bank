package com.iobuilders.bank.infra.db.mappers;

import com.iobuilders.bank.domain.entities.Transfer;
import com.iobuilders.bank.infra.db.entities.TransferEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper
public interface TransferMapper {

	TransferEntity toTransferEntity(Transfer transfer);

	Transfer toTransfer(TransferEntity transferEntity);

	List<Transfer> toTransferList(List<TransferEntity> transferEntity);

}