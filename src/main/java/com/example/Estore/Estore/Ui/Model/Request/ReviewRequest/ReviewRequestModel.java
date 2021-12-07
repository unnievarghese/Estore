package com.example.Estore.Estore.Ui.Model.Request.ReviewRequest;

public class ReviewRequestModel {
    private Long productId;
    private Integer rating;
    private String review;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }



    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
