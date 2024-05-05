/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.controller;

import com.haui_megatech.dto.AuthRequestDTO;
import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.service.AuthService;

/**
 *
 * @author vieth
 */
public class AuthController {
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    public CommonResponseDTO authenticate(AuthRequestDTO request) {
        return authService.authenticate(request);
    }
}
