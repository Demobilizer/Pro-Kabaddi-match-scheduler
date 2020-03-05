package com.prokabaddi.scheduler.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prokabaddi.scheduler.model.Venue;

public interface VenueRepository extends JpaRepository<Venue, Integer> {

}
