package com.example.Estore.Estore.Ui.Model.Response.ReviewRequest;

public class ReviewRest {
    private String productName;
    private String review;
    private Integer rating;
    private String userName;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }



    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void getUserName(String firstName) {
    }

}
