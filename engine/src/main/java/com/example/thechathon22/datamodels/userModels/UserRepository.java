package com.example.thechathon22.datamodels.userModels;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserData, UserPrimaryKey> {
    @Query("select * from user_data where userId=?0;")
    Iterable<UserData> findUserData(String userId);
}
