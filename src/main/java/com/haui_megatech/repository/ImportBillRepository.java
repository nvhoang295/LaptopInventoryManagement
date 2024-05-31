/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.repository;

import com.haui_megatech.model.ImportBill;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author vieth
 */
public interface ImportBillRepository {
    
    Optional<ImportBill> findById(Integer id);
    
    Optional<ImportBill> save(ImportBill importBill);
    
    void deleteById(int id);
    
    ArrayList<ImportBill> getAll();
}
