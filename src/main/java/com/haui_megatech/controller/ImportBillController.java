/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.controller;

import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.ImportBill;
import com.haui_megatech.service.ImportBillService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author vieth
 */
@RequiredArgsConstructor
public class ImportBillController {
    private final ImportBillService importBillService;
    
    public CommonResponseDTO<List<ImportBill>> getList() {
        return importBillService.getList();
    }
    
    public CommonResponseDTO<List<ImportBill>> searchList(String keyword) {
        return importBillService.searchList(keyword);
    }
    
    public CommonResponseDTO addOne(ImportBill item) {
        return importBillService.addOne(item);
    }
    
    public CommonResponseDTO deleteOne(Integer id) {
        return importBillService.deleteOne(id);
    }
    
    public Optional<ImportBill> findById(Integer id) {
        return importBillService.findById(id);
    }
    
    public CommonResponseDTO updateOne(Integer id, ImportBill item) {
        return importBillService.updateOne(id, item);
    }
}
