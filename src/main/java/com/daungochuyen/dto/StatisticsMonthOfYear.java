package com.daungochuyen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticsMonthOfYear {
	private int date;
	
	private long total;
}
