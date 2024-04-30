/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaentity;

/**
 *
 * @author nm204
 */
public class OperatingSystem {
    private Integer oSId;
    private String name;

    public OperatingSystem() {
    }

    public OperatingSystem(Integer oSId, String name) {
        this.oSId = oSId;
        this.name = name;
    }

    public Integer getoSId() {
        return oSId;
    }

    public void setoSId(Integer oSId) {
        this.oSId = oSId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
