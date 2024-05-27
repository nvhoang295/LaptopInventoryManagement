/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech;

import com.haui_megatech.model.User;
import com.haui_megatech.repository.*;
import com.haui_megatech.repository.impl.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vieth
 */
public class ApplicationContext {

    // HIGHEST PRIORITY
    public static String ABS_USERS_DATA_PATH = initAbsUsersDataPath();

    // MEDIUM PRIORITY
    private static UserRepository userRepository = new UserRepositoryImpl();
    private static Map<String, Object> beans = new HashMap<>();
    private static User loginedUser;

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

    public static void initAll() {

        // MEDIUM PRIORITY
        User.setCounter(userRepository.getAll().getLast().getId());

    }

    public static String initAbsUsersDataPath() {
        String mainClassPath = Main.class.getResource("").toString();
        String rootProjectPath = mainClassPath.substring(0, mainClassPath.indexOf("/target"));
        String absUsersDataPath = rootProjectPath + "/src/main/java/com/haui_megatech/data/users.dat";
        return absUsersDataPath.replace("file:/", "");
    }

}
