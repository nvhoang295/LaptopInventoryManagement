/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service;

import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.ExportBillItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author vieth
 */
public interface ExportBillItemService {
    CommonResponseDTO<List<ExportBillItem>> getList();
    
    CommonResponseDTO<List<ExportBillItem>> searchList(String keyword);
    
    CommonResponseDTO addOne(ExportBillItem item);
    
    CommonResponseDTO addList(ArrayList<ExportBillItem> items);
    
    CommonResponseDTO deleteOne(Integer id);
    
    Optional<ExportBillItem> findById(Integer id);
    
    CommonResponseDTO updateOne(Integer id, ExportBillItem item);
}
