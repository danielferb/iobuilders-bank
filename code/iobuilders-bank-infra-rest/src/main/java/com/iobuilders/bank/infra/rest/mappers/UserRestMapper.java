package com.iobuilders.bank.infra.rest.mappers;

import com.iobuilders.bank.domain.aggregate.User;
import com.iobuilders.bank.infra.rest.api.model.UserInformationDTO;
import com.iobuilders.bank.infra.rest.api.model.UserSignUpRequestDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserRestMapper {

	User toUser(UserSignUpRequestDTO signUpRequestDto);

	UserInformationDTO toUserInformationDto(User user);

}