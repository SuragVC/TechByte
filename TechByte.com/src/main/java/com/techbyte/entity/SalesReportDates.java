package com.techbyte.entity;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class SalesReportDates {
	private LocalDate startDate;
	private LocalDate endDate;
}
