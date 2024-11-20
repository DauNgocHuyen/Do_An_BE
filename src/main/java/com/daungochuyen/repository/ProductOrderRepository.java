package com.daungochuyen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.daungochuyen.entity.ProductOrder;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long>{
	
	@Query(value = "select * from product_order where order_detail_id = :id", nativeQuery = true)
	List<ProductOrder> findAllByOrderId(@Param("id") Long id);
	
}
