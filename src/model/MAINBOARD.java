/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btl;

/**
 *
 * @author ASUS
 */
public class MAINBOARD {
    private int productId;
    private int discreteGraphicCardId;
    private int processorId;
    private double maxMemoryCapacity;
    private double maxStorageCapacity;
    
    public MAINBOARD(){};

    public MAINBOARD(int productId, int discreteGraphicCardId, int processorId, double maxMemoryCapacity, double maxStorageCapacity) {
        this.productId = productId;
        this.discreteGraphicCardId = discreteGraphicCardId;
        this.processorId = processorId;
        this.maxMemoryCapacity = maxMemoryCapacity;
        this.maxStorageCapacity = maxStorageCapacity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getDiscreteGraphicCardId() {
        return discreteGraphicCardId;
    }

    public void setDiscreteGraphicCardId(int discreteGraphicCardId) {
        this.discreteGraphicCardId = discreteGraphicCardId;
    }

    public int getProcessorId() {
        return processorId;
    }

    public void setProcessorId(int processorId) {
        this.processorId = processorId;
    }

    public double getMaxMemoryCapacity() {
        return maxMemoryCapacity;
    }

    public void setMaxMemoryCapacity(double maxMemoryCapacity) {
        this.maxMemoryCapacity = maxMemoryCapacity;
    }

    public double getMaxStorageCapacity() {
        return maxStorageCapacity;
    }

    public void setMaxStorageCapacity(double maxStorageCapacity) {
        this.maxStorageCapacity = maxStorageCapacity;
    }
    
}
