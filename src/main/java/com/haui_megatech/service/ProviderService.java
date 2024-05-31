/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service;

import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author vieth
 */
public interface ProviderService {
    CommonResponseDTO<List<Provider>> getList();
    
    CommonResponseDTO<List<Provider>> searchList(String keyword);
    
    CommonResponseDTO addOne(Provider provider);
    
    CommonResponseDTO addList(ArrayList<Provider> providers);
    
    CommonResponseDTO deleteOne(Integer id);
    
    Optional<Provider> findById(Integer id);
    
    CommonResponseDTO updateOne(Integer id, Provider provider);
    
    Optional<Provider> findByName(String name);
}
