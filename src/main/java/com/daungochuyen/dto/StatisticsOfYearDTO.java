package com.daungochuyen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticsOfYearDTO {
	private int month;
	
	private long total;

}
