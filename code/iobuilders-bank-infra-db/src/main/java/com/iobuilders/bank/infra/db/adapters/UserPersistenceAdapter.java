package com.iobuilders.bank.infra.db.adapters;

import com.iobuilders.bank.application.exception.InstanceNotFoundException;
import com.iobuilders.bank.application.ports.outbound.persistence.UserPersistencePort;
import com.iobuilders.bank.domain.aggregate.User;
import com.iobuilders.bank.infra.db.entities.UserEntity;
import com.iobuilders.bank.infra.db.mappers.UserMapper;
import com.iobuilders.bank.infra.db.repositories.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

	@NonNull
	private UserMapper userMapper;

	@NonNull
	private UserRepository userRepository;

	@Override
	public User persistUser(final User user) {
		final UserEntity userEntity = this.userRepository.persistUser(
				this.userMapper.toUserEntity(user));
		return this.userMapper.toUser(userEntity);
	}

	@Override
	public User findUserById(final Long userId) throws InstanceNotFoundException {
		return this.userMapper.toUser(this.userRepository.findById(userId));
	}

}