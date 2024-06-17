package com.mysite.sbb.email;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mysite.sbb.user.SiteUser;

public interface RedisRepository extends CrudRepository<RedisUser, String> {
	
}
	