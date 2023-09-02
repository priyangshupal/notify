package com.example.thechathon22.datamodels.promoOfferModels;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoRepository extends CrudRepository<PromoOffer, PromoPrimaryKey> {
    @Query("select * from promo_offer_data where category= ?0;")
    Iterable<PromoOffer> findPromoOfferByCategory(String category);
}
