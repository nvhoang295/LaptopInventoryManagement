/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech;

import com.haui_megatech.model.User;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vieth
 */
public class ApplicationContext {
    private static Map<String, Object> beans = new HashMap<>();
    public static String ABS_USERS_DATA_PATH = init();
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
    
    public static String init() {
         // file:/D:/Workspace/6_Side_Projects/LaptopInventoryManagement/target/classes/com/haui_megatech/
        String mainClassPath = Main.class.getResource("").toString();
        
        String rootProjectPath = mainClassPath.substring(0, mainClassPath.indexOf("/target"));
        
        String absUsersDataPath = rootProjectPath + "/src/main/java/com/haui_megatech/data/users.dat";
        return absUsersDataPath.replace("file:/", "");
    }
    
    
}
