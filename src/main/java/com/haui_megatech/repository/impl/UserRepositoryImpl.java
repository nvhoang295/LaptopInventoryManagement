/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.repository.impl;

import com.haui_megatech.ApplicationContext;
import com.haui_megatech.Main;
import com.haui_megatech.constant.ErrorMessageConstant;
import com.haui_megatech.dto.ListItemsResponseDTO;
import com.haui_megatech.model.User;
import com.haui_megatech.repository.UserRepository;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author vieth
 */
public class UserRepositoryImpl implements UserRepository {

    private final String ABS_DATA_PATH = ApplicationContext.ABS_USERS_DATA_PATH;
    
    private boolean saveToDisk(ArrayList<User> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ABS_DATA_PATH))) {
            oos.writeObject(list);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    @Override
    public boolean add(User user) {
        ArrayList<User> list = this.getAll();
        list.add(user);

        return this.saveToDisk(list);
    }

    @Override
    public boolean addAll(ArrayList<User> users) {
        ArrayList<User> list = this.getAll();
        list.addAll(users);
        
        return this.saveToDisk(list);
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> users;
        try (ObjectInputStream ois = new ObjectInputStream((new FileInputStream(ABS_DATA_PATH)))) {
            users = (ArrayList<User>) ois.readObject();
            if (users == null) users = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

}
