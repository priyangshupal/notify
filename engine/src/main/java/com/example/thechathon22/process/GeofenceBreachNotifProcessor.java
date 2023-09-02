package com.example.thechathon22.process;

import com.example.thechathon22.datamodels.itemModels.ItemRepository;
import com.example.thechathon22.datamodels.itemModels.Items;
import com.example.thechathon22.datamodels.promoOfferModels.PromoOffer;
import com.example.thechathon22.datamodels.requestResponse.GeoBreachNotification;
import com.example.thechathon22.datamodels.requestResponse.TimeFrame;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.firebase.messaging.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
 * "Hey <user>, you're so close to <walmart store>!
 *  <item1> is <$y> less than the avg <timeframe>. Pick now.."
 */

@Component
public class GeofenceBreachNotifProcessor {

    @Autowired
    RecommendationProcessor recommendationProcessor;

    @Autowired
    ItemRepository itemRepository;

    /**
     * Method to process and send first notification
     */
    public void sendNotification(String userId, String latitude, String longitude, String token) throws FirebaseMessagingException {
        GeoBreachNotification notifContents = createGeoBreachNotif(userId, latitude, longitude);
        Notification notification = Notification
                .builder()
                .setTitle("Hey "+userId+", you're very close to Walmart "+notifContents.getStoreName())
                .setBody(notifContents.getItemName()+" is $"+notifContents.getPriceDifference()+" less in the past "+notifContents.getTimevalue()+" "+notifContents.getTimeFrame().name()+"!!! Pick now..")
                .build();

        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .build();
        String response = FirebaseMessaging.getInstance().send(message);
        System.out.println(response);
    }
    public GeoBreachNotification createGeoBreachNotif(String userId, String latitude, String longitude){
        String categoryOfInterest = recommendationProcessor.fetchUserCategory(userId);
        PromoOffer promoOffer = recommendationProcessor.fetchDealForCategory(categoryOfInterest);
        Items item = itemRepository.findByItemId(promoOffer.getKey().getItemId());
        GeoBreachNotification notif = new GeoBreachNotification();
        notif.setItemName(item.getItemName());
        notif.setPriceDifference(promoOffer.getOriginalPrice()- promoOffer.getCurrentPrice());
        // iterate through price history and identify timestamp when price was lower than current discounted price.
        // find difference between current and that tiemstamp.
        notif.setTimevalue(2);
        notif.setTimeFrame(TimeFrame.DAYS);
        notif.setStoreName(getStoreName(latitude,longitude));
        return notif;
    }

    public String getStoreName(String latitude, String longitude){
        return "Bentoville";
    }


}
