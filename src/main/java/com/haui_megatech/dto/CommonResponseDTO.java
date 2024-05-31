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
public record CommonResponseDTO<T>(
        Boolean success,
        String message,
        T data
) {
    public CommonResponseDTO(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    
//    public static CommonResponseDTOBuilder builder() {
//        return new CommonResponseDTOBuilder();
//    }
//    
//    public static class CommonResponseDTOBuilder<T>{
//        private Boolean success;
//        private String message;
//        T data;
//        
//        public CommonResponseDTOBuilder() {
//            
//        }
//        
//        public CommonResponseDTOBuilder success(Boolean success) {
//            this.success = success;
//            return this;
//        }
//        
//        public CommonResponseDTOBuilder message(String message) {
//            this.message = message;
//            return this;
//        }
//        
//        public CommonResponseDTOBuilder data(T data) {
//            this.data = data;
//            return this;
//        }
//        
//        public CommonResponseDTO build() {
//            return new CommonResponseDTO(success, message, data);
//        }
//    }
}
