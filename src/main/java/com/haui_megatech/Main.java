/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech;

import com.haui_megatech.*;
import com.haui_megatech.model.*;
import com.haui_megatech.repository.*;
import com.haui_megatech.repository.impl.*;
import java.util.*;

/**
 *
 * @author vieth
 */
public class Main {
    
    public static void main(String[] args) {
       ApplicationContext.init();
//       User.setCounter(1);
       
//        System.out.println(ApplicationContext.ABS_USERS_DATA_PATH);
        System.out.println(new UserRepositoryImpl(ApplicationContext.ABS_USERS_DATA_PATH).getList());
    }
    
}
