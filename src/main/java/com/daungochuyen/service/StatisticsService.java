package com.daungochuyen.service;

import java.util.List;

import com.daungochuyen.dto.StatisticsMonthOfYear;
import com.daungochuyen.dto.StatisticsOfYearDTO;

public interface StatisticsService {
	
	/**
	 * Statistics of year
	 * @param year
	 * @return
	 */
	List<StatisticsOfYearDTO> statisticsOfYear(int year);
	
	/**
	 * Statistics month of year
	 * @return
	 */
	List<StatisticsMonthOfYear> statisticsMonthOfYear(int year, int month);
	
}
