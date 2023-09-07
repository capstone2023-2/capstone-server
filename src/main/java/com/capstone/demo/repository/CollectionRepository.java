package com.capstone.demo.repository;

import com.capstone.demo.model.domain.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
}
