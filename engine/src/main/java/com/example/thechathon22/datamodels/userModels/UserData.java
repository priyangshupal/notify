package com.example.thechathon22.datamodels.userModels;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("user_data")
public class UserData {
    @PrimaryKey
    UserPrimaryKey key;
    @Column
    String category;
    @Column
    String itemId;

    public UserPrimaryKey getKey() {
        return key;
    }

    public void setKey(UserPrimaryKey key) {
        this.key = key;
    }

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

    public UserData(UserPrimaryKey key, String category, String itemId) {
        this.key = key;
        this.category = category;
        this.itemId = itemId;
    }

    public  UserData(){

    }
}
