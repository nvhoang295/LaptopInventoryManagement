/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.controller;

import com.haui_megatech.model.User;
import com.haui_megatech.service.UserService;
import com.haui_megatech.dto.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 *
 * @author vieth
 */
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    public CommonResponseDTO<List<User>> getList() {
        return userService.getList();
    }
    
    public CommonResponseDTO<List<User>> searchList(String keyword) {
        return userService.searchList(keyword);
    }
    
    public CommonResponseDTO addOne(User user) {
        return userService.addOne(user);
    }
    
    public CommonResponseDTO addList(ArrayList<User> users) {
        return userService.addList(users);
    }
    
    public CommonResponseDTO deleteOne(Integer id) {
        return userService.deleteOne(id);
    }
    
    public Optional<User> findByUsername(String username) {
        return userService.findByUsername(username);
    }
    
    public Optional<User> findById(Integer id) {
        return userService.findById(id);
    }
    
    public CommonResponseDTO updateOne(Integer id, UserDTO user) {
        return userService.updateOne(id, user);
    }
}
