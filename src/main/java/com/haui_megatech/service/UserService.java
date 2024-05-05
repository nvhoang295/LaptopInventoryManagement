/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service;

import com.haui_megatech.model.*;
import java.util.*;

/**
 *
 * @author vieth
 */
public interface UserService {
    List<User> getList();
    List<User> searchList(String keyword);
}
