/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.repository;

import com.haui_megatech.dto.ListItemsResponseDTO;
import com.haui_megatech.model.User;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author vieth
 */
public interface UserRepository {

    Optional<User> save(User user);

    List<User> saveAll(List<User> users);

    Optional<User> findByUsername(String username);

    List<User> getList();
}
