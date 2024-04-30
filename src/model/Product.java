/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pjsalecomputer;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Product {
    private int productId;
    private String name;
    private double currentPrice;
    private double oldPrice;
    private double discountPercent;
    private String mainImage;
    private int dailyViews;
    private int weeklyViews;
    private int monthlyViews;
    private int totalViews;
    private int dailySold;
    private int weeklySold;
    private int monthlySold;
    private int totalSold;
    private boolean imported;
    private int remained;
    private Date launchDate;
    private Date lastUpdated;
    private Date whenCreated;
    private int brandID;
    private int osID;
    
    private List<Post> posts;
    private List<ProductImage> productImages;
    private List<Feedback> feedbacks;

    public Product(int productId, String name, double currentPrice, double oldPrice, double discountPercent, String mainImage, int dailyViews, int weeklyViews, int monthlyViews, int totalViews, int dailySold, int weeklySold, int monthlySold, int totalSold, boolean imported, int remained, Date launchDate, Date lastUpdated, Date whenCreated, int brandID, int osID) {
        this.productId = productId;
        this.name = name;
        this.currentPrice = currentPrice;
        this.oldPrice = oldPrice;
        this.discountPercent = discountPercent;
        this.mainImage = mainImage;
        this.dailyViews = dailyViews;
        this.weeklyViews = weeklyViews;
        this.monthlyViews = monthlyViews;
        this.totalViews = totalViews;
        this.dailySold = dailySold;
        this.weeklySold = weeklySold;
        this.monthlySold = monthlySold;
        this.totalSold = totalSold;
        this.imported = imported;
        this.remained = remained;
        this.launchDate = launchDate;
        this.lastUpdated = lastUpdated;
        this.whenCreated = whenCreated;
        this.brandID = brandID;
        this.osID = osID;
    }
    
    public Product(List<Post> posts, List<ProductImage> productImages, List<Feedback> feedbacks) {
        this.posts = posts;
        this.productImages = productImages;
        this.feedbacks = feedbacks;
    }
    
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public int getDailyViews() {
        return dailyViews;
    }

    public void setDailyViews(int dailyViews) {
        this.dailyViews = dailyViews;
    }

    public int getWeeklyViews() {
        return weeklyViews;
    }

    public void setWeeklyViews(int weeklyViews) {
        this.weeklyViews = weeklyViews;
    }

    public int getMonthlyViews() {
        return monthlyViews;
    }

    public void setMonthlyViews(int monthlyViews) {
        this.monthlyViews = monthlyViews;
    }

    public int getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(int totalViews) {
        this.totalViews = totalViews;
    }

    public int getDailySold() {
        return dailySold;
    }

    public void setDailySold(int dailySold) {
        this.dailySold = dailySold;
    }

    public int getWeeklySold() {
        return weeklySold;
    }

    public void setWeeklySold(int weeklySold) {
        this.weeklySold = weeklySold;
    }

    public int getMonthlySold() {
        return monthlySold;
    }

    public void setMonthlySold(int monthlySold) {
        this.monthlySold = monthlySold;
    }

    public int getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(int totalSold) {
        this.totalSold = totalSold;
    }

    public boolean isImported() {
        return imported;
    }

    public void setImported(boolean imported) {
        this.imported = imported;
    }

    public int getRemained() {
        return remained;
    }

    public void setRemained(int remained) {
        this.remained = remained;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public int getOsID() {
        return osID;
    }

    public void setOsID(int osID) {
        this.osID = osID;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post post) {
        posts.add(post);
    }
    
    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public void addProductImage(ProductImage productImage) {
        productImages.add(productImage);
    }
    
    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
    
    public void addFeedback(Feedback feedback) {
        feedbacks.add(feedback);
    }
}
