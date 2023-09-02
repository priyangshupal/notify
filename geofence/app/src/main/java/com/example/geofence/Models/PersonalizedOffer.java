package com.example.geofence.Models;

public class PersonalizedOffer {
    String categoryImageURL, category, discount_percent;

    public PersonalizedOffer(String categoryImageURL, String category, String discount_percent) {
        this.categoryImageURL = categoryImageURL;
        this.category = category;
        this.discount_percent = discount_percent;
    }

    public String getCategoryImageURL() {
        return categoryImageURL;
    }

    public void setCategoryImageURL(String categoryImageURL) {
        this.categoryImageURL = categoryImageURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDiscount_percent() {
        return discount_percent;
    }

    public void setDiscount_percent(String discount_percent) {
        this.discount_percent = discount_percent;
    }
}
