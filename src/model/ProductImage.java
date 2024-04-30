/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pjsalecomputer;

/**
 *
 * @author Admin
 */
public class ProductImage {
    private int imageID;
    private int productID;
    private String link;

    public ProductImage(int imageID, int productID, String link) {
        this.imageID = imageID;
        this.productID = productID;
        this.link = link;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    
}
