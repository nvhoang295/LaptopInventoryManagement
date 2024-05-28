/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech;

import com.haui_megatech.view.*;
import com.haui_megatech.model.*;
import com.haui_megatech.repository.*;
import com.haui_megatech.repository.impl.*;
import java.io.PrintWriter;
import java.util.*;

/**
 *
 * @author vieth
 */
public class Main {
    
    public static void main(String[] args) {
        final ApplicationContext context = new ApplicationContext();
        context.initCounter();
        
        Login.main(new String[]{});

    }

}
