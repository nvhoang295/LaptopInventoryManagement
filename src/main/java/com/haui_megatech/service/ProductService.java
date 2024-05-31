/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service;

import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author vieth
 */
public interface ProductService {
    
    CommonResponseDTO<List<Product>> getList();
    
    CommonResponseDTO<List<Product>> searchList(String keyword);
    
    CommonResponseDTO addOne(Product product);
    
    CommonResponseDTO addList(ArrayList<Product> products);
    
    CommonResponseDTO deleteOne(Integer id);
    
    Optional<Product> findById(Integer id);
    
    CommonResponseDTO updateOne(Integer id, Product product);
    
    List<Product> getListByIds(List<Integer> ids);
    
}
