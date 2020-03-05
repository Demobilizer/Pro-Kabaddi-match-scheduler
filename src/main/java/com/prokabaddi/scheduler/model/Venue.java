package com.prokabaddi.scheduler.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

@Component
@Entity
public class Venue {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int venueId;
	
	

	public int getVenueId() {
		return venueId;
	}

	public void setVenueId(int venueId) {
		this.venueId = venueId;
	}

	@Override
	public String toString() {
		return "Venue [venueId=" + venueId +"]";
	}
	
	
}
