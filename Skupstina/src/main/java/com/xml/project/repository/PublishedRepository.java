package com.xml.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xml.project.model.Published;

public interface PublishedRepository extends JpaRepository<Published, Long> {

	Published findByXmlLink(String xmlLink);

}
