package com.daungochuyen.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daungochuyen.entity.Cart;

/**
 * Cart repository
 * @author ADMIN
 *
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	List<Cart> findByIdUser(Long idUser);
	List<Cart>  findByIdProduct(Long idProduct);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM cart WHERE id_product = ?1", nativeQuery = true)
	void deleteByIdProduct(Long idProduct);
}
