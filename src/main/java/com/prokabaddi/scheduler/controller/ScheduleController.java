package com.prokabaddi.scheduler.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.prokabaddi.scheduler.service.ScheduleService;

@RestController
public class ScheduleController {

	@Autowired
	ScheduleService scheduleService;
	
	@GetMapping("/schedule-matches/for-teams/{noOfTeams}")
	public String scheduleTeams(@PathVariable int noOfTeams)
	{
		
		Date startingDate = new Date();
		System.out.println("date-------"+startingDate);
		scheduleService.generateSchedule(noOfTeams, startingDate);
		return "Scheduling done, you can check it into database!";
	}
	
}
