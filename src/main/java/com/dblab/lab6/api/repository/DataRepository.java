package com.dblab.lab6.api.repository;

import com.dblab.lab6.api.entity.Datafile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<Datafile, Long> {

}
