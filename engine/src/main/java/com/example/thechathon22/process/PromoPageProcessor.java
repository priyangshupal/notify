package com.example.thechathon22.process;

import com.example.thechathon22.datamodels.itemModels.ItemRepository;
import com.example.thechathon22.datamodels.itemModels.Items;
import com.example.thechathon22.datamodels.promoOfferModels.PromoOffer;
import com.example.thechathon22.datamodels.requestResponse.Categories;
import com.example.thechathon22.datamodels.requestResponse.PromoPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PromoPageProcessor {
    @Autowired
    RecommendationProcessor recommendationProcessor;

    @Autowired
    ItemRepository itemRepository;

    public Categories getPromoDeals(String userId, String latitude, String longitude){
        Categories categories = new Categories();
        List<PromoPageResponse> category = new ArrayList<>();
        HashMap<String, Integer> catMap = recommendationProcessor.createCategoryMap(userId);
        catMap = recommendationProcessor.sortByValue(catMap);
        for(Map.Entry<String , Integer> entry : catMap.entrySet()){
            PromoPageResponse promo = new PromoPageResponse();
            PromoOffer promoOffer = recommendationProcessor.fetchDealForCategory(entry.getKey());
            Items item = itemRepository.findByItemId(promoOffer.getKey().getItemId());
            promo.setCurrentPrice("$"+String.valueOf(promoOffer.getCurrentPrice()));
            promo.setOriginalPrice("$"+String.valueOf(promoOffer.getOriginalPrice()));
            promo.setImageURL(item.getImageUrl());
            promo.setName(entry.getKey());
            promo.setItemDiscount(String.valueOf(promoOffer.getKey().getDiscountPercent())+"%");
            promo.setItemName(item.getItemName());
            category.add(promo);
        }
        categories.setCategories(category);
        return categories;

    }
}
