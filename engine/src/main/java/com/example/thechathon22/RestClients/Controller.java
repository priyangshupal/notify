package com.example.thechathon22.RestClients;

import com.example.thechathon22.datamodels.itemModels.ItemRepository;
import com.example.thechathon22.datamodels.itemModels.Items;
import com.example.thechathon22.datamodels.promoOfferModels.PromoOffer;
import com.example.thechathon22.datamodels.promoOfferModels.PromoRepository;
import com.example.thechathon22.datamodels.requestResponse.AppNotifResponse;
import com.example.thechathon22.datamodels.requestResponse.Categories;
import com.example.thechathon22.datamodels.requestResponse.PromoPageResponse;
import com.example.thechathon22.datamodels.userModels.UserData;
import com.example.thechathon22.datamodels.userModels.UserRepository;
import com.example.thechathon22.process.GeofenceBreachNotifProcessor;
import com.example.thechathon22.process.PromoPageProcessor;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/techathon22")
public class Controller {
    @Autowired
    PromoRepository promoRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    ItemRepository itemRepo;

    @Autowired
    GeofenceBreachNotifProcessor geofenceBreachNotifProcessor;

    @Autowired
    PromoPageProcessor promoPageProcessor;

    @GetMapping("/promo/category")
    public Iterable<PromoOffer> findPromoOfferByCategory(@RequestParam String category){
        return promoRepo.findPromoOfferByCategory(category);
    }

    @GetMapping("/user/id")
    public  Iterable<UserData> findUserDataById(@RequestParam String id){
        return userRepo.findUserData(id);
    }

    @GetMapping("/item")
    public Items findItemById(@RequestParam String itemId){
        return itemRepo.findByItemId(itemId);
    }

    @GetMapping("/test")
    public  String testApp(){
        return "SUCCESS";
    }

    @PostMapping("/geofenceBreach")
    public AppNotifResponse sendGeofenceBreachNotif(@RequestParam String userId, @RequestParam String Latitude, String Longitude, String token ) throws FirebaseMessagingException {
        geofenceBreachNotifProcessor.sendNotification(userId, Latitude, Longitude, token);
        AppNotifResponse response = new AppNotifResponse();
        response.setUserId("");
        response.setMessage("");
//        response.setMeta();
        response.setTimestamp(Instant.now());
        return response;
    }

//    @PostMapping("/viewAllOffers")
//    public List<PromoPageResponse> sendAllOffers(@RequestParam String userId, @RequestParam String Latitude, String Longitude, String token ) throws FirebaseMessagingException {
//        String storeName = geofenceBreachNotifProcessor.getStoreName(Latitude,Longitude);
//        promoRepo.findPromoOfferByCategory(storeName);
//
//    }

    @GetMapping("/promopage")
    public Categories getPromoPageDeals(@RequestParam String userId, @RequestParam String Latitude, @RequestParam String Longitude){
    return promoPageProcessor.getPromoDeals(userId, Latitude, Longitude);
    }

}
