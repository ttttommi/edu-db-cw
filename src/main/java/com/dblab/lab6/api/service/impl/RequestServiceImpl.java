package com.dblab.lab6.api.service.impl;

import com.dblab.lab6.api.dto.RequestDto;
import com.dblab.lab6.api.entity.Datafile;
import com.dblab.lab6.api.entity.Request;
import com.dblab.lab6.api.entity.User;
import com.dblab.lab6.api.repository.PermissionRepository;
import com.dblab.lab6.api.repository.RequestRepository;
import com.dblab.lab6.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    private final AuthService authService;
    private final DataService dataService;
    private final RequestRepository requestRepository;
    private final PermissionRepository permissionRepository;


    @Autowired
    public RequestServiceImpl(AuthService authService,
                              DataService dataService,
                              RequestRepository requestRepository,
                              PermissionRepository permissionRepository) {
        this.authService = authService;
        this.dataService = dataService;
        this.requestRepository = requestRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<Request> findAll() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return requestRepository.findAllByOrderByRequestDateDesc();
        }

        return requestRepository.findByUserOrderByRequestDateDesc(authService.findByUsername(authentication.getName()));
    }

    @Override
    public Request findById(Long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException(requestId));
    }

    @Override
    public Request createRequest(RequestDto requestDto) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = authService.findByUsername(username);

        if (!user.getPermissions().contains(permissionRepository.findByType("UPLOAD").orElse(null))) {
            throw new NoPermissionException("upload");
        }

        Request request = Request.builder()
                .message(requestDto.getMessage())
                .requestDate(LocalDate.now())
                .user(user)
                .datafile(null)
                .name(requestDto.getName())
                .content(requestDto.getContent())
                .description(requestDto.getDescription())
                .format(requestDto.getFormat())
                .build();

        return requestRepository.save(request);
    }

    @Override
    public Request editRequest(Long dataId, RequestDto requestDto) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = authService.findByUsername(username);

        if (!user.getPermissions().contains(permissionRepository.findByType("EDIT").orElse(null))) {
            throw new NoPermissionException("edit");
        }

        Request request = Request.builder()
                .message(requestDto.getMessage())
                .requestDate(LocalDate.now())
                .user(user)
                .datafile(dataService.findById(dataId))
                .name(requestDto.getName())
                .content(requestDto.getContent())
                .description(requestDto.getDescription())
                .format(requestDto.getFormat())
                .build();

        return requestRepository.save(request);
    }

    @Override
    public Datafile approveRequest(Long requestId) {

        Request request = findById(requestId);

        Datafile datafile;
        if (request.getDatafile() == null) {

            datafile = Datafile.builder()
                    .name(request.getName())
                    .content(request.getContent())
                    .description(request.getDescription())
                    .format(request.getFormat())
                    .lastUpdate(LocalDate.now()).build();
        } else {

            datafile = request.getDatafile();
            if (request.getName() != null) datafile.setName(request.getName());
            if (request.getContent() != null) datafile.setContent(request.getContent());
            if (request.getDescription() != null) datafile.setDescription(request.getDescription());
            if (request.getFormat() != null) datafile.setFormat(request.getFormat());
            datafile.setLastUpdate(LocalDate.now());
        }

        requestRepository.deleteById(requestId);

        return dataService.save(datafile);
    }

    @Override
    public void denyRequest(Long requestId) {
        findById(requestId);
        requestRepository.deleteById(requestId);
    }
}
