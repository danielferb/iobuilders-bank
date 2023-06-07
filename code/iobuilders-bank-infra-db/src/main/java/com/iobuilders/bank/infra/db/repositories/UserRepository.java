package com.iobuilders.bank.infra.db.repositories;

import static com.iobuilders.bank.application.exception.Error.INSTANCE_NOT_FOUND;

import com.iobuilders.bank.application.exception.InstanceNotFoundException;
import com.iobuilders.bank.infra.db.entities.UserEntity;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	private final AtomicLong counter = new AtomicLong(0L);

	private final ConcurrentHashMap<Long, UserEntity> users = new ConcurrentHashMap<>();

	public UserEntity persistUser(final UserEntity userEntity) {
		userEntity.setId(this.counter.addAndGet(1));
		this.users.put(userEntity.getId(), userEntity);
		return userEntity;
	}

	public UserEntity findById(final Long userId) throws InstanceNotFoundException {

		final UserEntity userEntity = this.users.get(userId);

		if (userEntity == null) {
			throw new InstanceNotFoundException(INSTANCE_NOT_FOUND,
					StringUtils.join("The user with ID ", userId, " could not be found"));
		}

		return userEntity;
	}

}