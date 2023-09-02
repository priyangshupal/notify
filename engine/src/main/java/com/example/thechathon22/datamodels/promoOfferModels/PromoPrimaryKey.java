package com.example.thechathon22.datamodels.promoOfferModels;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class PromoPrimaryKey {
    @PrimaryKeyColumn(name="category", type = PrimaryKeyType.PARTITIONED)
    String category;

    @PrimaryKeyColumn(name = "itemid", type = PrimaryKeyType.CLUSTERED)
    String itemId;

    @PrimaryKeyColumn(name = "store", type = PrimaryKeyType.CLUSTERED)
    String store;

    @PrimaryKeyColumn(name = "discount_percent", type = PrimaryKeyType.CLUSTERED)
    double discountPercent;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public PromoPrimaryKey(String category, String itemId, String store, double discountPercent) {
        this.category = category;
        this.itemId = itemId;
        this.store = store;
        this.discountPercent = discountPercent;
    }

    public PromoPrimaryKey(){}

}
