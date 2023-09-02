package com.example.thechathon22.datamodels.promoOfferModels;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;

@Table("promo_offer_data")
public class PromoOffer {

    @PrimaryKey
    PromoPrimaryKey key;
    @Column
    double currentPrice;
    @Column
    double originalPrice;
    @Column
    Instant startDate;
    @Column
    Instant endDate;

    public PromoPrimaryKey getKey() {
        return key;
    }

    public void setKey(PromoPrimaryKey key) {
        this.key = key;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }


    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

}
