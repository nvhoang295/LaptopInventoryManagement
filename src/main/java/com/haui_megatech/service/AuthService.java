/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service;

import com.haui_megatech.dto.AuthRequestDTO;
import com.haui_megatech.dto.CommonResponseDTO;

/**
 *
 * @author vieth
 */
public interface AuthService {
    CommonResponseDTO authenticate(AuthRequestDTO request);
    
}
