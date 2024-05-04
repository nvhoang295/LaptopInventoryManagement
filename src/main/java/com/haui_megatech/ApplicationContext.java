/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vieth
 */
public class ApplicationContext {
    private static Map<String, Object> beans = new HashMap<>();
    public static String ABS_USERS_DATA_PATH;
    
    public static void putBean(String beanName, Object bean) {
        beans.put(beanName, bean);
    }
    
    public static Object getBean(String beanName) {
        return beans.get("beanName");
    }
    
    public static void init() {
         // file:/D:/Workspace/6_Side_Projects/LaptopInventoryManagement/target/classes/com/haui_megatech/
        String mainClassPath = Main.class.getResource("").toString();
        
        String rootProjectPath = mainClassPath.substring(0, mainClassPath.indexOf("/target"));
        
        ABS_USERS_DATA_PATH = rootProjectPath + "/src/main/java/com/haui_megatech/data/users.dat";
        ABS_USERS_DATA_PATH = ABS_USERS_DATA_PATH.replace("file:/", "");
    }
    
    
}
