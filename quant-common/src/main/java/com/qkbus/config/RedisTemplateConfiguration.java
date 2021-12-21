package com.qkbus.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;

/**
 * RedisTemplate 配置
 *
 * @author Chill
 */
@EnableCaching
@Configuration
@AutoConfigureBefore(RedisAutoConfiguration.class)
/*
* @ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
* */
public class RedisTemplateConfiguration {

	/**
	 * 默认的序列化方式
	 * 使用JSON
	 *
	 * @return RedisSerializer
	 */
	private RedisSerializer<Object> defaultRedisSerializer() {
		return new GenericJackson2JsonRedisSerializer();
	}

	/**
	 * value 值 序列化
	 *
	 * @return RedisSerializer
	 */
	@Bean
	@ConditionalOnMissingBean(RedisSerializer.class)
	public RedisSerializer<Object> redisSerializer() {
		return defaultRedisSerializer();
	}

	@Bean(name = "redisTemplate")
	@ConditionalOnMissingBean(RedisTemplate.class)
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory, RedisSerializer<Object> redisSerializer) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		RedisKeySerializer redisKeySerializer = new RedisKeySerializer();
		// key 序列化
		redisTemplate.setKeySerializer(redisKeySerializer);
		redisTemplate.setHashKeySerializer(redisKeySerializer);
		// value 序列化
		redisTemplate.setValueSerializer(redisSerializer);
		redisTemplate.setHashValueSerializer(redisSerializer);
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
			.entryTtl(Duration.ofHours(1));
		return RedisCacheManager
			.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
			.cacheDefaults(redisCacheConfiguration).build();
	}
}
class RedisKeySerializer implements RedisSerializer<Object> {
	private final Charset charset;
	private final ConversionService converter;

	public RedisKeySerializer() {
		this(StandardCharsets.UTF_8);
	}

	public RedisKeySerializer(Charset charset) {
		Objects.requireNonNull(charset, "Charset must not be null");
		this.charset = charset;
		this.converter = DefaultConversionService.getSharedInstance();
	}

	@Override
	public Object deserialize(byte[] bytes) {
		// redis keys 会用到反序列化
		if (bytes == null) {
			return null;
		}
		return new String(bytes, charset);
	}

	@Override
	@Nullable
	public byte[] serialize(Object object) {
		Objects.requireNonNull(object, "redis key is null");
		String key;
		if (object instanceof SimpleKey) {
			key = "";
		} else if (object instanceof String) {
			key = (String) object;
		} else {
			key = converter.convert(object, String.class);
		}
		return key.getBytes(this.charset);
	}

}