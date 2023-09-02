package com.example.thechathon22.datamodels.userModels;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.time.Instant;

@PrimaryKeyClass
public class UserPrimaryKey {

    @PrimaryKeyColumn(name = "userId", type = PrimaryKeyType.PARTITIONED)
    String userId;
    @PrimaryKeyColumn(name = "eventDate", type = PrimaryKeyType.CLUSTERED)
    Instant eventDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getEventDate() {
        return eventDate;
    }

    public void setEventDate(Instant eventDate) {
        this.eventDate = eventDate;
    }

    public UserPrimaryKey(String userId, Instant eventDate) {
        this.userId = userId;
        this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        return "UserPrimaryKey{" +
                "userId='" + userId + '\'' +
                ", eventDate=" + eventDate +
                '}';
    }
}
