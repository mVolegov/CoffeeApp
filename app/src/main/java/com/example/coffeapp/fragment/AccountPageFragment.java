package com.example.coffeapp.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.coffeapp.R;

public class AccountPageFragment extends Fragment {

    private EditText usernameEditText;
    private Button logoutButton;

    public static AccountPageFragment newInstance() {
        return new AccountPageFragment();
    }

    public AccountPageFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_page, container, false);

        setViews(view);

        return view;
    }

    private void setViews(View view) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String username = prefs.getString("username", "");
        usernameEditText = view.findViewById(R.id.usernameAccountTextView);
        usernameEditText.setText(username);

        logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(view1 -> logout());
    }

    private void logout() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("accessTokenJWT");
        editor.remove("refreshTokenJWT");
        editor.remove("username");
        editor.apply();

        AccountLoginFragment accountLoginFragment = AccountLoginFragment.newInstance();

        FragmentTransaction fragmentTransaction = getActivity()
                .getSupportFragmentManager()
                .beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, accountLoginFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}