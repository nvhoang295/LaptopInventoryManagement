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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Phuc
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExportBill implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    public static Integer counter;
    
    private Integer id;
    private String clientName;
    private String clientPhoneNumber;
    private String clientAddress;
    
    private Double total;
    
    private Date whenCreated;
    private Date lastUpdated;
    
    private User user;
    private ArrayList<ExportBillItem> exportBillItems;
}
