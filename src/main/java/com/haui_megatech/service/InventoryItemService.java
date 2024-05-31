/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service;

import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.InventoryItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author vieth
 */
public interface InventoryItemService {
    CommonResponseDTO<List<InventoryItem>> getList();
    
    CommonResponseDTO<List<InventoryItem>> searchList(String keyword);
    
    CommonResponseDTO addOne(InventoryItem item);
    
    CommonResponseDTO addList(ArrayList<InventoryItem> items);
    
    CommonResponseDTO deleteOne(Integer id);
    
    Optional<InventoryItem> findById(Integer id);
    
    CommonResponseDTO updateOne(Integer id, InventoryItem item);
}
