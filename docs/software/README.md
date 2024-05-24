# Реалізація інформаційного та програмного забезпечення

## SQL-скрипт для створення на початкового наповнення бази даних

```sql
-- Postgresql

-- SCHEMA: public

DROP SCHEMA IF EXISTS public CASCADE;

CREATE SCHEMA IF NOT EXISTS public
    AUTHORIZATION postgres;

COMMENT ON SCHEMA public
    IS 'standard public schema';

GRANT USAGE ON SCHEMA public TO PUBLIC;

GRANT ALL ON SCHEMA public TO postgres;


-- Table: public.users

-- DROP TABLE IF EXISTS public.users CASCADE;

CREATE TABLE IF NOT EXISTS public.users
(
    id bigserial NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(255),
    password character varying(255) NOT NULL,
    second_name character varying(255),
    username character varying(255) NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_email_key UNIQUE (email),
    CONSTRAINT users_username_key UNIQUE (username)
)
    TABLESPACE pg_default;


-- Table: public.roles

-- DROP TABLE IF EXISTS public.roles CASCADE;

CREATE TABLE IF NOT EXISTS public.roles
(
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (id),
    CONSTRAINT roles_name_key UNIQUE (name)
)
    TABLESPACE pg_default;


-- Table: public.user_roles

-- DROP TABLE IF EXISTS public.user_roles;

CREATE TABLE IF NOT EXISTS public.user_roles
(
    role_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (role_id, user_id),
    CONSTRAINT fk_user_roles_role_id FOREIGN KEY (role_id)
    REFERENCES public.roles (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_user_id FOREIGN KEY (user_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE
)
    TABLESPACE pg_default;


-- Table: public.permissions

-- DROP TABLE IF EXISTS public.permissions CASCADE;

CREATE TABLE IF NOT EXISTS public.permissions
(
    id bigserial NOT NULL,
    type character varying(255) NOT NULL,
    CONSTRAINT permissions_pkey PRIMARY KEY (id),
    CONSTRAINT permissions_type_key UNIQUE (type)
)
    TABLESPACE pg_default;


-- Table: public.user_permissions

-- DROP TABLE IF EXISTS public.user_permissions;

CREATE TABLE IF NOT EXISTS public.user_permissions
(
    permission_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT user_permissions_pkey PRIMARY KEY (permission_id, user_id),
    CONSTRAINT fk_user_permissions_permission_id FOREIGN KEY (permission_id)
        REFERENCES public.permissions (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT fk_user_permissions_user_id FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)
    TABLESPACE pg_default;


-- Table: public.datafiles

-- DROP TABLE IF EXISTS public.datafiles CASCADE;

CREATE TABLE IF NOT EXISTS public.datafiles
(
    last_update date NOT NULL,
    id bigserial NOT NULL,
    content character varying(255) NOT NULL,
    description character varying(255),
    format character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    CONSTRAINT datafiles_pkey PRIMARY KEY (id)
)
    TABLESPACE pg_default;


-- Table: public.requests

-- DROP TABLE IF EXISTS public.requests CASCADE;

CREATE TABLE IF NOT EXISTS public.requests
(
    request_date date NOT NULL,
    datafile_id bigint,
    id bigserial NOT NULL,
    user_id bigint NOT NULL,
    content character varying(255) NOT NULL,
    description character varying(255),
    format character varying(255) NOT NULL,
    message character varying(255),
    name character varying(255) NOT NULL,
    CONSTRAINT requests_pkey PRIMARY KEY (id),
    CONSTRAINT fk_requests_user_id FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_requests_datafile_id FOREIGN KEY (datafile_id)
        REFERENCES public.datafiles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    TABLESPACE pg_default;


BEGIN;

-- Додавання першого адміна в таблицю users
-- login : admin / admin@example.com
-- password : unhacked123admin456password
INSERT INTO public.users (email, first_name, password, second_name, username)
VALUES ('admin@example.com', 'Kolya', '$2a$12$F7R/17QfquhPVLiJ5lQPfOXYau8oqbGWxkkw2x865KPM1u/KMqIIe', 'Tishchenko', 'admin');

-- Додавання ролей в таблицю roles
INSERT INTO public.roles (name)
VALUES ('ROLE_USER'), ('ROLE_ADMIN');

-- Додавання дозволів у таблицю roles
INSERT INTO public.permissions (type)
VALUES ('DOWNLOAD'), ('UPLOAD'), ('EDIT');

-- Видача ролей адміну
INSERT INTO public.user_roles (user_id, role_id)
VALUES (1, 1), (1, 2);

-- Видача дозволів адміну
INSERT INTO public.user_permissions (user_id, permission_id)
VALUES (1, 1), (1, 2), (1, 3);

COMMIT;
```

## RESTful сервіс для управління даними

### Контролери

#### DataController

```java
package com.dblab.opensourcedata.api.controller;

import com.dblab.opensourcedata.api.dto.DatafileDto;
import com.dblab.opensourcedata.api.dto.RequestDto;
import com.dblab.opensourcedata.api.entity.Datafile;
import com.dblab.opensourcedata.api.entity.Request;
import com.dblab.opensourcedata.api.service.DataService;
import com.dblab.opensourcedata.api.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {

    private final DataService dataService;
    private final RequestService requestService;

    @Autowired
    public DataController(DataService dataService, RequestService requestService) {
        this.dataService = dataService;
        this.requestService = requestService;
    }

    @GetMapping
    public ResponseEntity<List<Datafile>> getAllDatafiles() {
        return ResponseEntity.ok(dataService.findAll());
    }

    @GetMapping("/{dataId}")
    public ResponseEntity<Datafile> getDatafile(@PathVariable Long dataId) {
        return ResponseEntity.ok(dataService.findById(dataId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Datafile> createDatafile(@RequestBody DatafileDto datafileDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dataService.create(datafileDto));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/requests")
    public ResponseEntity<Request> createUploadRequest(@RequestBody RequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(requestService.createRequest(requestDto));
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/requests/{dataId}")
    public ResponseEntity<Request> createEditRequest(@PathVariable Long dataId, @RequestBody RequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(requestService.editRequest(dataId, requestDto));
    }

    @GetMapping("/requests")
    public ResponseEntity<List<Request>> getAllRequests() {
        return ResponseEntity.ok(requestService.findAll());
    }
}
```

#### AuthController

```java
package com.dblab.opensourcedata.api.controller;

import com.dblab.opensourcedata.api.dto.JwtResponse;
import com.dblab.opensourcedata.api.dto.SignInDto;
import com.dblab.opensourcedata.api.dto.SignUpDto;
import com.dblab.opensourcedata.api.dto.UserDto;
import com.dblab.opensourcedata.api.entity.User;
import com.dblab.opensourcedata.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInDto signInDto) {
        String jwt = authService.signIn(signInDto);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<User> getRegisterPage(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(signUpDto));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public ResponseEntity<User> getUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/user")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authService.update(authService.getCurrentUser(), userDto));
    }
}
```

#### AdminController

```java
package com.dblab.opensourcedata.api.controller;

import com.dblab.opensourcedata.api.dto.PermissionDto;
import com.dblab.opensourcedata.api.entity.Datafile;
import com.dblab.opensourcedata.api.entity.User;
import com.dblab.opensourcedata.api.service.AuthService;
import com.dblab.opensourcedata.api.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AuthService authService;
    private final RequestService requestService;

    @Autowired
    public AdminController(AuthService authService, RequestService requestService) {
        this.authService = authService;
        this.requestService = requestService;
    }

    @PostMapping("/requests/{requestId}")
    public ResponseEntity<Datafile> approveRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(requestService.approveRequest(requestId));
    }

    @DeleteMapping("/requests/{requestId}")
    public ResponseEntity<Void> denyRequest(@PathVariable Long requestId) {
        requestService.denyRequest(requestId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{username}/permissions")
    public ResponseEntity<User> addPermissions(@PathVariable String username,
                                              @RequestBody List<PermissionDto> permissions) {
        return ResponseEntity.ok(authService.addPermissions(authService.findByUsername(username), permissions));
    }

    @DeleteMapping("/{username}/permissions")
    public ResponseEntity<User> deletePermissions(@PathVariable String username,
                                              @RequestBody List<PermissionDto> permissions) {
        return ResponseEntity.ok(authService.deletePermissions(authService.findByUsername(username), permissions));
    }
}
```

### Сервіси

#### DataService

```java
package com.dblab.opensourcedata.api.service.impl;

import com.dblab.opensourcedata.api.dto.DatafileDto;
import com.dblab.opensourcedata.api.entity.Datafile;
import com.dblab.opensourcedata.api.repository.DataRepository;
import com.dblab.opensourcedata.api.service.DataService;
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
```

#### AuthService

```java
package com.dblab.opensourcedata.api.service.impl;

import com.dblab.opensourcedata.api.dto.PermissionDto;
import com.dblab.opensourcedata.api.dto.SignInDto;
import com.dblab.opensourcedata.api.dto.SignUpDto;
import com.dblab.opensourcedata.api.dto.UserDto;
import com.dblab.opensourcedata.api.entity.Permission;
import com.dblab.opensourcedata.api.entity.Role;
import com.dblab.opensourcedata.api.entity.User;
import com.dblab.opensourcedata.api.repository.PermissionRepository;
import com.dblab.opensourcedata.api.repository.RoleRepository;
import com.dblab.opensourcedata.api.repository.UserRepository;
import com.dblab.opensourcedata.api.security.jwt.JwtUtils;
import com.dblab.opensourcedata.api.service.AuthService;
import com.dblab.opensourcedata.api.service.UserAlreadyExistException;
import com.dblab.opensourcedata.api.service.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PermissionRepository permissionRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public String signIn(SignInDto signInDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInDto.getUsernameOrEmail(), signInDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtUtils.generateJwtToken(authentication);
    }

    @Override
    public User signUp(SignUpDto signUpDto) {

        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            throw new UserAlreadyExistException("username", signUpDto.getUsername());
        }

        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new UserAlreadyExistException("email", signUpDto.getEmail());
        }

        User user = User.builder()
                .username(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword())).build();

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER").orElse(null));
        user.setRoles(roles);

        Set<Permission> permissions = new HashSet<>();
        permissions.add(permissionRepository.findByType("DOWNLOAD").orElse(null));
        permissions.add(permissionRepository.findByType("UPLOAD").orElse(null));
        permissions.add(permissionRepository.findByType("EDIT").orElse(null));
        user.setPermissions(permissions);

        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByUsername(username);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public User update(User existingUser, UserDto userDto) {

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistException("email", userDto.getEmail());
        }

        if (userDto.getFirstName() != null) existingUser.setFirstName(userDto.getFirstName());
        if (userDto.getSecondName() != null) existingUser.setSecondName(userDto.getSecondName());
        if (userDto.getEmail() != null) existingUser.setEmail(userDto.getEmail());

        return userRepository.save(existingUser);
    }

    @Override
    public User addPermission(User existingUser, PermissionDto permissionDto) {

        Set<Permission> permissions = existingUser.getPermissions();
        Permission permission = permissionRepository.findByType(permissionDto.getType()).orElse(null);

        if (permissions.contains(permission)) return existingUser;

        permissions.add(permission);
        existingUser.setPermissions(permissions);

        return userRepository.save(existingUser);
    }

    @Override
    public User addPermissions(User existingUser, List<PermissionDto> permissionDtos) {

        User user = existingUser;
        for (PermissionDto permissionDto : permissionDtos) {
            user = addPermission(existingUser, permissionDto);
        }
        return user;
    }

    @Override
    public User deletePermission(User user, PermissionDto permissionDto) {

        Set<Permission> permissions = user.getPermissions();
        Permission permission = permissionRepository.findByType(permissionDto.getType()).orElse(null);

        if (!permissions.contains(permission)) return user;

        permissions.remove(permission);
        user.setPermissions(permissions);

        return userRepository.save(user);
    }

    @Override
    public User deletePermissions(User existingUser, List<PermissionDto> permissionDtos) {

        User user = existingUser;
        for (PermissionDto permissionDto : permissionDtos) {
            user = deletePermission(existingUser, permissionDto);
        }
        return user;
    }
}
```

#### RequestService

```java
package com.dblab.opensourcedata.api.service.impl;

import com.dblab.opensourcedata.api.dto.RequestDto;
import com.dblab.opensourcedata.api.entity.Datafile;
import com.dblab.opensourcedata.api.entity.Request;
import com.dblab.opensourcedata.api.entity.User;
import com.dblab.opensourcedata.api.repository.PermissionRepository;
import com.dblab.opensourcedata.api.repository.RequestRepository;
import com.dblab.opensourcedata.api.service.*;
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
```


