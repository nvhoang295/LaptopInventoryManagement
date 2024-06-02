/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.repository;

import com.haui_megatech.model.ExportBill;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author vieth
 */
public interface ExportBillRepository {
    
    Optional<ExportBill> findById(Integer id);
    
    Optional<ExportBill> save(ExportBill item);
    
    void deleteById(int id);
    
    ArrayList<ExportBill> getAll();
}
