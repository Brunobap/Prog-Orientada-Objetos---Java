package com.example.demo1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo1.models.Tutorial;


public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
  
  List<Tutorial> findByPublished(boolean published);
  List<Tutorial> findByTitleContaining(String title);


}