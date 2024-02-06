package com.sns.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sns.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	// null or 채워져 있거나(UserEntity 단건)
	public UserEntity findByLoginId(String loginId);
	
	public UserEntity findByLoginIdAndPassword(String loginId, String password);
}