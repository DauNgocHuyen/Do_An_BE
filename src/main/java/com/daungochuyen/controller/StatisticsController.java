package com.daungochuyen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daungochuyen.service.StatisticsService;

@RestController
@RequestMapping("/api/v1/statistics")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StatisticsController {
	
	@Autowired
	private StatisticsService statisticsService;
	
	/**
	 * Get statistics of the year
	 * @param year
	 * @return
	 */
	@GetMapping("/{year}")
	public ResponseEntity<?> statisticsOfYear(@PathVariable("year") int year) {
		return ResponseEntity.ok(statisticsService.statisticsOfYear(year));
	}
	
	/**
	 * Get statistics month of the year
	 * @param year
	 * @return
	 */
	@GetMapping("/{year}/{month}")
	public ResponseEntity<?> statisticsMonthOfYear(@PathVariable("year") int year, @PathVariable("month") int month) {
		return ResponseEntity.ok(statisticsService.statisticsMonthOfYear(year, month));
	}

}
