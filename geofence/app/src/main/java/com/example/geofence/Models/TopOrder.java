package com.example.geofence.Models;

public class TopOrder {
    String categoryImageURL, category, discount_percent, currentPrice, originalPrice, itemName;

    public TopOrder(String categoryImageURL, String category, String discount_percent, String currentPrice, String originalPrice, String itemName) {
        this.categoryImageURL = categoryImageURL;
        this.category = category;
        this.discount_percent = discount_percent;
        this.currentPrice = currentPrice;
        this.originalPrice = originalPrice;
        this.itemName = itemName;
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

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
