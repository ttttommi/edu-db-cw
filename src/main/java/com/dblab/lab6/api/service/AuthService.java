package com.dblab.lab6.api.service;

import com.dblab.lab6.api.dto.PermissionDto;
import com.dblab.lab6.api.dto.SignInDto;
import com.dblab.lab6.api.dto.SignUpDto;
import com.dblab.lab6.api.dto.UserDto;
import com.dblab.lab6.api.entity.User;

import java.util.List;

public interface AuthService {

    String signIn(SignInDto signInDto);

    User signUp(SignUpDto signUpDto);

    User getCurrentUser();

    User findByUsername(String username);

    User update(User existingUser, UserDto userDto);

    User addPermission(User existingUser, PermissionDto permissionDto);

    User addPermissions(User existingUser, List<PermissionDto> permissionDtos);

    User deletePermission(User existingUser, PermissionDto permissionDto);

    User deletePermissions(User existingUser, List<PermissionDto> permissionDtos);
}
