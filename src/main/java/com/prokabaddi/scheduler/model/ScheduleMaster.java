package com.prokabaddi.scheduler.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

import com.prokabaddi.scheduler.enums.SlotEnum;

@Component
@Entity
public class ScheduleMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int scheduleId;
	
	private Date matchDate;
	
	@OneToOne
	@JoinColumn(name = "team1Id")
	private Team team1;
	
	@OneToOne
	@JoinColumn(name = "team2Id")
	private Team team2;
	
	@Enumerated(EnumType.STRING)
	private SlotEnum slotEnum;

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Date getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}

	public Team getTeam1() {
		return team1;
	}

	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return team2;
	}

	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

	public SlotEnum getSlotEnum() {
		return slotEnum;
	}

	public void setSlotEnum(SlotEnum slotEnum) {
		this.slotEnum = slotEnum;
	}

	@Override
	public String toString() {
		return "ScheduleMaster [scheduleId=" + scheduleId + ", matchDate=" + matchDate + ", team1=" + team1 + ", team2="
				+ team2 + ", slotEnum=" + slotEnum + "]";
	}
	
	
}
