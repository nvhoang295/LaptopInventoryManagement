/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaentity;

/**
 *
 * @author nm204
 */
public class WifiCard {
    private Integer wifiCardId;
    private String name;

    public WifiCard() {
    }

    public WifiCard(Integer wifiCardId, String name) {
        this.wifiCardId = wifiCardId;
        this.name = name;
    }

    public Integer getWifiCardId() {
        return wifiCardId;
    }

    public void setWifiCardId(Integer wifiCardId) {
        this.wifiCardId = wifiCardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
