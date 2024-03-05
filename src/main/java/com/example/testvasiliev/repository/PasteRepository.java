package com.example.testvasiliev.repository;

import com.example.testvasiliev.model.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasteRepository extends JpaRepository<Paste, Integer> {
}
