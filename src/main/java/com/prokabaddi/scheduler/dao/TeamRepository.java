package com.prokabaddi.scheduler.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prokabaddi.scheduler.model.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {

}
