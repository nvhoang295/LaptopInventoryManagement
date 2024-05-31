/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service.impl;

import com.haui_megatech.constant.ErrorMessage;
import com.haui_megatech.constant.SuccessMessage;
import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.ExportBill;
import com.haui_megatech.repository.ExportBillItemRepository;
import com.haui_megatech.repository.ExportBillRepository;
import com.haui_megatech.repository.InventoryItemRepository;
import com.haui_megatech.service.ExportBillService;
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
public class ExportBillServiceImpl implements ExportBillService {
    
    private final ExportBillRepository exportBillRepository;
    private final ExportBillItemRepository exportBillItemRepository;
    
    @Override
    public CommonResponseDTO<List<ExportBill>> getList() {
        return CommonResponseDTO
                .<List<ExportBill>>builder()
                .data(exportBillRepository.getAll())
                .build();
    }

    @Override
    public CommonResponseDTO<List<ExportBill>> searchList(String keyword) {
        return CommonResponseDTO
                .<List<ExportBill>>builder()
                .data(exportBillRepository
                        .getAll()
                        .parallelStream()
                        .filter(item -> {
                            return new StringBuilder()
                                    .append(item.getClientName())
                                    .append(item.getClientPhoneNumber())
                                    .append(item.getClientAddress())
                                    .toString().toLowerCase()
                                    .contains(keyword.toLowerCase());
                        })
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public CommonResponseDTO addOne(ExportBill item) {
        item.setWhenCreated(new Date());
        
        item.getExportBillItems().forEach(billItem -> {
            exportBillItemRepository.save(billItem);
        });
        
        
        
        Optional<ExportBill> savedItem = exportBillRepository.save(item);
        return savedItem.isPresent()
                ? CommonResponseDTO
                        .builder()
                        .success(true)
                        .message(SuccessMessage.ExportBill.ADDED)
                        .build()
                : CommonResponseDTO
                        .builder()
                        .success(false)
                        .message(ErrorMessage.ExportBill.SAVE)
                        .build();
    }

    @Override
    public CommonResponseDTO deleteOne(Integer id) {
        Optional<ExportBill> found = exportBillRepository.findById(id);
        if (found.isEmpty()) {
            return CommonResponseDTO
                    .builder()
                    .success(Boolean.FALSE)
                    .message(ErrorMessage.ImportBill.NOT_FOUND)
                    .build();
        }
        
        found.get().getExportBillItems().forEach(item -> {
            exportBillItemRepository.deleteById(item.getId());
        });
        
        exportBillRepository.deleteById(id);
        
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message(SuccessMessage.ImportBillItem.DELETED)
                .build();
    }

    @Override
    public Optional<ExportBill> findById(Integer id) {
        return exportBillRepository.findById(id);
    }

    @Override
    public CommonResponseDTO updateOne(Integer id, ExportBill item) {
        Optional<ExportBill> found = exportBillRepository.findById(id);
        if (found.isEmpty()) {
            return CommonResponseDTO
                    .builder()
                    .success(Boolean.FALSE)
                    .message(ErrorMessage.Product.NOT_FOUND)
                    .build();
        }
        
        ExportBill foundExportBill = found.get();
        foundExportBill.setClientName(item.getClientName());
        foundExportBill.setClientPhoneNumber(item.getClientPhoneNumber());
        foundExportBill.setClientAddress(item.getClientAddress());
        
        Optional<ExportBill> updatedExportBill = exportBillRepository.save(foundExportBill);
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message(SuccessMessage.Product.UPDATED)
                .build();
    }
    
}
