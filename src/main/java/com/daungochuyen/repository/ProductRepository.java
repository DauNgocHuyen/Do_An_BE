package com.daungochuyen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.daungochuyen.entity.Product;

/**
 * Product repository
 * @author ADMIN
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	
	@Query(value = "select * from product where category_id = :id", nativeQuery = true)
	List<Product> findAllByCategoryIds(@Param("id") Long id);
	
	@Query(value = "select * from product where name like %:name%", nativeQuery = true)
	List<Product> findByName(@Param("name") String name);
	
	@Query(value = "select * from product where deleted = 0 order by id desc limit 6", nativeQuery = true)
	List<Product> findNewProducts();
	
}
