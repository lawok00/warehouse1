package com.warehause.warehause1.repository;

import com.warehause.warehause1.model.Importer;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface ImporterJpaRepository extends JpaRepository<Importer, Integer> {
}
