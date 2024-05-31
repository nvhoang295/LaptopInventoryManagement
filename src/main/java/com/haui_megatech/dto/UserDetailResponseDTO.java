/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.dto;

import lombok.Builder;

/**
 *
 * @author vieth
 */
@Builder
public record UserDetailResponseDTO(
        String username,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String whenCreated,
        String lastUpdated,
        String lastLoggedIn,
        Integer loggedIn
) {
    
}
