/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaentity;

/**
 *
 * @author nm204
 */
public class DiscreteGraphicsCard {
    private Integer disCreteGraphicsCardId;
    private String name;

    public DiscreteGraphicsCard() {
    }

    public DiscreteGraphicsCard(Integer disCreteGraphicsCardId, String name) {
        this.disCreteGraphicsCardId = disCreteGraphicsCardId;
        this.name = name;
    }

    public Integer getDisCreteGraphicsCardId() {
        return disCreteGraphicsCardId;
    }

    public void setDisCreteGraphicsCardId(Integer disCreteGraphicsCardId) {
        this.disCreteGraphicsCardId = disCreteGraphicsCardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
