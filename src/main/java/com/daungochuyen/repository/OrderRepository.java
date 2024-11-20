package com.daungochuyen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.daungochuyen.dto.StatisticsMonthOfYear;
import com.daungochuyen.dto.StatisticsOfYearDTO;
import com.daungochuyen.entity.Order;

/**
 * Order detail repository
 * @author ADMIN
 *
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	@Query(value = "select * from order_detail where user_id = :id", nativeQuery = true)
	List<Order> findAllByUserIds(@Param("id") Long id);
	
	
	/**
	 * Statistics of year
	 * @param year
	 * @return
	 */
	@Query(value = "SELECT new com.daungochuyen.dto.StatisticsOfYearDTO(MONTH(od.orderDate), SUM(od.total)) "
			+ "FROM Order AS od "
			+ "WHERE YEAR(od.orderDate) = :year "
			+ "AND od.status = 1 "
			+ "GROUP BY MONTH(od.orderDate) "
			+ "ORDER BY MONTH(od.orderDate)")
	List<StatisticsOfYearDTO> statisticsOfYear(@Param("year") int year);
	
	
	/**
	 * Statistics month of year
	 * @return
	 */
	@Query(value = "SELECT new com.daungochuyen.dto.StatisticsMonthOfYear(DAY(od.orderDate), SUM(od.total)) "
			+ "FROM Order AS od "
			+ "WHERE YEAR(od.orderDate) = :year "
			+ "AND MONTH(od.orderDate) = :month "
			+ "AND od.status = 1 "
			+ "GROUP BY DATE(od.orderDate) "
			+ "ORDER BY DATE(od.orderDate)")
	List<StatisticsMonthOfYear> statisticsMonthOfYear(@Param("year") int year, @Param("month") int month);
	
}
