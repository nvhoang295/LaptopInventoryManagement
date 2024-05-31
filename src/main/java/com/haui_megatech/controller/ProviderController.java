/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.controller;

import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.Provider;
import com.haui_megatech.service.ProviderService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author vieth
 */
@RequiredArgsConstructor
public class ProviderController {
    
    private final ProviderService providerService;
    
    public CommonResponseDTO<List<Provider>> getList() {
        return providerService.getList();
    }
    
    public CommonResponseDTO<List<Provider>> searchList(String keyword) {
        return providerService.searchList(keyword);
    }
    
    public CommonResponseDTO addOne(Provider provider) {
        return providerService.addOne(provider);
    }
    
    public CommonResponseDTO addList(ArrayList<Provider> providers) {
        return providerService.addList(providers);
    }
    
    public CommonResponseDTO deleteOne(Integer id) {
        return providerService.deleteOne(id);
    }
    
    public Optional<Provider> findById(Integer id) {
        return providerService.findById(id);
    }
    
    public CommonResponseDTO updateOne(Integer id, Provider provider) {
        return providerService.updateOne(id, provider);
    }
}
