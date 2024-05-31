/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.controller;

import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.ImportBillItem;
import com.haui_megatech.service.ImportBillItemService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author vieth
 */
@RequiredArgsConstructor
public class ImportBillItemController {
    private final ImportBillItemService service;
    
    public CommonResponseDTO<List<ImportBillItem>> getList() {
        return service.getList();
    }
    
    public CommonResponseDTO<List<ImportBillItem>> searchList(String keyword) {
        return service.searchList(keyword);
    }
    
    public CommonResponseDTO addOne(ImportBillItem item) {
        return service.addOne(item);
    }
    
    public CommonResponseDTO addList(ArrayList<ImportBillItem> items) {
        return service.addList(items);
    }
    
    public CommonResponseDTO deleteOne(Integer id) {
        return service.deleteOne(id);
    }
    
    public Optional<ImportBillItem> findById(Integer id) {
        return service.findById(id);
    }
    
    public CommonResponseDTO updateOne(Integer id, ImportBillItem item) {
        return service.updateOne(id, item);
    }
}
