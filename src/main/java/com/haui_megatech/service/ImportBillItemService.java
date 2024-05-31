/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service;

import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.ImportBillItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author vieth
 */
public interface ImportBillItemService {
    CommonResponseDTO<List<ImportBillItem>> getList();
    
    CommonResponseDTO<List<ImportBillItem>> searchList(String keyword);
    
    CommonResponseDTO addOne(ImportBillItem item);
    
    CommonResponseDTO addList(ArrayList<ImportBillItem> items);
    
    CommonResponseDTO deleteOne(Integer id);
    
    Optional<ImportBillItem> findById(Integer id);
    
    CommonResponseDTO updateOne(Integer id, ImportBillItem item);
}
