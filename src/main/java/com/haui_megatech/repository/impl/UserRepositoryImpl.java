/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.repository.impl;

import com.haui_megatech.ApplicationContext;
import com.haui_megatech.Main;
import com.haui_megatech.constant.ErrorMessage;
import com.haui_megatech.model.User;
import com.haui_megatech.repository.UserRepository;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author vieth
 */
public class UserRepositoryImpl implements UserRepository {

    private final String ABS_DATA_PATH;
    
    public UserRepositoryImpl() {
        ABS_DATA_PATH = new ApplicationContext().ABS_USERS_DATA_PATH;
        
        initCounter();
    }
    
    private void initCounter() {
        ArrayList<User> users = this.getAll();
        if (users.isEmpty()) {
            User.counter = 0;
        } else {
            User.counter = users.getLast().getId();
        }
    }
    
    private boolean saveToDisk(ArrayList<User> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ABS_DATA_PATH))
        ) {
            oos.writeObject(list);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    private boolean update(User oldInfo, User newInfo) {
        oldInfo.setUsername(newInfo.getUsername());
        oldInfo.setPassword(newInfo.getPassword());
        oldInfo.setFirstName(newInfo.getFirstName());
        oldInfo.setLastName(newInfo.getLastName());
        oldInfo.setPhoneNumber(newInfo.getPhoneNumber());
        oldInfo.setEmail(newInfo.getEmail());
        oldInfo.setLastUpdated(newInfo.getLastUpdated());
        oldInfo.setLoggedIn(newInfo.getLoggedIn());
        oldInfo.setLastLoggedIn(newInfo.getLastLoggedIn());
        return true;
    }
    
    
    private int findIndexById(Integer id) {
        ArrayList<User> list = this.getAll();
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).getId().equals(id)) return i;
        }
        return -1;
    }

    
    @Override
    public Optional<User> findById(Integer id) {
        return this.getAll()
                .parallelStream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        return this.getAll()
                .parallelStream()
                .filter(item -> item.getUsername().equals(username))
                .findFirst();
    }
    
    @Override
    public Optional<User> save(User user) {
        ArrayList<User> list = this.getAll();
        if (user.getId() != null) {
            int foundIndex = this.findIndexById(user.getId());
            User foundUser = list.get(foundIndex);
            update(foundUser, user);
            list.set(foundIndex, foundUser);
            return this.saveToDisk(list) 
                    ? Optional.of(foundUser) 
                    : Optional.empty();
        }
        
        user.setId(++User.counter);
        user.setWhenCreated(new Date());
        list.add(user);
        
        return this.saveToDisk(list) 
                ? Optional.of(user) 
                : Optional.empty();
        
    }

    @Override
    public ArrayList<User> saveAll(ArrayList<User> users) {
        ArrayList<User> savedUsers = new ArrayList<>();
        users.forEach(item -> {
            Optional<User> saved = this.save(item);
            if (saved.isPresent()) savedUsers.add(saved.get());
        });
        return savedUsers;
    }

    @Override
    public ArrayList<User> getAll() {        
        ArrayList<User> users;
        try (ObjectInputStream ois = new ObjectInputStream(
                (new FileInputStream(ABS_DATA_PATH))
        )) {
            users = (ArrayList<User>) ois.readObject();
            if (users == null) users = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList();
        }
        return users;
    }

    @Override
    public void deleteById(int id) {
        ArrayList<User> list = this.getAll();
        list.removeIf(item -> item.getId().equals(id));
        this.saveToDisk(list);
    }

}
