package com.example.coffeapp.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coffeapp.R;
import com.example.coffeapp.network.CoffeeAPI;
import com.example.coffeapp.network.DTO.OrderDTO;
import com.example.coffeapp.network.DTO.UserDTO;
import com.example.coffeapp.network.RetrofitInstance;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.security.SecureRandom;
import java.security.spec.ECField;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountLoginFragment extends Fragment {

    private EditText loginEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    public static AccountLoginFragment newInstance() {
        return new AccountLoginFragment();
    }

    public AccountLoginFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_login, container, false);

        setViews(view);

        return view;
    }

    private void setViews(View view) {
        loginEditText = view.findViewById(R.id.loginEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);

        loginButton = view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view1 -> login());

        registerButton = view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(view1 -> register());
    }

    private void login(){
        // TODO: login
    }

    private void register(){
        String username = loginEditText.toString();
        String password = passwordEditText.toString();

        if (username.equals("") || password.equals("")){
            Snackbar
                    .make(getView().findViewById(R.id.accountConstraintLayout), "Ошибка. Заполните обя поля", BaseTransientBottomBar.LENGTH_SHORT)
                    .setTextColor(getResources().getColor(R.color.white))
                    .setBackgroundTint(getResources().getColor(R.color.grey_default))
                    .show();
            return;
        }

        String cryptedPassword = hashPassword(password);

        CoffeeAPI api = RetrofitInstance.getRetrofitClient().create(CoffeeAPI.class);
        UserDTO userDTO = new UserDTO(username, cryptedPassword);
        Call<UserDTO> call = api.registerUser(userDTO);
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = prefs.edit();
                // TODO: get jwt from call
                editor.putString("accessTokenJWT", "");
                editor.putString("refreshTokenJWT", "");
                editor.putString("username", username);
                editor.apply();
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Snackbar
                        .make(getView().findViewById(R.id.accountConstraintLayout), "Ошибка при регистрации пользователя", BaseTransientBottomBar.LENGTH_SHORT)
                        .setTextColor(getResources().getColor(R.color.white))
                        .setBackgroundTint(getResources().getColor(R.color.grey_default))
                        .show();
            }
        });
    }

    private String hashPassword(String password) {
        return password;
    }
}
