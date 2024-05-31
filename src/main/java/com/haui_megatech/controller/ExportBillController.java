/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.controller;

import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.ExportBill;
import com.haui_megatech.service.ExportBillService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author vieth
 */
@RequiredArgsConstructor
public class ExportBillController {
    private final ExportBillService service;
    
    public CommonResponseDTO<List<ExportBill>> getList() {
        return service.getList();
    }
    
    public CommonResponseDTO<List<ExportBill>> searchList(String keyword) {
        return service.searchList(keyword);
    }
    
    public CommonResponseDTO addOne(ExportBill item) {
        return service.addOne(item);
    }
    
    public CommonResponseDTO deleteOne(Integer id) {
        return service.deleteOne(id);
    }
    
    public Optional<ExportBill> findById(Integer id) {
        return service.findById(id);
    }
    
    public CommonResponseDTO updateOne(Integer id, ExportBill item) {
        return service.updateOne(id, item);
    }
    
}
