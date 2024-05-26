package com.dblab.lab6.api.service;

import com.dblab.lab6.api.dto.DatafileDto;
import com.dblab.lab6.api.entity.Datafile;

import java.util.List;

public interface DataService {

    Datafile findById(Long id);

    List<Datafile> findAll();

    Datafile create(DatafileDto datafileDto);

    Datafile save(Datafile datafile);
}
