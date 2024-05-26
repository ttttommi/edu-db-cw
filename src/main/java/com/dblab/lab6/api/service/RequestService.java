package com.dblab.lab6.api.service;

import com.dblab.lab6.api.dto.RequestDto;
import com.dblab.lab6.api.entity.Datafile;
import com.dblab.lab6.api.entity.Request;

import java.util.List;

public interface RequestService {

    List<Request> findAll();

    Request findById(Long requestId);

    Request createRequest(RequestDto requestDto);

    Request editRequest(Long dataId, RequestDto requestDto);

    Datafile approveRequest(Long requestId);

    void denyRequest(Long requestId);
}
