package com.example.geofence.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.geofence.MainActivity;
import com.example.geofence.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PasswordFragment extends Fragment {

    ImageView btn_back;
    TextView forgot_pass, change, receivedEmail, btn_signin;
    EditText password;

    String userEmail;

    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_password, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        btn_back = rootView.findViewById(R.id.back_button);
        forgot_pass = rootView.findViewById(R.id.btn_forgot_pass);
        change = rootView.findViewById(R.id.change);
        receivedEmail = rootView.findViewById(R.id.receivedEmail);
        btn_signin = rootView.findViewById(R.id.btn_signin);
        password = rootView.findViewById(R.id.password);

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            userEmail = getArguments().getString("userEmail");
            receivedEmail.setText(userEmail);
        }

        change.setText(Html.fromHtml(getString(R.string.change)));
        forgot_pass.setText(Html.fromHtml(getString(R.string.forgot_password)));

        change.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.login_container, new EmailFragment())
                    .addToBackStack(null)
                    .commit();
        });
        btn_back.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.login_container, new EmailFragment())
                    .addToBackStack(null)
                    .commit();
        });
        btn_signin.setOnClickListener(view -> {
            rootView.findViewById(R.id.blocking_pass).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.progress_circular_pass).setVisibility(View.VISIBLE);
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

            String password_txt = password.getText().toString();

            firebaseAuth.signInWithEmailAndPassword(userEmail, password_txt)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid());
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    getActivity().finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            rootView.findViewById(R.id.blocking_pass).setVisibility(View.GONE);
                            rootView.findViewById(R.id.progress_circular_pass).setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Sign in failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        return rootView;
    }
}