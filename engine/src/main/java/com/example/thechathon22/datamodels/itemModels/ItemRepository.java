package com.example.thechathon22.datamodels.itemModels;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Items ,String> {
    @Query("Select * from item_data where itemId=?0;")
    Items findByItemId(String itemId);

}
