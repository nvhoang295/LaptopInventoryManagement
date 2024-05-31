/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.repository;

import com.haui_megatech.model.InventoryItem;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author vieth
 */
public interface InventoryItemRepository {
    
    Optional<InventoryItem> findById(Integer id);
    
    Optional<InventoryItem> save(InventoryItem inventoryItem);
    
    ArrayList<InventoryItem> saveAll(ArrayList<InventoryItem> inventoryItems);
    
    void deleteById(int id);
    
    ArrayList<InventoryItem> getAll();
}
