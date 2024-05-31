/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.repository;

import com.haui_megatech.model.ImportBillItem;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author vieth
 */
public interface ImportBillItemRepository {
    
    Optional<ImportBillItem> findById(Integer id);
    
    Optional<ImportBillItem> save(ImportBillItem inventoryItem);
    
    ArrayList<ImportBillItem> saveAll(ArrayList<ImportBillItem> inventoryItems);
    
    void deleteById(int id);
    
    ArrayList<ImportBillItem> getAll();
}
