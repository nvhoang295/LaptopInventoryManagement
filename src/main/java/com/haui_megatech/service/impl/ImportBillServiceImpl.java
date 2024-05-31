/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service.impl;

import com.haui_megatech.constant.ErrorMessage;
import com.haui_megatech.constant.SuccessMessage;
import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.ImportBill;
import com.haui_megatech.model.ImportBillItem;
import com.haui_megatech.repository.ImportBillItemRepository;
import com.haui_megatech.repository.ImportBillRepository;
import com.haui_megatech.repository.ProviderRepository;
import com.haui_megatech.service.ImportBillService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author vieth
 */
@RequiredArgsConstructor
public class ImportBillServiceImpl implements ImportBillService {
    
    private final ImportBillRepository importBillRepository;
    private final ImportBillItemRepository importBillItemRepository;
    private final ProviderRepository providerRepository;
    
    @Override
    public CommonResponseDTO<List<ImportBill>> getList() {
        return CommonResponseDTO
                .<List<ImportBill>>builder()
                .data(importBillRepository.getAll())
                .build();
    }

    @Override
    public CommonResponseDTO<List<ImportBill>> searchList(String keyword) {
        return CommonResponseDTO
                .<List<ImportBill>>builder()
                .data(importBillRepository
                        .getAll()
                        .parallelStream()
                        .filter(item -> {
                            return new StringBuilder()
                                    .append(item.getProvider().getName())
                                    .append(item.getUser().getUsername())
                                    .append(item.getTotal())
                                    .toString().toLowerCase()
                                    .contains(keyword.toLowerCase());
                        })
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public CommonResponseDTO addOne(ImportBill item) {
        item.setWhenCreated(new Date());
        
        item.getImportBillItems().forEach(billItem -> {
            importBillItemRepository.save(billItem);
        });
        
        item.getProvider().getImportBills().add(item);
        providerRepository.save(item.getProvider());
        
        Optional<ImportBill> savedItem = importBillRepository.save(item);
        return savedItem.isPresent()
                ? CommonResponseDTO
                        .builder()
                        .success(true)
                        .message(SuccessMessage.ImportBill.ADDED)
                        .build()
                : CommonResponseDTO
                        .builder()
                        .success(false)
                        .message(ErrorMessage.ImportBill.SAVE)
                        .build();
    }

    @Override
    public CommonResponseDTO deleteOne(Integer id) {
        Optional<ImportBill> found = importBillRepository.findById(id);
        if (found.isEmpty()) {
            return CommonResponseDTO
                    .builder()
                    .success(Boolean.FALSE)
                    .message(ErrorMessage.ImportBill.NOT_FOUND)
                    .build();
        }
        
        found.get().getImportBillItems().forEach(item -> {
            importBillItemRepository.deleteById(item.getId());
        });
        
        importBillRepository.deleteById(id);
        
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message(SuccessMessage.ImportBillItem.DELETED)
                .build();
    }

    @Override
    public Optional<ImportBill> findById(Integer id) {
        return importBillRepository.findById(id);
    }

    @Override
    public CommonResponseDTO updateOne(Integer id, ImportBill item) {
        
        return null;
    }
    
}
