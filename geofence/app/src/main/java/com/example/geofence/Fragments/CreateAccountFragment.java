package com.example.geofence.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.geofence.MainActivity;
import com.example.geofence.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateAccountFragment extends Fragment {

    TextView disclaimer, btn_create_account;
    ImageView back_button;
    EditText firstName, lastName, email, password;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        disclaimer = view.findViewById(R.id.disclaimer);
        back_button = view.findViewById(R.id.back_button);
        firstName = view.findViewById(R.id.firstName_txt);
        lastName = view.findViewById(R.id.lastName_txt);
        email = view.findViewById(R.id.email_txt);
        password = view.findViewById(R.id.password_txt);
        btn_create_account = view.findViewById(R.id.btn_create_account);

        disclaimer.setText(Html.fromHtml(getString(R.string.disclaimer)));

        back_button.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.login_container, new EmailFragment())
                    .addToBackStack(null)
                    .commit();
        });
        btn_create_account.setOnClickListener(view1 -> {
            String firstName_txt = firstName.getText().toString().trim();
            String lastName_txt = lastName.getText().toString().trim();
            String email_txt = email.getText().toString().trim();
            String password_txt = password.getText().toString().trim();

            createUserAccount(firstName_txt, lastName_txt, email_txt, password_txt);
        });

        return view;
    }

    private void createUserAccount(String firstName, String lastName, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String userId = firebaseUser.getUid();

                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                        HashMap<String, Object> userDetails = new HashMap<>();
                        userDetails.put("id", userId);
                        userDetails.put("firstName", firstName);
                        userDetails.put("lastName", lastName);

                        databaseReference.setValue(userDetails)
                                .addOnCompleteListener(task1 -> {
                                    if(task1.isSuccessful()) {
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                });
                    } else {
                        Toast.makeText(getActivity(), "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}