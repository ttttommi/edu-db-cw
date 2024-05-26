package com.dblab.lab6.api.service.impl;

import com.dblab.lab6.api.dto.PermissionDto;
import com.dblab.lab6.api.dto.SignInDto;
import com.dblab.lab6.api.dto.SignUpDto;
import com.dblab.lab6.api.dto.UserDto;
import com.dblab.lab6.api.entity.Permission;
import com.dblab.lab6.api.entity.Role;
import com.dblab.lab6.api.entity.User;
import com.dblab.lab6.api.repository.PermissionRepository;
import com.dblab.lab6.api.repository.RoleRepository;
import com.dblab.lab6.api.repository.UserRepository;
import com.dblab.lab6.api.security.jwt.JwtUtils;
import com.dblab.lab6.api.service.AuthService;
import com.dblab.lab6.api.service.UserAlreadyExistException;
import com.dblab.lab6.api.service.UserNotFoundException;
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
