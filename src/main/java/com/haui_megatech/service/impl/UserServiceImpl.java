/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service.impl;

import com.haui_megatech.model.User;
import com.haui_megatech.service.*;
import com.haui_megatech.repository.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author vieth
 */
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getList() {
        return userRepository.getList();
    }

    @Override
    public List<User> searchList(String keyword) {
        return this.getList().parallelStream()
                .filter(item -> {
                    StringBuilder temp = new StringBuilder();
                    temp.append(item.getUsername())
                            .append(item.getFirstName())
                            .append(item.getLastName())
                            .append(item.getPhoneNumber())
                            .append(item.getEmail());
                    return temp.toString().contains(keyword);
                })
                .collect(Collectors.toList());
    }

}
