/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.model;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 * @author vieth
 */
public class InventoryItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    public static Integer counter;
    
    private Integer id;
    private Float importPrice;
    private Integer totalQuantity;
    
    private Product product;
}
