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
    
    public static AuthRequestDTOBuilder builder() {
        return new AuthRequestDTOBuilder();
    }
    
    public static class AuthRequestDTOBuilder {
        private String username;
        private String password;
        
        public AuthRequestDTOBuilder() {}
        
        public AuthRequestDTOBuilder username(String username) {
            this.username = username;
            return this;
        }
        
        public AuthRequestDTOBuilder password(String password) {
            this.password = password;
            return this;
        }
        
        public AuthRequestDTO build() {
            return new AuthRequestDTO(this.username, this.password);
        }
    }
}
