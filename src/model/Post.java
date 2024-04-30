/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pjsalecomputer;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class Post {
    private int postId;
    private int productId;
    private String link;
    private String mainImage;
    private String content;
    private Date whenCreated;
    private Date lastUpdated;

    public Post(int postId, int productId, String link, String mainImage, String content, Date whenCreated, Date lastUpdated) {
        this.postId = postId;
        this.productId = productId;
        this.link = link;
        this.mainImage = mainImage;
        this.content = content;
        this.whenCreated = whenCreated;
        this.lastUpdated = lastUpdated;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    
}
