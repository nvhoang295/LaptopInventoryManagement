/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.controller;

import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.ExportBillItem;
import com.haui_megatech.service.ExportBillItemService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author vieth
 */
@RequiredArgsConstructor
public class ExportBillItemController {
    private final ExportBillItemService service;
    
    public CommonResponseDTO<List<ExportBillItem>> getList() {
        return service.getList();
    }
    
    public CommonResponseDTO<List<ExportBillItem>> searchList(String keyword) {
        return service.searchList(keyword);
    }
    
    public CommonResponseDTO addOne(ExportBillItem item) {
        return service.addOne(item);
    }
    
    public CommonResponseDTO addList(ArrayList<ExportBillItem> items) {
        return service.addList(items);
    }
    
    public CommonResponseDTO deleteOne(Integer id) {
        return service.deleteOne(id);
    }
    
    public Optional<ExportBillItem> findById(Integer id) {
        return service.findById(id);
    }
    
    public CommonResponseDTO updateOne(Integer id, ExportBillItem item) {
        return service.updateOne(id, item);
    }
}
