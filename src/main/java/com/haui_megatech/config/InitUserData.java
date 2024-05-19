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
        ApplicationContext.initAbsUsersDataPath();
        User.setCounter(1);
        UserRepository userRepository = new UserRepositoryImpl();
        User.setCounter(1);
        userRepository.saveAll(
                List.of(
                        User.builder()
                                .username("viethoang")
                                .password("123")
                                .firstName("Hoang")
                                .lastName("Nguyen Viet")
                                .phoneNumber("0336118268")
                                .email("hoangnv.swe@gmail.com")
                                .gender(Gender.MALE)
                                .dateOfBirth(new Date())
                                .whenCreated(new Date())
                                .build(),
                        
                        User.builder()
                                .username("thanhquynh")
                                .password("123")
                                .firstName("Quynh")
                                .lastName("Cao Thi Thanh")
                                .phoneNumber("0336118269")
                                .email("thanhquynh@gmail.com")
                                .gender(Gender.FEMALE)
                                .dateOfBirth(new Date())
                                .whenCreated(new Date())
                                .build(),
                        
                        User.builder()
                                .username("linhngan")
                                .password("123")
                                .firstName("Ngan")
                                .lastName("Nguyen Thi Linh")
                                .phoneNumber("0336118270")
                                .email("linhngan@gmail.com")
                                .gender(Gender.FEMALE)
                                .dateOfBirth(new Date())
                                .whenCreated(new Date())
                                .build(),
                        
                        User.builder()
                                .username("minhnghia")
                                .password("123")
                                .firstName("Nghia")
                                .lastName("Hoang Minh")
                                .phoneNumber("0336118271")
                                .email("minhnghia@gmail.com")
                                .gender(Gender.MALE)
                                .dateOfBirth(new Date())
                                .whenCreated(new Date())
                                .build(),
                        
                        User.builder()
                                .username("hoangphuc")
                                .password("123")
                                .firstName("Phuc")
                                .lastName("Pham Hoang Phuc")
                                .phoneNumber("0336118272")
                                .email("hoangphuc@gmail.com")
                                .gender(Gender.MALE)
                                .dateOfBirth(new Date())
                                .whenCreated(new Date())
                                .build()
                )
        );

    }
}
