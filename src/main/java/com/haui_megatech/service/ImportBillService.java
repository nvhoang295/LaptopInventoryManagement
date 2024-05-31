/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service;

import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.ImportBill;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author vieth
 */
public interface ImportBillService {
    CommonResponseDTO<List<ImportBill>> getList();
    
    CommonResponseDTO<List<ImportBill>> searchList(String keyword);
    
    CommonResponseDTO addOne(ImportBill item);
    
    CommonResponseDTO deleteOne(Integer id);
    
    Optional<ImportBill> findById(Integer id);
    
    CommonResponseDTO updateOne(Integer id, ImportBill item);
}
