package com.example.geofence;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.geofence.Adapters.PersonalizedOfferAdapter;
import com.example.geofence.Models.PersonalizedOffer;
import com.example.geofence.Models.TopOrder;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PromotionActivity extends AppCompatActivity {

    private static final String TAG = "PromotionActivity";
    public static final String IP = "";

    public static final int NUMBER_OF_GRID_COLUMNS = 2;

    private FusedLocationProviderClient fusedLocationClient;
    private FirebaseUser firebaseUser;
    private RecyclerView topPicks, generalPicks;
    private PersonalizedOfferAdapter personalizedOfferAdapter, generalPersonalizedOfferAdapter;
    private List<PersonalizedOffer> mOffers;
    private List<PersonalizedOffer> mOffers1;
    private TextView viewMore, moreOffers;
    private TopOrder topOrder;

    String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        topPicks = findViewById(R.id.topPicks);
        generalPicks = findViewById(R.id.generalPicks);
        viewMore = findViewById(R.id.viewMore);
        moreOffers = findViewById(R.id.moreOffers);
        mOffers = new ArrayList<>();
        mOffers1 = new ArrayList<>();

        if (isLocationPermitted()) {
            setUserLocation();
        }

        SharedPreferences prefs = this.getSharedPreferences("userTokenPrefs", MODE_PRIVATE);
        String userToken = prefs.getString("userToken", "No name defined");

        populatePersonalizedOffers(firebaseUser.getUid());
        populatePersonalizedOffers1();

        topPicks.setHasFixedSize(true);
        topPicks.setLayoutManager(new GridLayoutManager(getApplicationContext(), NUMBER_OF_GRID_COLUMNS));
        personalizedOfferAdapter = new PersonalizedOfferAdapter(getApplicationContext(), mOffers);
        topPicks.setAdapter(personalizedOfferAdapter);

        generalPicks.setHasFixedSize(true);
        generalPicks.setLayoutManager(new GridLayoutManager(getApplicationContext(), NUMBER_OF_GRID_COLUMNS));
        generalPersonalizedOfferAdapter = new PersonalizedOfferAdapter(getApplicationContext(), mOffers1);
        generalPicks.setAdapter(generalPersonalizedOfferAdapter);

        viewMore.setOnClickListener(view -> {
            viewMore.setVisibility(View.GONE);
            generalPicks.setVisibility(View.VISIBLE);
            moreOffers.setVisibility(View.VISIBLE);
        });
//        callPromotionsApi(firebaseUser.getUid());
    }

    private void populatePersonalizedOffers(String userId) {
//        String myUrl = "http://"+ IP + ":8080/techathon22/promopage?userId=" + userId + "&latitude=" + latitude + "&longitude=" + longitude;
        String myUrl = "http://";
        StringRequest myRequest = new StringRequest(Request.Method.GET, myUrl,
                response -> {
                    try {
                        JSONObject myJsonObject = new JSONObject(response);
                        JSONArray categories = myJsonObject.getJSONArray("categories");
                        for(int i=0; i < categories.length(); i++) {
                            JSONObject orderObject = categories.getJSONObject(i);
                            if(i == 0) {
                                topOrder = new TopOrder(
                                        orderObject.getString("imageURL"),
                                        orderObject.getString("name"),
                                        orderObject.getString("itemDiscount"),
                                        orderObject.getString("currentPrice"),
                                        orderObject.getString("originalPrice"),
                                        orderObject.getString("itemName")
                                );
                                continue;
                            }
                            PersonalizedOffer personalizedOffer = new PersonalizedOffer(
                                    orderObject.getString("imageURL"),
                                    orderObject.getString("name"),
                                    orderObject.getString("itemDiscount")
                            );
                            mOffers.add(personalizedOffer);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                volleyError -> Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_SHORT).show()
        );
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(myRequest);
    }

    private void populatePersonalizedOffers1() {
        mOffers1.add(new PersonalizedOffer("", "Electronics", "20%"));
        mOffers1.add(new PersonalizedOffer("", "Electronics", "20%"));
        mOffers1.add(new PersonalizedOffer("", "Electronics", "20%"));
        mOffers1.add(new PersonalizedOffer("", "Electronics", "20%"));
        mOffers1.add(new PersonalizedOffer("", "Electronics", "20%"));
        mOffers1.add(new PersonalizedOffer("", "Electronics", "20%"));
    }

    private void callPromotionsApi(String userId) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://google.com?userId=" + userId + "&latitude=" + latitude + "&longitude=" + longitude;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            Log.d(TAG, "callPromotionsApi: " + response);
        }, error -> {
            Log.e(TAG, "callPromotionsApi: Error: ", error);
        });

        queue.add(stringRequest);
    }

    @SuppressLint("MissingPermission")
    private void setUserLocation() {
        fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }

            @Override
            public boolean isCancellationRequested() {
                return false;
            }
        })
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        Log.d(TAG, "setUserLocation: Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude());
                        latitude = Double.toString(location.getLatitude());
                        longitude = Double.toString(location.getLongitude());
                    } else {
                        Log.d(TAG, "onCreate: location is null");
                    }
                });
    }

    private boolean isLocationPermitted() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}
