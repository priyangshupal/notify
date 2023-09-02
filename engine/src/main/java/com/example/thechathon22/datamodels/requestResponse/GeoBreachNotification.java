package com.example.thechathon22.datamodels.requestResponse;

import org.springframework.stereotype.Component;

@Component
public class GeoBreachNotification {
    String  itemName;
    double priceDifference;
    int timevalue;
    TimeFrame timeFrame;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    String storeName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPriceDifference() {
        return priceDifference;
    }

    public void setPriceDifference(double priceDifference) {
        this.priceDifference = priceDifference;
    }

    public int getTimevalue() {
        return timevalue;
    }

    public void setTimevalue(int timevalue) {
        this.timevalue = timevalue;
    }

    public TimeFrame getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(TimeFrame timeFrame) {
        this.timeFrame = timeFrame;
    }
}
