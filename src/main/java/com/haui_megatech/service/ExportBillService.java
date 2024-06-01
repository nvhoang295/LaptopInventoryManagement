/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service;

import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.ExportBill;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author vieth
 */
public interface ExportBillService {
    CommonResponseDTO<List<ExportBill>> getList();
    
    CommonResponseDTO<List<ExportBill>> searchList(String keyword);
    
    CommonResponseDTO addOne(ExportBill item);
    
    CommonResponseDTO deleteOne(Integer id);
    
    Optional<ExportBill> findById(Integer id);
    
    CommonResponseDTO updateOne(Integer id, ExportBill item);
}
