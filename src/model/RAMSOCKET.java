/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btl;

/**
 *
 * @author ASUS
 */
public class RAMSOCKET {
    private int memoryId;
    private int productId;

    public RAMSOCKET() {
    }

    public RAMSOCKET(int memoryId, int productId) {
        this.memoryId = memoryId;
        this.productId = productId;
    }

    public int getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(int memoryId) {
        this.memoryId = memoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    
}
