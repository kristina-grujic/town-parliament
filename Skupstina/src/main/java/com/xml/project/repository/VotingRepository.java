package com.xml.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xml.project.model.Published;
import com.xml.project.model.Voting;

public interface VotingRepository extends JpaRepository<Voting, Long> {

	public List<Voting> findByName(String name);

	public List<Voting> findByPublished(Published p);
}
