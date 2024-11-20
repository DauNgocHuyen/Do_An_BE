package com.daungochuyen.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daungochuyen.dto.StatisticsMonthOfYear;
import com.daungochuyen.dto.StatisticsOfYearDTO;
import com.daungochuyen.repository.OrderRepository;
import com.daungochuyen.service.StatisticsService;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private OrderRepository orderDetailRepository;
	
	/**
	 * Statistics of year
	 */
	@Override
	public List<StatisticsOfYearDTO> statisticsOfYear(int year) {
		List<StatisticsOfYearDTO> result = new ArrayList<>();
		
		List<StatisticsOfYearDTO> statisticsOfYearDTOs = orderDetailRepository.statisticsOfYear(year);
		for(int i = 1 ; i <= 12 ; i++) {
			boolean isOk = false;
			for(StatisticsOfYearDTO dto : statisticsOfYearDTOs) {
				if(dto.getMonth() == i) {
					result.add(dto);
					isOk = true;
					break;
				}
			}
			
			if(!isOk) {
				result.add(new StatisticsOfYearDTO(i, 0));
			}
		}
		
		return result;
	}

	/**
	 * Statistic month of the year
	 */
	@Override
	public List<StatisticsMonthOfYear> statisticsMonthOfYear(int year, int month) {
		List<StatisticsMonthOfYear> result = new ArrayList<>();
		
		List<StatisticsMonthOfYear> statisticsMonthOfYears = orderDetailRepository.statisticsMonthOfYear(year, month);
		for(int i = 1 ; i <= 31 ; i++) {
			boolean isOk = false;
			for(StatisticsMonthOfYear dto : statisticsMonthOfYears) {
				if(dto.getDate() == i) {
					result.add(dto);
					isOk = true;
					break;
				}
			}
			
			if(!isOk) {
				result.add(new StatisticsMonthOfYear(i, 0));
			}
		}
		
		return result;
	}

}
