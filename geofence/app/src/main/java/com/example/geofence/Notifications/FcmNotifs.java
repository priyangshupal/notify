package com.example.geofence.Notifications;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FcmNotifs extends FirebaseMessagingService {

    private static final String TAG = "FcmNotifs";

    public FcmNotifs() {
        super();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String userToken = task.getResult();

                    SharedPreferences.Editor editor = getSharedPreferences("userTokenPrefs", MODE_PRIVATE).edit();
                    editor.putString("userToken", userToken);
                    editor.apply();
                });
    }
}
