/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.repository.impl;

import com.haui_megatech.ApplicationContext;
import com.haui_megatech.Main;
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

/**
 *
 * @author vieth
 */
public class UserRepositoryImpl implements UserRepository {
    private final String absDataPath;
    
    public UserRepositoryImpl(String absDataPath) {
        this.absDataPath = absDataPath;
    }
    
    @Override
    public Optional<User> save(User user) {
        List<User> users;
       
        try (ObjectInputStream ois = new ObjectInputStream((new FileInputStream(absDataPath)))) {
            users = (List<User>) ois.readObject();
            if (users == null) {
                users = new ArrayList<>();
            }
            users.add(user);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(absDataPath))) {
            oos.writeObject(users);
            return Optional.of(user);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> saveAll(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(absDataPath))) {
            oos.writeObject(users);
            return users;
        } catch (IOException e) {
            return List.of();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        List<User> users = this.getList();
        return users.parallelStream().filter(item -> item.getUsername().equals(username)).findFirst();
    }

    @Override
    public List<User> getList() {
        List<User> users;
        try (ObjectInputStream ois = new ObjectInputStream((new FileInputStream(absDataPath)))) {
            users = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
    

}
