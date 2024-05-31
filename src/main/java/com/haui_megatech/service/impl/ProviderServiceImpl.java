/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service.impl;

import com.haui_megatech.constant.ErrorMessage;
import com.haui_megatech.constant.SuccessMessage;
import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.Provider;
import com.haui_megatech.repository.ProviderRepository;
import com.haui_megatech.service.ProviderService;
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
public class ProviderServiceImpl implements ProviderService {
    
    private final ProviderRepository providerRepository;
    
    @Override
    public CommonResponseDTO<List<Provider>> getList() {
        return CommonResponseDTO
                .<List<Provider>>builder()
                .data(providerRepository.getAll())
                .build();
    }

    @Override
    public CommonResponseDTO<List<Provider>> searchList(String keyword) {
        return CommonResponseDTO
                .<List<Provider>>builder()
                .data(providerRepository
                        .getAll()
                        .parallelStream()
                        .filter(item -> {
                            return new StringBuilder()
                                    .append(item.getName())
                                    .append(item.getPhoneNumber())
                                    .append(item.getEmail())
                                    .append(item.getAddress())
                                    .toString().toLowerCase()
                                    .contains(keyword.toLowerCase());
                        })
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public CommonResponseDTO addOne(Provider provider) {
        provider.setWhenCreated(new Date());
        Optional<Provider> savedProvider = providerRepository.save(provider);
        return savedProvider.isPresent()
                ? CommonResponseDTO
                        .builder()
                        .success(true)
                        .message(SuccessMessage.Provider.ADDED)
                        .build()
                : CommonResponseDTO
                        .builder()
                        .success(false)
                        .message(ErrorMessage.Provider.SAVE)
                        .build();
    }

    @Override
    public CommonResponseDTO addList(ArrayList<Provider> providers) {
        ArrayList<Provider> savedProviders = providerRepository.saveAll(providers);
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message("Lưu thành công " + savedProviders.size() + " nhà cung cấp")
                .build();
    }

    @Override
    public CommonResponseDTO deleteOne(Integer id) {
        Optional<Provider> found = providerRepository.findById(id);
        if (found.isEmpty()) {
            return CommonResponseDTO
                    .builder()
                    .success(Boolean.FALSE)
                    .message(ErrorMessage.Provider.NOT_FOUND)
                    .build();
        }
        
        providerRepository.deleteById(id);
        
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message(SuccessMessage.Provider.DELETED)
                .build();
    }

    @Override
    public Optional<Provider> findById(Integer id) {
        return providerRepository.findById(id);
    }

    @Override
    public CommonResponseDTO updateOne(Integer id, Provider provider) {
        Optional<Provider> found = providerRepository.findById(id);
        if (found.isEmpty()) {
            return CommonResponseDTO
                    .builder()
                    .success(Boolean.FALSE)
                    .message(ErrorMessage.Product.NOT_FOUND)
                    .build();
        }
        
        Provider foundProvider = found.get();
        foundProvider.setName(provider.getName());
        foundProvider.setPhoneNumber(provider.getPhoneNumber());
        foundProvider.setEmail(provider.getEmail());
        foundProvider.setAddress(provider.getAddress());
        foundProvider.setLastUpdated(new Date());
        
        Optional<Provider> updatedProvider = providerRepository.save(foundProvider);
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message(SuccessMessage.Product.UPDATED)
                .build();
    }
    
}
