package com.example.geofence.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.geofence.LoginActivity;
import com.example.geofence.PromotionActivity;
import com.example.geofence.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class AccountFragment extends Fragment {

    private static final String TAG = "AccountFragment";

    ConstraintLayout promotions_layout, sign_out_layout, lists_layout;
    TextView welcome_user;
    RelativeLayout blocking;
    ProgressBar progress_circular;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        promotions_layout = rootView.findViewById(R.id.promotions_layout);
        sign_out_layout = rootView.findViewById(R.id.sign_out_layout);
        welcome_user = rootView.findViewById(R.id.welcome_user);
        progress_circular = rootView.findViewById(R.id.progress_circular);
        blocking = rootView.findViewById(R.id.blocking);
        lists_layout = rootView.findViewById(R.id.lists_layout);

        promotions_layout.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), PromotionActivity.class));
        });
        sign_out_layout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });
        lists_layout.setOnClickListener(view -> {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }

                            // Get new FCM registration token
                            String token = task.getResult();

                            Log.d(TAG, "onComplete: userToken" + token);
                        }
                    });
        });

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    if(dataSnapshot.getKey().equals("firstName")) {
                        welcome_user.setText("Hi, " + dataSnapshot.getValue().toString());
                        progress_circular.setVisibility(View.GONE);
                        blocking.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return rootView;
    }
}