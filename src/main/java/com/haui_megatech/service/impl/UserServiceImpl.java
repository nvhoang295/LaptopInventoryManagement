/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service.impl;

import com.haui_megatech.constant.ErrorMessage;
import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.dto.ListItemsResponseDTO;
import com.haui_megatech.model.User;
import com.haui_megatech.service.*;
import com.haui_megatech.repository.*;
import java.util.*;
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
    public ListItemsResponseDTO<User> getList() {
        return ListItemsResponseDTO
                .<User>builder()
                .items(userRepository.getAll())
                .build();
    }

    @Override
    public ListItemsResponseDTO<User> searchList(String keyword) {
        return ListItemsResponseDTO
                .<User>builder()
                .items(userRepository
                        .getAll()
                        .parallelStream()
                        .filter(item -> {
                            return new StringBuilder()
                                    .append(item.getUsername())
                                    .append(item.getFirstName())
                                    .append(item.getLastName())
                                    .append(item.getPhoneNumber())
                                    .append(item.getEmail())
                                    .toString()
                                    .contains(keyword);
                        })
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public CommonResponseDTO addOne(User user) {
        var result = userRepository.save(user);
        return result.isPresent()
                ? CommonResponseDTO
                        .builder()
                        .success(true)
                        .build()
                : CommonResponseDTO
                        .builder()
                        .success(false)
                        .message(ErrorMessage.User.SAVE)
                        .build();
    }

}
