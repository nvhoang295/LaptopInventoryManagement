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
            return new CommonResponseDTO(false, ErrorMessage.Auth.BLANK_INPUT);
        
        Optional<User> found = userRepository.findByUsername(request.username());
        
        if (found.isEmpty()) 
            return new CommonResponseDTO(false, ErrorMessage.Auth.NOT_FOUND);
        
        if (!found.get().getPassword().equals(request.password()))
            return new CommonResponseDTO(false, ErrorMessage.Auth.PASSWORD_NOT_CORRECT);

        ApplicationContext.setLoginedUser(found.get());
        return new CommonResponseDTO(true, null);
    }
    
}
