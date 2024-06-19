package com.mysite.sbb.email;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

	private final RedisRepository repository;
	private final RedisTemplate redisTemplate;
	private final StringRedisTemplate stringRedisTemplate;

	@Transactional
	public RedisUser addUser(RedisUser user) {
		// save
		RedisUser save = repository.save(user);

		// find
		Optional<RedisUser> result = repository.findById(save.getId());

		// Handling
		// 해당 data 존재시 return.
		if (result.isPresent()) {
			return result.get();
		} else {
			throw new RuntimeException("Database has no Data");
		}
	}// save

	@Transactional(readOnly = true)
	public String getUserByUsername(String username) {
		String value = stringRedisTemplate.opsForValue().get(username);
//		Optional<RedisUser> result = repository.findByUsername(username);
		// Handling
		if (value != null || value != "") {
			return value;
		} else {
			return "Database has no Data";
		}

	}

	public String setRedis(String userId, String serial) {
		stringRedisTemplate.opsForValue().set(userId, serial, 3 * 60, TimeUnit.SECONDS);

		log.info("Temporay Password set : {}", redisTemplate.opsForValue().get(userId));

		return (String) redisTemplate.opsForValue().get(userId);
	}

	public String checkOtp(String id, String otp) {

		String target = id;

		if (stringRedisTemplate.hasKey(id)) {
			String value = stringRedisTemplate.opsForValue().get(target);

			if (value.equals(otp)) {
				log.info("OTP is Correct");
				return "SUCCESS";
			} else {
				return "FAIL";
			}
		} else {
			return "NO DATA";
		}
	}
}
