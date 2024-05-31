/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author vieth
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ImportBill implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    public static Integer counter;
    
    private Integer id;
    private Float total;
    private Date whenCreated;
    
    private User user;
    private Provider provider;
    private ArrayList<ImportBillItem> importBillItems;
}
