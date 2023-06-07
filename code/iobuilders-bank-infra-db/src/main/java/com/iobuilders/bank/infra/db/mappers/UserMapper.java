package com.iobuilders.bank.infra.db.mappers;

import com.iobuilders.bank.domain.aggregate.User;
import com.iobuilders.bank.infra.db.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

	User toUser(UserEntity userEntity);

	UserEntity toUserEntity(User user);

}