/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.dto;

import java.util.List;

/**
 *
 * @author vieth
 */
public record ListItemsResponseDTO<T>(
    String message,
    List<T> items
) {
    public ListItemsResponseDTO(String message, List<T> items) {
        this.message = message;
        this.items = items;
    }
    
    public ListItemsResponseDTO(List<T> items) {
        this(null, items);
    }
    
    public static ListItemsResponseDTOBuilder builder() {
        return new ListItemsResponseDTOBuilder();
    }
    
    public static class ListItemsResponseDTOBuilder<T> {
        private String message;
        private List<T> items;
        
        public ListItemsResponseDTOBuilder() {
            
        }
        
        public ListItemsResponseDTOBuilder<T> message(String message) {
            this.message = message;
            return this;
        }
        
        public ListItemsResponseDTOBuilder<T> items(List<T> items) {
            this.items = items;
            return this;
        }
        
        public ListItemsResponseDTO<T> build() {
            return new ListItemsResponseDTO(this.message, this.items);
        }
        
    }
}
