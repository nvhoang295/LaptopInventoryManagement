/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.service;

import com.haui_megatech.dto.ListItemsResponseDTO;
import com.haui_megatech.model.*;
import java.util.*;
import com.haui_megatech.dto.*;

/**
 *
 * @author vieth
 */
public interface UserService {
    ListItemsResponseDTO<User> getList();
    ListItemsResponseDTO<User> searchList(String keyword);
    CommonResponseDTO addOne(User user);
    CommonResponseDTO deleteOne(Integer id);
}
