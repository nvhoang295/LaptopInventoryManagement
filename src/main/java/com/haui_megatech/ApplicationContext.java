/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech;

import com.haui_megatech.model.User;
import com.haui_megatech.repository.*;
import com.haui_megatech.repository.impl.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vieth
 */
public class ApplicationContext {

    // MEDIUM PRIORITY
    private static final UserRepository userRepository = new UserRepositoryImpl();
    private static final Map<String, Object> beans = new HashMap<>();
    private static User loginedUser;

    public static final String REDUNDANT_PATH_PREFIX = "file:/";
    public final String ABS_MAIN_CLASS_PATH;
    public final String ABS_ROOT_PROJECT_PATH;

    public final String REL_USERS_DATA_PATH;

    public final String ABS_USERS_DATA_PATH;

    public String getAbsUsersDataPath() {
        return ABS_USERS_DATA_PATH;
    }

    public static void putBean(String beanName, Object bean) {
        beans.put(beanName, bean);
    }

    public static Object getBean(String beanName) {
        return beans.get("beanName");
    }

    public static User getLoginedUser() {
        return loginedUser;
    }

    public static void setLoginedUser(User user) {
        loginedUser = user;
    }

    public ApplicationContext() {
        ABS_MAIN_CLASS_PATH = removeRedundantPathPrefix(Main.class.getResource("").toString());
        ABS_ROOT_PROJECT_PATH = ABS_MAIN_CLASS_PATH.substring(0, ABS_MAIN_CLASS_PATH.indexOf("/target"));
        REL_USERS_DATA_PATH = "/src/main/java/com/haui_megatech/data/users.dat";
        ABS_USERS_DATA_PATH = ABS_ROOT_PROJECT_PATH + REL_USERS_DATA_PATH;
        

    }
    
    public void initCounter() {
        ArrayList<User> users = new ArrayList<>(userRepository.getAll());
        User.counter = users.isEmpty() ? 0 : users.getLast().getId();
    }

    private static String removeRedundantPathPrefix(String s) {
        return s.substring(REDUNDANT_PATH_PREFIX.length());
    }

}
