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

/**
 *
 * @author vieth
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Provider implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    public static Integer counter;
    private Integer id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private Date whenCreated;
    private Date lastUpdated;
}
