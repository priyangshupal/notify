package com.example.geofence.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.geofence.R;
import com.google.android.material.textfield.TextInputEditText;

public class EmailFragment extends Fragment {

    TextInputEditText email;
    TextView btn_continue, create_account;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_email, container, false);

        email = rootView.findViewById(R.id.customerEmail);
        btn_continue = rootView.findViewById(R.id.btn_signin);
        create_account = rootView.findViewById(R.id.textView3);

        String create_account_txt = getString(R.string.create_account_txt);
        Spanned styledText = Html.fromHtml(create_account_txt);
        create_account.setText(styledText);

        btn_continue.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("userEmail", email.getText().toString());

            PasswordFragment passwordFragment = new PasswordFragment();
            passwordFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.login_container, passwordFragment)
                    .addToBackStack(null)
                    .commit();
        });

        create_account.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.login_container, new CreateAccountFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }
}