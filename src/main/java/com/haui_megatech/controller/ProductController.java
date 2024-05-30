/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.controller;

import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.Product;
import com.haui_megatech.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author vieth
 */
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    
    public CommonResponseDTO<List<Product>> getList() {
        return productService.getList();
    }
    
    public CommonResponseDTO<List<Product>> searchList(String keyword) {
        return productService.searchList(keyword);
    }
    
    public CommonResponseDTO addOne(Product product) {
        return productService.addOne(product);
    }
    
    public CommonResponseDTO addList(ArrayList<Product> products) {
        return productService.addList(products);
    }
    
    public CommonResponseDTO deleteOne(Integer id) {
        return productService.deleteOne(id);
    }
    
    public Optional<Product> findById(Integer id) {
        return productService.findById(id);
    }
    
    
}
