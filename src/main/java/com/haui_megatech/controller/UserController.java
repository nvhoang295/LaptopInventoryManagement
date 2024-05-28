/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.controller;

import com.haui_megatech.model.User;
import com.haui_megatech.service.UserService;
import com.haui_megatech.dto.*;


/**
 *
 * @author vieth
 */
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    public ListItemsResponseDTO<User> getList() {
        return userService.getList();
    }
    
    public ListItemsResponseDTO<User> searchList(String keyword) {
        return userService.searchList(keyword);
    }
    
    public CommonResponseDTO addOne(User user) {
        return userService.addOne(user);
    }
}
