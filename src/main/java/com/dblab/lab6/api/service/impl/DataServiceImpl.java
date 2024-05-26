package com.dblab.lab6.api.service.impl;

import com.dblab.lab6.api.dto.DatafileDto;
import com.dblab.lab6.api.entity.Datafile;
import com.dblab.lab6.api.repository.DataRepository;
import com.dblab.lab6.api.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    private final DataRepository dataRepository;

    @Autowired
    public DataServiceImpl(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Override
    public Datafile findById(Long id) {
        return dataRepository.findById(id).orElse(null);
    }

    @Override
    public List<Datafile> findAll() {
        return dataRepository.findAll();
    }

    @Override
    public Datafile create(DatafileDto datafileDto) {

        Datafile datafile = Datafile.builder()
                .name(datafileDto.getName())
                .content(datafileDto.getContent())
                .description(datafileDto.getDescription())
                .format(datafileDto.getFormat())
                .lastUpdate(LocalDate.now())
                .build();

        return dataRepository.save(datafile);
    }

    @Override
    public Datafile save(Datafile datafile) {
        return dataRepository.save(datafile);
    }
}
