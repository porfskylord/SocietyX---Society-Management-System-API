package com.lordscave.societyxapi.admin_module.service.interfaces;

import com.lordscave.societyxapi.admin_module.dto.req.CreateUser;
import com.lordscave.societyxapi.admin_module.dto.req.UpdateUser;
import com.lordscave.societyxapi.admin_module.dto.rsp.GeneralResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.UserDetailsResponse;
import com.lordscave.societyxapi.admin_module.dto.rsp.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUser request);
    List<UserDetailsResponse> getAllUsers();
    UserDetailsResponse getUser(Long id);
    UserResponse updateUser(Long id, UpdateUser request);
    GeneralResponse deleteUser(Long id);
    UserResponse patchUser(Long id, UpdateUser request);
}
