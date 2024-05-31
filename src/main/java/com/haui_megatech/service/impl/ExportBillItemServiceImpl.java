/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service.impl;

import com.haui_megatech.constant.ErrorMessage;
import com.haui_megatech.constant.SuccessMessage;
import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.ExportBillItem;
import com.haui_megatech.repository.ExportBillItemRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import com.haui_megatech.service.ExportBillItemService;

/**
 *
 * @author vieth
 */
@RequiredArgsConstructor
public class ExportBillItemServiceImpl implements ExportBillItemService {
    
    private final ExportBillItemRepository exportBillItemRepository;
    
    @Override
    public CommonResponseDTO<List<ExportBillItem>> getList() {
        return CommonResponseDTO
                .<List<ExportBillItem>>builder()
                .data(exportBillItemRepository.getAll())
                .build();
    }

    @Override
    public CommonResponseDTO<List<ExportBillItem>> searchList(String keyword) {
        return CommonResponseDTO
                .<List<ExportBillItem>>builder()
                .data(exportBillItemRepository
                        .getAll()
                        .parallelStream()
                        .filter(item -> {
                            return new StringBuilder()
                                    .append(item.getQuantity())
                                    .append(item.getExportPrice())
                                    .toString().toLowerCase()
                                    .contains(keyword.toLowerCase());
                        })
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public CommonResponseDTO addOne(ExportBillItem item) {
        Optional<ExportBillItem> savedProduct = exportBillItemRepository.save(item);
        return savedProduct.isPresent()
                ? CommonResponseDTO
                        .builder()
                        .success(true)
                        .message(SuccessMessage.ImportBillItem.ADDED)
                        .build()
                : CommonResponseDTO
                        .builder()
                        .success(false)
                        .message(ErrorMessage.ImportBillItem.SAVE)
                        .build();
    }

    @Override
    public CommonResponseDTO addList(ArrayList<ExportBillItem> items) {
        ArrayList<ExportBillItem> savedProducts = exportBillItemRepository.saveAll(items);
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message("Lưu thành công " + savedProducts.size() + " sản phẩm")
                .build();
    }

    @Override
    public CommonResponseDTO deleteOne(Integer id) {
        Optional<ExportBillItem> found = exportBillItemRepository.findById(id);
        if (found.isEmpty()) {
            return CommonResponseDTO
                    .builder()
                    .success(Boolean.FALSE)
                    .message(ErrorMessage.ImportBillItem.NOT_FOUND)
                    .build();
        }
        
        exportBillItemRepository.deleteById(id);
        
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message(SuccessMessage.ImportBillItem.DELETED)
                .build();
    }

    @Override
    public Optional<ExportBillItem> findById(Integer id) {
        return exportBillItemRepository.findById(id);
    }

    @Override
    public CommonResponseDTO updateOne(Integer id, ExportBillItem item) {
        Optional<ExportBillItem> found = exportBillItemRepository.findById(id);
        if (found.isEmpty()) {
            return CommonResponseDTO
                    .builder()
                    .success(Boolean.FALSE)
                    .message(ErrorMessage.Product.NOT_FOUND)
                    .build();
        }
        
        ExportBillItem foundItem = found.get();
        foundItem.setQuantity(item.getQuantity());
        
        Optional<ExportBillItem> updatedItem = exportBillItemRepository.save(foundItem);
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message(SuccessMessage.ImportBillItem.UPDATED)
                .build();
    }

}
