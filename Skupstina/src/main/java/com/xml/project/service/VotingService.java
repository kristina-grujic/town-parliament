package com.xml.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xml.project.model.Voting;
import com.xml.project.repository.VotingRepository;

@Service
public class VotingService {

	@Autowired
	private VotingRepository repository;

	public Voting findOne(Long id) {
		return repository.findOne(id);
	}

	public List<Voting> findAll() {
		return repository.findAll();
	}

	public Page<Voting> findAll(Pageable page) {
		return repository.findAll(page);
	}

	public Voting save(Voting vote) {
		return repository.save(vote);
	}

	public void remove(Long id) {
		repository.delete(id);
	}

	public List<Voting> findByName(String name) {
		return repository.findByName(name);
	}
}
