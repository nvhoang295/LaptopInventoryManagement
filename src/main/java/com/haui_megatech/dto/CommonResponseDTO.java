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
}
