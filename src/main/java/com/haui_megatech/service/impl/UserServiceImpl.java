/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service.impl;

import com.haui_megatech.constant.ErrorMessage;
import com.haui_megatech.constant.SuccessMessage;
import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.dto.UserDTO;
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
    public CommonResponseDTO<List<User>> getList() {
        return CommonResponseDTO
                .<List<User>>builder()
                .data(userRepository.getAll())
                .build();
    }

    @Override
    public CommonResponseDTO<List<User>> searchList(String keyword) {
        return CommonResponseDTO
                .<List<User>>builder()
                .data(userRepository
                        .getAll()
                        .parallelStream()
                        .filter(item -> {
                            return new StringBuilder()
                                    .append(item.getUsername())
                                    .append(item.getFirstName())
                                    .append(item.getLastName())
                                    .append(item.getPhoneNumber())
                                    .append(item.getEmail())
                                    .toString().toLowerCase()
                                    .contains(keyword.toLowerCase());
                        })
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public CommonResponseDTO addOne(User user) {
        user.setWhenCreated(new Date());
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
    
    @Override
    public CommonResponseDTO addList(ArrayList<User> users) {
        ArrayList<User> savedUsers = userRepository.saveAll(users);
        return CommonResponseDTO
                .builder()
                .success(Boolean.TRUE)
                .message("Lưu thành công " + savedUsers.size() + " người dùng.")
                .build();
    }

    @Override
    public CommonResponseDTO deleteOne(Integer id) {
        Optional<User> found = userRepository.findById(id);
        if (found.isEmpty()) {
            return CommonResponseDTO
                    .builder()
                    .success(Boolean.FALSE)
                    .message(ErrorMessage.User.NOT_FOUND)
                    .build();
        }

        userRepository.deleteById(id);

        return CommonResponseDTO
                .builder()
                .message(SuccessMessage.User.DELETED)
                .success(Boolean.TRUE)
                .build();
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }
    
    @Override
    public CommonResponseDTO updateOne(Integer id, UserDTO user) {
        Optional<User> found = this.findById(id);
        if (found.isEmpty()) 
            return CommonResponseDTO
                    .builder()
                    .success(Boolean.FALSE)
                    .message(ErrorMessage.User.NOT_FOUND)
                    .build();
        
        User foundUser = found.get();
        foundUser.setFirstName(user.firstName());
        foundUser.setLastName(user.lastName());
        foundUser.setPhoneNumber(user.phoneNumber());
        foundUser.setEmail(user.email());
        foundUser.setLastUpdated(new Date());
        
        Optional<User> updatedUser = userRepository.save(foundUser);
        return CommonResponseDTO
                .builder()
                .message(SuccessMessage.User.UPDATED)
                .success(Boolean.TRUE)
                .build();
    }
    
}
