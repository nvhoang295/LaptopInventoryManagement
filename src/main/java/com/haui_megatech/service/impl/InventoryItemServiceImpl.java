/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service.impl;

import com.haui_megatech.constant.ErrorMessage;
import com.haui_megatech.constant.SuccessMessage;
import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.InventoryItem;
import com.haui_megatech.repository.InventoryItemRepository;
import com.haui_megatech.service.InventoryItemService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author vieth
 */
@RequiredArgsConstructor
public class InventoryItemServiceImpl implements InventoryItemService {
    private final InventoryItemRepository inventoryItemRepository;

    @Override
    public CommonResponseDTO<List<InventoryItem>> getList() {
        return CommonResponseDTO
                .<List<InventoryItem>>builder()
                .data(inventoryItemRepository.getAll())
                .build();
    }

    @Override
    public CommonResponseDTO<List<InventoryItem>> searchList(String keyword) {
        return CommonResponseDTO
                .<List<InventoryItem>>builder()
                .data(inventoryItemRepository
                        .getAll()
                        .parallelStream()
                        .filter(item -> {
                            return new StringBuilder()
                                    .append(item.getImportPrice())
                                    .append(item.getImportBillItem().getProduct().getName())
                                    .append(item.getQuantity())
                                    .toString().toLowerCase()
                                    .contains(keyword.toLowerCase());
                        })
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public CommonResponseDTO addOne(InventoryItem item) {
        Optional<InventoryItem> savedProduct = inventoryItemRepository.save(item);
        return savedProduct.isPresent()
                ? CommonResponseDTO
                        .builder()
                        .success(true)
                        .message(SuccessMessage.InventoryItem.ADDED)
                        .build()
                : CommonResponseDTO
                        .builder()
                        .success(false)
                        .message(ErrorMessage.InventoryItem.SAVE)
                        .build();
    }

    @Override
    public CommonResponseDTO addList(ArrayList<InventoryItem> items) {
        ArrayList<InventoryItem> savedProducts = inventoryItemRepository.saveAll(items);
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message("Lưu thành công " + savedProducts.size() + " sản phẩm")
                .build();
    }

    @Override
    public CommonResponseDTO deleteOne(Integer id) {
        Optional<InventoryItem> found = inventoryItemRepository.findById(id);
        if (found.isEmpty()) {
            return CommonResponseDTO
                    .builder()
                    .success(Boolean.FALSE)
                    .message(ErrorMessage.ImportBillItem.NOT_FOUND)
                    .build();
        }
        
        inventoryItemRepository.deleteById(id);
        
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message(SuccessMessage.ImportBillItem.DELETED)
                .build();
    }

    @Override
    public Optional<InventoryItem> findById(Integer id) {
        return inventoryItemRepository.findById(id);
    }

    @Override
    public CommonResponseDTO updateOne(Integer id, InventoryItem item) {
        Optional<InventoryItem> found = inventoryItemRepository.findById(id);
        if (found.isEmpty()) {
            return CommonResponseDTO
                    .builder()
                    .success(Boolean.FALSE)
                    .message(ErrorMessage.Product.NOT_FOUND)
                    .build();
        }
        
        InventoryItem foundItem = found.get();
        foundItem.setQuantity(item.getQuantity());
        
        Optional<InventoryItem> updatedItem = inventoryItemRepository.save(foundItem);
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message(SuccessMessage.ImportBillItem.UPDATED)
                .build();
    }
    
    
}
