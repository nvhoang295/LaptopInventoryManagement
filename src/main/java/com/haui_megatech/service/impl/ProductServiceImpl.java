/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service.impl;

import com.haui_megatech.constant.ErrorMessage;
import com.haui_megatech.constant.SuccessMessage;
import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.Product;
import com.haui_megatech.repository.ProductRepository;
import com.haui_megatech.service.ProductService;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public CommonResponseDTO<List<Product>> getList() {
        return CommonResponseDTO
                .<List<Product>>builder()
                .data(productRepository.getAll())
                .build();
    }

    @Override
    public CommonResponseDTO<List<Product>> searchList(String keyword) {
        return CommonResponseDTO
                .<List<Product>>builder()
                .data(productRepository
                        .getAll()
                        .parallelStream()
                        .filter(item -> {
                            return new StringBuilder()
                                    .append(item.getName())
                                    .append(item.getMemory())
                                    .append(item.getProcessor())
                                    .append(item.getStorage())
                                    .toString()
                                    .contains(keyword);
                        })
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public CommonResponseDTO addOne(Product product) {
        product.setWhenCreated(new Date());
        Optional<Product> savedProduct = productRepository.save(product);
        return savedProduct.isPresent()
                ? CommonResponseDTO
                        .builder()
                        .success(true)
                        .message(SuccessMessage.Product.ADDED)
                        .build()
                : CommonResponseDTO
                        .builder()
                        .success(false)
                        .message(ErrorMessage.Product.SAVE)
                        .build();
    }

    @Override
    public CommonResponseDTO addList(ArrayList<Product> products) {
        ArrayList<Product> savedProducts = productRepository.saveAll(products);
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message("Lưu thành công " + savedProducts.size() + " sản phẩm")
                .build();
    }

    @Override
    public CommonResponseDTO deleteOne(Integer id) {
        Optional<Product> found = productRepository.findById(id);
        if (found.isEmpty()) {
            return CommonResponseDTO
                    .builder()
                    .success(Boolean.FALSE)
                    .message(ErrorMessage.Product.NOT_FOUND)
                    .build();
        }
        
        productRepository.deleteById(id);
        
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message(SuccessMessage.Product.DELETED)
                .build();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public CommonResponseDTO updateOne(Integer id, Product product) {
        Optional<Product> found = productRepository.findById(id);
        if (found.isEmpty()) {
            return CommonResponseDTO
                    .builder()
                    .success(Boolean.FALSE)
                    .message(ErrorMessage.Product.NOT_FOUND)
                    .build();
        }
        
        Product foundProduct = found.get();
        foundProduct.setName(product.getName());
        foundProduct.setProcessor(product.getProcessor());
        foundProduct.setMemory(product.getMemory());
        foundProduct.setStorage(product.getStorage());
        foundProduct.setDisplay(product.getDisplay());
        foundProduct.setBattery(product.getBattery());
        
        Optional<Product> updatedProduct = productRepository.save(foundProduct);
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message(SuccessMessage.Product.UPDATED)
                .build();
    }

}
