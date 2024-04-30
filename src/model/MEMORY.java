/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btl;

/**
 *
 * @author ASUS
 */
public class MEMORY {
    private int memoryId;
    private double capacity;
    private String type;
    private String bus;

    public MEMORY() {
    }

    
    public MEMORY(int memoryId, double capacity, String type, String bus) {
        this.memoryId = memoryId;
        this.capacity = capacity;
        this.type = type;
        this.bus = bus;
    }

    public int getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(int memoryId) {
        this.memoryId = memoryId;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }
    
    
}
