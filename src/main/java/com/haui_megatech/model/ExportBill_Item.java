/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.model;

/**
 *
 * @author Phuc
 */
public class ExportBill_Item {
    private Integer id;
    private ExportBill exportBill;
    private Integer quantity;
    private Double exportPrice;

    public ExportBill_Item(
            Integer id, 
            ExportBill exportBill, 
            Integer quantity, 
            Double exportPrice
    ) {
        this.id = id;
        this.exportBill = exportBill;
        this.quantity = quantity;
        this.exportPrice = exportPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ExportBill getExportBill() {
        return exportBill;
    }

    public void setExportBill(ExportBill exportBill) {
        this.exportBill = exportBill;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getExportPrice() {
        return exportPrice;
    }

    public void setExportPrice(Double exportPrice) {
        this.exportPrice = exportPrice;
    }
    
    
}
