package com.dblab.lab6.api.repository;

import com.dblab.lab6.api.entity.Request;
import com.dblab.lab6.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByOrderByRequestDateDesc();

    List<Request> findByUserOrderByRequestDateDesc(User user);
}
