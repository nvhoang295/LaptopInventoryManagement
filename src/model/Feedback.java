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
public class Feedback {
    private int productID;
    private int userID;
    private String summary;
    private String content;
    private Date whenCreated;
    private int score;
    private int upVoted;
    
    private List<FeedbackImage> feedbackImages;

    public Feedback(int productID, int userID, String summary, String content, Date whenCreated, int score, int upVoted) {
        this.productID = productID;
        this.userID = userID;
        this.summary = summary;
        this.content = content;
        this.whenCreated = whenCreated;
        this.score = score;
        this.upVoted = upVoted;
    }

    public Feedback(List<FeedbackImage> feedbackImages) {
        this.feedbackImages = feedbackImages;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUpVoted() {
        return upVoted;
    }

    public void setUpVoted(int upVoted) {
        this.upVoted = upVoted;
    }

    public List<FeedbackImage> getFeedbackImages() {
        return feedbackImages;
    }

    public void setFeedbackImages(List<FeedbackImage> feedbackImages) {
        this.feedbackImages = feedbackImages;
    }
    
    public void addFeedbackImage(FeedbackImage feedbackImage) {
        feedbackImages.add(feedbackImage);
    }
}
