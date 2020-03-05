package com.prokabaddi.scheduler.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prokabaddi.scheduler.dao.ScheduleMasterRepository;
import com.prokabaddi.scheduler.dao.TeamRepository;
import com.prokabaddi.scheduler.dao.VenueRepository;
import com.prokabaddi.scheduler.enums.SlotEnum;
import com.prokabaddi.scheduler.model.ScheduleMaster;
import com.prokabaddi.scheduler.model.Team;
import com.prokabaddi.scheduler.model.Venue;

@Service
public class ScheduleService {
	
	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	VenueRepository venueRepository;
	
	@Autowired
	ScheduleMasterRepository scheduleMasterRepository;

	public void generateSchedule(int noOfTeams, Date startingDate) 
	{
		List<Team> teamList = generateTeams(noOfTeams);
		Date date = startingDate;
		//Date nextAvailDate = date;
						for (int i = 0; i < teamList.size(); i++) {
							
							for (int j = 0; j < teamList.size(); j++) {
								if (i != j)
								{
									Team team1 = teamList.get(i);
									Team team2 = teamList.get(j);
									Date nextAvailDate = getNextAvailDate(date);
									while(!checkTeamScheduleAvailability(team1, team2, nextAvailDate))
									{
										//nextAvailDate = getTomorrowDate(nextAvailDate);
										nextAvailDate = getNextAvailDate(getTomorrowDate(nextAvailDate));
									}
									
										if(!checkIfSameMatchExists(team1, team2, nextAvailDate))
										{
											scheduleThisMatch(team1, team2, nextAvailDate);
										}
								}
								
							}
						}
		 
	}

	private Date getNextAvailDate(Date date) {
		
		while(checkIfSlotAvailOnDate(date) == false)
		{
			date = getTomorrowDate(date);
		}
		return date;
	}

	private Date getTomorrowDate(Date date) {

		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, 1);
		date = c.getTime();
		return date;
	}
	
	private Date getYesterdayDate(Date date) {

		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, -1);
		date = c.getTime();
		return date;
	}

	private void scheduleThisMatch(Team team1, Team team2, Date date) {

		List<ScheduleMaster> scheduleList1 = scheduleMasterRepository.findAllByMatchDate(date);
		
		ScheduleMaster scheduleMaster = new ScheduleMaster();
		scheduleMaster.setMatchDate(date);
		scheduleMaster.setTeam1(team1);
		scheduleMaster.setTeam2(team2);
		
		if(scheduleList1.isEmpty())
		{
			scheduleMaster.setSlotEnum(SlotEnum.SLOT_1);
		}
		else
		{
			scheduleMaster.setSlotEnum(SlotEnum.SLOT_2);
		}
		
		scheduleMasterRepository.save(scheduleMaster);
		
	}

	private boolean checkIfSameMatchExists(Team team1, Team team2, Date date) {
		
		Date todayDate = date;
		int flag = 0;
		List<ScheduleMaster> scheduleList1 = scheduleMasterRepository.findAll();
		for (ScheduleMaster schedule : scheduleList1) {
			if(schedule.getTeam1() == team1 && schedule.getTeam2() == team2)
			{
				flag = 1;
			}
		}
		if(flag == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}

	private boolean checkTeamScheduleAvailability(Team team1, Team team2, Date date) {
		
		Date todayDate = date;
		Date yesterdayDate = getYesterdayDate(todayDate);
		Date tomorrowDate = getTomorrowDate(todayDate);
		List<ScheduleMaster> scheduleList1 = scheduleMasterRepository.findAllByMatchDate(date);
		List<ScheduleMaster> scheduleList2 = scheduleMasterRepository.findAllByMatchDate(yesterdayDate);
		List<ScheduleMaster> scheduleList3 = scheduleMasterRepository.findAllByMatchDate(tomorrowDate);
		
		scheduleList1.addAll(scheduleList2);
		scheduleList1.addAll(scheduleList3);
		
		int flag = 0;
		
		for (ScheduleMaster schedule : scheduleList1) {
			if(schedule.getTeam1().equals(team1) || schedule.getTeam2().equals(team1) || schedule.getTeam1().equals(team2) || schedule.getTeam2().equals(team2))
			{
				flag = 1;
			}
		}
		
		if(flag > 0)
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}

	private boolean checkIfSlotAvailOnDate(Date date) {
		
		Date todayDate = date;
		List<ScheduleMaster> scheduleList1 = scheduleMasterRepository.findAllByMatchDate(todayDate);
		if(scheduleList1.size() > 1)
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}

	private List<Team> generateTeams(int noOfTeams) {
		List<Team> teamList = new ArrayList<Team>();
		
		scheduleMasterRepository.deleteAll();
		teamRepository.deleteAll();
		venueRepository.deleteAll();
		
		for(int i = 0; i < noOfTeams; i++)
		{
			Team team = new Team();
			Venue venue = new Venue();
			
			team.setTeamName("Team"+(i+1));
			team.setVenue(venueRepository.save(venue));
			teamRepository.save(team);
			
			teamList.add(team);
		}
		return teamList;
	}

	
	
}
