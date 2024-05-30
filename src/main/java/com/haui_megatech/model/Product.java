/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author vieth
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Product implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    public static Integer counter;
    
    private Integer id;
    private String name;    
    private String processor;
    private String memory;
    private String storage;
    private String display;
    private String battery;
    private String card;
    private String weight;
    
    private Date whenCreated;
    private Date lastUpdated;
}
