/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.config;

import com.haui_megatech.*;
import com.haui_megatech.model.*;
import com.haui_megatech.repository.*;
import com.haui_megatech.repository.impl.*;
import java.util.*;

/**
 *
 * @author vieth
 */
public class InitUserData {

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepositoryImpl(new ApplicationContext());
        userRepository.saveAll(new ArrayList<>(List.of(
                User.builder()
                        .username("admin@gmail.com")
                        .password("12345678Abc!")
                        .firstName("Hoàng")
                        .lastName("Nguyễn Việt")
                        .phoneNumber("0336118268")
                        .email("hoangnv.swe@gmail.com")
                        .build()
//                User.builder()
//                        .username("thanhquynh")
//                        .password("123")
//                        .firstName("Quỳnh")
//                        .lastName("Cao Thị Thanh")
//                        .phoneNumber("0336118269")
//                        .email("thanhquynh@gmail.com")
//                        .build(),
//                User.builder()
//                        .username("linhngan")
//                        .password("123")
//                        .firstName("Ngân")
//                        .lastName("Nguyễn Thị Linh")
//                        .phoneNumber("0336118270")
//                        .email("linhngan@gmail.com")
//                        .build(),
//                User.builder()
//                        .username("minhnghia")
//                        .password("123")
//                        .firstName("Nghĩa")
//                        .lastName("Hoàng Minh")
//                        .phoneNumber("0336118271")
//                        .email("minhnghia@gmail.com")
//                        .build(),
//                User.builder()
//                        .username("hoangphuc")
//                        .password("123")
//                        .firstName("Phúc")
//                        .lastName("Phạm Hoàng")
//                        .phoneNumber("0336118272")
//                        .email("hoangphuc@gmail.com")
//                        .build()
        )));

    }
}
