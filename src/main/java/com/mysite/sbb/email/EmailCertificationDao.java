package com.mysite.sbb.email;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class EmailCertificationDao {
	private final int LIMIT_TIME = 3 * 60;
	
	private final StringRedisTemplate stringRedisTemplate;
	
	
}
