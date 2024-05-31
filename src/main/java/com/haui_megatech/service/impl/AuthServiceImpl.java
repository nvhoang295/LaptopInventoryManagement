/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service.impl;

import com.haui_megatech.ApplicationContext;
import com.haui_megatech.constant.ErrorMessage;
import com.haui_megatech.dto.AuthRequestDTO;
import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.User;
import com.haui_megatech.repository.UserRepository;
import com.haui_megatech.service.AuthService;
import java.util.*;

/**
 *
 * @author vieth
 */
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    

    @Override
    public CommonResponseDTO authenticate(AuthRequestDTO request) {
        if (request.username().trim().isEmpty() || request.password().trim().isEmpty())
            return CommonResponseDTO
                    .builder()
                    .success(false)
                    .message(ErrorMessage.Auth.BLANK_INPUT)
                    .build();
        
        Optional<User> found = userRepository
                .getAll()
                .parallelStream()
                .filter(item -> item.getUsername().equals(request.username()))
                .findFirst();
        
        if (found.isEmpty()) 
            return CommonResponseDTO
                    .builder()
                    .success(false)
                    .message(ErrorMessage.Auth.NOT_FOUND)
                    .build();
        
        if (!found.get().getPassword().equals(request.password()))
            return CommonResponseDTO
                    .builder()
                    .success(false)
                    .message(ErrorMessage.Auth.PASSWORD_NOT_CORRECT)
                    .build();

        ApplicationContext.setLoginedUser(found.get());
        found.get().setLastLoggedIn(new Date());
        Integer loggedIn = found.get().getLoggedIn();
        found.get().setLoggedIn(loggedIn == null ? 1 : loggedIn + 1);
        userRepository.save(found.get());
        
        return CommonResponseDTO
                .builder()
                .success(true)
                .build();
    }
    
}
