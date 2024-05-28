/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.util;

/**
 *
 * @author vieth
 */
public class InputValidator {
    
    
    public static boolean anyBlankInput(String ...inputs) {
        for (String input : inputs) {
            if (input == null || input.isBlank() || input.isEmpty()) return true;
        }
        return false;
    }
}
