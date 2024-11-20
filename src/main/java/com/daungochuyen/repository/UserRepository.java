package com.daungochuyen.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daungochuyen.entity.User;

/**
 * User repository
 * @author ADMIN
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	
	@Query(value = "select * from user where role =?1", nativeQuery = true)
	List<User> findByRole(String role);
	
	@Query(value = "select count(1) from user where username =?1", nativeQuery = true)
	int checkEmailExist(String email);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE user SET role = ?1 WHERE id = ?2 ", nativeQuery = true)
	public Integer addSeller(String role , Integer accountId);
}
