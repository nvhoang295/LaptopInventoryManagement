/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaentity;

/**
 *
 * @author nm204
 */
public class Port {
    private Integer portId;
    private String name;
    private Integer productId;

    public Port() {
    }

    public Port(Integer portId, String name, Integer productId) {
        this.portId = portId;
        this.name = name;
        this.productId = productId;
    }

    public Integer getPortId() {
        return portId;
    }

    public void setPortId(Integer portId) {
        this.portId = portId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    
}
