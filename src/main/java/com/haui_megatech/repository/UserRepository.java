/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.repository;

import com.haui_megatech.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author vieth
 */
public interface UserRepository {
    
    Optional<User> findById(Integer id);
    
    Optional<User> findByUsername(String username);
    
    Optional<User> save(User user);

    ArrayList<User> saveAll(ArrayList<User> users);
    
    void deleteById(int id);

    ArrayList<User> getAll();
}
