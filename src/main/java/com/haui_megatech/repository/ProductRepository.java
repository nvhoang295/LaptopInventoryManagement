/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.repository;

import com.haui_megatech.model.Product;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author vieth
 */
public interface ProductRepository {
    
    Optional<Product> findById(Integer id);
    
    Optional<Product> save(Product product);
    
    ArrayList<Product> saveAll(ArrayList<Product> products);
    
    void deleteById(int id);
    
    ArrayList<Product> getAll();
}
