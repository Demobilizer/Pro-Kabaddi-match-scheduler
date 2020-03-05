package com.prokabaddi.scheduler.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prokabaddi.scheduler.model.ScheduleMaster;

public interface ScheduleMasterRepository extends JpaRepository<ScheduleMaster, Integer>{

	List<ScheduleMaster> findAllByMatchDate(Date date);

}
