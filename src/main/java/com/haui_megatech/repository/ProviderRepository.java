/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.repository;

import com.haui_megatech.model.Provider;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author vieth
 */
public interface ProviderRepository {
    
    Optional<Provider> findById(Integer id);
    
    Optional<Provider> save(Provider provider);
    
    ArrayList<Provider> saveAll(ArrayList<Provider> providers);
    
    void deleteById(Integer id);
    
    ArrayList<Provider> getAll();
}
