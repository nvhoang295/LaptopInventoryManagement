/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.dto;

/**
 *
 * @author vieth
 */
public record CommonResponseDTO(
        Boolean success,
        String message
) {
    public CommonResponseDTO(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
    public static CommonResponseDTOBuilder builder() {
        return new CommonResponseDTOBuilder();
    }
    
    public static class CommonResponseDTOBuilder{
        private Boolean success;
        private String message;
        
        public CommonResponseDTOBuilder() {
            
        }
        
        public CommonResponseDTOBuilder success(Boolean success) {
            this.success = success;
            return this;
        }
        
        public CommonResponseDTOBuilder message(String message) {
            this.message = message;
            return this;
        }
        
        public CommonResponseDTO build() {
            return new CommonResponseDTO(success, message);
        }
    }
}
