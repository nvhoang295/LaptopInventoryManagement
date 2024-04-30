/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pjsalecomputer;

/**
 *
 * @author Admin
 */
public class FeedbackImage {
    private int imageID;
    private String link;
    private int productID;
    private int userID;

    public FeedbackImage(int imageID, String link, int productID, int userID) {
        this.imageID = imageID;
        this.link = link;
        this.productID = productID;
        this.userID = userID;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    
}
