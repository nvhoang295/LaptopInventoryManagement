/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaentity;

/**
 *
 * @author nm204
 */
public class SoundTech {
    private Integer soundTechId;
    private String name;
    private Integer productId;

    public SoundTech() {
    }

    public SoundTech(Integer soundTechId, String name, Integer productId) {
        this.soundTechId = soundTechId;
        this.name = name;
        this.productId = productId;
    }

    public Integer getSoundTechId() {
        return soundTechId;
    }

    public void setSoundTechId(Integer soundTechId) {
        this.soundTechId = soundTechId;
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
