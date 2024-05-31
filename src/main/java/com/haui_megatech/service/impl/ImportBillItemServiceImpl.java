/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service.impl;

import com.haui_megatech.constant.ErrorMessage;
import com.haui_megatech.constant.SuccessMessage;
import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.ImportBillItem;
import com.haui_megatech.service.ImportBillItemService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import com.haui_megatech.repository.ImportBillItemRepository;

/**
 *
 * @author vieth
 */
@RequiredArgsConstructor
public class ImportBillItemServiceImpl implements ImportBillItemService {
    
    private final ImportBillItemRepository importBillItemRepository;
    
    @Override
    public CommonResponseDTO<List<ImportBillItem>> getList() {
        return CommonResponseDTO
                .<List<ImportBillItem>>builder()
                .data(importBillItemRepository.getAll())
                .build();
    }

    @Override
    public CommonResponseDTO<List<ImportBillItem>> searchList(String keyword) {
        return CommonResponseDTO
                .<List<ImportBillItem>>builder()
                .data(importBillItemRepository
                        .getAll()
                        .parallelStream()
                        .filter(item -> {
                            return new StringBuilder()
                                    .append(item.getImportPrice())
                                    .append(item.getProduct().getName())
                                    .append(item.getQuantity())
                                    .toString().toLowerCase()
                                    .contains(keyword.toLowerCase());
                        })
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public CommonResponseDTO addOne(ImportBillItem item) {
        Optional<ImportBillItem> savedProduct = importBillItemRepository.save(item);
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
    public CommonResponseDTO addList(ArrayList<ImportBillItem> items) {
        ArrayList<ImportBillItem> savedProducts = importBillItemRepository.saveAll(items);
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message("Lưu thành công " + savedProducts.size() + " sản phẩm")
                .build();
    }

    @Override
    public CommonResponseDTO deleteOne(Integer id) {
        Optional<ImportBillItem> found = importBillItemRepository.findById(id);
        if (found.isEmpty()) {
            return CommonResponseDTO
                    .builder()
                    .success(Boolean.FALSE)
                    .message(ErrorMessage.ImportBillItem.NOT_FOUND)
                    .build();
        }
        
        importBillItemRepository.deleteById(id);
        
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message(SuccessMessage.ImportBillItem.DELETED)
                .build();
    }

    @Override
    public Optional<ImportBillItem> findById(Integer id) {
        return importBillItemRepository.findById(id);
    }

    @Override
    public CommonResponseDTO updateOne(Integer id, ImportBillItem item) {
        Optional<ImportBillItem> found = importBillItemRepository.findById(id);
        if (found.isEmpty()) {
            return CommonResponseDTO
                    .builder()
                    .success(Boolean.FALSE)
                    .message(ErrorMessage.Product.NOT_FOUND)
                    .build();
        }
        
        ImportBillItem foundItem = found.get();
        foundItem.setQuantity(item.getQuantity());
        
        Optional<ImportBillItem> updatedItem = importBillItemRepository.save(foundItem);
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message(SuccessMessage.ImportBillItem.UPDATED)
                .build();
    }

}
