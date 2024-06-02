/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.repository;

import com.haui_megatech.model.ExportBillItem;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author vieth
 */
public interface ExportBillItemRepository {
    
    Optional<ExportBillItem> findById(Integer id);
    
    Optional<ExportBillItem> save(ExportBillItem item);
    
    ArrayList<ExportBillItem> saveAll(ArrayList<ExportBillItem> item);
    
    void deleteById(int id);
    
    ArrayList<ExportBillItem> getAll();
}
