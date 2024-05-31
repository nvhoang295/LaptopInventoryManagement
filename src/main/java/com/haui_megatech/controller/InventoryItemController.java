/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.controller;

import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.InventoryItem;
import com.haui_megatech.service.InventoryItemService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author vieth
 */
@RequiredArgsConstructor
public class InventoryItemController {
    private final InventoryItemService service;
    
    public CommonResponseDTO<List<InventoryItem>> getList() {
        return service.getList();
    }
    
    public CommonResponseDTO<List<InventoryItem>> searchList(String keyword) {
        return service.searchList(keyword);
    }
    
    public CommonResponseDTO addOne(InventoryItem item) {
        return service.addOne(item);
    }
    
    public CommonResponseDTO addList(ArrayList<InventoryItem> items) {
        return service.addList(items);
    }
    
    public CommonResponseDTO deleteOne(Integer id) {
        return service.deleteOne(id);
    }
    
    public Optional<InventoryItem> findById(Integer id) {
        return service.findById(id);
    }
    
    public CommonResponseDTO updateOne(Integer id, InventoryItem item) {
        return service.updateOne(id, item);
    }
}
