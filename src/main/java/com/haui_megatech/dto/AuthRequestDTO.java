/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.dto;

/**
 *
 * @author vieth
 */
public record AuthRequestDTO(
        String username,
        String password
) {
    public AuthRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
