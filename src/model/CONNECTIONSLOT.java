/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btl;

/**
 *
 * @author ASUS
 */
public class CONNECTIONSLOT {
    private int storageId;
    private int productId;

    public CONNECTIONSLOT() {
    }

    public CONNECTIONSLOT(int storageId, int productId) {
        this.storageId = storageId;
        this.productId = productId;
    }

    public int getStorageId() {
        return storageId;
    }

    public void setStorageId(int storageId) {
        this.storageId = storageId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    
    
}
