package com.example.thechathon22.process;

import com.example.thechathon22.datamodels.promoOfferModels.PromoOffer;
import com.example.thechathon22.datamodels.promoOfferModels.PromoRepository;
import com.example.thechathon22.datamodels.userModels.UserData;
import com.example.thechathon22.datamodels.userModels.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RecommendationProcessor {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PromoRepository promoRepository;

    public String fetchUserCategory(String userId){
        Iterable<UserData> usersData = userRepository.findUserData(userId);
        Map<String, Integer> catMap = new HashMap<>();
        for(UserData userData: usersData){
            if(catMap.containsKey(userData.getCategory())){
                catMap.put(userData.getCategory(),catMap.get(userData.getCategory())+1 );
            }
            else{
                catMap.put(userData.getCategory(),1);
            }
            }
        int max = 0;
        String res="";
        for(Map.Entry<String , Integer> entry: catMap.entrySet()){
            if(entry.getValue()>max)
            {
                res = entry.getKey();
                max = entry.getValue();
            }
        }
        return res;
    }

    public PromoOffer fetchDealForCategory(String category){
        Iterable<PromoOffer> offers = promoRepository.findPromoOfferByCategory(category);
        return  offers.iterator().next();

    }

    public HashMap<String, Integer> createCategoryMap(String userId){
        Iterable<UserData> usersData = userRepository.findUserData(userId);
        HashMap<String, Integer> catMap = new HashMap<>();
        for(UserData userData: usersData){
            if(catMap.containsKey(userData.getCategory())){
                catMap.put(userData.getCategory(),catMap.get(userData.getCategory())+1 );
            }
            else{
                catMap.put(userData.getCategory(),1);
            }
        }
        return catMap;
    }
    public  HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list
                = new LinkedList<Map.Entry<String, Integer> >(
                hm.entrySet());

        // Sort the list using lambda expression
        Collections.sort(
                list,
                (i1,
                 i2) -> i1.getValue().compareTo(i2.getValue()));

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp
                = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
